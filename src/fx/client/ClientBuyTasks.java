package fx.client;

import fx.MenuSubGUI;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import rsc.PlayerManagement;
import rsc.StockHistory;
import rsc.Values;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by james on 6/10/2016.
 */
public class ClientBuyTasks {
    static String currentStock = null;

    public static class widgetService extends Service<Void> {
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        DecimalFormat perc = new DecimalFormat("$#,###,##0.00");
        int count = -1;
        Font stockNF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
        Font priceF = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
        Font buttonF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    ArrayList<String> stockNames = Values.stockNamesNC;
                    AnchorPane buyWidget = new AnchorPane();
                    AnchorPane.setTopAnchor(buyWidget, 0.0);
                    AnchorPane.setLeftAnchor(buyWidget, 0.0);
                    Platform.runLater(() -> {

                        if (stockNames.size() > 0) {
                            buyWidget.setPrefSize(440, 34.5 * (double) stockNames.size());
                            Collections.sort(stockNames, String.CASE_INSENSITIVE_ORDER);
                            double height = 0.0;
                            for (String name : stockNames) {

                                AnchorPane widgetPane = new AnchorPane();
                                widgetPane.setPrefSize(415.0, 35.0);
                                String graphPaneStyle = "-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #333333;";
                                widgetPane.setStyle(graphPaneStyle);

                                Label stock = new Label(name);
                                stock.setFont(stockNF);
                                stock.setTextFill(Paint.valueOf("White"));
                                stock.setPrefSize(75.0, 35.0);
                                stock.setTextAlignment(TextAlignment.CENTER);
                                stock.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(stock, 0.0);
                                AnchorPane.setLeftAnchor(stock, 0.0);
                                widgetPane.getChildren().add(stock);

                                Label price = new Label(money.format(StockHistory.getPrice(name)));
                                price.setFont(priceF);
                                price.setTextFill(Paint.valueOf("White"));
                                price.setPrefSize(75.0, 35.0);
                                price.setTextAlignment(TextAlignment.CENTER);
                                price.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(price, 0.0);
                                AnchorPane.setLeftAnchor(price, 100.0);
                                widgetPane.getChildren().add(price);

                                double pC = getPChange(name);
                                Label pChange;
                                if (pC < 0.0) {
                                    pChange = new Label(perc.format(Math.abs(pC)) /*+ "%"*/, new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/main/clientstockdownarrow-01.png"))));
                                } else {
                                    pChange = new Label(perc.format(Math.abs(pC)) /*+ "%"*/, new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/main/clientstockuparrow-01.png"))));
                                }
                                pChange.setContentDisplay(ContentDisplay.RIGHT);
                                pChange.setFont(priceF);
                                pChange.setTextFill(Paint.valueOf("White"));
                                pChange.setPrefSize(75.0, 35.0);
                                pChange.setTextAlignment(TextAlignment.CENTER);
                                pChange.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(pChange, 0.0);
                                AnchorPane.setLeftAnchor(pChange, 200.0);
                                widgetPane.getChildren().add(pChange);

                                Button lookin = new Button("Investigate");
                                lookin.setOnAction(event -> ClientBuyGUI.graphS.changeName = name);
                                lookin.setFont(buttonF);
                                lookin.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;");
                                lookin.setPrefSize(100, 25);
                                lookin.setTextFill(Paint.valueOf("White"));
                                lookin.setAlignment(Pos.CENTER);
                                lookin.setTextAlignment(TextAlignment.CENTER);
                                AnchorPane.setTopAnchor(lookin, 3.0);
                                AnchorPane.setRightAnchor(lookin, 15.0);
                                widgetPane.getChildren().add(lookin);

                                AnchorPane.setLeftAnchor(widgetPane, 0.0);
                                AnchorPane.setTopAnchor(widgetPane, height);
                                height += 42.0;
                                buyWidget.getChildren().add(widgetPane);
                            }
                        } else {
                            buyWidget.setPrefSize(440, 240);
                            Label noStocks = new Label("No stocks to buy!");
                            noStocks.setPrefSize(415, 35);
                            noStocks.setFont(stockNF);
                            noStocks.setTextFill(Paint.valueOf("White"));
                            noStocks.setAlignment(Pos.CENTER);
                            noStocks.setTextAlignment(TextAlignment.CENTER);
                            AnchorPane.setLeftAnchor(noStocks, 0.0);
                            AnchorPane.setTopAnchor(noStocks, 200.0);
                            buyWidget.getChildren().add(noStocks);
                        }
                        ClientBuyGUI.sPane.setContent(buyWidget);
                    });
                    while (count == Values.secCount) {
                        Thread.sleep(50);
                    }
                    count = Values.secCount;
                    return null;
                }
            }

