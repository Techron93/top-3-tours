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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

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

//	private final Messages messages = GWT.create(Messages.class);

	private HTML results;
	private Button testButton;
	private Label err_label;
	private OptionsPanel options_panel;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		options_panel = new OptionsPanel();
		Date today= new Date();
		
		options_panel.dateBox_from.setValue(today);
		Date nextDay = new Date();

		nextDay.setTime(today.getTime() + 1 * 24 * 60 * 60 * 1000 * 3);
		options_panel.dateBox_to.setValue(nextDay);

		options_panel.dateBox_from.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));
		options_panel.dateBox_to.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));

		

//		RootPanel.get("options_panel").add(options_panel);
		options_panel.addStyleName("options_panel");

		VerticalPanel results_panel = new VerticalPanel();
		RootPanel.get("results_panel").add(results_panel);
		results_panel.addStyleName("results_panel");

//		Label rlabel = new Label(messages.rlabel());
		Label rlabel = new Label("Результат:");
		results_panel.add(rlabel);
		
		
	//	err_label = new Label(messages.err_label());
		err_label = new Label();
		options_panel.addErrLabel(err_label);
		err_label.addStyleName("err_label");
		results_panel.add(err_label);
		
		//results = new HTML(messages.htmlNewHtml_html(), true);
		results = new HTML();
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
			RequestImpl requestBetter = options_panel.getUserInfo();
			Boolean flag_valid = true;
			Date today = new Date();
			Date nextYear = new Date();
			Date yesterday = new Date();
			yesterday.setTime(today.getTime() - 1 * 24 * 60 * 60 * 1000);
			nextYear.setTime(today.getTime() + 1 * 24 * 60 * 60 * 1000 * 365); // wtf am i doing?
			
			
			// Блок длительности тура
			if(requestBetter.getHotelRequest().getMinDuration()<3) { 
				err_label.setText("Выберите правильную длительность тура (от 3 до 31)"); flag_valid = false;} 
			if(requestBetter.getHotelRequest().getMaxDuration()<0 || requestBetter.getHotelRequest().getMaxDuration()>31) { 
				err_label.setText("Выберите правильную длительность тура (от 3 до 31)"); flag_valid = false;} 
			if(requestBetter.getHotelRequest().getMinDuration()>requestBetter.getHotelRequest().getMaxDuration()) { 
				err_label.setText("Выберите правильную длительность тура!"); flag_valid = false;} 
			if(requestBetter.getHotelRequest().getStartDate().after(requestBetter.getHotelRequest().getFinishDate()))  { 
				err_label.setText("Выберите правильную дату вылета!"); flag_valid = false;}
			
			// Блок даты вылета
//			if(requestBetter.getHotelRequest().getFinishDate().after(nextYear))  { 
//				err_label.setText("Выберите правильную дату вылета! (менее года (до))"); flag_valid = false;}
			
			if(requestBetter.getHotelRequest().getStartDate().after(nextYear))  { 
				err_label.setText("Выберите правильную дату вылета! (менее года (от))"); flag_valid = false;}
			
			if(requestBetter.getHotelRequest().getStartDate().before(yesterday))  { 
				err_label.setText("Выберите правильную дату вылета (не ранее чем сегодня)"); flag_valid = false;}
			
			// Блок цены
			if(requestBetter.getHotelRequest().getMinPrice()<0) { 
				err_label.setText("Выберите правильный диапазон цен (от<0) или ошибка формата"); flag_valid = false;} 
			if(requestBetter.getHotelRequest().getMaxPrice()<0) { 
				err_label.setText("Выберите правильный диапазон цен (до<0) или ошибка формата"); flag_valid = false;} 
			if(requestBetter.getHotelRequest().getMinPrice()>requestBetter.getHotelRequest().getMaxPrice()) { 
				err_label.setText("Выберите правильный диапазон цен (от < до)"); flag_valid = false;} 
			
			if (requestBetter!=null && flag_valid==true) sendRequestToServer(requestBetter); else options_panel.getSearchButton().setEnabled(true);	
		}

		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				options_panel.getSearchButton().setEnabled(false);
				results.setHTML("");
				err_label.setText("");
				RequestImpl requestBetter = options_panel.getUserInfo();
				Boolean flag_valid = true;
				Date today = new Date();
				Date nextYear = new Date();
				Date yesterday = new Date();
				yesterday.setTime(today.getTime() - 1 * 24 * 60 * 60 * 1000);
				nextYear.setTime(today.getTime() + 1 * 24 * 60 * 60 * 1000 * 365); // wtf am i doing?
				
				
				// Блок длительности тура
				if(requestBetter.getHotelRequest().getMinDuration()<3) { 
					err_label.setText("Выберите правильную длительность тура (от 3 до 31)"); flag_valid = false;} 
				if(requestBetter.getHotelRequest().getMaxDuration()<0 || requestBetter.getHotelRequest().getMaxDuration()>31) { 
					err_label.setText("Выберите правильную длительность тура (от 3 до 31)"); flag_valid = false;} 
				if(requestBetter.getHotelRequest().getMinDuration()>requestBetter.getHotelRequest().getMaxDuration()) { 
					err_label.setText("Выберите правильную длительность тура!"); flag_valid = false;} 
				if(requestBetter.getHotelRequest().getStartDate().after(requestBetter.getHotelRequest().getFinishDate()))  { 
					err_label.setText("Выберите правильную дату вылета!"); flag_valid = false;}
				
				// Блок даты вылета
