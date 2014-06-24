package uploading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import baseConnection.BandManager;


/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final static Logger LOGGER = 
	            Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
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
	    final String path = request.getParameter("destination");
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);
	    System.out.println(fileName+"  esaa failis saxeli");
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
	    String typeFile = request.getParameter("typeFile");
	    System.out.println("------------servletshi  "+typeFile);
	    if(typeFile.equals("bandImages"))
	    {
	    	String bandID = request.getParameter("BandID");
	    	int BandID = Integer.parseInt(bandID);
			ServletContext context = getServletContext();
			BandManager bandManager=(BandManager) context.getAttribute("BandManager");
			bandManager.addImage(BandID, fileName);
			forw ="BandProfile.jsp?BandID="+BandID;

	    }   
	    if(typeFile.equals("bandMusics"))
	    {
	    	String bandID = request.getParameter("BandID");
	    	System.out.println("----------jnjnjnjjn--servletshi");
	    	int BandID = Integer.parseInt(bandID);
			ServletContext context = getServletContext();
			BandManager bandManager=(BandManager) context.getAttribute("BandManager");
			bandManager.addMusic(BandID,fileName);
			forw ="BandProfile.jsp?BandID="+BandID;
	    }
	    if(typeFile.equals("bandVideos"))
	    {
	    	String bandID = request.getParameter("BandID");
	    	int BandID = Integer.parseInt(bandID);
			ServletContext context = getServletContext();
			BandManager bandManager=(BandManager) context.getAttribute("BandManager");
			System.out.println(bandManager.addVideo(BandID, fileName)+" videoos shecdomaaaa");
			forw ="BandProfile.jsp?BandID="+BandID;
	    }
	    response.getWriter().println("<p>File Uploaded<p>");
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
