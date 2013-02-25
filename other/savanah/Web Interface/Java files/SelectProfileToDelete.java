

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

/**
 * Servlet implementation class SelectProfileToDelete
 */
@WebServlet(
	    name = "SelectProfileToDelete", 
	    urlPatterns = {"/SelectProfileToDelete"}
	)
public class SelectProfileToDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	int id;
	String name;
	String guardian;
	String parent;
	String username;


	ArrayList<Profile> profiles = new ArrayList<Profile>();
	ArrayList<Profile> guardians = new ArrayList<Profile>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectProfileToDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String context = request.getContextPath();
			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			profiles.clear();

			guardians.clear();

			String loginID = request.getParameter("myId");


			//Check if not number in id
			if (loginID != null)
			{
				try {
					Integer.parseInt(request.getParameter("myId"));
	

				} catch (NumberFormatException e) {
					// TODO: handle exception
					loginID = null;
					session.setAttribute("SelectProfileERROR","Fejl id id (Kun numre er gyldige)");


				}
			}

			String triedLogin = request.getParameter("triedLogin");
			String guardianID = request.getParameter("guardianID");
			String password1 = request.getParameter("password1");
			//String username1 = request.getParameter("username");
			Profile originalProfile;
			//String originalUsername = request.getParameter("username");
			String openVar = request.getParameter("jsOpenVar");
			if (openVar == null)
				openVar = "0";




			Connection con = null;  
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con =DriverManager.getConnection 
						("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT idUser, username, picture, idProfile, firstname, middlename, surname from AuthUsers, Profile " +
						"where idUser = idProfile;");
				// displaying records
				while(rs.next()){
					id = rs.getInt("idUser");
					name = rs.getString("firstname") + " " +rs.getString("middlename") + " " + rs.getString("surname");
					username = rs.getString("username");
					
					String picture = rs.getString("picture");

					if (picture == null || picture.equals("null"))
						picture = context + "/images/i.jpg";
					else
						picture = context + "/"+picture;

					Profile p = new Profile(rs.getInt("idProfile"),rs.getString("firstname"),rs.getString("surname"), rs.getString("middlename"),  1, -1, picture, username);

					profiles.add(p);
				}

				if (loginID != null)
				{

					stmt = con.createStatement();
					rs = stmt.executeQuery("select firstname from Profile "+
							"where idProfile = " + loginID + ";");

					String user = null;
					// displaying records
					while(rs.next()){
						user = rs.getString("firstname");
					}

					if(user == null)
					{
						session.setAttribute("SelectProfileERROR", "Ugyldigt id");
						response.sendRedirect("SelectProfileToDelete");
						return;
					}
					else
					{
						//sets the attribute PROFILEIDTODELETE that is used in DeleteProfileNotLoggedin to delete on the ID
						session.setAttribute("PROFILEIDTODELETE", loginID);
						//redirects to the DeleteProfilNotLoggedin page
						response.sendRedirect("DeleteProfileNotLoggedin");
					}
					
				}
				else
				{
					stmt = con.createStatement();
					rs = stmt.executeQuery("select * from Profile, AuthUsers "+
							"where idProfile = "+loginID+";");
					originalProfile = null;
					while(rs.next()){
						originalProfile = new Profile(rs.getInt("idProfile"), rs.getString("firstname"),rs.getString("surname"), rs.getString("middlename"),1,-1, null, rs.getString("password"), rs.getString("username"));
					}
					rs = stmt.executeQuery("select * from Profile "+
							"where idProfile in (select idGuardian from HasGuardian " +
							"where idChild = "+loginID+");");
					guardians.add(originalProfile);
					// displaying records
					while(rs.next()){
						id = rs.getInt("idProfile");
						name = rs.getString("firstname") + " " +rs.getString("middlename") + " " + rs.getString("surname");
						Profile g = new Profile(rs.getInt("idProfile"), rs.getString("firstname"),rs.getString("surname"), rs.getString("middlename"),1,-1, null, username);
						guardians.add(g);
					}
				}



			} catch (SQLException e) {
				throw new ServletException("Servlet Could not display records. id=" + guardianID, e);
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


			out.println("<html>");
			out.println("<head>");
			out.println("<title>Savannah 1.0</title>");
			out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");
			out.println("<script src=\"javascript/popup.js\"></script>");
			out.println("</head>");
			
			out.println("<body onLoad=\"getFocusQuick();\">");

			//}	

			out.println("<script type='text/javascript'>"+
					"var open = 0;"+
					"function setOpen(number)"+
					"{"+
					"open = number;"+
					"document.DasForm.jsOpenVar.value = number;"+
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
					"function DoNav(theUrl)"+
					"{"+
					"document.location.href = theUrl;"+
					"}"+
					"function setID(id)"+
					"{"+
					"document.DasForm.myId.value = id;"+
					"}"+
					"function setLogin()"+
					"{"+
					"document.DasForm.triedLogin.value = 1;"+
					"}"+
					"function clearLogin()"+
					"{"+
					"document.DasForm.triedLogin.value = 0;"+
					"}"+
					"function submitform()"+
					"{"+
					"document.DasForm.submit();"+
					"}"+
					"function clearForm()" +
					"{"+
					"document.DasForm.password1.value= \"\";"+
					"}"+
					"function getFocus()" +
					"{"+
					"document.DasForm.password1.focus();" +
					"}"+
					"function getFocusQuick()" +
					"{"+
					"document.quickForm.quickSelect.focus();" +
					"}"+
					"document.onkeydown = function(e) {"+
					" e = e || window.event;" +
					"var keyCode = e.keyCode || e.which;" +
					//If in login and esc is pressed
					"if(keyCode == 27 && open == 1) {setOpen(0); popup('popUpDiv');  clearForm(); getFocusQuick();}" +
					//If no active screen and backspace is pressed - go back
					"if(keyCode == 8 && document.quickForm.quickSelect.value == '') {window.history.go(-1);}" +
					"}"+
					"</script>");
			out.println("<div id=\"mainBackground\">");
			out.println("<center><h2> Vælg profil til at slette:</h2>");
			out.println("<hr>");
			out.println("<div id=\"simple_wrapper\">"+
			"<div id=\"edit_wrapper\">");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Billede</th>");
			out.println("<th>ID</th>");
			out.println("<th>Navn</th>");
			out.println("</tr>");
			String numberScript = "";
			for (Profile p : profiles) 
			{

				out.println("<tr onmouseover=\"ChangeColor(this, true);\" onmouseout=\"ChangeColor(this, false);\"" + 
						"onclick=\"setID('"+p.getID()+"'); submitform();\">");
				out.println("<td><img src='"+p.getPicture()+"' width=50 height=50></td><td>" + p.getID() + "</td><td>"+p.getName()+"</td>");//<td>"+p.getGuardian()+"</td><td>"+p.getParent()+"</td>");
				out.println("</tr>");

			}
			out.println("</table>");
			out.println("</div>"+
			"<div id=\"my_wrapper\">");
			out.println("<hr>");
			
			out.println("<center>");
			out.println("<form method='POST' action='SelectProfileToDelete' name='quickForm'>");
			out.println("<input type='text' name='quickSelect' onkeypress='if (window.event.keyCode == 13) {setLogin(); setID(document.quickForm.quickSelect.value); submitform();}'>");
			out.println("</form>");
			if (session.getAttribute("SelectProfileERROR") != null)
			{
				out.println("<br>");
				out.println(session.getAttribute("SelectProfileERROR"));
				out.println("<br>");
				session.removeAttribute("SelectProfileERROR");
			}
			out.println("</center>");
			out.println("<p>");
			out.println("</div>	</div>");
			//out.println("<hr>");
			out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer>");
			

			out.println("<form method='POST' action='SelectProfileToDelete' name='DasForm'>\n" +
					"<input type='hidden' name='myId' value=''>"+
					"<input type='hidden' name='triedLogin' value=0>"+
					"</form>");
		
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response); 
	}

}
