package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import logic.UserLoginExistException;
import model.UserBean;
/**
*
* @author Julen
*/

public class Pool {
	
	Properties propiedades = new Properties();
	propiedades.load(new FileInputStream("src/logic/propierties"));

	public DataSource dataSource;

	private String dbHost;
	private String dbName;
	private String dbUser;
	private String dbPass;
	private String dbUrl;

	public void DBManager() throws IOException {
		if (dbHost == null) {
			Properties config = new Properties();
			FileInputStream input = null;
			
			try {
				input = new FileInputStream("./dao/properties");
				config.load(input);
				dbHost = config.getProperty("ip");
				dbName = config.getProperty("dbname");
				dbUser = config.getProperty("username");
				dbPass = config.getProperty("password");
				dbUrl = "jdbc:mysql://" + dbHost + "/" + dbName;
			} finally {
				if (input != null)
					input.close();
			}
		}
	}

	public Pool() {

		inicializaDataSource();

	}

	
	private void inicializaDataSource() {
		
		//MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
		BasicDataSourceFactory basicDataSource = new BasicDataSourceFactory();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPass);
		dataSource.setUrl(dbUrl);
		dataSource.setMaxActive(5);

	}
	
}