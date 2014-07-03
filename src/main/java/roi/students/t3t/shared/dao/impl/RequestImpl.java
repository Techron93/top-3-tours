package roi.students.t3t.shared.dao.impl;

import roi.students.t3t.shared.dao.ClientSettings;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;

public class RequestImpl implements Request {
	
	private HotelRequest hotelRequest;
	private ClientSettings clientSettings;
	
	public RequestImpl(){
		hotelRequest = new HotelRequestImpl();
		clientSettings = new ClientSettingsImpl();
	}
	
	public RequestImpl(HotelRequest hotelRequest, ClientSettings clientSettings){
		setHotelRequest(hotelRequest);
		setClientSettings(clientSettings);
	}
	
	public HotelRequest getHotelRequest() {
		return hotelRequest;
	}
	public void setHotelRequest(HotelRequest hotelRequest) {
		this.hotelRequest = hotelRequest;
	}
	public ClientSettings getClientSettings() {
		return clientSettings;
	}
	public void setClientSettings(ClientSettings clientSettings) {
		this.clientSettings = clientSettings;
	}
}
