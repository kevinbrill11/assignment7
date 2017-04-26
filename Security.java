package assignment7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class Security {
	private static File f;
	private static HashMap<String, String> users;
	
	public Security(){
	}
	
	public static void loadUsers(){
		f = new File("Top_Secret.txt");
		users = new HashMap<String, String>();
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
	
	private void registerNewUser(){
		//TODO
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Username: ");
		FileWriter fr = null;
		BufferedWriter bw = null;
		try {
			fr = new FileWriter(f);
			bw = new BufferedWriter(fr);
		}
		catch(Exception e){
			System.out.println("writing initialization exception");
		}
	
		String input = scan.nextLine();
		try {
			bw.write(input + "\n");
		} catch (IOException e) {
			System.out.println("file writing error");
			e.printStackTrace();
		}
		
		System.out.print("Enter password: ");
		input = scan.nextLine();
		try {
			bw.write(input + "\n");
		} catch (IOException e) {
			System.out.println("file writing error");
			e.printStackTrace();
		}
		//TODO
		try {
			bw.close();
		}
		catch (IOException e) {
			System.out.println("BufferedWriter closing error");
			e.printStackTrace();
		}
	}
	
	public void initiate(){
		System.out.println("Initiated Security Protocol");
		Scanner scan = new Scanner(System.in);
		System.out.print("New user? [y/n]:  ");
		char input = scan.nextLine().toLowerCase().charAt(0);
		if(input == 'y')
			registerNewUser();
		logIn();
		//scan.close();
	}
	
	private void logIn(){
		String username = "";
		String password = "";
		
		boolean done = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(!done){
			System.out.print("Enter username:  ");
			try{
				username = input.readLine();
			}
			catch(IOException e){
				System.out.println("username exception");
				e.printStackTrace();
			}
			if(users.containsKey(username)){
				System.out.print("Enter password:  ");
				try{
					password = input.readLine();
				}
				catch(IOException e){
					System.out.println("password exception");
				}
				if(users.get(username).equals(password))
					done = true;					
				else
					System.out.println("incorrect password");
			}
			else
				System.out.println("username not found");
		}
		
		/*try {
			//System.out.println("closing stream");
			br.close();
			input.close();
		} catch (IOException e) {
			System.out.println("closing security bufferedreader error");
		}*/
	}
}
