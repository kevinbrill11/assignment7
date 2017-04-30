package assignment7;
/* CHAT ROOM Conversation.java
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
	@Override
	public String toString(){
		return conversationName.get();
	}
}
