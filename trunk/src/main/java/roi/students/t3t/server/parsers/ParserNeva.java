package roi.students.t3t.server.parsers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;



public class ParserNeva implements SiteParser {
	
	final int WAIT_JAVASCRIPT = 10000;
	final Site site = Site.nevatravel;
	
	 public ParserNeva() {}

	 /**
	  * Возвращает список непроданных путевок с сайта www.nevatravel.ru,
	  * соответствующий полученному запросу.
	  * Данные берем с первой страницы, которую выдает www.nevatravel.ru
	  */
	public List<HotelInfo> getList(HotelRequest request) {
		
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		
		List<HotelInfo> result = new ArrayList<HotelInfo>();
		String url = "http://www.nevatravel.ru/tours/search/spb/gre_-_-_-_-/1/15/16.06.2014/23.06.2014/2/0/-/-/-/0/29000/-/-/-/-/-/search/";
		url = buildUrl(request);
		try {
			
			HtmlPage page = webClient.getPage(url);
			// wait for javascript to complete
			webClient.waitForBackgroundJavaScript(WAIT_JAVASCRIPT);
			String xmlPage = page.asXml();

			// If we want to check what we fetched from url
			PrintWriter printWriter = new PrintWriter("page.html");
			printWriter.print(xmlPage);
			printWriter.close();

			// parse HTML, get hotel name
			Document doc = Jsoup.parse(xmlPage);
			Elements hotels = doc.select("div.oneTourBlock");
			
			//System.out.println(hotels.get(5).select("div[class=dateT floatleft]").text());
			//return null;
			
			for (Element hotel : hotels) {
				
				//получаем "Грязное" название отель
				String hotelName = hotel.select("div.tourTitle").first().text();
				System.out.println(hotelName);
				System.out.println("\n");
				//Отделим название отеля от звезд
				String cleanHotelName = null;
				String starsStr = null;
				int stars;
				
				Pattern starsHotelPattern = Pattern.compile("[\\*]+");
		        Matcher matcherPrise = starsHotelPattern.matcher(hotelName);
				if (matcherPrise.find()) {
					starsStr = matcherPrise.group(0).toString();
		        }	else {
		        	System.out.println("НАШИ ЗВЕЗЫ УКРАДЕНЫ ВСЕ ПРОПАЛО");
		        	continue; //на нашли звезд - бросам тур и переходим к следующему
		        }
				stars = starsStr.length();
				cleanHotelName = hotelName.substring(0, hotelName.indexOf('*')).trim();
				
				
				//Название города
				String sityName = hotel.select("div[class=tourInfo itemView]").get(0).select("div").get(2).text();
				System.out.println(sityName);
				System.out.println("\n");
				
				//Цена
				String priseStr = hotel.select("div[class=bookingInfo itemView]").text();
				int cleanPrise = stringPriseToInt(priseStr);
				System.out.println(cleanPrise);
				System.out.println("\n");
				
				//Проданные туры не нужны
				if(cleanPrise == -1)
					continue;
				
				//Дата
				String dirtyData = hotel.select("div[class=dateT floatleft]").text();
				String cleanData = null;
						
				Pattern dataPattern = Pattern.compile("(\\d\\d)\\.(\\d\\d)\\.(\\d\\d\\d\\d)");
		        Matcher matcherData = dataPattern.matcher(dirtyData);	
				if (matcherData.find()) {
					cleanData = matcherData.group(0).toString();
		        }	else {
		        	System.out.println("НАШИ ЗВЕЗЫ УКРАДЕНЫ ВСЕ ПРОПАЛО");
		        	continue; //на нашли звезд - бросам тур и переходим к следующему
		        }

				HotelInfoImpl hotelInfo = new HotelInfoImpl();
				hotelInfo.setName(cleanHotelName);
				hotelInfo.setPrice(cleanPrise);
				hotelInfo.setStars(stars);
				hotelInfo.setUrl(site.url);
				hotelInfo.setStartData(cleanData);
				
				result.add(hotelInfo);
			}
			

			System.out.println("Done!");
			
			
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Something went wrong, check URL or parser");
			e.printStackTrace();
		}
		
		System.out.println("HOT HOTELS!");
		/*for (HotelInfo hotel : result){
			System.out.println(hotel.getName());
			System.out.println(hotel.getURL());
			System.out.println(hotel.getPrice());
			System.out.println(hotel.getStars());
		}*/
		
		System.out.println(url);
		return result;
	}
	
/**
 * Принимает строку, в которую включена цена тура(ожидает строку вида "от 234 234 RUB")
 * @param str
 * @return
 * Возвращает эту же цену в double. (234234 в примере), -1 если тур продан.
 */
	private int stringPriseToInt(String str){
		
		String priseStr = str;
		String cleanPrise = null;
		Pattern starHotel = Pattern.compile("((\\s)(\\d)*(\\s)(\\d)+)");
        Matcher matcherPrise = starHotel.matcher(priseStr);
		if (matcherPrise.find()) {
			cleanPrise = matcherPrise.group(1).toString();
        }	else {
        	return -1;
        }
		
		cleanPrise = cleanPrise.trim();
		
		String firstPath = null, secondPath = null;
		
		firstPath = cleanPrise.substring(0, cleanPrise.indexOf(' '));
		secondPath = cleanPrise.substring(cleanPrise.indexOf(' ')+1, cleanPrise.length() - 1);
		
		firstPath = firstPath.trim();
		secondPath = cleanPrise.substring(firstPath.length()+1, cleanPrise.length());
	
		int first = 0, second = 0;
		for(int i = 0; i < firstPath.length(); i++) first = first*10 + firstPath.charAt(i) - '0'; 
		for(int i = 0; i < secondPath.length(); i++) second = second*10 + secondPath.charAt(i) - '0'; 
		
		double prise =  first*Math.pow(10., secondPath.length()) + second;
		
		return (int)prise;
	}

/** 
 * Формирует url запрос для сайта www.nevatravel.ru по полученному (HotelRequest).
 */
	public String buildUrl(HotelRequest request) {
		String startDate = getDateForUrl(LocalDate.parse(request.getStartDate()));
		long period = ChronoUnit.DAYS.between(LocalDate.parse(request.getStartDate()), 
				LocalDate.parse(request.getFinishDate()));
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
		Long.toString(period)+"/"+
		Long.toString(period)+
		"/"+startDate+"/"+startDate+"/"+
		request.getPeopleCount()+"/0/-/-/"+starsRange+"/"+
		request.getMinPrice()+"/"+request.getMaxPrice()+"/-/-/-/-/-/search";
		
		return url;
	}

	public String getDateForUrl(LocalDate date){
		int day = date.getDayOfMonth();
		String day_str;
		if(day < 10)
			day_str = "0"+day;
		else
			day_str = Integer.toString(day);
		int month = date.getMonthValue();
		String month_str;
		if(month < 10)
			month_str = "0"+month;
		else
			month_str = Integer.toString(month);
		return day_str+"."+month_str+"."+date.getYear();
	}

	public Site getSite() {
		return site;
	}

	public String BuildUrl(HotelRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
