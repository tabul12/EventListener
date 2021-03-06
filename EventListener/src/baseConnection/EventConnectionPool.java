package baseConnection;

import static org.junit.Assert.assertEquals;

import java.sql.*;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import objects.Band; 
public class EventConnectionPool {
	private String driver = EventDBInfo.MYSQL_DRIVER;
	private String url = EventDBInfo.MYSQL_DATABASE_SERVER;
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
	public static void main(String[] args) throws SQLException {

	}
}
