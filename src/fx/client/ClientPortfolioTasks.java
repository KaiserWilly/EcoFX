package fx.client;

import fx.MenuSubGUI;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import rsc.StockHistory;
import rsc.StockManagement;
import rsc.Values;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by james on 6/11/2016.
 */
public class ClientPortfolioTasks {

    public static class portfolioService extends Service<Void> {
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        int count = -1;
        Font stockNF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
        Font priceF = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
        Font buttonF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
        public boolean change = false;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    ArrayList<String> stockNames = StockManagement.ownedStockN;
                    AnchorPane portfolioWidget = new AnchorPane();
                    portfolioWidget.setPrefSize(440, 1725);
                    AnchorPane.setTopAnchor(portfolioWidget, 0.0);
                    AnchorPane.setLeftAnchor(portfolioWidget, 0.0);
                    Platform.runLater(() -> {
                        if (stockNames.size() > 0) {
                            portfolioWidget.setPrefSize(440, 34.5 * (double) stockNames.size());
                            Collections.sort(stockNames, String.CASE_INSENSITIVE_ORDER);
                            double height = 0.0;
                            if (ClientPortfolioGUI.portfolioWidget.getChildren().size() > 0) {
                                ClientPortfolioGUI.portfolioWidget.getChildren().remove(0, ClientPortfolioGUI.portfolioWidget.getChildren().size());
                            }

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

                                double net = getNetChange(name);
                                Label pChange;
                                if (net < 0.0) {
                                    pChange = new Label(money.format(Math.abs(net)), new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/main/clientstockdownarrow-01.png"))));
                                } else {
                                    pChange = new Label(money.format(Math.abs(net)), new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/main/clientstockuparrow-01.png"))));
                                }
                                pChange.setContentDisplay(ContentDisplay.RIGHT);
                                pChange.setFont(priceF);
                                pChange.setTextFill(Paint.valueOf("White"));
                                pChange.setPrefSize(150.0, 35.0);
                                pChange.setTextAlignment(TextAlignment.CENTER);
                                pChange.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(pChange, 0.0);
                                AnchorPane.setLeftAnchor(pChange, 163.0);
                                widgetPane.getChildren().add(pChange);

                                Label qty = new Label(String.valueOf(StockManagement.getOwnedQty(name)));
                                qty.setFont(priceF);
                                qty.setTextFill(Paint.valueOf("White"));
                                qty.setPrefSize(75.0, 35.0);
                                qty.setTextAlignment(TextAlignment.CENTER);
                                qty.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(qty, 0.0);
                                AnchorPane.setRightAnchor(qty, 15.0);
                                widgetPane.getChildren().add(qty);

                                AnchorPane.setLeftAnchor(widgetPane, 0.0);
                                AnchorPane.setTopAnchor(widgetPane, height);
                                height += 42.0;
                                portfolioWidget.getChildren().add(widgetPane);
                            }
                        } else {
                            portfolioWidget.setPrefSize(440, 240);
                            Label noStocks = new Label("No stocks in portfolio!");
                            noStocks.setPrefSize(415, 35);
                            noStocks.setFont(stockNF);
                            noStocks.setTextFill(Paint.valueOf("White"));
                            noStocks.setAlignment(Pos.CENTER);
                            noStocks.setTextAlignment(TextAlignment.CENTER);
                            AnchorPane.setLeftAnchor(noStocks, 0.0);
                            AnchorPane.setTopAnchor(noStocks, 175.0);
                            portfolioWidget.getChildren().add(noStocks);
                        }
                        ClientPortfolioGUI.portPane.setContent(portfolioWidget);
                    });
                    while (count == Values.secCount && !change) {
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

        double getNetChange(String name) {
            double cPrice = StockHistory.getPrice(name);
            double orgPrice = StockManagement.getOrgPrice(name);
            int qty = StockManagement.getOwnedQty(name);
            return ((cPrice * (double) qty) - (orgPrice * (double) qty));

        }
    }

    public static class buyOrderService extends Service<Void> {
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        int count = -1;
        Font stockNF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
        Font priceF = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
        Font buttonF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
        public boolean change = false;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    ArrayList<String> stockNames = StockManagement.buyOrderN;
                    AnchorPane buyWidget = new AnchorPane();
                    buyWidget.setPrefSize(440, 1725);
                    AnchorPane.setTopAnchor(buyWidget, 0.0);
                    AnchorPane.setLeftAnchor(buyWidget, 0.0);
                    Platform.runLater(() -> {
                        if (stockNames.size() > 0) {
                            buyWidget.setPrefSize(440, 34.5 * (double) stockNames.size());
                            Collections.sort(stockNames, String.CASE_INSENSITIVE_ORDER);

                            double height = 0.0;
                            if (ClientPortfolioGUI.buyOrderWidget.getChildren().size() > 0) {
                                ClientPortfolioGUI.buyOrderWidget.getChildren().remove(0, ClientPortfolioGUI.buyOrderWidget.getChildren().size());
                            }

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

                                Label pChange = new Label(money.format(StockManagement.buyOrderTargetPPS(name)));
                                pChange.setFont(priceF);
                                pChange.setTextFill(Paint.valueOf("White"));
                                pChange.setPrefSize(75.0, 35.0);
                                pChange.setTextAlignment(TextAlignment.CENTER);
                                pChange.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(price, 0.0);
                                AnchorPane.setLeftAnchor(pChange, 200.0);
                                widgetPane.getChildren().add(pChange);

                                Button cancel = new Button("Cancel Order");
                                cancel.setOnAction(event -> {
                                    StockManagement.cancelBuyOrder(name);
                                    change = true;
                                });
                                cancel.setFont(buttonF);
                                cancel.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;");
                                cancel.setPrefSize(100, 25);
                                cancel.setTextFill(Paint.valueOf("White"));
                                cancel.setAlignment(Pos.CENTER);
                                cancel.setTextAlignment(TextAlignment.CENTER);
                                AnchorPane.setTopAnchor(cancel, 3.0);
                                AnchorPane.setRightAnchor(cancel, 15.0);
                                widgetPane.getChildren().add(cancel);

                                AnchorPane.setLeftAnchor(widgetPane, 0.0);
                                AnchorPane.setTopAnchor(widgetPane, height);
                                height += 42.0;
                                buyWidget.getChildren().add(widgetPane);
                            }
                        } else {
                            buyWidget.setPrefSize(440, 240);
                            Label noStocks = new Label("No buy orders!");
                            noStocks.setPrefSize(415, 35);
                            noStocks.setFont(stockNF);
                            noStocks.setTextFill(Paint.valueOf("White"));
                            noStocks.setAlignment(Pos.CENTER);
                            noStocks.setTextAlignment(TextAlignment.CENTER);
                            AnchorPane.setLeftAnchor(noStocks, 0.0);
                            AnchorPane.setTopAnchor(noStocks, 175.0);
                            buyWidget.getChildren().add(noStocks);
                        }
                        ClientPortfolioGUI.buyPane.setContent(buyWidget);
                    });
                    while (count == Values.secCount && !change) {
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

    public static class sellOrderService extends Service<Void> {
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        DecimalFormat perc = new DecimalFormat("#,##0.0");
        int count = -1;
        Font stockNF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
        Font priceF = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
        Font buttonF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
        public boolean change = false;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    ArrayList<String> stockNames = StockManagement.sellOrderN;
                    AnchorPane sellWidget = new AnchorPane();
                    sellWidget.setPrefSize(440, 1725);
                    AnchorPane.setTopAnchor(sellWidget, 0.0);
                    AnchorPane.setLeftAnchor(sellWidget, 0.0);
                    Platform.runLater(() -> {
                        if (stockNames.size() > 0) {
                            sellWidget.setPrefSize(440, 34.5 * (double) stockNames.size());
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

                                Label pChange = new Label(money.format(StockManagement.sellOrderTargetPPS(name)));
                                pChange.setFont(priceF);
                                pChange.setTextFill(Paint.valueOf("White"));
                                pChange.setPrefSize(75.0, 35.0);
                                pChange.setTextAlignment(TextAlignment.CENTER);
                                pChange.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(price, 0.0);
                                AnchorPane.setLeftAnchor(pChange, 200.0);
                                widgetPane.getChildren().add(pChange);


                                Button cancel = new Button("Cancel Order");
                                cancel.setOnAction(event -> {
                                    StockManagement.cancelSellOrder(name);
                                    ClientPortfolioGUI.sellS.change = true;
                                });
                                cancel.setFont(buttonF);
                                cancel.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;");
                                cancel.setPrefSize(100, 25);
                                cancel.setTextFill(Paint.valueOf("White"));
                                cancel.setAlignment(Pos.CENTER);
                                cancel.setTextAlignment(TextAlignment.CENTER);
                                AnchorPane.setTopAnchor(cancel, 3.0);
                                AnchorPane.setRightAnchor(cancel, 15.0);
                                widgetPane.getChildren().add(cancel);


                                AnchorPane.setLeftAnchor(widgetPane, 0.0);
                                AnchorPane.setTopAnchor(widgetPane, height);
                                height += 42.0;
                                sellWidget.getChildren().add(widgetPane);
                            }
                        } else {
                            sellWidget.setPrefSize(440, 240);
                            Label noStocks = new Label("No sell orders!");
                            noStocks.setPrefSize(415, 35);
                            noStocks.setFont(stockNF);
                            noStocks.setTextFill(Paint.valueOf("White"));
                            noStocks.setAlignment(Pos.CENTER);
                            noStocks.setTextAlignment(TextAlignment.CENTER);
                            AnchorPane.setLeftAnchor(noStocks, 0.0);
                            AnchorPane.setTopAnchor(noStocks, 175.0);
                            sellWidget.getChildren().add(noStocks);
                        }
                        ClientPortfolioGUI.sellPane.setContent(sellWidget);
                    });
                    while (count == Values.secCount && !change) {
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

        double getPProfit(String name) {
            Object[] orderData = StockManagement.sellOrderNameData(name);
            double tPPS = (double) orderData[2], orgPPS = (double) orderData[3];
            int quantity = (int) orderData[1];

            return (((double) quantity * tPPS) - ((double) quantity * orgPPS));
        }
    }

    public static class historyService extends Service<Void> {
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        DecimalFormat perc = new DecimalFormat("#,##0.00");
        int count = -1;
        Font stockNF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
        Font priceF = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
        Font buttonF = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
        public boolean change = false;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    ArrayList<String> stockNames = StockHistory.history;
                    AnchorPane sellWidget = new AnchorPane();
                    sellWidget.setPrefSize(440, 1725);
                    AnchorPane.setTopAnchor(sellWidget, 0.0);
                    AnchorPane.setLeftAnchor(sellWidget, 0.0);
                    Platform.runLater(() -> {
                        if (stockNames.size() > 0) {
                            sellWidget.setPrefSize(440, 34.5 * (double) stockNames.size());
                            double height = 0.0;
                            for (String name : stockNames) {
                                AnchorPane widgetPane = new AnchorPane();
                                widgetPane.setPrefSize(415.0, 20.0);
                                String graphPaneStyle = "-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #333333;";
                                widgetPane.setStyle(graphPaneStyle);

                                Label message = new Label(name);
                                message.setFont(buttonF);
                                message.setTextFill(Paint.valueOf("White"));
                                message.setPrefSize(425.0, 20.0);
                                message.setTextAlignment(TextAlignment.CENTER);
                                message.setAlignment(Pos.CENTER);
                                AnchorPane.setTopAnchor(message, 0.0);
                                AnchorPane.setLeftAnchor(message, 0.0);
                                widgetPane.getChildren().add(message);

                                AnchorPane.setLeftAnchor(widgetPane, 0.0);
                                AnchorPane.setTopAnchor(widgetPane, height);
                                height += 28.0;
                                sellWidget.getChildren().add(widgetPane);
                            }
                        } else {
                            sellWidget.setPrefSize(440, 240);
                            Label noStocks = new Label("No history!");
                            noStocks.setPrefSize(415, 35);
                            noStocks.setFont(stockNF);
                            noStocks.setTextFill(Paint.valueOf("White"));
                            noStocks.setAlignment(Pos.CENTER);
                            noStocks.setTextAlignment(TextAlignment.CENTER);
                            AnchorPane.setLeftAnchor(noStocks, 0.0);
                            AnchorPane.setTopAnchor(noStocks, 175.0);
                            sellWidget.getChildren().add(noStocks);
                        }
                        ClientPortfolioGUI.historyPane.setContent(sellWidget);
                    });
                    while (count == Values.secCount && !change) {
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
    }
}
