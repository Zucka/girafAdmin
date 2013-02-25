public class Profile {

	private int id;
	private String firstname;
	private String surname;
	private String middlename;
	private int role;
	private int phone;
	private String picture;
	private String username;
	private String password;
	private int profileMediaID;

	public Profile(int id, String firstname, String surname, String middlename,
			int role, int phone, String picture) {
		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.role = role;
		this.phone = phone;
		this.picture = picture;
	}
	
	public Profile(String firstname, String surname, String middlename, int profileMediaID)
	{
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.profileMediaID = profileMediaID;
	}
	
	public Profile(String firstname, String surname, String middlename, int profileMediaID, String picture)
	{
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.profileMediaID = profileMediaID;
		this.picture = picture;
	}
	
	

	public Profile(int id, String firstname, String surname, String middlename,
			int role, int phone, String picture, String username) {
		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.role = role;
		this.phone = phone;
		this.picture = picture;
		this.username = username;
	}

	public Profile(int id, String firstname, String surname, String middlename,
			int role, int phone, String picture, String password,
			String username) {
		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.role = role;
		this.phone = phone;
		this.picture = picture;
		this.username = username;
		this.password = password;
	}
	

	/*
	 * public String getUsername() { return this.username; }
	 */

	
	public int getProfileMediaID()
	{
		return this.profileMediaID;
	}
	
	public int getID() {
		return this.id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getPicture() {
		return this.picture;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getMiddlename() {
		if (this.middlename == null)
			return "";
		else
			return this.middlename;
	}

	public String getName() {
		if (middlename == null || middlename.equals("null"))
			return this.firstname + " " + this.surname;
		else
			return this.firstname + " " + this.middlename + " " + this.surname;

	}
}
