package roi.students.t3t.client;

import java.util.Date;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.TypeFood;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.ClientSettingsImpl;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;
import roi.students.t3t.shared.dao.impl.RequestImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.event.dom.client.FocusEvent;

public class OptionsPanel extends Composite implements HasText {

	private static OptionsPanelUiBinder uiBinder = GWT
			.create(OptionsPanelUiBinder.class);
	@UiField DateBox dateBox_from;
	@UiField DateBox dateBox_to;
	@UiField ListBox listBox_country;
	@UiField ListBox listBox_stars;
	@UiField Button button_search;
	@UiField Label Label_res;
	@UiField ListBox listBox_food;
	@UiField ListBox listBox_propleCount;
	@UiField TextBox textBox_durationFrom;
	@UiField TextBox textBox_durationTo;
	@UiField TextBox textBox_priceFrom;
	@UiField TextBox textBox_priceTo;
	@UiField CheckBox checkBox_nevaTravel;
	@UiField CheckBox checkBox_iTour;
	@UiField CheckBox checkBox_site3;

	interface OptionsPanelUiBinder extends UiBinder<Widget, OptionsPanel> {
	}
	private Label err_label;
	public void addErrLabel(Label errLabel) {
		err_label = errLabel;
	
	}

	public OptionsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public OptionsPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
//		button.setText("Options");
	}

	public void setText(String text) {
//		button.setText(text);
	}

	public String getText() {
		return "";
//		return button.getText();
	}


	public Button getSearchButton(){
		return button_search;
	}
	
	
	public RequestImpl getUserInfo() {
		Date startDate = dateBox_from.getValue();
		Date finishDate =  dateBox_to.getValue();
		if (startDate==null || finishDate==null) { 
			err_label.setText("Введите правильную дату");
			
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
		
		
		int tempStars = listBox_stars.getSelectedIndex() + 1;
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

		default: tempCountry = Country.Turkey;
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

		default: tempFood = TypeFood.NA;
			break;
		}
		
		int tempMinPrice = Integer.parseInt(textBox_priceFrom.getValue());

		int tempMaxPrice = Integer.parseInt(textBox_priceTo.getValue());
		

		int tempPeopleCount = listBox_propleCount.getSelectedIndex()+1;
		
		int tempDurationFrom = Integer.parseInt(textBox_durationFrom.getValue());
		
		int tempDurationTo = Integer.parseInt(textBox_durationTo.getValue());
		
		
		HotelRequestImpl testReq = new HotelRequestImpl(tempDateFrom, tempDateTo, tempStars, tempStars, tempCountry);
		
		HotelRequestImpl RequestFull = new HotelRequestImpl(tempDateFrom, tempDateTo, tempCountry, tempStars, 
				                       tempStars, tempFood, tempMinPrice, tempMaxPrice, tempPeopleCount, tempDurationFrom, tempDurationTo);
		
		
		// Хардкод!
//		testReq.setPeopleCount(2);
//		testReq.setMinPrice(10000);
//		testReq.setMaxPrice(600000);
//		testReq.setMaxDuration(15);
//		testReq.setMinDuration(1);

		// client make request
		ClientSettingsImpl clientSettings = new ClientSettingsImpl();
		
		if(checkBox_nevaTravel.getValue()) clientSettings.addSite(Site.nevatravel);
		if(checkBox_iTour.getValue()) clientSettings.addSite(Site.itour);
		
		if (!(checkBox_nevaTravel.getValue() || checkBox_iTour.getValue())) err_label.setText("Выберите хотя бы 1 сайт");
		
		
	
		
//		clientSettings.addSite(Site.teztour);
//		clientSettings.addSite(Site.itour);
		
		return new RequestImpl(RequestFull, clientSettings); }
	}

	
	@UiHandler("button_search")
	void onButton_searchClick(ClickEvent event) {
		
		RequestImpl tmp = getUserInfo();
		Label_res.setText("Вы выбрали: " + tmp.getHotelRequest().getCountry() + " ("+ tmp.getHotelRequest().getMinStars() +"*) " + " с " 
		+ tmp.getHotelRequest().getStartDate() + " по " + tmp.getHotelRequest().getFinishDate() + ". " + "Количество дней от " 
		+ tmp.getHotelRequest().getMinDuration() + " до " + tmp.getHotelRequest().getMaxDuration() + ". Тип питание: " + tmp.getHotelRequest().getTypeFood() 
		+ " . Стоимость от " + tmp.getHotelRequest().getMinPrice() + " до " + tmp.getHotelRequest().getMaxPrice() + ". Количество людей: " +
	    tmp.getHotelRequest().getPeopleCount() + ".;");
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
	
}
