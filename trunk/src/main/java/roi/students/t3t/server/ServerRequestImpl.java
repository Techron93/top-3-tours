package roi.students.t3t.server;

import roi.students.t3t.client.ServerRequest;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.ServerResponse;
import roi.students.t3t.shared.service.AgreggationService;
import roi.students.t3t.shared.service.impl.AgreggationServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServerRequestImpl extends RemoteServiceServlet implements
		ServerRequest {

	public ServerResponse requestServer(Request request) {
		// server make response
		AgreggationService agreggationService = new AgreggationServiceImpl();
		return agreggationService.getResult(request);
	}

}
