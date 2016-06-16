package server;

import rsc.Values;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by james on 1/12/2016.
 */
public class ServerTimer {
    private static Timer timer;

    public static void startTimer() {
        timer = new Timer();
        Values.consoleQueue.add("Engine Started: Game begins in 3 seconds");
        timer.scheduleAtFixedRate(task(), 3000, 2000); //Task, delay, update speed

    }

    public static TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                ServerValues.genServerData();
                ServerValues.secCount++;
            }
        };
    }

    public static void pauseTimer() {
        Values.consoleQueue.add("Game Paused! Press to resume");
        timer.cancel();
        timer.purge();
    }

    public static void resumeTimer() {
        timer.cancel();
        timer.purge();
        Values.consoleQueue.add("Game Resumed");
        timer = new Timer();
        timer.scheduleAtFixedRate(task(), 0, 2000); //Task, delay, update speed
    }

    public static void stopTimer() {
        Values.consoleQueue.add("Game Ended!");
        try {
            timer.cancel();
            timer.purge();
        }catch(NullPointerException e){
            System.out.println("No timer to cancel!");
        }
    }


}
