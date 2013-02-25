package dk.aau.cs.giraf.savannah.io.DOMinator;

// JDOM imports
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

// Input/Output imports
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.io.BufferedReader;

public class DOMinator {
	
	private SAXBuilder sax;
	
	public DOMinator()
	{
		sax = new SAXBuilder();	
	}
	
	public Document Dominate(File f) throws IOException, JDOMException
	{
		return sax.build(f);
	}
	
	public Document Dominate(InputStream in) throws IOException, JDOMException
	{
		return sax.build(in);
	}
	
	public Document Dominate(String s) throws JDOMException, IOException
	{
		StringReader reader = new StringReader(s);
		return sax.build(reader);
	}
}
