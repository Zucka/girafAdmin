/** 
 * This program is an example from the book "Internet 
 * programming with Java" by Svetlin Nakov. It is freeware. 
 * For more information: http://www.nakov.com/books/inetjava/ 
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.catalina.connector.Request;
import org.apache.tomcat.dbcp.dbcp.DbcpException;

import com.sun.xml.internal.bind.CycleRecoverable.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet(
		name = "TestDatabase", 
		urlPatterns = {"/TestDatabase"}
		)
public class TestDatabase extends HttpServlet {
	/**
	 * 
	 */
	HttpSession session;
	private static final long serialVersionUID = 1L;
	String nextLocation = "";
	String openVar;
	String systemMessage = null;

	public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws IOException, ServletException {

		session = aRequest.getSession();
		String username = aRequest.getParameter("username");
		String password = aRequest.getParameter("password");
		nextLocation = aRequest.getParameter("next");
		String DBpassword = null;
		String DBusername = null;
		session.setAttribute("COMINGFROM", "TestDatabase");
		systemMessage = (String) session.getAttribute("SYSTEMMESSAGE");

		openVar = aRequest.getParameter("jsOpenVar");
		if (openVar == null)
			openVar = "0";

		String DBID = "-1";

		PrintWriter out = aResponse.getWriter();

		// connecting to database
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from AuthUsers where username = '"
					+ username + "'");
			// displaying records
			while (rs.next()) {
				// out.print("\t\t\t");
				DBpassword = rs.getString("password");
				DBID = rs.getString("idUser");
				DBusername = rs.getString("username");
				// out.print("<br>");
			}
		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
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

		if ((username == null) || (password == null)) {
			session.setAttribute("TriedLOGIN", "0");
			showLoginForm("Brugerlogin påkrævet: ", out);
		} else if (password.equals(DBpassword) && username.equals(DBusername)) {

			session.setAttribute("USER", username);
			session.setAttribute("ID", DBID);
			// session.removeAttribute("TriedLOGIN");
			// session.setAttribute("ID", DBID);
			// out.println("<h1>GRATZ!</h1> " + username + " " + DBID);

			if (nextLocation == null) {
				out.println("<html>ERROR</html>");
			} else if (nextLocation.equals("profile"))
				aResponse.sendRedirect("main");
		} else {
			session.setAttribute("TriedLOGIN", "1");
			showLoginForm("Ugyldigt login, prøv venligst igen:", out);
		}
	}

	public void doPost(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}

	private void showLoginForm(String aCaptionText, PrintWriter aOutput) {
		String triedLogin = (String) session.getAttribute("TriedLOGIN");
		aOutput.println("<html>"
				+ "<head>"
				+ "<title>Savannah 1.0</title>"
				+ "<script src=\"javascript/popup.js\">"
				+ "</script>"
				+ "<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />"
				+ " </head>");
		//if (triedLogin.equals("1")) {
		//	aOutput.println("<body onLoad=\"setOpen(" + openVar
		//			+ "); testThis('profile');popup('popUpDiv');getFocus();\">");
		//} else {
		aOutput.println("<body>");

		//}
		aOutput.println("<div id=\"mainBackground\">");
		aOutput.println("<center><h2> Velkommen!</h2></center>");
		aOutput.println("<SCRIPT language = JavaScript> "
				+ "var open = 0;"
				+ "function setOpen(number)"
				+ "{"
				+ "open = number;"
				+ "document.DasForm.jsOpenVar.value = number;"
				+ "}"
				+ "function testThis(link)"
				+ "{"
				+ "document.DasForm.next.value = link"
				+ "}"
				+

				"function getFocus()"
				+ "{"
				+ "document.DasForm.username.focus();"
				+ "}"
				+ "function clearForm()"
				+ "{"
				+ "document.DasForm.username.value =\"\";"
				+ "document.DasForm.password.value= \"\";"
				+ "}"
				+ "document.onkeydown = function(e) {"
				+ " e = e || window.event;"
				+ "var keyCode = e.keyCode || e.which;"
				+
				// If in login and esc is pressed
				"if(keyCode == 27 && open == 1) {setOpen(0); testThis('profile');popup('popUpDiv');  clearForm(); }"
				+
				// If in add and esc is pressed
				"if(keyCode == 27 && open == 2) {setOpen(0); popup('popUpPick'); }"
				+ "if(keyCode == 80 && open == 2) {window.location = \"AddProfile\"}"
				+

				// if in edit
				"if(keyCode == 27 && open == 3) {setOpen(0); popup('popUpEdit'); }"
				+ "if(keyCode == 80 && open == 3) {window.location = \"SelectProfileToEdit\"}"
				+

				// MainScreen Key presses
				"if(keyCode == 80 && open == 0) {window.location = \"SelectProfile\"}"
				+ // P = redirect to SelectProfile
				"if(keyCode == 84 && open == 0) {setOpen(2); popup('popUpPick')}"

				+ // R = show edit window
				"if(keyCode == 82 && open == 0) {setOpen(3); popup('popUpEdit')}"

				+ // T = edit add window //Nice little feature for waiting 25 ms
				" if(keyCode == 76 && open == 0) {setOpen(1); clearForm(); testThis('profile');popup('popUpDiv'); document.DasForm.username.value =\"\"; setTimeout(function(){getFocus();clearForm();}, 25); }"
				+ // L = Show login
				"}" + "</SCRIPT>");
		aOutput.println("<hr>");
		aOutput.println("<div id=\"generic_wrapper\">");
		aOutput.println("<center>");
		if (systemMessage != null)
			aOutput.println(systemMessage + "<br>");
		aOutput.println("Vælg handling");
		aOutput.println("<p>");
		aOutput.println("<a href=\"SelectProfile\"><img src=\"images/i.jpg\" ALT=\"test\">");
		aOutput.println("</p>");
		aOutput.println("<br>");
		aOutput.println("<u>P</u>rofiler</a>");
		aOutput.println("<p>");
		aOutput.println("<a href=\"#\" onclick=\"setOpen(2); popup('popUpPick');\"><u>T</u>ilføj</a>  -  <a href=\"#\" onclick=\"setOpen(3); popup('popUpEdit');\"><u>R</u>ediger</a>  -  <a href=\"#\" onclick=\"setOpen(4); popup('popUpDelete');\"><u>S</u>let");
		aOutput.println("</p>");
		aOutput.println("</center>");
		aOutput.println("<br>");
		if (systemMessage == null)
			aOutput.println("<br>");
		aOutput.println("<a href=\"#\" onclick=\"setOpen(1); testThis('profile');popup('popUpDiv');getFocus();\">Hurtig <u>L</u>ogin</a>");


		aOutput.println("</div>");
		aOutput.println("<hr>");
		aOutput.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> </div>");

		// out.println("<form method='POST' action='main'>\n" +
		// "<input type='hidden' name='Logout'>"+
		// "<input type='submit' value='Logout'>\n" + "</form>");
		aOutput.println(""
				+ "<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpDiv\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"setOpen(0); popup('popUpDiv')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<form method='POST' action='TestDatabase' name='DasForm'>"
				+ "<center><h3>"
				+ aCaptionText
				+ "</h3>"
				+ "<br>\n"
				+ "<table border=\"0\">"
				+ "<tr>"
				+ "<td>Brugernavn:<td><input type='text' name='username' autocomplete='off'><br>\n"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Kodeord:<td><input type='password' name='password'><br>\n"
				+ "</tr>"
				+ "<tr>"
				+ "<td><input type='hidden' name='next'><input type='hidden' name='jsOpenVar'><br>\n"
				+ "</tr>"
				+ "</table>"
				+
				// "<tr>"+
				"<input type='submit' value='Login'><td><input type='button' value='Fortryd' onClick=\"setOpen(0); clearForm();popup('popUpDiv')\">"
				+
				// "</tr>"+

				"</center>" + "</div>");

		aOutput.println("<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpEdit\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"setOpen(0);popup('popUpEdit')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center><h3>"
				+ "Rediger:"
				+ "</h3>"
				+ "<a href=\"SelectProfileToEdit\"><u>P</u>rofil</a>  -  <a href=\"SelectProfileToLink\">Billede</a> - <a href=\"EditTags\"><u>T</u>ags</a>"
				+ "</center>" + "</div>");


		aOutput.println("<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpPick\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"setOpen(0);popup('popUpPick')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center><h3>"
				+ "Tilføj:"
				+ "</h3>"
				+ "<a href=\"AddProfile\"><u>P</u>rofil</a>  -  <a href=\"SelectProfileToPicture\">Billede</a>  -  <a href=\"SelectProfileToAudio\">Lyd</a>"
				+ "</center>" + "</div>");

		aOutput.println("<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpDelete\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"setOpen(0);popup('popUpDelete')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center><h3>"
				+ "Tilføj:"
				+ "</h3>"
				+ "<a href=\"SelectProfileToDelete\"><u>P</u>rofil</a>  -  <a href=\"SelectProfileToDeletePic\"><u>B</u>illede</a>  -  <a href=\"DeleteTags\"><u>T</u>ags</a>"
				+ "</center>" + "</div>");




		aOutput.println("</body></form></html>");
		systemMessage = null;
	}

}