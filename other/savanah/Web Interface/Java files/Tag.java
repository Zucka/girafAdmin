
public class Tag {
	
	private int idMedia;
	private int idTag;
	private String caption;
	private boolean selected = false;
	public Tag(int idMedia, int idTag, String caption)
	{
		this.idMedia = idMedia;
		this.idTag = idTag;
		this.caption = caption;
	}
	public Tag(int idTag, String caption)
	{
		this.idTag = idTag;
		this.caption = caption;
	}
	
	public Tag(String caption)
	{
		this.caption = caption;
	}
	
	public int getIdMedia()
	{
		return idMedia;
	}
	
	public boolean getSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean value)
	{
		selected=value;
	}
	
	public int getID()
	{
		return this.idTag;
	}
	
	public String getCaption()
	{
		return this.caption;
	}

}
