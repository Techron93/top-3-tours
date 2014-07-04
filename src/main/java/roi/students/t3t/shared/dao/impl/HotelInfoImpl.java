package roi.students.t3t.shared.dao.impl;

import roi.students.t3t.shared.dao.HotelInfo;

public class HotelInfoImpl implements HotelInfo{
	
	private String url;
	private int price;
	private String name;
	private int stars;
	
	
	@Override
	public String toString() {
		return "HotelInfoImpl [url=" + url + ", price=" + price + ", name="
				+ name + ", stars=" + stars + "]";
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
	
	public String getURL() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	

}
