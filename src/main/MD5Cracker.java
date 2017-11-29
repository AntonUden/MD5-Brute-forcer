package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class MD5Cracker {
	public static void main(String[] args) {
		if(args.length > 0) {
			if(args[0].length() > 0) {
				new MD5Cracker(args[0]);
			}
		} else {
			System.out.println("usage: java -jar MD5Cracker.jar <Hash>");
		}
	}

	public String out, outMD5 = "null";
	
	public MD5Cracker(String crack) {
		Timer t = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Current progress: " + out + " = " + outMD5 + " | Target = " + crack + " | Free memory: " + Runtime.getRuntime().freeMemory());
			}
		});
		t.start();
		String chars = "abcdefghijklmnopqrstuvwxyzåäöABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ0123456789!#%&/()=\" ?-_.,:;";
		int max = chars.length();
		int[] numberArray = new int[100];
		for (int i = 0; i < numberArray.length; i++) {
			numberArray[i] = -1;
		}
		System.out.println("Starting brute force of md5 : " + crack);
		do {
			numberArray[0]++;
			for (int i = 0; i < numberArray.length; i++) {
				if (numberArray[i] >= max) {
					if (!(i + 1 > numberArray.length)) {
						numberArray[i + 1]++;
						numberArray[i] = 0;
					}
				}
			}
			out = "";
			for (int j = 0; j < numberArray.length; j++) {
				if (!(numberArray[j] == -1)) {
					out += chars.charAt(numberArray[j]);
				}
			}
			outMD5 = MD5(out);
			
			//System.out.println(out + " = " + outMD5);
			
			if (outMD5.equals(crack)) {
				t.stop();
				System.out.println();
				System.out.println("MD5 Cracked result: " + crack + " = " + out);
				break;
			}
		} while(true);
	}

	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
}
