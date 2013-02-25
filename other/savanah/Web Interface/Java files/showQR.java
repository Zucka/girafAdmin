

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

/**
 * Servlet implementation class showQR
 */
@WebServlet("/showQR")
public class showQR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public showQR() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessoin = request.getSession();
		ServletContext context = getServletContext();
		PrintWriter out = response.getWriter();
		String user = (String) sessoin.getAttribute("QRNAME");
		sessoin.removeAttribute("QRNAME");

		out.println("<html>" +
				"<head>" +
				"<title>Din QR kode</title>" +
				"<Style type='text/css' media='print'>"+
				"#above {display : none}"+
				"#below {display : none}"+
				"</Style>"+
				"<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />"+
				"</head>" +

				"<body>" +
				"<center><h3>QR kode for "+user+":</h3><br>" +
				"<br>"+
				"<img src='"+context.getContextPath()+"/generateQR/"+user+"qr.gif'>" +
				"<br>" +
				"<Span id='below'>" +

				"<A HREF='javascript:window.print()'>Udskriv</A>   -   <a href=\"TestDatabase\">Hjem</a>"+
				"</span>"+
				"</body></html>");
		String path = getServletContext().getRealPath(context.getContextPath()+"/generateQR/"+user+"qr.gif");   
		File deleteFile = new File(path);  
		deleteFile.delete();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
