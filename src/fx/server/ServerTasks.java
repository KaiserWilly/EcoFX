package fx.server;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import rsc.StockHistory;
import rsc.Values;
import server.ServerMain;
import server.ServerValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created 5/1/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ServerTasks: Update and control ServerUI panel, serverside
 */

class ServerTasks {

    static class serverService extends Service<Void> {//Start Server

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

    static class SECService extends Service<Void> { //Update SEC Count
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
                    while (count == ServerValues.secCount) {
                        Thread.sleep(50);
                    }
                    count = ServerValues.secCount;
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

    static class consoleService extends Service<Void> { //Update console
        String[] consoleTextA = new String[28];

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

        String printToConsole(String command) { //Format console output
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

    static class graphService extends Service<Void> { //Update graph and ame widget
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
                        ServerGUI.markData.getData().remove(0, ServerGUI.markData.getData().size());
                        for (int i = 0; i < history.length; i++) {
                            if (history[i] != 0.0)
                                ServerGUI.markData.getData().add(new XYChart.Data<>(i * 2, history[i]));
                        }
                    });
                    while (count == ServerValues.secCount && Objects.equals(name, Values.currentStockName)) {
                        Thread.sleep(10);
                    }
                    count = ServerValues.secCount;
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

    static class playerListService extends Service<Void> {//Control playet Lists
        int size = 0;


        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage(PrintToList(ServerValues.playerList));
                    while (size == ServerValues.playerList.size()) {
                        Thread.sleep(50);
                    }
                    size = ServerValues.playerList.size();
                    return null;
                }
            };
        }

        String PrintToList(ArrayList<String> playerList) {
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

    static class playerLabelService extends Service<Void> {//Coltrol player List label
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
                    while (getSize() == ServerValues.playerList.size()) {
                        Thread.sleep(50);
                    }
                    size = ServerValues.playerList.size();
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
