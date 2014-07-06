package roi.students.t3t.client;

import roi.students.t3t.shared.dao.impl.RequestImpl;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("req")
public interface ServerRequest extends RemoteService {
	ServerResponseImpl requestServer(RequestImpl request);
}
