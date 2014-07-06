package roi.students.t3t.client;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.dao.impl.ClientSettingsImpl;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;
import roi.students.t3t.shared.dao.impl.RequestImpl;
import roi.students.t3t.shared.dao.impl.ServerResponseImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class t3t implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final ServerRequestAsync requestService = GWT
			.create(ServerRequest.class);

	private final Messages messages = GWT.create(Messages.class);

	private HTML results;
	private Button testButton;
	private Label err_label;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		final OptionsPanel options_panel = new OptionsPanel();

		RootPanel.get("options_panel").add(options_panel);
		options_panel.addStyleName("options_panel");

		VerticalPanel results_panel = new VerticalPanel();
		RootPanel.get("results_panel").add(results_panel);
		results_panel.addStyleName("results_panel");

		Label rlabel = new Label(messages.rlabel());
		results_panel.add(rlabel);
		
		err_label = new Label(messages.err_label());
		err_label.addStyleName("err_label");
		results_panel.add(err_label);
		
		results = new HTML(messages.htmlNewHtml_html(), true);
		results_panel.add(results);

		testButton = new Button(messages.testButton());
		RootPanel.get("test_button").add(testButton);
		testButton.addStyleName("test_button");

		SearchButtonHandler handler = new SearchButtonHandler();
		testButton.addClickHandler(handler);
		testButton.addKeyUpHandler(handler);

	}

	public class SearchButtonHandler implements KeyUpHandler, ClickHandler {

		public void onClick(ClickEvent event) {
			// Request request = options_panel.getUserInput();
			testButton.setEnabled(false);
			results.setHTML("");
			err_label.setText("");
			sendRequestToServer(formTestRequest());
		}

		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// Request request = options_panel.getUserInput();
				testButton.setEnabled(false);
				results.setHTML("");
				err_label.setText("");
				sendRequestToServer(formTestRequest());
			}
		}

		private RequestImpl formTestRequest() {
			HotelRequestImpl testReq = new HotelRequestImpl("2014-07-10",
					"2014-07-15", 2, 4, Country.Turkey);
			testReq.setMinStars(3);
			testReq.setMaxStars(4);
			testReq.setPeopleCount(2);
			// TODO: ограничить setter'ы, например, нельзя отрицательные числа
			testReq.setMinPrice(10000);
			testReq.setMaxPrice(60000);

			// client make request
			ClientSettingsImpl clientSettings = new ClientSettingsImpl();
//			clientSettings.addSite(Site.teztour);
//			clientSettings.addSite(Site.itour);
			clientSettings.addSite(Site.nevatravel);
			return new RequestImpl(testReq, clientSettings);
		}

		private void sendRequestToServer(RequestImpl request) {

			requestService.requestServer(request,
					new AsyncCallback<ServerResponseImpl>() {
						@Override
						public void onSuccess(ServerResponseImpl arg0) {
							String result = "";
							if (arg0.getHotelInfo().isEmpty())
								result = result.concat("<b>No results found</b>");
							else
								for (HotelInfo elem : arg0.getHotelInfo()) {
									result = result.concat("<b>Price: </b> = "
											+ elem.getPrice() + "<br>"
											+ "<b>Stars: </b> = " + elem.getStars()
											+ "<br>" + "<b>Site: </b> "
											+ "<a href=\"" + elem.getURL() + "\">" + elem.getURL() + "</a>"
											+ "<br>" + elem.getName()
											+ "<br>" + "<b>Start date: </b>" + elem.getStartData() 
											+ "<br><br>");
								}
							results.setHTML(result);
							testButton.setEnabled(true);
						}

						@Override
						public void onFailure(Throwable arg0) {
							String result = "\n error: \n" + arg0.getClass() 
									+ "\n" + arg0.getMessage()
									+ "\n" + arg0.getCause();
							err_label.setText(SERVER_ERROR + result);
							testButton.setEnabled(true);
						}
					});
		}

	}
}
