package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import codes.Message;
import logic.IData;

/**
*
* @author Ander
*/
public class ServerThread  extends Thread{
	
	private Message message;
	private Socket socket;
	
	public ServerThread(Socket socket, IData iData) {
		this.socket=socket;
	}
	
	public void run() {
		
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		try {
			
			Message receivedMessage = (Message) input.readObject();
			
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
					
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
