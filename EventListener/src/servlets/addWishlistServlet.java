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

import errors.BaseErrors;
import baseConnection.UserManager;

/**
 * Servlet implementation class addWishlistServlet
 */
@WebServlet("/addWishlistServlet")
public class addWishlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addWishlistServlet() {
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
		
		String bandst = request.getParameter("BandID");
		String userst = request.getParameter("UserID");
		int bandID = Integer.parseInt(bandst);
		int userID = Integer.parseInt(userst);
		
		ServletContext context = request.getServletContext();
		UserManager userManager = (UserManager) context.getAttribute("UserManager");
		
		boolean alreadyAdded = false;
		try {
			alreadyAdded = userManager.alreadyAddedToWishlist(userID, bandID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(alreadyAdded){
			request.setAttribute("AlreadyAddedToWishlist", BaseErrors.ALREADY_ADDED_TO_WISHLIST);
			
		} else userManager.addInWishList(userID, bandID);
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("BandProfile.jsp");
		dispatch.forward(request, response);
	}

}
