

import java.io.File;
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

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class ConfirmDeletePicture
 */
@WebServlet("/ConfirmDeletePicture")
public class ConfirmDeletePicture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArrayList<Profile> otherUsers = new ArrayList<Profile>();
	Media media = null;
	String path;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConfirmDeletePicture() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		PrintWriter out = response.getWriter();
		String mediaId = request.getParameter("myId");
		otherUsers.clear();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			Statement stmt0 = con.createStatement();
			ResultSet rs0 = stmt0
					.executeQuery("select * from Media where idMedia = '"
							+ mediaId + "';");
			while (rs0.next()) {
				media = new Media(rs0.getInt("idMedia"), rs0.getString("mPath"), rs0.getString("name"), rs0.getBoolean("mPublic"), rs0.getInt("mType"), rs0.getInt("ownerID"));
			}
			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2
					.executeQuery("select * from Profile "
							+"where idProfile in (select idProfile from MediaProfileAccess "
							+"where idMedia = "+media.getIdMedia()+");");
			while (rs2.next()) {
				String picture = rs2.getString("picture");

				if (picture == null || picture.equals("null"))
					picture = "/images/i.jpg";
				if (!rs2.getString("idProfile").equals(media.getOwnerID()))
					otherUsers.add(new Profile(rs2.getString("firstname"), rs2.getString("surname"), rs2.getString("middlename"), media.getIdMedia(), picture));
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

		path = getServletContext().getRealPath(media.getMPath()); 


		out.println("<html>");
		out.println("<head>");
		out.println("<title>Savannah 1.0</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");
		out.println("<script src=\"javascript/popup.js\"></script>");
		out.println("</head>");
		out.println("<body>");


		out.println("<div id=\"mainBackground\">");
		out.println("<center><h2> Er du sikker på du vil slette:</h2>");
		out.println("<hr>");
		out.println("<div id=\"simple_wrapper\">");
		out.println("<div id=\"edit_wrapper\">");
		out.println("<center>");
		out.println("<img src='"+request.getContextPath()+media.getMPath()+"' width=100 height=100>");
		out.println("<br>");
		out.println(media.getName());
		out.println("<br>");
		out.println("Dette vil påvirke følgende andre brugere:");
		out.println("<table>");
		for (Profile p : otherUsers) {
			out.println("<tr><td><img src='"+request.getContextPath()+p.getPicture()+"' width=50 heigth=50></td><td>"+p.getFirstname()+ " " + p.getMiddlename() + " " + p.getSurname()+"</td></tr>");
		}
		out.println("</table>");

		out.println("</center>");
		out.println("</div>");
		out.println("<div id=\"my_wrapper\">");
		out.println("<hr>");
		out.println("<center>");
		out.println("<form method='POST' action='ConfirmDeletePicture' name='confirmDel'>");
		out.println("<input type='submit' value='Slet'><input type='button' value='Fortryd'>");
		out.println("</form>");
		out.println("</center>");
		out.println("</div>");


		out.println("</div>");
		out.println("<hr>");
		out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> " +
				"</div>");

		out.println("</body>");
		out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		try
		{
			File deleteFile = new File(path);  
			deleteFile.delete();
		}
		catch (Exception e) {
			session.setAttribute("SYSTEMMESSAGE", "<font color='red'>Der skete en fejl under sletning af billedet på hard disken</font>");
			response.sendRedirect("TestDatabase");
			return;
		}

		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
			Statement pst=con.createStatement();

			pst.execute("DELETE FROM Media WHERE idMedia="+media.getIdMedia()+";");
			session.setAttribute("SYSTEMMESSAGE", "<font color='green'>Billedet slettet</font>");
			response.sendRedirect("TestDatabase");
			return;

		}
		catch(Exception e){
			session.setAttribute("SYSTEMMESSAGE", "<font color='red'>Uhåndteret fejl: "+e.getMessage()+"</font>");
			response.sendRedirect("TestDatabase");
		}
	}




}



