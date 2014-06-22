package tests;

import static org.junit.Assert.*;

import java.sql.*;
import java.util.ArrayList;

import objects.User;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import errors.BaseErrors;
import baseConnection.EventConnectionPool;
import baseConnection.UserManager;

public class UserManagerTests {

	private BasicDataSource dataSource;
	private UserManager manager;

	@Before
	public void setUp() throws SQLException {
		EventConnectionPool pool = new EventConnectionPool();
		dataSource = pool.getEventDataSource();
		manager = new UserManager(dataSource);
	}

	@Test
	public void addUserTest1() throws SQLException {
		assertEquals(manager.addUser("misha", "maghriani", "mmagh12", "123456",
				"www.mmagh12@freeuni.edu.ge", "misha.jpg", "551389638"),
				BaseErrors.ALL_DONE);
		Statement stmt = null;
		Connection con;
		con = dataSource.getConnection();
		stmt = con.createStatement();
		String query = "select * from User where UserName = 'mmagh12' ;";
		ResultSet res = stmt.executeQuery(query);
		while (res.next()) {
			assertEquals(res.getString("FirstName"), "misha");
			assertEquals(res.getString("LastName"), "maghriani");
			assertEquals(res.getString("UserName"), "mmagh12");
			assertEquals(res.getString("Password"), "123456");
			assertEquals(res.getString("Mail"), "www.mmagh12@freeuni.edu.ge");
			assertEquals(res.getString("Image"), "misha.jpg");
			assertEquals(res.getString("MobileNumber"), "551389638");
			assertEquals(manager.addUser("misha", "maghriani", "mmagh12", "123456",
					"www.mmagh12@freeuni.edu.ge", "misha.jpg", "551389638"),
					BaseErrors.UNABLE_EXECUTE);
		}
	}


	@Test
	public void addUserTest2() throws SQLException {
		assertEquals(manager.addUser("mamuka", "sakhelashvili", "msakh12",
				"123456", "www.msakh12@freeuni.edu.ge", "mamuka.jpg",
				"598465565"), BaseErrors.ALL_DONE);
		Statement stmt = null;
		Connection con;
		con = dataSource.getConnection();
		stmt = con.createStatement();
		String query = "select * from User where UserName = 'msakh12' ;";
		ResultSet res = stmt.executeQuery(query);
		while (res.next()) {
			assertEquals(res.getString("FirstName"), "mamuka");
			assertEquals(res.getString("LastName"), "sakhelashvili");
			assertEquals(res.getString("UserName"), "msakh12");
			assertEquals(res.getString("Password"), "123456");
			assertEquals(res.getString("Mail"), "www.msakh12@freeuni.edu.ge");
			assertEquals(res.getString("Image"), "mamuka.jpg");
			assertEquals(res.getString("MobileNumber"), "598465565");
		}
	}

