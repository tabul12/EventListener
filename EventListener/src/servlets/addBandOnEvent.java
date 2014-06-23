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

import errors.BaseErrors;
import baseConnection.BandManager;
import baseConnection.EventManager;

/**
 * Servlet implementation class addBandOnEvent
 */
@WebServlet("/addBandOnEvent")
public class addBandOnEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addBandOnEvent() {
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
		
		String bandName = request.getParameter("BandName");
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		BandManager bandManager = (BandManager) context.getAttribute("BandManager");
		EventManager eventManager = (EventManager) context.getAttribute("EventManager");
		
		Integer eventID = (Integer) session.getAttribute("EventID");
		if(eventID == null) eventID = 0;
		int bandID = 0;
		try {
			 bandID = bandManager.getBandID(bandName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean thisBandIsAlreadyAdded = false;
		
		try {
			 thisBandIsAlreadyAdded = eventManager.bandIsAlreadyAdded(bandID, eventID);
		} catch (SQLException e) {
			
		}
		
		
		
		
		if(bandID == 0){
			request.setAttribute("NoSuchBand", BaseErrors.NO_SUCH_BAND);
		}
		
		else {
			if(thisBandIsAlreadyAdded){
				request.setAttribute("ThisBandIsAlreadyAdded", BaseErrors.THIS_BAND_IS_ALREADY_ADDED);
			} else eventManager.addBand(eventID, bandID);
		}
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
		dispatch.forward(request, response);
	}

}
