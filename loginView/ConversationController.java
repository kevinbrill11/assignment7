package assignment7.loginView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import assignment7.ChatClient;
import assignment7.Conversation;
import assignment7.Message;
import javafx.application.Platform;
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
	private TextField conversationName;
	@FXML
	private Button okButton;
	
	private ObservableList<Item> recipientList = FXCollections.observableArrayList(); 
	private ChatClient client;
	private Stage stage;
	private HashMap<String, Item> online;
	private HashSet<String> recipients;
	
	@FXML
	public void newConversationPress(){
		//TODO: make a new conversation with the selected recipients
		Message msg = new Message(19, null);
		msg.setRecipients(recipients);
//		System.out.print("set recipients: ");
//		for(String n: recipients)                 works
//			System.out.print(n.toUpperCase());
		msg.setUsername(conversationName.getText());
		stage.hide();
		client.newConversation(msg);
		
	}
	
	public void setStage(Stage fourthStage){
		stage = fourthStage;
	}
	
	@SuppressWarnings("unchecked")
	public void initializeList(){
		System.out.println("Initializing listview");
		listView.setItems(recipientList);
//		listView.getSelectionModel().selectedItemProperty().addListener(
//		      new ChangeListener<Item>() {
//
//				@Override
//				public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
//					System.out.println(oldValue.getName());
//					
//				}
//		    	  
//		   });
		online = new HashMap<String, Item>();
		recipients = new HashSet<String>();
	}
	public void setClient(ChatClient c){
    	client = c;
    }
	
	public void setList(ArrayList<String> usernames){
		//recipientList = FXCollections.observableArrayList(usernames);
		ArrayList<Integer> removals = new ArrayList<Integer>();
		for(int i=0; i<listView.getItems().size(); i++){
			if(!usernames.contains(listView.getItems().get(i).toString())){
				System.out.println(listView.getItems().get(i).toString() + "is no longer online");
				removals.add(i);
			}
		}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	while(removals.size() > 0){
		    		listView.getItems().remove(((int)removals.get(0)));
		    		removals.remove(((int) 0));
		    	}
		    }
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("don't sleep");
		}
		for (int i=0; i<usernames.size(); i++) {
			if(!listView.getItems().contains(online.get(usernames.get(i)))){
				System.out.println("NAME TO ADD: " + usernames.get(i));
//				if(listView.getItems().size()>0)
//					System.out.println("name contained by list: " + listView.getItems().get(i));
			
	            Item item = new Item(usernames.get(i), false);

	            item.onProperty().addListener((obs, wasOn, isNowOn) -> {
	                if(!wasOn && isNowOn){
		                	//person selected
	                	recipients.add(item.toString());
	                }
		                
	                if(wasOn && !isNowOn){
		                	//person deselected
		                recipients.remove(item.toString());
	                }
	            });
	            Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                	listView.getItems().add(item); //does not like to add new users
	                }
	            });
	            online.put(usernames.get(i), item);
			}
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
