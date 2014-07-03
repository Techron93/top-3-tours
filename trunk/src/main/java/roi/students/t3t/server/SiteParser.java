package roi.students.t3t.server;

import java.util.List;

import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;

public interface SiteParser {
	public List<HotelInfo> getList(HotelRequest request);
	public String BuildUrl(HotelRequest request);
}
