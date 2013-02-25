package dk.aau.cs.giraf.savannah.test.JUnit;

import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;

import dk.aau.cs.giraf.savannah.io.DOMinator.DOMinator;
import junit.framework.TestCase;

public class DOMinatorJUnit extends TestCase {

	File f;
	DOMinator dom;
	public DOMinatorJUnit(String name)
	{
		super(name);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		dom = new DOMinator();
		f = new File("/home/martin/Documents/eadocs/sw6_example.xml");
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testDominateAFile() throws IOException, JDOMException
	{
		assertTrue("ehh..true",true);
	}
}