	@Test
	public void getUserTest1() throws SQLException {
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('tornike','abuladze','tabul12','123456','598728181','tornike.jpg',"
				+ "'tabul12@freeuni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'tabul12';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		User user = manager.getUser(ID);
		assertEquals(user.getImage(),"tornike.jpg");
		assertEquals(user.getLastName(),"abuladze");
		assertEquals(user.getMail(),"tabul12@freeuni.edu.ge");
		assertEquals(user.getName(),"tornike");
		assertEquals(user.getPassword(),"123456");
		assertEquals(user.getUserName(),"tabul12");
		assertEquals(user.getMobileNumber(),"598728181");
	}

	@Test
	public void getUserTest2() throws SQLException {
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('andro','kasrashvili','akasr13','123456','555126554','andro.jpg',"
				+ "'akasr13@freeuni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'akasr13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		User user = manager.getUser(ID);
		assertEquals(user.getImage(),"andro.jpg");
		assertEquals(user.getLastName(),"kasrashvili");
		assertEquals(user.getMail(),"akasr13@freeuni.edu.ge");
		assertEquals(user.getName(),"andro");
		assertEquals(user.getPassword(),"123456");
		assertEquals(user.getUserName(),"akasr13");
		assertEquals(user.getMobileNumber(),"555126554");
	}
	
	@Test
	public void getUserTest3() throws SQLException {
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('saba','kharabadze','skhar12','123456','123456789','saba.jpg',"
				+ "'skhar12@freeuni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'skhar12';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		User user = manager.getUser(ID);
		assertEquals(user.getImage(),"saba.jpg");
		assertEquals(user.getLastName(),"kharabadze");
		assertEquals(user.getMail(),"skhar12@freeuni.edu.ge");
		assertEquals(user.getName(),"saba");
		assertEquals(user.getPassword(),"123456");
		assertEquals(user.getUserName(),"skhar12");
		assertEquals(user.getMobileNumber(),"123456789");
	}
	
	
	@Test
	public void alreadyExistsTest1() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('nika','maghriani','nmagh13','123456','595670708','nika.jpg',"
				+ "'nmagh13@freeuni.edu.ge');";
		stmt.executeUpdate(query);
		assertEquals(manager.alreadyExists("nmagh13"),true);
		assertEquals(manager.alreadyExists("tabul11"),false);
	}
	
	@Test
	public void alreadyExistsTest2() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('tazo','toradze','ttora13','123456','598763677','tazo.jpg',"
				+ "'ttora13@freeuni.edu.ge');";
		stmt.executeUpdate(query);
		assertEquals(manager.alreadyExists("ttora13"),true);
		assertEquals(manager.alreadyExists("tora13"),false);
	}
	
	@Test
	public void alreadyExistsTest3() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('nika','beridze','nberi13','123456','123456789','nika.jpg',"
				+ "'nberi13@freeuni.edu.ge');";
		stmt.executeUpdate(query);
		assertEquals(manager.alreadyExists("nberi13"),true);
		assertEquals(manager.alreadyExists("nberi1"),false);
	}
	
	
	@Test
	public void   isPunishedTest1() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('boria','nizharadze','bnizh13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'bnizh13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		query = "insert into Punished (UserID)"
				+ "values (" + ID + ");";
		stmt.executeUpdate(query);
		assertEquals(manager.isPunished(ID), true);
		assertEquals(manager.isPunished(-4), false);
	}
	
	
	@Test
	public void   isPunishedTest2() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('sandro','meshveliani','smesh13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'smesh13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		query = "insert into Punished (UserID)"
				+ "values (" + ID + ");";
		stmt.executeUpdate(query);
		assertEquals(manager.isPunished(ID), true);
		assertEquals(manager.isPunished(50), false);
	}
	
	@Test
	public void   isPunishedTest3() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('rajden','kapetivadze','rkape13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'rkape13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		query = "insert into Punished (UserID)"
				+ "values (" + ID + ");";
		stmt.executeUpdate(query);
		assertEquals(manager.isPunished(ID), true);
		assertEquals(manager.isPunished(65), false);
	}
	
	
	@Test
	public void   isAdminTest1() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('ghvtiso','gurabanidze','ggura13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'ggura13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		query = "insert into Admin (UserID)"
				+ "values (" + ID + ");";
		stmt.executeUpdate(query);
		assertEquals(manager.isAdmin(ID), true);
		assertEquals(manager.isAdmin(-3), false);
	}
	
	@Test
	public void   isAdminTest2() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('firuz','kanteladze','fkant13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'fkant13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		query = "insert into Admin (UserID)"
				+ "values (" + ID + ");";
		stmt.executeUpdate(query);
		assertEquals(manager.isAdmin(ID), true);
		assertEquals(manager.isAdmin(128), false);
	}
	
	@Test
	public void   isAdminTest3() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('seva','gorduladze','sgord13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'sgord13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		query = "insert into Admin (UserID)"
				+ "values (" + ID + ");";
		stmt.executeUpdate(query);
		assertEquals(manager.isAdmin(ID), true);
		assertEquals(manager.isAdmin(10867), false);
	}
	
	@Test
	public void   getUserIDTest1() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('sergo','babunashvili','sbabu13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'sbabu13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		assertEquals(manager.getUserID("sbabu13","123456"),ID);
		assertNotEquals(manager.getUserID("sbabu13","123456"),456);
		assertNotEquals(manager.getUserID("sbabu13","123466"),ID);
		assertNotEquals(manager.getUserID("sbabu12","123456"),ID);
	}
	
	@Test
	public void   getUserIDTest2() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('gia','lorwkiani','glorw13','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'glorw13';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		assertEquals(manager.getUserID("glorw13","123456"),ID);
		assertNotEquals(manager.getUserID("glorw13","123456"),900);
		assertEquals(manager.getUserID("glorw13","123466"),-1);
		assertNotEquals(manager.getUserID("glorw12","123456"),ID);
	}
	
	@Test
	public void updateInfoTest1() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('gia','lorwkiani','glorw11','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'glorw11';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		int k = manager.updateInfo(ID, "julieta", "vashaymadze",
				"jvash13@freeuni.edu.ge","5555555555", "raisparoli");
		assertEquals(k,BaseErrors.ALL_DONE);
		ResultSet re = stmt.executeQuery("Select * from User Where ID  ="+ ID +";");
		if(re.next()){
			String name = re.getString("FirstName");
			assertEquals(name,"julieta");
			assertNotEquals(name,"juleta");
			assertEquals(re.getString("LastName"),"vashaymadze");
			assertEquals(re.getString("Mail"),"jvash13@freeuni.edu.ge");
			assertEquals(re.getString("MobileNumber"),"5555555555");
			assertEquals(re.getString("Image"),"yvelazemagaridgeee.jpg");
			assertEquals(re.getString("Password"),"raisparoli");
		}
		else
			throw new AssertionError();
	}
	
	
	@Test
	public void updateInfoTest2() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('cristiano','ronaldo','cron12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'cron12';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		int k = manager.updateInfo(ID, "jaba", "dvali",
				"jvash13@freeuni.edu.ge","5555555555", "raisparoli");
		assertEquals(k,BaseErrors.ALL_DONE);
		ResultSet re = stmt.executeQuery("Select * from User Where ID  ="+ ID +";");
		if(re.next()){
			assertEquals(re.getString("FirstName"),"jaba");
			assertEquals(k, BaseErrors.ALL_DONE);
		}
		else
			throw new AssertionError();
	}
	
	
	@Test
	public void punishUserTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('leo','messi','lmess12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'lmess12';");
		int ID = -1;
		while(res.next()){
			ID = res.getInt("ID");
		}
		manager.punishUser(ID);
		ResultSet re = stmt.executeQuery("Select UserID from Punished where UserID = "+ID);
		assertEquals(re.next(),true);
		if(re.next()){
			assertEquals(ID, re.getInt("UserID"));
		}
	}
	
	
	@Test
	public void addedBandsTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('arien','robben','arobb12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('tiago','silva','tsilv12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'arobb12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "Insert into Band(UserID,Name,About,Mail) "
				+ "values("+userID+",'pink','sfsh','mufgr');";
		stmt.executeUpdate(quer);
		quer =  "Insert into Band(UserID,Name,About,Mail) "
				+ "values("+userID+",'floyd','sfsh','mufg');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Band where Name ='pink';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Band where Name ='floyd'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		ArrayList<Integer> ban = new ArrayList<Integer>();
		ban = manager.addedBands(userID, 1);
		assertEquals(ban,bands);
	}
	
	@Test
	public void addedPlacesTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('anderea','pirlo','apirl12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('tiago','mota','tmota12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'tmota12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'restoran','rgbr','dgf');";
		stmt.executeUpdate(quer);
		quer =  "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'bar','javakhishvili','adf');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Place where Name ='restoran';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Place where Name ='bar'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		ArrayList<Integer> ban = new ArrayList<Integer>();
		ban = manager.addedPlaces(userID, 1);
		assertEquals(ban,bands);
	}
	
	@Test
	public void addedEventsTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('steven','gerard','sgeral12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('wayne','roonye','wroon12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'wroon12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'restor','rgbr','dgf');";
		stmt.executeUpdate(quer);
		quer =  "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'ba','javakhishvili','adf');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Place where Name ='restor';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Place where Name ='ba'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		ArrayList<Integer> ban = new ArrayList<Integer>();
		ban = manager.addedPlaces(userID, 1);
		assertEquals(ban,bands);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ ban.get(0) +",'asd');";
		stmt.executeUpdate(quer);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ ban.get(1) +",'zxc');";
		stmt.executeUpdate(quer);		
		s = "Select ID from Event where About ='asd';";
		re = stmt.executeQuery(s);
		ArrayList<Integer> events = new ArrayList<Integer>(); 
		while(re.next()){
			events.add(re.getInt("ID"));
		}
		s = "Select ID from Event where About ='zxc';";
		r = stmt.executeQuery(s);
		if(r.next()){
			events.add(r.getInt("ID"));
		}
		ArrayList<Integer> eve = new ArrayList<Integer>();
		eve = manager.addedEvents(userID, 1);
		assertEquals(events,eve);
	}
	
	@Test
	public void hasAddedEventTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('zalatan','ibrahimovic','zibra12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('edison','cavani','ecava12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'ecava12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'sg','rgbr','msdg');";
		stmt.executeUpdate(quer);
		quer =  "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'ethb','javakhishvili','mkjhvk');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Place where Name ='sg';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Place where Name ='ethb'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		ArrayList<Integer> ban = new ArrayList<Integer>();
		ban = manager.addedPlaces(userID, 1);
		assertEquals(ban,bands);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ ban.get(0) +",'mdebareobs');";
		stmt.executeUpdate(quer);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ ban.get(1) +",'misamartia');";
		stmt.executeUpdate(quer);		
		s = "Select ID from Event where About ='mdebareobs';";
		re = stmt.executeQuery(s);
		ArrayList<Integer> events = new ArrayList<Integer>(); 
		while(re.next()){
			events.add(re.getInt("ID"));
		}
		s = "Select ID from Event where About ='misamartia';";
		r = stmt.executeQuery(s);
		if(r.next()){
			events.add(r.getInt("ID"));
		}
		
		ArrayList<Integer> eve = new ArrayList<Integer>();
		assertEquals(manager.hasAddedEvent(userID, events.get(0)), true);
		assertEquals(manager.hasAddedEvent(userID, events.get(1)), true);
		assertEquals(manager.hasAddedEvent(1, events.get(1)), false);
		assertEquals(manager.hasAddedEvent(userID, 7), false);
		
		assertEquals(manager.hasAddedEvent(0, 7), false);
	
	}
	
	
	@Test
	public void addGoingTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('luka','modricc','lmodr12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('neimar','junior','njuni12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'njuni12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'macs','rgbr','msdg');";
		stmt.executeUpdate(quer);
		quer =  "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'physic','javakhishvili','mkjhvk');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Place where Name ='macs';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Place where Name ='physic'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		ArrayList<Integer> ban = new ArrayList<Integer>();
		ban = manager.addedPlaces(userID, 1);
		assertEquals(ban,bands);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ ban.get(0) +",'qutaisi');";
		stmt.executeUpdate(quer);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ ban.get(1) +",'batumi');";
		stmt.executeUpdate(quer);		
		s = "Select ID from Event where About ='qutaisi';";
		re = stmt.executeQuery(s);
		ArrayList<Integer> events = new ArrayList<Integer>(); 
		while(re.next()){
			events.add(re.getInt("ID"));
		}
		s = "Select ID from Event where About ='batumi';";
		r = stmt.executeQuery(s);
		if(r.next()){
			events.add(r.getInt("ID"));
		}
		manager.addGoing(userID, events.get(0));
		s = "Select UserID from User_Going_Event where EventID ="+ events.get(0) +";";
		r = stmt.executeQuery(s);
		int k = 0;
		if(r.next()){
			k = r.getInt("UserID");
		}
		assertEquals(userID, k);
		assertNotEquals(userID, k+1);
		assertNotEquals(1, k);
	}
	
	@Test
	public void isGoingTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('shota','futkaradze','sfutk12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('grigol','kapanadze','gkapa12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'gkapa12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'ssamikitno','rgbr','msdg');";
		stmt.executeUpdate(quer);
		quer =  "insert into Place(UserID,Name,Adress,About)"
				+ "values(" + userID + ",'shemoixede','javakhishvili','mkjhvk');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Place where Name ='shemoixede';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Place where Name ='ssamikitno'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		ArrayList<Integer> ban = new ArrayList<Integer>();
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ bands.get(0) +",'open');";
		stmt.executeUpdate(quer);
		quer = "insert into Event(UserID,PlaceID,About)"
				+ "values(" + userID + ","+ bands.get(1) +",'air');";
	    stmt.executeUpdate(quer);		
		s = "Select ID from Event where About ='open';";
		re = stmt.executeQuery(s);
		ArrayList<Integer> events = new ArrayList<Integer>(); 
		while(re.next()){
			events.add(re.getInt("ID"));
		}
		s = "Select ID from Event where About ='air';";
		r = stmt.executeQuery(s);
		if(r.next()){
			events.add(r.getInt("ID"));
		}
		stmt.executeUpdate("insert into User_Going_Event (UserID, EventID)"
				+ "values (" + userID + ", " + events.get(0) + ");");
		s = "Select UserID from User_Going_Event where EventID = "+ events.get(0) +";";
		r = stmt.executeQuery(s);
		int k = 0;
		if(r.next()){
			k = r.getInt("UserID");
		}
		assertEquals(manager.isGoing(k, events.get(0)),true);
		assertEquals(manager.isGoing(k, events.get(1)),false);
		assertEquals(manager.isGoing(1, events.get(1)),false);
		assertEquals(manager.isGoing(1, events.get(0)),false);
	}
	
	@Test
	public void hasAddedBandTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('andres','iniesta','anie12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('david','villa','dvill12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'dvill12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Band(UserID,Name,About)"
				+ "values(" + userID + ",'nir','dgf');";
		stmt.executeUpdate(quer);
		quer =  "insert into Band(UserID,Name,About)"
				+ "values(" + userID + ",'vana','adf');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from band where Name ='nir';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Band where Name ='vana'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		assertEquals(manager.hasAddedBand(userID, bands.get(0)), true);
		assertEquals(manager.hasAddedBand(userID, bands.get(1)), true);
		assertEquals(manager.hasAddedBand(userID+1, bands.get(1)), false);
	}
	
	@Test
	public void hasAddedPlaceTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('xavi','ernandes','xerna12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('dani','alves','dalve12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'dalve12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Place(UserID,Name,About)"
				+ "values(" + userID + ",'mmachaxela','machaxela');";
		stmt.executeUpdate(quer);
		quer =  "insert into Place(UserID,Name,About)"
				+ "values(" + userID + ",'orilula','orilula');";
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Place where Name ='mmachaxela';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Place where Name ='orilula'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		assertEquals(manager.hasAddedPlace(userID, bands.get(0)), true);
		assertEquals(manager.hasAddedPlace(userID, bands.get(1)), true);
		assertEquals(manager.hasAddedPlace(userID+1, bands.get(1)), false);
	}
	
	@Test
	public void addInWishlistTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('carles','puyol','kpuyo12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('gerard','pique','gpiqu12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'gpiqu12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Band (UserID,Name,About)"
				+ "values(" + userID + ",'33','machaxela');";
		stmt.executeUpdate(quer);
		quer =  "insert into Band (UserID,Name,About)"
				+ "values(" + userID + ",'a','orilula');";	
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Band where Name ='33';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Band where Name = 'a'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		manager.addInWishList(userID, bands.get(0));
		manager.addInWishList(userID, bands.get(1));
		ArrayList<Integer> arr = new ArrayList<Integer>();
		s = "select BandID from User_Band_Wishlist where UserID = " + userID;
		ResultSet result = stmt.executeQuery(s);
		while(result.next()){
			arr.add(result.getInt("BandID"));
		}
		assertEquals(arr,bands);
	}
	
	@Test
	public void getWishlistTest() throws SQLException{
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		String query = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('vincente','bosque','vbosq12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(query);
		String qu = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,"
				+ "Image,Mail) Values('joze','mourinio','jmour12','123456','123456789','boria.jpg',"
				+ "'bnizh2013@agruni.edu.ge');";
		stmt.executeUpdate(qu);
		ResultSet res = stmt.executeQuery("select ID from User where UserName = 'jmour12';");
		int userID = -1;
		while(res.next()){
			userID = res.getInt("ID");
		}
		String quer = "insert into Band (UserID,Name,About)"
				+ "values(" + userID + ",'black','machaxela');";
		stmt.executeUpdate(quer);
		quer =  "insert into Band (UserID,Name,About)"
				+ "values(" + userID + ",'sabath','orilula');";	
		stmt.executeUpdate(quer);
		ArrayList<Integer> bands = new ArrayList<Integer>(); 
		String  s = "Select ID from Band where Name ='black';";
		ResultSet re = stmt.executeQuery(s);
		while(re.next()){
			bands.add(re.getInt("ID"));
		}
		s = "Select ID from Band where Name = 'sabath'";
		ResultSet r = stmt.executeQuery(s);
		if(r.next()){
			bands.add(r.getInt("ID"));
		}
		s =  "insert into User_Band_Wishlist (UserID, BandID)"
				+ "values (" + userID + "," + bands.get(0) + ");";
		stmt.executeUpdate(s);
		s =  "insert into User_Band_Wishlist (UserID, BandID)"
				+ "values (" + userID + "," + bands.get(1) + ");";
		stmt.executeUpdate(s);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr = manager.getWishlist(userID, 1);
		assertEquals(arr,bands);
	}
	
}
