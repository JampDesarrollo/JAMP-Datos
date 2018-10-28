package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;

import logic.IData;
import logic.IDataFactory;


/**
*
* @author Ander
*/
public class SocketServer {

	private final String PORT = ResourceBundle.getBundle("config.config").getString("PORT");
	private final String MAXTHREADS = ResourceBundle.getBundle("config.config").getString("MAXTHREADS");

	public static void main(String[] args) {
		SocketServer s = new SocketServer();
		s.start();
	}

	public void start() {
		ServerSocket server = null;
		Socket socket = null;
		
		try {
			//Server socket creation
			server = new ServerSocket(Integer.parseInt(PORT));
			IData iData = IDataFactory.getIData();
			
			
			//Create threads while less than max threads
			while (true) {
				
				//Thread.activeCount() < Integer.parseInt(MAXTHREADS)
				// Wait for a connection request.
				socket = server.accept();
				//each client will be treated in a thread
				ServerThread thread = new ServerThread(socket, iData);
				thread.start();
			}
			
		// System.out.println(p.toString());

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			try {
				if (server != null)
					server.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

}
