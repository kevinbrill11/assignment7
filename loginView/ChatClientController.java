package assignment7.loginView;

import assignment7.ChatClient;
import assignment7.Conversation;
import assignment7.Message;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ChatClientController {

    @FXML
    private TableView<Conversation> table;
    @FXML
    private TableColumn<Conversation, String> column;
    @FXML
    private TextArea messageField;
    @FXML
    private Button clear;
    @FXML
    private Button send;
    @FXML
    private Button conversation;
    @FXML
    private Text conversationName;
    @FXML
    private TextArea display;
   
    private Stage stage;
    private Scene scene;
    private ChatClient client;
    private Stage thirdStage;
    private ConversationController controller3;
    Message currentMessage;
    
    private String chat;
    
    public void setStage(Stage secondStage){
		stage = secondStage;
	}
    
    public void setScene(Scene secondScene){
    	scene = secondScene;
    }
    
    public void setThirdStage(Stage thirdStage){
    	this.thirdStage = thirdStage;
    }
    
    public void setClient(ChatClient c){
    	client = c;
    	chat = "";
    	currentMessage = null;
    }
    
    public void displayTable(ObservableList<Conversation> conversations){
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	table.setItems(conversations); 
            }
        });
		
    }
    
    @FXML
	private void sendMessage() {
    	System.out.println("Pressed " + "\"" + messageField.getText() + "\"");
    	if(currentMessage != null){
    		currentMessage.setMessage(messageField.getText());
    		client.sendMessage(currentMessage);
    	}
    	
    }
    
    public void displayText(String s){
    	//chat += s + "\n";
    	display.setText(chat);
    }
    
    public void updateDisplay(Conversation c){
    	display.clear();
    	display.setText(c.getMessage().getMessage());
    }
    
    public void clearDisplay(){
    	//save current conversation text?
    	display.setText("");
    }
    
    public void clearMessage(){
    	messageField.setText("");
    }
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        column.setCellValueFactory(cellData -> cellData.getValue().getConversationName());
        
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateDisplay(newValue));
    }
    
    @FXML
    private void newConversation(){
    	System.out.println("TEST BUTTON");
		controller3.setStage(thirdStage);
    	client.getOnline();
    	thirdStage.show(); //TODO: We're getting errors when you close the new conversation window and then open it again
    }
    
    public void setController3(ConversationController c3){
    	controller3 = c3;
    }
    
    public void composeNewMessage(Message m){
    	//save current conversation text
    	clearDisplay();
    	clearMessage();
    	conversationName.setText(m.getUsername());
    	currentMessage = m;
    }
    
    public void receivedNewMessage(Message m){
    	conversationName.setText(m.getUsername());
    	clearDisplay();
    	clearMessage();
    	displayText(m.getMessage());
    }
    
    
}
