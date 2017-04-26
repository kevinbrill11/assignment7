package assignment7.loginView;

import assignment7.ChatClient;
import assignment7.Conversation;
import assignment7.Message;
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
    private Text display;
    @FXML
    private TextFlow displayParent;
    private Stage stage;
    private Scene scene;
    private ChatClient client;
    private Stage thirdStage;
    
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
    }
    
    @FXML
	private void sendMessage() {
    	System.out.println("Pressed " + "\"" + messageField.getText() + "\"");
    	client.sendMessage(new Message(59, messageField.getText()));
    	
    	
    }
    
    public void displayText(String s){
    	chat += s + "\n";
    	display.setText(chat);
    }
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        column.setCellValueFactory(cellData -> cellData.getValue().getConversationName());
    }
    
    @FXML
    private void newConversation(){
    	System.out.println("TEST BUTTON");
    	thirdStage.show();
    }
    
    
}
