package dk.aau.cs.giraf.savannah.test;

/**
 * A Client strictly for testing purposes
 */
import java.io.*;
import java.net.Socket;

//Client
public class TestingPurposeClient implements Runnable
{
	//XML fields
	static PrintWriter out_xml;
	static BufferedReader in_xml;
	
	//JPG fields
	static OutputStream os;
	static FileInputStream reader;
	static FileOutputStream sender;
	static BufferedInputStream in_jpg;
	static BufferedOutputStream out_jpg;
	static ObjectOutputStream oout;
	static ObjectInputStream oin;
	
	static String msg = "";
	
	public static void client_xml()
	{	
		try {
		File file = new File("/home/martin/Documents/eadocs/sw6_example.xml"); //Specific path for my computer
		reader = new FileInputStream(file);
		
		in_xml = new BufferedReader(new InputStreamReader(reader));
		
		while(in_xml.ready())
		{
			msg = msg + in_xml.readLine() + System.getProperty("line.separator");
			System.out.println(msg);
		}
		Socket socket = new Socket("127.0.0.1",50000);
		
			out_xml = new PrintWriter(socket.getOutputStream(),true);
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out_xml.println(msg);
		
		System.out.println("hopefully send a msg");
		}
		catch (Exception e)
		{ e.printStackTrace();}
	}
	
	public static void client_jpg()
	{	
		try {
		
		File file = new File("/home/martin/Documents/eadocs/pics/world.jpg"); //Specific path for my computer
		
		Socket socket = new Socket("127.0.0.1",50000);
		oout = new ObjectOutputStream(socket.getOutputStream());
		
		System.out.println("Preparing to send");
		
		oout.writeObject(file);	
		oout.close();
		
		System.out.println("hopefully send a msg");
		}
		catch (Exception e)
		{ e.printStackTrace();}
	}


	public static void main(String args[])
	{   
		//client_xml();
		client_jpg();
	}
	@Override
	public void run() {
		try { client_xml(); }
		catch (Exception e)
		{e.printStackTrace();}
	}
}

