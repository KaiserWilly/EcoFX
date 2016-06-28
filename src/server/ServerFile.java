package server;

import rsc.Values;
import server.engine.EcoEngine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created 12/28/15
 * Software Development
 * TSA Conference, 2016
 * ServerFile: Class containing code that stamps each console output
 */
class ServerFile {
    private static HashMap<String, Object> dataToClient;

    static void showTimeStamp(String command) {
        //Creates A Time Stamp, Used For Debugging Purposes

        Calendar now = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("[dd/MM HH:mm:ss]: ");
        String result = df.format(now.getTime()) + command;
        System.out.println(result);
    }

    public static HashMap<String, Object> getData() {
        dataToClient = new HashMap<>();
        dataToClient.put("Engine Data", EcoEngine.getData());
        dataToClient.put("SEC Count", Values.secCount);
        return dataToClient;
    }
}
