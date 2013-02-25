package dk.aau.cs.giraf.savannah.server;

import java.sql.SQLException;

import dk.aau.cs.giraf.savannah.io.IOHandler;


/**
 * EventHandler class for picking out events from the EventQue and process them.
 * Create as a thread and start.
 * @author Martin Fjordvald
 *
 */
public class EventHandler implements Runnable {
	private CommitHandler cHandler;
	private RequestHandler rHandler;

	public EventHandler()
	{
		try {
			cHandler = new CommitHandler();
			rHandler = new RequestHandler();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("something is wrong with the sql or db connection");
			e.printStackTrace();
		}
		
	}
	/**
	 * The EventHandler
	 */
	public void start() {
		EventQueue eque = EventQueue.getInstance();
		while(true) {
			while(!eque.isEmpty()) {
				System.out.println("Que is not Empty!");
				Event e = eque.remove();
				if (e.getClass().equals(dk.aau.cs.giraf.savannah.server.CommitEvent.class))
				{
					cHandler.HandleIt((CommitEvent)e);
					IOHandler.getInstance().removeConnection(e.getEventsocket());
				}
				else if(e.getClass().equals(dk.aau.cs.giraf.savannah.server.RequestEvent.class))
				{
					rHandler.HandleIt((RequestEvent)e);
					IOHandler.getInstance().removeConnection(e.getEventsocket());
				}	
			}
		}	
	}

	@Override
	public void run() {
		System.out.println("Starting the eventhandler");
		start();
	}
}