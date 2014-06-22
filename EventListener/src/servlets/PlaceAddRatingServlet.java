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
import baseConnection.EventManager;
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
		int eventID = (Integer) session.getAttribute("EventID");
		String st = (String) request.getParameter("name");
		Integer placeID = Integer.parseInt(st);
		
		st = request.getParameter("value");
		
		 
		int score = Integer.parseInt(st);
		
		
		
		PlaceManager manager = (PlaceManager) context.getAttribute("PlaceManager");
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		
		try {
			if(!eventManager.userAlreadyAttendsEvent(userID, eventID)){
				request.setAttribute("UserCantRatePlace", BaseErrors.USER_DOES_NOT_ATTEND_EVENT);
				RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
				System.out.println(" ar unda daamatos ");
				
				 
				dispatch.forward(request, response);
				return;
			}
		} catch (SQLException e1) {
			 
		}
		
		try {
			if(manager.addRate(userID, placeID, eventID, score) == BaseErrors.USER_ALREADY_RATED_PLACE){
				request.setAttribute("Rated", BaseErrors.USER_ALREADY_RATED_PLACE); 	
				System.out.println("you have already rated place");
			} else request.setAttribute("Rated", BaseErrors.ALL_DONE); 
			RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
			dispatch.forward(request, response);	 
		} catch (SQLException e) {
			
		}
		
	}

}
