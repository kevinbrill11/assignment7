package assignment7.loginView;

import assignment7.ChatClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ConversationController {
	@FXML
	private ListView listView = new ListView();
	private ObservableList recipientList=FXCollections.observableArrayList(); 
	private ChatClient client;
	
	@SuppressWarnings("unchecked")
	public void initializeList(){
		listView.getSelectionModel().selectedItemProperty().addListener(
		      new ChangeListener() {

				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					// TODO Auto-generated method stub
					
				}
		    	  
		   });
	}
	public void setClient(ChatClient c){
    	client = c;
    }

}
