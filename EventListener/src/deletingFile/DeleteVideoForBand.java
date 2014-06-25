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
 * Servlet implementation class DeleteVideoForBand
 */
@WebServlet("/DeleteVideoForBand")
public class DeleteVideoForBand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteVideoForBand() {
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
		System.out.println(Path+FileName+" esaa videos misamarti");
		//vtvlit rom default is nomeri 1 ia
		System.out.print(bandManager.deleteVideo(FileName));
		File f = new File(Path+FileName);
		f.delete();
		
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
