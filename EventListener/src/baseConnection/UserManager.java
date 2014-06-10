package baseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import objects.User;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.mysql.jdbc.Connection;

public class UserManager {
	private  BasicDataSource connectionPool;
	
	
	public UserManager(BasicDataSource connectionPool){
		this.connectionPool = connectionPool;
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
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
			user = new User(ID, name, lastName,userName,password, mail, image, mobileNumber);
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
			String image, String mobileNumber)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	public boolean alreadyExists(String userName)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public boolean isGoing(int userID,int eventID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public boolean isPunished(int userID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public boolean isAdmin(int userID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public ArrayList<Integer> beenList(int userID)throws SQLException{
		ArrayList <Integer> list = new ArrayList<Integer>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
		String query = "select EventID from User_Going_Event where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int eventID = res.getInt("User_Going_EventEventID");
			list.add(eventID);
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
	
	public int getUserID(String userName, String password)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public ArrayList<Integer> addedBands(int userID)throws SQLException{
		ArrayList <Integer> list = new ArrayList<Integer>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
		String query = "select ID from Band where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int bandID = res.getInt("BandID");
			list.add(bandID);
		}
		con.close();
		return list;
	}
	
	/***
	 * abrunebs placebis arraylists romelic am useris mier aris damatebuli
	 * @param userID
	 * @return
	 */
	
	public ArrayList<Integer> addedPlaces(int userID)throws SQLException{
		ArrayList <Integer> list = new ArrayList<Integer>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
		String query = "select ID from Place where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int placeID = res.getInt("PlaceID");
			list.add(placeID);
		}
		con.close();
		return list;
	}
	
	/***
	 * abrunebs eventebis arraylists romelic am useris mier aris damatebuli
	 * @param userID
	 * @return
	 */
	
	public ArrayList<Integer> addedEvents(int userID)throws SQLException{
		ArrayList <Integer> list = new ArrayList<Integer>();
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
		String query = "select ID from Event where "
				+ "UserID  = " + userID +";"; 
		ResultSet res  = stmt.executeQuery(query);
		while(res.next()){
			int eventID = res.getInt("EventID");
			list.add(eventID);
		}
		con.close();
		return list;
	}
	
	
	/***
	 * amatebs bands useris wishlistshi
	 * @param userID
	 * @param bandID
	 */
	
	public void addInWishList(int userID, int bandID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public boolean hasAddedBand(int userID, int bandID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public boolean hasAddedPlace(int userID, int placeID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
	
	public boolean hasAddedEvent(int userID, int eventID)throws SQLException{
		Connection con = (Connection) connectionPool.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
