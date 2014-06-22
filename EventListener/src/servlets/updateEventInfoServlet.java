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

import baseConnection.EventManager;

/**
 * Servlet implementation class updateEventInfoServlet
 */
@WebServlet("/updateEventInfoServlet")
public class updateEventInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateEventInfoServlet() {
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

		ServletContext context = request.getServletContext();
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		HttpSession session = request.getSession();
		Integer eventID = (Integer)session.getAttribute("EventID");
		
		String name = request.getParameter("Name");
		String about = request.getParameter("About");
		String price = request.getParameter("Price");
		String time = request.getParameter("Time");
		
		String pageN = request.getParameter("EventPageNum");
		Integer pageNum = 1;
		if(pageN != null) pageNum = Integer.parseInt(pageN);
		request.setAttribute("EventPageNum", pageNum);
		
		eventManager.updateInfo(eventID, name, time, about, price);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("event.jsp");
		dispatch.forward(request, response);
			
	}

}
