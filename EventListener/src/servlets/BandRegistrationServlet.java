package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import baseConnection.BandManager;

/**
 * Servlet implementation class BandRegistrationServlet
 */
@WebServlet("/BandRegistrationServlet")
public class BandRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BandRegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		
		BandManager bandManager = (BandManager) context.getAttribute("BandManager");
		String bandName = request.getParameter("BandName");
		String mail = request.getParameter("Mail");
		String about = request.getParameter("About");		
		int userID = (Integer)session.getAttribute("UserID");
		int ban = 0;
		try {
			ban = bandManager.getBandID(bandName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ban > 0){
			RequestDispatcher dispatch = request.getRequestDispatcher("bandRegister.jsp?id=34");
			dispatch.forward(request, response);
		}else{
			bandManager.addBand(userID, bandName, about, mail);
			RequestDispatcher dispatch = request.getRequestDispatcher("userPage.jsp");
			int bandID = 0;
			try {
				bandID = bandManager.getBandID(bandName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bandManager.addProfileImage(bandID, 1);
			dispatch.forward(request, response);
		}
		
	}

}
