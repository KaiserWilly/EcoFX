package fx.client;

import rsc.StockHistory;
import rsc.StockManagement;
import rsc.Values;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by james on 4/30/2016.
 */

public class ClientNetwork {

    static void connentToServer(String ip) {
        try {
            System.out.println("Entered Client/Server Class");
            Socket clientSocket = new Socket(ip, 1180);
            InputStream is = clientSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);
            System.out.println("Permanent Connection Made!");

            while (true) {
                HashMap<String, Object> serverData = (HashMap<String, Object>) in.readObject();
                StockHistory.addHistory((HashMap<String, Object>) serverData.get("Market Data"));
                StockManagement.checkOrders();
                Values.secCount = (int) serverData.get("SEC");
                System.out.println(Values.secCount);
            }
        } catch (Exception e) {
            System.out.println("Failed to Connect!");
        }
    }

    static HashMap<String, Object> getUserData() {
        HashMap<String, Object> playerData = new HashMap<>();
        playerData.put("Name", "The Kaiser");
        playerData.put("Trades", 255);
        playerData.put("Assets", 135750);
        return playerData;
    }
}
