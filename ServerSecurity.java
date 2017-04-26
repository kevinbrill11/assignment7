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
	private File f;
	private HashMap<String, String> users;
	
	public ServerSecurity(){
		//load map with usernames and passwords
		f = new File("Top_Secret.txt");
		users = new HashMap<String, String>();
		readDatabase();
	}
	
	private void readDatabase(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("Top_Secret.txt")));
		} catch (FileNotFoundException e) {
			System.out.println("Reading initialization error");
			e.printStackTrace();
		}
	
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
		FileWriter fr = null;
		BufferedWriter bw = null;
		try {
			fr = new FileWriter(f);
			bw = new BufferedWriter(fr);
		}
		catch(Exception e){
			System.out.println("writing initialization exception");
		}
		
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
		
		try {
			bw.close();
		}
		catch (IOException e) {
			System.out.println("BufferedWriter closing error");
			e.printStackTrace();
		}
		return true;
	}
	
	
	public boolean logIn(String username, String password){
		if(users.containsKey(username))
			return users.get(username).equals(password);
		return false;
	}
}
