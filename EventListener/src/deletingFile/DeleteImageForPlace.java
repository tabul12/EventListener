package deletingFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import baseConnection.BandManager;
import baseConnection.PlaceManager;

/**
 * Servlet implementation class DeleteImageForPlace
 */
@WebServlet("/DeleteImageForPlace")
public class DeleteImageForPlace extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getServletContext();
		PlaceManager placeManager = (PlaceManager) context.getAttribute("PlaceManager");
		 
		
		String st = request.getParameter("PlaceID");
		Integer placeID = Integer.parseInt(st);
		String Path = request.getParameter("Path");
		String FileName = request.getParameter("FileName");
		File f = new File(Path+FileName);
		f.delete();
		try {
			if(placeManager.getProfileImage(placeID).equals(FileName))
			{
				//vtvlit rom default is nomeri 1 ia
				placeManager.changeProfileImage(placeID, "default.jpg");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		placeManager.deleteImage(FileName);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("place.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
