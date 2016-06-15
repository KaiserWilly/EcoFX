package rsc;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by james on 6/12/2016.
 */
public class PlayerManagement {
    private static Double playerMoney = 100000.00;
    public static String name = "BillyBob";
    public static ArrayList<Object[]> leaderboardData = new ArrayList<>();
    public static Integer trades = 0;

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

    public static Double getAssetWorth() {
        double value = playerMoney;
        ArrayList<String> oSN = StockManagement.ownedStockN;
        Map<String, Object[]> oSM = StockManagement.getOwnedStockData();
        ArrayList<Object[]> sellOrderData = StockManagement.getSellOrderData(), buyOrderData = StockManagement.getBuyOrderData();

        for (String name : oSN) {
            Object[] data = oSM.get(name);
            value += Double.parseDouble(String.valueOf(data[0])) * StockHistory.getPrice(name);
        }

        for (Object[] data : buyOrderData) {
            value += Double.parseDouble(String.valueOf(data[1])) * StockHistory.getPrice((String) data[0]);
        }

        for (Object[] data : sellOrderData) {
            value += Double.parseDouble(String.valueOf(data[1])) * StockHistory.getPrice((String) data[0]);
        }

        return value;
    }
}
