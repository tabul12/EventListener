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
import baseConnection.EventManager;
import baseConnection.UserManager;

/**
 * Servlet implementation class UserAttendsEventServlet
 */
@WebServlet("/UserAttendsEventServlet")
public class UserAttendsEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAttendsEventServlet() {
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
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		int userID = (Integer) session.getAttribute("UserID");
		int eventID = (Integer) session.getAttribute("EventID");

		String pageN = (String) request.getParameter("EventPageNum");
		
		System.out.println(pageN + " kdmakm ");
		Integer pageNum = 1;
		if(pageN != null) pageNum = Integer.parseInt(pageN);
		request.setAttribute("EventPageNum", pageNum);
		
		
		
		try {
			if(eventManager.userAttendsEvent(userID, eventID) == BaseErrors.USER_ALREADY_ATTENDS_EVENT){
				request.setAttribute("UserAlreadyAttendsEvent", BaseErrors.USER_ALREADY_ATTENDS_EVENT);
			} else request.setAttribute("UserAlreadyAttendsEvent", BaseErrors.ALL_DONE);
			RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
			dispatch.forward(request, response);	
		} catch (SQLException e) {
			
		}
		
	}

}
