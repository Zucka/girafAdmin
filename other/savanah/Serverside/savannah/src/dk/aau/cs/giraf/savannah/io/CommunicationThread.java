package dk.aau.cs.giraf.savannah.io;

import org.jdom.JDOMException;

import dk.aau.cs.giraf.savannah.io.DOMinator.DOMinator;
import dk.aau.cs.giraf.savannah.server.*;


import java.net.Socket;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.Thread;



/**
 * This class is used to facilitate communication between the {@link dk.aau.cs.giraf.savannah.io.IOHandler} and any connection to it.
 * @author Thorbjørn Kvist Nielsen
 *
 */
public class CommunicationThread extends Thread {
	private Socket socket;
	private TransmissionHandler handle = null;
	private String folder;

	/**
	 * Constructs a CommunicationThread from the specified arguments.
	 * This {@link java.lang.Thread} does not start automatically, and therefore it needs to be started manually.
	 * @param _socket - the connecting {@link java.net.Socket}
	 * @param _folder - the specified path for file storage
	 */
	public CommunicationThread(Socket _socket, String _folder) {
		this.socket = _socket;
		this.folder = _folder;
		//		start();
	}
	
	/**
	 * Starts the {@link dk.aau.cs.giraf.savannah.io.CommunicationThread}.
	 */
	public void run() {
		try {
//			InputStream inputStream = new DataInputStream(this.socket.getInputStream());
			IOHandler.getInstance().displayMessage("Connecting from: " + this.socket);
			try {
				handle = new TransmissionHandler(this.socket, this.folder);
			}	catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}	catch (IOException e) {
				System.err.println(e.getMessage());
			}	catch (IllegalStateException e) {
				System.err.println(e.getMessage());
			}
//			IOHandler.getInstance().logIt(this.socket, handle.getCRUD().toString());
//			IOHandler.getInstance().logIt(handle.getFILES());

			//CommitEvent
			//TODO Insert the EventQueue and remove dummy 
			if (handle.getCRUD() == CRUD.COMMIT) {
				DOMinator domI = new DOMinator();
				IOHandler.getInstance().displayMessage("CommitEvent created by: " + this.socket);
				CommitEvent comEvt = new CommitEvent(domI.Dominate(handle.getXML()), this.socket);
				comEvt.addFiles(handle.getFILES());
				EventQueue.getInstance().add(comEvt);
//				IOHandler.getInstance().respond(this.socket, CRUD.COMMIT, "Anal");
//				IOHandler.getInstance().logIt(true);
			}
			//RequestEvent
			//TODO Insert the EventQueue and remove dummy
			else if (handle.getCRUD() == CRUD.REQUEST) {
				IOHandler.getInstance().displayMessage("RequestEvent created by: " + this.socket);

				RequestEvent reqEvt = new RequestEvent(handle.getXML(), this.socket);
				EventQueue.getInstance().add(reqEvt);
//				IOHandler.getInstance().respond(this.socket, CRUD.REQUEST, "I so hope that this is going to work !!!", 
//						new File("C:\\tempServer\\30767.jpg"), new File("C:\\tempServer\\sadPanda.jpg"), new File("C:\\tempServer\\2292.jpg"), 
//						new File("C:\\tempServer\\zCsLu.jpg"));
//				IOHandler.getInstance().logIt(true);
			}
			//Ping
			//TODO Fix the Ping implementation
			else if (handle.getCRUD() == CRUD.PING) {
				IOHandler.getInstance().displayMessage("Ping sent from: " + this.socket);
				IOHandler.getInstance().respond(this.socket, handle.getPING());
				
				LogFile lf = new LogFile(Configuration.LOGFILEPATH);
				lf.makeLogEntry(this.socket, handle.getCRUD().toString(), true);
//				IOHandler.getInstance().logIt(true);
			}
			//TODO When can we error ?
			else if (handle.getCRUD() == CRUD.ERROR){
				IOHandler.getInstance().displayMessage("TransmissionHandler could not resolve to a known package type");
				LogFile lf = new LogFile(Configuration.LOGFILEPATH);
				lf.makeLogEntry(this.socket, handle.getCRUD().toString(), false);
//				IOHandler.getInstance().logIt(false);
			}
			else {
				IOHandler.getInstance().displayMessage("TransmissionHandler could not resolve to a known package type");
				LogFile lf = new LogFile(Configuration.LOGFILEPATH);
				lf.makeLogEntry(this.socket, handle.getCRUD().toString(), false);
			}
		}	catch (IOException e) {
			System.out.println("CommunicationThread: Could not initiate inputStream for the connection");
		} 	catch (JDOMException e) {
			System.err.println("CommunicationThread: Could not DOMinate");
		}
//			finally {
//			//TODO Didn't we move this implementation ???
//			IOHandler.getInstance().removeConnection(this.socket);
//		}
	}

}

