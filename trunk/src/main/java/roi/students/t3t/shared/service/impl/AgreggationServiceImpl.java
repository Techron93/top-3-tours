package roi.students.t3t.shared.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import roi.students.t3t.server.parsers.ParserNeva;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;
import roi.students.t3t.shared.service.AgreggationService;

public class AgreggationServiceImpl implements AgreggationService {
	
	
	public ServerResponse getResult(Request request) {
		List<HotelInfo> parserResults = new ArrayList<HotelInfo>();
		Set<Site> siteSet = request.getClientSettings().getSiteList();
		HotelRequest hotelRequest = request.getHotelRequest();
		for(Site site:siteSet){
			switch(site){
			case itour:
//				MockITourParser itourParser = new MockITourParser();
//				parserResults.addAll(itourParser.getList(hotelRequest));
				break;
			case teztour:
//				MockTezTourParser tezTourParser = new MockTezTourParser();
//				parserResults.addAll(tezTourParser.getList(hotelRequest));
				break;
			case nevatravel:
				ParserNeva nevaTravelParser = new ParserNeva();
				parserResults.addAll(nevaTravelParser.getList(hotelRequest));
				break;
			default:
				break;
			}
		}
		
		return new ServerResponseImpl(AgreggationServiceSuit.getThreeBest(parserResults),request);
	}

}
