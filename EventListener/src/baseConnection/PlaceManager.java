package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Place;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import errors.BaseErrors;
import errors.ConstantValues;



public class PlaceManager {
	
	private BasicDataSource eventDataSource;
	private static final int placeImagesPerPage = ConstantValues.PLACE_IMAGES_ON_PER_PAGE;
	
	public PlaceManager(BasicDataSource connectionPool){
		this.eventDataSource = connectionPool;
	}
	
	/*
	 *  This method constructs and returns Place object on the placeID ID
	 */
	
	
	public int getPlaceID(String placeName) throws SQLException{
		String query = "select ID from Place where Name='" + placeName + "';";
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);
		
		int ID = 0;
		if(result.next()) ID = result.getInt("ID");
		connection.close();
		return ID;
		
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
		 
		return place;
	}
	
	/*
	 * this method adds new place in Place table field
	 * it constructs query String and passes it to changeBase method
	 */
	public int addPlace(int userID,String name,String adress,String about) {
		String query = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'" + name + "','" + adress + "','" + about +"');";
		
		return changeBase(query);
	}
	
	/*
	 * this method adds new rate for place by specified user, on some event
	 * it constructs query string and passes it to changeBase
	 */
	public int addRate(int userID,int placeID,int eventID,int score) throws SQLException{
		String query = "insert into Place_Rating(UserID,PlaceID,EventID,Rating)" +""
				+ "values(" + userID + "," + placeID + "," + eventID + "," + score +");";		
		
		if(hasAlreadyRated(userID, placeID, eventID, score)) 
			return BaseErrors.USER_ALREADY_RATED_PLACE;
		return changeBase(query);		
	}
	
	/*
	 * this method checks whether the user has already rated
	 * this place
	 */
	private boolean hasAlreadyRated(int userID,int placeID,int eventID,int score) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		
		String query = "select * from Place_Rating " + 
					 " where UserID=" + userID + " and PlaceID=" + placeID + " and EventID =" + eventID + ";";
		
		ResultSet result = stmt.executeQuery(query);
		
		boolean is = false;
		if(result.next())
			is = true;
		
		 
		connection.close();
		return is;
		
	}
	
	/*
	 * this method adds new image for specified place
	 * it constructs query string and passes to changeBase
	 */
	
	public int addImage(int placeID,String imageName){
		String query = "insert into Place_Image(Name,PlaceID)"
				+ "values('" + imageName + "'," + placeID + ");";
	 
		return changeBase(query);
	}
	
	/*
	 *  this method updates info about specified place
	 *  it constructs query string and passes it to changeBase method
	 */
	public int updateInfo(int placeID,String name,String adress,String about){
		String query = "update Place "
				+ "set Name='" + name + "', Adress='" + adress + "', About='" + about
						+ "' where ID=" + placeID + ";";
		 
		return changeBase(query);
	}
	
	 
	
	/*
	 * this method changes profile image of specified place
	 * it constructs query string and passes to changeBase method
	 */
	public int changeProfileImage(int placeID,int imageID)
	{	
		
		String query = "update Place_Profile_Image " + 
						"set Place_ImageID=" + imageID + " where PlaceID=" + placeID + ";";		
		 return changeBase(query);
		
	}
	
	/*
	 * this method gives us list of all images to draw on the
	 * pageNum numbered page
	 */
	
	public ArrayList<String> getAllImages(int placeID,int pageNum) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement(); 

		String query = "select Name from Place_Image" +
						" where PlaceID=" + placeID + " order by ID desc limit " + 
								(pageNum - 1) * placeImagesPerPage + "," + placeImagesPerPage + ";";
		
		ArrayList<String> list = new ArrayList<String>();
		 
		ResultSet result = stmt.executeQuery(query);
		
		while(result.next()){
			list.add(result.getString("Name"));
		}
		connection.close();
		
		return list;
	}

	/*
	 * this method gives us rating for specified place
	 */
	public double getRating(int placeID) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		String query = "select avg(Rating)" + 
					      "from Place_Rating where PlaceID=" + placeID + ";";
		
		ResultSet result = stmt.executeQuery(query);
		double ans = 0.0;
		if(result.next())
			ans = result.getDouble("avg(Rating)");	
		connection.close();
		return ans;
	}
	
	/*
	 * this method returns number of images of specified place
	 */
	public int getPlaceImagesNum(int placeID) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		String query = "select count(ID) " + 
					      "from Place_Image where PlaceID=" + placeID + ";";
		
		int ans = 0;
		ResultSet result = stmt.executeQuery(query);
		if(result.next()) ans = result.getInt("count(ID)");
		connection.close();		
		return ans;
	}
	
	/*
	 * this method returns top places count of which is num
	 */
	public ArrayList<Integer> getTopPlaces(int num) throws SQLException{
		String query = "select ID from Place"
				+	" order by placeAverageRating(ID) desc limit " + num + ";";
		
		
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		
		 
		ResultSet result = stmt.executeQuery(query);
		
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		
		while(result.next()){
			list.add(result.getInt("ID"));
		}
		
		connection.close();
		return list;		
	}
	
	/*
	 * this is method which gets query string and executes it
	 * it considers many edge cases for exceptions
	 */
	private int changeBase(String query){
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
	
	
}
