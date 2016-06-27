package rsc;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created 4/4/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * StockHistory: Creates the history of all the stocks currently in the market, and the market itself.
 */
public class StockHistory {
    public static HashMap<String, double[]> stockHistory = new HashMap<>();
    public static ArrayList<String> history = new ArrayList<>();

    public static void addHistory(HashMap<String, Object> stockMap) { //Adds the history of a chosen stock to the current GUI
        double composite = 0;
        ArrayList<String> stockN = (ArrayList<String>) stockMap.get("Names");
        for (int i = 0; i < stockN.size(); i++) {
            String name = stockN.get(i);
            Object[] stock = (Object[]) stockMap.get(name);
            if (!stockHistory.containsKey(name)) {
                Values.stockNames.add(name);
                Values.stockNamesNC.add(name);
                double[] history = new double[16];
                history[0] = (double) stock[1];
                stockHistory.put(name, history);
            } else {
                double[] history = stockHistory.get(name);
                System.arraycopy(history, 0, history, 1, history.length - 1);
                history[0] = (double) stock[1];
                stockHistory.replace(name, history);
            }

            composite += (double) stock[1];
        }

        composite = composite / stockN.size();
        if (!stockHistory.containsKey("Composite")) {
            double[] history = new double[16];
            history[0] = composite;
            stockHistory.put("Composite", history);
        } else {
            Values.stockNames.set(0, "Composite");
            double[] history = stockHistory.get("Composite");
            System.arraycopy(history, 0, history, 1, history.length - 1);
            history[0] = composite;
            stockHistory.replace("Composite", history);
        }
    }

    public static void removeStock(String name){ //Removes the history of a chosen stock from the current GUI
        Values.stockNamesNC.remove(name);
        Values.stockNames.remove(name);
        stockHistory.remove(name);
    }

    public static double[] getHistory(String key) { //Gets the history of a chosen stock
        if (!stockHistory.containsKey(key)) {
            return new double[30];
        }
        return stockHistory.get(key);
    }

    public static double getPrice(String key) { //Gets the price of a chosen stock
        double[] history = stockHistory.get(key);
        try {
            return history[0];
        } catch (NullPointerException e) {
            return 0.0;
        }

    }

}
