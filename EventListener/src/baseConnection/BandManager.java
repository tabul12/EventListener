package baseConnection;

import java.sql.*;
import java.util.ArrayList;

import objects.Band;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.sun.corba.se.spi.orbutil.fsm.State;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import errors.BaseErrors;

public class BandManager {
	private static final int numberOfVideosPerPage = 10; 
	private static final int numberOfImagesPerPage = 10;
	private static final int numberOfMusicsPerPage = 10;
	private BasicDataSource eventDataSource;
	
	/**
	 * constructor
	 * @param connectionPool
	 */
	public BandManager(BasicDataSource evenBasicDataSource) {
		this.eventDataSource = evenBasicDataSource;
		// TODO Auto-generated constructor stub
	}
	/**
	 * this method creates and returns Band object based on base 
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public Band getBand(int id) throws SQLException
	{
		int ID = 0;
		int UserID = 0;
		String Name = null;
		String About = null;
		String Mail = null;
		Connection con  = eventDataSource.getConnection();
		java.sql.Statement stm = con.createStatement();
		String query = "select * from Band where ID="+id;
		ResultSet rest=stm.executeQuery(query);
		while(rest.next())
		{
			ID = rest.getInt("ID");
			UserID = rest.getInt("UserID");
			Name = rest.getString("Name");
			About = rest.getString("About");
			Mail = rest.getString("Mail");
		}
		Band band = new Band(ID,UserID,Name, About, Mail);
		con.close();
		return band;
	}
	/**
	 * this is common method for updating base
	 * @param query
	 * @return code of error
	 */
	private int changeBase(String query)
	{
		Connection con;	
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					stm.executeUpdate(query);
				} catch (Exception e) {
					con.close();
					return BaseErrors.UNABLE_EXECUTE;
				}		
				con.close();
			} catch (Exception e) {
				con.close();
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
		} catch (SQLException e){
			// TODO Auto-generated catch block
			return BaseErrors.UNABLE_CONNECTION;
		}
		return BaseErrors.ALL_DONE;
		
	}
	/**
	 * adds Band into database, return true if adding complete succesfully 
	 * else returns false;
	 * @param userID
	 * @param Name
	 * @param About
	 * @param Mail
	 * @return code of error
	 */
	public int addBand(int UserID,String Name,String About,String Mail)
	{
		String query= "insert into Band(UserID,Name,About,Mail) "
				+ "values("+UserID+",'"+Name+"','"+About+"','"+Mail+"')";
		//String query1 = "isert into Band_Profile_Image(BandID,"
		return changeBase(query);
	}
	/**
	 * updates bands information
	 * @param bandID
	 * @param Name
	 * @param About
	 * @param Mail
	 * @return code of error
	 */
	public int updateBandInfo(int BandID,String Name,String About,String Mail)
	{
		String query= "update Band SET Name='"+Name+
				"',About='"+About+"',Mail='"+Mail+"' WHERE ID="+BandID;
		return changeBase(query);
	}
	/**
	 * when band adds music 
	 * @param BandID
	 * @param Name
	 * @return code of error
	 */
	public int addMusic(int BandID,String Name)
	{
		String query = "insert into Music(BandID,Name) "
				+"values("+BandID+",'"+Name+"')";
		return changeBase(query);
	}
	/**
	 * when band adds image
	 * @param BandID
	 * @param Name
	 * @return code of error
	 */
	public int addImage(int BandID,String Name){
		String query = "insert into Band_Image(BandID,Name) "
				+"values("+BandID+",'"+Name+"')";
		return changeBase(query);
	}
	/**
	 * when band adds videos
	 * @param BandID
	 * @param Name
	 * @return code of error
	 */
	public int addVideo(int BandID,String Name)
	{
		String query = "insert into Video(BandID,Name) "
				+"values("+BandID+",'"+Name+"')";
		return changeBase(query);			
	}
	/**
	 * updates profile image
	 * @param BandID
	 * @param Band_ImageID
	 * @return code Of error
	 */
	public int updateProfileImage(int BandID,int Band_ImageID)
	{
		String query = "UPDATE Band_Profile_Image SET Band_ImageID="+Band_ImageID+
				" where BandID="+BandID;
		return changeBase(query);
	}
	private ArrayList<String> getImagesAndVideos(String query)
	{
		ArrayList<String> ans = new ArrayList<>();
		Connection con;
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					ResultSet res = stm.executeQuery(query);
					while(res.next())
					{
						String name = res.getString("Name");
						ans.add(name);
					}
				} catch (Exception e) {
					System.err.println("eror code "+BaseErrors.UNABLE_EXECUTE);
					return null;
				}
			} catch (SQLException e) {
			  System.err.println("eror code "+BaseErrors.UNABLE_CREATE_STATEMENT);
			  return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("eror code "+BaseErrors.UNABLE_CONNECTION);
			return null;
		}
		return ans;
	}
	/**
	 * 
	 * @param BandID
	 * @param pageNumber
	 * @return lists of video names
	 */
	public ArrayList<String> getVideos(int BandID,int pageNumber)
	{
		String query = "select * from Video where BandID="+BandID+" limit "
		+(pageNumber-1)*numberOfVideosPerPage+","+numberOfVideosPerPage;
		return getImagesAndVideos(query);
	}
	/**
	 * 
	 * @param BandID
	 * @param pageNumber
	 * @return
	 */
	public ArrayList<String> getImages(int BandID,int pageNumber)
	{
		String query = "select * from Band_Image where BandID="+BandID+" limit "
		+(pageNumber-1)*numberOfVideosPerPage+","+numberOfVideosPerPage;
		return getImagesAndVideos(query);
	}
	/**
	 * 
	 * @param BandID
	 * @return profile image name of Band
	 */
	public String getProfileImage(int BandID)
	{
		String ans=null;
		String query = "select Name from Band_Image,Band_Profile_Image where "
				+ "Band_Profile_Image.Band_ImageID=Band_Image.ID and Band_Image.BandID="+BandID;
		try {
			Connection con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					ResultSet res = stm.executeQuery(query);
					while(res.next())
					{
						ans = res.getString("Name");
					}				
				} catch (Exception e) {
					System.err.println("eror code "+BaseErrors.UNABLE_EXECUTE);
					return ans;
				}
			} catch (SQLException e) {
				System.err.println("eror code "+BaseErrors.UNABLE_CREATE_STATEMENT);
				return ans;
			}
		} catch (SQLException e) {
			System.err.println("eror code "+BaseErrors.UNABLE_CONNECTION);
			return ans;
		}
		
		return ans;
	}
	
	public ArrayList<Integer> getTopBands(int num) throws SQLException{
		String query = "select ID,bandAverageRating(ID) from Band"
				+	" order by bandAverageRating(ID) desc limit " + num + ";";
		
		
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
	 * this method gives us rating for specified Band
	 */
	public double getRating(int bandID) throws SQLException{
		Connection connection = eventDataSource.getConnection();
		Statement stmt = connection.createStatement();
		String query = "select avg(Rating)" + 
					      "from Band_Rating where BandID=" + bandID + ";";
		
		ResultSet result = stmt.executeQuery(query);
		double ans = 0.0;
		if(result.next())
			ans = result.getDouble("avg(Rating)");	
		connection.close();
		return ans;
	}
	
}
