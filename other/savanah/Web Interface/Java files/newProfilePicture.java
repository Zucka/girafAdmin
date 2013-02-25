

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class newProfilePicture
 */
@WebServlet("/newProfilePicture")
public class newProfilePicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> appList = new ArrayList<String>();
	private static final String TMP_DIR_PATH = "/tmp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH = "/images";
	private File destinationDir;
	String oldpic;
	String userID;



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
	public newProfilePicture() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		ServletContext context = getServletContext();
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		/*
		 * Set the size threshold, above which content will be stored on disk.
		 */
		// fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		/*
		 * Set the temporary directory to store the uploaded files of size above
		 * threshold.
		 */





		Random r = new Random();
		String token = Long.toString(Math.abs(r.nextLong()), 36);
		token = token + ".jpg";
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
					if (item.getFieldName().equals("userID"))
						userID = item.getString();
					if (item.getFieldName().equals("oldPic"))
					{
						if (oldpic != null && !oldpic.equals(""))
							oldpic = oldpic.replaceAll("/TestDatabase/", "");
						String path = getServletContext().getRealPath(oldpic);   
						File deleteFile = new File(path);  
						deleteFile.delete();
					}
					else
					{
						oldpic = null;
					}


				} 
				else {


					/*
					 * Write file to the ultimate location.
					 */
					File file = new File(destinationDir, token);
					item.write(file);
				}

			}
		} catch (FileUploadException ex) {
			log("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log("Error encountered while uploading file", ex);
		}

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			// Turn on transactions

			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE Profile SET picture = '"
					+ "/images/" + token + "' WHERE idProfile ="
					+ userID + " ;");

		}
		catch (SQLException e) {
			throw new ServletException("Servlet Could not display records. ", e);
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
		session.setAttribute("EDITPICTUREMESSAGE", "<font color=green>Billede ændret</font>");
		response.sendRedirect("EditProfileNotLoggedin");

	}
}
