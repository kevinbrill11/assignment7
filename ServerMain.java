package assignment7;

import java.util.Observable;

public class ServerMain {
	public static void main(String args[]){
		ChatServer server = new ChatServer();
		server.start();
	}
}
