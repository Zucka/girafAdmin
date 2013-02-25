
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class editProfile
 */

@WebServlet(
	    name = "editProfile", 
	    urlPatterns = {"/editProfile"}
	)
public class editProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession session;
	String user;
	String firstname;
	String middlename;
	String surname;
	int pRole;
	long phone;
	String picture;
	String username;
	String password;
	String oldPass;
	String newPass;
	String newPass1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public editProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		session = request.getSession();
		PrintWriter out = response.getWriter();
		user = (String) session.getAttribute("USER");
		if (session.getAttribute("USER") == null) {
			user = null;
			session.invalidate();
			response.sendRedirect("TestDatabase");
		} else {

			String userId = (String) session.getAttribute("ID");
			String topText = (String) session.getAttribute("topText");

			String changedMade = request.getParameter("changed");
			oldPass = request.getParameter("oldPass");
			newPass = request.getParameter("newPass");
			newPass1 = request.getParameter("newPass1");

			String prole = request.getParameter("prole");
			if (prole != null) {
				if (prole.equals("Admin"))
					prole = "0";
				else if (prole.equals("Pædagog"))
					prole = "1";
				else if (prole.equals("Forældre"))
					prole = "2";
				else if (prole.equals("Barn"))
					prole = "3";
			}

			if (changedMade != null && changedMade.equals("2")) {
				response.sendRedirect("main");
				session.setAttribute("MESSAGETOUSER",
						"Intet ændret. Statuskode = 0");
			}

			// connecting to database
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(
						"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("select * from Profile where idProfile = "
								+ userId + ";");

				// displaying records
				while (rs.next()) {
					firstname = rs.getString("firstname");
					middlename = rs.getString("middlename");
					if (middlename == null)
						middlename = "";
					surname = rs.getString("surname");
					pRole = rs.getInt("pRole");
					phone = rs.getLong("phone");
					picture = rs.getString("picture");
					username = rs.getString("username");
					password = rs.getString("password");

				}

			} catch (SQLException e) {
				throw new ServletException(
						"Servlet Could not display records. ID = " + userId, e);
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

			// Update database fields
			if ((changedMade != null) && (changedMade.equals("1"))) {
				String updatePassword = "";

				if (oldPass != null && !oldPass.equals("")
						&& !oldPass.equals(password)) {
					session.setAttribute("MESSAGETOUSER",
							"Intet ændret - forkert kodeord. Statuskode = 201");
					response.sendRedirect("main");
					return;
				} else if ((newPass != null || newPass1 != null)
						&& !newPass.equals(newPass1)) {
					session.setAttribute("MESSAGETOUSER",
							"Intet ændret - De 2 kodeord er ikke ens. Statuskode = 202");
					response.sendRedirect("main");
					return;
				} else if ((newPass != null) && (newPass1 != null)
						&& (oldPass != null) && newPass.equals(newPass1)
						&& oldPass.equals(password)) {
					session.setAttribute("MESSAGETOUSER", "TROLOLOLOLOLO");
					updatePassword = ", password='" + newPass1 + "'";

				}

				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection(
							"jdbc:mysql://172.25.11.65:3306/04", "eder",
							"123456");
					// Turn on transactions
					// con.setAutoCommit(false);

					stmt = con.createStatement();
					String newFirstname = request.getParameter("firstname");
					int test = stmt.executeUpdate("UPDATE Profile SET"
							+ " firstname='" + newFirstname + "',"
							+ " surname='" + surname + "'," + " middlename='"
							+ middlename + "'" + updatePassword
							+ " WHERE idProfile=" + userId + ";");
					session.setAttribute("MESSAGETOUSER",
							"Profil ændret. Statuskode = " + test);
					response.sendRedirect("main");
				} catch (Exception e) {
					// Any error is grounds for rollback
					out.println(e.toString());
				} finally {
					// Clean up.
					try {
						if (con != null)
							con.close();
					} catch (SQLException ignored) {
					}
				}
			}

			// Create dropdown menu, with the correct option selected:
			String dropDownMenu = "<select name='prole'>";
			if (pRole == 0) {
				dropDownMenu = dropDownMenu
						+ "<option selected='yes'>Admin</option>"
						+ "<option>Pædagog</option>"
						+ "<option>Forældre</option>"
						+ "<option>Barn</option>;";
			} else if (pRole == 1) {
				dropDownMenu = dropDownMenu
						+ "<option selected='yes'>Pædagog</option>"
						+ "<option>Forældre</option>"
						+ "<option>Barn</option>;";
			} else if (pRole == 2) {
				dropDownMenu = dropDownMenu + "<option>Pædagog</option>"
						+ "<option selected='yes'>Forældre</option>"
						+ "<option>Barn</option>;";
			} else if (pRole == 3) {
				dropDownMenu = dropDownMenu + "<option>Pædagog</option>"
						+ "<option>Forældre</option>"
						+ "<option selected='yes'>Barn</option>;";
			}
			dropDownMenu = dropDownMenu + "</select>";

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Savannah 1.0  - Logget ind som " + user
					+ "</title>");
			out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");

			out.println("<SCRIPT language = JavaScript> "
					+ "function doSubmit()" + "{"
					+ "document.profileForm.changed.value='1';"
					+ "document.profileForm.submit();" + "}"
					+ "function doCancel()" + "{"
					+ "document.profileForm.changed.value='2';"
					+ "document.profileForm.submit();" + "}" + "</SCRIPT>");

			out.println("</head>");
			out.println("<body>");
			out.println("<div id=\"mainBackground\">");
			out.println("<div id=\"mainsiteTop\">");
			out.println("<img src=\"images/i.jpg\" width=\"150\" height=\"100\" style=\"float:left;margin:0 5px 0 0;\">");
			out.println(topText);
			out.println("</div>");

			out.println("<hr color=\"Black\" size=\"2\">");
			out.println("<a href=\"main\"><img src=\"images/homeicon.png\">Hjem</a>"
					+ " >> ");
			out.println("<img src=\"images/smallProfileIcon.png\">Hjem"
					+ " >> ");
			out.println("<hr color=\"Black\" size=\"2\">");

			out.println("<div id=\"my_wrapper\">");
			out.println("<div id=\"mainsiteMain\">");

			out.println("<img src=\"images/dummypicSelected.jpg\"> <br>Profil <br>");
			out.println("<img src=\"images/dummypic.jpg\"> <br> Indstillinger <br>");
			out.println("<img src=\"images/dummypic.jpg\"> <br> Statestik <br>");
			out.println("</div>");
			out.println("<div id=\"profileSite\">");
			out.println("<center><h2>Rediger profil:</h2></center>");
			out.println("<hr color=\"Black\" size=\"2\">");
			out.println("<center>");
			out.println("<p>");
			out.println("<table border='0'>");
			out.println("<form method='GET' action='editProfile' name='profileForm'>");
			out.println("<tr>");
			out.println("<td>Navn " + user
					+ ":<td><input type=text name='firstname' value='"
					+ firstname + "'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Mellemnavn:<td><input type=text name='middlename' value='"
					+ middlename + "'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Efternavn:<td><input type=text name='surname' value='"
					+ surname + "'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Rolle:<td>" + dropDownMenu);
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Telefon nummer:<td><input type=text name='phone' value='"
					+ phone + "'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Brugernavn:<td><input type=text name='username' value='"
					+ username + "' disabled='true'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td><hr color=\"Black\" size=\"2\"><td><hr color=\"Black\" size=\"2\">");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Ændre kodeord:<td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Gammelt kodeord:<td><input type=password name='oldPass'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Nyt kodeord:<td><input type=password name='newPass'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Gentag nyt kodeord:<td><input type=password name='newPass1'>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td><input type='button' value='Gem' onClick=\"javascript: doSubmit()\"><td><input type='button' value='Fortryd'  onClick=\"javascript: doCancel()\">");
			out.println("</tr>");
			out.println("</table>");
			out.println("<input type='hidden' name='changed' value='0'>");
			out.println("</form>");
			out.println("</center>");
			out.println("</div>");

			out.println("</div>");
			out.println("<div id=\"logoutAlign\">");
			out.println("<form method='POST' action='editProfile' name=\"logoutForm\">\n"
					+ "<P ALIGN=\"right\"> <a  href=\"#\" onClick=\"document.logoutForm.submit()\">Logout</a> <p>"); // "<input type='submit' value='Logout'>\n");
			out.println("</div>");
			out.println("<hr color=\"Black\" size=\"2\">");
			out.println("<footer> Savannah v. 1.0.0 (C)opyright me!</footer>");
			// out.println("<form method='POST' action='main'>\n" +
			// "<input type='hidden' name='Logout'>"+
			// "<input type='submit' value='Logout'>\n" + "</form>");

			out.println("</body>");
			out.println("</html>");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		PrintWriter out = response.getWriter();
		out.println("Logger ud... Vent venligst.");
		session.removeAttribute("USER");

		response.setHeader("Refresh", "1");

	}

}
