package roi.students.t3t.server.parsers;

import static org.junit.Assert.*;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.impl.ClientSettingsImpl;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;
import roi.students.t3t.shared.dao.impl.RequestImpl;

public class ParserNevaTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetList() {
		//HotelRequestImpl testReq = new HotelRequestImpl("2014-07-11","2014-07-19", 2, 5,Country.Bulgaria);
		ParserNeva parser = new ParserNeva();
		HotelRequestImpl request = new HotelRequestImpl();
		//HotelRequestImpl request;// = new HotelRequestImpl("2014-07-11","2014-07-19", 2, 5,Country.Bulgaria);
		  request.setMinPrice(10000);
		  request.setMaxPrice(90000);
		  request.setMinDuration(5);
		  request.setMaxDuration(10);
		  request.setPeopleCount(2);
		  request.setMinStars(3);
		  request.setMaxStars(5);
		  request.setCountry(Country.Turkey);
		  request.setTypeFood(TypeFood.AI);
		  
		  
		  List<HotelInfo> list = parser.getList(request);
		  System.out.println(parser.buildUrl(request));
		  
		  for(HotelInfo hotel : list){
		   System.out.println(hotel);
		  }
		  assertTrue ("yes", list.equals(list));
	}
	

	@Test
	public void testBuildUrl() {
		ParserNeva parserNeva = new ParserNeva();
		Date dstart = new Date(114, 6, 15);
		Date dfinish = new Date(114,6,25);
		HotelRequestImpl testReq = new HotelRequestImpl(dstart, dfinish, 2, 4,Country.Bulgaria);
		testReq.setMinStars(3);
		testReq.setMaxStars(4);
		testReq.setPeopleCount(2);
		testReq.setMinPrice(10000);
		testReq.setMaxPrice(60000);
		testReq.setTypeFood(TypeFood.AI);
			
		
		
		String tmp = parserNeva.buildUrl(testReq);
		System.out.println(tmp);
		assertTrue("all good", tmp.equals("http://www.nevatravel.ru/tours/search/spb/blg_-_-_-_-/7/14/15.07.2014/25.07.2014/2/0/-/AI/3-4/10000/60000/-/-/-/-/-/search"));
	}
	

	@Test
	public void testGetDateForUrl() {
		ParserNeva parserNeva = new ParserNeva();
		Date test = new Date(94,5,28);
		String test1 = new String("28.06.1994");
		String tmp = parserNeva.getDateForUrl(test);
		System.out.println(test1);
		System.out.println(tmp);
		assertTrue("yes yes yes",tmp.equals(test1));
	}

}
