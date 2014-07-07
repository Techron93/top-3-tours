package roi.students.t3t.client;

import java.util.Date;

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
	private OptionsPanel options_panel;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		options_panel = new OptionsPanel();

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

//		testButton = new Button(messages.testButton());
//		RootPanel.get("test_button").add(testButton);
//		testButton.addStyleName("test_button");

		SearchButtonHandler handler = new SearchButtonHandler();
		options_panel.getSearchButton().addClickHandler(handler);
		options_panel.getSearchButton().addKeyUpHandler(handler);
//		testButton.addClickHandler(handler);
//		testButton.addKeyUpHandler(handler);

	}

	public class SearchButtonHandler implements KeyUpHandler, ClickHandler {

		public void onClick(ClickEvent event) {
			// Request request = options_panel.getUserInput();
			options_panel.getSearchButton().setEnabled(false);
			results.setHTML("");
			err_label.setText("");
			RequestImpl request = formTestRequest();

			if (request!=null) sendRequestToServer(request); else options_panel.getSearchButton().setEnabled(true);
			
		}

		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// Request request = options_panel.getUserInput();
				options_panel.getSearchButton().setEnabled(false);
				results.setHTML("");
				err_label.setText("");
				RequestImpl request = formTestRequest();
				if (request!=null) sendRequestToServer(request); else options_panel.getSearchButton().setEnabled(true);
			}
		}

		private RequestImpl formTestRequest() {
			Date startDate = options_panel.dateBox_from.getValue();
			Date finishDate = options_panel.dateBox_to.getValue();
			if (startDate==null || finishDate==null) { 
				err_label.setText("Empty date");
				
				return null;
			} else { 
			
			// Setting DateFrom in fromat yyyy-mm-dd (very bad method)
			int temp_month_num = startDate.getMonth()+1;
			String temp_month_str;
			if (temp_month_num>9) temp_month_str = Integer.toString(temp_month_num);
			else temp_month_str = "0" + Integer.toString(temp_month_num);
			
			int temp_day_num = startDate.getDate();
			String temp_day_str;
			if (temp_day_num>9) temp_day_str = Integer.toString(temp_day_num);
			else temp_day_str = "0" + Integer.toString(temp_day_num);
			
			String tempDateFrom = (startDate.getYear() + 1900 )+ "-" + temp_month_str + "-" + temp_day_str;
			
		
			// Setting DateTo in fromat yyyy-mm-dd 
			 temp_month_num = finishDate.getMonth()+1;
			 
			if (temp_month_num>9) temp_month_str = Integer.toString(temp_month_num);
			else temp_month_str = "0" + Integer.toString(temp_month_num);
			
			 temp_day_num = finishDate.getDate();
			
			if (temp_day_num>9) temp_day_str = Integer.toString(temp_day_num);
			else temp_day_str = "0" + Integer.toString(temp_day_num);
			
			String tempDateTo = (finishDate.getYear() + 1900 )+ "-" + temp_month_str + "-" + temp_day_str;
			
			
			int tempStars = options_panel.listBox_stars.getSelectedIndex() + 1;
			Country tempCountry;
			switch (options_panel.listBox_country.getSelectedIndex()) {
			case 0:
				tempCountry = Country.Turkey;
				break;
				
			case 1:
				tempCountry = Country.Egypt;
				break;
				
			case 2:
				tempCountry = Country.Cyprus;
				break;
				
			case 3:
				tempCountry = Country.Maldives;
				break;
				
			case 4:
				tempCountry = Country.Bulgaria;
				break;

			default: tempCountry = Country.Turkey;
				break;
			}
			
			HotelRequestImpl testReq = new HotelRequestImpl(tempDateFrom, tempDateTo, tempStars, tempStars, tempCountry);
			// Хардкор!
			testReq.setPeopleCount(2);
			testReq.setMinPrice(10000);
			testReq.setMaxPrice(600000);
			testReq.setMaxDuration(15);
			testReq.setMinDuration(1);
			
//			HotelRequestImpl testReq = new HotelRequestImpl("2014-07-10",
//					"2014-07-15", 2, 4, Country.Turkey);
//			testReq.setMinStars(3);
//			testReq.setMaxStars(4);
//			testReq.setPeopleCount(2);
//			// TODO: ограничить setter'ы, например, нельзя отрицательные числа
//			testReq.setMinPrice(10000);
//			testReq.setMaxPrice(60000);

			// client make request
			ClientSettingsImpl clientSettings = new ClientSettingsImpl();
//			clientSettings.addSite(Site.teztour);
//			clientSettings.addSite(Site.itour);
			clientSettings.addSite(Site.nevatravel);
			
			return new RequestImpl(testReq, clientSettings); }
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
							options_panel.getSearchButton().setEnabled(true);
						}

						@Override
						public void onFailure(Throwable arg0) {
							String result = "\n error: \n" + arg0.getClass() 
									+ "\n" + arg0.getMessage()
									+ "\n" + arg0.getCause();
							err_label.setText(SERVER_ERROR + result);
							options_panel.getSearchButton().setEnabled(true);
						}
					});
		}

	}
}
