package roi.students.t3t.shared.dao;

import java.util.List;

public interface SiteParser {
	public List<HotelResponse> getResponse(HotelRequest request);
}
