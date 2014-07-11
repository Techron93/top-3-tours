package roi.students.t3t.server.parsers;

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

public class TourFind implements SiteParser {

	private static Logger logger = Logger.getLogger("itourLogger");

	public TourFind() {
	}

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
		try {
			page = webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(15000);
			String xmlPage = page.asXml();
			doc = Jsoup.parse(xmlPage);

			Elements elements = doc.select("div.tour-group.cfx.ng-scope");
			for (int i = 0; i < elements.size(); i++) {
				Elements els_name = elements.get(i).select("div.name.ng-scope");
				Elements els_stars = elements.get(i).select(
						"div.hotel-stars.ng-binding");
				Elements els_price = elements.get(i).select(
						"a.alt.ng-scope.ng-binding");
				Elements els_link = elements.get(i).select(
						"a.btn.btn-xs.btn-green.btn-more");
				String hyperLink = els_link.get(0).attr("abs:href");
				Elements els_date = elements.get(i).select(
						"span.hover-departure-date.ng-binding");
				Elements els_meal = elements.get(i).select(
						"span.hover-meal.ng-binding");
				// проверка на валидность типа питания
				if (request.getTypeFood().toString() != "NA") {
					String text = els_meal.text();
					String text1 = request.getTypeFood().toString();
					if (!text.equals(text1))
						continue;
				}
				String day = els_date.text();
				String[] date = day.split(",");
				StringBuilder priceString = new StringBuilder(els_price.get(0)
						.text());
				String[] priceM = priceString.substring(2,
						priceString.length() - 5).split(" ");
				String priceS = "";
				for (String temp : priceM)
					priceS += temp;
				HotelInfoImpl info = new HotelInfoImpl(hyperLink,
						Integer.parseInt(priceS), els_name.get(0).text(),
						Integer.parseInt(els_stars.get(0).text()), date[0]);
				if (checkInfo(request, info))
					result.add(info);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String buildUrl(HotelRequest request) {

		//int peopleCount = 4;
		 int peopleCount = request.getPeopleCount();// != 4 ? (request.getPeopleCount() - 1)
				//: peopleCount;

		int mealType = 115;

		switch (request.getTypeFood().toString()) {
		case "UAI":
			mealType = 116;
			break;
		case "AI":
			mealType = 115;
			break;
		case "FB":
			mealType = 112;
			break;
		case "HB":
			mealType = 113;
			break;
		case "BB":
			mealType = 114;
			break;
		case "RO":
			mealType = 117;
			break;
		}

		StringBuilder url = new StringBuilder();
		url.append("ttp://tour-find.ru/?sletat_serch_form[cityFromId]=1264&sletat_serch_form[adults]="
				+ peopleCount);
		url.append("&sletat_serch_form[kids]=0");
		url.append("&sletat_serch_form[countryId]="
				+ request.getCountry().idTourFind);
		url.append("&sletat_serch_form[cities][]=&sletat_serch_form[hotels_filter]=&sletat_serch_form[hotels][]=&sletat_serch_form[stars][]="
				+ (request.getMinStars() + 399));

		if (request.getTypeFood().toString() != "NA")
			url.append("&sletat_serch_form[meals]=" + mealType);
		else
			url.append("&sletat_serch_form[meals]=");

		url.append("&sletat_serch_form[depart][from]=" + request.getStartDate());
		url.append("&sletat_serch_form[meals]= " + request.getTypeFood());
		url.append("&sletat_serch_form[depart][to]=" + request.getStartDate());
		url.append("&sletat_serch_form[nightsMin]=" + request.getMaxDuration());
		url.append("&sletat_serch_form[nightsMax]=" + request.getMinDuration());

		return url.toString();
	}

	private boolean checkInfo(HotelRequest request, HotelInfo info)
			throws ParseException {
		// проверка на валидность цену отеля
		if (info.getPrice() < request.getMinPrice()
				|| info.getPrice() > request.getMaxPrice())
			return false;

		// проверка количества звезд
		if (info.getStars() != request.getMinStars())
			return false;

		// проверка даты заезда
		Calendar calendarBegin = new GregorianCalendar(request.getStartDate()
				.getYear(), request.getStartDate().getMonth(), request
				.getStartDate().getDate());// начальная дата заезда
		Calendar calendarEnd = new GregorianCalendar(request.getFinishDate()
				.getYear(), request.getFinishDate().getMonth(), request
				.getFinishDate().getDate());// конечная дата заезда

		String[] dateS = info.getStartData().split(" ");
		DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
		Date date = format.parse(dateS[0] + " " + dateS[1].toLowerCase() + " "
				+ ((new Date()).getYear() + 1900) + " г.");
		Calendar calendar = new GregorianCalendar(date.getYear() + 1900,
				date.getMonth() + 1, date.getDate());
		if (calendar.after(calendarEnd) || calendar.before(calendarBegin))
			return false;

		return true;
	}
}
