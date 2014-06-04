package baseConnection;

import java.sql.*;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import Objects.Band;

public class BandManager {
	private BasicDataSource connectionPool;
	/**
	 * constructor
	 * @param connectionPool
	 */
	public BandManager(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
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
		Connection con  = connectionPool.getConnection();
		java.sql.Statement stm = con.createStatement();
		stm.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
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
		return band;
	}
}
