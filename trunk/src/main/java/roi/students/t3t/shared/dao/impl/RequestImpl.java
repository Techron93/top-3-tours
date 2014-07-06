package roi.students.t3t.shared.dao.impl;

import java.io.Serializable;

import roi.students.t3t.shared.dao.ClientSettings;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Request;

public class RequestImpl implements Request, Serializable {
	
	private static final long serialVersionUID = 9089593530316029343L;
	private HotelRequestImpl hotelRequest;
	private ClientSettingsImpl clientSettings;
	
	public RequestImpl(){
		hotelRequest = new HotelRequestImpl();
		clientSettings = new ClientSettingsImpl();
	}
	
	public RequestImpl(HotelRequestImpl hotelRequest, ClientSettingsImpl clientSettings){
		setHotelRequest(hotelRequest);
		setClientSettings(clientSettings);
	}
	
	public HotelRequest getHotelRequest() {
		return hotelRequest;
	}
	public void setHotelRequest(HotelRequestImpl hotelRequest) {
		this.hotelRequest = hotelRequest;
	}
	public ClientSettings getClientSettings() {
		return clientSettings;
	}
	public void setClientSettings(ClientSettingsImpl clientSettings) {
		this.clientSettings = clientSettings;
	}
}
