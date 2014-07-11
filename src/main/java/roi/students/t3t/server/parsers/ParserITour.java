package roi.students.t3t.server.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
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
	
	
	
	private static Logger logger =
            Logger.getLogger("itourLogger");
	
	
	
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
			Elements elements = null;
			for (int i = 0; i < 5; i++)
			{
				page = webClient.getPage(url);
				webClient.waitForBackgroundJavaScript(15000);
				String xmlPage = page.asXml();
				doc = Jsoup.parse(xmlPage);
				elements = doc.select("div.tour-group.cfx.ng-scope");
				if (elements.size() != 0)
					break;
			}
			for (int i = 0; i < elements.size(); i++)
			{
				Elements els_name = elements.get(i).select("div.name.ng-scope");
				Elements els_stars = elements.get(i).select("div.hotel-stars.ng-binding");
				Elements els_price = elements.get(i).select("a.alt.ng-scope.ng-binding");
				Elements els_link= elements.get(i).select("a.btn.btn-xs.btn-green.btn-more");
				String hyperLink = els_link.get(0).attr("abs:href");
				Elements els_date = elements.get(i).select("span.hover-departure-date.ng-binding");
				Elements els_meal = elements.get(i).select("span.hover-meal.ng-binding");
				//проверка на валидность типа питания
				if (request.getTypeFood().toString().equals("NA"))
				{
					String  text = els_meal.text();
					String text1 = request.getTypeFood().toString();
					if (!text.equals(text1))
						continue;
				}
				String day = els_date.text();
				String[] date = day.split(",");
				StringBuilder priceString = new StringBuilder(els_price.get(0).text());
				String[] priceM = priceString.substring(2, priceString.length() - 5).split(" ");
				String priceS = "";
				for (String temp : priceM)
					priceS += temp;
				HotelInfoImpl info = new HotelInfoImpl(hyperLink, Integer.parseInt(priceS), els_name.get(0).text(), Integer.parseInt(els_stars.get(0).text()), date[0]);
				
				if (checkInfo(request, info))
					result.add(info);
			}
			
			FileWriter temp = new FileWriter("temp.txt");
			for (int i = 0; i < result.size(); i++)
			{
				temp.write(result.get(i).getName());
			}
			temp.close();
		}
		catch (MalformedURLException e)
		{e.printStackTrace();}
		catch (IOException e)
		{e.printStackTrace();}
		catch (ParseException e)
		{ e.printStackTrace();}
		
		return result;
	}

	public String buildUrl(HotelRequest request){
		
		logger.error("Hello! ITOUR ERROR LOG");
		
		double mesure = getBaxPrice();
		
		
		
		
		int peopleCount = 4;
		peopleCount = request.getPeopleCount() != 4 ? (request.getPeopleCount() - 1) : peopleCount;
		int roundedMinPrice = (int) Math.round(request.getMinPrice() / mesure);
		int roundedMaxPrice = (int) Math.round(request.getMaxPrice() / mesure);
		int id = 0;
		switch(request.getTypeFood().toString()){
			case "UAI":
				id = 1;
				break;
			case "AI":
				id = 2;
				break;
			case "FB":
				id = 3;
				break;
			case "HB":
				id = 4;
				break;
			case "BB":
				id = 5;
				break;
			case "RO":
				id = 6;
				break;
		}
		
		int duration = request.getMaxDuration() > 30 ? 30 : request.getMaxDuration();
		
		StringBuilder url = new StringBuilder();
		url.append("http://itour.ru/tour/?city=2&room=" + peopleCount);
		url.append("&childAges%5B%5D=14&childAges%5B%5D=14&arrivalCountry=" + request.getCountry().itour);
		url.append("&arrivalCountryCode=" + request.getCountry().idITour);
		url.append("&nightsFrom=" + request.getMinDuration());
		url.append("&nightsTo=" + duration);
		url.append("&grade=" + (request.getMinStars() - 1));
		url.append("&meal=" + id);
		url.append("&priceType=0&departureFrom=" + formatDate(request.getStartDate()));
		url.append("&departureTo=" + formatDate(request.getFinishDate()));
		url.append("&priceFrom=" + roundedMinPrice);
		url.append("&priceTo=" + roundedMaxPrice);
		url.append("&mealsBetter=false&gradesBetter=false&currencyCode=RUR&bestHotels=0&limit=20");
		
		try{
		FileWriter writer = new FileWriter("url.txt");
		writer.write(url.toString());
		writer.close();
		}
		catch (IOException e)
		{e.printStackTrace();}
		
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
			String month = (date.getMonth() < 10 ? "0" : "") + (date.getMonth() + 1);
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
		catch (NullPointerException e)
		{result = 34.0;}
		
		return result.doubleValue();
	}
	
	private String formatDate(Date date)
	{
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		StringBuilder newFormat = new StringBuilder();
		newFormat.append(date.getDate() + ".");
		newFormat.append((month + "."));
		newFormat.append(year);
		return newFormat.toString();
	}
	
	private boolean checkInfo(HotelRequest request, HotelInfo info) throws ParseException
	{
		//проверка на валидность цену отеля
		if (info.getPrice() < request.getMinPrice() || info.getPrice() > request.getMaxPrice())
			return false;
		
		//проверка количества звезд
		if (info.getStars() != request.getMinStars())
			return false;
		
		//проверка даты заезда
		Calendar calendarBegin = new GregorianCalendar(request.getStartDate().getYear(), request.getStartDate().getMonth(), request.getStartDate().getDate());//начальная дата заезда
		Calendar calendarEnd = new GregorianCalendar(request.getFinishDate().getYear(), request.getFinishDate().getMonth(), request.getFinishDate().getDate());//конечная дата заезда
		
		String[] dateS = info.getStartData().split(" ");
		DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
		Date date = format.parse(dateS[0] + " " + dateS[1].toLowerCase() + " " + ((new Date()).getYear()) + " г.");
		Calendar calendar = new GregorianCalendar(date.getYear(), date.getMonth() + 1, date.getDate());
		if (calendar.before(calendarBegin))
			return false;
		return true;
	}
}

