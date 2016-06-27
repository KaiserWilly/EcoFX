package server;

import rsc.StockHistory;
import rsc.StockManagement;
import rsc.Values;
import server.engine.EcoEngine;

import java.util.*;

/**
 * Created 6/10/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ServerValues: Obtains the data from the engine that gets pushed out to the server
 */
public class ServerValues {
    public static ArrayList<String> playerList = new ArrayList<>();
    private static HashMap<String, Object> serverData = new HashMap<>();
    public static Integer secCount = 0;
    public static ArrayList<Object[]> clientsData = new ArrayList<>();
    public static int stockEventID;
    public static String stockEventsAffect;

    public static void genServerData() { //Generates new engine data
        HashMap<String, Object> data;
        server.engine.EcoEngine.genereateData();
        HashMap<String, Object> serverD = EcoEngine.getData();
        StockHistory.addHistory(serverD);

        data = new HashMap<>();
        data.put("SEC", secCount);
        data.put("Market Data", serverD);
        data.put("Leaderboard", filterClientData(clientsData));
        if (secCount % 24 == 0) {
            data.put("Message", messageExecution(server.engine.EcoEngine.stockEvents(secCount)));  //Put the message to be broadcast so that it replaces the empty string
        }
        else{
            data.put("Message", serverData.get("Message").toString());
        }
        data.put("Remove", "");
        if (stockEventID == 1) {
            data.replace("Remove", stockEventsAffect); //Put names of stocks that are being removed
        }

        playerList = genUsernames(filterClientData(clientsData));

        serverData = data;
    }

    public static HashMap<String, Object> getServerData() { //Returns all the current data the server has
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

    public static String messageExecution(Object[] eventQ) { //Creates a random stock event, and executes it
        String eventMessage = "";
        int eventID = (int) eventQ[0];
        String stockAffected = eventQ[1].toString();

        stockEventID = eventID;
        stockEventsAffect = stockAffected;


        if (eventID == 3){ //Passes a general statement to the rest of the application that no new events have occurred
            eventMessage = "No events happening today";

        }
        else if (eventID == 0) { //Creates a new stock to add to the market
            double stockPrice;
            int volitility, trend;

            boolean isTrending = false;
            int trendingUp = 0, trendDuration = 0;
            Random stockRandomizer = new Random();
            Object[] stockdata = new Object[8];
            String stockName = "";
            for (int j = 0; j <= 3; j++) {
                int number = stockRandomizer.nextInt(26 + 1); //26 letters in the alphabet, but the Random function does the number inputted minus 1.
                if (number == 1) {
                    stockName = stockName + "A";
                } else if (number == 2) {
                    stockName = stockName + "B";
                } else if (number == 3) {
                    stockName = stockName + "C";
                } else if (number == 4) {
                    stockName = stockName + "D";
                } else if (number == 5) {
                    stockName = stockName + "E";
                } else if (number == 6) {
                    stockName = stockName + "F";
                } else if (number == 7) {
                    stockName = stockName + "G";
                } else if (number == 8) {
                    stockName = stockName + "H";
                } else if (number == 9) {
                    stockName = stockName + "I";
                } else if (number == 10) {
                    stockName = stockName + "J";
                } else if (number == 11) {
                    stockName = stockName + "K";
                } else if (number == 12) {
                    stockName = stockName + "L";
                } else if (number == 13) {
                    stockName = stockName + "M";
                } else if (number == 14) {
                    stockName = stockName + "N";
                } else if (number == 15) {
                    stockName = stockName + "O";
                } else if (number == 16) {
                    stockName = stockName + "P";
                } else if (number == 17) {
                    stockName = stockName + "Q";
                } else if (number == 18) {
                    stockName = stockName + "R";
                } else if (number == 19) {
                    stockName = stockName + "S";
                } else if (number == 20) {
                    stockName = stockName + "T";
                } else if (number == 21) {
                    stockName = stockName + "U";
                } else if (number == 22) {
                    stockName = stockName + "V";
                } else if (number == 23) {
                    stockName = stockName + "W";
                } else if (number == 24) {
                    stockName = stockName + "X";
                } else if (number == 25) {
                    stockName = stockName + "Y";
                } else if (number == 26) {
                    stockName = stockName + "Z";
                }
            }
            stockPrice = stockRandomizer.nextDouble() * (double) 50; // Generates a random number between 0 and 50.
            stockPrice = (double) Math.round(stockPrice * (double) 100) / (double) 100; //Rounds the random value to the hundredths place.
            volitility = stockRandomizer.nextInt(100 + 1); //Generates a random number from 1 to 100
            trend = stockRandomizer.nextInt(100 + 1);

            stockdata[0] = stockName;
            stockdata[1] = stockPrice;
            stockdata[2] = volitility;
            stockdata[3] = trend;
            stockdata[4] = isTrending;
            stockdata[5] = trendingUp;
            stockdata[6] = trendDuration;
            stockdata[7] = stockPrice; //Starting Price. Doesn't Change

            EcoEngine.stockInfo.put(stockName, stockdata);
            EcoEngine.stockNames.add((String) stockdata[0]);
            EcoEngine.stockInfo.put("Names", EcoEngine.stockNames);

            Values.consoleQueue.add(stockName + "was added");

            eventMessage = ("Stock " + stockName + " has been added to the market");
        }
        else if (eventID == 1){ //Removes the stock that was passed from the event seed created by the engine
            EcoEngine.stockInfo.remove(stockAffected);
            EcoEngine.stockNames.remove(stockAffected);

            Values.consoleQueue.add(stockAffected + "was removed");

            eventMessage = ("Stock " + stockAffected + " has gone bankrupt, and has been removed from the market");
        }
        else if (eventID == 2){ //Splits the stock that was passed from the event seed created by the engine
            Random stockMultiplier = new Random();
            int multiplier = stockMultiplier.nextInt(10);
            StockManagement.splitStocks(multiplier, stockAffected);

            Object[] newStockPriceAfterSplit = (Object[])EcoEngine.stockInfo.get(stockAffected);
            newStockPriceAfterSplit[1] = (double)newStockPriceAfterSplit[1] / multiplier;
            EcoEngine.stockInfo.replace(stockAffected,newStockPriceAfterSplit);

            eventMessage = ("Stock " + stockAffected + " has split 1/" + multiplier);
        }
        return eventMessage;
    }
}
