package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created 12/27/15
 * Software Development
 * TSA Conference, 2016
 * ServerClientHandler: Class containing code that interacts with the client
 */
public class ServerClientHandler extends Thread {
    private int ID;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Object[] fill = new Object[1];

    public ServerClientHandler(Socket socket, int id) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        out = new ObjectOutputStream(outputStream);
        ID = id;

        InputStream inputStream = socket.getInputStream();
        in = new ObjectInputStream(inputStream);
    }

    public void run() {
        try {
            int secCount = 0;
            System.out.println("New client connected!");
            ServerValues.clientsData.add(fill);

            while (true) {
                if (secCount < ServerValues.secCount) {
                    secCount = ServerValues.secCount;
                    System.out.println(secCount);
                    out.writeObject(ServerValues.serverData); //Writes The Stock Data To The Client
                    out.reset(); //Resets the Output Stream to clear to markData inside

                    HashMap<String, Object> userData = (HashMap<String, Object>) in.readObject();
                    Object[] data = {userData.get("Name"), userData.get("Trades"), userData.get("Assets")};
                    ServerValues.clientsData.set(ID, data);

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
