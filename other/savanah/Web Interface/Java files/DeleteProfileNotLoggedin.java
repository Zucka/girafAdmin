

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
 * Servlet implementation class DeleteProfileNotLoggedin
 */
@WebServlet(
	    name = "DeleteProfileNotLoggedin", 
	    urlPatterns = {"/DeleteProfileNotLoggedin"}
	)
public class DeleteProfileNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProfileNotLoggedin() {
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
		
		ArrayList<Profile> profilelist = new ArrayList<Profile>();
		//this line will get the PROFILEIDTODELETE that was set in SelectProfileToDelete and puts it in userID for use in the delete query
		String userID = (String) session.getAttribute("PROFILEIDTODELETE");
		//session.removeAttribute("PROFILEIDTODELETE");
		//out.println(userID);
		
		//establishing a connection
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");

			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from Profile where idProfile = "+userID+";");
			// Creates profiles, retrieves the fields from the database and put them into a list
			//Not all fields are used to display the profile so maybe not necessary to include all fields
			while(rs.next()){
				int idProfile = rs.getInt("idProfile");
				String firstname = rs.getString("firstname");
				String surname = rs.getString("surname");
				String middlename = rs.getString("middlename");
				int pRole = rs.getInt("pRole");
				int phone = rs.getInt("phone");
				String picture = rs.getString("picture");
				Profile p = new Profile(idProfile, firstname, surname, middlename, pRole, phone, picture);
				profilelist.add(p);
			}

		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		} 	catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally 
		{
			try 
			{
				if(rs != null) 
				{
					rs.close();
					rs = null;
				}
				if(stmt != null) 
				{
					stmt.close();
					stmt = null;
				}
				if(con != null) 
				{
					con.close();
					con = null;
				}
			} 
			catch (SQLException e) 
			{

			}
		}
		
		out.println("<html>" +
					"<head>" +
					"<title>Savannah 1.0 - Slet profil</title>" +
					//implements stylesheet
					"<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />" +
					"<script src=\"javascript/popup.js\"></script>" +
					"</head>" +
					"<body onLoad=\"getFocusQuick();\">" +
					"<div id=\"mainBackground\">" +
					"<script type='text/javascript'>"+
						//used to delete profiles from the database
						"function deleteContent() {" +
						"document.DasForm.submit();" +
						"}" +
						"function submitform()"+
						"{"+
						"document.DasForm.submit();"+
						"}"+
						"function ChangeColor(tableRow, highLight)"+
						"{"+
						"if (highLight)"+
						"{"+
						"tableRow.style.backgroundColor = '#dcfac9';"+
						"}"+
						"else"+
						"{"+
						"tableRow.style.backgroundColor = '#00CCFA';"+
						"}"+
						"}"+
						"function setID(id)"+
						"{"+
						"document.DasForm.myId.value = id;"+
						"}"+
						"</script>");
				out.println("<body>");
				out.println("<center><h2>Vær opmærksom på at alle billeder som er ejet af denne profil også vil blive slettet. Er du sikker på at du vil slette: </h2>");
				out.println("<br>");
				out.println("<hr>");
				out.println("<form method='POST' name='DasForm' action='DeleteProfileNotLoggedin'>");
				out.println("<table borders='0'>");
				// The list is unnecessary because it only contains 1 element
				//FIXME
				for (Profile p : profilelist){
				
					out.println("<tr onmouseover=\"ChangeColor(this, true);\" onmouseout=\"ChangeColor(this, false);\"" + 
					"onclick=\"setID('"+p.getID()+"'); submitform();\">");
					out.println("<td><img src='"+p.getPicture()+"' width=50 height=50></td><td>" + p.getID() + "</td><td>"+p.getName()+"</td>");
					out.println("</tr>");
				}
				//creates two buttons, one to delete that utilizes deleteContent() and one that takes you back to the selection screen
				out.println("<tr><td></td><td><input type='button' onClick=\"deleteContent();\" value='Slet'/></td><td><input type='button' onclick=\"javascript:window.location = \'TestDatabase\';\" value='Fortryd'/></td></tr>");
				out.println("</table>");
				//out.println("<hr>");
				out.println("</form>");
				out.println("<hr>");
				out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String userID = (String) session.getAttribute("PROFILEIDTODELETE");
		ArrayList<Media> medialist = new ArrayList<Media>();
		//out.println(userID);
		
		//creating a connection
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String path = null;
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			//connects to our server with user: eder and password: 123456
			con = DriverManager.getConnection("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("select mPath from Media where ownerID = " + userID + ";");
			
			
			while(rs.next()){
				String mPath = rs.getString("mPath");
				Media m = new Media(mPath);
				medialist.add(m);
			}
			
			for(Media m : medialist){
				path = getServletContext().getRealPath(m.getMPath()); 
				File deletePicture = new File(path);
				if(deletePicture.exists()){
					deletePicture.delete();
				}
				//out.println("Debug");
				//out.println(m.getMPath());
			}
			
			//This line executes the query to delete from Authusers table where idUser matches the userID that we get from the selected profile	
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("delete from AuthUsers where idUser = " + userID +";");
			int i = ps.executeUpdate();
			//this if-statement is not working properly 
			if (i == 0){
				out.println(userID + " has been deleted");
				
			}
			else{
				out.println(userID + " has been deleted");
				//response.sendRedirect("SelectProfileToDelete");
				//out.println(i);
			}
		}
		catch(Exception e){
			out.println("The execution is " + e);
		}
		finally
		{
			try
			{
				if (rs != null) 
				{
					rs.close();
					rs = null;
				}
				if (stmt != null) 
				{
					stmt.close();
					stmt = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (SQLException e) 
			{
				out.println("The execution is " + e);
			}
			
			session.setAttribute("SYSTEMMESSAGE", "<font color='green'>Profilen blev slettet korrekt</font>");
			response.sendRedirect("TestDatabase");
		}
	}
}
