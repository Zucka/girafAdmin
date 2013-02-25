package dk.aau.cs.giraf.savannah.server;


import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
public class XMLBuilder {
	private ArrayList<File> files;;
	private String out;
	private StringBuilder sBuilder;
	private StringBuilder auth,prof,dept,hdept,hsdept,apps,loa,media,tags,htags,hlink,mda,mpa,hguard;
	
	public XMLBuilder() 
	{
		out = "";
		prof = new StringBuilder();
		auth = new StringBuilder();
		dept = new StringBuilder();
		hdept = new StringBuilder();
		hsdept = new StringBuilder();
		apps = new StringBuilder();
		loa = new StringBuilder();
		media = new StringBuilder();
		tags = new StringBuilder();
		htags = new StringBuilder();
		hlink = new StringBuilder();
		mda = new StringBuilder();
		mpa = new StringBuilder();
		hguard = new StringBuilder();
		files = new ArrayList<File>();
	}
	
	public void clearAll()
	{
		out = "";
		prof.delete(0, prof.length());
		auth.delete(0, prof.length());
		dept.delete(0, prof.length());
		hsdept.delete(0, prof.length());
		hdept.delete(0, prof.length());
		apps.delete(0, prof.length());
		loa.delete(0, prof.length());
		media.delete(0, prof.length());
		tags.delete(0, prof.length());
		htags.delete(0, prof.length());
		hlink.delete(0, prof.length());
		mda.delete(0, prof.length());
		mpa.delete(0, prof.length());
		hguard.delete(0, prof.length());
		files.clear();
	}
	
	
	protected ArrayList<File> getFiles()
	{
		return files;
	}
	
	public String build(ArrayList<ResultSet> rset) throws SQLException
	{
		clearAll();
		for(int i = 0;i < rset.size();i++)
		{	
			String str = "";
			ResultSet curSet = rset.get(i);
			//Build AuthUser:certificate,idUser,aRole,username,password.
			curSet.first();
			str = "<Entry action=\"read\">\n" +
				 	"<certificate type=\"string\">"+curSet.getString("certificate")+"</certificate>\n" +
				 	"<idUser type=\"int\">"+curSet.getInt("idUser")+"</idUser>\n" +
				 	"<aRole type=\"int\">"+curSet.getInt("aRole")+"</aRole>" +
				 	"<username type=\"string\">"+curSet.getString("username")+"</username>\n" +
				 	"<password type=\"string\">"+curSet.getString("password")+"</password>\n" +
			      "</Entry>\n";
			auth.append(str);
		
			//Build Profile: idProfile,firstname,surname,middlename,pRole,phone,picture,settings
			String picture = curSet.getString("picture");
			str = "<Entry action=\"read\">\n" +
					"<idProfile type=\"int\">"+curSet.getInt("idProfile")+"</idProfile>\n" +
					"<firstname type=\"string\">"+curSet.getString("firstname")+"</firstname>\n" +
					"<surname type=\"string\">"+curSet.getString("surname")+"</surname>\n" +
					"<middlename type=\"string\">"+curSet.getString("middlename")+"</middlename>\n" +
					"<pRole type=\"int\">"+curSet.getInt("pRole")+"</pRole>\n"+
					"<phone type=\"int\">"+curSet.getLong("phone")+"</phone>\n"+
					"<picture type=\"string\">"+picture+"</picture>\n"+
					"<settings type=\"string\">"+curSet.getString("settings")+"</settings>\n"+
				   "</Entry>\n";
			prof.append(str);
			if(!(picture.equals("null")))
			{
				files.add(new File(picture));
			}
			
			//Build Department: idDepartment,name,address,phone,email
			
			str ="<Entry action=\"read\">\n" +
			       "<idDepartment type=\"int\">"+curSet.getInt("idDepartment")+"</idDepartment>\n"+
				   "<name type=\"string\">"+curSet.getString("name")+"</name>\n"+
			       "<address type=\"string\">"+curSet.getString("address")+"</address>\n"+
				   "<phone type=\"int\">"+curSet.getLong("phone")+"</phone>\n"+
			       "<email type=\"string\">"+curSet.getString("email")+"</email>\n"+
			     "</Entry>\n";
			dept.append(str);
			
			
			//Build HasDepartment: idProfile,idDepartment
			//Get Next resultset first
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str ="<Entry action=\"read\">\n" +
			           "<idProfile type=\"int\">"+curSet.getInt("idProfile")+"</idProfile>\n"+
					   "<idDepartment type=\"int\">"+curSet.getInt("idDepartment")+"</idDepartment>\n"+
			         "</Entry>\n";
				hdept.append(str);
			}
			
			
			//Build HasSubDepartment: idDepartment,idSubdepartment
			//Get Next resultset first
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str ="<Entry action=\"read\">\n" +
			            "<idDepartment type=\"int\">"+curSet.getInt("idDepartment")+"</idDepartment>\n"+
						"<idSubDepartment type=\"int\">"+curSet.getInt("idSubDepartment")+"</idSubDepartment>\n"+
		             "</Entry>\n";
				hsdept.append(str);
			}
			//Build Apps: idApp,name,version,icon,package,activity
			//Get Next resultset
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				String icon = curSet.getString("icon");
				str ="<Entry action=\"read\">\n" +
					   "<idApp type=\"int\">"+curSet.getInt("idApp")+"</idApp>\n"+
					   "<name type=\"string\">"+curSet.getString("name")+"</name>\n"+
					   "<version type=\"string\">"+curSet.getString("version")+"</version>\n"+
					   "<icon type=\"string\">"+icon+"</icon>\n"+
					   "<package type=\"string\">"+curSet.getString("package")+"</package>\n"+
					   "<activity type=\"string\">"+curSet.getString("activity")+"</activity>\n"+
				     "</Entry>\n";
				apps.append(str);
				if(!(icon.equals("null")))
				{
					files.add(new File(icon));
				}
			//Build ListOfApss: idApps,idProfile,settings,stats.

