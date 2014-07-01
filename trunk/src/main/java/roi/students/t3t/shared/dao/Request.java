package roi.students.t3t.shared.dao;

import java.util.Date;

public interface Request {
	//TODO: check Date class
	public Date getStartDate();
	public Date getFinishDate();
	public String getCountry();
	public int getStars();
	public int getPrice();
	public int getPeopleCount();
	public TypeFood getTypeFood();
}
