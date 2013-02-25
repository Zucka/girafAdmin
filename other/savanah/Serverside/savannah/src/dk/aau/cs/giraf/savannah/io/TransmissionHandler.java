package dk.aau.cs.giraf.savannah.io;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.net.Socket;

import java.lang.IllegalStateException;

/**
 * When this object is constructed it calls a method that will begin to deconstruct the {@link java.io.InputStream} data.
 * <pre>
 * Format:
 * 	Ping:		TYPE[int]PING[size]="randomBytes"
 * 		int		= int, CRUD value
 * 		size	= int, size of the random bytes
 * 
 * 	Request:	TYPE[int1]MXML[length,int2]="xmlData"[ACCEPT]
 * 		int1	= int, CRUD value
 * 		length	= int, length of xmlData
 * 		int2	= boolean, any files comming ? False for Request
 * 
 * 	COMMIT:		TYPE[int1]MXML[length,int2]="xmlData"FILE[length2,name.ext,int3]="fileData"[ACCEPT]
 * 		int1	= int, CRUD value
 * 		length	= int, length of xmlData
 * 		int2	= boolean, any files comming ? False if last file.
 * 		length2	= long, size of file
 * 		name.ext	= String, name and extension of file
 * 		int3	= boolean, any files comming ?
 * </pre>
 * 
 * <pre>
 * 	TYPE[int]
 * 		- int = has 10 cell spaces allocated
 * 
 * 	MXML[length,int]
 * 		- length = has 10 cell spaces allocated
 * 		- int = has 1 cell space allocated
 * 
 * 	FILE[length,name.ext,int]
 * 		- length = has 19 cell spaces allocated
 * 		- name.ext = has 256 cell spaces allocated
 * 		- int = has 1 cell space allocated
 * </pre>
 * @author Thorbjørn Kvist Nielsen
 *
 */
public class TransmissionHandler {
	private List<File> files 	= null;
	private File folder			= null;
	private int bufferSize 		= -1;
	private boolean working 	= true;

	private CRUD cr 			= CRUD.ERROR;
	private String xml 			= "";
	private boolean iHaveFile	= false;
	private boolean accepted	= false;
	private int pingLength		= -1; 

	/**
	 * Constructs a TransmissionHandler from the specified arguments.
	 * @param socket - the {@link java.net.Socket} used to connect to the Server.
	 * @param folder - the folder used to stored received files.
	 * @throws FileNotFoundException - If the files received could not be written to the HDD.
	 * @throws IOException - If the internal {@link java.net.Socket} could not connect. 
	 * If the transmission cannot be receive from the Server.
	 * If the files received could not be written to the HDD.
	 * @throws IllegalStateException - If the data in the InputStream does not conform with the defined transmission format.
	 */
	public TransmissionHandler(Socket socket, String folder) throws FileNotFoundException, IOException, IllegalStateException {
		this.bufferSize = 4096;
		this.files = new ArrayList<File>();
		if (this.makeFolder(folder) == false) {
			throw new IllegalArgumentException("Could not find or create folder !");
		}
		this.deconstruct(new DataInputStream(socket.getInputStream()));
	}

	private final boolean makeFolder(String folderPath) {
		File dir = new File(folderPath);
		boolean success = false;
		try {
			if (dir.exists() == false) {
				success = dir.mkdir();
				if (success == false) {
					throw new IOException("folderPath: Could not create folder !");
				}
			}	else {
				this.folder = dir;
				success = true;
			}
		}	catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return success;
	}

	private final void deconstruct(InputStream is) throws FileNotFoundException, IOException {
		is = new DataInputStream(is);
		this.cr = this.CR(is);
		if (cr == CRUD.PING) {
			this.pingLength = this.PING(is);
		}
		else {
			this.xml = this.XML(is);

			if (iHaveFile == true) {
				while (this.working == true) {
					this.FILE(is);
				}
			}	else {
				this.accepted = this.ACCEPT(is);
			}
		}
	}

