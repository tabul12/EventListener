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

import errors.BaseErrors;
import baseConnection.PlaceManager;

/**
 * Servlet implementation class PlaceAddRatingServlet
 */
@WebServlet("/PlaceAddRatingServlet")
public class PlaceAddRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceAddRatingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext context  = request.getServletContext();
		int userID = (Integer) session.getAttribute("UserID");
		System.out.println(request.getAttribute("PlaceID"));
		int placeID = (Integer) request.getAttribute("PlaceID");
		
		String st = request.getParameter("value");
		int score = Integer.parseInt(st);
		
		System.out.println("aq var rac var" + session.getAttribute("EventID"));
		Integer eventID = (Integer) session.getAttribute("EventID");
		PlaceManager manager = (PlaceManager) context.getAttribute("PlaceManager");
		
		try {
			if(manager.addRate(userID, placeID, eventID, score) == BaseErrors.USER_ALREADY_RATED_PLACE){
				request.setAttribute("Rated", BaseErrors.USER_ALREADY_RATED_PLACE); 				 
			} else request.setAttribute("Rated", BaseErrors.ALL_DONE); 
			RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
			dispatch.forward(request, response);	 
		} catch (SQLException e) {
			
		}
		
	}

}
