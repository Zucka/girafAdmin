public class App {

	private int id;
	private String name;
	private String version;

	public App(int id, String name, String version) {
		this.id = id;
		this.name = name;
		this.version = version;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getVersion() {
		return this.version;
	}

}
