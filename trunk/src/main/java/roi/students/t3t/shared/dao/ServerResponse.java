package roi.students.t3t.shared.dao;

import java.util.List;

public interface ServerResponse {
	public List<? extends HotelInfo> getHotelInfo();
	public Request getRequest();
}
