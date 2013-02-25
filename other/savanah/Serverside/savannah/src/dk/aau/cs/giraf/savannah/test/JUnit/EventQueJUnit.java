package dk.aau.cs.giraf.savannah.test.JUnit;

import dk.aau.cs.giraf.savannah.server.CommitEvent;
import dk.aau.cs.giraf.savannah.server.EventQueue;
import junit.framework.TestCase;

public class EventQueJUnit extends TestCase {
	private EventQueue e;
	private EventQueue f;
	private CommitEvent c;
	
	public EventQueJUnit(String name)
	{
		super(name);
	}
	
	protected void setUp() throws Exception
	{
		c = new CommitEvent(null, null);
		e = EventQueue.getInstance();
		f = EventQueue.getInstance();
		super.setUp();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testSingleton()
	{
		assertEquals(e, f);
	}
	
	public void testRemoveEvent()
	{
		e.add(c);
		e.remove();
		assertTrue("Should be empty",e.isEmpty());
	}
	
	public void testAddEvent()
	{
		e.add(c);
		assertFalse("Should not be empty",e.isEmpty());
	}
}
