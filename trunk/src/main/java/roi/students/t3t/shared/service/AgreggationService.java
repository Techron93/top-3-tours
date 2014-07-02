package roi.students.t3t.shared.service;

import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;

public interface AgreggationService {
	public ServerResponse getResult(Request request);
}
