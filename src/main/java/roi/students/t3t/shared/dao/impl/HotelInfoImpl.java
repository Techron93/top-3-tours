package roi.students.t3t.shared.dao.impl;

import java.io.Serializable;

import roi.students.t3t.shared.dao.HotelInfo;

public class HotelInfoImpl implements HotelInfo, Serializable{
	
	private static final long serialVersionUID = 87375359649805572L;
	private String url;
	private int price;
	private String name;
	private int stars;
	private String startData;
	
	
	@Override
	public String toString() {
		return "HotelInfoImpl [url=" + url + ", price=" + price + ", name="
				+ name + ", stars=" + stars + ", startData=" + startData + "]";
	}

	public HotelInfoImpl(){
		url = null;
		price = 0;
		name = null;
		stars  = 0;
	}
	
	public HotelInfoImpl(String url, int price, String name, int stars){
		setUrl(url);
		setPrice(price);
		setStars(stars);
		setName(name);
	}
	
	public HotelInfoImpl(String url, int price, String name, int stars, String startData){
		setUrl(url);
		setPrice(price);
		setStars(stars);
		setName(name);
		setStartData(startData);
	}
	
	@Override
	public String getURL() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int getStars() {
		return stars;
	}
	
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	@Override
	public String getStartData() {
		return this.startData;
	}
	
	public void setStartData(String data) {
		this.startData = data;
	}
	

}