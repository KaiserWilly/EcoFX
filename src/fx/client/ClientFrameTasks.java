package fx.client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import rsc.PlayerManagement;
import rsc.Values;

import java.text.DecimalFormat;

/**
 * Created 4/4/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ClientFrameTasks: Services that control updates of Frame UI, as well as starting client network.
 */
class ClientFrameTasks {

    static class ServerService extends Service<Void> { //Start Server
        String ip = null;
        ServerService(String serverIP) {
            ip = serverIP;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ClientNetwork.connentToServer(ip);
                    return null;
                }
            };
        }
    }

    static class CohService extends Service<Void> {//Update Cash on Hand
        double coh = 100000.00;
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Cash on Hand: " + money.format(coh));

                    while (coh == PlayerManagement.getMoney()) {
                        Thread.sleep(50);
                    }
                    coh = PlayerManagement.getMoney();

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

    static class MessageService extends Service<Void> {//Handles incoming messages from server

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("News:");
                    while (Values.messageQueue.size() == 0) {
                        Thread.sleep(50);
                    }
                    updateMessage("News: " + Values.messageQueue.get(0));
                    int count = Values.secCount;
                    while (Values.secCount - count < 2) {
                        Thread.sleep(50);
                    }
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

