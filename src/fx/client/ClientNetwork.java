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
 * Created 6/10/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ClientNetwork: Connects the client to the server and transfers data from the server to a form that the clients can use
 */

class ClientNetwork {

    static void connentToServer(String ip) { //Attempt to open connection with server
        try {
            Socket clientSocket = new Socket(ip, 1180);
            InputStream is = clientSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            System.out.println("Permanent Connection Made!");

            while (true) { //Communicate to server indefinitely
                Object rawServerData = in.readObject();
                HashMap<String, Object> serverData = (HashMap<String, Object>) rawServerData;
                if (((String) serverData.get("Remove")).length() > 0) {
                    StockHistory.removeStock((String) serverData.get("Remove"));
                }
                StockHistory.addHistory((HashMap<String, Object>) serverData.get("Market Data"));
                StockManagement.checkOrders();
                ArrayList<Object[]> leaderData = (ArrayList<Object[]>) serverData.get("Leaderboard");


                if (leaderData != null) {
                    PlayerManagement.leaderboardData = leaderBoardSort(leaderData);
                }
                String message = (String) serverData.get("Message");
                if (message.length() > 0) {
                    Values.messageQueue.add(message);
                }
                out.writeObject(getUserData());
                out.flush();
                Values.secCount = (Integer) serverData.get("SEC");
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Object[]> leaderBoardSort(ArrayList<Object[]> data) { //Sort incoming leaderboard data
        try {
            Collections.sort(data, (o1, o2) -> {
                Double assetValue1 = (Double) o1[2];
                Double assetValue2 = (Double) o2[2];
                return assetValue2.compareTo(assetValue1);
            });
        } catch (Exception ignored) {
        }

        return data;
    }

    private static HashMap<String, Object> getUserData() {
        HashMap<String, Object> playerData = new HashMap<>();
        playerData.put("Name", PlayerManagement.name);
        playerData.put("Trades", PlayerManagement.trades);
        playerData.put("Assets", PlayerManagement.getAssetWorth());
        return playerData;
    }
}
