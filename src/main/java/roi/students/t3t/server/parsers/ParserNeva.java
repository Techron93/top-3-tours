package roi.students.t3t.server.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class ParserNeva implements SiteParser {
	
	final int WAIT_JAVASCRIPT = 10000;
	final Site site = Site.nevatravel;
	
	public final static Pattern dataPattern = Pattern.compile("(\\d\\d)\\.(\\d\\d)\\.(\\d\\d\\d\\d)");
	public final static Pattern starsHotelPattern = Pattern.compile("[\\*]+");
	public final static Pattern urlPattern = Pattern.compile("(/tours/)[\\S]+");
	public final static Pattern mealPattern = Pattern.compile("\\(\\s\\S\\S\\s\\)");
	
	private Calendar calendar;

	 private static Logger logger =
	            Logger.getLogger("nevaLogger");
	
	public ParserNeva() {
		calendar = new GregorianCalendar();
	}

	 
	public List<HotelInfo> getList(HotelRequest request) {
		
		
		
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		

		
		List<HotelInfo> result = new ArrayList<HotelInfo>();
		
		String url = buildUrl(request);
		try {
			
			HtmlPage page = webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(WAIT_JAVASCRIPT);
			String xmlPage = page.asXml();

			Document doc = Jsoup.parse(xmlPage);
			Elements hotels = doc.select("div.oneTourBlock");

			//System.out.println(hotels.get(5).select("div[class=meal]").text());
            //return null;

			for (Element hotel : hotels) {
				
				String hotelName = hotel.select("div.tourTitle").first().text();

				String cleanHotelName = null;
				String starsStr = null;
				int stars;
		        Matcher matcherPrise = starsHotelPattern.matcher(hotelName);
				if (matcherPrise.find()) {
					starsStr = matcherPrise.group(0).toString();
		        }	else {
		        	//TODO need logging 
		        	continue; 
		        }
				stars = starsStr.length();
				cleanHotelName = hotelName.substring(0, hotelName.indexOf('*')).trim();
				
				String sityName = hotel.select("div[class=tourInfo itemView]").get(0).select("div").get(2).text();
				
				String priseStr = hotel.select("div[class=bookingInfo itemView]").text();
				int cleanPrise = stringPriсeToInt(priseStr);
				if(cleanPrise == -1)
					continue;
				
				String dirtyData = hotel.select("div[class=dateT floatleft]").text();
				String cleanData = null;		
		        Matcher matcherData = dataPattern.matcher(dirtyData);	
				if (matcherData.find()) {
					cleanData = matcherData.group(0).toString();
		        }	else {
		        	//TODO need logging 
		        	continue; 
		        }
				
				String dirtyMeal = hotel.select("div[class=meal]").text();
				String cleanMeal = null;		
		        Matcher matcherMeal = mealPattern.matcher(dirtyMeal);	
				if (matcherMeal.find()) {
					cleanMeal = matcherMeal.group(0).toString().replaceAll("\\( | \\)", "");
		        }	else {
		        	//TODO need logging 
		        	continue; 
		        }
				
				
				String dirtyUrl = hotel.select("div[class=tourTitle]").get(0).toString();
				String cleanUrl = null;
		        Matcher matcherUrl = urlPattern.matcher(dirtyUrl);	
				if (matcherUrl.find()) {
					cleanUrl = matcherUrl.group(0).toString();
					cleanUrl = cleanUrl.substring(0, cleanUrl.length() - 1);
					cleanUrl = "http://www.nevatravel.ru" + cleanUrl;
		        }	else {
		        	//TODO need logging 
		        }

				HotelInfoImpl hotelInfo = new HotelInfoImpl();
				hotelInfo.setName(cleanHotelName);
				hotelInfo.setPrice(cleanPrise);
				hotelInfo.setStars(stars);
				hotelInfo.setUrl(cleanUrl);
				hotelInfo.setStartData(cleanData);
				
				
				result.add(hotelInfo);
			}
				
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Something went wrong, check URL or parser");
			e.printStackTrace();
		}
		
		return result;
	}
	
/**
 * Принимает строку, в которую включена цена тура(ожидает строку вида "от 234 234 RUB")
 * @param str
 * @return
 * Возвращает эту же цену в int. (234234 в примере), -1 если тур продан.
 */
	public int stringPriсeToInt(String str){
		
		String priсeStr = str;
		String cleanPrice = null;
		Pattern starHotel = Pattern.compile("((\\s)(\\d)*(\\s)(\\d)+)");
		
        Matcher matcherPriсe = starHotel.matcher(priсeStr);
		if (matcherPriсe.find()) {
			cleanPrice = matcherPriсe.group(1).toString();
        }	else {
        	return -1;
        }
		
		
		cleanPrice = cleanPrice.replaceAll("\\s", "");
		Integer price = Integer.valueOf(cleanPrice);
		
		return price.intValue();
	}

/** 
 * Формирует url запрос для сайта www.nevatravel.ru по полученному (HotelRequest).
 */
	public String buildUrl(HotelRequest request) {
		
		logger.error("Hello! It's my ERROR LOG");
		System.out.println("SYSOUT");
		
		String starsRange;
		
		if(request.getMinStars() == request.getMaxStars())
			starsRange = Integer.toString(request.getMinStars());
		else{
			int min = request.getMinStars();
			starsRange = Integer.toString(min);
			for(int i = min+1; i <= request.getMaxStars(); ++i){
				starsRange+="-"+Integer.toString(i);
			}
		}
		String food = "-";
		if(request.getTypeFood() != TypeFood.NA)
			food = request.getTypeFood().toString();
		String url = 
	    "http://www.nevatravel.ru/tours/search/spb/"+
		request.getCountry().nevatravel+
		"_-_-_-_-/"+
		request.getMinDuration()+"/"+
		request.getMaxDuration()+
		"/"+ getDateForUrl(request.getStartDate()) +"/"+ getDateForUrl(request.getFinishDate()) +"/"+
		request.getPeopleCount()+"/0/-/"+food+"/"+starsRange+"/"+
		request.getMinPrice()+"/"+request.getMaxPrice()+"/-/-/-/-/-/search";
		
		return url;
	}

	public Site getSite() {
		return site;
	}
	
	public String getDateForUrl(Date date){		
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);		
		String day_str;		
		if(day < 10)		
			day_str = "0"+day;		
		else		
			day_str = Integer.toString(day);		
		int month = calendar.get(Calendar.MONTH)+1;		
		String month_str;		
		if(month < 10)		
			month_str = "0"+month;		
		else		
			month_str = Integer.toString(month);		
		return day_str+"."+month_str+"."+calendar.get(Calendar.YEAR);		
	}

}
