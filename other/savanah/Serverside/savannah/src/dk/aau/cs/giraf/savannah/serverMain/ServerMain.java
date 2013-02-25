package dk.aau.cs.giraf.savannah.serverMain;

import dk.aau.cs.giraf.savannah.io.IOHandler;
import dk.aau.cs.giraf.savannah.server.*;

public class ServerMain {
	
	public static void main(String[] args) {
		Thread t1 = new Thread(IOHandler.getInstance());
		Thread t2 = new Thread(new EventHandler());
		t1.start();
		t2.start();
	}

}
