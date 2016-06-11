package fx.client;

import fx.server.ServerGUI;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import rsc.StockHistory;
import rsc.Values;

import java.util.Objects;

/**
 * Created by james on 4/30/2016.
 */
public class ClientOverviewTasks {

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
                        ClientOverviewGUI.avgMarData.getData().remove(0, ClientOverviewGUI.avgMarData.getData().size());
                        for (int i = 0; i < history.length; i++) {
                            if (history[i] != 0.0)
                                ClientOverviewGUI.avgMarData.getData().add(new XYChart.Data<>(i * 2, history[i]));
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
}
