package roi.students.t3t.shared.dao.impl;

import java.util.Date;

import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.TypeFood;

public class HotelRequestImpl implements HotelRequest {
	
	private Date startDate;
	private Date finishDate;
	private String country;
	private int minStars;
	private int maxStars;
	private TypeFood typeFood;
	//дополнительные параметры по умолчанию
	private int minPrice = 0;
	private int maxPrice = 0;
	private int peopleCount = 0; //TODO: сделать enum на основе предложений сайтов
	
	public HotelRequestImpl() {
		startDate = new Date(); //текущая дата
		finishDate = new Date();
		country = null;
	}
	
	public HotelRequestImpl(Date startDate, Date finishDate, int minStars, int maxStars, String country){
		setStartDate(startDate);
		setFinishDate(finishDate);
		setMinStars(minStars);
		setMaxStars(maxStars);
		setCountry(country);
	}
	
	public HotelRequestImpl(Date startDate, Date finishDate, String country, int minStars, int maxStars, TypeFood typeFood, 
			int minPrice, int maxPrice, int peopleCount){
		
		setStartDate(startDate);
		setFinishDate(finishDate);
		setCountry(country);
		setMinStars(minStars);
		setMaxStars(maxStars);
		setTypeFood(typeFood);
		setMinPrice(minPrice);
		setMaxPrice(maxPrice);
		setPeopleCount(peopleCount);
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getMinStars() {
		return minStars;
	}
	public void setMinStars(int minStars) {
		this.minStars = minStars;
	}
	public int getMaxStars() {
		return maxStars;
	}
	public void setMaxStars(int maxStars) {
		this.maxStars = maxStars;
	}
	public TypeFood getTypeFood() {
		return typeFood;
	}
	public void setTypeFood(TypeFood typeFood) {
		this.typeFood = typeFood;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}
}
