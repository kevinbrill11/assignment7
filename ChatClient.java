package assignment7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.*;

import assignment7.loginView.ChatClientController;
import assignment7.loginView.ConversationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.awt.event.*;

public class ChatClient implements Runnable{
	private int unique;
	private String username;
	private boolean IPEntered;
	private String address;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	boolean loggedIn;
	String errorMessage;
	ChatClientController chatControl;
	ConversationController convControl;
	private ObservableList<Conversation> conversations = FXCollections.observableArrayList();

	

	public void run() {
		errorMessage = "";
		
	}
	
	public ObservableList<Conversation> getConversations(){
		return conversations;
	}
	
	public void setController(ChatClientController c){
		chatControl = c;
	}
	
	public void setComposer(ConversationController c){
		convControl = c;
	}
	
	public void registerNewUser(String username, String password){
		Message m = new Message(5, username, password);
		m.setCode(m.getCode()*unique);
		m.setSuccess(false);
		sendMessage(m);
	}
	
	public void logIn(String username, String password){
		Message m = new Message(7, username, password);
		m.setCode(m.getCode()*unique);
		m.setSuccess(false);
		sendMessage(m);
	}
	
	public void logOff(){
		System.out.println("Client starting log off process");
		sendMessage(new Message(13*unique, null));
	}
	
	public void getOnline(){
		sendMessage(new Message(11*unique, null));
	}
	
	private void assignUnique(){
		unique = 0;
		sendMessage(new Message(3, null));
	}
	
	public void enterIP(String ip){
		address = ip;
		IPEntered = true;
		try {
			setUpNetworking();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assignUnique();
	}

	private void setUpNetworking() throws Exception {
		
		@SuppressWarnings("resource")
		Socket sock = new Socket(address, 8387); //127.0.0.1 192.168.43.136
		//InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		outToServer = new ObjectOutputStream(sock.getOutputStream());
	    inFromServer = new ObjectInputStream(sock.getInputStream());
		//reader = new BufferedReader(streamReader);
		//writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}
	
	public void sendMessage(Message msg){
		try {
			System.out.println("Client about to send: " + msg.getMessage());
			/*LinkedList<Message> msgList = new LinkedList<>();
			msgList.push(new Message(code, msg));*/
			if(msg.getRecipients() != null){
				System.out.println("new convo chat recipients: " + msg.getRecipients().toUpperCase());
			}
			outToServer.writeObject(msg);
			outToServer.flush();
		} catch (IOException e) {
			System.out.println("Client writing object issue");
			try {
				setUpNetworking();
			} catch (Exception e1) {
				System.out.println("nest catch");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void newConversation(Message m){
		if(!m.getRecipients().contains(username))
			m.setRecipients(m.getRecipients() + username + " ");
//		System.out.print("new convo chat recipients: ");
//		for(String n: m.getRecipients())                    works
//			System.out.print(n.toUpperCase());
//		System.out.println();
		chatControl.composeNewMessage(m);
	}
	public boolean isLoggedIn(){
		return loggedIn;
	}
	
	public String getMessage(){
		return errorMessage;
	}
	
	public int getUnique(){
		return unique;
	}
	
	private void adjustRepeatName(Conversation conv, int n){
		boolean match = false;
		for(Conversation c: conversations){
			if(conv.toString().equals(c.toString())){
				System.out.println(c + " matched with " + conv.toString() + ", iteration: " + n);
				if(n>1)
					conv.setConversationName(conv.toString().replace(Integer.toString(n-1), Integer.toString(n)));
				else
					conv.setConversationName(conv.toString() + "(" + n + ")");
				match = true;
				break;
			}
		}
		
		if(match){
			System.out.println("n: " + n);
			n++;
			adjustRepeatName(conv, n);
		}
	}
	class IncomingReader implements Runnable {
		
		public void run() {
			boolean done = false;
			ArrayList<String> names = new ArrayList<String>();
			while(!done){
				try {
					/*LinkedList<Message> inFromServerList = new LinkedList<>();
			        Message msgFrmServer = null;
			        inFromServerList = (LinkedList<Message>)inFromServer.readObject();
			        msgFrmServer = inFromServerList.pop(); */
					Message message = (Message) inFromServer.readObject();
					System.out.println("received message <== ");
					if(message.getCode()%3 == 0 && unique == 0){
						unique = message.getCode()/3;
						System.out.println("Assigned unique: " + message.getCode()/3);
					}
					if(message.getCode()%59 == 0){
						chatControl.displayText(message.getMessage());
					}
					if(message.getCode()%5 == 0 && message.getCode()%unique == 0){//tried to register new username
						if(!message.success()){
							errorMessage = "Username already exists.";
						}
						else
							errorMessage = "Successful registration";
					}
					
					if(message.getCode()%7 == 0 && message.getCode()%unique == 0){
						if(!message.success()){
							errorMessage = message.getMessage();
						}
						else{
							if(message.getCode()%17==0){
								unique = ((message.getCode()/7)/17)/unique;
								System.out.println("redid unique: " + unique);
							}
							username = message.getUsername();
							System.out.println("set username: " + username);
							errorMessage = "Successful login";
							loggedIn = true;
						}
					}
					
					if(message.getCode()%11 == 0 && message.getCode()%unique == 0){//request username list
						String[] list = message.getUsername().split(" ");
						names.clear();
						for(int k=0; k<list.length; k++){
							names.add(list[k]);
						}
						convControl.setList(names);
						System.out.println("got usernames: " + names);
					}
					
					if(message.getCode()%13 ==0 && message.getCode()%unique==0){
						done = true;
						outToServer.close();
						inFromServer.close();
					}
					
					if(message.getCode()%19==0 && message.getRecipients().contains(username)){
						//chatControl.receivedNewMessage(message);
						Conversation c = new Conversation(message.getUsername(), message);
						adjustRepeatName(c, 1);
						c.setConversationName(c.getConversationName().get()+"(new)");
						c.getMessage().setMessage("Recipients: " + c.getMessage().getRecipients() + "\n" + c.getMessage().getMessage());
						conversations.add(c);
						chatControl.displayTable(conversations);
					}
					
					if(message.getCode()%23==0 && message.getRecipients().contains(username)){
						//chatControl.receivedNewMessage(message);
						Conversation conv = null; 
						for(Conversation c: conversations){
							if(c.toString().equals(message.getUsername())){
								conv = c;
							}
						}
						conv.getMessage().setMessage(conv.getMessage().getMessage() + "\n" + message.getMessage());
						chatControl.displayTable(conversations);
						if(!chatControl.checkUpdateDisplay(conv)){
							conv.setConversationName(conv.toString() + "(new)");
							chatControl.displayTable(conversations);
						}
					}
					
				} catch (Exception ex) {
					System.out.println("Error receiving the message");
					ex.printStackTrace();
				}
			}
		}
	}
}
