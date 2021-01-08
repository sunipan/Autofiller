

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class CryptoUtilsTest {
	public static void main(String[] args) throws CryptoException {
		String key = "Very Fun Project";
		File inputFile = new File("src/first/pass.encr").getAbsoluteFile();
		File outputFile = new File("src/first/temp.txt").getAbsoluteFile();
		
			CryptoUtils.encrypt(key, inputFile, outputFile);
			inputFile.delete();
			Scanner in = new Scanner(System.in);
			String j = in.next();
			in.close();
			CryptoUtils.decrypt(key, outputFile,inputFile);
			outputFile.delete();
	}
}
