package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objects.Place;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class PlaceManager {
	
	private BasicDataSource connectionPool;
	
	public PlaceManager(BasicDataSource connectionPool){
		this.connectionPool = connectionPool;
	}
	
	public Place getPlace(int placeID) throws SQLException{
		Connection connection = connectionPool.getConnection();
		Statement stmt = connection.createStatement();
		stmt.execute("Use database ev");
		
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
		
		query = "select Place_Image.Name from Place_Image,Place_Profile_Image,Place" +""
				+ "where Place_Profile_Image.Place_ImageID=Place_Image.ID and "
				+ "Place_Profile_Image.PlaceID=" + placeID + ";";
		result = stmt.executeQuery(query);
		if(result.next()){
			image = result.getString("Name");
		}
		
		Place place = new Place(placeID,userID, name, adress, about, image);
		connection.close();
		return place;
	}
	
}
