package roi.students.t3t.server;

import java.time.LocalDate;

import roi.students.t3t.client.ServerRequest;
import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.ClientSettings;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.RequestImpl;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;
import roi.students.t3t.shared.service.AgreggationService;
import roi.students.t3t.shared.service.impl.AgreggationServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServerRequestImpl extends RemoteServiceServlet implements
		ServerRequest {

	@Override
	public ServerResponseImpl requestServer(RequestImpl request) {
		validateRequest(request.getHotelRequest());
		validateClientSettings(request.getClientSettings());
		// server make response
		AgreggationService agreggationService = new AgreggationServiceImpl();
		ServerResponseImpl response = (ServerResponseImpl) agreggationService
				.getResult(request);
		// test response
		// List<HotelInfoImpl> infos = new ArrayList<HotelInfoImpl>();
		// HotelInfoImpl hi = new HotelInfoImpl("www.google.com", 100500,
		// "Hotel", 15);
		// infos.add(hi);
		// ServerResponseImpl response = new ServerResponseImpl(infos, request);
		return response;
	}

	private void validateRequest(HotelRequest hr) throws IllegalArgumentException {
		Country country = hr.getCountry();
		// Check if country is null.
		if (country == null)
			throw new IllegalArgumentException("country == null");
		// Check if country is undefined.
		switch (country) {
		case Bulgaria: case Cyprus: case Egypt: case Maldives: case Turkey:
			break;
		default:
			throw new IllegalArgumentException("no country " + country
					+ " found");
		}

		// Convert string date to LocalDate
		LocalDate start_date = LocalDate.parse(hr.getStartDate());
		LocalDate finish_date = LocalDate.parse(hr.getFinishDate());
		// Check if date is not null
		if (start_date == null || finish_date == null)
			throw new IllegalArgumentException("invalid date");
		// Check if start_date > finish_date
		if (start_date.isAfter(finish_date))
			throw new IllegalArgumentException("start_date > finish_date");

		int min_price = hr.getMinPrice();
		int max_price = hr.getMaxPrice();
		// Check if price is negative
		if (min_price < 0 || max_price < 0)
			throw new IllegalArgumentException("invalid price");
		// Check if min_price > max_price
		if (min_price > max_price)
			throw new IllegalArgumentException("min_price > max_price");

		int min_stars = hr.getMinStars();
		int max_stars = hr.getMaxStars();
		// Check if stars count is negative
		if (min_stars < 0 || max_stars < 0)
			throw new IllegalArgumentException("invalid stars count");
		// Check if min_stars > max_stars
		if (min_stars > max_stars)
			throw new IllegalArgumentException("min_stars > max_stars");
		
		int people_count = hr.getPeopleCount();
		// Check if people_count is negative
		if (people_count <= 0 || people_count > 4)
			throw new IllegalArgumentException("invalid people count");
		
		TypeFood type_food = hr.getTypeFood();
		// Check if type_food == null
		if (type_food == null)
			throw new IllegalArgumentException("invalid type_food");
		// Check if type_food is undefined.
		switch (type_food) {
		case AI: case BB: case FB: case HB: case NA: case RO: case UAI: break;
		default: throw new IllegalArgumentException("type_food is undefined");
		}
	}

	private void validateClientSettings(ClientSettings s) {
		for (Site site : s.getSiteList()) {
			switch(site) {
			case itour:
			case nevatravel:
			case teztour: break;
			default: throw new IllegalArgumentException("no site " + site + " found");
			}
		}
	}
}
