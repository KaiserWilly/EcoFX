package rsc;

import java.util.HashMap;

/**
 * Created by james on 5/12/2016.
 */
public class StockHistory {
    static HashMap<String, double[]> stockHistory = new HashMap<>();

    public static void addHistory(Object[][] stockArray) {
        int i;
        double composite = 0;
        for (i = 0; i < stockArray.length; i++) {
            String name = String.valueOf(stockArray[i][0]);
            if (!stockHistory.containsKey(name)) {
                Values.stockNames.add(name);
                Values.stockNamesNC.add(name);
                double[] history = new double[16];
                history[0] = (double) stockArray[i][1];
                stockHistory.put(name, history);
            } else {
                double[] history = stockHistory.get(name);
                System.arraycopy(history, 0, history, 1, history.length - 1);
                history[0] = (double) stockArray[i][1];
                stockHistory.replace(name, history);
            }
            composite += (double) stockArray[i][1];
        }
        composite = composite / stockArray.length;
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
        return history[0];

    }

}
