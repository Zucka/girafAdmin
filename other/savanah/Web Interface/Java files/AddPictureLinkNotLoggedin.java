

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.catalina.Session;

/**
 * Servlet implementation class EditPictureAccess
 */
@WebServlet("/AddPictureLinkNotLoggedin")
public class AddPictureLinkNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Profile currentProfile;

	ArrayList<Media> publicMedia = new ArrayList<Media>();
	ArrayList<Media> currentMedia = new ArrayList<Media>();

	ArrayList<Media> publicSound = new ArrayList<Media>();
	ArrayList<Tag> tags = new ArrayList<Tag>();
	boolean search = false;
	String userID;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPictureLinkNotLoggedin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		userID = (String) session.getAttribute("PROFILEIDTOEDIT");


		String searchName = "";
		String searchTags[] = null;

		String message = (String) session.getAttribute("PICTURERESPONSE");
		session.removeAttribute("PICTURERESPONSE");

		publicMedia.clear();
		currentMedia.clear();
		publicSound.clear();



		if(request.getParameter("searchName")!=null)
			searchName = request.getParameter("searchName");

		if(request.getParameterValues("Tags") != null)
			searchTags = request.getParameterValues("Tags");

		if (request.getParameter("search") != null && (!searchName.equals("") || searchTags != null))
			search = true;
		else
			search = false;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT * from Profile where idProfile = "+userID+";");

			// displaying records
			while (rs.next()) {
				String picture = "";
				if (picture == null || picture.equals(""))
					picture =request.getContextPath()+ "/images/i.jpg";
				else
					picture = request.getContextPath()+ "/"+picture;

				currentProfile = new Profile(Integer.parseInt(userID), rs.getString("firstname"), rs.getString("surname"), rs.getString("middlename"), rs.getInt("prole"), (int)rs.getLong("phone"), picture);
			}


			if(!search)
			{
				Statement stmt1 = con.createStatement();
				ResultSet rs1 = stmt1
						.executeQuery("SELECT * from Media where mPublic = 1 and mType=0;");

				// displaying records
				while (rs1.next()) {
					int temp = rs1.getInt("mPublic");
					Boolean mPublic = false;
					if (temp == 1)
						mPublic = true;
					else
						mPublic = false;


					publicMedia.add(new Media(rs1.getInt("idMedia"), request.getContextPath()+rs1.getString("mPath"), rs1.getString("name"), mPublic, rs1.getInt("mType"), rs1.getInt("ownerID")));
				}
				Statement stmt0 = con.createStatement();
				ResultSet rs0 = stmt0
						.executeQuery("SELECT * from Media where mPublic = 1 and mType=1;");

				// displaying records
				while (rs0.next()) {
					int temp = rs0.getInt("mPublic");
					Boolean mPublic = false;
					if (temp == 1)
						mPublic = true;
					else
						mPublic = false;


					publicSound.add(new Media(rs0.getInt("idMedia"), request.getContextPath()+rs0.getString("mPath"), rs0.getString("name"), mPublic, rs0.getInt("mType"), rs0.getInt("ownerID")));
				}
			}
			else
			{
				if(!searchName.equals(""))
				{
					Statement stmt1 = con.createStatement();
					ResultSet rs1 = stmt1
							.executeQuery("select * from Media "+
									"where name like '%"+searchName+"%' or idMedia in (select idMedia from HasTag "+
									"where idTag in (select idTag from Tags "+
									"where caption like '%"+searchName+"%')) and mType=0;");					// displaying records
					while (rs1.next()) {
						int temp = rs1.getInt("mPublic");
						Boolean mPublic = false;
						if (temp == 1)
							mPublic = true;
						else
							mPublic = false;
						publicMedia.add(new Media(rs1.getInt("idMedia"), request.getContextPath()+rs1.getString("mPath"), rs1.getString("name"), mPublic, rs1.getInt("mType"), rs1.getInt("ownerID")));
					}
				}

				if(searchTags != null && searchTags.length > 0)
				{
					String sqlStatement = "";
					for (int i =0; i<searchTags.length; i++)
					{
						if (i == 0)
							sqlStatement = sqlStatement + "(select idMedia from HasTag where idTag = "+searchTags[i]+")";
						else
							sqlStatement = sqlStatement + " and (select idMedia from HasTag where idTag = "+searchTags[i]+")";
					}
					sqlStatement = sqlStatement+";";

					Statement stmt1 = con.createStatement();
					ResultSet rs1 = stmt1
							.executeQuery("Select * from Media where idMedia in "+ sqlStatement);

					// displaying records
					while (rs1.next()) {
						int temp = rs1.getInt("mPublic");
						Boolean mPublic = false;
						if (temp == 1)
							mPublic = true;
						else
							mPublic = false;
						publicMedia.add(new Media(rs1.getInt("idMedia"), request.getContextPath()+rs1.getString("mPath"), rs1.getString("name"), mPublic, rs1.getInt("mType"), rs1.getInt("ownerID")));
					}
				}
			}


			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2
					.executeQuery("select * from Media "+
							"where idMedia in (select idMedia from MediaProfileAccess "+
							"where idProfile = "+userID+") or ownerID = "+userID+" and mType=0;");

			// displaying records
			while (rs2.next()) {
				int temp = rs2.getInt("mPublic");
				Boolean mPublic = false;

				if (temp == 1)
					mPublic = true;
				else
					mPublic = false;

				currentMedia.add(new Media(rs2.getInt("idMedia"), request.getContextPath()+rs2.getString("mPath"), rs2.getString("name"), mPublic, rs2.getInt("mType"), rs2.getInt("ownerID")));
			}

			Statement stmt3 = con.createStatement();
			ResultSet rs3 = stmt3
					.executeQuery("SELECT * from Tags;");

			// displaying records
			while (rs3.next()) {
				tags.add(new Tag(rs3.getInt("idTags"), rs3.getString("caption")));
			}





		}
		catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.",e);
		} catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {

			}
		}




		out.println("<html>" +
				"<head>"
				+ "<title>Savannah 1.0</title>"
				+ "<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />"
				+ "<script src=\"javascript/popup.js\">"
				+ "</script>"
				+"<SCRIPT language = JavaScript>" +
				"function errorMessage()" +
				"{" +
				"alert(\"Bemærk: Der er en lille fejl i systemet, så billedet skal vælges på ny hvis du søger\");"+
				"}"+
				"function doSearch()" +
				"{" +
				"document.forms[\"SearchForm\"].submit();"+
				"}"+
				"function doSubmit()" +
				"{" +
				"document.forms[\"PicForm\"].submit();"+
				"}"+
				"</SCRIPT>"
				+ "</head>"
				+ "<body>"
				+ "<div id=\"mainBackground\">"
				+ "<center>"
				+ "<h2>Rediger billede</h2>"
				+ "</center>"
				+"<hr>");
		out.println("<div id=\"simple_wrapper\">");
		out.println("<div id=\"edit_wrapper\">");
		out.println("<center>Tilføj link til billede<hr>");
		out.println("<form method='POST' action='AddPictureLinkNotLoggedin' name='PicForm'>");
		if (message != null && !message.equals(""))
			out.println(message + "<br>");
		out.println("Bruger: <input type=text name='name' disabled=true value='"+currentProfile.getName()+"'>");
		out.println("<br>");
		out.println("<table>");
		out.println("<tr><td>");

		for (int i=0; i<currentMedia.size(); i++)
		{


			if (i % 4 == 0)
				out.println("</td></tr><tr><td>");

			out.println("<table>");

			out.println("<tr><td align=\"center\"><img src='"+currentMedia.get(i).getMPath()+"' width=50 height=50></td></tr><tr><td align=\"center\"><input type=radio name='selectedPic' value='"+currentMedia.get(i).getIdMedia()+"'>"+currentMedia.get(i).getName()+"</td></tr>");
			out.println("</table></td><td>");



		}

		//Lav if check om current er tom!

		int added = 0;
		for (int i=0; i<publicMedia.size(); i++)
		{
			boolean alreadyAdded = false;


			if (added > 1 && added % 4 == 0)
				out.println("</td></tr><tr><td>");

			for (int j = 0; j < currentMedia.size(); j++)
			{
				if (currentMedia.get(j).getIdMedia() == publicMedia.get(i).getIdMedia())
				{
					alreadyAdded = true;
					break;
				}
				else
					alreadyAdded = false;
			}
			if (!alreadyAdded)
			{
				added++;
				out.println("<table>");
				out.println("<tr><td align=\"center\"><img src='"+publicMedia.get(i).getMPath()+"' width=50 height=50></td></tr><tr><td align=\"center\"><input type=radio name='selectedPic' value='"+publicMedia.get(i).getIdMedia()+"'>"+publicMedia.get(i).getName()+"</td></tr>");
				out.println("</table></td><td>");
			}

		}





		out.println("</td></tr>" +
				"</table>");
		out.println("Link til lyd: ");
		out.println("<select name='selectedSound'>");
		out.println("<option value='-2'>Fjen alle link</option>"); 
		for (Media sound : publicSound) {
			out.println("<option  value='"+sound.getIdMedia()+"'>"+sound.getName()+"</option>");
		}
		out.println("<option value='-1'>Fjen alle link</option>"); 
		out.println("</select>");
		out.println("</form>");
		out.println("</center>");
		out.println("</div>");
		out.println("<div id=\"my_wrapper\">");
		out.println("<hr>");
		out.println("<center>");
		out.println("<input type=button value='Gem' onClick=\"doSubmit();\"> <input type=button value='Fortryd' onClick=\"window.location='TestDatabase'\"> <input type=button value='Søg' onclick=\"errorMessage(); popup('popUpDiv')\">");
		out.println("</div>"
				+"<hr>");
		out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> </div>");



		// "<input type='submit' value='Logout'>\n" + "</form>");
		out.println(""
				+ "<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpDiv\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpDiv')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center>"+
				"<h3>Søg:</h3>" +
				"<hr>" +
				"<form method='GET' action='AddPictureLinkNotLoggedin' name='SearchForm'>"+
				"<input type=hidden name='search' value='yes'>"+
				"<table>" +
				"<tr><td>Navn:</td><td><input type=text name='searchName'></td></tr></table>"
				+"Tags:"
				+"<div id='boxOfTags'>");
		out.println("<table><tr>");
		for (int i=0; i<tags.size();i++)
		{

			if(i % 3 ==0)
				out.println("</tr><tr>");

			out.println("<td><input type=checkbox name='Tags' value='"+tags.get(i).getID()+"'><br>"+tags.get(i).getCaption()+"</td>");
		}

		out.println("</td></tr></table></div><input type='button' onClick=\"doSearch();\" value='Søg'> <input type=button value='Fortryd' onclick=\"popup('popUpDiv')>"
				+"</form></div>");

		out.println("</body></html>");
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String pictureID = request.getParameter("selectedPic");
		String soundID = request.getParameter("selectedSound");
		HttpSession session = request.getSession();
		String hasBeenAdded = null;

		if (pictureID == null || pictureID.equals(""))
		{
			session.setAttribute("PICTURERESPONSE", "<font color='red'>Intet billede valgt</font>");
			response.sendRedirect("AddPictureLinkNotLoggedin");
			return;
		}

		boolean delete = false;
		boolean noSoundSelected = false;
		if (soundID.equals("-1"))
			delete = true;
		if (soundID.equals("-2"))
		{
			noSoundSelected = true;
			session.setAttribute("PICTURERESPONSE", "<font color='red'>Ingen ændreinger foretaget</font>");
		}




		if (!noSoundSelected)
		{
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(
						"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");




				if (delete)
				{


					PreparedStatement pst = con.prepareStatement("DELETE FROM HasLink WHERE idMedia="+pictureID+";");
					pst.executeUpdate();
					session.setAttribute("PICTURERESPONSE", "<font color='green'>Alle links slettet</font>");
					response.sendRedirect("AddPictureLinkNotLoggedin");
					return;

				}
				else
				{
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT * from HasLink where idMedia = " + pictureID + " and idSubMedia = " + soundID+";");

					// displaying records
					while (rs.next()) {
						hasBeenAdded = "" + rs.getInt("idMedia");
					}

					if (hasBeenAdded != null)
					{
						session.setAttribute("PICTURERESPONSE", "<font color='red'>Link findes allerede</font>");
						response.sendRedirect("AddPictureLinkNotLoggedin");
						hasBeenAdded = null;
						return;
					}

					PreparedStatement pst = con.prepareStatement("insert into HasLink values(?,?)");
					pst.setInt(1, Integer.parseInt(pictureID));
					pst.setInt(2, Integer.parseInt(soundID));


					pst.executeUpdate();
					session.setAttribute("PICTURERESPONSE", "<font color='green'>Billede og lyd linket</font>");
				}


			} catch (SQLException e) {
				throw new ServletException(e.getMessage()
						+ " Servlet Could not display records. ", e);
			} catch (ClassNotFoundException e) {
				throw new ServletException("JDBC Driver not found.", e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (SQLException e) {

				}
			}
		}



		response.sendRedirect("AddPictureLinkNotLoggedin");

	}

}
