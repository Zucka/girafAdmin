package dk.aau.cs.giraf.savannah.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom.input.*;
import org.jdom.*;

//Server
public class TestingPurposeServer implements Runnable
{
	static BufferedReader in;
	static PrintWriter out;
	static ServerSocket serverSocket;
	static Socket client;
	static String msg = "";
	public static void server()
	{
		try {
			serverSocket = new ServerSocket(50000);
			System.out.println("Server running, listening on port 50000");
			
			client = serverSocket.accept();
			
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(),true);
			
			boolean done = false;
			boolean skippedProlog = false;
			while(!done) {
				while(in.ready()) {
					if(!skippedProlog) {
							in.readLine();
							skippedProlog = true;
						}
					msg = msg + in.readLine();
					//out.println(msg);
					if(!(in.ready())) {
						done = true;
					}
				}	
			}	
			System.out.println(msg);
			
			SAXBuilder sax = new SAXBuilder();
			Document d = sax.build(new StringReader(msg));
			System.out.println("2");
			System.out.println(d.toString());
			
			
		} catch(Exception e)
		  { e.printStackTrace();}
	}

	public static void main(String args[])
	{
		try{ server(); }
		catch (Exception e)
		{e.printStackTrace();}
	}
	@Override
	public void run() {
		try { server(); }
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}
