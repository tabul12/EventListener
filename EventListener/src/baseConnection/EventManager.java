package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Event;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import errors.BaseErrors;

public class EventManager {
private BasicDataSource eventDataSource;
	
	public EventManager(BasicDataSource connectionPool){
		this.eventDataSource = connectionPool;
	}

	/*
	 * this method constructs and returns event object
	 * on specified ID
	 */
	public Event getEvent(int eventID) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		
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
	
	/*
	 * this method returns The ID of the event
	 * we pass it image Name of this event because
	 * image Name is unic
	 */
	public int getEventID(String eventImageName) throws SQLException{
		String query = "select ID from Event where Image='" + eventImageName + "';";
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);
		System.out.println(query);
		boolean is = result.next();
		if(is) return result.getInt("ID");
		return 0;
	}
	/*
	 * this method adds new event
	 * it constructs query string and passes to changeBase method
	 */
	public int addEvent(int userID,int placeID,String time,String about,String price,String image){
		 
		String query = "insert into Event(UserID,placeID,Time,About,Price,Image)"
				+ "values(" + userID + "," + placeID + ",'" + time + "','" + about +"','" + 
				price + "','" + image +"');";
		System.out.println(query);
		return changeBase(query);		 
	}
	
	
	
	/*
	 * this method updates info about specified event
	 * it constructs query string and passes it to changeBase method
	 */
	public int updateInfo(int eventID,int placeID,String time,String about,String price,String image){
		String query = "update Event "
				+ "set PlaceId=" + placeID + ", Time='" + time + "', About='" + about + "', "
						+ "Price='" + price + "', Image='" + image + "' "
								+ "where ID=" + eventID + ";";
		 
		return changeBase(query);
	}
	
	/*
	 * this method returns list of ID of users, who goes on this 
	 * event 
	 */
	public ArrayList<Integer> getGoingUsers(int eventID) throws SQLException{
		String query = "select UserID from User_Going_Event "
				+ "where EventID=" + eventID + ";";
		
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		while(result.next()){
			list.add(result.getInt("UserID"));
		} 
		
		return list;
	}
	
	/*
	 * this method returns list of bands
	 * which plays on this event
	 */
	public ArrayList<Integer> getBandsOnEvent(int eventID) throws SQLException{
		String query = "select BandID from Band_On_Event" +
					" where EventID=" + eventID + ";"; 
		
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		while(result.next()){
			list.add(result.getInt("BandID"));
		} 
		
		return list;
	}
	
	/*
	 * this method return boolean about 
	 * this userd has added this event or not
	 */
	public boolean hasAdded(int userID,int eventID) throws SQLException{
		String query = "select ID from Event " +
						"where UserID=" + userID + " and  ID=" + eventID + ";";
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		System.out.println(query);
		ResultSet result = stmt.executeQuery(query);
		 
		return result.next();
	}
	
	/*
	 * this method gets query string
	 * and executes it
	 */
	public int changeBase(String query){
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = eventDataSource.getConnection();
			try {
				stmt = connection.createStatement();
				 
				try {
					stmt.executeUpdate(query);
				} catch (Exception e) {
					connection.close();
					return BaseErrors.UNABLE_EXECUTE;
				}		
				connection.close();
			} catch (Exception e) {
				connection.close();
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
		} catch (SQLException e){
			return BaseErrors.UNABLE_CONNECTION;
		}
		return BaseErrors.ALL_DONE;
	}
	
	/*
	 * this method returns latest num count events
	 * from newer to older
	 */
	public ArrayList<Integer> getLatestEvents(int num) throws SQLException{
		String query = "select ID from Event order by ID desc limit " + num + ";";
		
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		System.out.println(query);
		ResultSet result = stmt.executeQuery(query);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		while(result.next()){
			list.add(result.getInt("ID"));
		} 
		
		return list;
	}
	
}
