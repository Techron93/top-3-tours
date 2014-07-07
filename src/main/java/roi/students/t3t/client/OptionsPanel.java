package roi.students.t3t.client;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.dao.HotelRequest;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;

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

public class OptionsPanel extends Composite implements HasText {

	private static OptionsPanelUiBinder uiBinder = GWT
			.create(OptionsPanelUiBinder.class);
	@UiField DateBox dateBox_from;
	@UiField DateBox dateBox_to;
	@UiField ListBox listBox_country;
	@UiField ListBox listBox_stars;
	@UiField Button button_search;
	@UiField Label Label_res;

	interface OptionsPanelUiBinder extends UiBinder<Widget, OptionsPanel> {
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
	
	public HotelRequest getUserInfo() {
		// Setting DateFrom in fromat yyyy-mm-dd (very bad method)
		int temp_month_num = dateBox_from.getValue().getMonth()+1;
		String temp_month_str;
		if (temp_month_num>9) temp_month_str = Integer.toString(temp_month_num);
		else temp_month_str = "0" + Integer.toString(temp_month_num);
		
		int temp_day_num = dateBox_from.getValue().getDate();
		String temp_day_str;
		if (temp_day_num>9) temp_day_str = Integer.toString(temp_day_num);
		else temp_day_str = "0" + Integer.toString(temp_day_num);
		
		String tempDateFrom = (dateBox_from.getValue().getYear() + 1900 )+ "-" + temp_month_str + "-" + temp_day_str;
	
		// Setting DateTo in fromat yyyy-mm-dd 
		 temp_month_num = dateBox_to.getValue().getMonth()+1;
		 
		if (temp_month_num>9) temp_month_str = Integer.toString(temp_month_num);
		else temp_month_str = "0" + Integer.toString(temp_month_num);
		
		 temp_day_num = dateBox_to.getValue().getDate();
		
		if (temp_day_num>9) temp_day_str = Integer.toString(temp_day_num);
		else temp_day_str = "0" + Integer.toString(temp_day_num);
		
		String tempDateTo = (dateBox_to.getValue().getYear() + 1900 )+ "-" + temp_month_str + "-" + temp_day_str;
		
		
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
		
		HotelRequest tempHR = new HotelRequestImpl(tempDateFrom, tempDateTo, tempStars, tempStars, tempCountry);
		
		
		return tempHR;
		
		
	}

	
	@UiHandler("button_search")
	void onButton_searchClick(ClickEvent event) {
		
		HotelRequest tmp = getUserInfo();
		Label_res.setText("Вы выбрали: " + tmp.getCountry() + " ("+ tmp.getMinStars() +"*) " + " с " 
																					+ tmp.getStartDate() + " по " + tmp.getFinishDate() + ". ");
	}
}
