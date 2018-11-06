package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import messageuserbean.UserBean;

/**
 *
 * @author Markel
 * @author Julen
 */

public class IDataImplementation implements IData {

	private Connection con;
	private PreparedStatement stmt;
	private String dbHost = ResourceBundle.getBundle("config.config").getString("IP");
	private String dbName = ResourceBundle.getBundle("config.config").getString("dbName");
	private String dbUser = ResourceBundle.getBundle("config.config").getString("dbUser");
	private String dbPassword = ResourceBundle.getBundle("config.config").getString("dbPassword");

	private void connect()
			throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("Abriendo Conexion.");
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://" + dbHost + "/" + dbName;
		con = DriverManager.getConnection(url, dbUser, dbPassword);
		System.out.println("Conexion Abierta.");
	}

	private void disconnect() throws SQLException {
		System.out.println("Cerrando Conexion.");
		if (con != null)
			con.close();
		System.out.println("Conexion Cerrada.");
	}

	@Override
	public synchronized void userSignUp(UserBean user) throws UserLoginExistException, SQLException {
		ResultSet rs = null;
		try {
			connect();
			
			// -------------------- Comprobar la existencia del usuario --------------------
			String selectUserExist = "select * from usuarios where login=?";
			stmt = con.prepareStatement(selectUserExist);
			stmt.setString(1, user.getLogin());		
			rs=stmt.executeQuery();
			// -------------------- Comprobar la existencia del usuario --------------------
			
			
			// -------------------- si existe el usuario llamar a la excepcion --------------------
			if(rs.next()) {
				throw new UserLoginExistException();
			// -------------------- si existe el usuario llamar a la excepcion --------------------
			
			
			
			// -------------------- si no existe el usuario introducir los datos que se le pasan en la variable user --------------------
			}else {
				String insert = "insert into usuarios(login, email, fullname, password) values(?, ?, ?, ?)";
				stmt = con.prepareStatement(insert);
				stmt.setString(1, user.getLogin());
				stmt.setString(2, user.getEmail());
				stmt.setString(3, user.getFullname());
				stmt.setString(4, user.getPassword());
				stmt.executeUpdate();
			}
			// -------------------- si no existe el usuario introducir los datos que se le pasan en la variable user --------------------

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			stmt.close();
			disconnect();
		}

	}

	@Override

	public synchronized UserBean userLogin(UserBean user)
			throws UserNotExistException, PasswordNotOkException, SQLException {
		ResultSet rs = null,rs2 = null;

		UserBean usuario = new UserBean();

		try {
			// -------------------- Comprobar la existencia del usuario --------------------
			connect();
			String selectComprobarExistencia = "select * from usuarios where login = ?";
			stmt = con.prepareStatement(selectComprobarExistencia);
			stmt.setString(1, user.getLogin());
			rs = stmt.executeQuery();
			// -------------------- Comprobar la existencia del usuario --------------------
			
			
			
			
			// -------------------- Si existe el usuario Comprobar la Contraseña --------------------
			if (rs.next()) {
				String selectInicioSesion = "select * from usuarios where login = ? and password=? ";
				stmt = con.prepareStatement(selectInicioSesion);
				stmt.setString(1, user.getLogin());
				stmt.setString(2, user.getPassword());

				rs2 = stmt.executeQuery();
			// -------------------- Si existe el usuario Comprobar la Contraseña --------------------
			
				
				
				
			// -------------------- Si no existe el usuario llamar a la excepcion --------------------
			}else {
				throw new UserNotExistException();
			}
			// -------------------- Si no existe el usuario llamar a la excepcion --------------------
			
			
			
			
			// -------------------- Si la Contraseña es correcta almacenar los datos en una variable user --------------------
			if (rs2.next()) {
				usuario.setId(rs2.getInt(1));
				usuario.setLogin(rs2.getString(2));
				usuario.setEmail(rs2.getString(3));
				usuario.setFullname(rs2.getString(4));
				usuario.setPassword(rs2.getString(7));
				usuario.setLastPasswordChange(rs2.getTimestamp(8));
				usuario.setLastAccess(rs2.getTimestamp(9));
			// -------------------- Si la Contraseña es correcta almacenar los datos en una variable user --------------------
				
				
				
				
			// -------------------- Si la Contraseña no es correcta llamar a la excepcion --------------------
			}else {
				throw new PasswordNotOkException();
			}
			// -------------------- Si la Contraseña no es correcta llamar a la excepcion --------------------
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			stmt.close();
			disconnect();
		}

		return usuario;
	}
}