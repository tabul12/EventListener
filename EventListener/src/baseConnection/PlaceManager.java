package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objects.Place;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


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
	
	public boolean addPlace(int userID,String name,String adress,String about) {
		Connection connection = null;
		Statement stmt = null;
		String query = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'" + name + "','" + adress + "','" + about +"');";
		
		
		try {
			connection = eventDataSource.getConnection();
		} catch (SQLException e) {
			return false;
		}
		
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			try {
				connection.close();
			} catch (SQLException e1) {
				return false;
			}
			return false;
		}

		try {
			stmt.execute(query);
		} catch (SQLException e) {
			try {
				connection.close();
			} catch (SQLException e1) {
				return false;
			}
			return false;
		}
		
		try {
			connection.close();
		} catch (SQLException e1) {
			return false;
		}

		
		 return true;		
	}

	
	
	public boolean addRate(int userID,int placeID,int eventID,int score){
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = eventDataSource.getConnection();
			stmt = connection.createStatement();
				
			//String query =			
			
		} catch (SQLException e) {
			 try {
				connection.close();
			} catch (SQLException e1) {
				return false;
			}
			 return false;
		}
		 
		return false;
		
	}
}
