package roi.students.t3t.shared.dao.impl;

import java.util.ArrayList;
import java.util.List;

import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;

public class ServerResponseImpl implements ServerResponse {

	private List<HotelInfo> hotelInfo;
	private Request request;
	
	public ServerResponseImpl(){
		hotelInfo = new ArrayList<HotelInfo>();
		request = new RequestImpl();
	}
	
	public ServerResponseImpl(List<HotelInfo> hotelInfo, Request request){
		setHotelInfo(hotelInfo);
		setRequest(request);
	}
	
	public List<HotelInfo> getHotelInfo() {
		return hotelInfo;
	}
	public void setHotelInfo(List<HotelInfo> hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	

}
