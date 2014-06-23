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
 * Servlet implementation class addToWishlist
 */
@WebServlet("/addToWishlist")
public class addToWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addToWishlist() {
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
		System.out.println("  add to wishlist servlet aqamde movida");
		String userst = request.getParameter("UserID");
		String bandst = request.getParameter("BandID");
		
		int userID = Integer.parseInt(userst);
		int bandID = Integer.parseInt(bandst);
		
		ServletContext context = request.getServletContext();
		UserManager manager = (UserManager) context.getAttribute("UserManager");
		manager.addInWishList(userID, bandID);
		System.out.println(userID + " user and band " + bandID); 
		request.setAttribute("BandID", bandID);
		RequestDispatcher dispatch = request.getRequestDispatcher("BandProfile.jsp");
		dispatch.forward(request, response);
	}

}
