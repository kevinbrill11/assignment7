package assignment7;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

//import day23network.observer.ClientObserver;
//import day23network.observer.ChatServer.ClientHandler;

public class ChatServer extends Observable{
	private ClientObserver outToClient;
    private ServerSecurity ss;
    int[] primes = {3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};
    //3 => assign unique number
    //5 => register new username/password
    //7 => verify username/password
    //11 => user is requesting users online
    //13 => user has left chat
    //17 => previous user returning, reassign unique
    //19 => new conversation
    //23 => previous conversation
    int userIndex;
    HashMap<String, Integer> usernames;
    HashSet<String> clientsLoggedIn;
    
    public static void main(String[] args){
    	ServerGrfx.initiate(args);
    	System.out.println("Server started.");
    	new ChatServer().start();
    }

	public void start(){
		usernames = new HashMap<String,Integer>();
		clientsLoggedIn = new HashSet<String>();
		userIndex = 8;
		initSecurity();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8387);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Problem with accept()");
				e.printStackTrace();
			}
			//ClientObserver writer = null;
			try {
				//writer = new ClientObserver(clientSocket.getOutputStream());
				outToClient = new ClientObserver(clientSocket.getOutputStream());

			} catch (Exception e) {
				System.out.println("Problem with new ClientObserver");
				e.printStackTrace();
			}
			Thread t = new Thread(new ClientHandler(clientSocket, this, outToClient));
			t.start();
			this.addObserver(outToClient);
			System.out.println("got a connection");
		}
	}
	class ClientHandler implements Runnable {
		int unique;
		ObjectInputStream inFromClient;
		ClientObserver output;
		ChatServer server;
		public ClientHandler(Socket clientSocket, ChatServer s, ClientObserver o) {
			Socket sock = clientSocket;
			unique = primes[userIndex];
			server = s;
			output = o;
			try {
	            inFromClient = new ObjectInputStream(sock.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//@SuppressWarnings("unchecked")
		public void run() {
			try {
				boolean done = false;
				while (!done) {
					/*LinkedList<Message> inList = new LinkedList<>();
		            Message inMsg = null;
		            inList = (LinkedList<Message>)inFromClient.readObject();
		            inMsg = inList.pop(); */
					System.out.println("At top of server reader #" + unique);
					Message message = (Message) inFromClient.readObject();
					System.out.println("Server read message " + message.getCode() + " " + message.getMessage());
					if(message.getRecipients() != null){
						System.out.print("server recipients: " + message.getRecipients().toUpperCase());
					}
					
					if(message.getCode()%3 == 0){ //assigning unique number
						message.setCode(message.getCode()*primes[userIndex]);
						userIndex++;
					}
					
					else if(message.getCode()%5 == 0){ //register new username/password pair
						System.out.println("registering new user");
						if(ss.registerNewUser(message.getUsername(), message.getPassword()))
							message.setSuccess(true);
						else message.setSuccess(false);
					}
					
					else if(message.getCode()%7 == 0){ //verify username/password pair
						if(clientsLoggedIn.contains(message.getUsername())){
							message.setSuccess(false);
							message.setMessage("User " + "\"" + message.getUsername() + "\"" + " is already logged in");
						}
						else if(ss.logIn(message.getUsername(), message.getPassword())){
							message.setSuccess(true);
							if(usernames.containsKey(message.getUsername())){
								message.setCode((message.getCode()*17));
								message.setCode(message.getCode()*usernames.get(message.getUsername()));
								unique = usernames.get(message.getUsername());
								userIndex--;								
							}
							else
								usernames.put(message.getUsername(), message.getCode()/7);
							clientsLoggedIn.add(message.getUsername());
						}
						else{
							message.setSuccess(false);
							message.setMessage("Unrecognized username/password combination");
						}
					}
					
					else if(message.getCode()%11 == 0){ //user is requesting users online
						String names = "";
						for(String name: clientsLoggedIn){
							names += name + " ";
						}
						message.setUsername(names);
					}
					
					else if(message.getCode()%13 != 0){ //new message bc %19
						String sender = null;
						for(String name: usernames.keySet()){
							if(usernames.get(name) == unique){
								sender = name;
							}
						}
						if(sender == null){
							sender = "xX_Hacker69_Xx";
						}
						
						sender += ": ";
						
						message.setMessage(sender+message.getMessage());						
					}
					
					setChanged();
					notifyObservers(message);
					
					if(message.getCode()%13 == 0){//user is logging off
						System.out.println("server logoff protocol");
						ss.logOff();
						server.deleteObserver(output);
						done = true;
						
						String sender = null;
						for(String name: usernames.keySet()){
							if(usernames.get(name) == unique){
								sender = name;
							}
						}
						clientsLoggedIn.remove(sender);
					}
				}
			} catch (Exception e) {
				System.out.println("Problem with server reading message");
				e.printStackTrace();
			}
		}
	}
		
	public boolean isSpecialCode(int n){
		return n%3==0 || n%5==0 || n%7==0;
	}
	
	public void initSecurity(){
		ss = new ServerSecurity();
		
	}
}
