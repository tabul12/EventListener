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
import baseConnection.PlaceManager;

/**
 * Servlet implementation class updatePlaceInfoServlet
 */
@WebServlet("/updatePlaceInfoServlet")
public class updatePlaceInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatePlaceInfoServlet() {
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
		PlaceManager placeManager = (PlaceManager) context.getAttribute("PlaceManager");
		HttpSession session = request.getSession();
		Integer placeID = (Integer)session.getAttribute("PlaceID");
		
		String pageN = (String) request.getParameter("PlacePageNum");
		
		System.out.println(pageN + " kdmakm ");
		Integer pageNum = 1;
		if(pageN != null) pageNum = Integer.parseInt(pageN);
		request.setAttribute("PlacePageNum", pageNum);
		
		String name = request.getParameter("Name");
		String about = request.getParameter("About");
		String adress = request.getParameter("Adress");
		 
		placeManager.updateInfo(placeID, name, adress, about);
		 
		
		RequestDispatcher dispatch = request.getRequestDispatcher("place.jsp");
		dispatch.forward(request, response);
	}

}
