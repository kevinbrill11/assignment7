package assignment7;

import javafx.beans.property.StringProperty;

public class Conversation {
	StringProperty conversationName;
	Message message;
	public Conversation(String s, Message m){
		message = m;
		conversationName.set(s);
	}
	
	public Message getMessage(String s){
		return message;
	}
	
	public StringProperty getConversationName(){
		return conversationName;
	}
}
