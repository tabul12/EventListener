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
		
		userManager.addUser(firstName, lastName, userName, password, mail, "default.jpg", mobileNumber);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("homePage.jsp");
		dispatch.forward(request, response);
	}

}
