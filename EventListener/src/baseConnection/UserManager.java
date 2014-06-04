package baseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Band;
import objects.Event;
import objects.User;
import objects.Place;

import baseConnection.BandManager;
import baseConnection.EventManager;
import baseConnection.PlaceManager;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.mysql.jdbc.Connection;

public class UserManager {
	private static BasicDataSource connectionPool;
	
	
	public UserManager(BasicDataSource connectionPool){
		connectionPool = connectionPool;
	}
	
	/***
	 * abrunebs user tipis obieqts gadacemul ID  ze
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	
	public User getUser(int ID) throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String order = "select * from User where ID = " + ID + ";";
		ResultSet res = stmt.executeQuery(order);
		User user = null;
		if(res.next()){
			String name  = res.getString("FirstName");
			String lastName = res.getString("LastName");
			String mail = res.getString("Mail");
			String image = res.getString("Image");
			String mobileNumber = res.getString("MobileNumber");
			String userName = res.getString("UserName");
			String password = res.getString("Password");
			Statement wishStmt = con.createStatement();
			String bandIDs = "select BandID from User_Band_Wishlist where "
					+ "UserID = " + ID + ";";  
			ResultSet bandIDsSet = wishStmt.executeQuery(bandIDs);
			ArrayList<Band> list = new ArrayList<Band>();
			
			while(bandIDsSet.next()){
				int bandID = bandIDsSet.getInt("BandID");
				Band band = BandManager.getBand(bandID);
				list.add(band);
			}
			user = new User(ID, name, lastName,userName,password, mail, image, list, mobileNumber);
		}
		con.close();
		return user;
	}
	
	/***
	 * qmnis axal users (amatebs cxrilshi) romlistvisac gadaecema shemdegi parametrebi
	 * romlebic avsebs cxrilis yvela svets
	 * @param name
	 * @param lastName
	 * @param userName
	 * @param password
	 * @param mail
	 * @param image
	 * @param mobileNumber
	 */
	
	public void createUser(String name, String lastName, String userName, String password, String mail,
			String image, String mobileNumber){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FisrtName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail)" 
				+ "Values('"+ name +"','" + lastName +"','" + userName +"','"
				+ password + "','"+ mobileNumber + "','"+image+"','"+ mail+"');";
		stmt.executeUpdate(query);
		con.close();
	}
	
	
	/***
	 * daloginebisas username is mixedvit amowmebs arsebobs tu ara ukve aseti useri
	 * @param userName
	 * @return
	 */
	public boolean alreadyExists(String userName){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from User  where UserName = '" +userName +"';"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){ 
			return true;
		}else{
			return false;
		}
	}
	
	/***
	 * amowmebs am eventze aqvs tu ara going micemul users
	 * @param userID
	 * @param eventID
	 * @return
	 */
	
	public boolean isGoing(int userID,int eventID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select UserID from User_Going_Event  "
				+ " where UserID  = " + userID +" and EventID = "+ 
				eventID + ";"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return true;
		}else{
			return false;
		}
	}
	
	
	/***
	 * amowmebs dasjilia tu ara useri 
	 * @param userID
	 * @return
	 */
	
	public boolean isPunished(int userID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from  Punished  where UserID = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/***
	 * amowmebs aris tu ara useri admini
	 * @param userID
	 * @return
	 */
	
	public boolean isAdmin(int userID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Admin  where UserID = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/***
	 * abrunebs arraylists romelshic weria eventebi romlebzec aris namyofi 
	 * @param userID
	 * @return
	 */
	
	public ArrayList<Event> beenList(int userID){
		ArrayList <Event> list = new ArrayList<Event>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select EventID from User_Going_Event where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int eventID = res.getInt("User_Going_EventEventID");
			Event event = EventManager.getEvent(eventID);
			list.add(event);
		}
		con.close();
		return list;
	}
	
	/***
	 * abrunebs useris ID is username is da passwordis mixedvit sachiroa daloginebisas
	 * @param userName
	 * @param password
	 * @return
	 */
	
	public int getUserID(String userName, String password){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from User where "
				+ "Username  = '" + userName +"' and Password= '"+ password +"';"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return res.getInt("UserID");
		}else{
			return -1;
		}
	}
	
	/***
	 * abrunebs bandebis arraylists romelic am useris mier aris damatebuli
	 * @param userID
	 * @return
	 */
	
	public ArrayList<Band> addedBands(int userID){
		ArrayList <Band> list = new ArrayList<Band>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Band where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int bandID = res.getInt("BandID");
			Band band = BandManager.getBand(bandID);
			list.add(band);
		}
		con.close();
		return list;
	}
	
	/***
	 * abrunebs placebis arraylists romelic am useris mier aris damatebuli
	 * @param userID
	 * @return
	 */
	
	public ArrayList<Place> addedPlaces(int userID){
		ArrayList <Place> list = new ArrayList<Place>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Place where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int placeID = res.getInt("PlaceID");
			Place place = PlaceManager.getPlace(placeID);
			list.add(place);
		}
		con.close();
		return list;
	}
	
	/***
	 * abrunebs eventebis arraylists romelic am useris mier aris damatebuli
	 * @param userID
	 * @return
	 */
	
	public ArrayList<Event> addedEvents(int userID){
		ArrayList <Event> list = new ArrayList<Event>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Event where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int eventID = res.getInt("EventID");
			Event event = EventManager.getEvent(eventID);
			list.add(event);
		}
		con.close();
		return list;
	}
	
	
	/***
	 * amatebs bands useris wishlistshi
	 * @param userID
	 * @param bandID
	 */
	
	public void addInWishList(int userID, int bandID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "insert into User_Band_Wishlist (UserID, BandID)" 
				+ "values ("+ userID +"," + bandID + ");";
		stmt.executeUpdate(query);
		con.close();
	}
	
	/***
	 * amowmebs aris tu ara es band gadacemuli useris mier damatebuli
	 * @param userID
	 * @param bandID
	 * @return
	 */
	
	public boolean hasAddedBand(int userID, int bandID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Band  "
				+ " where ID  = " + bandID +" and UserID = " +userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/***
	 * amowmebs aris tu ara es place gadacemuli useris mier damatebuli
	 * @param userID
	 * @param placeID
	 * @return
	 */
	
	public boolean hasAddedPlace(int userID, int placeID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Place  "
				+ " where ID  = " + placeID +" and UserID = "+ userID + ";"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/***
	 * amowmebs aris tu ara es event gadacemuli useris mier damatebuli
	 * @param userID
	 * @param eventID
	 * @return
	 */
	
	public boolean hasAddedEvent(int userID, int eventID){
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Event  "
				+ " where ID  = " + eventID +" and UserID = "+ userID + ";"; 
		ResultSet res  = stmt.executeQuery(query);
		con.close();
		if(res.next()){
			return true;
		}else{
			return false;
		}
	}
	
}
