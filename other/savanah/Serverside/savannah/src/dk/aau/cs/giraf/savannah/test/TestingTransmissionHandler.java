package dk.aau.cs.giraf.savannah.test;

import java.io.File;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dk.aau.cs.giraf.savannah.device.*;
import dk.aau.cs.giraf.savannah.io.TransmissionHandler;


public class TestingTransmissionHandler {
	
	private static final String CLIENTFOLDER = "C:\\tempClient";
	
	public static void main(String[] args) {

//		for (int i = 0; i < 10; i++) {
//			if ( (i + 1) >= 10 ) {
//				System.out.println("i = " + i);
//			}	else {
//				System.out.println("lower: " + i);
//			}
//		}
		
		
		
//		try {
//			Connection con = new Connection(TestingTransmissionHandler.CLIENTFOLDER);
//		
////			TransmissionHandler th = con.sendRequest("Anal !");
////			System.out.println("Any file: " + th.getAnyFiles());
////			for (File f: th.getFILES()) {
////				System.out.println(f.getName());
////				System.out.println(f.getAbsolutePath());
////			}
//			System.out.println(con.sendCommit("My test String", new File("C:\\tempClient\\sadPanda.jpg"), new File("C:\\tempClient\\bendover-sml.jpg")));
//			
//			//The long "empty" part of the received transmission is because of the bufferSize in the Connection.java
//			//Maybe it should receive a TransmissionHandler instead ?
//			
//			//Debugging path:
//			//D:\MyDocuments\SW6\SW62012\branches\Group_3_Server\savannah\bin>
//			
//			//Run option:
//			//java -classpath . savannah.serverMain.ServerMain
//			//java -classpath . savannah.test.TestingTransmissionHandler
//			
////			System.out.println(con.sendCommit("Anal"));
//		}	catch (IOException e) {
//			System.err.println("con: " + e);
//		}
//		long result = -1;
//		System.out.println("result = " + result);
//		try {
//			Connection con = new Connection(TestingTransmissionHandler.CLIENTFOLDER);
//			result = con.sendPing(0);
//		}	catch (IOException e) {
//			System.err.println("Could not ping !");
//		}
//		System.out.println("result = " + result);
		
		
		

		//"FILE\\[[0-9]*,[0-9]*\\]"
//		Pattern pat = Pattern.compile("\\[[0-9]*\\]");
//		String input = "PING[4096]=\"";
//		Matcher man = pat.matcher(input);
//		
//		if (man.find() == true) {
//			System.out.println("Found: " + man.group());
//			System.out.println("Start index ? = " + man.start());
//			System.out.println("End index ? = " + man.end());
//			System.out.println(input.subSequence(man.start(), man.end()));
//			System.out.println("SubString test: " + (input.substring(man.start()+1, man.end()-1)));
//		}
//		else {
//			System.err.println("Could not match expression");
//		}
//		
//		System.out.println("---------------------------------");
//		String in = "                     hello.jpg";
//		String result = "";
//		int index = -1;
//		for (int i = 0; i < in.length(); i++) {
//			if (in.charAt(i) != ' ') {
//				index = i;
//				break;
//			}
//		}
//		for (int i = index; i  < in.length(); i++) {
//			result += in.charAt(i);
//		}
//		System.out.println("Result: ---" + result + "---");
		
		char[] testData = new char[]{'T','e','s','t',' ','d','a','t','a'};
		byte[] temp = messageToBytes(testData);
		System.out.println(temp);
//		[84, 101, 115, 116, 32, 100, 97, 116, 97]
		
		for(int i = 0; i < temp.length; i++) {
			System.out.print((char)temp[i]);
		}
		System.out.println("");
		
		String tempp = "XMLSchema.xsd";
		String result = fileName(tempp);
		System.out.println(fileName(tempp));
	}
	public static byte[] messageToBytes(char[] c) {
		byte[] buf = new byte[c.length];
		for (int i = 0; i < c.length; i++) {
			buf[i] = (byte)c[i];
		}
		return buf;
	}
	private static String fileName(String fileName) {
		StringBuilder sb = new StringBuilder();
		int maxFileNameSize = 256;
		for (int i = 0; i < (maxFileNameSize - fileName.length()); i++) {
			sb.append(" ");
		}
		sb.append(fileName);
		return sb.toString();
	}

}
