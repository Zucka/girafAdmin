package dk.aau.cs.giraf.savannah.test;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;

import dk.aau.cs.giraf.savannah.io.DOMinator.DOMinator;
import dk.aau.cs.giraf.savannah.server.CommitEvent;


public class DOMinatorTest {

	public static CommitEvent e = new CommitEvent(null,null);
	public static void main(String[] args) throws JDOMException, IOException {
		DOMinator dom = new DOMinator();
		Document d = dom.Dominate(xml);
		System.out.println(d.toString());
		System.out.println(e.getFileList());

	}
//<?xml version=\"1.0\"?>
	static String xml = "<sw6ml><AuthUsers><Entry type=\"create\"><certificate type=\"string\">min</certificate><idUser type=\"int\">123</idUser><aRole type=\"int\">1</aRole><username type=\"string\">bob</username><password type=\"string\">bobps</password></Entry></AuthUsers></sw6ml>";
}
