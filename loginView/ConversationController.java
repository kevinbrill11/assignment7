package assignment7.loginView;

import java.util.ArrayList;
import assignment7.ChatClient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConversationController {
	@FXML
	private ListView<Item> listView = new ListView<Item>();
	@FXML
	private TextField coversationName;
	@FXML
	private Button okButton;
	
	private ObservableList<Item> recipientList = FXCollections.observableArrayList(); 
	private ChatClient client;
	private Stage stage;
	
	@FXML
	public void newConversationPress(){
		//TODO: make a new conversation with the selected recipients
		//Message message =  new Message() with set of recipients
		//Conversation newConversation = new Conversation (okButton.getText(), message);
		//add conversation to table of conversations for each individual client
		stage.hide();
	}
	
	public void setStage(Stage fourthStage){
		stage = fourthStage;
	}
	
	@SuppressWarnings("unchecked")
	public void initializeList(){
		System.out.println("Initializing listview");
		listView.setItems(recipientList);
		listView.getSelectionModel().selectedItemProperty().addListener(
		      new ChangeListener<Item>() {

				@Override
				public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
					// TODO Auto-generated method stub
					
				}
		    	  
		   });
	}
	public void setClient(ChatClient c){
    	client = c;
    }
	
	public void setList(ArrayList<String> usernames){
		//recipientList = FXCollections.observableArrayList(usernames);
		for (int i=0; i<usernames.size(); i++) {
	            Item item = new Item(usernames.get(i), false);

	            item.onProperty().addListener((obs, wasOn, isNowOn) -> {
	                if(!wasOn && isNowOn){
	                	//TODO
	                	//person selected
	                }
	                
	                if(wasOn && !isNowOn){
	                	//TODO
	                	//person deselected
	                }
	            });
	            
	            listView.getItems().add(item);
		}
		
		listView.setCellFactory(CheckBoxListCell.forListView(new Callback<Item, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Item item) {
                return item.onProperty();
            }
        }));
	}
	
	public static class Item {
        private final StringProperty name = new SimpleStringProperty();
        private final BooleanProperty on = new SimpleBooleanProperty();

        public Item(String name, boolean on) {
            setName(name);
            setOn(on);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        public final BooleanProperty onProperty() {
            return this.on;
        }

        public final boolean isOn() {
            return this.onProperty().get();
        }

        public final void setOn(final boolean on) {
            this.onProperty().set(on);
        }

        @Override
        public String toString() {
            return getName();
        }

    }

}
