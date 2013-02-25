import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jdt.internal.compiler.ast.BinaryExpression;


import com.sun.java_cup.internal.runtime.Scanner;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

/**
 * Servlet implementation class AddProfile
 */

@WebServlet(
		name = "AddProfile", 
		urlPatterns = {"/AddProfile"}
		)
public class AddProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> appList = new ArrayList<String>();
	private static final String TMP_DIR_PATH = "/tmp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH = "/images";
	private File destinationDir;
	String imageName;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if (!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
		String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		destinationDir = new File(realPath);
		if (!destinationDir.isDirectory()) {
			throw new ServletException(DESTINATION_DIR_PATH
					+ " is not a directory");
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		ArrayList<Department> departments = new ArrayList<Department>();
		ArrayList<App> apps = new ArrayList<App>();
		ArrayList<App> appLink = new ArrayList<App>();
		appList.clear();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from Department;");
			// displaying records
			while (rs.next()) {
				int dId = rs.getInt("idDepartment");
				String dName = rs.getString("name");
				String dAddress = rs.getString("address");
				int dPhone = rs.getInt("phone");
				String dEmail = rs.getString("email");
				Department dept = new Department(dId, dName, dAddress, dPhone,
						dEmail);
				departments.add(dept);
			}

			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("select * from Apps;");
			// displaying records
			while (rs1.next()) {
				int appIP = rs1.getInt("idApp");
				String appName = rs1.getString("name");
				String appVersion = rs1.getString("version");
				App app = new App(appIP, appName, appVersion);
				apps.add(app);
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


		out.println(""
				+ "<html>"
				+ "<head>"
				+ "<title>Savannah 1.0 - Tilføj profil</title>"
				+ "<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />"
				+ "</head>" + "<body>" + "<script type='text/javascript'>"
				+ "function updateTryToAdd()" + "{"
				+ "document.addForm1.tryToAdd.value = '1';" + "}"
				+ "function submitform()" + "{" + "document.addForm1.submit();"
				+ "}" + "</script>" + "<div id=\"mainBackground\">"
				+ "<script>" + "fillOutForm();" + "</script>"
				+ "<center><h2> Tilføj profil</h2><hr></center>");
		out.println("<div id=\"generic_wrapper\">");
		String userOutput = (String) session.getAttribute("ADDPROFILERESPONSE");

		if (userOutput != null && !userOutput.equals(""))
		{
			out.print(userOutput + "<br>");
			session.removeAttribute("ADDPROFILERESPONSE");
		}

		/*
		 * if(appList != null) { for (int i=0;i<appList.length; i++) {
		 * out.println("<li>" + appList[i]); } }
		 */

		out.println("<table border='0'>"
				+ "<form method='POST' name='addForm1' enctype='multipart/form-data' action='AddProfile'>");
		if (!departments.isEmpty()) {
			out.println("<tr>" + "<td>Afdeling</td><td>"
					+ "<select name='dept'>");
			for (Department d : departments) {
				out.println("<option value='" + d.getID() + "'");
				// if(deptField.equals(String.valueOf(d.getID())))
				// out.print(" selected='true' " );
				out.print("'>" + d.getName());
			}
			out.println("</select>" + "</tr>");
		}
		out.println("<tr>"
				+ "<td>Navn:</td> <td><input type='text' name='firstname' value=''/>");

		// if (firstname != null && firstname.equals("") &&
		// hasTriedToAdd.equals("1"))
		// out.println("<font color='red'>Indtast navn</font>");

		out.println("</td></tr>"
				+ "<tr>"
				+ "<td>Mellemnavn(e):</td> <td> <input type='text' name='middlename' value='' /> </td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Efternavn: </td><td><input type='text' name='surname'value='' />");

		// if (surname != null && surname.equals("") &&
		// hasTriedToAdd.equals("1"))
		// out.println("<font color='red'>Indtast efternavn</font>");

		out.println("</td></tr>"
				+ "<tr>"
				+ "<td>Telefon nummer: </td> <td> <input type='text' name='phone' value='' />");

		// if (phone != null && phone.equals("") && hasTriedToAdd.equals("1"))
		// out.println("<font color='red'>Indtast telefon nummer</font>");

		out.println("</td></tr>" + "<tr>" + "<td><select name='prole'>"
				+ "<option value='0' ");
		// if(role.equals("0"))
		// out.print("selected='true'");
		out.print(">Administrator</option>" + "<option value='1' ");
		// if(role.equals("1"))
		// out.print("selected='true'");
		out.print(">Pedagog</option>" + "<option value='2' ");
		// if(role.equals("2"))
		// out.print("selected='true'");
		out.print(">Forældre</option>" + "<option value='3' ");
		// if(role.equals("3"))
		// out.print("selected='true'");
		out.print(">Barn</option>"
				+ "</select></td> "
				+ "</tr>"
				+ "<tr>"
				+ "<td><input type='hidden' name='MAX_FILE_SIZE' value='100' />"
				+ "<input name='file1' type='file' value='null'/></td> "
				+ "</tr>"
				+ "<tr>"
				+ "<td>Brugernavn: </td><td><input type='text' name='username' value='' />");
		// if (username != null && username.equals("") &&
		// hasTriedToAdd.equals("1"))
		// out.println("<font color='red'>Indtast brugernavn</font>");
		out.println("</td></tr>"
				+ "<tr>"
				+ "<td>Kodeord:</td><td> <input type='password' name='password' />");
		// if (password != null && password.equals("") &&
		// hasTriedToAdd.equals("1"))
		// out.println("<font color='red'>Indtast kodeord</font>");
		out.println("</td></tr>"
				+ "<tr>"
				+ "<td>Gentag kodeord:</td> <td> <input type='password' name='password2' />");
		// if (password2 != null && password2.equals("") &&
		// hasTriedToAdd.equals("1"))
		// out.println("<font color='red'>Indtast kodeord</font>");
		out.println("</td></tr>" + "<tr>" + "<td><hr></td><td><hr></td>"
				+ "</tr>" + "<tr>" + "<td>Adgang til apps</td><td>");
		for (App app : apps) {
			if (appLink.contains(app.getID())) {
				out.println("<input type='checkbox' name=\"appList\" value=\""
						+ app.getID() + "\" checked='true'/>" + app.getName()
						+ "<br>");
			} else {
				out.println("<input type='checkbox' name=\"appList\" value=\""
						+ app.getID() + "\"/>" + app.getName() + "<br>");
			}

		}
		out.println("</td></tr>"
				+ "<tr>"
				+ "<td></td><td><input type='submit' value='Tilføj'>"
				+ // onClick=\"updateTryToAdd(); submitform();\" value='Tilføj'>"
				// +
				"<input type='button' value='Fortryd'></td>" + "</tr>"
				+ "<input type='hidden' name='tryToAdd' value='0'>" + "</form>"

				+ "</table>" + "<br><br><br></div><hr>");
		out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> </div>");

		out.println( "</div></body>" + "</html>");
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String tempFile = null;
		ServletContext context = getServletContext();
		PrintWriter out = response.getWriter();
		String qrToken = getNewCertificate();
		HttpSession session = request.getSession();

		String dept = request.getParameter("dept");
		
		String firstname = request.getParameter("firstname");
		if (firstname == null)
			firstname = "";
		String middlename = request.getParameter("middlename");
		if (middlename == null)
			middlename = "";
		String surname = request.getParameter("surname");
		if (surname == null)
			surname = "";
		String phone = request.getParameter("phone");
		if (phone == null)
			phone = "";
		String role = request.getParameter("prole");
		if (role == null)
			role = "0";
		String deptField = String.valueOf((request.getParameter("dept")));
		if (deptField == null)
			deptField = "";
		String file1 = request.getParameter("file");
		if (file1 == null)
			file1 = "";
		String username = request.getParameter("username");
		if (username == null)
			username = "";
		String password = request.getParameter("password");
		if (password == null)
			password = "";
		String password2 = request.getParameter("password2");
		if (password2 == null)
			password2 = "";
		String hasTriedToAdd = request.getParameter("tryToAdd");

		Random r = new Random();

		String token = Long.toString(Math.abs(r.nextLong()), 36);
		token = token + ".jpg";

		// / TEST CODE!
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		/*
		 * Set the size threshold, above which content will be stored on disk.
		 */
		// fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		/*
		 * Set the temporary directory to store the uploaded files of size above
		 * threshold.
		 */
		fileItemFactory.setRepository(tmpDir);

		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			/*
			 * Parse the request
			 */
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				/*
				 * Handle Form Fields.
				 */
				if (item.isFormField()) {

					if (item.getFieldName().equals("dept"))
						dept = item.getString();
					if (item.getFieldName().equals("firstname"))
						firstname = item.getString();
					if (item.getFieldName().equals("middlename"))
						middlename = item.getString();
					if (item.getFieldName().equals("surname"))
						surname = item.getString();
					if (item.getFieldName().equals("phone"))
						phone = item.getString();
					if (item.getFieldName().equals("prole"))
						role = item.getString();
					if (item.getFieldName().equals("dept"))
						deptField = item.getString();
					if (item.getFieldName().equals("username"))
						username = item.getString();
					if (item.getFieldName().equals("password"))
						password = item.getString();
					if (item.getFieldName().equals("password2"))
						password2 = item.getString();
					if (item.getFieldName().equals("appList"))
						appList.add(item.getString());

				} else {


					/*
					 * Write file to the ultimate location.
					 */
					tempFile = item.getName();
					if (tempFile != null && !tempFile.equals(""))
					{	
						File file = new File(destinationDir, token);
						item.write(file);
					}
				}

			}
		} catch (FileUploadException ex) {
			log("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log("Error encountered while uploading file", ex);
		}
		// END TEST CODE

		// appList = request.getParameterValues("appList");
		// doGet(aRequest, aResponse);
		
		try {
			int phoneint = Integer.parseInt(phone);
		} catch (NumberFormatException  e) {
			// TODO: handle exception
			session.setAttribute("ADDPROFILERESPONSE", "<font color=red>Telefon nummer må kun indeholde tal</font>)");
			response.sendRedirect("AddProfile");
			return;
		}
		
		int tempId = -1;
		int temp = -1;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			Statement stmt0 = con.createStatement();
			String tempUsername = null;
			ResultSet rs0 = stmt0
					.executeQuery("select * from AuthUsers where username = '"
							+ username + "';");
			while (rs0.next()) {
				tempUsername = rs0.getString("username");
			}

			if (tempUsername != null) {
				session.setAttribute("ADDPROFILERESPONSE",
						"<font color='red'>Brugernavnet " + username
						+ " findes allerede</font>");
				firstname = "";
				middlename = "";
				surname = "";
				role = "";
				file1 = "";
				username = "";
				password = "";
				password2 = "";
				deptField = "";
				phone = "";
				response.sendRedirect("AddProfile");
			} else {


				PreparedStatement pst = con
						.prepareStatement("insert into AuthUsers values(?,?,?,?,?)");
				pst.setString(1, qrToken);
				pst.setString(2, null);
				pst.setString(3, role);
				pst.setString(4, username);
				pst.setString(5, password);

				int i = pst.executeUpdate();
				

				con = DriverManager.getConnection(
						"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("select * from AuthUsers where username = '"
								+ username + "';");

				while (rs.next()) {
					tempId = rs.getInt("idUser");
				}

				PreparedStatement pst1 = con
						.prepareStatement("insert into Profile values(?,?,?,?,?,?,?,?)");
				if (middlename.equals(""))
					middlename = null;

				String imageFileLocation = "" + DESTINATION_DIR_PATH + ""
						+ imageName;
				pst1.setInt(1, tempId);
				pst1.setString(2, firstname);
				pst1.setString(3, surname);
				pst1.setString(4, middlename);
				pst1.setInt(5, Integer.parseInt(role));
				pst1.setString(6, phone);
				
				if (tempFile == null || tempFile.equals("") || tempFile.equals(" "))
					pst1.setString(7, null);
				else
					pst1.setString(7, "/images/" + token);
				
				session.setAttribute("QRNAME", firstname + " " + middlename + " " + surname);
				pst1.setString(8, null);

				

				int i1 = pst1.executeUpdate();
				
				PreparedStatement pst11 = con
						.prepareStatement("insert into HasDepartment values(?,?);");

				pst11.setInt(1, tempId);
				pst11.setString(2, dept);
		

				

				int i11 = pst11.executeUpdate();

				PreparedStatement pst2 = con
						.prepareStatement("insert into ListOfApps values(?,?,?,?)");
				for (int appId = 0; appId < appList.size(); appId++) {
					if (appList.get(appId) != null) {
						byte[] byteArray = { 1, 2, 3, 4, 5 };
						Blob myBlob = new SerialBlob(byteArray);

						temp = Integer.parseInt(appList.get(appId));
						pst2.setInt(1, temp);
						pst2.setInt(2, tempId);
						pst2.setBlob(3, myBlob);
						pst2.setBlob(4, myBlob);

						pst2.executeUpdate();

					}
				}

				session.setAttribute("ADDPROFILERESPONSE", imageFileLocation
						+ "<font color='green'>" + firstname + " " + surname
						+ " oprettet</font>");
				firstname = "";
				middlename = "";
				surname = "";
				role = "";
				file1 = "";
				username = "";
				password = "";
				password2 = "";
				deptField = "";
				phone = "";
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage()
					+ " Servlet Could not display records. " + temp, e);
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

		session.setAttribute("QRTOKEN",qrToken );

		response.sendRedirect("generateQR");

	}

	/**
	 * @return a certificate
	 */
	private String getNewCertificate() {
		Random rnd = new Random();
		String certificate = "";
		for (int i = 0; i < 256 + 1; i++)
		{
			certificate += (char)(rnd.nextInt(26) + 97);
		}
		return certificate;
	}

}
