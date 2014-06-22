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

import baseConnection.PlaceManager;

/**
 * Servlet implementation class updatePlaceProfileServlet
 */
@WebServlet("/updatePlaceProfileServlet")
public class updatePlaceProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatePlaceProfileServlet() {
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
		
		String name = request.getParameter("name");
		
		String pageN = request.getParameter("PlacePageNum");
		Integer pageNum = 1;
		if(pageN != null) pageNum = Integer.parseInt(pageN);
		request.setAttribute("PlacePageNum", pageNum);
		 
		int imageID = 0;
		try {
			imageID = placeManager.getPlaceImageID(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		placeManager.changeProfileImage(placeID, imageID);
		 
		
		RequestDispatcher dispatch = request.getRequestDispatcher("place.jsp");
		dispatch.forward(request, response);
	}

}
