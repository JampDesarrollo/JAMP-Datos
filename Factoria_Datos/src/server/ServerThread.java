package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.IData;
import logic.PasswordNotOkException;
import logic.UserLoginExistException;
import logic.UserNotExistException;
import messageuserbean.Message;
import messageuserbean.UserBean;

/**
 * Thread class for treating individual requests of the client. Extends from the
 * class Thread.
 * 
 * @author Ander, Paula
 */
public class ServerThread extends Thread {
	/**
	 * Message with a code and a user
	 */
	private Message message;
	/**
	 * Socket of the client
	 */
	private Socket socket;
	/**
	 * Class that implements IData interface
	 */
	private IData iData;
	/**
	 * User object
	 */
	private UserBean user;
	/**
	 * Logger of the class
	 */
	private static final Logger LOGGER = Logger.getLogger("server");

	/**
	 * Server Thread constructor that receives the socket and the
	 * IdataImplementation
	 * 
	 * @param socket Socket of the client
	 * @param iData  IDataImplementation class
	 */
	public ServerThread(Socket socket, IData iData) {
		this.socket = socket;
		this.iData = iData;
	}

	/**
	 * Run method of the thread.
	 */
	public void run() {

		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		UserBean receivedBean = null;

		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			LOGGER.info("Dentro de thread, leyendo del socket");
			message = (Message) input.readObject();
			int code = message.getCode();
			receivedBean = (UserBean) message.getUser();

			switch (code) {
			case 1:
				iData.userSignUp(receivedBean);
				message = new Message(1, null);
				try {
					output.writeObject(message);
				} catch (Exception e1) {
					message = new Message(-1, null);
					try {
						output.writeObject(message);
					} catch (IOException e2) {
						LOGGER.log(Level.SEVERE, "IOException on SignUp Thread: " + e2);
					}
				}

				break;
			case 2:
				user = iData.userLogin(receivedBean);
				message = new Message(2, user);
				try {
					output.writeObject(message);
				} catch (Exception e1) {
					message = new Message(-2, null);
					try {
						output.writeObject(message);
					} catch (IOException e2) {
						LOGGER.log(Level.SEVERE, "IOException on Login Thread: " + e2);
					}
				}
				break;
			}

		} catch (UserLoginExistException e) {
			LOGGER.log(Level.SEVERE, "Ya existe un usuario con ese Login. " + e);
			message = new Message(11, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				LOGGER.log(Level.SEVERE, "IOException on Thread UserLoginExistException writing: " + e1);
			}
		} catch (UserNotExistException e) {
			LOGGER.log(Level.SEVERE, "No existe un usuario con ese Login. " + e);
			message = new Message(22, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				LOGGER.log(Level.SEVERE, "IOException on Thread UserNotExistException writing: " + e1);
			}
		} catch (PasswordNotOkException e) {
			LOGGER.log(Level.SEVERE, "Contraseña incorrecta para ese Login. " + e);
			message = new Message(21, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				LOGGER.log(Level.SEVERE, "IOException on Thread PasswordNotOkException writing: " + e1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error SQLException. " + e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "No se ha podido abrir la ventana. " + e);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Clase no encontrada. " + e);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error. " + e);
			message = new Message(-1, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				LOGGER.log(Level.SEVERE, "IOException on Thread Exception writing: " + e1);
			}
		}finally {
			try {
				this.interrupt();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
