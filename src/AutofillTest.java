import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.junit.Test;

public class AutofillTest {
	@Test
	public static void main(String[] args) throws FileNotFoundException, CryptoException {
		//create Autofill object
		Autofill obj = new Autofill();
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Add a new pass.");
		System.out.println("2 - Find a pass.");
		System.out.println("3 - Remove pass.");
		System.out.println("0 - Exit");
		String answer = in.next();
		while(!answer.equals("0")) {
			if(answer.equals("1")) {
				System.out.println("What is the username?");
				String username = in.next();
				System.out.println("What is the password?");	//append new user info to file
				String pass = in.next();
				obj.addPass(username, pass);
			}
			else if(answer.equals("2")) {
				System.out.println("What's the username of the password you're looking for?"); 
				String username = in.next();
				obj.findPass(username);
			}
			else if(answer.equals("3")) {
				System.out.println("Enter the username for the password you'd like to remove.");
				String username = in.next();
				obj.deletePass(username);
			}
			else {
				System.out.println("Enter one of the following selections:");
			}
			System.out.println("1 - Add a new pass.");
			System.out.println("2 - Find a pass.");
			System.out.println("3 - Remove pass.");
			System.out.println("0 - Exit");
			answer = in.next(); 
		}
	}
}
