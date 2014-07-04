package roi.students.t3t.server.parsers;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class NevaParser implements SiteParser {

	public List<HotelInfo> getList(HotelRequest request) {
		// set up inner browser for fetching HTML page with completed javascript
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		List<HotelInfo> result = new ArrayList<HotelInfo>();

		String url = buildUrl(request);
		try {
			HtmlPage page = webClient.getPage(url);
			// wait for javascript to complete
			webClient.waitForBackgroundJavaScript(10000);
			String xmlPage = page.asXml();

			// If we want to check what we fetched from url
			PrintWriter printWriter = new PrintWriter("page.html");
			printWriter.print(xmlPage);
			printWriter.close();

			// parse HTML, get hotel name
			Document doc = Jsoup.parse(xmlPage);
			Elements hotels = doc.select("div.oneTourBlock");
			for (Element hotel : hotels) {
				String hotelName = hotel.select("div.tourTitle").first().text();
				System.out.println(hotelName);
				System.out.println("\n");

				HotelInfoImpl hotelInfo = new HotelInfoImpl();
				hotelInfo.setName(hotelName);

				result.add(hotelInfo);
			}

			System.out.println("Done!");

		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Something went wrong, check URL or parser");
			e.printStackTrace();
		}

		return result;
	}

	public String buildUrl(HotelRequest request) {
		String startDate = getDateForUrl(request.getStartDate());
		long period = ChronoUnit.DAYS.between(request.getStartDate(), request.getFinishDate());
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

}
