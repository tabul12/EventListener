package baseConnection;

import java.sql.*;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import objects.Band;
import sun.jdbc.odbc.ee.DataSource;
import sun.org.mozilla.javascript.internal.ast.NewExpression;

public class EventConnectionPool {
	private String driver = EventDBInfo.MYSQL_DRIVER;
	private String url = "jdbc:mysql://"+EventDBInfo.MYSQL_DATABASE_SERVER;
	private String userName = EventDBInfo.MYSQL_USERNAME;
	private String password = EventDBInfo.MYSQL_PASSWORD;
	private int maxActive = 100;
	private int maxWait = 10000;
	private int maxIdle = 10;
	private static BasicDataSource eventDataSource;
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
	 * returns datasource object
	 * @return
	 */
	public BasicDataSource getEventDataSource()
	{
		return eventDataSource;
	}
	public static void main(String[] args) {
		EventConnectionPool CP;
		try {
			CP = new EventConnectionPool();
			BandManager manag = new BandManager(CP.getEventDataSource());
			Connection con1 = CP.getEventDataSource().getConnection();
			Statement stm1 = con1.createStatement();
			String update = "Insert into User(FirstName,LastName,UserName,Password,MobileNumber,Image)" 
					+ "Values('jondi','bagaturia','jondi-jondi','shalva','555555555','jondi.jpg')";
			stm1.executeUpdate(update);
			con1.close();
			Connection con2 = CP.getEventDataSource().getConnection();
			Statement stm2 = con2.createStatement();
			String select = "Select * from User";
			ResultSet res = stm2.executeQuery(select);
			while(res.next())
			{
				System.out.println(res.getString("UserName"));
			}
			manag.addBand(1,"Radiohead","Rock Group", "radiohead@gmail.com");
			Band b = manag.getBand(1);
			System.out.println(b.getName());
			System.out.println(b.getAbout());
			System.out.println(b.getMail()); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
