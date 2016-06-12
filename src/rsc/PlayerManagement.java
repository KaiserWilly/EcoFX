package rsc;

/**
 * Created by james on 6/12/2016.
 */
public class PlayerManagement {
    private static double playerMoney = 100000.00;

    public static void subtractMoney(double money) {
        if (money > playerMoney) {
            return;
        }
        playerMoney -= money;
    }

    public static void addMoney(double money) {
        playerMoney += money;
    }
}
