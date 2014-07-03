package roi.students.t3t.shared.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import roi.students.t3t.server.mock.MockITourParser;
import roi.students.t3t.server.mock.MockTezTourParser;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;
import roi.students.t3t.shared.service.AgreggationService;

public class AgreggationServiceImpl implements AgreggationService {
	
	public static int siteNum = 3;
	
	public ServerResponse getResult(Request request) {
		List<HotelInfo> parserResults = new ArrayList<HotelInfo>();
		Set<Site> siteSet = request.getClientSettings().getSiteList();
		HotelRequest hotelRequest = request.getHotelRequest();
		for(Site site:siteSet){
			switch(site){
			case itour:
				MockITourParser itourParser = new MockITourParser();
				parserResults.addAll(itourParser.getList(hotelRequest));
			case teztour:
				MockTezTourParser tezTourParser = new MockTezTourParser();
				parserResults.addAll(tezTourParser.getList(hotelRequest));
			default:
				break;
			}
		}
		
		return new ServerResponseImpl(doSort(parserResults),request);
	}
	
	private List<HotelInfo> doSort(List<HotelInfo> list){
		//Сортировка по цене
		//TODO: обсудить логику
		Collections.sort(list, new Comparator<HotelInfo>() {
			  public int compare(HotelInfo o1, HotelInfo o2) {
			    return Integer.compare(o1.getPrice(), o2.getPrice());
			  }
			});
		List<HotelInfo> result = new ArrayList<HotelInfo>();
		for(int i =0; i < list.size() && i < siteNum; i++){
			result.add(list.get(i));
		}

		return result;
	}

}
