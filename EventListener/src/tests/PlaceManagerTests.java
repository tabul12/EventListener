package tests;
 

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
 

import errors.BaseErrors;
import baseConnection.EventConnectionPool;
import baseConnection.EventDBInfo;
import baseConnection.PlaceManager;
import objects.Place;


public class PlaceManagerTests {

	private BasicDataSource dataSource;
	private PlaceManager manager;
	private Connection connection;
	private Statement stmt;
	private ResultSet result;
	
	@Before
	public void setUp() throws SQLException {
		 EventConnectionPool pool = new EventConnectionPool();
		 dataSource = pool.getEventDataSource();
		 manager = new PlaceManager(dataSource);
	}
	
	
	private void insertUser(String one,String two,String three) throws SQLException{
		String query = "insert into User(FirstName,LastName,UserName,Password,Mail,Image)"
				+ "	values('jondi','aqac','" + one + "','pass','mal','me.jpg');";
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		
		stmt.executeUpdate(query);
		
		query = "insert into User(FirstName,LastName,UserName,Password,Mail,Image)"
				+ "	values('jondi','aqac','" + two + "','pass','mal','me.jpg');";
		stmt.executeUpdate(query);
		query = "insert into User(FirstName,LastName,UserName,Password,Mail,Image)"
				+ "	values('jondi','aqac','" + three + "','pass','mal','me.jpg');";
		stmt.executeUpdate(query);
	}
	
