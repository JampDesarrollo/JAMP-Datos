package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import messageuserbean.UserBean;

/**
 * This class implements IData interface.
 * 
 * @author Markel
 * @author Julen
 * @author Ander
 * @author Paula
 */

public class IDataImplementation implements IData {
	/**
	 * Connection to the database.
	 */
	private Connection con;
	/**
	 * Statements to execute on the database.
	 */
	private PreparedStatement stmt;
	/**
	 * Databases' IP address
	 */
	private String dbHost = ResourceBundle.getBundle("config.config").getString("IP");
	/**
	 * Databases name
	 */
	private String dbName = ResourceBundle.getBundle("config.config").getString("dbName");
	/**
	 * User of the database
	 */
	private String dbUser = ResourceBundle.getBundle("config.config").getString("dbUser");
	/**
	 * Password to access the database
	 */
	private String dbPassword = ResourceBundle.getBundle("config.config").getString("dbPassword");
	/**
	 * Logger of the class
	 */
	private static final Logger LOGGER = Logger.getLogger("logic");

	/**
	 * Method that opens connection to the database.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private void connect()
			throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		LOGGER.info("Openning connection");
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://" + dbHost + "/" + dbName;
		con = DriverManager.getConnection(url, dbUser, dbPassword);
		LOGGER.info("Connection openned");
	}

	/**
	 * Method that closes connection to the database.
	 * 
	 * @throws SQLException
	 */
	private void disconnect() throws SQLException {
		LOGGER.info("Closing connection");
		if (con != null)
			con.close();
		LOGGER.info("Connection closed");
	}

	/**
	 * This method before creating a new UserBean, check if the login already
	 * exists.
	 * 
	 * @param user The UserBean object to be added.
	 * @throws UserLoginExistException If the login already exists
	 * @throws Exception               If there is any error while processing.
	 */
	@Override
	public synchronized void userSignUp(UserBean user) throws UserLoginExistException, Exception {
		ResultSet rs = null;
		try {
			connect();

			// -------------------- Comprobar la existencia del usuario --------------------
			String selectUserExist = "select * from usuarios where login=?";
			stmt = con.prepareStatement(selectUserExist);
			stmt.setString(1, user.getLogin());
			rs = stmt.executeQuery();
			// -------------------- Comprobar la existencia del usuario --------------------

			// -------------------- si existe el usuario llamar a la excepcion
			// --------------------
			if (rs.next()) {
				throw new UserLoginExistException();
				// -------------------- si existe el usuario llamar a la excepcion
				// --------------------

				// -------------------- si no existe el usuario introducir los datos que se le
				// pasan en la variable user --------------------
			} else {
				String insert = "insert into usuarios(login, email, fullname, password) values(?, ?, ?, ?)";
				stmt = con.prepareStatement(insert);
				stmt.setString(1, user.getLogin());
				stmt.setString(2, user.getEmail());
				stmt.setString(3, user.getFullname());
				stmt.setString(4, user.getPassword());
				stmt.executeUpdate();
			}
			// -------------------- si no existe el usuario introducir los datos que se le
			// pasan en la variable user --------------------

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQLException: " + e);
		} catch (InstantiationException e) {
			LOGGER.log(Level.SEVERE, "InstantiationException: " + e);
		} catch (IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, "IllegalAccessException: " + e);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "ClassNotFoundException: " + e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IOException: " + e);
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			disconnect();
		}

	}

	/**
	 * This method getting the UserBean, checks if the login exists and then if the
	 * password for that login is correct.
	 * 
	 * @return All info of the UserBean
	 * @param user The UserBean object to check
	 * @throws UserNotExistException  If the login doesn't exist
	 * @throws PasswordNotOkException If the password for the login isn't correct
	 * @throws Exception              If there is any error while processing.
	 */
	@Override
	public synchronized UserBean userLogin(UserBean user)
			throws UserNotExistException, PasswordNotOkException, Exception {
		ResultSet rs = null, rs2 = null;

		UserBean usuario = new UserBean();

		try {
			// -------------------- Comprobar la existencia del usuario --------------------
			connect();
			String selectComprobarExistencia = "select * from usuarios where login = ?";
			stmt = con.prepareStatement(selectComprobarExistencia);
			stmt.setString(1, user.getLogin());
			rs = stmt.executeQuery();
			// -------------------- Comprobar la existencia del usuario --------------------

			// -------------------- Si existe el usuario Comprobar la Contraseña
			// --------------------
			if (rs.next()) {
				String selectInicioSesion = "select * from usuarios where login = ? and password=? ";
				stmt = con.prepareStatement(selectInicioSesion);
				stmt.setString(1, user.getLogin());
				stmt.setString(2, user.getPassword());

				rs2 = stmt.executeQuery();
				// -------------------- Si existe el usuario Comprobar la Contraseña
				// --------------------

				// -------------------- Si no existe el usuario llamar a la excepcion
				// --------------------
			} else {
				throw new UserNotExistException();
			}
			// -------------------- Como las comprobaciones correctas, actualizamos fecha de
			// login
			// --------------------
			Timestamp last = new Timestamp(System.currentTimeMillis());
			String updateLastAcces = "update usuarios set lastAccess = '" + last + "' where login = ?";
			stmt = con.prepareStatement(updateLastAcces);
			stmt.setString(1, user.getLogin());

			stmt.executeUpdate();

			// -------------------- Si la Contraseña es correcta almacenar los datos en una
			// variable user --------------------
			if (rs2.next()) {
				usuario.setId(rs2.getInt(1));
				usuario.setLogin(rs2.getString(2));
				usuario.setEmail(rs2.getString(3));
				usuario.setFullname(rs2.getString(4));
				usuario.setPassword(rs2.getString(7));
				usuario.setLastAccess(rs2.getTimestamp(8));
				usuario.setLastPasswordChange(rs2.getTimestamp(9));

				// -------------------- Si la Contraseña es correcta almacenar los datos en una
				// variable user --------------------

				// -------------------- Si la Contraseña no es correcta llamar a la excepcion
				// --------------------
			} else {
				throw new PasswordNotOkException();
			}
			// -------------------- Si la Contraseña no es correcta llamar a la excepcion
			// --------------------
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQLException: " + e);
		} catch (InstantiationException e) {
			LOGGER.log(Level.SEVERE, "InstantiationException: " + e);
		} catch (IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, "IllegalAccessException: " + e);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "ClassNotFoundException: " + e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IOException: " + e);
		} finally {
			if (rs != null)
				rs.close();
			if (rs2 != null)
				rs2.close();
			if (stmt != null)
				stmt.close();
			disconnect();
		}

		return usuario;
	}
}