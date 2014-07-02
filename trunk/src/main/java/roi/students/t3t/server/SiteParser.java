package roi.students.t3t.server;

import java.util.List;

import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.Site;

public interface SiteParser {
	public List<HotelInfo> getList(HotelRequest request);
	public Site getSite();
}
