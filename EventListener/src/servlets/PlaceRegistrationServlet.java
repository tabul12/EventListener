package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import baseConnection.PlaceManager;

/**
 * Servlet implementation class BandRegistrationServlet
 */
@WebServlet("/PlaceRegistrationServlet")
public class PlaceRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceRegistrationServlet() {
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
		PlaceManager placeManager = (PlaceManager) context.getAttribute("PlaceManager");
		String placeName = request.getParameter("PlaceName");
		String about = request.getParameter("About");	
		String adress = request.getParameter("Adress");
		int userID = (Integer)session.getAttribute("UserID");
		placeManager.addPlace(userID, placeName, adress, about);
		RequestDispatcher dispatch = request.getRequestDispatcher("userPage.jsp");
		dispatch.forward(request, response);
	}

}
