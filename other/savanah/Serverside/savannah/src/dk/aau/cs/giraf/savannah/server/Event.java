package dk.aau.cs.giraf.savannah.server;

import java.net.Socket;

import dk.aau.cs.giraf.savannah.io.CommunicationThread;


/**
 * Event Interface
 * @author Martin Fjordvald
 *
 */
public interface Event {
	public Socket socket = null;
	public CommunicationThread com = null;
	
	public Socket getEventsocket();
	public Event getEventType();
	public CommunicationThread getCom();
	
}
