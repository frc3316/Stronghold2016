
/**
 * Created by Leon on 12.1.2016.
 */
import java.io.*;
import java.net.*;

class JavaServer {
	public static void main(String args[]) throws Exception {

		// A socket listening on port 8080 for connection.
		ServerSocket server = new ServerSocket(8080);
		System.out.println("wait for connection on port 8080");
		// The socket to the python side.
		Networker worker = new Networker();
		new Thread(worker).start();
		
		while (true) {
			Socket client = server.accept();
			worker.change(client);
			System.out.println("got connection on port 8080");
		}
	}
}