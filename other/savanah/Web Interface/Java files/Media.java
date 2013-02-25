
public class Media {
	
	private int idMedia;
	private String mPath;
	private String name;
	private boolean mPublic;
	private int mType;
	private int ownerID;
	
	
	public Media(int idMedia, String mPath, String name, boolean mPublic, int mType, int ownerID){
		this.idMedia = idMedia;
		this.mPath = mPath;
		this.name = name;
		this.mPublic = mPublic;
		this.mType = mType;
		this.ownerID = ownerID;
	}
	
	public Media(String mPath){
		this.mPath = mPath;
	}
	
	public int getIdMedia(){
		return this.idMedia;
	}
	
	public String getMPath(){
		return this.mPath;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean getMPublic(){
		return this.mPublic;
	}
	
	public int getMType(){
		return this.mType;
	}
	
	public int getOwnerID(){
		return this.ownerID;
	}

}
