package hr.fer.oprpp1.crypto;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.Scanner;

public class Crypto {
	
	private static final int BUFFER_SIZE = 4096;
	
	public static void main(String[] args) {
		
		if (args.length == 2) {
			String mode = args[0];
			String fileName = args[1];
			checkSha(mode, fileName);
		} else if (args.length == 3) {
			
		} else throw new IllegalArgumentException("Expected 2 or 3 arguments");
	}
	
	private static void checkSha(String mode, String fileName) {
		Objects.requireNonNull(mode, "The given mode can not be null!");
		if(!mode.equalsIgnoreCase("checksha")) {
			throw new IllegalArgumentException("The given mode of program is not valid. "
					+ "Expected checksha. Was " +  mode);
		}
		
		Scanner s = new Scanner(System.in);
		System.out.println("Please provide expected sha-256 digest for " + fileName + ":\n>");
		String expectedDigest = s.nextLine();
		s.close();
		
		try(InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("./" + fileName), StandardOpenOption.READ))) {
			
			byte[] buffer = new byte[BUFFER_SIZE];
			MessageDigest digest = MessageDigest.getInstance("sha-256");
			
			while(true) {
				int numberOfReadBytes = is.read(buffer);
				if(numberOfReadBytes < 1) break;
				digest.update(buffer, 0, numberOfReadBytes);
			}
			
			byte hash[] = digest.digest();
			String messageDigest = Util.bytetohex(hash);
			
			if(expectedDigest.equals(messageDigest)) {
				System.out.println("Digesting completed. Digest of "
						+ "hw06test.bin matches expected digest.");
				
			} else {
				System.out.println("Digesting completed. Digest of hw06test.bin does "
						+ "not match the expected digest. Digest\n" 
						+ "was: " + messageDigest);
			}
			
		} catch (Exception e) {
			//can be ignored
		}
		
	}
}
