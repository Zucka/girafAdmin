package dk.aau.cs.giraf.savannah.test;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import dk.aau.cs.giraf.savannah.*;
import dk.aau.cs.giraf.savannah.device.Connection;
public class ServerIntegrationTest{

	/**
	 * @param args
	 */
	public static void main(String[] args){
		try {
			String respons;
			Connection con = new Connection("/home/martin/Documents/eadocs/savannah");
//			TransmissionHandler trans = null;
//			long res = con.sendPing(2);
			respons = con.sendCommit("<sw6ml><AuthUsers></AuthUsers></sw6ml>",new File("/home/martin/Documents/eadocs/sw6_example.xml"));
//			respons = con.sendCommit("<sw6ml><AuthUsers><Entry action=\"create\"><certificate type=\"string\">min</certificate><idUser type=\"int\">124</idUser><aRole type=\"int\">1</aRole><username type=\"string\">bob</username><password type=\"string\">bobps</password></Entry></AuthUsers></sw6ml>");
//			respons = con.sendCommit("<sw6ml><AuthUsers><Entry action=\"delete\"><idUser type=\"int\">124</idUser></Entry></AuthUsers></sw6ml>");
			System.out.println(respons);
//			System.out.println(res);
//			trans = con.sendRequest("profiles=bphiomxvbsricewxcpuzpdtqjdcywlaplsmzjqzayhdyxeawyaeeofkpvfhwaudzwaafihtfuddsbrjhuztepopztbdgcokafnrgqrbaydsryfianltscyitukssklazgubhkdvvjqolmwiyyhuidhyqxoxwabmvdnnxatvzhvkawyiktbswjdcqlustzermuytgqvae,11&");
//			long slong = con.sendPing();
//			
//			System.out.println(slong);
//			String xml = trans.getXML();
//			System.out.println(xml);
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
