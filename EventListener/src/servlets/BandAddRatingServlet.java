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
import baseConnection.EventManager;
import baseConnection.PlaceManager;
import errors.BaseErrors;

/**
 * Servlet implementation class BandAddRatingServlet
 */
@WebServlet("/BandAddRatingServlet")
public class BandAddRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BandAddRatingServlet() {
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
		System.out.println("you have already rated BAND");
		HttpSession session = request.getSession();
		ServletContext context  = request.getServletContext();
		int userID = (Integer) session.getAttribute("UserID");
		String st = (String) request.getParameter("name");
		int eventID = (Integer) session.getAttribute("EventID");
		Integer bandID = Integer.parseInt(st);
		
		st = request.getParameter("value");
		
		String pageN = request.getParameter("EventPageNum");
		Integer pageNum = 1;
		if(pageN != null) pageNum = Integer.parseInt(pageN);
		request.setAttribute("EventPageNum", pageNum);
		 
		int score = Integer.parseInt(st);
		
		 
		BandManager manager = (BandManager) context.getAttribute("BandManager");
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		
		try {
			if(!eventManager.userAlreadyAttendsEvent(userID, eventID)){
				request.setAttribute("UserCantRateBand", BaseErrors.USER_DOES_NOT_ATTEND_EVENT);
				RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
				System.out.println("ar eswreba am ivents");
				dispatch.forward(request, response);
				return;
			}
		} catch (SQLException e1) {
			 
		}
		
		try {
			if(manager.addRating(userID, bandID, eventID, score) == BaseErrors.USER_ALREADY_RATED_BAND){
				request.setAttribute("Rated", BaseErrors.USER_ALREADY_RATED_BAND); 	
			} else request.setAttribute("Rated", BaseErrors.ALL_DONE); 
			RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
			dispatch.forward(request, response);	 
		} catch (SQLException e) {
			
		}
	}

}
