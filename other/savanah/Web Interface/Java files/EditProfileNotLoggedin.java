
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.net.httpserver.HttpContext;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.internal.ws.api.pipe.NextAction;

/**
 * Servlet implementation class EditProfileNotLoggedin
 */

@WebServlet(
		name = "EditProfileNotLoggedin", 
		urlPatterns = {"/EditProfileNotLoggedin"}
		)
public class EditProfileNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<Department> possibleDepartments = new ArrayList<Department>();
	ArrayList<Department> currentDepartments = new ArrayList<Department>();
	ArrayList<Profile> possibleChild = new ArrayList<Profile>();
	ArrayList<Profile> currentChild = new ArrayList<Profile>();
	ArrayList<Profile> possibleGuardian = new ArrayList<Profile>();
	ArrayList<Profile> currentGuardian = new ArrayList<Profile>();
	ArrayList<App> currentApps = new ArrayList<App>();
	ArrayList<App> possibleApps = new ArrayList<App>();
	ArrayList<Profile> currentParents = new ArrayList<Profile>();
	ArrayList<Profile> possibleParents = new ArrayList<Profile>();
	String userID = "-1";
	String currentPassword = null;
	PrintWriter out;
	boolean isChild = false;
	boolean isGuardian = false;
	boolean isParent = false;
	String firstname;
	String surname;
	String middlename;

	String[] selectedDeps;
	String[] unselectedDeps;
	String[] selectedKids;
	String[] unselectedKids;
	String[] selectedGuards;
	String[] unselectedGuards;
	String[] selectedParents;
	String[] unselectedParents;
	String[] selectedApps;
	String[] ubselectedApps;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProfileNotLoggedin() {
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
		String context = request.getContextPath();

		possibleDepartments.clear();
		currentDepartments.clear();
		possibleChild.clear();
		currentChild.clear();
		currentApps.clear();
		possibleApps.clear();
		possibleGuardian.clear();
		currentGuardian.clear();
		currentParents.clear();
		possibleParents.clear();

		out = response.getWriter();

		HttpSession session = request.getSession();

		userID = (String) session.getAttribute("PROFILEIDTOEDIT");
		String errorMessage = (String) session.getAttribute("EDITERROR");
		session.removeAttribute("EDITERROR");

		if (userID == null)
			response.sendRedirect("TestDatabase");

		int idProfile = -1;
		firstname = null;
		surname = null;
		middlename = null;
		int pRole = -1;
		int phone = -1;
		String picture = null;
		int idDepartment = -1;

		String username = null;

		ArrayList<Profile> guardians = new ArrayList<Profile>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from Profile where idProfile = '"
					+ userID + "'");
			// displaying records
			while (rs.next()) {
				// out.print("\t\t\t");
				idProfile = rs.getInt("idProfile");
				firstname = rs.getString("firstname");
				surname = rs.getString("surname");
				middlename = rs.getString("middlename");
				pRole = rs.getInt("pRole");
				phone = (int) rs.getLong("phone");
				picture = rs.getString("picture");

				if (picture == null || picture.equals("null"))
					picture = context + "/images/i.jpg";
				else
					picture = context + picture;

				switch (pRole) {
				case 1: {
					isGuardian = true;
					isParent = false;
					isChild = false;
					break;
				}
				case 2: {
					isGuardian = false;
					isParent = true;
					isChild = false;
					break;
				}
				case 3: {
					isGuardian = false;
					isParent = false;
					isChild = true;
					break;
				}

				default:
					break;
				}

				// out.print("<br>");
			}

			Statement stmt0 = con.createStatement();
			ResultSet rs0 = stmt0
					.executeQuery("SELECT * from AuthUsers where idUser = '"
							+ userID + "'");
			// displaying records
			while (rs0.next()) {
				username = rs0.getString("username");
				currentPassword = rs0.getString("password");
				// out.print("\t\t\t");

			}

			// Find all departments a person can be linked to
			if (pRole != 2) {
				Statement stmts1 = con.createStatement();
				ResultSet rss1 = stmts1
						.executeQuery("select * from Department "
								+ "where idDepartment not in "
								+ "(select idSubDepartment from HasSubDepartment);");

				while (rss1.next()) {

					Department d = new Department(rss1.getInt("idDepartment"),
							rss1.getString("name"), rss1.getString("address"),
							(int) rss1.getInt("phone"), rss1.getString("email"));
					// possibleDepartments.add(d);
					possibleDepartments.add(d);
					getAllDepts(d, "");
				}

			}
			// Find all departments a person is be linked to
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1
					.executeQuery("SELECT * from HasDepartment where idProfile = '"
							+ idProfile + "'");
			while (rs1.next()) {
				idDepartment = rs1.getInt("idDepartment");
			}

			if (pRole != 2) {
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery("select * from Department "
						+ "where idDepartment in "
						+ "(select idDepartment from HasDepartment "
						+ "where  idProfile = '" + idProfile + "');");

				while (rs2.next()) {

					Department d = new Department(rs2.getInt("idDepartment"),
							rs2.getString("name"), rs2.getString("address"),
							(int) rs2.getInt("phone"), rs2.getString("email"));
					currentDepartments.add(d);
					// possibleDepartments.add(d);
					// getAllDepts(d,"");
				}

			}

			// Find possible Guardians for a child
			if (isChild) {
				for (Department d : currentDepartments) {

					Statement stmt3 = con.createStatement();
					ResultSet rs3 = stmt3.executeQuery("select * from Profile "
							+ "where idProfile in "
							+ "(select idProfile from HasDepartment "
							+ "where idDepartment = " + d.getID() + ") "
							+ "and pRole = 1;");
					while (rs3.next()) {
						Profile p = new Profile(rs3.getInt("idProfile"),
								rs3.getString("firstname"),
								rs3.getString("surname"),
								rs3.getString("middlename"),
								rs3.getInt("pRole"),
								(int) rs3.getLong("phone"),
								rs3.getString("picture"));
						possibleGuardian.add(p);

					}
				}
				// Find current Guardian(s) for child
				Statement stmt31 = con.createStatement();
				ResultSet rs31 = stmt31.executeQuery("select * from Profile "
						+ "where pRole=1 and idProfile in "
						+ "(select idGuardian from HasGuardian "
						+ "where idChild=" + idProfile + ");");
				while (rs31.next()) {
					Profile p = new Profile(rs31.getInt("idProfile"),
							rs31.getString("firstname"),
							rs31.getString("surname"),
							rs31.getString("middlename"), rs31.getInt("pRole"),
							(int) rs31.getLong("phone"),
							rs31.getString("picture"));
					currentGuardian.add(p);
				}

				// Find current parents
				Statement stmt9 = con.createStatement();
				ResultSet rs9 = stmt9.executeQuery("select * from Profile "
						+ "where pRole=2 and idProfile in "
						+ "(select idGuardian from HasGuardian "
						+ "where idChild=" + idProfile + ");");
				while (rs9.next()) {
					Profile p = new Profile(rs9.getInt("idProfile"),
							rs9.getString("firstname"),
							rs9.getString("surname"),
							rs9.getString("middlename"), rs9.getInt("pRole"),
							(int) rs9.getLong("phone"),
							rs9.getString("picture"));
					currentParents.add(p);
				}

				// Find current parents
				Statement stmt10 = con.createStatement();
				ResultSet rs10 = stmt10.executeQuery("select * from Profile "
						+ "where pRole=2;");
				while (rs10.next()) {
					Profile p = new Profile(rs10.getInt("idProfile"),
							rs10.getString("firstname"),
							rs10.getString("surname"),
							rs10.getString("middlename"), rs10.getInt("pRole"),
							(int) rs10.getLong("phone"),
							rs10.getString("picture"));
					possibleParents.add(p);
				}

			}

			// Find possible childs for a guardian
			if (isGuardian) {
				for (Department d : currentDepartments) {

					Statement stmt3 = con.createStatement();
					ResultSet rs3 = stmt3.executeQuery("select * from Profile "
							+ "where idProfile in "
							+ "(select idProfile from HasDepartment "
							+ "where idDepartment = " + d.getID() + ") "
							+ "and pRole = 3;");
					while (rs3.next()) {
						Profile p = new Profile(rs3.getInt("idProfile"),
								rs3.getString("firstname"),
								rs3.getString("surname"),
								rs3.getString("middlename"),
								rs3.getInt("pRole"),
								(int) rs3.getLong("phone"),
								rs3.getString("picture"));
						possibleChild.add(p);

					}
				}
				// Find current Guardian(s) for child
				Statement stmt4 = con.createStatement();
				ResultSet rs4 = stmt4.executeQuery("select * from Profile "
						+ "where idProfile in "
						+ "(select idChild from HasGuardian "
						+ "where idGuardian=" + userID + ");");
				while (rs4.next()) {
					Profile p = new Profile(rs4.getInt("idProfile"),
							rs4.getString("firstname"),
							rs4.getString("surname"),
							rs4.getString("middlename"), rs4.getInt("pRole"),
							(int) rs4.getLong("phone"),
							rs4.getString("picture"));
					currentChild.add(p);

				}
			}

			// Get all possible childen for parents
			if (isParent) {
				Statement stmt7 = con.createStatement();
				ResultSet rs7 = stmt7.executeQuery("select * from Profile "
						+ "where pRole = 3;");

				while (rs7.next()) {
					Profile p = new Profile(rs7.getInt("idProfile"),
							rs7.getString("firstname"),
							rs7.getString("surname"),
							rs7.getString("middlename"), rs7.getInt("pRole"),
							(int) rs7.getLong("phone"),
							rs7.getString("picture"));
					possibleChild.add(p);

				}

				// Find current children for parent
				Statement stmt8 = con.createStatement();
				ResultSet rs8 = stmt8.executeQuery("select * from Profile "
						+ "where idProfile in "
						+ "(select idChild from HasGuardian "
						+ "where idGuardian=" + userID + ");");
				while (rs8.next()) {
					Profile p = new Profile(rs8.getInt("idProfile"),
							rs8.getString("firstname"),
							rs8.getString("surname"),
							rs8.getString("middlename"), rs8.getInt("pRole"),
							(int) rs8.getLong("phone"),
							rs8.getString("picture"));
					currentChild.add(p);
				}

			}

			// Get current apps:
			Statement stmt5 = con.createStatement();
			ResultSet rs5 = stmt5.executeQuery("select * from Apps "
					+ "where idApp in (select idApp from ListOfApps "
					+ "where idProfile =" + userID + ");");

			while (rs5.next()) {
				App app = new App(rs5.getInt("idApp"), rs5.getString("name"),
						rs5.getString("version"));
				currentApps.add(app);

			}

			// Get all possible apps:
			Statement stmt6 = con.createStatement();
			ResultSet rs6 = stmt6.executeQuery("select * from Apps;");

			while (rs6.next()) {
				App app = new App(rs6.getInt("idApp"), rs6.getString("name"),
						rs6.getString("version"));
				possibleApps.add(app);

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

		removeDuplicatesDept(possibleDepartments, currentDepartments);
		if (isGuardian || isParent)
			removeDuplicatesProf(possibleChild, currentChild);
		if (isChild) {
			removeDuplicatesProf(possibleGuardian, currentGuardian);
			removeDuplicatesProf(possibleParents, currentParents);
		}

		if (middlename == null || middlename.equals("null"))
			middlename = "";

		out.println("<html>"
				+ "<head>"
				+ "<title>Savannah 1.0</title>"
				+ "<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />"
				+ "<script src=\"javascript/popup.js\">"
				+ "</script>"
				+ "<SCRIPT language = JavaScript>"
				+ "function listbox_move(fromID, toID) "
				+ "{"
				+ "var from = document.getElementById(fromID);"
				+ "var to = document.getElementById(toID);"
				+ "for(var count=0; count < from.options.length; count++) "
				+ "{"
				+ "if(from.options[count].selected == true) "
				+ "{"
				+ "var option = from.options[count];"
				+ "var newOption = document.createElement(\"option\");"
				+ "newOption.value = option.value;"
				+ "newOption.text = option.text;"
				+ "newOption.selected = true;"
				+ "try "
				+ "{"
				+ "to.add(newOption, null);"
				+ // for normal browsers"
				"from.remove(count, null);"
				+ "}"
				+ "catch(error) "
				+ "{"
				+ "to.add(newOption);"
				+ // for Internet Explorer
				"from.remove(count);" + "}" + "count--;" + "}" + "}" + "}"
				+ "function doSubmit()" + "{" + "document.editForm.submit();"
				+ "}" +

				"function SelectAll()" + "{"
				+ "if  (document.getElementById('SelectedDep') != null)" + "{"
				+ "SelectedDepSelectAll();" + "}" +

				"if (document.getElementById('Selected1') != null)" + "{"
				+ "SelectedKidsSelectAll();" + "}" +

				"if (document.getElementById('SelectedParents') != null)" + "{"
				+ "SelectedParSelectAll()" + "}" + 
				"if (document.getElementById('SelectedGuards') != null)" + "{"
				+ "SelectedGuarSelectAll()" + "}" +
				"}"


				+"function SelectedDepSelectAll()" + "{"
				+ "var select = document.getElementById('SelectedDep');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}"
				+ "var select = document.getElementById('AllDeps');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}" +

				"}" +

"var reader = new FileReader();"+
"reader.onload = function(e) {"+
"document.billedet.src  = e.target.result;"+ 
"};"+

"function readURL(input){"+ 
"if(input.files && input.files[0]){"+
"reader.readAsDataURL(input.files[0]);"+
"}"+
"else {"+
"document.billedet.src = input.value || \"No file selected\";"+
"alert(input.value);"+
"}"+
"}"+

				"function SelectedParSelectAll()" + "{" +

				"var select = document.getElementById('SelectedParents');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}" +

				"var select = document.getElementById('AllParents');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}" +

				"}" +


				"function SelectedGuarSelectAll()" + "{" +

				"var select = document.getElementById('SelectedGuards');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}" +

				"var select = document.getElementById('AllGuards');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}" +

				"}" +

				"function SelectedKidsSelectAll()" + "{"
				+ "var select = document.getElementById('Selected1');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}"
				+ "var select = document.getElementById('AllKids');"
				+ "for(var count=0; count < select.options.length; count++) "
				+ "{" + "select.options[count].selected = 'true';" + "}" +

				"}");

		if (session.getAttribute("EDITPICTUREMESSAGE") != null)
		{
			out.println("alert('Billede ændret');");
			session.removeAttribute("EDITPICTUREMESSAGE");
		}
		out.println("</SCRIPT>"
				+ "</head>"
				+ "<body>"
				+ "<center>"
				+ "<div id=\"mainBackground\">"
				+ "<h2>Rediger profiler</h2>"
				+"<hr>");
		out.println("<div id=\"generic_wrapper\">");
		if (errorMessage != null)
			out.println("<br><font color='red'>" + errorMessage + "</font>");

		out.println("<center>"
				+"<table border = 0>"
				+ "<form method='POST' name='editForm' action='EditProfileNotLoggedin'>"
				+ "<tr>"
				+ "<td align='center' colspan='3'><img src='"+picture+"' width=100 height=100><br><a href='#', onClick=\"popup('popUpDiv')\">Skift<a></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Navn:</td> <td colspan='3'><input type=\"text\" name=\"firstname\" value='"
				+ firstname
				+ "' /></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Mellemnavn(e):</td> <td colspan='3'><input type=\"text\" name=\"middlename\" value='"
				+ middlename
				+ "' /> </td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Efternavn: </td><td colspan='3'><input type=\"text\" name=\"surname\" value='"
				+ surname
				+ "' /> </td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Telefon nummer: </td> <td colspan='3'> <input type=\"text\" name=\"phone\" value='"
				+ phone + "'  /> </td>" + "</tr>" + "<tr>"
				+ "<td></td><td><select name=\"prole\">" + "<option value=\"0\"");
		if (pRole == 0) {
			out.println("selected=\"selected\"");
		}
		out.println(">Administrator</option>" + "<option value=\"1\"");
		if (pRole == 1) {
			out.println("selected=\"selected\"");
		}
		out.println(">Pedagog</option>" + "<option value=\"2\"");
		if (pRole == 2) {
			out.println("selected=\"selected\"");
		}
		out.println(">Forældre</option>" + "<option value=\"3\"");
		if (pRole == 3) {
			out.println("selected=\"selected\"");
		}
		out.println(">Barn</option>" + "</select></td>" +"</tr>");
		out.println("<tr>" + "<td colspan='3'><hr></td>"
				+ "</tr>");
		if (!isParent) {
			out.println("<tr><td>Mulige afdelinger:<br><select name=\"AllDep\" style='width:150px' id=\"AllDeps\" size=\"4\" multiple=\"multiple\">");

			for (Department d : possibleDepartments) {
				out.println("<option value=\"" + d.getID() + "\">"
						+ d.getName() + "</option>");

			}
			out.println("</select></td>"
					+ "<td> " +
					"<center><br>" +
					"<input type=\"button\" style='width:100px' value=\"Tilføj\" onclick=\"listbox_move('AllDeps', 'SelectedDep');\"><br />"
					+ "<input type=\"button\" style='width:100px' value=\"Fjern\" onclick=\"listbox_move('SelectedDep', 'AllDeps');\"><br />"
					+ "<input type=\"button\" style='width:100px' value=\"Fjern alle\">" +
					"</center>" +
					"</td>"
					+ "<td>Med i afdelinger:<br><select name=\"SelectedDep\" style='width:150px' id=\"SelectedDep\" size=\"4\" multiple=\"multiple\">");

			for (Department d : currentDepartments) {
				out.println("<option value=\"" + d.getID() + "\">"
						+ d.getName() + "</option>");
			}

			out.println("</select></td>" + "</tr>");
		}

		if (isGuardian || isParent) {
			out.println("<tr>"
					+ "<td>Mulige Børn:<br><select name=\"AllKids\" style='width:150px' id=\"AllKids\" size=\"4\" multiple=\"multiple\">");
			for (Profile p : possibleChild) {
				out.println("<option value=\"" + p.getID() + "\">"
						+ p.getName() + "</option>");

			}
			out.println("</select></td>"
					+"<td>"
					+ "<center><br><input type=\"button\" style='width:100px' value=\"Tilføj\" onclick=\"listbox_move('AllKids', 'Selected1');\"><br />"
					+ "<input type=\"button\" style='width:100px' value=\"Fjern\" onclick=\"listbox_move('Selected1', 'AllKids');\"><br />"
					+ "<input type=\"button\" style='width:100px' value=\"Fjern alle\">" +
					"</center>" +
					"</td>"
					+ "<td>");
			if (isGuardian)
				out.println("Pædagog for:");
			if (isParent)
				out.println("Forældre for:");

			out.println("<br><select name=\"SelectedKids\" style='width:150px' id=\"Selected1\" size=\"4\" multiple=\"multiple\">");

			for (Profile p : currentChild) {
				out.println("<option value=\"" + p.getID() + "\">"
						+ p.getName() + "</option>");
			}

			out.println("</select></td>" + "</tr>");
		}

		if (isChild) {
			out.println("<tr>"
					+ "<td>Mulige pædagoger:<br><select name=\"AllGuards\" style='width:150px' id=\"AllGuards\" size=\"4\" multiple=\"multiple\">");
			for (Profile p : possibleGuardian) {
				out.println("<option value=\"" + p.getID() + "\">"
						+ p.getName() + "</option>");

			}
			out.println("</select></td>"
					+ "<td> " +
					"<center><br><input type=\"button\" value=\"Tilføj\" style='width:100px' onclick=\"listbox_move('AllGuards', 'SelectedGuards');\"><br />"
					+ "<input type=\"button\" value=\"Fjern\" style='width:100px' onclick=\"listbox_move('SelectedGuards', 'AllGuards');\"><br />"
					+ "<input type=\"button\" style='width:100px' value=\"Fjern alle\">"
					+"</center>"
					+"</td>" 
					+ "<td>Barn ved:<br><select name=\"SelectedGuards\" style='width:150px' id=\"SelectedGuards\" size=\"4\" multiple=\"multiple\">");

			for (Profile p : currentGuardian) {
				out.println("<option value=\"" + p.getID() + "\">"
						+ p.getName() + "</option>");
			}

			out.println("<tr>"
					+ "<td>Mulige forældre:<br><select name=\"AllParents\" style='width:150px' id=\"AllParents\" size=\"4\" multiple=\"multiple\">");
			for (Profile p : possibleParents) {
				out.println("<option value=\"" + p.getID() + "\">"
						+ p.getName() + "</option>");

			}
			out.println("</select></td>"
					+ "<td><center><br> <input type=\"button\" value=\"Tilføj\" style='width:100px' onclick=\"listbox_move('AllParents', 'SelectedParents');\"><br />"
					+ "<input type=\"button\" value=\"Fjern\" style='width:100px'  onclick=\"listbox_move('SelectedParents', 'AllParents');\"><br />"
					+ "<input type=\"button\" style='width:100px' value=\"Fjern alle\"></td>"
					+ "<td>Barn ved:<br><select name=\"SelectedParents\" style='width:150px' id=\"SelectedParents\" size=\"4\" multiple=\"multiple\"></center>");

			for (Profile p : currentParents) {
				out.println("<option value=\"" + p.getID() + "\">"
						+ p.getName() + "</option>");
			}
		}

		/*
		 * if (isParent) { out.println("<tr>"+
		 * "<td>Mulige børn:<br><select name=\"AllParents\" id=\"AllParents\" size=\"4\" multiple=\"multiple\">"
		 * ); for (Profile p : possibleChild) {
		 * out.println("<option value=\""+p.
		 * getID()+"\">"+p.getName()+"</option>");
		 * 
		 * } out.println("</select></td>"+
		 * "<td> <input type=\"button\" value=\"Tilføj\" onclick=\"listbox_move('AllParents', 'SelectedParents');\"><br />"
		 * +
		 * "<input type=\"button\" value=\"Fjern\" onclick=\"listbox_move('SelectedParents', 'AllParents');\"> <br />"
		 * + "<input type=\"button\" value=\"Fjern alle\"></td>"+
		 * "<td>Forældre for:<br><select name=\"SelectedParents\" id=\"SelectedParents\" size=\"4\" multiple=\"multiple\">"
		 * );
		 * 
		 * for (Profile p : currentChild) {
		 * out.println("<option value=\""+p.getID
		 * ()+"\">"+p.getName()+"</option>"); }
		 * 
		 * out.println("</select></td>"+ "</tr>"); }
		 */

		out.println("<tr>" + "<td colspan='3'><hr></td>"
				+ "</tr>");
		out.println("<tr>"
				+ "<td>Adgang til apps:</td><td>");
		// This section needs optimization!
		for (App app : possibleApps) {
			boolean hasBeenPrinted = false;
			for (App hasApp : currentApps) {
				if (app.getID() == hasApp.getID()) {
					hasBeenPrinted = true;
					out.println("<input type='checkbox' name='apps' value='"
							+ app.getID() + "' checked=true>" + app.getName()
							+ "<br>");
				}
			}
			if (!hasBeenPrinted) {
				out.println("<input type='checkbox' name='apps' value='"
						+ app.getID() + "'>" + app.getName() + "<br>");
			} else {
				hasBeenPrinted = false;
			}

		}
		// /The above section needs optimization

		out.println("<tr>" + "<td colspan='3'><hr></td>"
				+ "</tr>");
		out.println("<tr><td></td><td><a href=\"EditPictureAccess\">Billede håndtering</a></td><td></td></tr>");
		out.println("<tr>" + "<td colspan='3'><hr></td>"
				+ "</tr>");
		out.println("</td></tr><tr>"
				+ "<td>Brugernavn: </td><td><input type=\"text\" name=\"brugernavn\" disabled=true value='"
				+ username
				+ "' /></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Gamle kodeord:</td><td><input type=\"password\" name=\"oldpassword\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Kodeord:</td><td> <input type=\"password\" name=\"password\" /> </td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Gentag kodeord:</td> <td> <input type=\"password\" name=\"password2\" /> </td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td></td><td><input type=\"button\" onClick=\"SelectAll(); doSubmit();\" value=\"Gem\"/>"
				+ "<input type=\"button\" value=\"Fortryd\" onClick=\"javascript:history.go(-1)\"></td>" + "</tr>"
				+ "</form>"
				+"</table>"
				+"</center>"
				+"</div>"
				+"<hr>");
		out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> </div>");

		out.println(""
				+ "<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpDiv\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpDiv')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center>"
				+"<form method='POST' name='newPic' enctype='multipart/form-data' action='newProfilePicture'>"
				+"Nyt billede:"
				+"<br>"
				+"<img src='test.jpg' name='billedet' width=100 height=100><br>"
				+"<input name='file1' type='file' accept='image/*' onChange=\"readURL(this);\"/>"
				+"<input type='hidden' name='userID' value='"+userID+"'>"
				+"<input type='hidden' name='oldPic' value='"+picture+"'>"
				+"<br>"
				+"<input type='submit' value='Tilføj'> <input type=button onclick=\"popup('popUpDiv')\" value='Fortryd'>"
				+"</form>"
				+"</center>" + "</div>");
		out.println("</body>" + "</html>");

	}

	/**
	 * @throws ServletException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/**
	 * Remove duplicates. This needs optimization!
	 * 
	 * @param possible
	 * @param current
	 */
	private void removeDuplicatesDept(ArrayList<Department> possible,
			ArrayList<Department> current) {
		ArrayList<Department> toRemove = new ArrayList<Department>();
		for (int i = 0; i < possible.size(); i++) {
			for (int j = 0; j < current.size(); j++) {
				if (possible.get(i).getID() == current.get(j).getID()) {
					toRemove.add(possible.get(i));
					// possible.remove(i);

				}
			}
		}

		for (Department department : toRemove) {
			possible.remove(department);
		}

	}

	private void removeDuplicatesProf(ArrayList<Profile> possible,
			ArrayList<Profile> current) {
		ArrayList<Profile> toRemove = new ArrayList<Profile>();

		for (int i = 0; i < possible.size(); i++) {
			for (int j = 0; j < current.size(); j++) {
				if ((possible.size() > 0)
						&& possible.get(i).getID() == current.get(j).getID()) {
					toRemove.add(possible.get(i));
					// possible.remove(i);

				}
			}
		}
		for (Profile profile : toRemove) {
			possible.remove(profile);
		}
	}

	private void getAllDepts(Department d, String star) throws ServletException {
		star = star + "*";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from Department "
					+ "where idDepartment in "
					+ "(select idSubdepartment from HasSubDepartment "
					+ "where idDepartment = " + d.getID() + ");");
			// displaying records
			while (rs.next()) {
				// out.print("\t\t\t");

				Department d1 = new Department(rs.getInt("idDepartment"), star
						+ rs.getString("name"), rs.getString("address"),
						(int) rs.getInt("phone"), rs.getString("email"));

				possibleDepartments.add(d1);
				getAllDepts(d1, star);

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
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tempMessage = "";
		// Husk: SelectedDep
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		selectedDeps = request.getParameterValues("SelectedDep");
		unselectedDeps = request.getParameterValues("AllDep");
		selectedKids = request.getParameterValues("SelectedKids");
		unselectedKids = request.getParameterValues("AllKids");
		selectedGuards = request.getParameterValues("SelectedGuards");
		unselectedGuards = request.getParameterValues("AllGuards");
		selectedParents = request.getParameterValues("SelectedParents");
		unselectedParents = request.getParameterValues("AllParents");
		selectedApps = request.getParameterValues("apps");



		String oldPass = request.getParameter("oldpassword");
		boolean oldPassCorrect = false;
		String newPass1 = request.getParameter("password");
		String newPass2 = request.getParameter("password2");
		boolean newPassword = true;
		Integer prole = Integer.parseInt(request.getParameter("prole"));
		if (prole == null)
			prole = 1;
		if (selectedDeps != null) {
			for (int i = 0; i < selectedDeps.length; i++) {
				out.println(selectedDeps[i] + "<br>");
			}
		}

		if (!oldPass.equals("") && !oldPass.equals(currentPassword)) {

			session.setAttribute("EDITERROR",
					"'Nuværende kodeord' er ikke korrekt");
			response.sendRedirect("EditProfileNotLoggedin");
			return;

		} else {
			if (!oldPass.equals(""))
				oldPassCorrect = true;
			else
				oldPassCorrect = false;
		}

		if (oldPassCorrect && !newPass1.equals(newPass2)) {
			session.setAttribute("EDITERROR", "De to nye kodeord er ikke ens");
			response.sendRedirect("EditProfileNotLoggedin");
			return;
		} else {
			if (oldPassCorrect && !newPass1.equals("") && !newPass2.equals(""))
				newPassword = true;
			else
				newPassword = false;
		}

		out.println("<html>" + prole + "</html>");

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			// Turn on transactions
			con.setAutoCommit(false);

			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE Profile SET firstname = '"
					+ request.getParameter("firstname") + "' WHERE idProfile ="
					+ userID + " ;");
			stmt.executeUpdate("UPDATE Profile SET middlename = '"
					+ request.getParameter("middlename")
					+ "' WHERE idProfile =" + userID + " ;");
			stmt.executeUpdate("UPDATE Profile SET surname = '"
					+ request.getParameter("surname") + "' WHERE idProfile ="
					+ userID + " ;");
			stmt.executeUpdate("UPDATE Profile SET phone = '"
					+ request.getParameter("phone") + "' WHERE idProfile ="
					+ userID + " ;");
			stmt.executeUpdate("UPDATE Profile SET pRole = " + prole
					+ " WHERE idProfile =" + userID + " ;");
			if (newPassword)
				stmt.executeUpdate("UPDATE AuthUsers SET password = '"
						+ newPass2 + "' WHERE idUser =" + userID + " ;");

			// MONDAY STUFF

			// Add new departments
			if (selectedDeps != null) {
				ArrayList<Department> depToDelte = findDepLists();
				for (int i = 0; i < selectedDeps.length; i++) {
					if (!selectedDeps[i].equals("-1"))
						stmt.executeUpdate("INSERT into HasDepartment values("
								+ userID + "," + selectedDeps[i] + ");");
				}

				// Delete departments
				out.println("To delete");
				for (Department dep : depToDelte) {
					stmt.executeUpdate("DELETE FROM HasDepartment WHERE idProfile="
							+ userID + " and idDepartment=" + dep.getID() + ";");
				}
			} else {
				stmt.executeUpdate("DELETE FROM HasDepartment WHERE idProfile="
						+ userID + ";");
			}



			// Add new Apps
			if (selectedApps != null) {
				ArrayList<App> appToDelete = findAppsList();
				//ArrayList<Department> depToDelte = findDepLists();
				for (int i = 0; i < selectedApps.length; i++) {
					if (!selectedApps[i].equals("0") && !selectedApps[i].equals("-1"))
					{
						tempMessage = selectedApps[i];
						stmt.executeUpdate("INSERT into ListOfApps values("+
								selectedApps[i] +"," + userID + ",null,null);");
					}
				}
				// Delete kid relation
				out.println("To delete");

				for (App app : appToDelete) {
					stmt.executeUpdate("DELETE FROM ListOfApps WHERE idProfile="
							+ userID + " and idApp=" + app.getID() + ";");
				}
			} else {
				stmt.executeUpdate("DELETE FROM ListOfApps WHERE idProfile="
						+ userID + ";");
			}



			// Add new Kid relation
			if (selectedKids != null) {
				ArrayList<Profile> kidToDelete = findKidLists();
				for (int i = 0; i < selectedKids.length; i++) {
					if (!selectedKids[i].equals("-1"))
						stmt.executeUpdate("INSERT into HasGuardian values("
								+ userID + "," + selectedKids[i] + ");");
				}
				// Delete kid relation
				out.println("To delete");
				for (Profile pro : kidToDelete) {
					stmt.executeUpdate("DELETE FROM HasGuardian WHERE idGuardian="
							+ userID + " and idChild=" + pro.getID() + ";");
				}
			} else {
				stmt.executeUpdate("DELETE FROM HasGuardian WHERE idGuardian="
						+ userID + ";");
			}

			// Add guardian
			if (isChild) {
				if (selectedGuards != null) {
					ArrayList<Profile> guardToDelete = findKidLists();
					try {


						for (int i = 0; i < selectedGuards.length; i++) {
							if (!selectedGuards[i].equals("-1"))
								stmt.executeUpdate("INSERT into HasGuardian values("
										+ selectedGuards[i] + "," + userID + ");");
						}
					}
					catch (Exception e) {

					}
					// Delete kid relation
					out.println("To delete");
					for (Profile pro : guardToDelete) {
						stmt.executeUpdate("DELETE FROM HasGuardian WHERE idChild="
								+ userID
								+ " and idGuardian="
								+ pro.getID()
								+ ";");
					}
				} else {
					stmt.executeUpdate("DELETE FROM HasGuardian WHERE idChild="
							+ userID + ";");
				}

				if (selectedParents != null) {
					ArrayList<Profile> guardToDelete = findKidLists();
					try {
						for (int i = 0; i < selectedParents.length; i++) {
							if (!selectedParents[i].equals("-1"))
								stmt.executeUpdate("INSERT into HasGuardian values("
										+ selectedParents[i] + "," + userID + ");");
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					// Delete kid relation
					out.println("PARENTS!");
					for (Profile pro : guardToDelete) {
						stmt.executeUpdate("DELETE FROM HasGuardian WHERE idChild="
								+ userID + " and idParent=" + pro.getID() + ";");
					}
				} else {
					stmt.executeUpdate("DELETE FROM HasGuardian WHERE idChild="
							+ userID + ";");
				}
			}

			// END MONDAY STUFF

			con.commit();
			if (middlename == null || middlename.equals(""))
				middlename = "";
			session.setAttribute("SYSTEMMESSAGE", "<font color=green>"+firstname + " "+ middlename + " " + surname + " redigeret korrekt</font>");

			//response.sendRedirect("TestDatabase");

		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records. " + tempMessage, e);
		} catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally {
			// Clean up.
			try {
				if (con != null)
					con.close();
			} catch (SQLException ignored) {
			}
		}

		session.setAttribute("SYSTEMMESSAGE", "Bruger redigeret!");
		response.sendRedirect("TestDatabase");
		//out.println("Selected: ");
		//for (int i = 0; i< selectedApps.length; i++)
		//{
		//	out.println(selectedApps[i]);
		//}

	}

	// doGet(request, response);

	/**
	 * Clean lists, to confirm which departments to add or delete Note: Monday
	 * code
	 */
	public ArrayList<Department> findDepLists() {
		// To add:
		for (int j = 0; j < selectedDeps.length; j++) {
			for (int i = 0; i < currentDepartments.size(); i++) {
				if (currentDepartments.get(i).getID() == Integer
						.parseInt(selectedDeps[j])) {

					selectedDeps[j] = "-1";
				}
			}
		}

		// To delete:
		ArrayList<Department> depToDelte = new ArrayList<Department>();

		if (unselectedDeps != null) {
			for (int j = 0; j < unselectedDeps.length; j++) {
				for (int i = 0; i < currentDepartments.size(); i++) {
					if (currentDepartments.get(i).getID() == Integer
							.parseInt(unselectedDeps[j])) {

						depToDelte.add(currentDepartments.get(i));
					}
				}
			}
		}

		return depToDelte;
	}

	public ArrayList<App> findAppsList()
	{

		ArrayList<App> appToDelete = new ArrayList<App>();

		if (selectedApps != null) {
			// To delete:

			for (int j = 0; j< possibleApps.size(); j++)
			{
				boolean found = false;
				for (int i = 0; i<selectedApps.length;i++)
				{
					//out.println("Looking at: " + possibleApps.get(j).getID() + " vs " + selectedApps[i] +"<br>");
					if (Integer.parseInt(selectedApps[i]) == possibleApps.get(j).getID())
					{
						found = true;
						break;
					}
					else
						found = false;
				}
				if (!found)
				{
					//out.println("Adding " + possibleApps.get(j).getName() + " to delete <br>");
					appToDelete.add(possibleApps.get(j));
				}
			}

			// To add:

			for (int j = 0; j < selectedApps.length; j++) {
				for (int i = 0; i < currentApps.size(); i++) {
					if (currentApps.get(i).getID() == Integer
							.parseInt(selectedApps[j])) {

						selectedApps[j] = "-1";
					}
				}
			}



		}
		return appToDelete;
	}


	/**
	 * Clean lists, to confirm which departments to add or delete Note: Monday
	 * code
	 */
	public ArrayList<Profile> findKidLists() {
		// To add:
		if (selectedKids != null) {
			for (int j = 0; j < selectedKids.length; j++) {
				for (int i = 0; i < currentChild.size(); i++) {
					if (currentChild.get(i).getID() == Integer
							.parseInt(selectedKids[j])) {

						selectedKids[j] = "-1";
					}
				}
			}
		}

		// To delete:
		ArrayList<Profile> kidToDelte = new ArrayList<Profile>();

		if (unselectedKids != null) {
			for (int j = 0; j < unselectedKids.length; j++) {
				for (int i = 0; i < currentChild.size(); i++) {
					out.println("Looking at 'unselectedkid' "
							+ unselectedKids[j] + " vs CurrentChild "
							+ currentChild.get(i));
					if (currentChild.get(i).getID() == Integer
							.parseInt(unselectedKids[j])) {
						out.println("REMOIVE");
						kidToDelte.add(currentChild.get(i));
					}
				}
			}
		}

		return kidToDelte;
	}
}
