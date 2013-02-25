package dk.aau.cs.giraf.savannah.test.JUnit;

import java.io.File;

import dk.aau.cs.giraf.savannah.server.CommitEvent;

import junit.framework.TestCase;

public class CommitEventJUnit extends TestCase {
	private CommitEvent e;
	private File f;
	public CommitEventJUnit(String name)
	{
		super(name);  
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		e = new CommitEvent(null, null);
		f = new File("/home/martin/Documents/eadocs/sw6_example.xml");
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testNullList()
	{
		assertNull("clean event, list should be null",e.getFileList());
	}
	
	public void testAddingOneFile()
	{
		e.addFile(f);
		assertNotNull("Should now not be null, since i added a file",e.getFileList());
	}
	
	public void testContainsFile()
	{
		e.addFile(f); 
		assertTrue("should contain the file f",e.getFileList().contains(f));
	}
	public void testRemoveFile()
	{
		e.addFile(f); //Adding a file, or the file list will be null
		e.removeFile(f);
		assertFalse("should no longer contain the file f",e.getFileList().contains(f));
	}
	
	public void testEventType()
	{
		assertTrue("some message", e.getEventType().getClass().equals(dk.aau.cs.giraf.savannah.server.CommitEvent.class));
	}
	
}
