package roi.students.t3t.shared.dao;

import java.util.Date;

public interface HotelRequest {
	
	public Date getStartDate();
	public Date getFinishDate();
	public String getCountry();
	public String getMinStars();
	public String getMaxStars();
	public TypeFood getTypeFood();
	public int getMinPrice();
	public int getMaxPrice();
	public int getPeopleCount(); //TODO: обсудить взрослый/ребенок

}
