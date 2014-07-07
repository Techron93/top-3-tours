package roi.students.t3t.shared.dao;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.TypeFood;

public interface HotelRequest {
	
	public String getStartDate();
	public String getFinishDate();
	public Country getCountry();
	public int getMinStars();
	public int getMaxStars();
	public TypeFood getTypeFood();
	public int getMinPrice();
	public int getMaxPrice();
	public int getPeopleCount(); //TODO: обсудить взрослый/ребенок
	public int getMaxDuration();
	public int getMinDuration();

}