	private final XMLWrapper findXMLlength(byte[] buf) {
		String data = this.bytesToString(buf);

		Pattern expr = Pattern.compile("MXML\\[");
		Matcher match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find MXML length !");
		}
		String length = data.substring(match.end() + 1, match.end() + 10);

		expr = Pattern.compile(",[0-1]\\]");
		match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find MXML anyFiles !");
		}
		String anyFiles = data.substring(match.start() + 1, match.end() -1);
		boolean result = (anyFiles.contains("1") == true) ? true : false;

		return new XMLWrapper(Integer.parseInt(length), result);
	}
	private final FileWrapper findFilelength(byte[] buf) {
		String data = this.bytesToString(buf);

		Pattern expr = Pattern.compile("FILE\\[");
		Matcher match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find FILE length, FILE name");
		}
		String length = data.substring(match.end() + 1, match.end() + 19);
		String name = this.getfileName(data.substring(match.end() + 21, match.end() + 276));

		expr = Pattern.compile(",[0-1]\\]");
		match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find FILE moreFiles !");
		}
		boolean moreFiles = (Integer.parseInt(data.substring(match.start() + 1, match.end() -1)) == 1) ? true : false;

		return new FileWrapper(Long.parseLong(length), name, moreFiles);
	}
	private final String getfileName(String in) {
		String result = "";
		int index = -1;
		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) != ' ') {
				index = i;
				break;
			}
		}
		for (int i = index; i  < in.length(); i++) {
			result += in.charAt(i);
		}
		return result;
	}
	private final String bytesToString(byte[] buf) {
		StringBuilder sb = new StringBuilder();
		for (byte b : buf) {
			sb.append((char)b);
		}
		return sb.toString();
	}

	private final int PING(InputStream is) throws IOException {
		/*
			-------------------------
					PING
			-------------------------
			crudLength	= 4
			tag-stuff	= 8
			total		= 12
		 */
		is = new DataInputStream(is);
		byte[] buf = new byte[12];
		@SuppressWarnings("unused")
		int len = is.read(buf);
		String data = this.bytesToString(buf);

		Pattern expr = Pattern.compile("\\[[0-9]*\\]");
		Matcher match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find PING !");
		}
		int length = Integer.parseInt(data.substring(match.start() + 1, match.end() - 1));

		//Reading the random PING data
		buf = null;
		buf = new byte[length];
		len = is.read(buf);

		//Dumping the last char
		len = is.read();

		return length; 
	}
	private final CRUD CR(InputStream is) throws IOException {
		/*
			-------------------------
					CRUD
			-------------------------
			crudLength	= 1
			tag-stuff	= 6
			total		= 7
		 */
		is = new DataInputStream(is);
		byte[] buf = new byte[7];
		@SuppressWarnings("unused")
		int len = is.read(buf);
		String data = this.bytesToString(buf);

		Pattern expr = Pattern.compile("\\[[0-9]\\]");
		Matcher match = expr.matcher(data);
		if (match.find() == false) {
			throw new IllegalStateException("Could not find CRUD !");
		}
		//		String result = data.substring(match.start() + 1, match.end() - 1);

		switch(Integer.parseInt(data.substring(match.start() + 1, match.end() - 1))) {
		case 1:
			return CRUD.COMMIT;
		case 2:
			return CRUD.REQUEST;
		case 3:
			return CRUD.PING;
		default:
			return CRUD.ERROR;
		}
	}
	private final String XML(InputStream is) throws IOException {
		/*	-------------------------
					Data
			-------------------------
			dataLength	= 10
			anyFiles	= 1
			tag-stuff	= 9
			total		= 20
		 */
		byte[] buf = new byte[20];
		int len = is.read(buf);
		XMLWrapper wrap = this.findXMLlength(buf);
		StringBuilder sb = new StringBuilder();

		int bufCalc = wrap.getLength();
		while (bufCalc > 0) {
			if (bufCalc > this.bufferSize) {
				buf = new byte[this.bufferSize];
				len = is.read(buf);
				sb.append(this.bytesToString(buf));
				bufCalc -= len;
			}
			else {
				buf = new byte[bufCalc];
				len = is.read(buf);
				sb.append(this.bytesToString(buf));
				bufCalc = 0;
			}
		}
		//maybe dump ï¿½nï¿½ char.... for the "
		@SuppressWarnings("unused")
		int dump = is.read();

		this.iHaveFile = wrap.areThereFiles();
		return sb.toString();
	}
	private final void FILE(InputStream is) throws FileNotFoundException, IOException {
		/*
		 	-------------------------
					File
			-------------------------
			fileLength	= 19
			fileName	= 256
			moreFiles	= 1
			tag-stuff	= 11
			total		= 286
		 */
		byte[] buf = new byte[286];
		int len = is.read(buf);
		FileWrapper wrap = this.findFilelength(buf);

		File dst = new File(this.folder.getAbsolutePath() + File.separator + wrap.getName());
		if (dst.exists() == false) {
			boolean success = dst.createNewFile();
			if (success == false) {
				throw new IllegalArgumentException("Could not create File !");
			}
		}
		OutputStream os = new FileOutputStream(this.folder.getAbsolutePath() + File.separator + wrap.getName());

		long bufCalc = wrap.getLength();
		while (bufCalc > 0) {
			if (bufCalc > this.bufferSize) {
				buf = new byte[this.bufferSize];
				len = is.read(buf);
				os.write(buf);
				bufCalc -= len;
			}
			else {
				buf = new byte[(int)bufCalc];
				len = is.read(buf);
				os.write(buf);
				bufCalc = 0;
			}
		}
		//maybe dump ï¿½nï¿½ char.... for the "
		@SuppressWarnings("unused")
		int dump = is.read();

		this.working = wrap.getMoreFiles();
		this.files.add(dst);
		os.flush();
		os.close();
	}	
	private final boolean ACCEPT(InputStream is) throws IOException {
		/*
		 	-------------------------
					ACCEPT
			-------------------------
			keyword		= 6
			tag-stuff	= 8
			total		= 10
		 */
		byte[] buf = new byte[10];
		@SuppressWarnings("unused")
		int len = is.read(buf);
		String data = this.bytesToString(buf);

		Pattern expr = Pattern.compile("\\[ACCEPT\\]");
		Matcher match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find ACCEPT !");
		}
		//		String result = data.substring(match.start() + 1, match.end() -1);

		return (data.substring(match.start() + 1, match.end() -1).equals("ACCEPT") == true) ? true : false;
	}

	/**
	 * @return The amount of data used in the Ping. Returns -1 unless a Ping is received.
	 */
	public int getPING() {
		return this.pingLength;
	}

	/**
	 * @return The {@link dk.aau.cs.giraf.savannah.io.CRUD} value of the transmission.
	 */
	public CRUD getCRUD() {
		return this.cr;
	}

	/**
	 * @return The xml contained in the transmission. Returns an empty String, if a Ping is received.
	 */
	public String getXML() {
		return this.xml;
	}

	/**
	 * @return A list of {@link java.io.File} objects which were transfered. Returns an empty list, if a Ping is received.
	 */
	public List<File> getFILES() {
		return this.files;
	}

	/**
	 * @return True if any files were transfered, otherwise it returns false.
	 */
	public boolean getAnyFiles() {
		return !this.files.isEmpty();
	}

	/**
	 * @return True, if a transmission is received. Returns false if a Ping is received.
	 */
	public boolean getAccepted() {
		return this.accepted;
	}

	class XMLWrapper {
		private int _length;
		private boolean _anyFiles;

		XMLWrapper(int length, boolean anyFiles) {
			this._length = length;
			this._anyFiles = anyFiles;
		}

		public int getLength() {
			return this._length;
		}
		public boolean areThereFiles() {
			return this._anyFiles;
		}
	}
	class FileWrapper {
		private long _length;
		private String _name;
		private boolean _moreFiles;

		FileWrapper(long length, String name, boolean moreFiles) {
			this._length = length;
			this._name = name;
			this._moreFiles = moreFiles;
		}

		public long getLength() {
			return this._length;
		}
		public String getName() {
			return this._name;
		}
		public boolean getMoreFiles() {
			return this._moreFiles;
		}
	}

}
