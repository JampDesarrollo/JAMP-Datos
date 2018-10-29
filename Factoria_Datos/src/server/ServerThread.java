package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import javax.sound.midi.Receiver;

import codes.Message;
import logic.IData;
import logic.PasswordNotOkException;
import logic.UserLoginExistException;
import logic.UserNotExistException;
import model.UserBean;

/**
 *
 * @author Ander
 */
public class ServerThread extends Thread {

	private Message message;
	private Socket socket;
	private IData iData;

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

			message = (Message) input.readObject();
			int code = message.getCode();
			receivedBean = (UserBean) message.getUser();

			switch (code) {
			case 1:
				iData.userSignUp(receivedBean);
			case 2:
				iData.userLogin(receivedBean);
			}

		} catch (UserLoginExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
