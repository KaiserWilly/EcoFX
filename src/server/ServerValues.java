package server;

import rsc.StockHistory;
import server.engine.EcoEngine;

import java.util.ArrayList;
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
        server.engine.EcoEngine.genereateData();
        HashMap<String, Object> serverD = EcoEngine.getData();
        StockHistory.addHistory(serverD);

        HashMap<String, Object> data = new HashMap<>();
        data.put("SEC", secCount);
        data.put("Market Data", serverD);
        data.put("Usernames", genUsernames());
        data.put("User Data", new HashMap<String, Object>());

        serverData = data;

    }

    public static ArrayList<String> genUsernames() {
        try {
            usernames.clear();
                for (Object[] data: clientsData) {
                    usernames.add(data[0].toString());
                }
            return usernames;
        } catch (Exception e) {return usernames;}
    }
}
