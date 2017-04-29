package assignment7;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Conversation {
	StringProperty conversationName;
	Message message;
	public Conversation(String s, Message m){
		message = m;
		conversationName = new SimpleStringProperty(s);
	}
	
	public Message getMessage(){
		return message;
	}
	
	public StringProperty getConversationName(){
		return conversationName;
	}
	
	public void setConversationName(String s){
		conversationName.set(s);
	}
}
