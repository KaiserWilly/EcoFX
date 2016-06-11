package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created 12/27/15
 * Software Development
 * TSA Conference, 2016
 * ServerClientHandler: Class containing code that interacts with the client
 */
public class ServerClientHandler extends Thread {
    private int ID;
    private ObjectOutputStream out;

    public ServerClientHandler(Socket socket, int id) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        out = new ObjectOutputStream(outputStream);
        ID = id;
    }

    public void run() {
        try {
            int secCount = 0;
            System.out.println("New client connected!");

            while (true) {
                if (secCount < ServerValues.secCount) {
                    secCount = ServerValues.secCount;
                    System.out.println(secCount);
                    out.writeObject(ServerValues.serverData); //Writes The Stock Data To The Client
                    out.reset(); //Resets the Output Stream to clear to avgMarData inside
//                    HashMap<String, Object> userData = (HashMap<String, Object>) in.readObject(); //To be used later


                }
                Thread.sleep(50); //Sleeps the socket
            }
        } catch (SocketException e) {
            ServerFile.showTimeStamp("Socket Error on Socket ID: " + ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
