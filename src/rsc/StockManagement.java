package rsc;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by james on 6/10/2016.
 */
public class StockManagement {
    public static ArrayList<String> ownedStockN = new ArrayList<>();
    private static HashMap<String, Object[]> ownedStock = new HashMap<>();
    private static ArrayList<Object[]> buyOrders = new ArrayList<>(), sellOrders = new ArrayList<>();

    public static void buyStock(String stockName, int quantity, double pricePerShare, boolean order) {
        if (!ownedStock.containsKey(stockName)) {
            ownedStock.put(stockName, new Object[]{quantity, pricePerShare});
            ownedStockN.add(stockName);
        } else {
            double pps = (double) ownedStock.get(stockName)[1];
            int orgQ = (int) ownedStock.get(stockName)[0];
            double npps = ((pps * (double) orgQ) + ((double) quantity * pricePerShare)) / ((double) quantity + (double) orgQ);
            ownedStock.replace(stockName, new Object[]{quantity + orgQ, npps});
        }
        if (!order) {
            PlayerManagement.subtractMoney(pricePerShare * (double) quantity);
        }
    }

    public static void sellStock(String stockName, int quantity, double pricePerShare) {
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
        PlayerManagement.addMoney(pricePerShare * (double) quantity);
    }

    public static void setBuyOrder(String stockName, int quantity, double pricePershare) {
        Object[] bOrder = new Object[]{stockName, quantity, pricePershare};
        buyOrders.add(bOrder);
        PlayerManagement.subtractMoney(pricePershare * (double) quantity);
    }

    public static void setSellOrder(String stockName, int quantity, double pricePerShare) {
        Object[] sOrder = new Object[]{stockName, quantity, pricePerShare};
        sellOrders.add(sOrder);
    }

    public static void cancelBuyOrder(String name) {
        for (int i = 0; i < buyOrders.size(); i++) {
            Object[] orderData = buyOrders.get(i);
            if (orderData[0].equals(name)) {
                PlayerManagement.addMoney((double) orderData[2] * (double) orderData[1]);
                buyOrders.remove(i);
                return;
            }
        }
    }

    public static void cancelSellOrder(String name) {
        for (int i = 0; i < sellOrders.size(); i++) {
            Object[] orderData = sellOrders.get(i);
            if (orderData[0].equals(name)) {
                sellOrders.remove(i);
                return;
            }
        }
    }

    public static ArrayList<Object[]> getBuyOrderData() {
        return buyOrders;
    }

    public static ArrayList<Object[]> getSellOrderData() {
        return sellOrders;
    }

    public static void checkOrders() {
        for (int i = 0; i < buyOrders.size(); i++) {
            Object[] orderData = buyOrders.get(i);
            double pps = StockHistory.getPrice((String) orderData[0]);
            if (pps <= (double) orderData[2]) {
                buyStock((String) orderData[0], (int) orderData[1], pps, true);
                buyOrders.remove(i);
                i--; //Compensate for shift due to removal
            }
        }
        for (int i = 0; i < sellOrders.size(); i++) {
            Object[] orderData = sellOrders.get(i);
            double pps = StockHistory.getPrice((String) orderData[0]);
            if (pps >= (double) orderData[2]) {
                sellStock((String) orderData[0], (int) orderData[1], pps);
                sellOrders.remove(i);
                i--; //Compensate for shift due to removal
            }
        }
    }
}
