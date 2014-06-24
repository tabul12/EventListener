package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Place;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;

import errors.BaseErrors;
import errors.ConstantValues;



public class PlaceManager {
	
	private BasicDataSource eventDataSource;
	private static final int placeImagesPerPage = ConstantValues.PLACE_IMAGES_ON_PER_PAGE;
	
	public PlaceManager(BasicDataSource connectionPool){
		this.eventDataSource = connectionPool;
	}
	
	
	/**
	 * This method constructs and returns Place object on the placeID ID
	 * @param placeName
	 * @return ID
	 * @throws SQLException
	 */
	public int getPlaceID(String placeName) throws SQLException{
		String query = "select ID from Place where Name='" + placeName + "';";
		Connection connection = null;
		int ID = 0;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);	
			 
			if(result.next()) ID = result.getInt("ID");
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(connection != null)
				connection.close();
		}
		 
		return ID;
		
	}
	/**
	 * 
	 * @param placeID
	 * @return place object 
	 * @throws SQLException
	 */
	public Place getPlace(int placeID) throws SQLException{
		
		Connection connection = null;
		Place place = null;
		
		try {
			connection = eventDataSource.getConnection();
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
			
			query = "select Name from Place_Profile_Image " 
					+ "where PlaceID=" + placeID + ";";
			result = stmt.executeQuery(query);
			if(result.next()){
				image = result.getString("Name");
			}
			
			place = new Place(placeID,userID, name, adress, about, image);
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if( connection != null)
				connection.close();
		}
		 
		
		 
		return place;
	}
	
	/**
	 * this method adds new place in Place table field
	 * it constructs query String and passes it to changeBase method
	 * @param userID
	 * @param name
	 * @param adress
	 * @param about
	 * @return constan from constantValues
	 */
	public int addPlace(int userID,String name,String adress,String about) {
		String query = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'" + name + "','" + adress + "','" + about +"');";
		
		return changeBase(query);
	}
	/**
	 * adds profile image
	 * @param placeID
	 * @param image
	 * @return constant from constantvalues
	 */
	public int addProfileImage(int placeID, String image){
		String query = "insert into Place_Profile_Image(PlaceID,Name)"
				+ "values(" + placeID + ",'" + image + "');";
		return changeBase(query);
	}
	

	/**
	 * 
	 * @param placeID
	 * @return profile image name
	 * @throws SQLException
	 */
	public String getProfileImage(int placeID) throws SQLException{
		
		Connection connection = null;
		String image = ""; 
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();			
			String query = "select Name from Place_Profile_Image " 
					+ "where PlaceID=" + placeID + ";";
			ResultSet result = stmt.executeQuery(query);
			 
			if(result.next()){
				image = result.getString("Name");
			} 
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(connection != null)
				connection.close();
		} 
		
		 
		return image;
	}
	
	/**
	 * this method takes the name of place image and returns its ID
	 * @param imageName
	 * @return
	 * @throws SQLException
	 */
	public int getPlaceImageID(String imageName) throws SQLException{
		String query = "select ID from Place_Image where Name='" + imageName + "';";
		
		Connection connection = null;
		int ans = 0;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();	 
			ResultSet result = stmt.executeQuery(query);
			if(result.next())
				 ans = result.getInt("ID");
			
		} catch (Exception e) {
			// TODO: handle exception
		}	finally{
			if(connection != null)
				connection.close();
		}
		
		return ans;
		  
	}
	
	/**
	 * this method adds new rate for place by specified user, on some event
	 * it constructs query string and passes it to changeBase
	 * @param userID
	 * @param placeID
	 * @param eventID
	 * @param score
	 * @return constant from ConstantValues
	 * @throws SQLException
	 */
	public int addRate(int userID,int placeID,int eventID,int score) throws SQLException{
		String query = "insert into Place_Rating(UserID,PlaceID,EventID,Rating)" +""
				+ "values(" + userID + "," + placeID + "," + eventID + "," + score +");";		
		
		if(hasAlreadyRated(userID, placeID, eventID, score)) 
			return BaseErrors.USER_ALREADY_RATED_PLACE;
		return changeBase(query);		
	}
	
	/**
	 * this method checks whether the user has already rated
	 * this place
	 * @param userID
	 * @param placeID
	 * @param eventID
	 * @param score
	 * @return true or false if already rated
	 * @throws SQLException
	 */
	private boolean hasAlreadyRated(int userID,int placeID,int eventID,int score) throws SQLException{
		String query = "select * from Place_Rating " + 
				 " where UserID=" + userID + " and PlaceID=" + placeID + " and EventID =" + eventID + ";";
	
		Connection connection = null;
		boolean is = false;
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			 
			ResultSet result = stmt.executeQuery(query);
			
			
			if(result.next())
				is = true;
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null){
				connection.close();
			}
		}
		 
		return is;
		
	}
	
	/**
	 * this method returns the id of the image
	 * whichs Name is name
	 * @param imageName
	 * @return imageID
	 * @throws SQLException
	 */
	public int getImageID(String imageName) throws SQLException{
		
		Connection connection = null;
		int  id = 0;
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			String query = "select ID from Place_Image " + 
						 " where Name='" + imageName + "';";
			
			ResultSet result = stmt.executeQuery(query);
			
			 
			if(result.next())
				id = result.getInt("ID");
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(connection != null)
				connection.close();
		}
		 
		return id;
	}
	

	/**
	 * this method adds new image for specified place
	 * it constructs query string and passes to changeBase 
	 * @param placeID
	 * @param imageName
	 * @return constant from Constantvalues
	 */
	public int addImage(int placeID,String imageName){
		String query = "insert into Place_Image(Name,PlaceID)"
				+ "values('" + imageName + "'," + placeID + ");";
	 
		return changeBase(query);
	}
	
	/**
	 * this method updates info about specified place
	 *  it constructs query string and passes it to changeBase method
	 * @param placeID
	 * @param name
	 * @param adress
	 * @param about
	 * @return constant from Constantvalues
	 */
	public int updateInfo(int placeID,String name,String adress,String about){
		String query = "update Place "
				+ "set Name='" + name + "', Adress='" + adress + "', About='" + about
						+ "' where ID=" + placeID + ";";
		 
		return changeBase(query);
	}
	
	 
	
	/**
	 * this method changes profile image of specified place
	 * it constructs query string and passes to changeBase method
	 * @param placeID
	 * @param image
	 * @return constant from Constantvalues
	 */
	public int changeProfileImage(int placeID,String image)
	{	
		
		String query = "update Place_Profile_Image " + 
						"set Name='" + image + "' where PlaceID=" + placeID + ";";		
		 return changeBase(query);
		
	}
	
	
	/**
	 * this method gives us list of all images to draw on the
	 * pageNum numbered page
	 * @param placeID
	 * @param pageNum
	 * @return list of images
	 * @throws SQLException
	 */
	public ArrayList<String> getAllImages(int placeID,int pageNum) throws SQLException{
		
		Connection connection = null;
		ArrayList<String> list = null;
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement(); 

			String query = "select Name from Place_Image" +
							" where PlaceID=" + placeID + " order by ID desc limit " + 
									(pageNum - 1) * placeImagesPerPage + "," + placeImagesPerPage + ";";
			
			 list = new ArrayList<String>();
			 
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				list.add(result.getString("Name"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null)
				connection.close();
		}
		
		
		return list;
	}

	/**
	 * this method rwturns rating
	 * @param placeID
	 * @return
	 * @throws SQLException
	 */
	public double getRating(int placeID) throws SQLException{
		Connection connection = null;
		double ans = 0.0;
		
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			String query = "select avg(Rating)" + 
						      "from Place_Rating where PlaceID=" + placeID + ";";
			
			ResultSet result = stmt.executeQuery(query);
			 
			if(result.next())
				ans = result.getDouble("avg(Rating)");
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null)
				connection.close();
		}
		 	 
		return ans;
	}
	
	/**
	 *  this method returns number of images of specified place
	 * @param placeID
	 * @return
	 * @throws SQLException
	 */
	public int getPlaceImagesNum(int placeID) throws SQLException{
		Connection connection = null;
		int ans = 0;
		
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			String query = "select count(ID) " + 
						      "from Place_Image where PlaceID=" + placeID + ";";
			
			 
			ResultSet result = stmt.executeQuery(query);
			if(result.next()) ans = result.getInt("count(ID)");
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null)
				connection.close();
		}
		 
		 
		return ans;
	}
	
	/**
	 * this method returns top places count of which is num
	 * @param num
	 * @return top places count of which is num
	 * @throws SQLException
	 */
	public ArrayList<Integer> getTopPlaces(int num) throws SQLException{
		String query = "select ID from Place"
				+	" order by placeAverageRating(ID) desc limit " + num + ";";
		
		
		Connection connection = null;
		ArrayList<Integer> list = null;
		try {
			
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			 
			ResultSet result = stmt.executeQuery(query);
			
			
			 list  = new ArrayList<Integer>();
			
			
			while(result.next()){
				list.add(result.getInt("ID"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null)
				connection.close();
		}
		 
		return list;		
	}
	
	/**
	 * this is method which gets query string and executes it
	 * it considers many edge cases for exceptions
	 * @param query
	 * @return constant from cosntantvalues
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
					 
					return BaseErrors.UNABLE_EXECUTE;
				}		
				connection.close();
			} catch (Exception e) {
				 
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
		} catch (SQLException e){
			return BaseErrors.UNABLE_CONNECTION;
		}
		finally{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					 
				}
		}
		return BaseErrors.ALL_DONE;
	} 
	
	
}
