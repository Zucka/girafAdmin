

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.tomcat.util.buf.UDecoder;

/**
 * Servlet implementation class DeletePictureNotLoggedin
 */
@WebServlet("/DeletePictureNotLoggedin")
public class DeletePictureNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<Media> medias = new ArrayList<Media>();
	ArrayList<Tag> tags = new ArrayList<Tag>();
	ArrayList<Profile> otherUsers = new ArrayList<Profile>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeletePictureNotLoggedin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String userID = (String) session.getAttribute("PROFILEIDTODELETEPIC");

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			Statement stmt0 = con.createStatement();
			ResultSet rs0 = stmt0
					.executeQuery("select * from Media where ownerID = '"
							+ userID + "';");
			while (rs0.next()) {
				boolean mPublic;
				int temp = rs0.getInt("mPublic");
				if (temp == 0)
					mPublic = false;
				else
					mPublic = true;
				medias.add(new Media(rs0.getInt("idMedia"), rs0.getString("mPath"), rs0.getString("name"),mPublic,rs0.getInt("mType"), rs0.getInt("ownerID")));
			}

			for (Media m : medias) {
				Statement stmt1 = con.createStatement();
				ResultSet rs1 = stmt1
						.executeQuery("select idTags, caption from Tags "+
								"where idTags in (select idTag from HasTag " + 
								"where idMedia ="+m.getIdMedia()+");");
				while (rs1.next()) {
					tags.add(new Tag(m.getIdMedia(), rs1.getInt("idTags"), rs1.getString("caption")));
				}

				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2
						.executeQuery("select * from Profile "
								+"where idProfile in (select idProfile from MediaProfileAccess "
								+"where idMedia = "+m.getIdMedia()+");");
				while (rs2.next()) {
					otherUsers.add(new Profile(rs2.getString("firstname"), rs2.getString("surname"), rs2.getString("middlename"), m.getIdMedia()));
				}



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


		out.println("<html>" +
				"<head>" +
				"<script type='text/javascript'>"
				+"var open = 0;"
				+ "function setOpen(number)"
				+ "{"
				+ "open = number;"
				+ "document.DasForm.jsOpenVar.value = number;"
				+ "}"
				+ "function ChangeColor(tableRow, highLight)"
				+ "{"
				+ "if (highLight)"
				+ "{"
				+ "tableRow.style.backgroundColor = '#dcfac9';"
				+ "}"
				+ "else"
				+ "{"
				+ "tableRow.style.backgroundColor = '#00CCFA';"
				+ "}"
				+ "}"
				+ "function setID(id)"
				+ "{"
				+ "document.DasForm.myId.value = id;"
				+ "}"
				+ "function submitform()"
				+ "{"
				+ "document.DasForm.submit();"
				+ "}"
				+"</script>"+
				"<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />"+
				"</head>" +
				"<body>"+
				"<div id =\"MainBackground\">" +
				"<center><h2>Slet billede</h2></center>" +
				"<hr>"+
				"<div id=\"simple_wrapper\">"+
				"<div id=\"edit_wrapper\">"+
				"<center>" +
				"<table bordeR=0>" +
				"<th> Billede </th>" +
				"<th> ID </th>" +
				"<th> Tags </th>" +
				"<th> Andre brugere af billdet:</th>");
		for (Media m : medias) {
			String currentTags = "";
			String otherProfiles = "";
			for (Tag t : tags) {
				if (m.getIdMedia() == t.getIdMedia())
					currentTags = currentTags + "<"+t.getCaption()+">";
			}
			for (Profile p : otherUsers) {
				if (m.getIdMedia() == p.getProfileMediaID())
					otherProfiles = otherProfiles + p.getFirstname() + " " + p.getSurname() + ",\n";
			}
			if (otherProfiles.length() > 2)
			{
				otherProfiles = otherProfiles.substring(0, otherProfiles.length()-2);
				otherProfiles = otherProfiles + ".";
			}
			out.println("<tr onmouseover=\"ChangeColor(this, true);\" onmouseout=\"ChangeColor(this, false);\" onclick=\"setID('"+ m.getIdMedia()+"'); submitform();\"><td><img src='"+request.getContextPath()+m.getMPath()+"' width=50 height=50></td><td>" + m.getIdMedia() + "</td><td><textarea name=\"tags\" cols=\"20\" rows=\"3\">"+currentTags+"</textarea></td><td><textarea name=\"others\" cols=\"20\" rows=\"3\">"+otherProfiles+"</textarea></td></tr>");

		}
		out.println("</table>");

		out.println("<form method='GET' action='ConfirmDeletePicture' name='DasForm'>"
				+ "<input type='hidden' name='myId' value=''><input type='hidden' name='userId' value=''></form>");

		out.println("</center></div>"
				+"<div id=\"my_wrapper\">" +
				"<center>"+
				"<a href=\"TestDatabase\">Tilbage</a></center>" +
				"</div>" +
				"</div>" +
				"<hr>" +
				"<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> " +
				"</div>" +
				"</body>" +
				"</html>");
		
		otherUsers.clear();
		medias.clear();
		tags.clear();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
