package tests;

import static org.junit.Assert.*;

import java.sql.*;
import java.util.ArrayList;

import objects.Band;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import errors.BaseErrors;
import baseConnection.BandManager;
import baseConnection.EventConnectionPool;

public class BandManagerTests {
	private EventConnectionPool CP;
	private BasicDataSource dataSource;
	@Before
	public void setUp() throws SQLException
	{
		CP = new EventConnectionPool();
		dataSource = CP.getEventDataSource();
	}

	@Test
	//1 is damatebis shemtxveveashi
	public void addBandTest1() throws SQLException {
			BandManager manag = new BandManager(dataSource);
			Connection con = dataSource.getConnection();
			Statement stm = con.createStatement();
			String insertToUser = "insert into User(UserName) values('tornike')";
			stm.executeUpdate(insertToUser);
			assertEquals(BaseErrors.ALL_DONE,manag.addBand(1,"Radiohead","Rock Group", "radiohead@gmail.com"));
			String q ="select * from Band where Name='Radiohead'";
			ResultSet res = stm.executeQuery(q);
			if(res.next())
			{
				assertEquals("Radiohead",res.getString("Name"));
				assertEquals("Rock Group",res.getString("About"));
				assertEquals("radiohead@gmail.com",res.getString("Mail"));
			}
			else
				throw new AssertionError();
			con.close();
	}
	@Test
	//tu bazashi 2 maincaa
	public void addBandTest2() throws SQLException
	{		
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String insertToUser = "insert into User(UserName) values('toko')";
		stm.executeUpdate(insertToUser);
		assertEquals(BaseErrors.ALL_DONE,manag.addBand(1,"Pink Floyd","Rock Group", "PinkFloyd@gmail.com"));
		String q ="select * from Band where Name='Pink Floyd'";
		ResultSet res = stm.executeQuery(q);
		if(res.next())
		{
			assertEquals("Pink Floyd",res.getString("Name"));
			assertEquals("Rock Group",res.getString("About"));
			assertEquals("PinkFloyd@gmail.com",res.getString("Mail"));
		}
		else
			throw new AssertionError();
		con.close();

	}
	@Test
	//rom ver shezlos ori ertnairi bendis damateba
	public void addBandTest3()
	{
		BandManager manag = new BandManager(dataSource);
		assertEquals(BaseErrors.ALL_DONE,manag.addBand(1,"Pink Floyd1","Rock Group", "PinkFloyd@gmail.com"));
		assertEquals(BaseErrors.UNABLE_EXECUTE,manag.addBand(1,"Pink Floyd1","Rock Group", "PinkFloyd@gmail.com"));
	}
	@Test
	//testebi bendis dabrunebis martivi
	public void getBandTest1() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('Nigthwish','Rock Group','Nigthwish@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='NigtHwish'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		Band b = manag.getBand(TID);
		assertEquals("Nigthwish", b.getName());
		assertEquals("Rock Group", b.getAbout());
		assertEquals("Nigthwish@gmail.com",b.getMail());
		con.close();
	}
	@Test
	//testi informaciis ganaxlebis metodistvis
	//vamatebt bands vanaxlebT infomraias da vamowmebt
	public void updateBandInfoTest() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('Gia','Pop Musican','Gia@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='Gia'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		System.out.println(manag.updateBandInfo(TID,"Otxi Gia","Pop Band","4G.gmail.com"));
		String q3 = "select * from Band where ID="+TID;
		ResultSet resG = stm.executeQuery(q3);
		if(resG.next())
		{
			assertEquals("Otxi Gia",resG.getString("Name"));
			assertEquals("Pop Band",resG.getString("About"));
			assertEquals("4G.gmail.com",resG.getString("Mail"));
		} 
		else
			throw new AssertionError();
		con.close();

	}
	@Test
	//music is damatebis testi
	public void addMusicTest1() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('The Mins','Rock Group','themins@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='The Mins'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		manag.addMusic(TID,"Blind World");
		String qMusic = "select * from Music where Name='Blind World' and BandID="+TID;
		ResultSet resMus = stm.executeQuery(qMusic);
		if(!resMus.next()) throw new AssertionError();
		con.close();
	}
	@Test
	//ori ertnairi rom ver daematos
	public void addMusicTest2() throws SQLException 
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('Ara','Rock Group','Ara@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='The Mins'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		manag.addMusic(TID, "ki");
		assertEquals(BaseErrors.UNABLE_EXECUTE,manag.addMusic(TID, "ki"));
		con.close();
	}
	@Test
	//suratis damatebis testi
	public void addImageTest1() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('tt','Rock Group','tt@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='tt'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		manag.addImage(TID,"t.jpg");
		String qMusic = "select * from Band_Image where Name='t.jpg' and BandID="+TID;
		ResultSet resMus = stm.executeQuery(qMusic);
		if(!resMus.next()) throw new AssertionError();
		con.close();
	}
	@Test
	//ori ertnairi saxelis rom ver daematos
	public void addImageTest2() throws SQLException 
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('yvela','Rock Group','yvela@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='yvela'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		manag.addImage(TID,"tt.jpg");
		assertEquals(BaseErrors.UNABLE_EXECUTE,manag.addImage(TID,"tt.jpg"));		
		con.close();
	} 
	@Test
	//videos damaetbois testi
	public void addVideoTest1() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('sting','Pop Musican','st@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='sting'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		manag.addVideo(TID,"fragile.mp4");
		String qVide = "select * from Video where Name='fragile.mp4' and BandID="+TID;
		ResultSet resVid = stm.executeQuery(qVide);
		if(!resVid.next()) throw new AssertionError();		
		con.close();
	}
	@Test
	//ori ertnairi rom ver daematos
	public void addVideoTest2() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('beatles','Rock Group','beatles@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='beatles'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		manag.addVideo(TID, "imagine.mp4");
		assertEquals(BaseErrors.UNABLE_EXECUTE,manag.addVideo(TID,"imagine.mp4"));
		con.close();
	}
	@Test
	//testi suratis ganaxlebistvis testi damoukidebelia bazis mdgomareobisgan
	public void updateProfileImageTest1() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('queen','Rock Group','queen@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='beatles'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		String q1 = "insert into Band_Image(Name,BandID) values('love.jpg',"+TID+")";
		String q11 = "insert into Band_Image(Name,BandID) values('queen.jpg',"+TID+")";
		stm.executeUpdate(q1);
		stm.executeUpdate(q11);
		String imID = "select ID from Band_Image where Name='love.jpg'";
		ResultSet resImage = stm.executeQuery(imID);
		int imageID = 0;
		if(resImage.next())
		{
			imageID = resImage.getInt("ID");
		}
		else
			throw new AssertionError();
		String q3 = "insert into Band_Profile_Image(Band_ImageID,BandID) values("+imageID+","+TID+")";
		stm.executeUpdate(q3);
		String imIDNew = "select * from Band_Image where Name='queen.jpg'";
		ResultSet resImageNew = stm.executeQuery(imIDNew);
		int imageIDNew = 0;
		if(resImageNew.next())
		{
			imageIDNew = resImageNew.getInt("ID");
		}
		else
			throw new AssertionError();
		assertEquals(BaseErrors.ALL_DONE,manag.updateProfileImage(TID, imageIDNew));
		String check = "select* from Band_Profile_Image where BandID="+TID;
		ResultSet resCheck = stm.executeQuery(check);
		if(resCheck.next())
		{
			assertEquals(resCheck.getInt("Band_ImageID"), imageIDNew);
		}
		else
			throw new AssertionError();
	}
	@Test
	//test videos wamogebistvis
	public void getVideosTest() throws SQLException
	{

		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('loudspeaker','Rock Group','loudspeaker@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='loudspeaker'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		String qVoide1 = "insert into Video(BandID,Name) values("+TID+","+"'in this world')";
		String qVoide2 = "insert into Video(BandID,Name) values("+TID+","+"'world in my eyes')";
		stm.executeUpdate(qVoide1);
		stm.executeUpdate(qVoide2);
		ArrayList<String>exp = new ArrayList<>();
		exp.add("in this world");
		exp.add("world in my eyes");
		ArrayList<String>act = manag.getVideos(TID,1);
		assertEquals(exp.size(), act.size());
		for(int i = 0; i<exp.size(); i++)
			assertEquals(exp.get(i), act.get(i));
		con.close();
	}
	@Test
	public void getImagesTest() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('moby','alternative rock','moby@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='moby'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		String qImage1 = "insert into Band_Image(BandID,Name) values("+TID+","+"'natural blue')";
		String qImage2 = "insert into Band_Image(BandID,Name) values("+TID+","+"'be the one')";
		stm.executeUpdate(qImage1);
		stm.executeUpdate(qImage2);
		
		ArrayList<String>exp = new ArrayList<>();
		exp.add("natural blue");
		exp.add("be the one");
		ArrayList<String>act = manag.getImages(TID,1);
		assertEquals(exp.size(), act.size());
		for(int i = 0; i<exp.size(); i++)
			assertEquals(exp.get(i), act.get(i));
		con.close();
	}
	@Test
	public void getProfileImageTest() throws SQLException
	{
		BandManager manag = new BandManager(dataSource);
		Connection con = dataSource.getConnection();
		Statement stm = con.createStatement();
		String q ="insert into Band(Name,About,Mail) values('led zeppelin','rock band','zeppelin@gmail.com')";
		stm.executeUpdate(q);
		String id ="select ID from Band where Name='led zeppelin'";
		ResultSet res = stm.executeQuery(id);
		int TID = 0;
		if(res.next())
		{
			TID = res.getInt("ID");
		}
		else
			throw new AssertionError();
		String q1 = "insert into Band_Image(Name,BandID) values('heaven.jpg',"+TID+")";
		stm.executeUpdate(q1);
		String imID = "select ID from Band_Image where Name='heaven.jpg'";
		ResultSet resImage = stm.executeQuery(imID);
		int imageID = 0;
		if(resImage.next())
		{
			imageID = resImage.getInt("ID");
		}
		else
			throw new AssertionError();
		String q3 = "insert into Band_Profile_Image(Band_ImageID,BandID) values("+imageID+","+TID+")";
		stm.executeUpdate(q3);
		assertEquals("heaven.jpg",manag.getProfileImage(TID));
		con.close();
	}
	
	

}
