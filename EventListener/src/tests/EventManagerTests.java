package tests;
 
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Event;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import errors.BaseErrors;
import errors.ConstantValues;
import baseConnection.EventConnectionPool;
import baseConnection.EventManager;
import baseConnection.PlaceManager;

public class EventManagerTests {
	private BasicDataSource dataSource;
	private EventManager manager;
	private Connection connection;
	private Statement stmt;
	private ResultSet result;
	
	
	@Before
	public void setUp() throws SQLException {
		 EventConnectionPool pool = new EventConnectionPool();
		 dataSource = pool.getEventDataSource();
		 manager = new EventManager(dataSource);
	}
	
	
	@Test 
	public void testGetEvent() throws SQLException{
		
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('amiko','sakhelashvili','dadu','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','mdinareze.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'dzudzgi','sofeli','beko')";
		stmt.executeUpdate(addPlace);
		
		String addEvent = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
							"values(1,1,'discoo','2/15/2014','wamodit','5.00','wina.jpg')";
		stmt.executeUpdate(addEvent);
		
		Event event = manager.getEvent(manager.getEventID("discoo"));
		
		assertEquals(1, event.getUserID());
		assertEquals(1, event.getPlaceID());
		assertEquals("2/15/2014", event.getTime()); 
		assertEquals("wamodit", event.getAbout());
		assertEquals("5.00", event.getPrice());
		assertEquals("wina.jpg", event.getImage());
		connection.close();
		
	}
	
	@Test
	public void testAdd() throws SQLException{
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
						" values('mamuka','sakhelashvili','bool','wtf','birja12@terjola.edu.ge',"
						+ "'+995598465565','mdinareze.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'chistavi','sofeli','tancebia')";
		stmt.executeUpdate(addPlace);
		
		
		
		assertEquals(BaseErrors.ALL_DONE,manager.addEvent(1, 1, "event1","2014-06-14 02:22:06", "disco", "0.0", "chveeen.jpg"));
		assertEquals(BaseErrors.ALL_DONE,manager.addEvent(1,1,"event2","2014-06-14 02:22:06","wavidaa","0.0","gushin ro iyo.jpg"));
		 
		String query = "select * from Event where ID=" + manager.getEventID("event1") +  ";";
		
		result = stmt.executeQuery(query);
		
		assertEquals(true,result.next());
		
		 
		
		assertEquals("2014-06-14 02:22:06", result.getString("Time"));
		 
		assertEquals("disco", result.getString("About"));
		assertEquals("0.0", result.getString("price"));
		assertEquals("chveeen.jpg", result.getString("Image"));
		connection.close();
	}
	
	
	@Test 
	public void testUpdateInfo() throws SQLException{
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('giorgi','sakhelashvili','maita','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','mdinareze.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'zneula','sofeli','beko')";
		stmt.executeUpdate(addPlace);
		
		String addEvent = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
							"values(1,1,'discoo2','2/15/2014','wamodit','5.00','modee.jpg')";
		stmt.executeUpdate(addEvent);
		
		int id = manager.getEventID("discoo2");
		 
		manager.updateInfo( id, "discoo3", "2/19/2014", "mere wamodit", "4.00");
		 
		String query = "select * from Event where ID=" + manager.getEventID("discoo3") + ";";
		result = stmt.executeQuery(query);
		 
		assertEquals(true,result.next());
		assertEquals("2/19/2014", result.getString("Time"));
		assertEquals("mere wamodit",result.getString("About"));
		assertEquals("4.00",result.getString("Price"));
		connection.close();
	} 
	
	
	@Test
	public void testGoingUsers() throws SQLException{
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('antoni','nemsadze','shene','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','dagagdeb dzirs.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addUser1 = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('iura','nemsadze','ravaxar','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','burti.jpg')";
		stmt.executeUpdate(addUser1);
		
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'mefis wyaro','sofeli','beko')";
		stmt.executeUpdate(addPlace);
		
		String addEvent = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
							"values(1,1,'event4','2/15/2014','moitmoit','5.00','guguni.jpg')";
		stmt.executeUpdate(addEvent);
		int id = manager.getEventID("event4");
		
		String addGoingUsers = "insert into User_Going_Event(UserID,EventID) "
				+ "values(1,"+ id + ");";
		
		stmt.executeUpdate(addGoingUsers);
		addGoingUsers = "insert into User_Going_Event(UserID,EventID) "
				+ "values(2,"+ id + ");";
		stmt.executeUpdate(addGoingUsers);
		
		ArrayList<Integer> list = manager.getGoingUsers(id,1);
		
		assertEquals(1, (int)list.get(0));
		assertEquals(2,(int)list.get(1));		
		connection.close();
	}
	 
	
	@Test
	public void testHasAdded() throws SQLException{
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('antoni','nemsadze','maitaa','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','dagagdeb dzirs.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addUser1 = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('iura','nemsadze','vatata','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','burti.jpg')";
		stmt.executeUpdate(addUser1);
		
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'ise wyaro','sofeli','beko')";
		stmt.executeUpdate(addPlace);
		
		String query = "select Id from User where UserName=" + "'maitaa';";		
		result = stmt.executeQuery(query);
		int userID1 = 0;
		if(result.next())
		  userID1 = result.getInt("ID");
		
		query = "select Id from User where UserName=" + "'vatata';";
		
		int userID2 = 0;
		result = stmt.executeQuery(query);
		if(result.next())
		  userID2 = result.getInt("ID");
		
		
		
		String addEvent = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
							"values(" + userID1 + ",1,'discoo5','2/15/2014','moitmoit','5.00','guguni.jpg')";
		stmt.executeUpdate(addEvent);
		String addEvent1 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(" + userID1 + ",2,'discoo6','2/15/2014','moitmoit','5.00','jondi.jpg')";
		stmt.executeUpdate(addEvent1);
		
		 	 
		
		assertEquals(true, manager.hasAdded(userID1,manager.getEventID("discoo5")));  
		assertEquals(true, manager.hasAdded(userID1, manager.getEventID("discoo6")));
		assertEquals(false, manager.hasAdded(userID2, manager.getEventID("discoo6")));
		connection.close();
	}
	
	
	@Test
	public void testGetLatestEvents() throws SQLException{
		
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('antoni','nemsadze','vataa','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','dagagdeb dzirs.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addUser1 = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('iura','nemsadze','vat','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','burti.jpg')";
		stmt.executeUpdate(addUser1);
		
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'isec da asec wyaro','sofeli','beko')";
		stmt.executeUpdate(addPlace);
		
		String addEvent = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
							"values(1,1,'discoo7','2/15/2014','moitmoit','5.00','to est.jpg')";
		stmt.executeUpdate(addEvent);
		String addEvent1 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event6','2/15/2014','moitmoit','5.00','igida.jpg')";
		stmt.executeUpdate(addEvent1);
		
		String addEvent2 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event7','2/16/2014','natata','5.00','moitmoit.jpg')";
		stmt.executeUpdate(addEvent2);
		
		
		String addEvent3 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event8','2/16/2014','natata','5.00','moitmoit.jpg')";
		stmt.executeUpdate(addEvent3);
		
		String addEvent4 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event9','2/16/2014','natata','5.00','natt.jpg')";
		stmt.executeUpdate(addEvent4);
		
		String addEvent5 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event10','2/16/2014','natata','5.00','noty.jpg')";
		stmt.executeUpdate(addEvent5);
		
		String addEvent6 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event11','2/16/2014','natata','5.00','wss.jpg')";
		stmt.executeUpdate(addEvent6);
		
		String addEvent7 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,2,'event12','2/16/2014','natata','5.00','ssrss.jpg')";
		stmt.executeUpdate(addEvent7);
		
		ArrayList<Integer> list = manager.getLatestEvents(3);
		
		assertEquals((int)list.get(0), manager.getEventID("event12"));
		assertEquals((int)list.get(1), manager.getEventID("event11"));
		assertEquals((int)list.get(2),manager.getEventID("event10"));
		connection.close();
	}
	
	@Test
	public void testBandsOnEvent() throws SQLException{
		String addUser = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('antoni','nemsadze','notynoty','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','dagagdeb dzirs.jpg')";
		connection = dataSource.getConnection();
		stmt = connection.createStatement(); 
		stmt.executeUpdate(addUser);
		String addUser1 = "insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)" +
				" values('iura','nemsadze','nichkanichka','wtf','birja12@terjola.edu.ge',"
				+ "'+995598465565','burti.jpg')";
		stmt.executeUpdate(addUser1);
		
		String addPlace = "insert into Place(UserID,Name,Adress,About)" +
								"values(1,'isec kkide da asec wyaro','sofeli','beko')";
		stmt.executeUpdate(addPlace);
		
		 
		
		String addEvent2 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,1,'event14','2/15/2014','moitmoit','5.00','hasting.jpg')";
		stmt.executeUpdate(addEvent2);
		
		String addEvent3 = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
				"values(1,1,'event15','2/15/2014','moitmoit','5.00','dusting.jpg')";
		stmt.executeUpdate(addEvent3);
		
		  
		
		String addBand = "insert into  Band(UserID,Name,About,Mail)" + 
						" values(1,'The Minss', 'jigrebiii', 'racxa@openair.gmail');";
		stmt.executeUpdate(addBand);
		
		String addBand2 = "insert into  Band(UserID,Name,About,Mail)" + 
				" values(1,'araa', 'sastave', 'gej.gmail');";
		stmt.executeUpdate(addBand2);
		
		int id1 = 0;
		int id2 = 0;
		
		result = stmt.executeQuery("select ID from Band where Name='The Minss' ;");
		if(result.next()) id1 = result.getInt("ID");
		
		result = stmt.executeQuery("select ID from Band where Name='araa' ;");
		if(result.next()) id2 = result.getInt("ID");
		
		
		String addBandOnEvent = "insert into Band_On_Event(EventID,BandID)" +
							" values(" + manager.getEventID("event15") + "," + id1 +");";
		System.out.println(addBandOnEvent);
		stmt.executeUpdate(addBandOnEvent);
		
		 
		
		String addBandOnEvent2 = "insert into Band_On_Event(EventID,BandID)" +
				" values(" + manager.getEventID("event15") + ","+ id2 + ");";
			
		stmt.executeUpdate(addBandOnEvent2);
		
		String addBandOnEvent3 = "insert into Band_On_Event(EventID,BandID)" +
				" values(" + manager.getEventID("event14") + "," + id1 + ");";
		
		stmt.executeUpdate(addBandOnEvent3);
		
		ArrayList<Integer> list = manager.getBandsOnEvent(manager.getEventID("event15"));
		
		assertEquals((int)list.get(0), id1);
		assertEquals((int)list.get(1), id2);
		
		ArrayList<Integer> list2 = manager.getBandsOnEvent(manager.getEventID("event14"));
		
		assertEquals((int)list2.get(0), id1);		
		connection.close();
		
	}
	
