package baseConnection;

import java.sql.*;

import objects.Band;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import errors.BaseErrors;

public class BandManager {
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
	 * adds Band into database, return true if adding complete succesfully 
	 * else returns false;
	 * @param userID
	 * @param Name
	 * @param About
	 * @param Mail
	 * @return
	 */
	public int addBand(int userID,String Name,String About,String Mail)
	{
		Connection con;	
		try {
			con = eventDataSource.getConnection();
			try {
				Statement stm = con.createStatement();
				String query= "Insert into Band(UserID,Name,About,Mail) "
						+ "values("+userID+",'"+Name+"','"+About+"','"+Mail+"')";
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
}
