package roi.students.t3t.client;

import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("req")
public interface ServerRequest extends RemoteService {
	ServerResponse requestServer(Request request);
}
