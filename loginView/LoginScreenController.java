package assignment7.loginView;

/* CHAT ROOM LoginScreenController.java
 * EE422C Project 7 submission by
 * Replace <...> with your actual data.
 * Kevin Brill	
 * kjb2786
 * 16230
 * Grayson Barrett
 * gmb974
 * 16230
 * Github URL: https://github.com/kevinbrill11/assignment7
 * Slip days used: 1
 * Spring 2017
 */

import assignment7.ChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController {
	@FXML
    private Button enterButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label errorMessage;
	@FXML
	private TextField ipField;
	@FXML
	private Button ipButton;
	private ChatClient client;
	private Stage stage;
	private Stage secondStage;
	
	public void setStage(Stage primaryStage){
		stage = primaryStage;
	}
	
	public void setSecondStage(Stage secondStage){
		this.secondStage = secondStage;
	}
		
	/**
	 * Called when the user clicks on the button.
	 */
	@FXML
	private void handleNewUser() {
	   client.registerNewUser(username.getText(), password.getText());
	   try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   errorMessage.setText(client.getMessage());
	}
	
	@FXML
	private void handleIPEntry(){
		client.enterIP(ipField.getText());
		enterButton.setDisable(false);
		registerButton.setDisable(false);
		username.setDisable(false);
		password.setDisable(false);
	}
	
	@FXML
	private void handleUserLogin() {
	   client.logIn(username.getText(), password.getText());
	   try {
		Thread.sleep(1000); //TODO: does this work when Internet is involved????
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   if(client.isLoggedIn()){
		   stage.hide();
		   secondStage.show();
	   }
	   else
		   errorMessage.setText(client.getMessage());
	}
	
	public void setClient(ChatClient c){
		client = c;
	}
}
