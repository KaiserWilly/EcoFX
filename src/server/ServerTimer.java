package server;

import rsc.Values;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by james on 1/12/2016.
 */
public class ServerTimer {
    static Timer timer;

    public static void startTimer() {
        timer = new Timer();
        Values.consoleQueue.add("Engine Started: Game begins in 3 seconds");
        timer.scheduleAtFixedRate(task(), 3000, 2000); //Task, delay, update speed

    }

    public static TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                ServerValues.secCount++;
                ServerValues.genServerData();
//                ServerFile.showTimeStamp("Data Update Sent; SEC: " + Values.secCount);
            }
        };
    }


}
