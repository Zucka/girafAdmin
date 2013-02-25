

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
 * Servlet implementation class DeleteTags
 */
@WebServlet("/DeleteTags")
public class DeleteTags extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int idTag;
	String caption;
	ArrayList<Tag> tags = new ArrayList<Tag>();
	ArrayList<Tag> selectedTags = new ArrayList<Tag>();
	ArrayList<Tag> searchTags = new ArrayList<Tag>();
	String searchCriteria;
	String searchString;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteTags() {
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

		try
		{
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
			}
			else
				rs = stmt.executeQuery("SELECT * from Tags");

			while (rs.next()) {
				boolean inList = false;
				// out.print("\t\t\t");
				int idTag = rs.getInt("idTags");
				String caption = rs.getString("caption");
				if(!doSearch)
				{
					for (Tag t : tags) {
						if (t.getID() == idTag)
							inList = true;

					}
				}
				if (doSearch)
				{
					for (Tag t : searchTags) {
						if (t.getID() == idTag)
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
		}
		catch (SQLException e) {
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
		out.println("<head><title>Slet tag</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");
		out.println("<script src=\"javascript/popup.js\"></script>");
		out.println("<script language=JavaScript>" +
				"function deleteTag11(){" +
				//"alert(\"Pressed\");"+
				"document.forms[\"deleteTag\"].submit();" +
				"}" +
				"</script>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"mainBackground\">");
		out.println("<center><h2> Vælg de tags du ønsker at slette </h2>");
		out.println("<hr>");
		out.println("<div id=\"simple_wrapper\">"+
		"<div id=\"edit_wrapper\">");
		out.println("<center><form method='POST' name='deleteTag' action='DeleteTags'>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<table>");
		out.println("<tr>");
		if(!doSearch)
		{
			for (int i=0; i<tags.size();i++)
			{

				if(i > 0 &&  i % 7 ==0)
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

				if(i > 0 && i % 7 ==0)
					out.println("</tr><tr>");

				if (searchTags.get(i).getSelected())
					out.println("<td><input type=checkbox name='selectedTags' checked='yes' value='"+searchTags.get(i).getID()+"'><br>"+searchTags.get(i).getCaption()+"</td>");
				else
					out.println("<td><input type=checkbox name='selectedTags' value='"+searchTags.get(i).getID()+"'><br>"+searchTags.get(i).getCaption()+"</td>");
			}
			for (int i=0; i<tags.size();i++)
			{
				if (tags.get(i).getSelected())	
					out.println("<input type=hidden name='selectedTags' value='"+tags.get(i).getID()+"'></td>");
			}
		}
		out.println("</tr></table>");
		//out.println("<tr><td></td><td align=center><a href='#' onClick=\"errorMessage(); popup('popUpSearch')\" >Søg</a></td></tr>");
		out.println("<tr><td><input type='button' name='Delete' onClick=\"deleteTag11()\" value='Slet' /></td></tr>");

		/*out.println(""
				+ "<div id=\"blanket\" style=\"display:none;\"></div>"
				+ "<div id=\"popUpSearch\" style=\"display:none;\">"
				+ "<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpSearch')\" ALIGN=RIGHT>[X]</a></p>"
				+ "<center><h3>"
				+ "Søg tag:</h3>"
				+ "<br>"
				+ "<input type='radio' name='criteria' value='begins' checked/>Begynder med<br />"
				+ "<input type='radio' name='criteria' value='contains'/>Indeholder<br />"
				+ "<br>"
				+ "<input type=text name='serchString'>"
				+ "<br>"
				+ "<input type=submit name='search' value='Søg'>"
				+ "<input type=button name='clearsearch' value='Slet' onClick=\"clearSearch();\">"
				+ "</h3>"
				+ "</form></div>");*/
		out.println("</table></div>"
				+"<div id=\"my_wrapper\"></div></div>");
		out.println("<hr>");
		out.println("<footer>Savannah v. 1.0.0 <a href='http://en.wikipedia.org/wiki/Copyleft'>(C)opyleft</a> under Freedom 3 me!</footer> </div>");
		out.println("</form></center>");
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
		out.println("POST");
		searchCriteria = request.getParameter("criteria");
		searchString = request.getParameter("serchString");
		ArrayList<HasTag> hasTag = new ArrayList<HasTag>();
		Connection con = null;
		Statement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		boolean exists = false;
		Tag t = null;
		
		String Tags[] = request.getParameterValues("selectedTags");
		out.println(Tags.length);
		if (Tags != null && Tags.length > 0)
		{
			out.println("1");
			for (int i=0; i<Tags.length;i++)
			{
				//out.println(Tags[i]);
				try{
					Class.forName("org.gjt.mm.mysql.Driver");
					//connects to our server with user: eder and password: 123456
					con = DriverManager.getConnection("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
					
					stmt = con.createStatement();
					rs = stmt.executeQuery("select idTag from HasTag;");
					
					//out.println("2");
					while(rs.next()){
						int idTag = rs.getInt("idTag");
						HasTag ht = new HasTag(idTag);
						//out.println("Adding " + ht.getIdTag());
						hasTag.add(ht);
					}
						
					for(HasTag ht: hasTag){
						if(ht.getIdTag() == Integer.parseInt(Tags[i])){
							
							stmt1 = con.createStatement();
							rs1 = stmt1.executeQuery("select caption from Tags where idTags = " + Tags[i] + ";");
							
							while(rs1.next()){
								String caption = rs1.getString("caption");
								t = new Tag(caption);
							}
							out.println("Der er et eller flere billeder der benytter tagget: " + t.getCaption() + " og kan derfor ikke slettes");
							exists = true;
							break;
						}
					}
					
					if(exists == false){
			
						out.println("TROLOLOLO");
						
						//This line executes the query to delete from Authusers table where idUser matches the userID that we get from the selected profile	
						PreparedStatement ps = (PreparedStatement) con.prepareStatement("delete from Tags where idTags = " + Tags[i] +";");
						out.println("Deleting " + Tags[i]);
						int j = ps.executeUpdate();
						//this if-statement is not working properly 
						if (j == 0){
							out.println(Tags[i] + " has been deleted");
						
						}
						else{
							//out.println(Tags[i] + " has been deleted");
							tags.clear();
							selectedTags.clear();
							searchTags.clear();
							response.sendRedirect("TestDatabase");
							//out.println(i);
						}
					}
					exists = false;
				}
				catch(Exception e){
					out.println("The execution is " + e);
				}
				
				session.setAttribute("SYSTEMMESSAGE", "<font color='green'>Tag slettet</font>");
				response.sendRedirect("TestDatabase");
			}
		}
		
		
	}

}
