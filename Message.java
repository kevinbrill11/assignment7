package assignment7;

import java.util.HashSet;

public class Message implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //lol idk, need this to send objects
	private int code;
	private String message;
	private String username;
	private String password;
	private boolean success;
	HashSet<String> recipients;
	
	public Message(int c, String m){
		code = c;
		message = m;
		username = null;
		password = null;
	}
	
	public Message(int c, String u, String p){
		code = c;
		username = u;
		password = p;
		message = null;
	}
	public Message(int c, String u, HashSet<String> recipients){ //TODO: check over this constructor, it's for the group chat functionality
		code = c;
		username = u;
		this.recipients = recipients;
	}
	
	public int getCode(){
		return code;
	}
	
	public void setCode(int c){
		code = c;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String u){
		username = u;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setSuccess(boolean s){
		success = s;
	}
	
	public boolean success(){
		return success;
	}
	
	public void setRecipients(HashSet<String> r){
		recipients = r;
	}
	
	public HashSet<String> getRecipients(){
		return recipients;
	}
}
