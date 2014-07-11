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

public class ParserITourTest {

	@Before
	public void setUp() throws Exception {
	}

	//public void FormatDateTest() {
		//ParserITour parserItour = new ParserITour();
		//StringBuilder newFormat = new StringBuilder();
		//Date test = new Date(94,5,28);
		//String test1 = new String("28.06.1994");
		//String tmp = parserItour.formatDate(test);
	//	System.out.println(test1);
	//	System.out.println(tmp);
	//	assertTrue("yes yes yes",tmp.equals(test1));
	//}
	@Test
	public void buildUrl1() {
		ParserITour parserItour = new ParserITour();
		Date dstart = new Date(114, 6, 15);
		Date dfinish = new Date(114,6,25);
		HotelRequestImpl testReq = new HotelRequestImpl(dstart, dfinish, Country.Bulgaria, 3, 4, TypeFood.UAI, 10000, 90000, 3, 3, 6);
		
		
		String temp = parserItour.buildUrl(testReq);
		System.out.println(temp);
		assertTrue("all good", temp.equals("http://itour.ru/tour/?city=2&room=2&childAges%5B%5D=14&childAges%5B%5D=14&arrivalCountry=bg&arrivalCountryCode=66&nightsFrom=3&nightsTo=6&grade=2&meal=1&priceType=0&departureFrom=15.7.2014&departureTo=25.7.2014&priceFrom=296&priceTo=2660&mealsBetter=false&gradesBetter=false&currencyCode=RUR&bestHotels=0&limit=20"));
	}
	@Test
	public void buildUrl2() {
		ParserITour parserItour = new ParserITour();
		Date dstart = new Date(114, 8, 15);
		Date dfinish = new Date(114,9,25);
		HotelRequestImpl testReq = new HotelRequestImpl(dstart, dfinish, Country.Egypt, 3, 4, TypeFood.UAI, 10000, 90000, 3, 3, 6);
		
		
		String temp = parserItour.buildUrl(testReq);
		System.out.println(temp);
		assertTrue("all good", temp.equals("http://itour.ru/tour/?city=2&room=2&childAges%5B%5D=14&childAges%5B%5D=14&arrivalCountry=eg&arrivalCountryCode=2&nightsFrom=3&nightsTo=6&grade=2&meal=1&priceType=0&departureFrom=15.9.2014&departureTo=25.10.2014&priceFrom=296&priceTo=2660&mealsBetter=false&gradesBetter=false&currencyCode=RUR&bestHotels=0&limit=20"));
	}
	@Test
	public void buildUrl3() {
		ParserITour parserItour = new ParserITour();
		Date dstart = new Date(114, 10, 15);
		Date dfinish = new Date(114,10,25);
		HotelRequestImpl testReq = new HotelRequestImpl(dstart, dfinish, Country.Egypt, 3, 4, TypeFood.AI, 20000, 90000, 4, 3, 6);
		
		
		String temp = parserItour.buildUrl(testReq);
		System.out.println(temp);
		assertTrue("all good", temp.equals("http://itour.ru/tour/?city=2&room=4&childAges%5B%5D=14&childAges%5B%5D=14&arrivalCountry=eg&arrivalCountryCode=2&nightsFrom=3&nightsTo=6&grade=2&meal=2&priceType=0&departureFrom=15.11.2014&departureTo=25.11.2014&priceFrom=591&priceTo=2660&mealsBetter=false&gradesBetter=false&currencyCode=RUR&bestHotels=0&limit=20"));
	}
}
