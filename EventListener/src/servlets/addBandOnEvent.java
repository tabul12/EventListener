package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Band;
import objects.Event;
import objects.Place;
import objects.User;
import sendMail.SendEmail;
import errors.BaseErrors;
import baseConnection.BandManager;
import baseConnection.EventManager;
import baseConnection.PlaceManager;
import baseConnection.UserManager;

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
		bandID = bandManager.getBandID(bandName);
		
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
			} else 
			{
				eventManager.addBand(eventID, bandID);
				try {
					ArrayList<Integer> fans = bandManager.getFansArrayForBand(bandID);
					SendEmail mailsen = (SendEmail) context.getAttribute("SendEmail");
					UserManager userManager = (UserManager) context.getAttribute("UserManager");
					PlaceManager placeManager = (PlaceManager) context.getAttribute("PlaceManager");
					Band band = bandManager.getBand(bandID);
					Event event = eventManager.getEvent(eventID);
					Place place = placeManager.getPlace(event.getPlaceID());
					for(int i = 0; i< fans.size(); i++)
					{
						User fanUser = userManager.getUser(fans.get(i));
						String To = fanUser.getMail();
						String subject = "Band from Your Wishlist";
						String mailtext = band.getName()+"- has a concert at "+ place.getName()+", Event-Time: " + event.getTime();
						mailsen.sendMailFromE(To, subject, mailtext);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		RequestDispatcher	dispatch = request.getRequestDispatcher("event.jsp");
		dispatch.forward(request, response);
	}

}
