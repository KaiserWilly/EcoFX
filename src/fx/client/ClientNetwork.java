package fx.client;

import rsc.PlayerManagement;
import rsc.StockHistory;
import rsc.StockManagement;
import rsc.Values;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
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
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            System.out.println("Permanent Connection Made!");

            while (true) {
                HashMap<String, Object> serverData = (HashMap<String, Object>) in.readObject();
                StockHistory.addHistory((HashMap<String, Object>) serverData.get("Market Data"));
                StockManagement.checkOrders();
                Values.secCount = (int) serverData.get("SEC");
                out.writeObject(getUserData());
                ArrayList<Object[]> clientData = (ArrayList<Object[]>) serverData.get("Leaderboard");
                ArrayList<Object[]> leaderboardData = leaderBoardSort(clientData);
            }
        } catch (Exception e) {
            System.out.println("Failed to Connect!");
        }
    }

    static ArrayList<Object[]> leaderBoardSort(ArrayList<Object[]> data) {
        try {
            Collections.sort(data, (o1, o2) -> {
                Double assetValue1 = (Double) o1[2];
                Double assetValue2 = (Double) o2[2];
                return assetValue2.compareTo(assetValue1);
            });
        } catch (Exception e) {
        }

        return data;
    }

    static HashMap<String, Object> getUserData() {
        HashMap<String, Object> playerData = new HashMap<>();
        playerData.put("Name", PlayerManagement.name);
        playerData.put("Trades", 255);
        playerData.put("Assets", PlayerManagement.getAssetWorth());
        return playerData;
    }
}
