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

import baseConnection.EventManager;
import baseConnection.PlaceManager;
import baseConnection.UserManager;

/**
 * Servlet implementation class deletePlace
 */
@WebServlet("/deletePlace")
public class deletePlace extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deletePlace() {
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
		String st = request.getParameter("PlaceID");
		ServletContext context = request.getServletContext();
		PlaceManager placeManager = (PlaceManager) context.getAttribute("PlaceManager");
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		
		Integer placeID = 0;
		 
		if(st != null)
				placeID = Integer.parseInt(st); 
		int userID = 0;
		try {
			 userID = placeManager.getAuthorID(placeID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(placeID);
		userManager.punishUser(userID);
		userManager.deletePlace(placeID);
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("homePage.jsp");
		dispatch.forward(request, response);
	}

}
