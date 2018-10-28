package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import logic.UserLoginExistException;
import model.UserBean;

/**
*
* @author Julen
*/

public class Pool {

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
		

		BasicDataSource basicDataSource = new BasicDataSource();

		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUsername(dbUser);
		basicDataSource.setPassword(dbPass);
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setMaxActive(5);

		dataSource = basicDataSource;

	}
	
}