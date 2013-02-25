package dk.aau.cs.giraf.savannah.server;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Event queue class for the savannah server.
 * implemented as a synchronized fifo queue(linked list)
 * EventQueue is a singleton class.
 * @author Martin Fjordvald	
 *
 */
public class EventQueue{
	private static EventQueue _que = null;
	
	private EventQueue() { }
	private Queue<Event> eque = new LinkedList<Event>();
	/**
	 * Singleton implementation EventQueue.
	 * @return the EventQueue.
	 */
	public static synchronized EventQueue getInstance()
	{
		if (_que == null)
		{
			_que = new EventQueue();
		}
		return _que;
	}
	/**
	 * Add an event to the back of the queue
	 * @param e The event to be added
	 */
	public synchronized void add(Event e)
	{
		eque.add(e);
	}
	/**
	 * Remove the event at the front of the queue
	 * @return The Event that was removed.
	 */
	public synchronized Event remove()
	{
		return eque.remove();
	}
	
	/**
	 * Is the queue currently empty?
	 * @return true/false
	 */
	public synchronized boolean isEmpty()
	{
		return eque.isEmpty();
	}
}
