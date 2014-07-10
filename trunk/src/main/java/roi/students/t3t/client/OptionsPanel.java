package roi.students.t3t.client;

import java.util.Date;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.impl.ClientSettingsImpl;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;
import roi.students.t3t.shared.dao.impl.RequestImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.event.dom.client.KeyPressEvent;

public class OptionsPanel extends Composite {

	private static OptionsPanelUiBinder uiBinder = GWT
			.create(OptionsPanelUiBinder.class);

	// labels
	@UiField
	Label label_date;
	@UiField
	Label label_from;
	@UiField
	Label label_to;
	@UiField
	Label label_duration;
	@UiField
	Label label_min_duration;
	@UiField
	Label label_max_duration;
	@UiField
	Label label_country;
	@UiField
	Label label_hotel;
	@UiField
	Label label_price;
	@UiField
	Label label_min_price;
	@UiField
	Label label_max_price;
	@UiField
	Label label_food;
	@UiField
	Label label_people_count;
	@UiField
	Label label_sites;
	@UiField
	Label Label_res;

	// date boxes
	@UiField
	DateBox dateBox_from;
	@UiField
	DateBox dateBox_to;

	// list boxes
	@UiField
	ListBox listBox_country;
	@UiField
	ListBox listBox_stars;
	@UiField
	ListBox listBox_food;
	@UiField
	ListBox listBox_propleCount;

	// text boxes
	@UiField
	TextBox textBox_durationFrom;
	@UiField
	TextBox textBox_durationTo;
	@UiField
	TextBox textBox_priceFrom;
	@UiField
	TextBox textBox_priceTo;

	// check boxes
	@UiField
	VerticalPanel checkbox_site;
	@UiField
	CheckBox checkBox_nevaTravel;
	@UiField
	CheckBox checkBox_iTour;
	@UiField
	CheckBox checkBox_site3;

	// buttons
	@UiField
	Button button_search;

	interface OptionsPanelUiBinder extends UiBinder<Widget, OptionsPanel> {
	}

	private HTML err_label;

	public void addErrLabel(HTML errLabel) {
		err_label = errLabel;
	}

