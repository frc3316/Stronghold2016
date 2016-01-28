/**
 * Created by Leon on 12.1.2016.
 */
import java.io.*;
import java.net.*;

class JavaServer {
    public static void main(String args[]) throws Exception {

        // The number of floats written in the data from the socket:
        final int NUMBER_OF_DATA_NUMBERS = 4;
        // The var holding the string received from the python side.
        String fromClient;
        // A conformation string to the python side saying data received successfully.
        String toClient = "Ok";
        // A socket listening on port 8080 for connection.
        ServerSocket server = new ServerSocket(8080);
        System.out.println("wait for connection on port 8080");
        // The socket to the python side.
        Socket client = server.accept();
        System.out.println("got connection on port 8080");

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        // Should we read the next input from the python side.
        boolean run = true;

        while (run) {
            ///////////////////////
            //// Parsing Data /////
            ///////////////////////

            // The data from the python process:
            fromClient = in.readLine();
            // An array holding the numbers the python process sent:
            Integer[] dataNumbers = new Integer[NUMBER_OF_DATA_NUMBERS]; //[Distance,Angle,Xpos,Ypos]
            // Number of commas past in from client in the for statement:
            int numOfCommasRead = 0;
            // Current Int str:
            String currentIntStr = "";

            // If data received:
            if (!fromClient.equals("null")) {

                out.println(toClient);
                for (int i = 0; i < fromClient.length(); i++){
                    char c = fromClient.charAt(i);
                    if (!(c == ',')) {
                        currentIntStr += c;
                    }
                    else if (c == ','){

                        dataNumbers[numOfCommasRead] = Integer.parseInt(currentIntStr);
                        numOfCommasRead += 1;
                        currentIntStr = "";
                    }
                    else {
                        System.out.println("Error in data parsing");
                        break;
                    }

                }
                dataNumbers[numOfCommasRead] = Integer.parseInt(currentIntStr);

            }
            else {
                System.out.println("Couldn't get data");
            }
        }
        System.exit(0);
    }
}