	@Test 
	public void testAddPlace1() throws SQLException{
		
		insertUser("erti","meore","mesame");
		
		
		
		assertEquals(manager.addPlace(1, "samikitno", "saakadze", "xinkali"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(1, "samikitno", "saakadze", "xinkali"),BaseErrors.UNABLE_EXECUTE);
		assertEquals(manager.addPlace(2, "machaxela", "saakadze", "lobiani"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(3, "lemongra", "teqnikurtanaa", "kai shaurmaa"),BaseErrors.ALL_DONE);
		
		String query = "select * from Place where Name='samikitno';";
		
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		result = stmt.executeQuery(query);
		
		if(!result.next()) assertEquals(true, false); 
		
		assertEquals(result.getInt("UserID"),1);
		assertEquals(result.getString("Name"),"samikitno");
		assertEquals(result.getString("Adress"),"saakadze");
		assertEquals(result.getString("About"),"xinkali");
		 
		
		connection.close();
	}
	
	@Test 
	public void testAddPlace2() throws SQLException{
		insertUser("one","two","three");
		String query = "select * from Place where Name='machaxela';";
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		result = stmt.executeQuery(query);
		
		if(!result.next()) assertEquals(true, false);
		
		assertEquals(result.getInt("UserID"),2);
		assertEquals(result.getString("Name"),"machaxela");
		assertEquals(result.getString("Adress"),"saakadze");
		assertEquals(result.getString("About"),"lobiani");
		
		
		connection.close();
	}
	
	 
	@Test
	public void testAddImage() throws SQLException{
		insertUser("a","b","c");
		manager.addPlace(2, "evrika", "zastava", "xinkali");
		manager.addPlace(3, "sharaguli", "vake", "xinkali");
		
		assertEquals(manager.addImage(manager.getPlaceID("evrika"), "bichebi.jpg"),BaseErrors.ALL_DONE);
		assertEquals(manager.addImage(manager.getPlaceID("sharaguli"), "chveeeen.jpg"),BaseErrors.ALL_DONE);
		
	}
	 
	
	
	@Test
	public void testChangeProfileImage() throws SQLException{
		insertUser("x","y","z");
		
		assertEquals(manager.addPlace(2, "sss", "zastava", "xinkali"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(3, "jimi", "vake", "xinkali"),BaseErrors.ALL_DONE);
		
		assertEquals(manager.addImage(manager.getPlaceID("sss"), "magariaa.jpg"),BaseErrors.ALL_DONE);
		assertEquals(manager.addImage(manager.getPlaceID("jimi"), "chveen.jpg"),BaseErrors.ALL_DONE);
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		
		
		String query = "insert into Place_Profile_Image(Place_ImageID,PlaceID) " + 
							"values(1," + manager.getPlaceID("sss")+ ");";
		
		
		
		stmt.executeUpdate(query);		 
		
		query = "insert into Place_Profile_Image(Place_ImageID,PlaceID) " + 
				"values(2," + manager.getPlaceID("jimi")+ ");";
		
		stmt.executeUpdate(query);
		
		assertEquals(manager.changeProfileImage(manager.getPlaceID("sss"),2),BaseErrors.ALL_DONE);
		assertEquals(manager.changeProfileImage(manager.getPlaceID("jimi"),1),BaseErrors.ALL_DONE);
		
		connection.close();
	}			
	
	
	@Test 
	public void testUpdateInfo() throws SQLException{
		insertUser("1","2","3");
		assertEquals(manager.addPlace(1,"free", "nucubidze", "jigrebi swavloben"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(2, "moika", "terjola", "jigrebi abirjaveben"), BaseErrors.ALL_DONE);
		
		manager.updateInfo(manager.getPlaceID("free"), "free", "digomi", "sxvebic swavloben");
		manager.updateInfo(manager.getPlaceID("moika"), "moika", "terjola", "jigrebi isev abirjaveben");
		
		Place place1 = manager.getPlace(manager.getPlaceID("free"));
		Place place2 = manager.getPlace(manager.getPlaceID("moika"));
		
		assertEquals(place1.getName(), "free");
		assertEquals(place1.getAdress(),"digomi");
		assertEquals(place1.getAbout(), "sxvebic swavloben");
		assertEquals(place2.getName(), "moika");
		assertEquals(place2.getAdress(), "terjola");
		assertEquals(place2.getAbout(), "jigrebi isev abirjaveben");		
	}
	
	
	@Test
	public void testAddRating() throws SQLException{
		insertUser("da","ad","add");
		assertEquals(manager.addPlace(1,"wyaro", "nucubidze", "jigrebi swavloben"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(2, "garemo", "terjola", "jigrebi abirjaveben"), BaseErrors.ALL_DONE);
		
		String query = "insert into Event(UserID,PlaceID,Time,About,Price,Image)" + 
						"values(1," + manager.getPlaceID("wyaro") +
						",now(),'magari partiaa','5.00','cool.jpg');";	
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		
		stmt.executeUpdate(query);
		
		assertEquals(BaseErrors.ALL_DONE,manager.addRate(1, manager.getPlaceID("wyaro"), 1, 10));
		assertEquals(BaseErrors.ALL_DONE, manager.addRate(2, manager.getPlaceID("wyaro"), 1, 8));
		assertEquals(BaseErrors.USER_ALREADY_RATED_PLACE, manager.addRate(2, manager.getPlaceID("wyaro"), 1, 8));
		
		assertEquals(BaseErrors.ALL_DONE,manager.addRate(1, manager.getPlaceID("garemo"), 1, 9));
		assertEquals(BaseErrors.ALL_DONE, manager.addRate(2, manager.getPlaceID("garemo"), 1, 7));		
	
		connection.close();
	}
	
	@Test
	public void testGetRating() throws SQLException{
		insertUser("moi","modi","wamodi");
		assertEquals(manager.addPlace(1,"napiri", "nucubidze", "jigrebi swavloben"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(1,"gube", "nucubidze", "jigrebi swavloben"),BaseErrors.ALL_DONE);
		 
		
		String query = "insert into Event(UserID,PlaceID,Time,About,Price,Image)" + 
						"values(1," + manager.getPlaceID("napiri") +
						",now(),'magari partiaa dzaan','5.00','cool.jpg');";	
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		
		stmt.executeUpdate(query);
		
		assertEquals(BaseErrors.ALL_DONE,manager.addRate(1, manager.getPlaceID("napiri"), 1, 10));
		assertEquals(BaseErrors.ALL_DONE, manager.addRate(2, manager.getPlaceID("napiri"), 1, 8));
		
		assertEquals(BaseErrors.ALL_DONE,manager.addRate(1, manager.getPlaceID("gube"), 1, 9));
		assertEquals(BaseErrors.ALL_DONE, manager.addRate(2, manager.getPlaceID("gube"), 1, 8));	
		assertEquals(BaseErrors.USER_ALREADY_RATED_PLACE, manager.addRate(2, manager.getPlaceID("gube"), 1, 8));
		
		
		assertEquals(manager.getRating(manager.getPlaceID("napiri")), 9,0.01);
		assertEquals(manager.getRating(manager.getPlaceID("gube")), 8.5,0.01);	
		
		assertEquals(BaseErrors.USER_ALREADY_RATED_PLACE, manager.addRate(2, manager.getPlaceID("gube"), 1, 8));
		
		connection.close();
	}
	
	 
	@Test
	public void testGetAllImages() throws SQLException{
		insertUser("dd","ss","aa");
		
		manager.addPlace(2, "zastava", "zastava", "xinkali");
		manager.addPlace(3, "gora", "vake", "xinkali");
		
		assertEquals(manager.addImage(manager.getPlaceID("zastava"), "dao.jpg"),BaseErrors.ALL_DONE);
		assertEquals(manager.addImage(manager.getPlaceID("gora"), "gogoeboo!.jpg"),BaseErrors.ALL_DONE);
		assertEquals(manager.addImage(manager.getPlaceID("zastava"), "aaaw.jpg"),BaseErrors.ALL_DONE);
		assertEquals(manager.addImage(manager.getPlaceID("gora"), "hah.jpg"),BaseErrors.ALL_DONE);
		 
		ArrayList<String> list = manager.getAllImages(manager.getPlaceID("zastava"), 1);
		
		assertEquals(list.get(0),"aaaw.jpg");
		assertEquals(list.get(1),"dao.jpg");
		
		ArrayList<String> list2 = manager.getAllImages(manager.getPlaceID("gora"), 1);
		
		assertEquals(list2.get(0),"hah.jpg");
		assertEquals(list2.get(1),"gogoeboo!.jpg");
		
	}
	
	
	@Test
	public void testGetTopPlaces() throws SQLException{
		
		insertUser("wq","re","tr");
		
		assertEquals(manager.addPlace(1,"birja", "nucubidze", "jigrebi swavloben"),BaseErrors.ALL_DONE);
		assertEquals(manager.addPlace(1,"bostani", "nucubidze", "jigrebi swavloben"),BaseErrors.ALL_DONE);
		 
		
		String query = "insert into Event(UserID,PlaceID,Time,About,Price,Image)" + 
						"values(1," + manager.getPlaceID("birja") +
						",now(),'magari partiaa dzaan','5.00','cool.jpg');";	
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		
		stmt.executeUpdate(query);
		
		assertEquals(BaseErrors.ALL_DONE,manager.addRate(1, manager.getPlaceID("birja"), 1, 10));
		assertEquals(BaseErrors.ALL_DONE, manager.addRate(2, manager.getPlaceID("birja"), 1, 10));
		
		assertEquals(BaseErrors.ALL_DONE,manager.addRate(1, manager.getPlaceID("bostani"), 1, 10));
		assertEquals(BaseErrors.ALL_DONE, manager.addRate(2, manager.getPlaceID("bostani"), 1, 9));	
		 
		ArrayList<Integer> list = manager.getTopPlaces(2);
		
		Place place1 = manager.getPlace(list.get(0));
		Place place2 = manager.getPlace(list.get(1));
		 
		
		assertEquals(place1.getName(),"birja");
		assertEquals(place2.getName(), "bostani");	
		
		connection.close();
		
	}	
	
	@Test
	public void testGetPlaceImageID(){
		
	}
	
}