	public OptionsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		attachElementsToHtml();
		removeDefaultStyles();
		addNewStylesToElements();
	}

	public Button getSearchButton() {
		return button_search;
	}

	public RequestImpl getUserInfo() {
		Date startDate = dateBox_from.getValue();
		Date finishDate = dateBox_to.getValue();
		if (startDate == null || finishDate == null) {
			err_label.setHTML("Введите правильную дату");

			return null;
		} else {

			int tempStars = listBox_stars.getSelectedIndex() + 2;
			Country tempCountry;
			switch (listBox_country.getSelectedIndex()) {
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

			default:
				tempCountry = Country.Turkey;
				break;
			}

			TypeFood tempFood;

			switch (listBox_food.getSelectedIndex()) {

			case 0:
				tempFood = TypeFood.NA;
				break;

			case 1:
				tempFood = TypeFood.UAI;
				break;

			case 2:
				tempFood = TypeFood.AI;
				break;

			case 3:
				tempFood = TypeFood.FB;
				break;

			case 4:
				tempFood = TypeFood.HB;
				break;

			case 5:
				tempFood = TypeFood.BB;
				break;

			case 6:
				tempFood = TypeFood.RO;
				break;

			default:
				tempFood = TypeFood.NA;
				break;
			}
// Валидация формата входных данных
			int tempMinPrice = -1;
			try { tempMinPrice = Integer.parseInt(textBox_priceFrom.getValue());
			}
			catch (NumberFormatException ex) {}

			int tempMaxPrice = -2;
			try { tempMaxPrice = Integer.parseInt(textBox_priceTo.getValue());
			}
			catch  (NumberFormatException ex) {}
			

			int tempPeopleCount = listBox_propleCount.getSelectedIndex()+1;
			
			int tempDurationFrom = -1;
			try {
			tempDurationFrom = Integer.parseInt(textBox_durationFrom.getValue());
			}
			catch (NumberFormatException ex) {}
			
			int tempDurationTo = -2;
			try{
			tempDurationTo = Integer.parseInt(textBox_durationTo.getValue());
			}
			catch (NumberFormatException ex) {}
			
			
			// HotelRequestImpl testReq = new HotelRequestImpl(tempDateFrom,
			// tempDateTo, tempStars, tempStars, tempCountry);

			HotelRequestImpl RequestFull = new HotelRequestImpl(startDate,
					finishDate, tempCountry, tempStars, tempStars, tempFood,
					tempMinPrice, tempMaxPrice, tempPeopleCount,
					tempDurationFrom, tempDurationTo);

			// Хардкод!
			// testReq.setPeopleCount(2);
			// testReq.setMinPrice(10000);
			// testReq.setMaxPrice(600000);
			// testReq.setMaxDuration(15);
			// testReq.setMinDuration(1);

			// client make request
			ClientSettingsImpl clientSettings = new ClientSettingsImpl();

			if (checkBox_nevaTravel.getValue())
				clientSettings.addSite(Site.nevatravel);
			if (checkBox_iTour.getValue())
				clientSettings.addSite(Site.itour);

			if (!(checkBox_nevaTravel.getValue() || checkBox_iTour.getValue()))
				err_label.setText("Выберите хотя бы 1 сайт");

			// clientSettings.addSite(Site.teztour);
			// clientSettings.addSite(Site.itour);

			return new RequestImpl(RequestFull, clientSettings);
		}
	}

	@UiHandler("button_search")
	void onButton_searchClick(ClickEvent event) {

		RequestImpl tmp = getUserInfo();
		Label_res.setText("Вы выбрали: " + tmp.getHotelRequest().getCountry()
				+ " (" + tmp.getHotelRequest().getMinStars() + "*) " + " с "
				+ tmp.getHotelRequest().getStartDate() + " по "
				+ tmp.getHotelRequest().getFinishDate() + ". "
				+ "Количество дней от "
				+ tmp.getHotelRequest().getMinDuration() + " до "
				+ tmp.getHotelRequest().getMaxDuration() + ". Тип питание: "
				+ tmp.getHotelRequest().getTypeFood() + " . Стоимость от "
				+ tmp.getHotelRequest().getMinPrice() + " до "
				+ tmp.getHotelRequest().getMaxPrice() + ". Количество людей: "
				+ tmp.getHotelRequest().getPeopleCount() + ".;");
	}

	@UiHandler("textBox_priceFrom")
	void onTextBox_priceFromFocus(FocusEvent event) {
		textBox_priceFrom.setValue("");
	}

	@UiHandler("textBox_priceTo")
	void onTextBox_priceToFocus(FocusEvent event) {
		textBox_priceTo.setValue("");
	}

	@UiHandler("textBox_durationFrom")
	void onTextBox_durationFromFocus(FocusEvent event) {
		textBox_durationFrom.setValue("");
	}

	@UiHandler("textBox_durationTo")
	void onTextBox_durationToFocus(FocusEvent event) {
		textBox_durationTo.setValue("");
	}

	private void attachElementsToHtml() {
		RootPanel.get("label_date").add(label_date);
		RootPanel.get("label_from").add(label_from);
		RootPanel.get("label_to").add(label_to);
		RootPanel.get("label_duration").add(label_duration);
		RootPanel.get("label_min_duration").add(label_min_duration);
		RootPanel.get("label_max_duration").add(label_max_duration);
		RootPanel.get("label_country").add(label_country);
		RootPanel.get("label_hotel").add(label_hotel);
		RootPanel.get("label_price").add(label_price);
		RootPanel.get("label_min_price").add(label_min_price);
		RootPanel.get("label_max_price").add(label_max_price);
		RootPanel.get("label_food").add(label_food);
		RootPanel.get("label_people_count").add(label_people_count);
		RootPanel.get("label_sites").add(label_sites);

		RootPanel.get("datebox_from").add(dateBox_from);
		RootPanel.get("datebox_to").add(dateBox_to);

		RootPanel.get("list_country").add(listBox_country);
		RootPanel.get("list_stars").add(listBox_stars);
		RootPanel.get("list_food").add(listBox_food);
		RootPanel.get("list_people_count").add(listBox_propleCount);

		RootPanel.get("textbox_min_duration").add(textBox_durationFrom);
		RootPanel.get("textbox_max_duration").add(textBox_durationTo);
		RootPanel.get("textbox_min_price").add(textBox_priceFrom);
		RootPanel.get("textbox_max_price").add(textBox_priceTo);

		RootPanel.get("checkbox_site").add(checkbox_site);

		RootPanel.get("search_button").add(button_search);
		
		// debug
		RootPanel.get("label_res").add(Label_res);
	}

	private void removeDefaultStyles() {
		// remove default styles
		dateBox_from.removeStyleName("gwt-DateBox");
		dateBox_to.removeStyleName("gwt-DateBox");

		listBox_country.removeStyleName("gwt-ListBox");
		listBox_stars.removeStyleName("gwt-ListBox");
		listBox_food.removeStyleName("gwt-ListBox");
		listBox_propleCount.removeStyleName("gwt-ListBox");

		textBox_durationFrom.removeStyleName("gwt-TextBox");
		textBox_durationTo.removeStyleName("gwt-TextBox");
		textBox_priceFrom.removeStyleName("gwt-TextBox");
		textBox_priceTo.removeStyleName("gwt-TextBox");

		checkBox_nevaTravel.removeStyleName("gwt-CheckBox");
		checkBox_iTour.removeStyleName("gwt-CheckBox");
		checkBox_site3.removeStyleName("gwt-CheckBox");

		button_search.removeStyleName("gwt-Button");
	}

	private void addNewStylesToElements() {
		// add new styles
		label_date.addStyleName("title_label");
		label_duration.addStyleName("title_label");
		label_price.addStyleName("title_label");
		label_sites.addStyleName("title_label");

		label_from.addStyleName("row_label");
		label_to.addStyleName("row_label");
		label_min_duration.addStyleName("row_label");
		label_max_duration.addStyleName("row_label");
		label_country.addStyleName("row_label");
		label_hotel.addStyleName("row_label");
		label_min_price.addStyleName("row_label");
		label_max_price.addStyleName("row_label");
		label_food.addStyleName("row_label");
		label_people_count.addStyleName("row_label");

		dateBox_from.addStyleName("form-control input-sm");
		dateBox_to.addStyleName("form-control input-sm");

		listBox_country.addStyleName("form-control input-sm");
		listBox_stars.addStyleName("form-control input-sm");
		listBox_food.addStyleName("form-control input-sm");
		listBox_propleCount.addStyleName("form-control input-sm");

		textBox_durationFrom.addStyleName("form-control input-sm");
		textBox_durationTo.addStyleName("form-control input-sm");
		textBox_priceFrom.addStyleName("form-control input-sm");
		textBox_priceTo.addStyleName("form-control input-sm");

		checkBox_nevaTravel.addStyleName("checkbox-inline");
		checkBox_iTour.addStyleName("checkbox-inline");
		checkBox_site3.addStyleName("checkbox-inline");

		button_search.addStyleName("btn btn-primary search_button");
	}

	@UiHandler("textBox_durationFrom")
	void onTextBox_durationFromKeyPress(KeyPressEvent event) {
		if(!"0123456789".contains(String.valueOf(event.getCharCode()))) {
			textBox_durationFrom.cancelKey();
		}
	}
	@UiHandler("textBox_durationTo")
	void onTextBox_durationToKeyPress(KeyPressEvent event) {
		if(!"0123456789".contains(String.valueOf(event.getCharCode()))) {
			textBox_durationTo.cancelKey();
		}
		
	}
	@UiHandler("textBox_priceFrom")
	void onTextBox_priceFromKeyPress(KeyPressEvent event) {
		if(!"0123456789".contains(String.valueOf(event.getCharCode()))) {
			textBox_priceFrom.cancelKey();
		}
	}
	
	
	@UiHandler("textBox_priceTo")
	void onTextBox_priceToKeyPress(KeyPressEvent event) {
		if(!"0123456789".contains(String.valueOf(event.getCharCode()))) {
			textBox_priceTo.cancelKey();
		}
		
	}
	
}
