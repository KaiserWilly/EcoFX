package fx.client;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import rsc.PlayerManagement;
import rsc.StockHistory;
import rsc.Values;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created 4/9/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ClientOverviewTasks: Services and tasks that update the Overview Pane
 */
class ClientOverviewTasks {

    static class GraphService extends Service<Void> { //Update graph and selection widget
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

    static class RankService extends Service<Void> {//Update leaderboard widget
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        int count = -1;
        public boolean change = false;
        Font stockNF = Font.loadFont(ClientOverviewTasks.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
        Font priceF = Font.loadFont(ClientOverviewTasks.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    ArrayList<Object[]> stockNames = PlayerManagement.leaderboardData;
                    AnchorPane sellWidget = new AnchorPane();
                    AnchorPane.setTopAnchor(sellWidget, 0.0);
                    AnchorPane.setLeftAnchor(sellWidget, 0.0);
                    Platform.runLater(() -> {
                        if (stockNames.size() > 0) {
                            sellWidget.setPrefSize(440, 34.5 * (double) stockNames.size());
                            double height = 0.0;
                            int rank = 1;
                            for (Object[] data : stockNames) {
                                AnchorPane widgetPane = new AnchorPane();
                                widgetPane.setPrefSize(415.0, 30.0);
                                String graphPaneStyle = "-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #333333;";
                                widgetPane.setStyle(graphPaneStyle);

                                Label rankL = new Label(String.valueOf(rank));
                                rankL.setFont(stockNF);
                                rankL.setTextFill(Paint.valueOf("White"));
                                rankL.setPrefSize(50.0, 30.0);
                                rankL.setTextAlignment(TextAlignment.CENTER);
                                rankL.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(rankL, 0.0);
                                AnchorPane.setLeftAnchor(rankL, 0.0);
                                widgetPane.getChildren().add(rankL);

                                Label trades = new Label(String.valueOf(data[1]));
                                trades.setFont(priceF);
                                trades.setTextFill(Paint.valueOf("White"));
                                trades.setPrefSize(50.0, 30.0);
                                trades.setTextAlignment(TextAlignment.CENTER);
                                trades.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(trades, 0.0);
                                AnchorPane.setLeftAnchor(trades, 60.0);
                                widgetPane.getChildren().add(trades);

                                Label name = new Label(String.valueOf(data[0]));
                                name.setFont(priceF);
                                name.setTextFill(Paint.valueOf("White"));
                                name.setPrefSize(120.0, 30.0);
                                name.setTextAlignment(TextAlignment.CENTER);
                                name.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(name, 0.0);
                                AnchorPane.setLeftAnchor(name, 120.0);
                                widgetPane.getChildren().add(name);

                                Label assets = new Label(money.format(Double.parseDouble(String.valueOf(data[2]))));
                                assets.setFont(priceF);
                                assets.setTextFill(Paint.valueOf("White"));
                                assets.setPrefSize(125.0, 30.0);
                                assets.setTextAlignment(TextAlignment.CENTER);
                                assets.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(assets, 0.0);
                                AnchorPane.setLeftAnchor(assets, 250.0);
                                widgetPane.getChildren().add(assets);


                                AnchorPane.setLeftAnchor(widgetPane, 0.0);
                                AnchorPane.setTopAnchor(widgetPane, height);
                                height += 37.0;
                                rank++;
                                sellWidget.getChildren().add(widgetPane);
                            }
                        } else {
                            sellWidget.setPrefSize(440, 240);

                            AnchorPane widgetPane = new AnchorPane();
                            widgetPane.setPrefSize(415.0, 30.0);
                            String graphPaneStyle = "-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #333333;";
                            widgetPane.setStyle(graphPaneStyle);

                            Label rankL = new Label(String.valueOf(1));
                            rankL.setFont(stockNF);
                            rankL.setTextFill(Paint.valueOf("White"));
                            rankL.setPrefSize(50.0, 30.0);
                            rankL.setTextAlignment(TextAlignment.CENTER);
                            rankL.setAlignment(Pos.CENTER);
                            AnchorPane.setTopAnchor(rankL, 0.0);
                            AnchorPane.setLeftAnchor(rankL, 0.0);
                            widgetPane.getChildren().add(rankL);

                            Label trades = new Label(String.valueOf(PlayerManagement.trades));
                            trades.setFont(priceF);
                            trades.setTextFill(Paint.valueOf("White"));
                            trades.setPrefSize(50.0, 30.0);
                            trades.setTextAlignment(TextAlignment.CENTER);
                            trades.setAlignment(Pos.CENTER);
                            AnchorPane.setTopAnchor(trades, 0.0);
                            AnchorPane.setLeftAnchor(trades, 60.0);
                            widgetPane.getChildren().add(trades);

                            Label name = new Label(PlayerManagement.name);
                            name.setFont(priceF);
                            name.setTextFill(Paint.valueOf("White"));
                            name.setPrefSize(120.0, 30.0);
                            name.setTextAlignment(TextAlignment.CENTER);
                            name.setAlignment(Pos.CENTER);
                            AnchorPane.setTopAnchor(name, 0.0);
                            AnchorPane.setLeftAnchor(name, 120.0);
                            widgetPane.getChildren().add(name);

                            Label assets = new Label(money.format(PlayerManagement.getAssetWorth()));
                            assets.setFont(priceF);
                            assets.setTextFill(Paint.valueOf("White"));
                            assets.setPrefSize(125.0, 30.0);
                            assets.setTextAlignment(TextAlignment.CENTER);
                            assets.setAlignment(Pos.CENTER);
                            AnchorPane.setTopAnchor(assets, 0.0);
                            AnchorPane.setLeftAnchor(assets, 250.0);
                            widgetPane.getChildren().add(assets);


                            AnchorPane.setLeftAnchor(widgetPane, 0.0);
                            AnchorPane.setTopAnchor(widgetPane, 0.0);
                            sellWidget.getChildren().add(widgetPane);
                        }
                        ClientOverviewGUI.tablePane.setContent(sellWidget);
                    });
                    while (count == Values.secCount && !change) {
                        Thread.sleep(50);
                    }
                    change = false;
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
}

