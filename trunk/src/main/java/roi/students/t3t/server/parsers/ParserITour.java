package roi.students.t3t.server.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;

public class ParserITour implements SiteParser{
	
	public ParserITour()
	{}

	public List<HotelInfo> getList(HotelRequest request) {
		
		String url = buildUrl(request);
		List<HotelInfo> result = new ArrayList<HotelInfo>(0);
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		HtmlPage page = null;
		Document doc = null;
		try
		{
			page = webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(10000);
			String xmlPage = page.asXml();
			doc = Jsoup.parse(xmlPage);
			
			FileWriter writer = new FileWriter("site.xml");
			writer.write(xmlPage);
			writer.close();
			
			Elements elements = doc.select("div.tour-group.cfx.ng-scope");
			for (int i = 0; i < elements.size(); i++)
			{
				Elements els_name = elements.get(i).select("div.name.ng-scope");
				Elements els_stars = elements.get(i).select("div.hotel-stars.ng-binding");
				Elements els_price = elements.get(i).select("a.alt.ng-scope.ng-binding");
				Elements els_link= elements.get(i).select("a.btn.btn-xs.btn-green.btn-more");
				String hyperLink = els_link.get(0).attr("abs:href");
				Elements els_date = elements.get(i).select("span.hover-departure-date.ng-binding");
				String day = els_date.text();
				String[] date = day.split(",");
				StringBuilder priceString = new StringBuilder(els_price.get(0).text());
				String[] priceM = priceString.substring(2, priceString.length() - 5).split(" ");
				String priceS = "";
				for (String temp : priceM)
					priceS += temp;
				if (request.getMinStars() == Integer.parseInt(els_stars.get(0).text()))
					result.add(new HotelInfoImpl(hyperLink, Integer.parseInt(priceS), els_name.get(0).text(), Integer.parseInt(els_stars.get(0).text()), date[0]));
			}
		}
		catch (MalformedURLException e)
		{e.printStackTrace();}
		catch (IOException e)
		{e.printStackTrace();}
		return result;
	}

	public String buildUrl(HotelRequest request){
		
		double mesure = getBaxPrice();
		int peopleCount = 4;
		peopleCount = request.getPeopleCount() != 4 ? (request.getPeopleCount() - 1) : peopleCount;
		int roundedMinPrice = (int) Math.round(request.getMinPrice() / mesure);
		int roundedMaxPrice = (int) Math.round(request.getMaxPrice() / mesure);
		
		
		StringBuilder url = new StringBuilder();
		url.append("http://itour.ru/tour/?city=2&room=" + peopleCount);
		url.append("&childAges[]=14&childAges[]=14&arrivalCountry=" + request.getCountry().itour);
		url.append("&arrivalCountryCode=" + request.getCountry().idITour);
		url.append("&nightsFrom=" + request.getMinDuration());
		url.append("&nightsTo=" + request.getMaxDuration());
		url.append("&grade=" + (request.getMinStars() - 1));
		url.append("&meal=" + TypeFood.AI.toString());
		url.append("&priceType=0&departureFrom=" + formatDate(request.getStartDate()));
		url.append("&departureTo=" + formatDate(request.getFinishDate()));
		url.append("&priceFrom=" + roundedMinPrice);
		url.append("&priceTo=" + roundedMaxPrice);
		url.append("&mealsBetter=false&gradesBetter=false&currencyCode=RUR&bestHotels=0&limit=60");
		
		return url.toString();
	}
	
	
	//находим курс доллара, используя API Центробанка
	public static double getBaxPrice(){
		Number result = null;
		try
		{
	        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			
			Date date = new Date();
			
			String day = (date.getDate() < 10 ? "0" : "") + date.getDate();
			String month = (LocalDate.now().getDayOfMonth() < 10 ? "0" : "") + (date.getMonth() + 1);
			String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + day  + "/" + month +"/" + (date.getYear() + 1900);
			
			XmlPage page = webClient.getPage(url);
	        String pageAsString = page.asXml();
			Document doc = Jsoup.parse(pageAsString);
			Element el = doc.getElementById("R01235");
			List<Element> values = el.getElementsByTag("Value");
		    
			//автоматически форматируем при помощи заданного шаблона
			DecimalFormat format = new DecimalFormat("#0.0000");
		    result = format.parse(values.get(0).text());
		}
		catch (IOException e)
		{e.printStackTrace();}
		catch (ParseException e)
		{e.printStackTrace();}
		
		return result.doubleValue();
	}
	
	private String formatDate(String oldFormat)
	{
		String[] temp = oldFormat.split("-");
		StringBuilder newFormat = new StringBuilder();
		newFormat.append(temp[2] + ".");
		newFormat.append(temp[1] + ".");
		newFormat.append(temp[0]);
		return newFormat.toString();
	}
}

