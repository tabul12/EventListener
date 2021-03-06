package baseConnection;

import java.sql.*;
import java.util.ArrayList;

import objects.Band;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
 










import errors.BaseErrors;
import errors.ConstantValues;

public class BandManager {
	private BasicDataSource eventDataSource;

	/**
	 * constructor
	 * 
	 * @param connectionPool
	 */
	public BandManager(BasicDataSource evenBasicDataSource) {
		this.eventDataSource = evenBasicDataSource;
		// TODO Auto-generated constructor stub
	}

	/**
	 * this method creates and returns Band object based on base
	 * 
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public Band getBand(int id){
		int ID = 0;
		int UserID = 0;
		String Name = null;
		String About = null;
		String Mail = null;
		Connection con = null; 
		Band band = null;
		try {
			
			con = eventDataSource.getConnection();
			java.sql.Statement stm = con.createStatement();
			String query = "select * from Band where ID=" + id;
			ResultSet rest = stm.executeQuery(query);
			while (rest.next()) {
				ID = rest.getInt("ID");
				UserID = rest.getInt("UserID");
				Name = rest.getString("Name");
				About = rest.getString("About");
				Mail = rest.getString("Mail");
			}
			band = new Band(ID, UserID, Name, About, Mail);
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return band;
	}

	/**
	 * this is common method for updating base
	 * 
	 * @param query
	 * @return code of error
	 */
	private int changeBase(String query) {
		Connection con = null;
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					stm.executeUpdate(query);
				} catch (Exception e) {
					return BaseErrors.UNABLE_EXECUTE;
				}
			} catch (Exception e) {
				return BaseErrors.UNABLE_CREATE_STATEMENT;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return BaseErrors.UNABLE_CONNECTION;
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return BaseErrors.CONNECTION_ClOSE_EXCEPTION;
			}
		}
		return BaseErrors.ALL_DONE;

	}
	
	/*
	 * this method gets band name and returns
	 * this band ID
	 */
	
	public int getBandID(String bandName){
		Connection connection =null;
		int  id = 0;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			String query = "select ID from Band " + 
						 " where Name='" + bandName + "';";
			
			ResultSet result = stmt.executeQuery(query);
			
	
			if(result.next())
				id = result.getInt("ID");
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
			
		}
		return id;
	}
	
	
	public int getImageID(String imageName){
		Connection  connection = null;
		int  id = 0;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			
			String query = "select ID from Band_Image " + 
						 " where Name='" + imageName + "';";
			
			ResultSet result = stmt.executeQuery(query);
			
			
			if(result.next())
				id = result.getInt("ID");
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return id;
			}
		}
		return id;
	}

	/**
	 * adds Band into database, return true if adding complete succesfully else
	 * returns false;
	 * 
	 * @param userID
	 * @param Name
	 * @param About
	 * @param Mail
	 * @return code of error
	 */
	public int addBand(int UserID, String Name, String About, String Mail) {
		String query = "insert into Band(UserID,Name,About,Mail) " + "values("
				+ UserID + ",'" + Name + "','" + About + "','" + Mail + "')";
		// String query1 = "isert into Band_Profile_Image(BandID,"
		return changeBase(query);
	}

	/**
	 * updates bands information
	 * 
	 * @param bandID
	 * @param Name
	 * @param About
	 * @param Mail
	 * @return code of error
	 */
	public int updateBandInfo(int BandID, String Name, String About, String Mail) {
		String query = "update Band SET Name='" + Name + "',About='" + About
				+ "',Mail='" + Mail + "' WHERE ID=" + BandID;
		return changeBase(query);
	}

	/**
	 * when band adds music
	 * 
	 * @param BandID
	 * @param Name
	 * @return code of error
	 */
	public int addMusic(int BandID, String Name) {
		String query = "insert into Music(BandID,Name) " + "values(" + BandID
				+ ",'" + Name + "')";
		return changeBase(query);
	}

	/**
	 * when band adds image
	 * 
	 * @param BandID
	 * @param Name
	 * @return code of error
	 */
	public int addImage(int BandID, String Name) {
		String query = "insert into Band_Image(BandID,Name) " + "values("
				+ BandID + ",'" + Name + "')";
		return changeBase(query);
	}

	/**
	 * when band adds videos
	 * 
	 * @param BandID
	 * @param Name
	 * @return code of error
	 */
	public int addVideo(int BandID, String Name) {
		System.out.println("movidaaaa ");
		String query = "insert into Video(BandID,Name) " + "values(" + BandID
				+ ",'" + Name + "')";
		return changeBase(query);
	}
	

	public int addProfileImage(int bandID,int imageID){
		String query = "insert into Band_Profile_Image(Band_ImageID,BandID) values("+imageID + "," + bandID +");";
		return changeBase(query);
	}

	/**
	 * updates profile image
	 * 
	 * @param BandID
	 * @param Band_ImageID
	 * @return code Of error
	 */
	public int updateProfileImage(int BandID, int Band_ImageID) {
		String query = "UPDATE Band_Profile_Image SET Band_ImageID="
				+ Band_ImageID + " where BandID=" + BandID;
		return changeBase(query);
	}
	/**
	 * saerto metodi sami ramis wamosagebad
	 * @param query
	 * @return
	 */
	private ArrayList<String> getImagesAndVideosAndMusics(String query) {
		ArrayList<String> ans = new ArrayList<>();
		Connection con =null;
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					ResultSet res = stm.executeQuery(query);
					while (res.next()) {
						String name = res.getString("Name");
						ans.add(name);
					}
				} catch (Exception e) {
					System.err.println("eror code " + BaseErrors.UNABLE_EXECUTE);
					return null;
				}
			} catch (SQLException e) {
				System.err.println("eror code "
						+ BaseErrors.UNABLE_CREATE_STATEMENT);
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("eror code " + BaseErrors.UNABLE_CONNECTION);
			return null;
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ans;
	}

	/**
	 * 
	 * @param BandID
	 * @param pageNumber
	 * @return lists of video names
	 */
	public ArrayList<String> getVideos(int BandID, int pageNumber) {
		String query = "select * from Video where BandID=" + BandID + " limit "
				+ (pageNumber - 1)* ConstantValues.NUMBER_OF_VIDEOS_PER_PAGE_FOR_BAND_PROFILE+ ","+ ConstantValues.NUMBER_OF_VIDEOS_PER_PAGE_FOR_BAND_PROFILE;
		return getImagesAndVideosAndMusics(query);
	}

	/**
	 * 
	 * @param BandID
	 * @param pageNumber
	 * @return
	 */
	public ArrayList<String> getImages(int BandID, int pageNumber) {
		String query = "select * from Band_Image where BandID=" + BandID
				+ " limit " + (pageNumber - 1)* ConstantValues.NUMBER_OF_IMAGES_PER_PAGE_FOR_BAND_PROFILE+ ","
				+ ConstantValues.NUMBER_OF_IMAGES_PER_PAGE_FOR_BAND_PROFILE;
		return getImagesAndVideosAndMusics(query);
	}
	/**
	 * 
	 * @param BandID
	 * @param pageNumber
	 * @return list of musics for one page
	 */
	public ArrayList<String> getMusics(int BandID, int pageNumber)
	{
		String query = "select * from Music where BandID=" + BandID
				+ " limit " + (pageNumber - 1)
				* ConstantValues.NUMBER_OF_MUSICS_PER_PAGE_FOR_BAND_PROFILE+ ","
				+ ConstantValues.NUMBER_OF_MUSICS_PER_PAGE_FOR_BAND_PROFILE;
		return getImagesAndVideosAndMusics(query);
	}
	

	/**
	 * 
	 * @param BandID
	 * @return profile image name of Band
	 */
	public String getProfileImage(int BandID) {
		String ans = "default.jpg";
		String query = "select Name from Band_Image,Band_Profile_Image where "
				+ "Band_Profile_Image.Band_ImageID=Band_Image.ID and Band_Image.BandID="
				+ BandID;
		Connection con = null;
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				try {
					ResultSet res = stm.executeQuery(query);
					while (res.next()) {
						ans = res.getString("Name");
					}
				} catch (Exception e) {
					System.err.println("eror code " + BaseErrors.UNABLE_EXECUTE);
					return ans;
				}
			} catch (SQLException e) {
				System.err.println("eror code "
						+ BaseErrors.UNABLE_CREATE_STATEMENT);
				return ans;
			}
		} catch (SQLException e) {
			System.err.println("eror code " + BaseErrors.UNABLE_CONNECTION);
			return ans;
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ans;
	}
	/**
	 * abrunebs top bendebs
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getTopBands(int num){
		String query = "select ID,bandAverageRating(ID) from Band"
				+ " order by bandAverageRating(ID) desc limit " + num + ";";
		Connection connection = null;
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				list.add(result.getInt("ID"));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * amatebs raitingebs
	 * @param userID
	 * @param bandID
	 * @param eventID
	 * @param score
	 * @return
	 * @throws SQLException
	 */
	public int addRating(int userID, int bandID, int eventID, int score) throws SQLException{
		if(hasAlreadyRated(userID, eventID, bandID)) return BaseErrors.USER_ALREADY_RATED_BAND;
		String query = "insert into Band_Rating(UserID,BandID,EventID,Rating) values"
				+ "(" + userID + "," + bandID + "," + eventID + "," + score +");";
		System.out.println(query);
		return changeBase(query);
		
	}

	/*
	 * this method checks whether this user has already rated
	 * this band on this specified event
	 */
	
	/**
	 * 
	 * @param userID
	 * @param eventID
	 * @param bandID
	 * @return
	 * @throws SQLException
	 */
	public boolean hasAlreadyRated(int userID,int eventID,int bandID) throws SQLException{
		Connection connection =null; 
		try {
			connection= eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			String query = "select ID from Band_Rating where BandID="
					+ bandID + " and UserID=" + userID + " and EventID=" + eventID + ";";
			
			System.out.println(query);
			ResultSet result = stmt.executeQuery(query);
			if (result.next()){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			connection.close();
		}
		return false;	
	}
	
	/*
	 * this method gives us rating for specified Band
	 */
	/**
	 * 
	 * @param bandID
	 * @return' s rating of band
	 * @throws SQLException
	 */
	public double getRating(int bandID){
		double ans = 0.0;
		Connection connection = null;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			String query = "select avg(Rating)" + "from Band_Rating where BandID="
					+ bandID + ";";

			ResultSet result = stmt.executeQuery(query);
			if (result.next())
				ans = result.getDouble("avg(Rating)");
		} catch (Exception e) {
			// TODO: handle exception
		} 
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ans;
	}
	/**
	 * raodenobis dasatvleli metodi
	 * romelsac iyenebs sxva public metodebi
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	private int countNUmber(String query)
	{
		int ans = 0;
		Connection connection = null;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			if(result.next()) ans = result.getInt("count(ID)");
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ans;
	}
	/**
	 * gasatesti counts Bands Image number
	 * 
	 * @param bandID
	 * @return
	 * @throws SQLException
	 */
	public int getImagesNumberForBand(int bandID) throws SQLException {
		String query = "select count(ID) from Band_Image where " + "BandID="
				+ bandID + ";";
		return countNUmber(query);
	}
	/**
	 * gastatesti
	 * itvlis vidoebis raodenobas bendistvis
	 * @param bandID
	 * @return number of videos for band
	 * @throws SQLException
	 */
	public int getVideosNumberForBand(int bandID) throws SQLException {
		String query = "select count(ID) from Video where " + "BandID="
				+ bandID + ";";
		return countNUmber(query);

	}
	/**
	 * 
	 * @param bandID
	 * @return number of music for band 
	 * @throws SQLException
	 */
	public int getMusicsNumberForBand(int bandID) throws SQLException
	{
		String query = "select count(ID) from Music where " + "BandID="
				+ bandID + ";";
		return countNUmber(query);
	}
	/**
	 * 
	 * @param BandID
	 * @return list of fans, who has this band in their whishlist
	 * @throws SQLException 
	 */
	public ArrayList<Integer> getFansArrayForBand(int BandID) throws SQLException
	{
		ArrayList<Integer> answ = new ArrayList<>();
		String query = "select UserID from User_Band_Wishlist where BandID="+ BandID;
		Connection connection = null;
		try {
			connection= eventDataSource.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {
				answ.add(result.getInt("UserID"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(connection !=null)
			connection.close();			
		}
		return answ;		
	}
	/**
	 * deletes image from images
	 * @param ImageName
	 * @return
	 */
	public int deleteImage(String ImageName)
	{
		String  query ="DELETE from Band_Image where Name='"+ImageName+"'";
		return changeBase(query);
	}
	/**
	 * deletes music 
	 * @param MusicName
	 * @return constant
	 */
	public int deleteMusic(String MusicName)
	{
		String query = "DELETE from Music where Name='"+MusicName+"'";
		return changeBase(query);
	}
	/**
	 * deletes video from base
	 * @param VideoName
	 * @return constant
	 */
	public int deleteVideo(String VideoName)
	{
		String query = "DELETE from Video where Name='"+VideoName+"'";
		return changeBase(query);	
	}
	
	public int getAuthorID(int bandID) throws SQLException{
		String query = "select UserID from Place where ID=" + bandID + ";";
		
		Connection connection = null;
		int userID = 0;
		try {
			connection = eventDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);			 
			if(result.next()) userID = result.getInt("UserID");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(connection != null)
				connection.close();
		}
		
		return userID;
	}

}
