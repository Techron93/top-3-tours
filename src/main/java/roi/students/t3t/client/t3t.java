package roi.students.t3t.client;

import java.util.Date;

import roi.students.t3t.shared.dao.HotelInfo;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
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

	// private final Messages messages = GWT.create(Messages.class);

	private HTML results;
	private HTML err_label;
	private OptionsPanel options_panel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		options_panel = new OptionsPanel();
		Date today = new Date();
		options_panel.dateBox_from.setValue(today);
		Date threeDaysLater = new Date(today.getTime());
		CalendarUtil.addDaysToDate(threeDaysLater, 3);
		options_panel.dateBox_to.setValue(threeDaysLater);
		options_panel.dateBox_from.setFormat(new DateBox.DefaultFormat(
				DateTimeFormat.getFormat("dd.MM.yyyy")));
		options_panel.dateBox_to.setFormat(new DateBox.DefaultFormat(
				DateTimeFormat.getFormat("dd.MM.yyyy")));
		options_panel.addStyleName("options_panel");

		err_label = new HTML();
		options_panel.addErrLabel(err_label);
		RootPanel.get("error_label").add(err_label);
		err_label.addStyleName("error_label");

		results = new HTML();
		RootPanel.get("results_panel").add(results);
		results.addStyleName("row");

		SearchButtonHandler handler = new SearchButtonHandler();
		options_panel.button_search.addClickHandler(handler);
		options_panel.button_search.addKeyUpHandler(handler);
	}

	/**
	 * Search button click and enter key handler
	 * @author nick_yakuba
	 */
	public class SearchButtonHandler implements KeyUpHandler, ClickHandler {

		public void onClick(ClickEvent event) {
			// Request request = options_panel.getUserInput();
			options_panel.button_search.setEnabled(false);
			options_panel.progress_bar.setVisible(true);
			results.setHTML("");
			err_label.setHTML("");
			RequestImpl requestBetter = options_panel.getUserInfo();
			validateAndSendRequest(requestBetter);
		}

		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				options_panel.button_search.setEnabled(false);
				options_panel.progress_bar.setVisible(true);
				results.setHTML("");
				err_label.setHTML("");
				RequestImpl requestBetter = options_panel.getUserInfo();
				validateAndSendRequest(requestBetter);
			}
		}
	}

	/**
	 * Input validation and sending request.
	 * @param requestBetter formed request to server.
	 */
	private void validateAndSendRequest(RequestImpl requestBetter) {
		Boolean flag_valid = true;
		Date today = new Date();
		Date nextYear = new Date(today.getTime());
		Date yesterday = new Date(today.getTime());

		CalendarUtil.addDaysToDate(yesterday, -1);
		CalendarUtil.addDaysToDate(nextYear, 365);

		// Блок длительности тура
		if (requestBetter.getHotelRequest().getMinDuration() < 3) {
			err_label
					.setHTML("Выберите правильную длительность тура (от 3 до 31)");
			flag_valid = false;
		}
		if (requestBetter.getHotelRequest().getMaxDuration() < 0
				|| requestBetter.getHotelRequest().getMaxDuration() > 31) {
			err_label
					.setHTML("Выберите правильную длительность тура (от 3 до 31)");
			flag_valid = false;
		}
		if (requestBetter.getHotelRequest().getMinDuration() > requestBetter
				.getHotelRequest().getMaxDuration()) {
			err_label.setHTML("Выберите правильную длительность тура (от 3 до 31)");
			flag_valid = false;
		}
		if (requestBetter.getHotelRequest().getStartDate()
				.after(requestBetter.getHotelRequest().getFinishDate())) {
			err_label.setHTML("Выберите правильную дату вылета!");
			flag_valid = false;
		}

		// Блок даты вылета
		// if(requestBetter.getHotelRequest().getFinishDate().after(nextYear))
		// {
		// err_label.setText("Выберите правильную дату вылета! (менее года (до))");
		// flag_valid = false;}

		if (requestBetter.getHotelRequest().getStartDate().after(nextYear)) {
			err_label
					.setHTML("Выберите правильную дату вылета!");
			flag_valid = false;
		}

		if (requestBetter.getHotelRequest().getStartDate()
				.before(yesterday)) {
			err_label
					.setHTML("Выберите правильную дату вылета (не ранее чем сегодня)");
			flag_valid = false;
		}

		// Блок цены
		if (requestBetter.getHotelRequest().getMinPrice() < 0) {
			err_label
					.setHTML("Выберите правильный диапазон цен");
			flag_valid = false;
		}
		if (requestBetter.getHotelRequest().getMaxPrice() < 0) {
			err_label
					.setHTML("Выберите правильный диапазон цен");
			flag_valid = false;
		}
		if (requestBetter.getHotelRequest().getMinPrice() > requestBetter
				.getHotelRequest().getMaxPrice()) {
			err_label.setHTML("Выберите правильный диапазон цен");
			flag_valid = false;
		}

		if (requestBetter.getHotelRequest().getMinPrice() == 0
				&& requestBetter.getHotelRequest().getMaxPrice() == 0) {
			err_label.setText("Халявы.Нет!");
			flag_valid = false;
		}

		if (requestBetter != null && flag_valid == true)
			sendRequestToServer(requestBetter);
		else {
			options_panel.button_search.setEnabled(true);
			options_panel.progress_bar.setVisible(false);
		}
	}
	
	/**
	 * Send request to server. 
	 * 1. Define callback.
	 * 2. Process result onSuccess() or onFailure()
	 * @param request
	 */
	private void sendRequestToServer(RequestImpl request) {

		requestService.requestServer(request,
				new AsyncCallback<ServerResponseImpl>() {
					@Override
					public void onSuccess(ServerResponseImpl arg0) {
						String result = "";
						if (arg0.getHotelInfo().isEmpty())
							result = result
									.concat("<b>По вашему запросу ничего не найдено</b>");
						else
							for (HotelInfo elem : arg0.getHotelInfo()) {
								result = result
										+ "<div class=\"col-md-4\">"
										+ "<div class=\"search_item\">"
										+ "<b>Цена:</b> "
										+ elem.getPrice()
										+ "<br><b>Звезды:</b> "
										+ elem.getStars()
										+ "<br><b>Название отеля:</b><br>"
										+ elem.getName()
										+ "<br><b>Дата вылета:</b><br>"
										+ elem.getStartData()
										+ "<a target=\"_blank\" href=\""
										+ elem.getURL()
										+ "\" class=\"btn btn-success btn-sm go_button\" role=\"button\">Перейти</a>"
										+ "</div>"
										+ "</div>";
							}
						results.setHTML(result);
						options_panel.button_search.setEnabled(true);
						options_panel.progress_bar.setVisible(false);
					}

					@Override
					public void onFailure(Throwable arg0) {
						String result = "<br>" + arg0.getMessage();

						err_label.setHTML(SERVER_ERROR + result);
						options_panel.button_search.setEnabled(true);
						options_panel.progress_bar.setVisible(false);
					}
				});
	}
}
