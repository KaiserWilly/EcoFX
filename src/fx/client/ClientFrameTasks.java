package fx.client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import rsc.PlayerManagement;

import java.text.DecimalFormat;

/**
 * Created by james on 4/30/2016.
 */
public class ClientFrameTasks {

    public static class serverService extends Service<Void> {
        String ip = null;

        public serverService(String serverIP) {
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

    public static class cohService extends Service<Void> {
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
}
