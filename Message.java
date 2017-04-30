package assignment7;

/* CHAT ROOM Message.java
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
	String recipients;
	
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
	
	public void setRecipients(String r){
		recipients = r;
	}
	
	public String getRecipients(){
		return recipients;
	}
}
