
package roi.students.t3t.shared.dao.impl;

import java.io.Serializable;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelRequest;

public class HotelRequestImpl implements HotelRequest, Serializable {
	
	private static final long serialVersionUID = -2697439235862041240L;
	
	/**Самая ранняя дата вылета*/
	private String startDate;
	/**Самая поздняя дата вылета*/
	private String finishDate;
	/**Минимальная длительность тура*/
	private String minDuration;
	/**Максимальная длительность тура*/
	private String maxDuration;
	/**Страна прилета*/
	private Country country;
	
	private int minStars;
	private int maxStars;
	private TypeFood typeFood;
	//дополнительные параметры по умолчанию
	private int minPrice = 0;
	private int maxPrice = 0;
	private int peopleCount = 0; //TODO: сделать enum на основе предложений сайтов
	
	public HotelRequestImpl() {
		startDate = null; //текущая дата
		finishDate = null;
		country = null;
	}
	
	public HotelRequestImpl(String startDate, String finishDate, int minStars, int maxStars, Country country){
		setStartDate(startDate);
		setFinishDate(finishDate);
		setMinStars(minStars);
		setMaxStars(maxStars);
		setCountry(country);
	}
	
	public HotelRequestImpl(String startDate, String finishDate, Country country, int minStars, int maxStars, TypeFood typeFood, 
			int minPrice, int maxPrice, int peopleCount, String minDuration, String maxDuration){
		
		setStartDate(startDate);
		setFinishDate(finishDate);
		setCountry(country);
		setMinStars(minStars);
		setMaxStars(maxStars);
		setTypeFood(typeFood);
		setMinPrice(minPrice);
		setMaxPrice(maxPrice);
		setPeopleCount(peopleCount);
		setMinDuration(minDuration);
		setMaxDuration(maxDuration);
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
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
	public String getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(String duration) {
		this.minDuration = duration;
	}
	public String getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(String duration) {
		this.maxDuration = duration;
	}
}
