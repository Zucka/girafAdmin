package dk.aau.cs.giraf.savannah.test;

import java.io.File;
import java.util.ArrayList;

import org.jdom.Document;

import dk.aau.cs.giraf.savannah.io.DOMinator.DOMinator;
import dk.aau.cs.giraf.savannah.server.*;


/**
 * Ad hoc testing only.
 * @author martin
 *
 */
public class EventHandlerTest {
		public static void main(String[] args) throws Exception
		{			
			File f = new File("/home/martin/Documents/eadocs/sw6_example.xml");
			DOMinator dom = new DOMinator();
			Document d = dom.Dominate(f);
			
			CommitEvent com = new CommitEvent(d,null);
			
			CommitHandler handler = new CommitHandler();
			handler.HandleIt(com);
			ArrayList<String> q = handler.getQueries();
			for(String s: q)
			{
				System.out.println(s);
			}
			System.out.println("done");
		}
}
