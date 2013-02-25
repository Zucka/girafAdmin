public class Department {

	private int id;
	private String name;
	private String address;
	private int phone;
	private String email;

	public Department(int id, String name, String address, int phone,
			String email) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public int getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

}
