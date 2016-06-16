package server;

import rsc.StockHistory;
import server.engine.EcoEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by james on 6/10/2016.
 */
public class ServerValues {
    public static ArrayList<String> playerList = new ArrayList<>();
    private static HashMap<String, Object> serverData = new HashMap<>();
    public static Integer secCount = 0;
    public static ArrayList<Object[]> clientsData = new ArrayList<>();

    public static void genServerData() {
        HashMap<String, Object> data;
        server.engine.EcoEngine.genereateData();
        HashMap<String, Object> serverD = EcoEngine.getData();
        StockHistory.addHistory(serverD);
        data = new HashMap<>();
        data.put("SEC", secCount);
        data.put("Market Data", serverD);
        data.put("Leaderboard", filterClientData(clientsData));
        playerList = genUsernames(filterClientData(clientsData));
        serverData = data;
    }

    public static HashMap<String, Object> getServerData() {
        return serverData;
    }

    public static ArrayList<String> genUsernames(ArrayList<Object[]> data) {
        ArrayList<String> uN = new ArrayList<>();
        for (Object[] raw : data) {
            uN.add((String) raw[0]);
        }
        Collections.sort(uN, String.CASE_INSENSITIVE_ORDER);
        return uN;
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

    public static void addClient() {
        clientsData.add(new Object[]{"", 0, 0});
    }

    public static void refreshClientData(Object[] data, int ID) {
        ServerValues.clientsData.set(ID, data);
    }
}
