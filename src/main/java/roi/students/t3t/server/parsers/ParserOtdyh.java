package roi.students.t3t.server.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class ParserOtdyh implements SiteParser {
	
	final int WAIT_JAVASCRIPT = 25000;
	final Site site = Site.tourfind;
	
	
	public final static Pattern dataPattern = Pattern.compile("(\\d\\d)\\.(\\d\\d)\\.(\\d\\d\\d\\d)");
	public final static Pattern mealPattern = Pattern.compile("\\(\\s\\S\\S\\s\\)");
	public final static Pattern urlPattern = Pattern.compile("(order/)[\\S|\\s]+");
	
	
	public ParserOtdyh() {}
	 
	public List<HotelInfo> getList(HotelRequest request) {
		
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		
		List<HotelInfo> result = new ArrayList<HotelInfo>();
		
		//String url = buildUrl(request);
		String url = "http://www.tour-find.ru/?sletat_serch_form[cityFromId]=1264&sletat_serch_form[adults]=2&sletat_serch_form[kids]=0&sletat_serch_form[countryId]=40&sletat_serch_form[cities][]=&sletat_serch_form[hotels_filter]=&sletat_serch_form[hotels][]=&sletat_serch_form[stars][]=&sletat_serch_form[meals]=112&sletat_serch_form[depart][from]=11.07.2014&sletat_serch_form[depart][to]=17.07.2014&sletat_serch_form[nightsMin]=5&sletat_serch_form[nightsMax]=14&sb1.x=131&sb1.y=12";
		try {
			
			HtmlPage page = webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(WAIT_JAVASCRIPT);
			String xmlPage = page.asXml();

			Document doc = Jsoup.parse(xmlPage);
			
			
			//System.out.println(doc.toString());
			Elements tableHotels = doc.select("div[style=padding:10px 0;border-top:1px solid #eeeae4;border-bottom:1px solid #eeeae4;]");
			

			for (Element hotel : tableHotels) {
				//System.out.println(hotel.toString());
				//System.out.println(hotel.select("b").text());
				int price = Integer.valueOf(hotel.select("b").text());
				
				String hotelName = (hotel.select("a").text());
				String stars = (hotel.select("div[style=margin-left:150px;height:90px;width:350px;]").text());
				stars = stars.substring(stars.indexOf('*')-1, stars.indexOf('*'));
				int star = Integer.valueOf(stars);
				
				String dirtyData = (hotel.select("div[style=margin-left:150px;height:90px;width:350px;]").text());
				String cleanData = null;		
		        Matcher matcherData = dataPattern.matcher(dirtyData);	
				if (matcherData.find()) {
					cleanData = matcherData.group(0).toString();
		        }	else {
		        	System.out.print("Nothing found");
		        	//TODO need logging 
		        	continue; 
		        }
				
				String dirtyUrl = hotel.select("div[style=padding:10px 0;border-top:1px solid #eeeae4;border-bottom:1px solid #eeeae4;]").get(0).toString();
				String cleanUrl = null;
		        Matcher matcherUrl = urlPattern.matcher(dirtyUrl);	
				if (matcherUrl.find()) {
					cleanUrl = matcherUrl.group(0).toString();
				
					cleanUrl = cleanUrl.substring(0, cleanUrl.indexOf('"'));
					cleanUrl = "http://www.tour-find.ru/" + cleanUrl;
		        }	else {
		        	//TODO need logging 
		        };
				
		;
				

				
				

				HotelInfoImpl hotelInfo = new HotelInfoImpl();
				hotelInfo.setName(hotelName);
				hotelInfo.setPrice(price);
				hotelInfo.setStars(star);
				hotelInfo.setUrl(url);
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
		
		String url = 
	    "http://www.nevatravel.ru/tours/search/spb/"+
		request.getCountry().nevatravel+
		"_-_-_-_-/"+
		request.getMinDuration()+"/"+
		request.getMaxDuration()+
		"/"+ request.getStartDate() +"/"+ request.getFinishDate() +"/"+
		request.getPeopleCount()+"/0/-/-/"+starsRange+"/"+
		request.getMinPrice()+"/"+request.getMaxPrice()+"/-/-/-/-/-/search";
		
		return url;
	}

	public Site getSite() {
		return site;
	}

}