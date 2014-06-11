package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Place;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import errors.BaseErrors;


public class PlaceManager {
	
	private BasicDataSource eventDataSource;
	
	public PlaceManager(BasicDataSource connectionPool){
		this.eventDataSource = connectionPool;
	}
	
	public Place getPlace(int placeID) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		
		
		String query = "Select * from Place where ID=" + placeID + ";";
		
		ResultSet result = stmt.executeQuery(query);
		
		int userID = 0;
		String name = "";
		String adress = "";
		String about = "";
		String image = "";
		
		if(result.next()){
			userID = result.getInt("UserID");
			name = result.getString("Name");
			adress = result.getString("Adress");
			about = result.getString("About");			
		}
		
		query = "select Place_Image.Name from Place_Image, Place_Profile_Image ,Place " +""
				+ "where Place_Profile_Image.Place_ImageID=Place_Image.ID and "
				+ "Place_Profile_Image.PlaceID=" + placeID + ";";
		result = stmt.executeQuery(query);
		if(result.next()){
			image = result.getString("Name");
		}
		
		Place place = new Place(placeID,userID, name, adress, about, image);
		connection.close();
		System.out.println("yumyy");
		return place;
	}
	
	public int addPlace(int userID,String name,String adress,String about) {
		Connection connection = null;
		Statement stmt = null;
		String query = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'" + name + "','" + adress + "','" + about +"');";
		
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

	
	
	public int addRate(int userID,int placeID,int eventID,int score) throws SQLException{
		Connection connection = null;
		Statement stmt = null;
		String query = "insert into Place_Rating(UserID,PlaceID,EventID,Rating)" +""
				+ "values(" + userID + "," + placeID + "," + eventID + "," + score +");";
		
		
		if(hasAlreadyRated(userID, placeID, eventID, score)) 
			return BaseErrors.USER_ALREADY_RATED_PLACE;
		
		try {
			connection = eventDataSource.getConnection();	
			
			try{
				stmt = connection.createStatement();
				try{
					stmt.executeUpdate(query);				
				} catch(SQLException e){
					connection.close();
					return BaseErrors.UNABLE_EXECUTE;
				}
			} catch(SQLException e){
				connection.close();
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
			 
		} catch (SQLException e) {
			return BaseErrors.UNABLE_CONNECTION;
		}
		 
		return BaseErrors.ALL_DONE;
		
	}
	
	public boolean hasAlreadyRated(int userID,int placeID,int eventID,int score) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		
		String query = "select * from Place_Rating" +
					"where userID=" + userID + " and placeID=" + placeID
					+ " and eventID=" + eventID + ";";
		
		ResultSet result = stmt.executeQuery(query);
		
		return result.next();
		
	}
	
	public int changeProfileImage(int placeID,int imageID)
	{
		Connection connection = null;
		Statement stmt = null;
		
		String query = "update Place_Profile_Image "
				+ "set Place_ImageID=" + imageID + ""
						+ "where PlaceID=" + placeID + ";";		
		try {
			connection = eventDataSource.getConnection();
			
			try{
				stmt = connection.createStatement();
				try{
					stmt.executeUpdate(query);
				} catch(SQLException e){
					connection.close();
					return BaseErrors.UNABLE_EXECUTE;
				}
			} catch(SQLException e){
				connection.close();
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
			
		} catch (SQLException e) {
			return BaseErrors.UNABLE_CONNECTION;
		}
		
		return BaseErrors.ALL_DONE;
		
	}
	/*
	public ArrayList<Integer> getPictures(int placeID) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		
		String query = "select Place_Image.ID from Place_Image"
				+ "";
		
	}*/
}
