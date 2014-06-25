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

import baseConnection.BandManager;
import baseConnection.EventManager;
import baseConnection.UserManager;

/**
 * Servlet implementation class deleteBand
 */
@WebServlet("/deleteBand")
public class deleteBand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteBand() {
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
		String st = request.getParameter("BandID");
		ServletContext context = request.getServletContext();
		BandManager bandManager = (BandManager) context.getAttribute("BandManager");
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		
		Integer bandID = 0;
		if(st != null)
			bandID = Integer.parseInt(st); 
		int userID = 0;
		try {
			 userID = bandManager.getAuthorID(bandID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userManager.punishUser(userID);
		userManager.deleteBand(bandID);
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("homePage.jsp");
		dispatch.forward(request, response);
	}

}
