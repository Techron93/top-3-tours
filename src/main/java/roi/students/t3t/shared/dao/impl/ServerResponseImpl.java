package roi.students.t3t.shared.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;

public class ServerResponseImpl implements ServerResponse, Serializable {

	private static final long serialVersionUID = -8736623118491281095L;
	private ArrayList<HotelInfoImpl> hotelInfo;
	private RequestImpl request;
	
	public ServerResponseImpl(){
		hotelInfo = new ArrayList<HotelInfoImpl>();
		request = new RequestImpl();
	}
	
	public ServerResponseImpl(List<? extends HotelInfo> hotelInfo, Request request){
		setHotelInfo(hotelInfo);
		setRequest(request);
	}
	
	@Override
	public List<? extends HotelInfo> getHotelInfo() {
		return hotelInfo;
	}
	
	@SuppressWarnings("unchecked")
	public void setHotelInfo(List<? extends HotelInfo> hotelInfo) {
		if (hotelInfo.getClass() == ArrayList.class
				&& (hotelInfo.isEmpty() || hotelInfo.get(0).getClass() == HotelInfoImpl.class))
			this.hotelInfo = (ArrayList<HotelInfoImpl>) hotelInfo;
		else
			throw new ClassCastException(
					"ServerResponse: setHotelInfo: \n"
							+ "cannot cast List<? extends HotelInfo> to ArrayList<HotelInfoImpl>"); 
	}
	
	@Override
	public Request getRequest() {
		return request;
	}
	
	public void setRequest(Request request) {
		if (request.getClass() == RequestImpl.class)
			this.request = ( RequestImpl ) request;
		else throw new ClassCastException("ServerResponse: setRequest: \n"
				+ "cannot cast Request to RequestImpl");
	}
	
	

}
