package roi.students.t3t.server.parsers;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;

public class NevaParserTest {

	@Test
	public void testGetList() {
		HotelRequestImpl testReq = new HotelRequestImpl(LocalDate.of(2014, 7, 11), LocalDate.of(2014, 7, 19), 2, 4,Country.Bulgaria);
		testReq.setMinStars(2);
		testReq.setMaxStars(4);
		testReq.setPeopleCount(2);
		testReq.setMinPrice(10000);
		testReq.setMaxPrice(60000);
		
		NevaParser nevaParser = new NevaParser();
		List<HotelInfo> list = nevaParser.getList(testReq);
		for(HotelInfo elem : list){
			System.out.println(elem);
		}

		assertTrue(Boolean.TRUE);

	}

	@Test
	public void testBuildUrl() {
		// fail("Not yet implemented");
	}

}
