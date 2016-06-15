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
            Socket clientSocket = new Socket(ip, 1180);
            InputStream is = clientSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            System.out.println("Permanent Connection Made!");

            while (true) {
                Object rawServerData = in.readObject();
                HashMap<String, Object> serverData = (HashMap<String, Object>) rawServerData;
                StockHistory.addHistory((HashMap<String, Object>) serverData.get("Market Data"));
                System.out.println(StockHistory.getPrice("Composite"));
                StockManagement.checkOrders();

                out.writeObject(getUserData());
                out.flush();
                ArrayList<Object[]> leaderData = (ArrayList<Object[]>) serverData.get("Leaderboard");
                if (leaderData != null) {
                    PlayerManagement.leaderboardData = leaderBoardSort(leaderData);
                }
                Values.secCount = (Integer) serverData.get("SEC");
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        playerData.put("Trades", PlayerManagement.trades);
        playerData.put("Assets", PlayerManagement.getAssetWorth());
        return playerData;
    }
}
