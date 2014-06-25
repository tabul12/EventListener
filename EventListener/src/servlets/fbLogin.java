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

/**
 * Servlet implementation class fbLogin
 */
@WebServlet("/fbLogin")
public class fbLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fbLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =  request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String userName = request.getParameter("id");
       
        
        ServletContext context = request.getServletContext();
        UserManager userManager = (UserManager) context.getAttribute("UserManager");
        HttpSession session = request.getSession();
        
        int addUser = 0;
        
        try {
			addUser = userManager.getVIPUserID(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(addUser == BaseErrors.NO_SUCH_USER){
        	userManager.addUser(name, lastname, userName, "", email, "default.jpg", "");
        	try {
				int userID = userManager.getVIPUserID(userName);
				
				session.setAttribute("UserID", userID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        } else {
        	session.setAttribute("UserID", addUser);
        }       
        RequestDispatcher dispatch = request.getRequestDispatcher("homePage.jsp");
		dispatch.forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response); 
	}

}
