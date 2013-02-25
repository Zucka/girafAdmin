package dk.aau.cs.giraf.savannah.test;

import java.util.ArrayList;

import dk.aau.cs.giraf.savannah.server.QueryBuilder;
import dk.aau.cs.giraf.savannah.test.*;

public class RequestQueryBuilderTest {
	public static void main(String args[])
	{
		QueryBuilder qbuilder = new QueryBuilder();
		String data = "profiles=cert1,1,cert2,2,cert3,3,cert4,4&childrenOfGuardian=1,2,3,4&";
		data = "profiles=cert1,1&";
		ArrayList<String> q = qbuilder.buildQueries(data);
		
		for (String s : q) {
			System.out.println(s);
		}
		
	}

}
