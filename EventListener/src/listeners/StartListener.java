package listeners;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import sendMail.SendEmail;
import baseConnection.BandManager;
import baseConnection.EventConnectionPool;
import baseConnection.EventManager;
import baseConnection.PlaceManager;
import baseConnection.UserManager;

/**
 * Application Lifecycle Listener implementation class StartListener
 *
 */
@WebListener
public class StartListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public StartListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	try {
			EventConnectionPool evConPool = new EventConnectionPool();
			BasicDataSource dataSource = evConPool.getEventDataSource();
			UserManager uManager = new UserManager(dataSource);
			BandManager bManager = new BandManager(dataSource);
			PlaceManager pManager =  new PlaceManager(dataSource);
			EventManager eManager = new EventManager(dataSource);
			sendMail.SendEmail  SendMail = new SendEmail();
			ServletContext context = arg0.getServletContext();
			context.setAttribute("UserManager", uManager);
			context.setAttribute("BandManager", bManager);
			context.setAttribute("PlaceManager", pManager);
			context.setAttribute("EventManager", eManager);
			context.setAttribute("SendEmail", SendMail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
