package dk.aau.cs.giraf.savannah.io;

/**
 * This enum represents the different actions that can be contained in a 
 * transmission to the Server.
 * <pre>
 * 	COMMIT(1, "COMMIT")		- Commit Event
 * 	REQUEST(2, "REQUEST")	- Request Event
 * 	PING(3, "PING")			- Ping (Event)
 * 	ERROR(4, "ERROR")		- Error, in case something goes wrong, then this message is sent.
 * </pre>
 * @author Thorbjørn Kvist Nielsen
 *
 */
public enum CRUD {
	COMMIT(1, "COMMIT"), REQUEST(2, "REQUEST"), PING(3, "PING"), ERROR(4, "ERROR");
	
	private int value;
	private String toString;
	
	private CRUD(int value, String toString) {
		this.value = value;
		this.toString = toString;
	}
	
	/**
	 * @return The integer value of the CRUD.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * @return The String representation of the CRUD's integer.
	 */
	public String getValueString() {
		return Integer.toString(this.value);
	}
	
	/**
	 * @return The String representation of the CRUD.
	 */
	public String toString() {
		return this.toString;
	}
}
