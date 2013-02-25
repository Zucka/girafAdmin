package dk.aau.cs.giraf.savannah.server;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import dk.aau.cs.giraf.savannah.io.*;


public class RequestHandler {
	private QueryHandler qHandler;
	private ArrayList<ResultSet> rset;
	private XMLBuilder xBuilder;
	private String xml;
	public RequestHandler()
	{
		try {
			qbuilder = new QueryBuilder();
			qHandler = new QueryHandler();
			xBuilder = new XMLBuilder();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private QueryBuilder qbuilder;
	
	public void HandleIt(RequestEvent e)
	{
		try{

		String s = e.getEventContent();
		ArrayList<String> que = qbuilder.buildQueries(s);
		rset = RunQueries(que);
		xml = xBuilder.build(rset);
		ArrayList<File> files = xBuilder.getFiles();
		File[] afiles = new File[files.size()];
		files.toArray(afiles);
		//line 2 for no files, line 1 for plenty files
//		savannah.io.IOHandler.getInstance().respond(e.getEventsocket(),CRUD.REQUEST,xml,afiles);
		dk.aau.cs.giraf.savannah.io.IOHandler.getInstance().respond(e.getEventsocket(),CRUD.REQUEST,xml);
		dk.aau.cs.giraf.savannah.io.IOHandler.getInstance().logIt(e.getEventsocket(), CRUD.REQUEST.toString(), true);
		}
		catch (SQLException exc)
		{
			exc.printStackTrace();
		}
	
		
	}
	
	private ArrayList<ResultSet> RunQueries(ArrayList<String> queries)
	{
		ArrayList<ResultSet> out = new ArrayList<ResultSet>();
		for(String s: queries)
			{
				out.add(qHandler.SendRequest(s));
			}
		return out;
	}
	
	public ArrayList<ResultSet> getRset()
	{
		return rset;
	}
	public String getXML()
	{
		return xml;
	}
}
