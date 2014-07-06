package roi.students.t3t.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerRequestAsync {
	/**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see roi.students.t3t.client.GreetingService
     */
    void requestServer( roi.students.t3t.shared.dao.impl.RequestImpl name, 
    		AsyncCallback<roi.students.t3t.shared.dao.impl.ServerResponseImpl> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static ServerRequestAsync instance;

        public static final ServerRequestAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (ServerRequestAsync) GWT.create( ServerRequestAsync.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
