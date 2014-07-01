package roi.students.t3t.shared.dao;

import java.util.List;

public class ServerResponse implements Response{
	
	private List<HotelResponse> hotelResponse;
	private HotelRequest hotelRequest;
	
	public List<HotelResponse> getHotelResponse() {
		return hotelResponse;
	}

	public HotelRequest getHotelRequest() {
		return hotelRequest;
	}
}
