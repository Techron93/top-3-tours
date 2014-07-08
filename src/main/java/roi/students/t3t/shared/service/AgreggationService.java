package roi.students.t3t.shared.service;

import java.util.concurrent.ExecutionException;

import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;

public interface AgreggationService {
	public static int resultSiteNum = 3;
	public ServerResponse getResult(Request request);
}
