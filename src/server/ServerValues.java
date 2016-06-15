package server;

import rsc.StockHistory;
import server.engine.EcoEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by james on 6/10/2016.
 */
public class ServerValues {
    static HashMap<String, Object> serverData = new HashMap<>();
    static ArrayList<String> usernames = new ArrayList<>();
    public static Integer secCount = 0;

    static public ArrayList<Object[]> clientsData = new ArrayList<>();

    public static void genServerData() {
        HashMap<String, Object> data;
        server.engine.EcoEngine.genereateData();
        HashMap<String, Object> serverD = EcoEngine.getData();
        StockHistory.addHistory(serverD);
        data = new HashMap<>();
        data.put("SEC", secCount);
        data.put("Market Data", serverD);
        serverData.put("Leaderboard", filterClientData(clientsData));
        serverData.put("Usernames", genUsernames());
        data.put("User Data", new HashMap<String, Object>());
        System.out.println(Arrays.toString(StockHistory.getHistory("Composite")));

        serverData = data;
    }

    public static HashMap<String, Object> getServerData() {
        HashMap<String, Object> data = serverData;
        return data;
    }

    public static ArrayList<String> genUsernames() {
        try {
            usernames.clear();
            for (Object[] data : clientsData) {
                if (!data[0].equals("")) {
                    usernames.add(data[0].toString());
                }
            }
            return usernames;
        } catch (Exception e) {
            return usernames;
        }
    }

    public static ArrayList<Object[]> filterClientData(ArrayList<Object[]> data) {
        ArrayList<Object[]> filteredData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Object[] raw = data.get(i);
            if (!String.valueOf(raw[0]).equals("")) {
                filteredData.add(raw);
            }
        }
        return filteredData;
    }
}
