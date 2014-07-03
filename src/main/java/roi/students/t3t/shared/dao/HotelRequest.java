package roi.students.t3t.shared.dao;

import java.time.LocalDate;

import roi.students.t3t.shared.TypeFood;

public interface HotelRequest {
	
	public LocalDate getStartDate();
	public LocalDate getFinishDate();
	public String getCountry();
	public int getMinStars();
	public int getMaxStars();
	public TypeFood getTypeFood();
	public int getMinPrice();
	public int getMaxPrice();
	public int getPeopleCount(); //TODO: обсудить взрослый/ребенок

}
