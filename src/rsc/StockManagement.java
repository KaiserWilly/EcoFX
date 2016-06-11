package rsc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 6/10/2016.
 */
public class StockManagement {
    public static ArrayList<String> ownedStockN = new ArrayList<>();
    HashMap<String, Object[]> ownedStock = new HashMap<>();

    public void buyStock(String stockName, int quantity, double pricePerShare) {
        if (!ownedStock.containsKey(stockName)) {
            ownedStock.put(stockName, new Object[]{quantity, pricePerShare});
            ownedStockN.add(stockName);
        } else {
            double pps = (double) ownedStock.get(stockName)[1];
            int orgQ = (int) ownedStock.get(stockName)[0];
            double npps = ((pps * (double) orgQ) + ((double) quantity * pricePerShare)) / ((double) quantity + (double) orgQ);
            ownedStock.replace(stockName, new Object[]{quantity + orgQ, npps});
        }
    }

    public void sellStock(String stockName, int quantity) {
        if (!ownedStock.containsKey(stockName)) {
            return;
        }
        int org = (int) ownedStock.get(stockName)[0];
        double pps = (double) ownedStock.get(stockName)[1];
        if (org <= quantity) {
            ownedStockN.remove(stockName);
            ownedStock.remove(stockName);
        } else {
            ownedStock.replace(stockName, new Object[]{org - quantity, pps});
        }
    }

    public ArrayList<String> getOwnedStockN;

    public HashMap<String, Integer> getOwnedStock;

    public double getPricePerShare(String stockName) {
        return (double) ownedStock.get(stockName)[1];
    }

}
