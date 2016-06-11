package server;

import rsc.StockHistory;
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
                Values.secCount++;
                switch (Values.secCount) {
                    case 1:
                        Values.consoleQueue.add("Game started!");
                        break;
                    case 2:
                        Values.consoleQueue.add("Kaiser Joined!");
                        Values.playerList.add("The Kaiser");
                        break;
                    case 4:
                        Values.consoleQueue.add("Mandalute Joined!");
                        Values.playerList.add("Mandalute");
                        break;
                    case 6:
                        Values.consoleQueue.add("TheGamerzRevolution Joined!");
                        Values.playerList.add("TheGamerzRevolution");
                        break;
                    case 8:
                        Values.consoleQueue.add("E-Dubble Joined!");
                        Values.playerList.add("E-Dubble");
                        break;

                    case 16:
                        Values.consoleQueue.add("E-Dubble Disconnected!");
                        Values.playerList.remove("E-Dubble");
                        break;
                }
                server.engine.EcoEngine.genereateData();
                StockHistory.addHistory(server.engine.EcoEngine.getData());
//                ServerFile.showTimeStamp("Data Update Sent; SEC: " + Values.secCount);
            }
        };
    }


}
