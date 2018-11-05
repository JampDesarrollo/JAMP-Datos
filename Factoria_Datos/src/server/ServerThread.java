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
 *
 * @author Ander
 */
public class ServerThread extends Thread {

	private Message message;
	private Socket socket;
	private IData iData;
	private UserBean user;
	private static final Logger LOGGER = Logger.getLogger("logic");

	public ServerThread(Socket socket, IData iData) {
		this.socket = socket;
		this.iData = iData;
	}

	public void run() {

		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		UserBean receivedBean = null;

		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Dentro de tread, leyendo del socket");
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			}

		} catch (UserLoginExistException e) {
			LOGGER.log(Level.INFO, "{0} Ya existe un usuario con ese Login. \n ", e.getMessage());
			message = new Message(11, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (UserNotExistException e) {
			LOGGER.log(Level.INFO, "{0} No existe un usuario con ese Login. \n ", e.getMessage());
			message = new Message(22, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (PasswordNotOkException e) {
			LOGGER.log(Level.INFO, "{0} Contraseña incorrecta para ese Login. \n ", e.getMessage());
			message = new Message(21, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "{0} Error SQL. \n ", e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "{0} No se ha podido abrir la ventana. \n ", e.getMessage());
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.INFO, "{0} Clase no encontrada. \n ", e.getMessage());
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "{0} Error. \n ", e.getMessage());
			message = new Message(-1, null);
			try {
				output.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
