import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Autofill {
	//this method is used by findPass to copy the password linked to the entered username to clipboard.
	public void copyStringtoClipboard(String s) {
		String str = s;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(str);
		clipboard.setContents(strSel, null);
	}
	//this method takes in a username and password and adds it to the password file
	public void addPass(String username, String pass) throws FileNotFoundException, CryptoException {
		String key = "Very Fun Project";
		File logFile = new File("log.txt").getAbsoluteFile();
		File passFile = new File("pass.encr").getAbsoluteFile();
		//check if file exists
		if(passFile.exists()) {
			//set it true to append info to existing file.
			try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(passFile, true))){ //efficiently write to files
				CryptoUtils.decrypt(key, passFile, passFile); 			//decrypt the file first
				bos.write(("\n" + username + " " + pass).getBytes());	//write info to file
			}
			catch(IOException e) {
				System.out.println("Could not add password to file.");
				PrintStream ps = new PrintStream(logFile);
				e.printStackTrace(ps);
				ps.close();
			}
			finally {
				CryptoUtils.encrypt(key, passFile, passFile);	//even if program crashes, file will still be encrypted
			}
		}
		else {
			try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(passFile, true))){	//efficiently write to files
				System.out.println("The password file has been created.");
				bos.write((username + " " + pass).getBytes());	//write info to file
			}
			catch(IOException e) {
				System.out.println("There was a problem writing to the new file.");
				PrintStream ps = new PrintStream(logFile);
				e.printStackTrace(ps);
				ps.close();
			}
			finally {
				CryptoUtils.encrypt(key, passFile, passFile); 	//always encrypt file after writing
				}
		}
	}
	//this method finds a password linked with the username the method takes in
	public void findPass(String username) throws CryptoException, FileNotFoundException {
		String key = "Very Fun Project";
		File logFile = new File("log.txt").getAbsoluteFile();
		File passFile = new File("pass.encr").getAbsoluteFile();
		if(passFile.exists()) {
			try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(passFile))){
				CryptoUtils.decrypt(key, passFile, passFile); //decrypt the file to read it
				byte[] input = bis.readAllBytes();		//read all bytes of file
				String[] fileContents = new String(input).split("\n");		//convert byte array to string array split by new line char
				for(String i: fileContents) {
					String[] temp = i.split(" ");	//iterate through string array and split up by space char to get password
					if(temp[0].equals(username)) {
						copyStringtoClipboard(temp[1]);	//copy password to clipboard
						System.out.println("Password copied.");
						CryptoUtils.encrypt(key, passFile, passFile); 
						return;
					}
				}
				System.out.println("Password not found.");
				CryptoUtils.encrypt(key, passFile, passFile);	
			}
			catch(IOException | CryptoException e) {
				System.out.println("Something went wrong reading the file.");
				PrintStream ps = new PrintStream(logFile);
				e.printStackTrace(ps);
				ps.close();
			}
		}
		else {
			System.out.println("File doesn't exist.");
		}
	}
	//this method takes in a username and deletes the username and password linked to in the file
	public void deletePass(String username) throws CryptoException, FileNotFoundException {
		String key = "Very Fun Project";
		File logFile = new File("log.txt").getAbsoluteFile();
		File passFile = new File("pass.encr").getAbsoluteFile();	//open file
		if(passFile.exists()) {										//check if file exists
			try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(passFile))){
				CryptoUtils.decrypt(key, passFile, passFile);		//decrypt file
				byte[] input = bis.readAllBytes();					//read all contents of file into byte array
				String[] fileContents = new String(input).split("\n");	//split contents into string array by new line character
				String outputToFile = "";	//this string will be the concatenation of the newFileContents with new line spaces in between each line.
				int len = fileContents.length;
				for(int i = 0; i < len; i++) {
					if(i == 0) {
						if(fileContents[i].contains(username))	//for both cases, if an entry of user/combo contains username to be deleted, it is not added to new file
							continue;
						outputToFile += fileContents[i];		//first user/pass combo doesn't get new line character
					}
					else {
						if(fileContents[i].contains(username))
							continue;
						outputToFile += "\n" + fileContents[i];
					}
				}
				//next we want to turn the array list contents back into bytes and overwrite them to the file, then encrypt
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(passFile));
				bos.write(outputToFile.getBytes());
				bos.close();
				System.out.println("Password has been deleted.");
				CryptoUtils.encrypt(key, passFile, passFile);
			}
			catch(IOException e) {
				System.out.println("Something went wrong with reading the file");
				PrintStream ps = new PrintStream(logFile);
				e.printStackTrace(ps);						//print to log file before exiting
				ps.close();
			}
		}
		else {
			System.out.println("File doesn't exist.");		
		}
	}
}