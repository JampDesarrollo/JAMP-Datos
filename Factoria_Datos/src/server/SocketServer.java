package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.IData;
import logic.IDataFactory;

/**
 * Socket server class for communication between socket client and server. It
 * contains start method for initialization of threads for each connection.
 * 
 * @author Ander, Markel
 */
public class SocketServer {
	/**
	 * Port from which the connection to the client socket will be done.
	 */
	private final String PORT = ResourceBundle.getBundle("config.config").getString("PORT");
	/**
	 * Maximum threads that can be created.
	 */
	private final String MAXTHREADS = ResourceBundle.getBundle("config.config").getString("MAXTHREADS");
	/**
	 * Logger of the class
	 */
	private static final Logger LOGGER = Logger.getLogger("server");

	/**
	 * Main method for the initialization of the server application.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		SocketServer s = new SocketServer();
		s.start();
	}

	/**
	 * Start method that accepts incoming requests from the client and creates a new
	 * thread for each request.
	 */
	public void start() {
		ServerSocket server = null;
		Socket socket = null;

		try {
			// Server socket creation
			server = new ServerSocket(Integer.parseInt(PORT));
			IData iData = IDataFactory.getIData();

			// Accept every request
			while (true) {
				// Create threads while less than max threads
				if (Thread.activeCount() < Integer.parseInt(MAXTHREADS)) {
					// Wait for a connection request.
					LOGGER.info("Esperando");
					socket = server.accept();
					LOGGER.info("Aceptado");
					// Each client will be treated in a thread
					ServerThread thread = new ServerThread(socket, iData);
					Thread t = new Thread(thread);
					t.start();
				}
			}

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IOExcpetion in socket server: " + e);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Excpetion in socket server: " + e);
		} finally {
			try {
				if (server != null)
					server.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IOExcpetion in socket server while closing: " + e);
			}
		}
	}

}
