package roi.students.t3t.server.mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.ClientSettings;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;
import roi.students.t3t.shared.dao.impl.ClientSettingsImpl;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;
import roi.students.t3t.shared.dao.impl.RequestImpl;
import roi.students.t3t.shared.service.AgreggationService;
import roi.students.t3t.shared.service.impl.AgreggationServiceImpl;

//я не использовал пока здесь Mock, но показать базовую логику можно
public class MainMock {
	public static void main(String[] args) {
		HotelRequestImpl testReq = new HotelRequestImpl(LocalDate.of(2014, 7, 11), LocalDate.of(2014, 7, 19), 2, 4,Country.Bulgaria);
		testReq.setMinStars(3);
		testReq.setMaxStars(4);
		testReq.setPeopleCount(2);
		//TODO: ограничить setter'ы, например, нельзя отрицательные числа
		testReq.setMinPrice(10000);
		testReq.setMaxPrice(60000);
		
		//client make request
		ClientSettings clientSettings = new ClientSettingsImpl();
		clientSettings.addSite(Site.teztour);
		clientSettings.addSite(Site.itour);
		Request request = new RequestImpl(testReq, clientSettings);
		//server make response
		AgreggationService agreggationService = new AgreggationServiceImpl();
		ServerResponse serverResponse = agreggationService.getResult(request);
		
		List<HotelInfo> result= serverResponse.getHotelInfo();
		
		for(HotelInfo elem : result){
			System.out.println("price = "+elem.getPrice());
			System.out.println("stars = "+elem.getStars());
			System.out.println("info "+elem.getURL());
			System.out.println(elem.getName());
			System.out.println();
		}
		
		LocalDate localDate = LocalDate.of(2014,8,15);
		System.out.println(buildUrl(testReq));
		
	}
	
	public static String buildUrl(HotelRequest request){
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
	
	public static String getDateForUrl(LocalDate date){
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
