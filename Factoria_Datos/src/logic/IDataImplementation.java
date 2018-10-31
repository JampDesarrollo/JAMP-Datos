package logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import messageuserbean.UserBean;

/**
*
* @author Markel
* @author Julen
*/

public class IDataImplementation implements IData  {
	
	private Connection con;
	private PreparedStatement stmt;
	private String dbHost = ResourceBundle.getBundle("config.config").getString("IP");
	private String dbName = ResourceBundle.getBundle("config.config").getString("dbName");
	private String dbUser = ResourceBundle.getBundle("config.config").getString("dbUser");
	private String dbPassword = ResourceBundle.getBundle("config.config").getString("dbPassword");
	


	private void connect() throws Exception {
		System.out.println("Conexion Abierta.");
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://" + dbHost + "/" + dbName;
		con = DriverManager.getConnection(url, dbUser, dbPassword);
	}

	private void disconnect() throws SQLException {
		System.out.println("Conexion Cerrada.");
		if (con != null)
			con.close();
		System.out.println("------------------------");
	}
	
	@Override
	public synchronized void userSignUp(UserBean user) throws UserLoginExistException, SQLException {
		try {
			connect();
			String insert="insert into usuarios('login','email','fullname','passsword') values(?,?,?,?)";
			stmt = con.prepareStatement(insert);
			stmt.setString(1,user.getLogin());
			stmt.setString(2,user.getEmail());
			stmt.setString(3,user.getFullname());
			stmt.setString(4,user.getPassword());
			stmt.executeUpdate(insert);
			
		}catch(Exception e) {
			e.getMessage();
		}finally {
			stmt.close();
			disconnect();
		}
		
	}

	@Override

	public synchronized UserBean userLogin(UserBean user) throws UserNotExistException, PasswordNotOkException, SQLException {
		ResultSet rs = null;

		UserBean usuario = new UserBean();

		try {
			connect();
			String select="select * from usuarios where login=? and password=?";
			stmt = con.prepareStatement(select);
			stmt.setString(1,user.getLogin());
			stmt.setString(2,user.getPassword());
			
			rs=stmt.executeQuery(select);
			
			usuario.setId(rs.getInt(1));
			usuario.setLogin(rs.getString(2));
			usuario.setEmail(rs.getString(3));
			usuario.setFullname(rs.getString(4));
			usuario.setPassword(rs.getString(7));
			
			
		}catch(Exception e) {
			e.getMessage();
		}finally {
			stmt.close();
			disconnect();
		}
		
		return usuario;
	}
}
