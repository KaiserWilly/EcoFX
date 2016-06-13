package rsc;

import fx.client.ClientPortfolioGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 6/10/2016.
 */
public class StockManagement {
    public static ArrayList<String> ownedStockN = new ArrayList<>(), buyOrderN = new ArrayList<>(), sellOrderN = new ArrayList<>();
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
        StockHistory.history.add("Bought " + quantity + " shares of " + stockName + " for $" + pricePerShare + " per Share");
    }

    public static void sellStock(String stockName, int quantity, double pricePerShare, boolean order) {
        if (!order) {
            int org = (int) ownedStock.get(stockName)[0];
            double pps = (double) ownedStock.get(stockName)[1];
            if (org <= quantity) {
                ownedStockN.remove(stockName);
                ownedStock.remove(stockName);
            } else {
                ownedStock.replace(stockName, new Object[]{org - quantity, pps});
            }
        }
        PlayerManagement.addMoney(pricePerShare * (double) quantity);
        StockHistory.history.add("Sold " + quantity + " shares of " + stockName + " for $" + pricePerShare + " per Share");
    }

    public static double getOrgPrice(String name) {
        return (double) ownedStock.get(name)[1];
    }

    public static int getOwnedQty(String name) {
        try {
            return (int) ownedStock.get(name)[0];
        } catch (Exception e) {
            return -1;
        }
    }

    public static void setBuyOrder(String stockName, int quantity, double pricePerShare) {
        double orgPPS = StockHistory.getPrice(stockName);
        Object[] bOrder = new Object[]{stockName, quantity, pricePerShare, orgPPS};
        buyOrders.add(bOrder);
        buyOrderN.add(stockName);
        PlayerManagement.subtractMoney(pricePerShare * (double) quantity);
    }

    public static double buyOrderTargetPPS(String name) {
        for (int i = 0; i < buyOrders.size(); i++) {
            Object[] orderData = buyOrders.get(i);
            if (orderData[0].equals(name)) {

                return (double) orderData[2];
            }
        }
        return 0.0;
    }

    public static double sellOrderTargetPPS(String name) {
        for (int i = 0; i < sellOrders.size(); i++) {
            Object[] orderData = sellOrders.get(i);
            if (orderData[0].equals(name)) {

                return (double) orderData[2];
            }
        }
        return 0.0;
    }

    public static Object[] sellOrderNameData(String name) {
        for (int i = 0; i < sellOrders.size(); i++) {
            Object[] orderData = sellOrders.get(i);
            if (orderData[0].equals(name)) {

                return orderData;
            }
        }
        return null;
    }

    public static void setSellOrder(String stockName, int quantity, double pricePerShare) {
        double orgPPS = StockHistory.getPrice(stockName);
        Object[] sOrder = new Object[]{stockName, quantity, pricePerShare, orgPPS};
        sellOrderN.add(stockName);
        sellOrders.add(sOrder);

        int org = (int) ownedStock.get(stockName)[0];
        double pps = (double) ownedStock.get(stockName)[1];
        if (org <= quantity) {
            ownedStockN.remove(stockName);
            ownedStock.remove(stockName);
        } else {
            ownedStock.replace(stockName, new Object[]{org - quantity, pps});
        }
    }

    public static void cancelBuyOrder(String name) {
        for (int i = 0; i < buyOrders.size(); i++) {
            Object[] orderData = buyOrders.get(i);
            if (orderData[0].equals(name)) {
                PlayerManagement.addMoney((double) orderData[2] * (double) orderData[1]);
                buyOrders.remove(i);
                buyOrderN.remove(name);
                return;
            }
        }
    }

    public static void cancelSellOrder(String name) {
        for (int i = 0; i < sellOrders.size(); i++) {
            Object[] orderData = sellOrders.get(i);
            if (orderData[0].equals(name)) {
                String stockName = (String) orderData[0];
                int quantity = (int) orderData[1];
                double pricePerShare = (double) orderData[3];
                sellOrders.remove(i);
                sellOrderN.remove(name);
                if (!ownedStock.containsKey(stockName)) {
                    ownedStock.put(stockName, new Object[]{quantity, pricePerShare});
                    ownedStockN.add(stockName);
                } else {
                    double pps = (double) ownedStock.get(stockName)[1];
                    int orgQ = (int) ownedStock.get(stockName)[0];
                    double npps = ((pps * (double) orgQ) + ((double) quantity * pricePerShare)) / ((double) quantity + (double) orgQ);
                    ownedStock.replace(stockName, new Object[]{quantity + orgQ, npps});
                }
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

    public static Map<String, Object[]> getOwnedStockData() {
        return ownedStock;
    }

    public static void checkOrders() {
        for (int i = 0; i < buyOrders.size(); i++) {
            Object[] orderData = buyOrders.get(i);
            double pps = StockHistory.getPrice((String) orderData[0]);
            if (pps <= (double) orderData[2]) {
                buyStock((String) orderData[0], (int) orderData[1], pps, true);
                buyOrders.remove(i);
                buyOrderN.remove((String) orderData[0]);
                ClientPortfolioGUI.portS.change = true;
                ClientPortfolioGUI.buyS.change = true;
                i--; //Compensate for shift due to removal
            }
        }
        for (int i = 0; i < sellOrders.size(); i++) {
            Object[] orderData = sellOrders.get(i);
            double pps = StockHistory.getPrice((String) orderData[0]);
            if (pps >= (double) orderData[2]) {
                sellStock((String) orderData[0], (int) orderData[1], pps, true);
                sellOrders.remove(i);
                sellOrderN.remove((String) orderData[0]);
                i--; //Compensate for shift due to removal
            }
        }
    }
}
