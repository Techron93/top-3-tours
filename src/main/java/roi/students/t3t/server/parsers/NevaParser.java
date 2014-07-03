package roi.students.t3t.server.parsers;

import java.io.IOException;
import java.io.PrintWriter;
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

	@Override
	public List<HotelInfo> getList(HotelRequest request) {
		// set up inner browser for fetching HTML page with completed javascript
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		List<HotelInfo> result = new ArrayList<HotelInfo>();

		String url = "http://www.nevatravel.ru/tours/search/spb/gre_-_-_-_-/1/15/16.06.2014/23.06.2014/2/0/-/-/-/0/29000/-/-/-/-/-/search/";
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

	@Override
	public String buildUrl(HotelRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
