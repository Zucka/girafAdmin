package dk.aau.cs.giraf.savannah.io;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.Socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by the Server to create messages in the Logfile.
 * This is a very crude implementation, however it works.
 * @author Thorbjørn Kvist Nielsen
 *
 */
public class LogFile {
	//Field variables
	private String path	= "";

	/**
	 * Constructs a LogFile object from the specified arguments.
	 * @param filePath - the path of the file used as the logfile.
	 */
	public LogFile(String filePath) {
		this.path = filePath;
	}

	
	/**
	 * Creates an entry in the logfile with the following format:
	 * <pre>
	 *	"[dd-MM-yyyy - HH:mm:ss]\t Connecting from: %s%n", socket
	 *	"\t Performed Action: %s%n", performedAction
	 *	"\t Completed: %s%n%n", completed
	 *	"%n"
	 * </pre>
	 * @param socket - the {@link java.net.Socket} to log
	 * @param performedAction - the CRUD action performed
	 * @param completed - did the {@link dk.aau.cs.giraf.savannah.server.Event} fail or succeed
	 */
	public synchronized void makeLogEntry(Socket socket, String performedAction, boolean completed) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\t Performed Action: %s%n", performedAction);
			print.printf("\t Completed: %s%n%n", complete);
			
			//Flushing
			print.flush();
			write.flush();
			//Closing
			print.close();
			write.close();
			
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
	}
	
	/**
	 * Creates an entry in the logfile with the following format:
	 * <pre>
	 *	"[dd-MM-yyyy - HH:mm:ss]\t Connecting from: %s%n", socket
	 *	"\t Performed Action: %s%n", performedAction
	 *	"\t Completed: %s%n%n", completed
	 *	"\t\t Path: %s\t---\tName: %s\t---\t Size: %d bytes%n", files[i].getAbsolutePath(), files[i].getName(), files[i].length()
	 *	"%n"
	 * </pre>
	 * @param socket - the {@link java.net.Socket} to log
	 * @param performedAction - the CRUD action performed
	 * @param completed - did the {@link dk.aau.cs.giraf.savannah.server.Event} fail or succeed
	 * @param files - the {@link java.io.File} objects to log
	 */
	public synchronized void makeLogEntry(Socket socket, String performedAction, boolean completed, List<File> files) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\tPerformed Action: %s%n", performedAction);
			print.printf("\tCompleted: %s%n", complete);
			
			//Flushing
			print.flush();
			write.flush();

		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
		
		for (File f: files) {
			try {
				//Writing messages to the log file
				print.printf("\t\tPath: %s\t---\tName: %s\t---\tSize: %d bytes%n", f.getAbsolutePath(), f.getName(), f.length());
				
				//Flushing
				print.flush();
				write.flush();
				
			}	catch (IOException e) {
				System.err.println("Could not find file: " + this.path + " !");
			}
		}
		
		try {
			print.print("%n");
			
			print.flush();
			write.flush();
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
		
		try {
			//Closing
			print.close();
			write.close();
			
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
	}
	
	/**
	 * Creates an entry in the logfile with the following format:
	 * <pre>
	 *	"[dd-MM-yyyy - HH:mm:ss]\t Connecting from: %s%n", socket
	 *	"\t Performed Action: %s%n", performedAction
	 *	"\t Completed: %s%n%n", completed
	 *	"\t\t Path: %s\t---\tName: %s\t---\t Size: %d bytes%n", files[i].getAbsolutePath(), files[i].getName(), files[i].length()
	 *	"%n"
	 * </pre>
	 * @param socket - the {@link java.net.Socket} to log
	 * @param performedAction - the CRUD action performed
	 * @param completed - did the {@link dk.aau.cs.giraf.savannah.server.Event} fail or succeed
	 * @param files - the {@link java.io.File} objects to log
	 */
	public synchronized void makeLogEntry(Socket socket, String performedAction, boolean completed, ArrayList<File> files) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\tPerformed Action: %s%n", performedAction);
			print.printf("\tCompleted: %s%n", complete);
			
			//Flushing
			print.flush();
			write.flush();

		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
		
		for (File f: files) {
			try {
				//Writing messages to the log file
				print.printf("\t\tPath: %s\t---\tName: %s\t---\tSize: %d bytes%n", f.getAbsolutePath(), f.getName(), f.length());
				
				//Flushing
				print.flush();
				write.flush();
				
			}	catch (IOException e) {
				System.err.println("Could not find file: " + this.path + " !");
			}
		}
		
		try {
			print.print("%n");
			
			print.flush();
			write.flush();
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
		
		try {
			//Closing
			print.close();
			write.close();
			
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
	}

}
