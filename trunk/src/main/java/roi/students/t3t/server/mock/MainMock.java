package roi.students.t3t.server.mock;

import java.util.Date;
import java.util.List;

import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.ClientSettings;
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
		HotelRequestImpl testReq = new HotelRequestImpl(new Date(), new Date(2014,10,20), 2, 4,"Egypt");
		//TODO: ограничить setter'ы, например, нельзя отрицательные числа
		testReq.setMinPrice(10000);
		testReq.setMaxPrice(30000);
		
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
	}
}
