package roi.students.t3t.server.mock;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;
import roi.students.t3t.shared.service.impl.AgreggationServiceSuit;

public class MockTezTourParser implements SiteParser {
	private Site site;
	private Country[] countries;//набор сайтов для тестов
	private String url;
	private String name;
	
	public MockTezTourParser(){
		site = Site.teztour;
		countries = new Country[5];
		countries[0] = Country.Bulgaria;
		countries[1] = Country.Cyprus;
		countries[2] = Country.Egypt;
		countries[3] = Country.Maldives;
		countries[4] = Country.Turkey;
		url = "";
		name = "teztour";
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randomNum;
	}

	public List<HotelInfo> getList(HotelRequest request) {
		List<HotelInfo> resultList = new ArrayList<HotelInfo>();
		for(int i=0; i < 1000; i++){
			//черт, можно сделать умнее, хотя это просто заглушка
			//TODO: корректная работа с датами
			LocalDate siteDate = LocalDate.of(randInt(2014,2015), randInt(1,12), randInt(1,28));
			Country  siteCountry = countries[randInt(0,4)];
			int sitePrice = randInt(1,100000);
			int siteStars = randInt(1,5);
			if(request.getStartDate().compareTo(siteDate) <= 0 && request.getFinishDate().compareTo(siteDate) >= 0 &&
					request.getCountry() == siteCountry && request.getMinPrice() <= sitePrice && 
					request.getMaxPrice() >= sitePrice && request.getMinStars() <= siteStars &&
					request.getMaxStars() >= siteStars){
				resultList.add(new HotelInfoImpl(siteCountry+" "+siteDate.toString(),
						sitePrice,name,siteStars));
			}
		}
		return AgreggationServiceSuit.getThreeBest(resultList);
	}

	public Site getSite() {
		return site;
	}

	public String buildUrl(HotelRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
