
package roi.students.t3t.shared.dao.impl;

import java.io.Serializable;
import java.util.Date;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelRequest;

public class HotelRequestImpl implements HotelRequest, Serializable {
	
	private static final long serialVersionUID = -2697439235862041240L;
	
	/**Самая ранняя дата вылета*/
	private Date startDate;
	/**Самая поздняя дата вылета*/
	private Date finishDate;
	/**Минимальная длительность тура*/
	private int minDuration = 7;
	/**Максимальная длительность тура*/
	private int maxDuration = 14;
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
		startDate = new Date(); //текущая дата
		finishDate = new Date();
		country = null;
	}
	
	public HotelRequestImpl(Date startDate, Date finishDate, int minStars, int maxStars, Country country){
		setStartDate(startDate);
		setFinishDate(finishDate);
		setMinStars(minStars);
		setMaxStars(maxStars);
		setCountry(country);
	}
	
	public HotelRequestImpl(Date startDate, Date finishDate, Country country, int minStars, int maxStars, TypeFood typeFood, 
			int minPrice, int maxPrice, int peopleCount, int minDuration, int maxDuration){
		
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
	public int getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(int duration) {
		this.minDuration = duration;
	}
	public int getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(int duration) {
		this.maxDuration = duration;
	}
}
