package fx.client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import server.ServerMain;

/**
 * Created by james on 4/30/2016.
 */
public class ClientFrameTasks {

    public static class serverService extends Service<Void> {
        String ip = null;
        public serverService(String serverIP){
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
}
