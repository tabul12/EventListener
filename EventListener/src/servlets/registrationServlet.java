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
import errors.ConstantValues;
import sun.rmi.server.Dispatcher;
import baseConnection.UserManager;

/**
 * Servlet implementation class registrationServlet
 */
@WebServlet("/registrationServlet")
public class registrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registrationServlet() {
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
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String userName = request.getParameter("UserName");
		String password = request.getParameter("Password");
		String mail = request.getParameter("Mail");
		String mobileNumber = request.getParameter("MobileNumber");
		
		 
		
		boolean exists = false;
		try {
			 exists = userManager.alreadyExists(userName);
		} catch (SQLException e) {
			
		}
		
		 
		if(exists){
				session.setAttribute("UserNameAlreadyUsed", BaseErrors.USER_NAME_ALREADY_USED);
				RequestDispatcher dispatch = request.getRequestDispatcher("register.jsp");
				dispatch.forward(request, response);
		} else {			 	
				userManager.addUser(firstName, lastName, userName, password, mail, "default.jpg", mobileNumber);
				try {
					int userID = userManager.getUserID(userName, password);
					session.setAttribute("UserID", userID);
				} catch (SQLException e) { 
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RequestDispatcher	dispatch = request.getRequestDispatcher("homePage.jsp");
				dispatch.forward(request, response);	
		}
			 
		
		 
	}

}
