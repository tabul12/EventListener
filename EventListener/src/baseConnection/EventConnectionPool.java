package baseConnection;

import java.sql.*;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class EventConnectionPool {
	private String driver = EventDBInfo.MYSQL_DRIVER;
	private String url = "jdbc:mysql://"+EventDBInfo.MYSQL_DATABASE_SERVER;
	private String userName = EventDBInfo.MYSQL_USERNAME;
	private String password = EventDBInfo.MYSQL_PASSWORD;
	private int maxActive = 100;
	private int maxWait = 10000;
	private int maxIdle = 10;
	private BasicDataSource eventDataSource;
	/**
	 * constructor of connection pool
	 * @throws SQLException
	 */
	public EventConnectionPool() throws SQLException{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setMaxIdle(maxIdle);
		eventDataSource = dataSource;
	}
	/**
	 * return connection
	 */
	public Connection getConnection()
	{
		Connection ret=null;
		try {
			ret = eventDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection Requisition*** Could not connect to the database msg :" + e.getMessage());		
		}
		return ret;
	}
	public static void main(String[] args) {
		EventConnectionPool CP;
		try {
			CP = new EventConnectionPool();
			Connection con1 = CP.getConnection();
			
			Statement stm1 = con1.createStatement();
			
			stm1.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
			String update = "Insert into User(UserName,UserLastName,UserUserName,UserPassword,UserMobileNumber,UserPhoto)" 
					+ "Values('jondi','bagaturia','jondi-jondi','shalva','555555555','jondi.jpg')";
			stm1.executeUpdate(update);
			con1.close();
			Connection con2 = CP.getConnection();
			Statement stm2 = con2.createStatement();
			stm2.executeQuery("USE "+EventDBInfo.MYSQL_DATABASE_NAME);
			String select = "Select * from User";
			ResultSet res = stm2.executeQuery(select);
			while(res.next())
			{
				System.out.println(res.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
