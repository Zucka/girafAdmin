

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

/**
 * Servlet implementation class TestSound
 */
@WebServlet("/TestSound")
public class TestSound extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestSound() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String context = request.getContextPath();
		String file = "";
		// connecting to database
				Connection con = null;
				Statement stmt = null;
				ResultSet rs = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection(
							"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT * from Media where mType = 1;");
					// displaying records
					while (rs.next()) {
						file = context+  rs.getString("mPath");
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
				out.println("<html><head><title>Tester</title></head><body>" +
						"<audio controls=\"controls\" src=\""+file+"\" type=\"audio/mpeg\" /></audio></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
