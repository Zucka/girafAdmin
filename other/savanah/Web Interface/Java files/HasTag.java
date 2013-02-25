
public class HasTag {
	
	private int idMedia;
	private int idTag;
	
	public HasTag(int idMedia, int idTag){
		this.idMedia = idMedia;
		this.idTag = idTag;
	}
	
	public HasTag(int idTag){
		this.idTag = idTag;
	}
	
	public int getIdMedia(){
		return this.idMedia;
	}
	
	public int getIdTag(){
		return this.idTag;
	}
}
