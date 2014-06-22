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

import baseConnection.UserManager;

/**
 * Servlet implementation class BandRegistrationServlet
 */
@WebServlet("/UpdateUserInfoServlet")
public class UpdateUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserInfoServlet() {
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
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");	
		String mail = request.getParameter("Mail");	
		String mobileNumber = request.getParameter("MobileNumber");	
		String password = request.getParameter("Password");
		int userID = (Integer)session.getAttribute("UserID");
		userManager.updateInfo(userID, firstName, lastName, mail, mobileNumber,password);
		RequestDispatcher dispatch = request.getRequestDispatcher("userPage.jsp");
		dispatch.forward(request, response);
	}

}
