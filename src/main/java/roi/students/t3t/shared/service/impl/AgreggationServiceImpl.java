package roi.students.t3t.shared.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import roi.students.t3t.server.parsers.ParserITour;
import roi.students.t3t.server.parsers.ParserNeva;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;
import roi.students.t3t.shared.service.AgreggationService;

public class AgreggationServiceImpl implements AgreggationService {

	final ParserITour itourParser = new ParserITour();
	final ParserNeva nevaTravelParser = new ParserNeva();

	public ServerResponse getResult(Request request) {
		final HotelRequest hotelRequest = request.getHotelRequest();

		//
		// интерфесы для параллельной обработки инфы с парсеров
		// добавил 3, т. к. у нас по идее столько парсеров должно быть
		//
		Callable<List<HotelInfo>> callable1 = new Callable<List<HotelInfo>>() {

			@Override
			public List<HotelInfo> call() throws Exception {
				return itourParser.getList(hotelRequest);
			}
		};
		Callable<List<HotelInfo>> callable2 = new Callable<List<HotelInfo>>() {

			@Override
			public List<HotelInfo> call() throws Exception {
				return nevaTravelParser.getList(hotelRequest);
			}
		};
		/*
		 * Callable<List<HotelInfo>> callable3 = new
		 * Callable<List<HotelInfo>>(){
		 * 
		 * @Override public List<HotelInfo> call() throws Exception { //TODO:
		 * here must be method from third parser return null; } };
		 */

		ArrayList<HotelInfo> result = new ArrayList<HotelInfo>();

		// ArrayList<Future<List<HotelInfo>>> resultsAsync = new
		// ArrayList<Future<List<HotelInfo>>>();
		// List<HotelInfo> parserResults = new ArrayList<HotelInfo>();
	 Set<Site> siteSet = request.getClientSettings().getSiteList();
		// ExecutorService exec = Executors.newCachedThreadPool();
		for (Site site : siteSet) {
			switch (site) {
			case itour:
				result.addAll(itourParser.getList(hotelRequest));
				break;
			//	resultsAsync.add(exec.submit(callable1));
			case teztour:
				// MockTezTourParser tezTourParser = new MockTezTourParser();
				// parserResults.addAll(tezTourParser.getList(hotelRequest));
				break;
			case nevatravel:
				result.addAll(nevaTravelParser.getList(hotelRequest));
				
				//resultsAsync.add(exec.submit(callable2));
				break;
			default:
				break;
			}
		}
	/*	exec.shutdown();

		for (Future<List<HotelInfo>> info : resultsAsync)
			try {
				parserResults.addAll(info.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		return new ServerResponseImpl(
				AgreggationServiceSuit.getThreeBest(result), request);
	}
}
