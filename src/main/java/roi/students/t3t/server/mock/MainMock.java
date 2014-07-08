package roi.students.t3t.server.mock;

import java.time.LocalDate;
import java.util.List;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
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
		HotelRequestImpl testReq = new HotelRequestImpl("2014-07-11","2014-07-19", 2, 4,Country.Bulgaria);
		testReq.setMinStars(3);
		testReq.setMaxStars(4);
		testReq.setPeopleCount(2);
		//TODO: ограничить setter'ы, например, нельзя отрицательные числа
		testReq.setMinPrice(10000);
		testReq.setMaxPrice(60000);
		
		//client make request
		ClientSettingsImpl clientSettings = new ClientSettingsImpl();
		clientSettings.addSite(Site.itour);
		clientSettings.addSite(Site.nevatravel);
		Request request = new RequestImpl(testReq, clientSettings);
		//server make response
		AgreggationService agreggationService = new AgreggationServiceImpl();
		ServerResponse serverResponse = agreggationService.getResult(request);
		
		@SuppressWarnings("unchecked")
		List<HotelInfo> result = (List<HotelInfo>) serverResponse.getHotelInfo();
		
		if (result.isEmpty()) System.out.println("no results");
		else 
			for(HotelInfo elem : result){
				System.out.println("price = "+elem.getPrice());
				System.out.println("stars = "+elem.getStars());
				System.out.println("info "+elem.getURL());
				System.out.println(elem.getName());
				System.out.println();
			}
		
		LocalDate localDate = LocalDate.of(2014,8,15);
		LocalDate test = LocalDate.parse("2007-12-13");
		System.out.println(test);
		
	}
	
}
