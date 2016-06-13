package rsc;

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

    public static double getAssetWorth(){
        return playerMoney;
    }
}
