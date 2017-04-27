package assignment7;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.*;

import assignment7.loginView.ChatClientController;

import java.awt.*;
import java.awt.event.*;

public class ChatClient implements Runnable{
	private int unique;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	ClientSecurity cs;
	boolean loggedIn;
	String errorMessage;
	ChatClientController chatControl;
	

	public void run() {
		errorMessage = "";
		try {
			setUpNetworking();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assignUnique();
	}
	
	public void setController(ChatClientController c){
		chatControl = c;
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
	
	private void assignUnique(){
		unique = 0;
		sendMessage(new Message(3, null));
	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket sock = new Socket("127.0.0.1", 4243); //127.0.0.1
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
			outToServer.writeObject(msg);
			outToServer.flush();
		} catch (IOException e) {
			System.out.println("Client writing object issue");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {	
		try {
			new ChatClient().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isLoggedIn(){
		return loggedIn;
	}
	
	public String getMessage(){
		return errorMessage;
	}
	class IncomingReader implements Runnable {
		
		public void run() {
			boolean done = false;
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
						if(message.getCode()%17==0){
							unique = ((message.getCode()/7)/17)/unique;
							System.out.println("redid unique: " + unique);
						}
						if(!message.success()){
							errorMessage = message.getMessage();
						}
						else{
							errorMessage = "Successful login";
							loggedIn = true;
							
						}
					}
					
					if(message.getCode()%13 ==0 && message.getCode()%unique==0){
						done = true;
						outToServer.close();
						inFromServer.close();
					}
					
				} catch (Exception ex) {
					System.out.println("Error receiving the message");
					ex.printStackTrace();
				}
			}
		}
	}
}
