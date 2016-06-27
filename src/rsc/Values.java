package rsc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created 10/29/15
 * Software Development
 * TSA Conference, 2016
 * Values: Class containing code that handles global variables
 */
public class Values {
    public final static double VERSION = 2.0;
    public static int secCount = 0, stockCount = 0;
    public static ArrayList<String> consoleQueue = new ArrayList<>(), messageQueue = new ArrayList<>();
    public static String seed = "0", maxP = "10", mode = "con", ip = "0.0.0.0", currentStockName = "Composite";
    public static ArrayList<String> stockNames = new ArrayList<>(), stockNamesNC = new ArrayList<>();

    public static HashMap<String, Object> getSettings() {

        HashMap<String, Object> settings;
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(Values.class.getClassLoader().getResource("src/rsc/settings.config")));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            settings = (HashMap<String, Object>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception i) {
            i.printStackTrace();
            settings = null;
        }
        return settings;
    }

    public static void saveSettings(HashMap<String, Object> settings) {
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Values.class.getClassLoader().getResource("src/rsc/settings.config")));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(settings);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStockForward() { //Returns the name of the next stock in the list
        stockCount++;
        if (stockNames.size() == 0) {
            stockCount = 0;
            return "Composite";
        } else if (stockCount >= stockNames.size()) {
            stockCount = 0;
        }
        return stockNames.get(stockCount);
    }

    public static String getStockBackward() { //Returns the name of the previous stock in the list
        stockCount--;
        if (stockNames.size() == 0) {
            stockCount = 0;
            return "Composite";
        } else if (stockCount < 0 && stockCount < stockNames.size()) {
            stockCount = stockNames.size() - 1;
        }

        return stockNames.get(stockCount);
    }

}

