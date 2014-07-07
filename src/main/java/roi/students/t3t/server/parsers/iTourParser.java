package roi.students.t3t.server.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.Node;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;

public class iTourParser implements SiteParser {
	
	public iTourParser()
	{}

	public List<HotelInfo> getList(HotelRequest request) {
		List<HotelInfo> result = new ArrayList(0);
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		HtmlPage page = null;
		Document doc = null;
		try {
			page = webClient.getPage(buildUrl(request));
			webClient.waitForBackgroundJavaScript(10000);
			String xmlPage = page.asXml();
			doc = Jsoup.parse(xmlPage);
			
			Elements elements = doc.select("div.tour-group.cfx.ng-scope");
			for (int i = 0; i < elements.size(); i++)
			{
				Elements els_name = elements.get(i).select("div.name.ng-scope");
				Elements els_stars = elements.get(i).select("div.hotel-stars.ng-binding");
				Elements els_price = elements.get(i).select("a.alt.ng-scope.ng-binding");
				char[] priceM = els_price.get(0).text().toCharArray();
				String priceS = "";
				for (int j = 0; j < priceM.length; j++)
					if (priceM[j] >= '0' && priceM[j] <= '9')
						priceS += priceM[j];
				int price = Integer.parseInt(priceS);
				result.add(new HotelInfoImpl(buildUrl(request), price, els_name.get(0).text(), Integer.parseInt(els_stars.get(0).text())));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String buildUrl(HotelRequest request) {
		int id = 0;
		id = request.getCountry().itour == "tr" ? 1 : id;
		id = request.getCountry().itour == "eg" ? 2 : id;
		id = request.getCountry().itour == "cy" ? 6 : id;
		id = request.getCountry().itour == "mv" ? 19 : id;
		id = request.getCountry().itour == "bg" ? 66 : id;
		String url = "http://itour.ru/tour/?city=2&room=" + request.getPeopleCount()
				+ "&childAges[]=14&childAges[]=14&arrivalCountry=" + request.getCountry().itour
				+"&arrivalCountryCode=" + id
				+ "&nightsFrom=" + request.getMinDuration()
				+ "&nightsTo=" + request.getMaxDuration()
				+ "&grade=" + (request.getMinStars() - 1)
				+ "&meal=" + request.getTypeFood()
				+ "&priceType=1&departureFrom=" + request.getStartDate()
				+ "&departureTo=" + request.getFinishDate()
				+ "&priceFrom=" + request.getMinPrice()
				+ "&priceTo=" + request.getMaxPrice()
				+ "&mealsBetter=true&gradesBetter=false&currencyCode=RUR&bestHotels=20&limit=20";
		return url;
	}
}

