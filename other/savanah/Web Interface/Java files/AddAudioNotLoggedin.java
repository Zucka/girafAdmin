

import java.io.File;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.catalina.Session;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class AddPictureNotLoggedin
 */
@WebServlet("/AddAudioNotLoggedin")
public class AddAudioNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> appList = new ArrayList<String>();
	private static final String TMP_DIR_PATH = "/tmp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH = "/audio/appAudio";
	private File destinationDir;
	String imageName;
	String userID;
	String name;
	String mPublic;
	String mType;
	String owner;
	String searchCriteria;
	String searchString;


	ArrayList<Tag> tags = new ArrayList<Tag>();
	ArrayList<Tag> searchTags = new ArrayList<Tag>();
	ArrayList<String> selectedTags = new ArrayList<String>(); 

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
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAudioNotLoggedin() {
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
		boolean doSearch = false;
		String userID= (String) session.getAttribute("PROFILEIDTOADD");
		String profileName = null;

		//String searchCriteria = (String) request.getAttribute("CRITERIA");
		//String searchString = (String) request.getAttribute("SEARCHSTRING");
		searchTags.clear();

		if (searchCriteria != null && searchString != null && !searchString.equals(""))
		{
			doSearch = true;
		}
		else
			doSearch = false;


		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
			stmt = con.createStatement();
			if (doSearch)
			{
				if (searchCriteria.equals("begins"))
					rs = stmt.executeQuery("SELECT * from Tags where caption like '"+searchString+"%';");
				else if (searchCriteria.equals("contains"))
					rs = stmt.executeQuery("SELECT * from Tags where caption like '%"+searchString+"%';");
				else if (searchCriteria.equals("ends"))
					rs = stmt.executeQuery("SELECT * from Tags where caption like '%"+searchString+"';");
			}
			else
				rs = stmt.executeQuery("SELECT * from Tags");
			// displaying records
			while (rs.next()) {
				boolean inList = false;
				// out.print("\t\t\t");
				int idTag = rs.getInt("idTags");
				String caption = rs.getString("caption");
				if(!doSearch)
				{
					for (Tag tag : tags) {
						if (tag.getID() == idTag)
							inList = true;

					}
				}
				if (doSearch)
				{
					for (Tag tag : searchTags) {
						if (tag.getID() == idTag)
							inList = true;

					}
				}

				if(!inList && doSearch)
					searchTags.add(new Tag(idTag, caption));
				else if (!inList && !doSearch)
					tags.add(new Tag(idTag, caption));
				else
					inList = false;
				// out.print("<br>");
			}
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT * from Profile where idProfile = " +userID);
			// displaying records
			while (rs1.next()) {
				// out.print("\t\t\t");
				profileName = rs1.getString("firstname");
				// out.print("<br>");
			}
			if (profileName == null)
			{
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT * from Department where idDepartment = " +userID);
				// displaying records
				while (rs2.next()) {
					// out.print("\t\t\t");
					profileName = rs2.getString("name");
					// out.print("<br>");
				}
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

		//selectedTags = request.getParameterValues("selectedTags");
		int maxSize;

		if(selectedTags != null)
		{			
			if (!doSearch)
			{
				for (int j=0; j <tags.size(); j++)
				{
					boolean found = false;
					for (int i =0; i<selectedTags.size();i++)
					{
						if (selectedTags.get(i).equals(Integer.toString(tags.get(j).getID())))
						{
							found = true;
							tags.get(j).setSelected(true);
						}

					}
					if (!found)
						tags.get(j).setSelected(false);
				}
			}
			else
			{
				for (int j=0; j <searchTags.size(); j++)
				{
					boolean found = false;
					for (int i =0; i<selectedTags.size();i++)
					{
						if (selectedTags.get(i).equals(Integer.toString(searchTags.get(j).getID())))
						{
							found = true;
							searchTags.get(j).setSelected(true);
						}
					}
					if (!found && doSearch)
						searchTags.get(j).setSelected(false);
				}
				for (int j=0; j <tags.size(); j++)
				{
					boolean found = false;
					for (int i =0; i<selectedTags.size();i++)
					{
						if (selectedTags.get(i).equals(Integer.toString(tags.get(j).getID())))
						{
							found = true;
							tags.get(j).setSelected(true);
						}

					}
					if (!found)
						tags.get(j).setSelected(false);
				}
			}
		}
		else
		{
			if (doSearch)
				maxSize = searchTags.size();
			else
				maxSize = tags.size();

			for (int j=0; j <maxSize; j++)
			{
				if (!doSearch)
				{
					tags.get(j).setSelected(false);
				}
				if (doSearch)
				{
					searchTags.get(j).setSelected(false);
				}

			}
		}


		out.println("<html>");
		out.println("<head><title>Tilføj lyd</title>");
		out.println("<script src=\"javascript/popup.js\">"
				+ "</script>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");
		out.println("<SCRIPT language = JavaScript>"+
				"var reader = new FileReader();"+
				"reader.onload = function(e) {"+
				"var audioElement = document.getElementById('billedet');"+
				"alert(audioElement);"+
				"audioElement.setAttribute('src', e.target.result);"+
				"var picName = addForm1.file1.value;"+
				"var picName1 = picName.replace(\"C:\\\\fakepath\\\\\",\"\");" +
				"var picName2 = picName1.replace(\".jpg\",\"\");"+
				"addForm1.txtName.value = picName2;"+
				"};"+

"function readURL(input){"+ 
"if(input.files && input.files[0]){"+
"reader.readAsDataURL(input.files[0]);"+
"}"+
"else {"+
"document.billedet.src = input.value || \"No file selected\";"+
"}"+
"}" +
"function clearSearch()" +
"{" +
"addForm1.serchString.value ='-1';"+
"document.forms[\"addForm1\"].submit();"+
"}"+
"function errorMessage()" +
"{" +
"alert(\"Bemærk: Der er en lille fejl i systemet, så billedet skal vælges på ny hvis du søger eller tilføjer tags\");"+
"}"+
				"</script>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id='MainBackground'>");
		out.println("<center><h2>Tilføj lyd</h2></center>");
		out.println("<hr>");
		out.println("<div id='generic_wrapper'>");
		out.println("<center>");
		if (session.getAttribute("PICTUREMESSAGE") != null)
		{
			out.println(session.getAttribute("PICTUREMESSAGE")+"<br>");
			session.removeAttribute("PICTUREMESSAGE");
		}
		out.println("<form method='POST' name='addForm1' enctype='multipart/form-data' action='AddAudioNotLoggedin'>");
		out.println("<table>");

		out.println("<tr>"
				+ "<td align='center' colspan='2'><audio controls=\"controls\" src=\"\" id='billedet' name=\"billedet\"/>"
+"Your browser does not support this audio"
+"</audio></td>"
				+ "</tr>");
		out.println("<tr>" +
				"<td colspan='2' align=center><input name='file1' type='file' accept='audio/*' onChange=\"readURL(this);\"/></td>"
				+"</tr>");
		out.println("<tr>" +
				"<td>" +
				"Navn:" +
				"</td>" +
				"<td>" +
				"<input type='text' name='txtName'>" +
				"</td>" +
				"</tr>");
		//out.println("<tr><td><input type=checkbox name='isPublic' value='on' />Offentlig</td></tr>");
		out.println("<tr><td><select name=\"isPublic\">" +
				"<option value=\"1\">Offentlig</option>" +
				"<option value=\"0\">Privat</option>" +
				"</select></td></tr>");
		out.println("<tr>"+
				"<td>Tags:");		
		out.println("</td><td>");



		out.println("<div id='boxOfTags'>");
		out.println("<table>" +
				"<tr>");
		if(!doSearch)
		{
			for (int i=0; i<tags.size();i++)
			{

				if(i % 3 ==0)
					out.println("</tr><tr>");

				if (tags.get(i).getSelected())
					out.println("<td><input type=checkbox name='selectedTags' checked='yes' value='"+tags.get(i).getID()+"'><br>"+tags.get(i).getCaption()+"</td>");
				else
					out.println("<td><input type=checkbox name='selectedTags' value='"+tags.get(i).getID()+"'><br>"+tags.get(i).getCaption()+"</td>");
			}
		}
		else
		{
			for (int i=0; i<searchTags.size();i++)
			{

				if(i % 3 ==0)
					out.println("</tr><tr>");

				if (searchTags.get(i).getSelected())
					out.println("<td><input type=checkbox name='selectedTags' checked='yes' value='"+searchTags.get(i).getID()+"'><br>"+searchTags.get(i).getCaption()+"</td>");
				else
					out.println("<td><input type=checkbox name='selectedTags' value='"+searchTags.get(i).getID()+"'><br>"+searchTags.get(i).getCaption()+"</td>");
			}
			for (int i=0; i<tags.size();i++)
			{
				if (tags.get(i).getSelected())	
					out.println("<input type=hidden name='selectedTags' value='"+tags.get(i).getID()+"'>");
			}
		}



		out.println("</td></tr></table></div>" +
				"<tr><td></td><td align=center><a href='#' onClick=\"errorMessage(); popup('popUpSearch')\">Søg</a> - <a href='#' onClick=\"errorMessage(); popup('popUpDiv')\">Tilføj</a></td></tr></tr>");
		out.println("<tr><td>Ejer:</td><td><input type=Text disabled='true' value='"+profileName+"' /></td></tr>");
		out.println("<tr><td><input type='submit' name='send' value='Tilføj!'></td><td><input type='button' name='send' value='Fortryd' onclick=\"javascript:window.location = \'TestDatabase\';\"></td></tr>");
		out.println("</table></div></td>");
		out.println("</table>");

		out.println("</center>");

		out.println("<hr>");
		out.println("</div>");
		out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer>");
		out.println("</div>");


		out.println(""
				+ "<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpSearch\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpSearch')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center><h3>"
				+ "Søg tag:</h3>"
				+ "<br>"
				+ "<input type='radio' name='criteria' value='begins' checked/>Begynder med<br />"
				+ "<input type='radio' name='criteria' value='contains'/>Indeholder<br />"
				+ "<input type='radio' name='criteria' value='ends'/>Slutter med<br />"
				+ "<br>"
				+ "<input type=text name='serchString'>"
				+ "<br>"
				+ "<input type=submit name='search' value='Søg'>"
				+ "<input type=button name='clearsearch' value='Slet' onClick=\"clearSearch();\">"
				+ "</h3>"
				+ "</form></div>");

		out.println(""
				+ "<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpDiv\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpDiv')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<form method='POST' action='AddNewTag' name='TagForm'>"
				+ "<center><h3>"
				+ "Tilføj tag:"
				+ "<br>"
				+ "<input type=text name='newTag'>"
				+ "<br>"
				+ "<input type=submit values='Tilføj tag'>"
				+ "</h3>"
				+ "</form>"+

				"</center>" + "</div>");



		out.println("</body>");
		out.println("</html>");
		session.setAttribute("FIRSTTIME","nope");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		String token = "";
		int mediaID= -1;

		boolean writeFile = true;
		searchCriteria = request.getParameter("criteria");
		searchString = request.getParameter("serchString");
		FileItem temp = null;
		try {
			/*
			 * Parse the request
			 */
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				{
					FileItem item = (FileItem) itr.next();
					/*
					 * Handle Form Fields.
					 */
					if (item.isFormField()) {

						if (item.getFieldName().equals("criteria"))
							searchCriteria = item.getString();
						if (item.getFieldName().equals("serchString"))
							searchString = item.getString();
						if (item.getFieldName().equals("selectedTags"))
							selectedTags.add(item.getString());


						userID= (String) session.getAttribute("PROFILEIDTOADD");


						if (item.getFieldName().equals("txtName"))
							name = item.getString();
						owner = userID;
						mType = "1";

						if (item.getFieldName().equals("isPublic"))
						{
							mPublic = item.getString();
						}



						if (searchCriteria != null && searchString != null && !searchString.equals(""))
						{

							if (searchString.equals("-1"))
							{
								searchString = null;
								searchCriteria = null;
							}
							writeFile = false;
							response.sendRedirect("AddAudioNotLoggedin");
							break;
						}
						else
							writeFile = true;
					}
					else
					{

						temp = item;


					}
				}
			}



		} catch (FileUploadException ex) {
			log("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log("Error encountered while uploading file", ex);
		}


		if (temp.getName() == null || temp.getName().equals(""))
		{
			writeFile = false;
			session.setAttribute("PICTUREMESSAGE", "<font color='red'>Ingen lyd valgt</font>");
			response.sendRedirect("AddAudioNotLoggedin");
		}
		
		if (writeFile)
		{
			Connection con = null;


			Random r = new Random();

			token = Long.toString(Math.abs(r.nextLong()), 36);
			token = token + ".mp3";
			File file = new File(destinationDir, token);
			try {
				temp.write(file);





				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(
						"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");


				PreparedStatement pst = con
						.prepareStatement("insert into Media values(?,?,?,?,?,?)");
				pst.setString(1, null);
				pst.setString(2, "/audio/appAudio/"+token);
				pst.setString(3, name);
				pst.setString(4, mPublic);
				pst.setString(5, mType);
				pst.setString(6, userID);

				int i = pst.executeUpdate();

				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from Media where mPath = '"
								+ "/audio/appAudio/"+token + "';");

				while (rs.next()) {
					mediaID = rs.getInt("idMedia");
				}


				for (String s : selectedTags) {
					PreparedStatement pst1 = con
							.prepareStatement("insert into HasTag values(?,?)");
					pst1.setInt(1, mediaID);
					pst1.setString(2, s);
					pst1.executeUpdate();
				}
			}
			catch (SQLException e) {
				throw new ServletException(e.getMessage()
						+ " Servlet Could not display records. " + temp, e);
			} catch (ClassNotFoundException e) {
				throw new ServletException("JDBC Driver not found.", e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (SQLException e) {

				}

			}

			
			searchCriteria = null;
			searchString = null;
			searchTags.clear();
			selectedTags.clear();

			session.setAttribute("PICTUREMESSAGE","<font color='green'>Lyd tilføjet</font>");
			response.sendRedirect("AddAudioNotLoggedin");

		}

	}

}