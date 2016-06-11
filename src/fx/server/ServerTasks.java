package fx.server;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import rsc.StockHistory;
import rsc.Values;
import server.ServerMain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by isenhartjd on 4/15/2016.
 */

public class ServerTasks {

    public static class serverService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ServerMain.startServer();
                    return null;
                }
            };
        }
    }

    public static class SECService extends Service<Void> {
        int count = -1;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (count <= 0) {
                        updateMessage("SEC Count: N/A");
                    } else {
                        updateMessage("SEC Count: " + count);
                    }
                    while (count == Values.secCount) {
                        Thread.sleep(50);
                    }
                    count = Values.secCount;
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }
    }

    public static class consoleService extends Service<Void> {
        String[] consoleTextA = new String[30];

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage(printToConsole(""));
                    while (Values.consoleQueue.size() == 0) {
                        Thread.sleep(50);
                    }
                    for (int i = 0; i < Values.consoleQueue.size(); i++) {
                        printToConsole(Values.consoleQueue.get(0));
                        Values.consoleQueue.remove(0);
                    }
                    return null;
                }
            };
        }

        public String printToConsole(String command) {
            String result;
            if (!command.equals("")) {
                Calendar now = Calendar.getInstance();
                DateFormat df = new SimpleDateFormat("[HH:mm:ss]: ");
                result = df.format(now.getTime()) + command;
                System.arraycopy(consoleTextA, 0, consoleTextA, 1, consoleTextA.length - 1);
                consoleTextA[0] = result;
            }
            StringBuilder consText = new StringBuilder();
            for (int i = (consoleTextA.length - 1); i >= 0; i--) {
                if (!Objects.equals(consoleTextA[i], "") && !Objects.equals(consoleTextA[i], null)) {
                    consText.append(consoleTextA[i]).append("\n");
                }
            }
            return consText.toString();
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }
    }

    public static class graphService extends Service<Void> {
        int count = 0;
        String name = "Composite";

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage(name);
                    Platform.runLater(() -> {
                        double[] history = StockHistory.getHistory(name);
                        ServerGUI.avgMarData.getData().remove(0, ServerGUI.avgMarData.getData().size());
                        for (int i = 0; i < history.length; i++) {
                            if (history[i] != 0.0)
                                ServerGUI.avgMarData.getData().add(new XYChart.Data<>(i * 2, history[i]));
                        }
                    });
                    while (count == Values.secCount && Objects.equals(name, Values.currentStockName)) {
                        Thread.sleep(10);
                    }
                    count = Values.secCount;
                    name = Values.currentStockName;
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }
    }

    public static class playerListService extends Service<Void> {
        int size = 0;


        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage(PrintToList(Values.playerList));
                    while (size == Values.playerList.size()) {
                        Thread.sleep(50);
                    }
                    size = Values.playerList.size();
                    return null;
                }
            };
        }

        public String PrintToList(ArrayList<String> playerList) {
            StringBuilder listText = new StringBuilder();
            for (int i = (playerList.size() - 1); i >= 0; i--) {
                listText.append(playerList.get(i)).append("\n");
            }
            return listText.toString();
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }
    }

    public static class playerLabelService extends Service<Void> {
        int size = -1;


        public int getSize() {
            return size;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Player Count: " + size);
                    while (getSize() == Values.playerList.size()) {
                        Thread.sleep(50);
                    }
                    size = Values.playerList.size();
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }
    }
}
