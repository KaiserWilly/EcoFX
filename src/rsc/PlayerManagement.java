package rsc;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by james on 6/12/2016.
 */
public class PlayerManagement {
    private static double playerMoney = 100000.00;
    public static String name = "BillyBob";

    public static void subtractMoney(double money) {
        if (money > playerMoney) {
            return;
        }
        playerMoney -= money;
    }

    public static void addMoney(double money) {
        playerMoney += money;
    }

    public static double getMoney() {
        return playerMoney;
    }

    public static double getAssetWorth() {
        double value = playerMoney;
        ArrayList<String> oSN = StockManagement.ownedStockN;
        Map<String, Object[]> oSM = StockManagement.getOwnedStockData();
        ArrayList<Object[]> sellOrderData = StockManagement.getSellOrderData(), buyOrderData = StockManagement.getBuyOrderData();

        for (String name : oSN) {
            Object[] data = oSM.get(name);
            value += (double) data[0] * StockHistory.getPrice(name);
        }

        for (Object[] data : buyOrderData) {
            value += (double) data[1] * StockHistory.getPrice((String) data[0]);
        }

        for (Object[] data : sellOrderData) {
            value += (double) data[1] * StockHistory.getPrice((String) data[0]);
        }

        return value;
    }
}