                    ;
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }

        double getPChange(String name) {
            double[] history = StockHistory.getHistory(name);
            for (int i = 0; i < history.length; i++) {
                if (history[i] == 0.0) {
//                    return ((history[0] / history[i - 1]) * (double) 100) - 100.0;
                    return history[0] - history[i - 1];
                }

            }
//            return ((history[0] / history[history.length - 1]) * (double) 100) - 100.0;
            return history[0] - history[history.length - 1];

        }
    }

    public static class graphService extends Service<Void> {
        int count = 0;
        public String changeName = "<Select Stock>";
        private String name = "<Select Stock>";
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage(name);
                    Platform.runLater(() -> {
                        double pC = getPChange(name);
                        ClientBuyGUI.pChange.setText(money.format(Math.abs(pC)));
                        if (pC < 0.0) {
                            ClientBuyGUI.pChange.setGraphic(new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/main/clientstockdownarrow-01.png"))));

                        } else {
                            ClientBuyGUI.pChange.setGraphic(new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/main/clientstockuparrow-01.png"))));
                        }
                        ClientBuyGUI.pChange.setContentDisplay(ContentDisplay.RIGHT);

                        ClientBuyGUI.price.setText(money.format(StockHistory.getPrice(name)));

                        if (Values.stockNamesNC.contains(name)) {
                            double[] history = StockHistory.getHistory(name);
                            ClientBuyGUI.markData.getData().remove(0, ClientBuyGUI.markData.getData().size());
                            for (int i = 0; i < history.length; i++) {
                                if (history[i] != 0.0)
                                    ClientBuyGUI.markData.getData().add(new XYChart.Data<>(i * 2, history[i]));
                            }
                        }
                    });
                    while (count == Values.secCount && Objects.equals(changeName, name)) {
                        Thread.sleep(10);
                    }
                    count = Values.secCount;
                    name = changeName;
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            reset();
            start();
        }

        double getPChange(String name) {
            double[] history = StockHistory.getHistory(name);
            for (int i = 0; i < history.length; i++) {
                if (history[i] == 0.0) {
//                    return ((history[0] / history[i - 1]) * (double) 100) - 100.0;
                    try {
                        return history[0] - history[i - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return 0.0;
                    }
                }

            }
//            return ((history[0] / history[history.length - 1]) * (double) 100) - 100.0;
            return history[0] - history[history.length - 1];

        }
    }

    public static class sliderService extends Service<Void> {
        int count = 0;
        private String name = "<Select Stock>";
        double max;
        public boolean change = false;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ArrayList<String> stockN = Values.stockNamesNC;
                    Platform.runLater(() -> {
                        if (stockN.contains(name)) {
                            ClientBuyGUI.buySlider.setMajorTickUnit((int) PlayerManagement.getMoney() / StockHistory.getPrice(name));
                            ClientBuyGUI.buySlider.setMax(Math.floor(PlayerManagement.getMoney() / StockHistory.getPrice(name)));
                        } else {
                            ClientBuyGUI.buySlider.setMax(10);
                        }
                    });
                    while (name.equals(ClientBuyGUI.graphS.changeName) && !change) {
                        Thread.sleep(50);
                    }
                    name = ClientBuyGUI.graphS.changeName;
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
