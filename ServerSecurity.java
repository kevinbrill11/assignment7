
package assignment7;

/* CHAT ROOM ServerSecurity.java
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
	private BufferedWriter bw;
	private HashMap<String, String> users;
	boolean init;
	
	public ServerSecurity(){
		//load map with usernames and passwords
		folder = new File(System.getProperty("user.home"), "Top_Secret");
		if(!folder.exists()) {
		   //failed to create the folder, probably exit
		   throw new RuntimeException("Failed to create save directory.");
		}
		bw = null;
		try {
			br = new BufferedReader(new FileReader(new File(folder, "Top_Secret.txt")));
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
		boolean retVal = true;
		readDatabase();
		try {
			bw = new BufferedWriter(new FileWriter(new File(folder, "Top_Secret.txt")));
		} catch (Exception e) {
			System.out.println("Reading initialization error");
			e.printStackTrace();
		}
		System.out.println("Server registering new user");
		System.out.println("Adding " + username + " - " + password);
		if(users.containsKey(username))
			retVal = false;
		else
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
		return retVal;
	}
	
	
	public boolean logIn(String username, String password){
		if(users.containsKey(username))
			return users.get(username).equals(password);
		return false;
	}
	
	public void logOff(){
		try {
			if(bw != null)
				bw.close();
		}
		catch (IOException e) {
			System.out.println("BufferedWriter closing error");
			e.printStackTrace();
		}
	}
}
