package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Created 12/27/15
 * Software Development
 * TSA Conference, 2016
 * ServerClientHandler: Class containing code that interacts with the client
 */
class ServerClientHandler extends Thread {
    private int id;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    ServerClientHandler(Socket socket, int id) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        out = new ObjectOutputStream(outputStream);
        this.id = id;

        InputStream inputStream = socket.getInputStream();
        in = new ObjectInputStream(inputStream);
    }

    public void run() {
        try {
            int secCount = 0;
            System.out.println("New client connected!");
            ServerValues.addClient();

            while (true) {
                if (secCount < ServerValues.secCount) {
                    secCount = ServerValues.secCount;
                    out.writeObject(ServerValues.getServerData()); //Writes The Stock Data To The Client
                    out.reset(); //Resets the Output Stream to clear to markData inside
                    HashMap<String, Object> userData = (HashMap<String, Object>) in.readObject();
                    Object[] data = {userData.get("Name"), userData.get("Trades"), userData.get("Assets")};
                    ServerValues.refreshClientData(data, id);
                }
                Thread.sleep(50); //Sleeps the socket
            }
        } catch (SocketException e) {
            ServerFile.showTimeStamp("Socket Error on Socket ID: " + id);
            ServerValues.refreshClientData(new Object[]{"", 0, 0}, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
