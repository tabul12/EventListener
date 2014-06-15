package baseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.User;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import errors.BaseErrors;

public class UserManager {
	private BasicDataSource eventDataSource;
	private static final int numBandsPerPage = 10;
	private static final int numEventsPerPage = 10;
	private static final int numPlacesPerPage = 10;
	private static final int numBeenPlacesPerPage = 10;
	private static final int numWishlistBandsPerPage = 10;

	public UserManager(BasicDataSource eventDataSource) {
		this.eventDataSource = eventDataSource;
	}

	/***
	 * abrunebs user tipis obieqts gadacemul ID ze
	 * 
	 * @param ID
	 * @return
	 * @throws SQLException
	 */

	public User getUser(int ID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select * from User where ID = " + ID + ";";
		ResultSet res = stmt.executeQuery(query);
		User user = null;
		if (res.next()) {
			String name = res.getString("FirstName");
			String lastName = res.getString("LastName");
			String mail = res.getString("Mail");
			String image = res.getString("Image");
			String mobileNumber = res.getString("MobileNumber");
			String userName = res.getString("UserName");
			String password = res.getString("Password");
			user = new User(ID, name, lastName, userName, password, mail,
					image, mobileNumber);
		}
		con.close();
		return user;
	}

	/***
	 * qmnis axal users (amatebs cxrilshi) romlistvisac gadaecema shemdegi
	 * parametrebi romlebic avsebs cxrilis yvela svets
	 * 
	 * @param name
	 * @param lastName
	 * @param userName
	 * @param password
	 * @param mail
	 * @param image
	 * @param mobileNumber
	 * @throws SQLException
	 */

