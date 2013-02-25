package dk.aau.cs.giraf.savannah.server;

import java.sql.*;

public class QueryHandler {
	private Connection con = null;
	private PreparedStatement pStat = null;
	private ResultSet result = null;
	public QueryHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://172.25.11.65:3306/04","eder","123456"); //This needs to be changed, to anyone who may read
																								  //this and think, "WTF, clear text password in a public project!?!?!"
																								  //don't worry, this is strictly a testing database
																								  //which is blocked from external access anyway..:-)
	}
	
	public int SendCommit(String s)
	{
		try {
		pStat = con.prepareStatement(s);
		pStat.execute();
		
		} 
		catch (SQLException e)
			{ e.printStackTrace(); }
		return 1;
	}
	
	public ResultSet SendRequest(String s)
	{
		try {
			pStat = con.prepareStatement(s);
			result = pStat.executeQuery();
		}
		catch (SQLException e)
			{ e.printStackTrace(); }
		
		return result;
	}
}
