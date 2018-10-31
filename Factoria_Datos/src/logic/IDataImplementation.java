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

/**
*
* @author Markel
* @author Julen
*/

public class IDataImplementation implements IData  {
	
	private Connection con;
	private PreparedStatement stmt;
	private String dbHost;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	

	public void DBManager() throws IOException {
		if (dbHost == null) {
			Properties config = new Properties();
			FileInputStream input = null;
			try {
				input = new FileInputStream("config.config");
				config.load(input);
				dbHost = config.getProperty("IP");
				dbName = config.getProperty("dbName");
				dbUser = config.getProperty("dbUser");
				dbPassword = config.getProperty("dbPass");
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
			stmt.setString(3,user.getFullName());
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
		ArrayList <UserBean> usuarios = new ArrayList <UserBean>();
		try {
			connect();
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
				auxUser.setPassword(rs.getString(7));
				usuarios.add(auxUser);
			}
		}catch(Exception e) {
			e.getMessage();
		}finally {
			stmt.close();
			disconnect();
		}
		
		return usuarios;
	}
}
