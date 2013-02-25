package dk.aau.cs.giraf.savannah.test;

import java.sql.ResultSet;
import java.util.ArrayList;

import dk.aau.cs.giraf.savannah.server.RequestEvent;
import dk.aau.cs.giraf.savannah.server.RequestHandler;

public class RequestEventTest {

	/**
	 * 
	 */
	public static void main(String[] args) throws Exception {
		
		RequestEvent req = new RequestEvent("profiles=cert1,1&", null);
		RequestHandler rHandler = new RequestHandler();		
		
		rHandler.HandleIt(req);
		ArrayList<ResultSet> rset = rHandler.getRset();
		System.out.println(rHandler.getXML());
	}
}
