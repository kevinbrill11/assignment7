
package assignment7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ServerSecurity {
	private final File folder;
	private BufferedReader br;
	BufferedWriter bw;
	private HashMap<String, String> users;
	boolean init;
	
	public ServerSecurity(){
		//load map with usernames and passwords
		folder = new File(System.getProperty("user.home"), "Top_Secret");
		if(!folder.exists() && !folder.mkdir()) {
		   //failed to create the folder, probably exit
		   throw new RuntimeException("Failed to create save directory.");
		}
		
		br = null;
		try {
			br = new BufferedReader(new FileReader(new File(folder, "Top_Secret.txt")));
			bw = new BufferedWriter(new FileWriter(new File(folder, "Top_Secret.txt")));
		} catch (Exception e) {
			System.out.println("Reading initialization error");
			e.printStackTrace();
		}
		
		users = new HashMap<String, String>();
		init = false;
		readDatabase();
	}
	
	private void readDatabase(){
		String username = null;
		String password = null;
	
		try {
			while ((username = br.readLine())!=null) {
				password = br.readLine();
				if(!users.containsKey(username))
					users.put(username, password);
			}
		} catch (Exception e) {
			System.out.println("Reading/reading closure error");
			e.printStackTrace();
		}
	}
	
	public boolean registerNewUser(String username, String password){
		readDatabase();
		System.out.println("Server registering new user");
		System.out.println("Adding " + username + " - " + password);
		if(users.containsKey(username))
			return false;
		users.put(username, password);
		
		for(String str: users.keySet())
		{
			try {
				bw.write(str + "\n");
				bw.write(users.get(str) + "\n");
			} catch (IOException e) {
				System.out.println("file writing error");
				e.printStackTrace();
			}
		}
		
//		try {
//			bw.close();
//		}
//		catch (IOException e) {
//			System.out.println("BufferedWriter closing error");
//			e.printStackTrace();
//		}
		return true;
	}
	
	
	public boolean logIn(String username, String password){
		if(users.containsKey(username))
			return users.get(username).equals(password);
		return false;
	}
}
