package dk.aau.cs.giraf.savannah.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import dk.aau.cs.giraf.savannah.io.DOMinator.DOMinator;


public class XPathTest {
	public static void main(String args[])
	{
		doStuff();
	}
	
	public static void doStuff()
	{
		try{
		File f = new File("/home/martin/Documents/eadocs/sw6_example.xml");
		DOMinator dom = new DOMinator();
		Document d = dom.Dominate(f);
		
		XPath xp = XPath.newInstance("/sw6ml/AuthUser/Entry");
		List<Object> t = xp.selectNodes(d);
		
		int i = 1;
		String[] str = new String[2];
		for(Object o: t)
		{	
			xp = XPath.newInstance("/sw6ml/AuthUser/Entry["+ i +" ]/certificate");
			str[0] = xp.valueOf(o);
			xp = XPath.newInstance("/sw6ml/AuthUser/Entry["+ i +" ]/idUser");
			str[1] = xp.valueOf(o);
			xp = XPath.newInstance("/sw6ml/AuthUser//@action");
			System.out.println(xp.valueOf(o));
			System.out.println(str[0] + " " + str[1]);
			i++;
		}

		}
		catch(IOException e)
		{ e.printStackTrace(); }
		catch(JDOMException e)
		{ e.printStackTrace();}
		
		
	}

}
