package logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import model.UserBean;
import server.Pool;

/**
*
* @author Julen
*/

public class IDataImplementation extends Pool implements IData  {
	
	private Connection con;
	private PreparedStatement stmt;
	private String dbHost;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	
/*
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
				dbPassword = config.getProperty("password");
			} finally {
				if (input != null)
					input.close();
			}
		}
	}

	private void connect() throws Exception {
		System.out.println("Conexion Abierta.");
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://" + dbHost + "/" + dbName;
		con = DriverManager.getConnection(url, dbUser, dbPassword);
	}

	private void disconnect() throws SQLException {
		System.out.println("Conexion Cerrada.");
		if (stmt != null)
			stmt.close();
		if (con != null)
			con.close();
		System.out.println("------------------------");
	}
	*/
	

	@Override
	public synchronized void userSignUp(UserBean user) throws UserLoginExistException, SQLException {
		try {
			con = dataSource.getConnection();
			String insert="insert into usuarios values(?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(insert);
			stmt.setString(2,user.getLogin());
			stmt.setString(3,user.getEmail());
			stmt.setString(4,user.getFullName());
			stmt.setString(7,user.getPassword());
			stmt.executeUpdate(insert);
			
		}catch(Exception e) {
			e.getMessage();
		}finally {
			con.close();
		}
		
	}

	@Override
	public synchronized UserBean userLogin(UserBean user) throws UserNotExistException, PasswordNotOkException, SQLException {
		ResultSet rs = null;
		UserBean usuario = new UserBean(); 
		try {
			con = dataSource.getConnection();
			String select="select * from usuarios where login=? and password=?";
			stmt = con.prepareStatement(select);
			stmt.setString(1,user.getLogin());
			stmt.setString(2,user.getPassword());
			
			rs=stmt.executeQuery(select);
			
			while (rs.next()) {
				UserBean auxUser=new UserBean();
				auxUser.setId(rs.getInt(1));
				auxUser.setLogin(rs.getString(2));
				auxUser.setEmail(rs.getString(3));
				auxUser.setFullName(rs.getString(4));
				//auxUser.setStatus(rs.getString(5));
				//auxUser.setPrivileges(rs.getString(6));
				auxUser.setPassword(rs.getString(7));
				//auxUser.setLastAccess(cambiarFecha(rs.getDate(8)));
				//auxUser.setLastPasswordChange(cambiarFecha(rs.getDate(9)));
			}
		}catch(Exception e) {
			e.getMessage();
		}finally {
			con.close();
		}
		
		return usuario;
	}
}