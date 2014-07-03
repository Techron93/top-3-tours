package roi.students.t3t.server.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roi.students.t3t.server.SiteParser;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelInfoImpl;

public class MockITourParser implements SiteParser {

	private Site site;
	private String[] countries;//набор сайтов для тестов
	private String url;
	private String name;
	
	public MockITourParser(){
		site = Site.itour;
		countries = new String[5];
		countries[0] = "Egypt";
		countries[1] = "Thailand";
		countries[2] = "Sweden";
		countries[3] = "Turkey";
		countries[4] = "Australia";
		url = "";
		name = "itour";
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randomNum;
	}
	
	public List<HotelInfo> getList(HotelRequest request) {
		List<HotelInfo> resultList = new ArrayList<HotelInfo>();
		for(int i=0; i < 1000; i++){
			Date siteDate = new Date(randInt(2014,2015), randInt(1,12), randInt(1,28));
			String  siteCountry = countries[randInt(0,4)];
			int sitePrice = randInt(1,100000);
			int siteStars = randInt(1,5);
			if(request.getStartDate().compareTo(siteDate) <= 0 && request.getFinishDate().compareTo(siteDate) >= 0 &&
					request.getCountry() == siteCountry && request.getMinPrice() <= sitePrice && 
					request.getMaxPrice() >= sitePrice && request.getMinStars() <= siteStars &&
					request.getMaxStars() >= siteStars){
				//TODO: enum не пашет как хотелось бы, надо пофиксить
				resultList.add(new HotelInfoImpl(siteCountry+" "+siteDate.toString()+" year " + siteDate.getYear(),
						sitePrice,name,siteStars));
			}
		}
		return resultList;
	}

	public Site getSite() {
		return site;
	}

	public String BuildUrl(HotelRequest request) {
		return null;
	}

}