	public int addUser(String name, String lastName, String userName,
			String password, String mail, String image, String mobileNumber) {
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) "
				+ "Values('"
				+ name
				+ "','"
				+ lastName
				+ "','"
				+ userName
				+ "','"
				+ password
				+ "','"
				+ mobileNumber
				+ "','"
				+ image + "','" + mail + "');";
		return changeBase(query);
	}

	/***
	 * daloginebisas username is mixedvit amowmebs arsebobs tu ara ukve aseti
	 * useri
	 * 
	 * @param userName
	 * @return
	 */

	public boolean alreadyExists(String userName) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from User  where UserName = '" + userName
				+ "';";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * amowmebs dasjilia tu ara useri
	 * 
	 * @param userID
	 * @return
	 */

	public boolean isPunished(int userID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from  Punished  where UserID = " + userID
				+ ";";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * amowmebs aris tu ara useri admini
	 * 
	 * @param userID
	 * @return
	 */

	public boolean isAdmin(int userID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Admin  where UserID = " + userID + ";";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * abrunebs arraylists romelshic weria eventebis ID romlebzec aris namyofi
	 * 
	 * @param userID
	 * @return
	 */

	public ArrayList<Integer> beenList(int userID, int pageNum)
			throws SQLException {
		int num = (pageNum - 1) * numBeenPlacesPerPage;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select EventID from User_Going_Event where "
				+ "UserID  = " + userID + " LIMIT " + num + ","
				+ numBeenPlacesPerPage + ";";
		ResultSet res = stmt.executeQuery(query);
		while (res.next()) {
			int eventID = res.getInt("User_Going_EventEventID");
			list.add(eventID);
		}
		con.close();
		return list;
	}

	/***
	 * abrunebs useris ID is username is da passwordis mixedvit sachiroa
	 * daloginebisas
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */

	public int getUserID(String userName, String password) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from User where " + "Username  = '"
				+ userName + "' and Password= '" + password + "';";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			int ret = res.getInt("ID");
			con.close();
			return ret;
		} else {
			con.close();
			return -1;
		}
	}

	/***
	 * abrunebs bandebis Id ebis arraylists romelic am useris mier aris
	 * damatebuli
	 * 
	 * @param userID
	 * @return
	 */

	public ArrayList<Integer> addedBands(int userID, int pageNum)
			throws SQLException {
		int num = (pageNum - 1) * numBandsPerPage;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Band  where " + "UserID  = " + userID
				+ " LIMIT " + num + "," + numBandsPerPage + ";";
		ResultSet res = stmt.executeQuery(query);
		while(res.next()){
			int bandID = res.getInt("ID");
			list.add(bandID);
		 }
		con.close();
		return list;
	}

	/***
	 * abrunebs placebis ID ebis arraylists romelic am useris mier aris
	 * damatebuli
	 * 
	 * @param userID
	 * @return
	 */

	public ArrayList<Integer> addedPlaces(int userID, int pageNum)
			throws SQLException {
		int num = (pageNum - 1) * numPlacesPerPage;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Place where " + "UserID  = " + userID
				+ " LIMIT " + num + "," + numPlacesPerPage + ";";
		ResultSet res = stmt.executeQuery(query);
		while (res.next()) {
			int placeID = res.getInt("ID");
			list.add(placeID);
		}
		con.close();
		return list;
	}

	/***
	 * abrunebs eventebis ID ebis arraylists romelic am useris mier aris
	 * damatebuli
	 * 
	 * @param userID
	 * @return
	 */

	public ArrayList<Integer> addedEvents(int userID, int pageNum)
			throws SQLException {
		int num = (pageNum - 1) * numEventsPerPage;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Event where " + "UserID  = " + userID
				+ " LIMIT " + num + "," + numEventsPerPage + ";";
		ResultSet res = stmt.executeQuery(query);
		while (res.next()) {
			int eventID = res.getInt("ID");
			list.add(eventID);
		}
		con.close();
		return list;
	}

	/***
	 * amatebs bands useris wishlistshi
	 * 
	 * @param userID
	 * @param bandID
	 */

	public int addInWishList(int userID, int bandID) {
		String query = "insert into User_Band_Wishlist (UserID, BandID)"
				+ "values (" + userID + "," + bandID + ");";
		return changeBase(query);
	}

	/***
	 * amowmebs aris tu ara es band gadacemuli useris mier damatebuli
	 * 
	 * @param userID
	 * @param bandID
	 * @return
	 */

	public boolean hasAddedBand(int userID, int bandID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Band  " + " where ID  = " + bandID
				+ " and UserID = " + userID + ";";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * amowmebs aris tu ara es place gadacemuli useris mier damatebuli
	 * 
	 * @param userID
	 * @param placeID
	 * @return
	 */

	public boolean hasAddedPlace(int userID, int placeID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Place  " + " where ID  = " + placeID
				+ " and UserID = " + userID + ";";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * amowmebs aris tu ara es event gadacemuli useris mier damatebuli
	 * 
	 * @param userID
	 * @param eventID
	 * @return
	 */

	public boolean hasAddedEvent(int userID, int eventID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from Event  " + " where ID  = " + eventID
				+ " and UserID = " + userID + ";";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * abrunebs bandebis ID ebis arraylists romelic yavs wishlistshi
	 * 
	 * @param userID
	 * @param pageNum
	 * @return
	 * @throws SQLException
	 */

	public ArrayList<Integer> getWishlist(int userID, int pageNum)
			throws SQLException {
		int num = (pageNum - 1) * numWishlistBandsPerPage;
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select BandID from User_Band_Wishlist where "
				+ "UserID = " + userID + " LIMIT " + num + ","
				+ numWishlistBandsPerPage + ";";
		ResultSet bandIDsSet = stmt.executeQuery(query);
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (bandIDsSet.next()) {
			int bandID = bandIDsSet.getInt("BandID");
			list.add(bandID);
		}
		con.close();
		return list;
	}

	/***
	 * vanaxlebt useris shesaxeb informacias
	 * 
	 * @param ID
	 * @param firstName
	 * @param lastName
	 * @param mail
	 * @param mobileNumber
	 * @param image
	 * @param password
	 * @return
	 */

	public int updateInfo(int ID, String firstName, String lastName,
			String mail, String mobileNumber, String image, String password) {
		String query = "UPDATE User SET FirstName='" + firstName
				+ "',LastName ='" + lastName + "', Mail ='" + mail
				+ "', MobileNumber ='" + mobileNumber + "', Image ='" + image
				+ "', Password  ='" + password + "' where ID =" + ID + ";";
		return changeBase(query);
	}

	/***
	 * administratori sjis users
	 * 
	 * @param userID
	 * @return
	 */

	public int punishUser(int userID) {
		String query = "insert into Punished (UserID)" + "values (" + userID
				+ ");";
		return changeBase(query);
	}

	/***
	 * amatebs userze going mititebul iventze roca user adzlevs goings
	 * 
	 * @param userID
	 * @param eventID
	 * @return
	 */

	public int addGoing(int userID, int eventID) {
		String query = "insert into User_Going_Event (UserID, EventID)"
				+ "values (" + userID + ", " + eventID + ");";
		return changeBase(query);
	}
	
	/***
	 * amowmebs mocemul users mocemul eventze aqvs tu ara going micemuli
	 * @param userID
	 * @param eventID
	 * @return
	 * @throws SQLException
	 */

	public boolean isGoing(int userID, int eventID) throws SQLException {
		Connection con = eventDataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "select ID from  User_Going_Event where UserID = "
				+ userID + " and EventID = " + eventID + ";";
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

	/***
	 * axorcielebs bazashi cvlilebebs gadacemuli querys mixedvit
	 * 
	 * @param quer
	 * @return
	 */

	private int changeBase(String quer) {
		Connection con;
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					stm.executeUpdate(quer);
				} catch (Exception e) {
					con.close();
					return BaseErrors.UNABLE_EXECUTE;
				}
				con.close();
			} catch (Exception e) {
				con.close();
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
		} catch (SQLException e) {
			return BaseErrors.UNABLE_CONNECTION;
		}
		return BaseErrors.ALL_DONE;
	}

}
