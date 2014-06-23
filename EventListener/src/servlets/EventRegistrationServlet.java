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

import baseConnection.EventManager;
import baseConnection.PlaceManager;

/**
 * Servlet implementation class BandRegistrationServlet
 */
@WebServlet("/EventRegistrationServlet")
public class EventRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventRegistrationServlet() {
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
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		PlaceManager placeManager = (PlaceManager) context.getAttribute("PlaceManager");
		String eventName = request.getParameter("EventName");
		String placeName = request.getParameter("Place");
		String day = request.getParameter("Day");
		String month = request.getParameter("Month");
		String year = request.getParameter("Year");
		String time = day + "-"+ month +"-" +year; 
		String price = request.getParameter("Price");
		String about = request.getParameter("About");	
		String image = request.getParameter("Image");	
		int userID = (Integer)session.getAttribute("UserID");
		Integer placeID;
		try {		
			placeID = placeManager.getPlaceID(placeName);
			if(placeID == 0){
				RequestDispatcher dispatch = request.getRequestDispatcher("eventRegister.jsp?id=33");
				dispatch.forward(request, response);
			}else{
				eventManager.addEvent(userID, placeID, eventName,time,about,price,image);
				RequestDispatcher dispatch = request.getRequestDispatcher("userPage.jsp");
				dispatch.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
