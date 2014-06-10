package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objects.Event;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class EventManager {
private BasicDataSource connectionPool;
	
	public EventManager(BasicDataSource connectionPool){
		this.connectionPool = connectionPool;
	}

	public Event getEvent(int eventID) throws SQLException{
		Connection connection = connectionPool.getConnection();
		Statement stmt = connection.createStatement();
		stmt.execute("Use database ev");
		
		String query = "select * from Event where Event.ID=" + eventID + ";";
		
		ResultSet result = stmt.executeQuery(query);
		
		 
		int userID = 0;
		int placeID = 0;
		String time = "";
		String about = "";
		String price = "";
		String image = "";
		
		if(result.next()){
			userID = result.getInt("UserID");
			placeID = result.getInt("PlaceID");
			time = result.getString("Time");
			about = result.getString("About");
			price = result.getString("Price");
			image = result.getString("Image");
		}
		
		connection.close();
		Event event = new Event(eventID, userID, placeID, time, about, price, image);
		return event;
	}
}
