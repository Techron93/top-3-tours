package roi.students.t3t.shared.dao;

import java.util.Date;

public class HotelRequest implements Request{
	private Date startDate, finishDate;
	private String country;
	private int stars;
	private int price = -1;
	private int peopleCount =-1;
	private TypeFood typeFood = TypeFood.NA;//TODO: Enum
	
	HotelRequest(Date startDate,Date finishDate, String country, int stars){
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.country = country;
		this.stars =stars;
	}
	
	HotelRequest(Date startDate,Date finishDate, String country, int stars, int price, int peopleCount, TypeFood typeFood){
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.country = country;
		this.stars =stars;
		this.price = price;
		this.peopleCount = peopleCount;
		this.typeFood = typeFood;
	}

	public String getCountry() {
		return country;
	}

	public int getStars() {
		return stars;
	}

	public int getPrice() {
		return price;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public TypeFood getTypeFood() {
		return typeFood;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}
}
