package rsc;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by james on 5/12/2016.
 */
public class StockHistory {
    static HashMap<String, double[]> stockHistory = new HashMap<>();
    public static ArrayList<String> history = new ArrayList<>();

    public static void addHistory(HashMap<String, Object> stockMap) {
        double composite = 0;
        ArrayList<String> localNames = Values.stockNamesNC;
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
                localNames.remove(name);
            }

            composite += (double) stock[1];
        }

        for (String localName : localNames) {
            stockHistory.remove(localName);
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

    public static double[] getHistory(String key) {
        if (!stockHistory.containsKey(key)) {
            return new double[30];
        }
        return stockHistory.get(key);
    }

    public static double getPrice(String key) {
        double[] history = stockHistory.get(key);
        try {
            return history[0];
        } catch (NullPointerException e) {
            return 0.0;
        }

    }

}
