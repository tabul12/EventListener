package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import baseConnection.UserManager;
import sun.rmi.server.Dispatcher;
 

/**
 * Servlet implementation class LoggingIn
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		ServletContext context = request.getServletContext();
		
		 
		String userName = request.getParameter("UserName");
		String password = request.getParameter("Password");
		
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		int userID = 0;
		try {
			 userID = userManager.getUserID(userName, password);
		} catch (SQLException e1) {
		}
		boolean exists = false;
		
		try {
			exists = userManager.userExists(userName, password);
		} catch (SQLException e) {
			 
		}
		
		
		if( exists ) {
			session.setAttribute("UserExists", BaseErrors.CORRECT_USERNAME_AND_PASSWORD); 
			session.setAttribute("UserID", userID);		 	
		} else {
			session.setAttribute("UserExists", BaseErrors.IN_CORRECT_USERNAME_OR_PASSWORD);
		}
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("homePage.jsp");
		dispatch.forward(request, response);	
		
	}

}
