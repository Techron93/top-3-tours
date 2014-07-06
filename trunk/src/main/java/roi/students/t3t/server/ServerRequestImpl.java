package roi.students.t3t.server;

import roi.students.t3t.client.ServerRequest;
import roi.students.t3t.shared.dao.impl.RequestImpl;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;
import roi.students.t3t.shared.service.AgreggationService;
import roi.students.t3t.shared.service.impl.AgreggationServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServerRequestImpl extends RemoteServiceServlet implements
		ServerRequest {

	@Override
	public ServerResponseImpl requestServer(RequestImpl request) {
		// server make response
		AgreggationService agreggationService = new AgreggationServiceImpl();
		ServerResponseImpl response = (ServerResponseImpl) agreggationService.getResult(request);
		// test response
//		List<HotelInfoImpl> infos = new ArrayList<HotelInfoImpl>();
//		HotelInfoImpl hi = new HotelInfoImpl("www.google.com", 100500, "Hotel", 15);
//		infos.add(hi);
//		ServerResponseImpl response = new ServerResponseImpl(infos, request);
		return response;
	}

}
