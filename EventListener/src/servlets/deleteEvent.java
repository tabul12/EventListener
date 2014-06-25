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
import baseConnection.UserManager;

/**
 * Servlet implementation class deleteEvent
 */
@WebServlet("/deleteEvent")
public class deleteEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteEvent() {
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
		 
		String st = request.getParameter("EventID");
		ServletContext context = request.getServletContext();
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		
		Integer eventID = 0;
		if(st != null)
				eventID = Integer.parseInt(st); 
		int userID = 0;
		try {
			 userID = eventManager.getAuthorID(eventID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userManager.punishUser(userID);
		userManager.deleteEvent(eventID);
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("homePage.jsp");
		dispatch.forward(request, response);
		
	}

}