	@Test
	public void testGetEventsNum() throws SQLException{
	
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		String query = "select count(ID) from Event";
		
		result = stmt.executeQuery(query);
		
		assertEquals(result.next(), true);
		assertEquals(result.getInt("count(ID)"), manager.getEventsNum()); 
		
	}
	
	 
	
	@Test
	public void testgetEventsForNthPage() throws SQLException{
		
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		stmt.execute("insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image) " +
					"values('mamuka','sakhelashvili','donchika','ramtamtam','msakh12@edu.ge','+995598465565','selfshot.jpg');");
		stmt.execute("insert into Place(UserID,Name,Adress,About)"+
						"values(1,'dkad','dad','xinkali');");
		
		String add = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
			"values(1,1,'2/15/2014','event16','moitmoit','5.00','kda.jpg');";
		 
		stmt.executeUpdate(add); 
		
		add = "insert into Event(UserID,PlaceID,Name,Time,About,Price,Image)" +
		"values(1,1,'event17','2/15/2014','moitmoit','5.00','md.jpg');";
		
		 
		 
		stmt.executeUpdate(add);
						 
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		String query = "select ID from Event order by ID desc limit 0," + ConstantValues.NUM_EVENT_ON_PER_PAGE + ";";
		result = stmt.executeQuery(query);
		 
		
		
		
		ArrayList<Integer> list = manager.getEventsForNthPage(1);
		 
		
		
		
		assertEquals(result.next(), true);
		for(int i = 0; i < list.size(); i++){
			int num = result.getInt("ID");
			assertEquals((int)list.get(i), num);	
			result.next();
		}
	}
	
}
