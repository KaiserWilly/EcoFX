package server;

import rsc.Values;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created 1/12/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ServerTimer: Keeps the global server time, and runs the timer on a separate thread
 */
public class ServerTimer {
    private static Timer timer;

    public static void startTimer() { //Starts the timer
        timer = new Timer();
        Values.consoleQueue.add("Engine Started: Game begins in 3 seconds");
        timer.scheduleAtFixedRate(task(), 3000, 2000); //Task, delay, update speed

    }

    public static TimerTask task() { //Keeps the time of the server
        return new TimerTask() {
            @Override
            public void run() {
                ServerValues.genServerData();
                ServerValues.secCount++;
            }
        };
    }

    public static void pauseTimer() { //Pauses the timer if the server gets paused
        Values.consoleQueue.add("Game Paused! Press to resume");
        timer.cancel();
        timer.purge();
    }

    public static void resumeTimer() { //Resumes the timer when the server gets resumed
        timer.cancel();
        timer.purge();
        Values.consoleQueue.add("Game Resumed");
        timer = new Timer();
        timer.scheduleAtFixedRate(task(), 0, 2000); //Task, delay, update speed
    }

    public static void stopTimer() { //Stops timer when the game ends
        Values.consoleQueue.add("Game Ended!");
        try {
            timer.cancel();
            timer.purge();
        }catch(NullPointerException e){
            System.out.println("No timer to cancel!");
        }
    }


}
