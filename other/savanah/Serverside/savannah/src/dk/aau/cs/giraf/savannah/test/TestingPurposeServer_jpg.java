package dk.aau.cs.giraf.savannah.test;


import java.io.*;
import java.net.*;

//Server
public class TestingPurposeServer_jpg implements Runnable
{
	static ObjectInputStream ooin;
	static InputStream in;
	
	static ServerSocket serverSocket;
	static Socket client;
	public static void server()
	{
		try {
			serverSocket = new ServerSocket(50000);
			System.out.println("Server running, listening on port 50000");
			
			client = serverSocket.accept();
			in = client.getInputStream();
			ooin = new ObjectInputStream(in);
		
			File f = null;
			while(f == null)
				{
					f = (File) ooin.readObject();
				}

			File f1 = new File("/home/martin/Documents/eadocs/pics/world_rcv.jpg");
			
			InputStream fin = new FileInputStream(f);
			OutputStream fout = new FileOutputStream(f1);
			
			int len;
			byte[] buf = new byte[1024];
			
			while((len = fin.read(buf)) > 0)
			{
				fout.write(buf,0,len);
			}
			in.close();

			System.out.println("Done");

			in.close();
			ooin.close();
			client.close();
			
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