				str ="<Entry action=\"read\">\n" +
						"<idApp type=\"int\">"+curSet.getInt("idApp")+"</idApp>\n"+
						"<idProfile type=\"int\">"+curSet.getInt("idProfile")+"</idProfile>\n"+
						"<settings type=\"string\">"+curSet.getString("settings")+"</settings>\n"+
						"<stats type=\"string\">"+curSet.getString("stats")+"</stats>\n"+
					 "</Entry>\n";
				loa.append(new File(str));
			}
			
			
			//Build Media: idMedia,mPath,name,mPublic,mType,ownerID
			//Get Next resultset
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str ="<Entry action=\"read\">\n" +
			           "<idMedia type=\"int\">"+curSet.getInt("idMedia")+"</idMedia>\n" +
					   "<mPath type=\"string\">"+curSet.getString("mPath")+"</mPath>\n" +
			           "<name type=\"string\">"+curSet.getString("name")+"</name>\n" +
					   "<mPublic type=\"int\">"+curSet.getBoolean("mPublic")+"</mPublic>\n" +
			           "<mType type=\"int\">"+curSet.getInt("mType")+"</mType>\n" +
					   "<ownerID type=\"int\">"+curSet.getInt("ownerID")+"</ownerID>\n" +
		             "</Entry>\n";
				files.add(new File(curSet.getString("mPath")));
				media.append(str);
			}
			//Build Tags: idTag,caption
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str ="<Entry action=\"read\">\n" +
					   "<idTags type=\"int\">"+curSet.getInt("idTags")+"</idTags>\n" +
					   "<caption type=\"string\">"+curSet.getString("caption")+"</caption>\n" +
				     "</Entry>\n";
				tags.append(str);
			}
			
			
			//Build HasTag: idMedia,idTag
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str ="<Entry action=\"read\">\n" +
			           "<idMedia type=\"int\">"+curSet.getInt("idMedia")+"</idMedia>\n" +
					   "<idTag type=\"int\">"+curSet.getInt("idTag")+"</idTag>\n" +
		             "</Entry>\n";
				htags.append(str);
			}
			
			//Build HasLink: idMedia,idSubMedia
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str ="<Entry action=\"read\">\n" +
						"<idMedia type=\"int\">"+curSet.getInt("idMedia")+"</idMedia>\n" +
						"<idSubMedia type=\"int\">"+curSet.getInt("idSubMedia")+"</idSubMedia>\n" +
					 "</Entry>\n";
				hlink.append(str);
			}
			
			//Build MediaDepartmentAccess: idDepartment,idMedia
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str="<Entry action=\"read\">\n" +
			          "<idDepartment type=\"int\">"+curSet.getInt("idDepartment")+"</idDepartment>\n"+
					  "<idMedia type=\"int\">"+curSet.getInt("idMedia")+"</idMedia>\n"+
		            "</Entry>\n";
				mda.append(str);
			}
			
//			//Build MediaProfileAcess: idProfile,idMedia
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str="<Entry action=\"read\">\n" +
			          "<idProfile type=\"int\">"+curSet.getInt("idProfile")+"</idProfile>\n"+
					  "<idMedia type=\"int\">"+curSet.getInt("idMedia")+"</idMedia>\n"+
		            "</Entry>\n";
				mpa.append(str);
			}
			
			//Build HasGuardian: idGuardian,idChild
			i++;
			curSet = rset.get(i);
			while(curSet.next())
			{
				str="<Entry action=\"read\">\n" +
			          "<idGuardian type=\"int\">"+curSet.getInt("idGuardian")+"</idGuardian>\n"+
					  "<idChild type=\"int\">"+curSet.getInt("idChild")+"</idChild>\n"+
		            "</Entry>\n";
				hguard.append(str);
			}
		}
		
		out = "<?xml version=\"1.0\"?>\n"+
			  "<sw6ml>\n"+
			  "<AuthUsers>"+auth.toString()+"</AuthUsers>\n"+
		      "<Profile>"+prof.toString()+"</Profile>\n"+
			  "<Department>"+dept.toString()+"</Department>\n"+
		      "<HasDepartment>"+hdept.toString()+"</HasDepartment>\n"+
			  "<HasSubDepartment>"+hsdept.toString()+"</HasSubDepartment>\n"+
			  "<Apps>"+apps.toString()+"</Apps>\n"+
			  "<ListOfApps>"+loa.toString()+"</ListOfApps>\n"+
			  "<Media>"+media.toString()+"</Media>\n"+
			  "<Tags>"+tags.toString()+"</Tags>\n"+
			  "<HasTags>"+htags.toString()+"</HasTags>\n"+
			  "<HasLink>"+hlink.toString()+"</HasLink>\n"+
			  "<MediaDepartmentAccess>"+mda.toString()+"</MediaDepartmentAccess>\n"+
			  "<MediaProfileAccess>"+mpa.toString()+"</MediaProfileAccess>\n"+
			  "<HasGuardian>"+hguard.toString()+"</HasGuardian>\n"+
			  "</sw6ml>";
		return out;
	}

}
