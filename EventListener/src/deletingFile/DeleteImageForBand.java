package deletingFile;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import baseConnection.BandManager;

/**
 * Servlet implementation class DeleteFileServlet
 */
@WebServlet("/DeleteImageForBand")
public class DeleteImageForBand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteImageForBand() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getServletContext();
		BandManager bandManager = (BandManager) context.getAttribute("BandManager");
		 
		
		String st = request.getParameter("BandID");
		Integer bandID = Integer.parseInt(st);
		String Path = request.getParameter("Path");
		String FileName = request.getParameter("FileName");
		File f = new File(Path+FileName);
		f.delete();
		System.out.println(FileName+" esssaaa saxeli");
		if(bandManager.getProfileImage(bandID).equals(FileName))
		{
			//vtvlit rom default is nomeri 1 ia
			bandManager.updateProfileImage(bandID,1);
		}
		bandManager.deleteImage(FileName);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("BandProfile.jsp");
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