//				if(requestBetter.getHotelRequest().getFinishDate().after(nextYear))  { 
//					err_label.setText("Выберите правильную дату вылета! (менее года (до))"); flag_valid = false;}
				
				if(requestBetter.getHotelRequest().getStartDate().after(nextYear))  { 
					err_label.setText("Выберите правильную дату вылета! (менее года (от))"); flag_valid = false;}
				
				if(requestBetter.getHotelRequest().getStartDate().before(yesterday))  { 
					err_label.setText("Выберите правильную дату вылета (не ранее чем сегодня)"); flag_valid = false;}
				
				// Блок цены
				if(requestBetter.getHotelRequest().getMinPrice()<0) { 
					err_label.setText("Выберите правильный диапазон цен (от<0) или ошибка формата"); flag_valid = false;} 
				if(requestBetter.getHotelRequest().getMaxPrice()<0) { 
					err_label.setText("Выберите правильный диапазон цен (до<0) или ошибка формата"); flag_valid = false;} 
				if(requestBetter.getHotelRequest().getMinPrice()>requestBetter.getHotelRequest().getMaxPrice()) { 
					err_label.setText("Выберите правильный диапазон цен (от < до)"); flag_valid = false;} 
				
				if (requestBetter!=null && flag_valid==true) sendRequestToServer(requestBetter); else options_panel.getSearchButton().setEnabled(true);	
				
			}
		}

//		private RequestImpl formTestRequest() {
			
//			
//			HotelRequestImpl testReq = new HotelRequestImpl("2014-07-10",
//					"2014-07-15", 2, 4, Country.Turkey);
//			testReq.setMinStars(3);
//			testReq.setMaxStars(4);
//			testReq.setPeopleCount(2);
//			// TODO: ограничить setter'ы, например, нельзя отрицательные числа
//			testReq.setMinPrice(10000);
//			testReq.setMaxPrice(60000);
//
//			// client make request
//			ClientSettingsImpl clientSettings = new ClientSettingsImpl();
//			clientSettings.addSite(Site.teztour);
//			clientSettings.addSite(Site.itour);
//			clientSettings.addSite(Site.nevatravel);
//			
//			return new RequestImpl(testReq, clientSettings); }
		}

		private void sendRequestToServer(RequestImpl request) {

			requestService.requestServer(request,
					new AsyncCallback<ServerResponseImpl>() {
						@Override
						public void onSuccess(ServerResponseImpl arg0) {
							String result = "";
							if (arg0.getHotelInfo().isEmpty())
								result = result.concat("<b>По вашему запросу ничего не найдено</b>");
							else
								for (HotelInfo elem : arg0.getHotelInfo()) {
									result = result.concat("<b>Цена: </b> "
											+ elem.getPrice() + "<br>"
											+ "<b>Звезды: </b> " + elem.getStars()
											+ "<br>" + "<b>Сайт: </b> "
											+ "<a href=\"" + elem.getURL() + "\">" + elem.getURL() + "</a>"
											+ "<br>" + elem.getName()
											+ "<br>" + "<b>Дата вылета: </b>" + elem.getStartData() 
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

