package dk.aau.cs.giraf.savannah.server;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;

import dk.aau.cs.giraf.savannah.io.CommunicationThread;

/**
 * 
 * @author Martin Fjordvald.
 * Event for handling commits to the database
 * Commits: delete, update and insert queries.
 */
public class CommitEvent implements Event {
	
	private Document event;
	private Socket socket;
	private CommunicationThread com1;
	public ArrayList<File> files = null;
	/**
	 * 
	 * @param d The DOM object containing the xml(DOM object)
	 * @param s The network socket attached to this event, needed for responding to the sender
	 */
	public CommitEvent(Document d,Socket s)
	{
		this.event = d;
		this.socket = s;
	}
	/**
	 * return the DOM object attached to this event.
	 * @return Document, returns the DOM Object.
	 */
	public Document getEventContent()
	{
		return event;
	}
	/**
	 * Add a file to the event
	 * @param e File to be added to the event.
	 */
	public void addFile(File e)
	{
		
		if(files == null) {
			this.files = new ArrayList<File>();
		}
		this.files.add(e);
	}
	/**
	 * Remove a file from the event
	 * @param e The file to be removed. will not fail if file does not exist.
	 */
	public void removeFile(File e)
	{
		if(files.contains(e))
		{
			files.remove(e);
		}
	}
	
	public void addFiles(List<File> newFiles)
	{
		if(files == null)
			 { this.files = new ArrayList<File>(); }
		this.files.addAll(newFiles);
	}
	/**
	 * Retrieve a an ArrayList<File> of files attached to this event
	 * @return ArrayList<File>
	 */
	public ArrayList<File> getFileList()
	{
		return files;
	}
	
	@Override
	public Socket getEventsocket() {
		return socket;
	}
	
	@Override
	public Event getEventType() {
		return this;
	}
	@Override
	public CommunicationThread getCom() {
		return com1;
	}
}
