package assignment7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientSecurity {
	
	public boolean initiate(){
		System.out.println("Initiated Security Protocol");
		Scanner scan = new Scanner(System.in);
		System.out.print("New user? [y/n]:  ");
		char input = scan.nextLine().toLowerCase().charAt(0);
		return input == 'y';
		//scan.close();
	}
	
	public Message registerNewUser(){
		//TODO
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Username: ");
	
		String username = scan.nextLine();
		
		System.out.print("Enter password: ");
		String password = scan.nextLine();
		
		return new Message(5, username, password);
	}
	
	public Message logIn(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Username: ");
	
		String username = scan.nextLine();
		
		System.out.print("Enter password: ");
		String password = scan.nextLine();
		
		return new Message(7, username, password);
	}
}
