package uploading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import errors.ConstantValues;
import baseConnection.BandManager;
import baseConnection.EventManager;
import baseConnection.PlaceManager;
import baseConnection.UserManager;

/**
 * Servlet implementation class ChangeProfilePicture
 */

@WebServlet(name = "ChangeProfilePicture", urlPatterns = {"/ChangeProfilePicture"})
@MultipartConfig

public class ChangeProfilePicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final static Logger LOGGER = 
	            Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeProfilePicture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
	    final String path = ConstantValues.PATH_TO_IMAGES;
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);
	    OutputStream out = null;
	    InputStream filecontent = null;

	    try {
	        out = new FileOutputStream(new File(path + File.separator
	                + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	   
	        LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
	                new Object[]{fileName, path});
	    } catch (FileNotFoundException fne) {

	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
	                new Object[]{fne.getMessage()});
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
	    String  forw ="";//tu rame shecdoma moxda gadavides mtavar gverdze
	    String typeProfile = request.getParameter("typeProfile");
	    System.out.println(typeProfile+" jjjjjjjjjjjjjjjjjj");
	    if(typeProfile.equals("UserProfile"))
	    {
	    	System.out.println(fileName);
			ServletContext context = getServletContext();
			UserManager userManager=(UserManager) context.getAttribute("UserManager");
			HttpSession sesion = request.getSession();
			int userID = (Integer)sesion.getAttribute("UserID");
			System.out.println(userID+"--------userid");
			System.out.println(userManager.changeProfilePicture(userID,fileName));
			forw ="userPage.jsp";

	    }   
	    if(typeProfile.equals("EventProfile"))
	    {
	    	String eventID = request.getParameter("EventID");
	    	int EventID = Integer.parseInt(eventID);
			ServletContext context = getServletContext();
			EventManager eventManager=(EventManager) context.getAttribute("EventManager");
		    eventManager.changeProfilePicture(EventID,fileName);
			forw ="event.jsp?EventID="+EventID;
	    }
	    response.getWriter().println("<p>Profile Picture Has Changed <p>");
	    response.getWriter().println("<a href='"+forw+"' "+">Click Here To Go Back</a>");
	    
	    //RequestDispatcher	dispatch = request.getRequestDispatcher(forw);
		//dispatch.forward(request, response);	
	}
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
