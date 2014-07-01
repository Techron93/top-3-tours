package roi.students.t3t.shared.dao;

public class HotelResponse{
	private int stars;
	private String url;
	private String name;
	private int price;
	private Site site;
	
	HotelResponse(){
		stars = 0;
		price = 0;
	}
	
	HotelResponse(int stars, String url, String name, int price){
		this.stars = stars;
		this.url =url;
		this.name = name;
	}

	public int getStars() {
		return stars;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public Site getSite() {
		return site;
	}
}
