package roi.students.t3t.server.parsers;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;

public class ParserNeva2Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetList() {
		
		HotelRequestImpl request = new HotelRequestImpl();
		
		Date start = new Date(114, 6, 15);
		Date end = new Date(114, 6, 25);
		
		/*start.setYear(2014);
		start.setMonth(7);
		start.setDate(12);
		
		end.setYear(2014);
		end.setMonth(7);
		end.setDate(12);*/
		
		request.setStartDate(start);
		request.setFinishDate(end);
		
		request.setMinPrice(10000);
		request.setMaxPrice(90000);
		request.setMinDuration(5);
		request.setMaxDuration(10);
		request.setPeopleCount(2);
		request.setMinStars(3);
		request.setMaxStars(5);
		request.setCountry(Country.Turkey);
		request.setTypeFood(TypeFood.AI);

//		ParserITour parser2 = new ParserITour();
		ParserNeva parser = new ParserNeva();
		
//		String url = parser.buildUrl(request);
//		url = parser2.buildUrl(request);
//		System.out.println(url);
		
		List<HotelInfo> list = parser.getList(request);
		
		System.out.println(parser.buildUrl(request));
		
		for(HotelInfo hotel : list){
			System.out.println(hotel);
		}
		
		assertTrue(Boolean.TRUE);
		
		
	}

	@Test
	public void testBuildUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDateForUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSite() {
		fail("Not yet implemented");
	}

}
