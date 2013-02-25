package dk.aau.cs.giraf.savannah.test;

import java.sql.SQLException;
import java.util.ArrayList;

import dk.aau.cs.giraf.savannah.server.QueryHandler;


public class QueryTest {


	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		ArrayList<String> queries = new ArrayList<String>();
		QueryHandler qhandler = new QueryHandler();
		
		String delete = "DELETE FROM AuthUsers where certificate='cert3';";
		String insert = "INSERT INTO AuthUsers values('cert3',null,1,'user3','pass');";
		String select = "SELECT * FROM AuthUsers;";
		String update = "UPDATE AuthUsers set idUser=4 where certificate='cert3';";
		queries.add(delete);
		queries.add(insert);
		queries.add(update);
		queries.add(select);
		
		for(String s: queries)
		{
			if(s.startsWith("SELECT"))
			{
				qhandler.SendRequest(s);
				System.out.println("Request send");
			}
			else { System.out.println(qhandler.SendCommit(s)); };
		}
	}

}
