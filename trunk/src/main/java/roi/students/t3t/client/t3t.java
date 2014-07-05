package roi.students.t3t.client;

import java.time.LocalDate;

import roi.students.t3t.shared.Country;
import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.ClientSettings;
import roi.students.t3t.shared.dao.Request;
import roi.students.t3t.shared.dao.impl.ClientSettingsImpl;
import roi.students.t3t.shared.dao.impl.HotelRequestImpl;
import roi.students.t3t.shared.dao.impl.RequestImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
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

  private final ServerRequestAsync requestService = GWT.create(ServerRequestAsync.class);

  private final Messages messages = GWT.create(Messages.class);

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
	  
	  final Button testButton = new Button( messages.testButton() );
	  RootPanel.get("test_button").add(testButton);
	  testButton.addStyleName("test_button");
	  
	  
	  
  }
  
  public class SearchButtonHandler implements KeyUpHandler, ClickHandler {

	public void onClick(ClickEvent event) {
//		Request request = options_panel.getUserInput();
//		sendRequest(request);
		HotelRequestImpl testReq = new HotelRequestImpl("2014-07-11", "2014-07-19", 2, 4,Country.Bulgaria);
		testReq.setMinStars(3);
		testReq.setMaxStars(4);
		testReq.setPeopleCount(2);
		//TODO: ограничить setter'ы, например, нельзя отрицательные числа
		testReq.setMinPrice(10000);
		testReq.setMaxPrice(60000);
		
		//client make request
		ClientSettings clientSettings = new ClientSettingsImpl();
		clientSettings.addSite(Site.teztour);
		clientSettings.addSite(Site.itour);
		Request request = new RequestImpl(testReq, clientSettings);
	}

	public void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//			Request request = options_panel.getUserInput();
//			sendRequest(request);
		}
	}
	  
  }
//    final Button sendButton = new Button( messages.sendButton() );
//    final TextBox nameField = new TextBox();
//    nameField.setText( messages.nameField() );
//    final Label errorLabel = new Label();
//
//    // We can add style names to widgets
//    sendButton.addStyleName("sendButton");
//
//    // Add the nameField and sendButton to the RootPanel
//    // Use RootPanel.get() to get the entire body element
//    RootPanel.get("nameFieldContainer").add(nameField);
//    RootPanel.get("sendButtonContainer").add(sendButton);
//    RootPanel.get("errorLabelContainer").add(errorLabel);
//
//    // Focus the cursor on the name field when the app loads
//    nameField.setFocus(true);
//    nameField.selectAll();
//
//    // Create the popup dialog box
//    final DialogBox dialogBox = new DialogBox();
//    dialogBox.setText("Remote Procedure Call");
//    dialogBox.setAnimationEnabled(true);
//    final Button closeButton = new Button("Close");
//    // We can set the id of a widget by accessing its Element
//    closeButton.getElement().setId("closeButton");
//    final Label textToServerLabel = new Label();
//    final HTML serverResponseLabel = new HTML();
//    VerticalPanel dialogVPanel = new VerticalPanel();
//    dialogVPanel.addStyleName("dialogVPanel");
//    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//    dialogVPanel.add(textToServerLabel);
//    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//    dialogVPanel.add(serverResponseLabel);
//    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//    dialogVPanel.add(closeButton);
//    dialogBox.setWidget(dialogVPanel);
//
//    // Add a handler to close the DialogBox
//    closeButton.addClickHandler(new ClickHandler() {
//      public void onClick(ClickEvent event) {
//        dialogBox.hide();
//        sendButton.setEnabled(true);
//        sendButton.setFocus(true);
//      }
//    });
//
//    // Create a handler for the sendButton and nameField
//    class MyHandler implements ClickHandler, KeyUpHandler {
//      /**
//       * Fired when the user clicks on the sendButton.
//       */
//      public void onClick(ClickEvent event) {
//        sendNameToServer();
//      }
//
//      /**
//       * Fired when the user types in the nameField.
//       */
//      public void onKeyUp(KeyUpEvent event) {
//        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//          sendNameToServer();
//        }
//      }
//
//      /**
//       * Send the name from the nameField to the server and wait for a response.
//       */
//      private void sendNameToServer() {
//        // First, we validate the input.
//        errorLabel.setText("");
//        String textToServer = nameField.getText();
//        if (!FieldVerifier.isValidName(textToServer)) {
//          errorLabel.setText("Please enter at least four characters");
//          return;
//        }
//
//        // Then, we send the input to the server.
//        sendButton.setEnabled(false);
//        textToServerLabel.setText(textToServer);
//        serverResponseLabel.setText("");
//        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
//          public void onFailure(Throwable caught) {
//            // Show the RPC error message to the user
//            dialogBox.setText("Remote Procedure Call - Failure");
//            serverResponseLabel.addStyleName("serverResponseLabelError");
//            serverResponseLabel.setHTML(SERVER_ERROR);
//            dialogBox.center();
//            closeButton.setFocus(true);
//          }
//
//          public void onSuccess(String result) {
//            dialogBox.setText("Remote Procedure Call");
//            serverResponseLabel.removeStyleName("serverResponseLabelError");
//            serverResponseLabel.setHTML(result);
//            dialogBox.center();
//            closeButton.setFocus(true);
//          }
//        });
//      }
//    }
//
//    // Add a handler to send the name to the server
//    MyHandler handler = new MyHandler();
//    sendButton.addClickHandler(handler);
//    nameField.addKeyUpHandler(handler);
//  }
}
