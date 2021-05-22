package hr.fer.oprpp1.crypto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0 || !isHex(keyText)) {
			throw new IllegalArgumentException("Invalid input string!");
		}
		
		byte[] array = new byte[keyText.length() / 2];
		
		for (int i = 0; i < keyText.length(); i += 2) {
			array[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4) 
								  + Character.digit(keyText.charAt(i + 1), 16));
		}
		
		return array;
	}
	
	public static String bytetohex(byte[] byteArray) {
		StringBuilder str = new StringBuilder(byteArray.length * 2);
		for (byte b : byteArray) {
			str.append(Character.forDigit((b >> 4) & 0xF, 16));
			str.append(Character.forDigit((b & 0xF), 16));
		}
		
		return str.toString();
	}
	
	private static boolean isHex(String text) {
		if (text.length() == 0) return true;
		Pattern p = Pattern.compile("\\p{XDigit}+");
		Matcher matcher = p.matcher(text);
		return matcher.matches();
	}
}

