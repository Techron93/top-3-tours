package roi.students.t3t.client;

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

public class OptionsPanel extends Composite implements HasText {

	private static OptionsPanelUiBinder uiBinder = GWT
			.create(OptionsPanelUiBinder.class);

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

}
