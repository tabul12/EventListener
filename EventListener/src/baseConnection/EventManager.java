package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Event;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import errors.BaseErrors;
import errors.ConstantValues;

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
		Connection connection = null;
		int userID = 0;
		int placeID = 0;
		String name = "";
		String time = "";
		String about = "";
		String price = "";
		String image = "";
		Event event = null;
		try {
			connection=eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			String query = "select * from Event where Event.ID=" + eventID + ";";
			
			ResultSet result = stmt.executeQuery(query);
			
			if(result.next()){
				userID = result.getInt("UserID");
				placeID = result.getInt("PlaceID");
				name = result.getString("Name");
				time = result.getString("Time");
				about = result.getString("About");
				price = result.getString("Price");
				image = result.getString("Image");
				event = new Event(eventID, userID, placeID, name, time, about, price, image);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(connection!= null)
			connection.close();
		}
		return event;
	}
	
	 
	
	/*
	 * this method returns The ID of the event
	 * we pass it image Name of this event because
	 * image Name is unic
	 */
	public int getEventID(String eventName) throws SQLException{
		String query = "select ID from Event where Name='" + eventName + "';";
		Connection connection = null;
		int ans = 0;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			 
			 
			if(result.next()) ans = result.getInt("ID");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null){
				connection.close();
			}
		}
		  
		
		return ans;
	}
	/**
	 * this method returns number of events in database
	 * @return
	 * @throws SQLException
	 */
	public int getEventsNum() throws SQLException{
		Connection connection = null;
		int ans = 0;
		
		try{
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			String query = "select count(ID) from Event;";		 
			ResultSet result = stmt.executeQuery(query);
			
			 
			if(result.next()) ans = result.getInt("count(ID)");
			
		} catch(SQLException e){
			
		} finally{
			if(connection != null)
			connection.close();
		}
		 
		 
		return ans;
	}
	/*
	 * this method adds new event
	 * it constructs query string and passes to changeBase method
	 */
	public int addEvent(int userID,int placeID,String name,String time,String about,String price,String image){
		 
		String query = "insert into Event(UserID,placeID,Name,Time,About,Price,Image)"
				+ "values(" + userID + "," + placeID + ",'" + name + "','" + time + "','" + about +"','" + 
				price + "','" + image +"');";
		 
		return changeBase(query);		 
	}
	
	
	public boolean userAlreadyAttendsEvent(int userID,int eventID) throws SQLException{
		String query = "select * from User_Going_Event where UserID=" + userID + " and EventID=" + eventID + ";"; 		
		
		Connection connection = null;
		boolean rest = false;
		
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			if(result.next()){
				rest = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(connection != null) 
				connection.close();
		}
		 
		 
		return rest;
	}
	
	public int userAttendsEvent(int userID,int eventID) throws SQLException{		
		
		if(userAlreadyAttendsEvent(userID, eventID)){
			return BaseErrors.USER_ALREADY_ATTENDS_EVENT;
		}
		
		String query = "insert into User_Going_Event(UserID,EventID) values(" + userID + "," + eventID + ");";
		return changeBase(query);
	}
	
	/*
	 * this method adds new band to specified event
	 */
	public int addBand(int eventID,int bandID){
		String query = "insert into Band_On_Event(EventID,BandID) values(" + eventID + "," + bandID + ");";		 
		return changeBase(query);
	}
	
	/*
	 * this method updates info about specified event
	 * it constructs query string and passes it to changeBase method
	 */
	public int updateInfo(int eventID,String name,String time,String about,String price){
		String query = "update Event "
				+ "set  Name='" + name + "', Time='" + time + "', About='" + about + "', "
						+ "Price='" + price + "' where ID=" + eventID + ";";
		 
		return changeBase(query);
	}
	
	/*
	 * this method returns number of going users on 
	 * specified event
	 */
	public int getGoingUsersNum(int eventID) throws SQLException{
		String query = "select count(ID) from User_Going_Event where EventID='" + eventID + "';";
		Connection connection = null;
		
		int ans = 0;
		
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);		
			if(result.next()) ans = result.getInt("count(ID)");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(connection != null)
				connection.close();
		}
		
		return ans;
	}
	
	/*
	 * this method returns list of ID of users, who goes on this 
	 * event 
	 */
	public ArrayList<Integer> getGoingUsers(int eventID,int pageNum) throws SQLException{
		String query = "select UserID from User_Going_Event "
				+ "where EventID=" + eventID + " order by ID limit " + (pageNum - 1)*ConstantValues.NUM_GOING_USERS_PER_PAGE + 
				"," + ConstantValues.NUM_GOING_USERS_PER_PAGE + ";";
		
		return getList(query,"UserID");
	}
	
	/*
	 * this method returns list of bands
	 * which plays on this event
	 */
	public ArrayList<Integer> getBandsOnEvent(int eventID) throws SQLException{
		String query = "select BandID from Band_On_Event" +
					" where EventID=" + eventID + ";"; 
		
		return getList(query, "BandID");
	}
	
	/*
	 * this method checks whether this band is already
	 * added to this event
	 */
	
	public boolean bandIsAlreadyAdded(int bandID,int eventID) throws SQLException{
		String query = "select ID from Band_On_Event " +
				"where EventID=" + eventID + " and  BandID=" + bandID + ";";
		
		Connection connection = null;
		boolean is = false;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			  
			is = result.next();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(connection != null){
				connection.close();
			}
		} 
		 
		return is;
	}
	
	
	/*
	 * this method return boolean about 
	 * this userd has added this event or not
	 */
	public boolean hasAdded(int userID,int eventID) throws SQLException{
		String query = "select ID from Event " +
						"where UserID=" + userID + " and  ID=" + eventID + ";";
		
		Connection connection = null;
		boolean is = false;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);			 
			is = result.next();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null)
				connection.close();
		}
		 
		 
		return is;
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
					 
					return BaseErrors.UNABLE_EXECUTE;
				}		
				 
			} catch (Exception e) {
				 
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
		} catch (SQLException e){
			return BaseErrors.UNABLE_CONNECTION;
		}
		finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return BaseErrors.ALL_DONE;
	}
	
	/*
	 * this method returns latest num count events
	 * from newer to older
	 */
	public ArrayList<Integer> getLatestEvents(int num) throws SQLException{
		String query = "select ID from Event order by ID desc limit " + num + ";";
		
		return getList(query,"ID");
	}
	
	public ArrayList<Integer> getEventsForNthPage(int pageNum) throws SQLException{
		String query = "select ID from Event order by ID desc limit " + 
				(pageNum - 1)*ConstantValues.NUM_EVENT_ON_PER_PAGE + "," + ConstantValues.NUM_EVENT_ON_PER_PAGE + ";";
		 
		return getList(query,"ID");
	}
	
	public ArrayList<Integer> getList(String query,String column) throws SQLException{
		Connection connection = null;
		ArrayList<Integer> list = null;
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			list = new ArrayList<Integer>();
			
			while(result.next()){
				list.add(result.getInt(column));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(connection != null){
				connection.close();
			}
		}
		 
		 
		
		return list;
	}
	/**
	 * 
	 * @param UserID
	 * @param image
	 * @return eror index, which is in the eror package
	 */
	public int changeProfilePicture(int EventID,String image)
	{
		String query = "UPDATE Event SET Image='" + image
				+ "' where ID =" + EventID + ";";
		return changeBase(query);
	}
	
	
	
}
