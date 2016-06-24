package fx.client;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created 4/11/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ClientPortfolioGUI: Styles, Manages, and displays graphical components of the Portfolio pane
 * Also ties Services to nodes that need to be updated
 */

public class ClientPortfolioGUI{
    public static ClientPortfolioTasks.PortfolioService portS = new ClientPortfolioTasks.PortfolioService();
    public static ClientPortfolioTasks.BuyOrderService buyS = new ClientPortfolioTasks.BuyOrderService();
    static AnchorPane portfolioWidget = new AnchorPane(), buyOrderWidget = new AnchorPane(), sellOrderWidget = new AnchorPane(), historyWidget = new AnchorPane();
    static ClientPortfolioTasks.SellOrderService sellS = new ClientPortfolioTasks.SellOrderService();
    static ClientPortfolioTasks.HistoryService histS = new ClientPortfolioTasks.HistoryService();
    static ScrollPane portPane, buyPane, sellPane, historyPane;
    private AnchorPane portfolioAnchorPane = new AnchorPane();
    private Font aeroMI10 = Font.loadFont(ClientPortfolioGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    private boolean opened = false;

    AnchorPane createPane() { //Basic and lowest level of pane
        portfolioAnchorPane.setPrefSize(980, 490);
        portfolioAnchorPane.getChildren().add(tabbedPane());
        portfolioAnchorPane.managedProperty().bind(portfolioAnchorPane.visibleProperty());
        portfolioAnchorPane.setVisible(false);
        return portfolioAnchorPane;
    }

    private AnchorPane tabbedPane() {

        AnchorPane tabPane = new AnchorPane();
        tabPane.setPrefSize(450, 478);
        AnchorPane.setTopAnchor(tabPane, 5.0);
        AnchorPane.setLeftAnchor(tabPane, 270.0);

        Tab port = new Tab("Portfolio");
        port.setContent(portfolioPane());

        Tab buy = new Tab("Buy Orders");
        buy.setContent(buyOrderPane());

        Tab sell = new Tab("Sell Orders");
        sell.setContent(sellOrderPane());

        Tab log = new Tab("History");
        log.setContent(historyPane());

        TabPane tab = new TabPane();
        tab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tab.getStylesheets().add("/rsc/StylesheetTabPane.css");
        tab.setPrefSize(450, 478);
        AnchorPane.setTopAnchor(tab, 0.0);
        AnchorPane.setLeftAnchor(tab, 0.0);

        tab.getTabs().add(port);
        tab.getTabs().add(buy);
        tab.getTabs().add(sell);
        tab.getTabs().add(log);

        tabPane.getChildren().add(tab);


        return tabPane;
    }

    private AnchorPane portfolioPane() {

        AnchorPane stockPane = new AnchorPane();
        stockPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        stockPane.setPrefSize(450.0, 478.0);
        AnchorPane.setTopAnchor(stockPane, 5.0);
        AnchorPane.setLeftAnchor(stockPane, 5.0);


        Label title = new Label("STOCK NAME");
        title.setFont(aeroMI10);
        title.setPrefSize(75, 10);
        title.setTextFill(Paint.valueOf("White"));
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(title, -1.0);
        AnchorPane.setLeftAnchor(title, 5.0);
        stockPane.getChildren().add(title);

        Label price = new Label("STOCK PRICE");
        price.setFont(aeroMI10);
        price.setPrefSize(75, 10);
        price.setTextFill(Paint.valueOf("White"));
        price.setAlignment(Pos.CENTER);
        price.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(price, -1.0);
        AnchorPane.setLeftAnchor(price, 105.0);
        stockPane.getChildren().add(price);

        Label change = new Label("NET PROFIT");
        change.setFont(aeroMI10);
        change.setPrefSize(75, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, -1.0);
        AnchorPane.setLeftAnchor(change, 215.0);
        stockPane.getChildren().add(change);

        portPane = new ScrollPane();
        portPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        portPane.setPrefSize(440.0, 418.0);
        portPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        portPane.setFitToWidth(true);
        portPane.setContent(portfolioWidget());
        AnchorPane.setTopAnchor(portPane, 10.0);
        AnchorPane.setLeftAnchor(portPane, 5.0);
        stockPane.getChildren().add(portPane);
        return stockPane;
    }

    private AnchorPane portfolioWidget() {
        portfolioWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(portfolioWidget, 0.0);
        AnchorPane.setLeftAnchor(portfolioWidget, 0.0);
        return portfolioWidget;
    }

    private AnchorPane buyOrderPane() {

        AnchorPane stockPane = new AnchorPane();
        stockPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        stockPane.setPrefSize(450.0, 478.0);
        AnchorPane.setTopAnchor(stockPane, 5.0);
        AnchorPane.setLeftAnchor(stockPane, 5.0);


        Label title = new Label("STOCK NAME");
        title.setFont(aeroMI10);
        title.setPrefSize(75, 10);
        title.setTextFill(Paint.valueOf("White"));
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(title, -1.0);
        AnchorPane.setLeftAnchor(title, 5.0);
        stockPane.getChildren().add(title);

        Label price = new Label("STOCK PRICE");
        price.setFont(aeroMI10);
        price.setPrefSize(75, 10);
        price.setTextFill(Paint.valueOf("White"));
        price.setAlignment(Pos.CENTER);
        price.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(price, -1.0);
        AnchorPane.setLeftAnchor(price, 105.0);
        stockPane.getChildren().add(price);

        Label change = new Label("TARGET PRICE");
        change.setFont(aeroMI10);
        change.setPrefSize(75, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, -1.0);
        AnchorPane.setLeftAnchor(change, 205.0);
        stockPane.getChildren().add(change);

        buyPane = new ScrollPane();
        buyPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        buyPane.setPrefSize(440.0, 418.0);
        buyPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        buyPane.setFitToWidth(true);
        buyPane.setContent(buyOrderWidget());
        AnchorPane.setTopAnchor(buyPane, 10.0);
        AnchorPane.setLeftAnchor(buyPane, 5.0);
        stockPane.getChildren().add(buyPane);
        return stockPane;
    }

    private AnchorPane buyOrderWidget() {
        buyOrderWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(buyOrderWidget, 0.0);
        AnchorPane.setLeftAnchor(buyOrderWidget, 0.0);
        return buyOrderWidget;
    }

    private AnchorPane sellOrderPane() {

        AnchorPane stockPane = new AnchorPane();
        stockPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        stockPane.setPrefSize(450.0, 478.0);
        AnchorPane.setTopAnchor(stockPane, 5.0);
        AnchorPane.setLeftAnchor(stockPane, 5.0);


        Label title = new Label("STOCK NAME");
        title.setFont(aeroMI10);
        title.setPrefSize(75, 10);
        title.setTextFill(Paint.valueOf("White"));
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(title, -1.0);
        AnchorPane.setLeftAnchor(title, 5.0);
        stockPane.getChildren().add(title);

        Label price = new Label("STOCK PRICE");
        price.setFont(aeroMI10);
        price.setPrefSize(75, 10);
        price.setTextFill(Paint.valueOf("White"));
        price.setAlignment(Pos.CENTER);
        price.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(price, -1.0);
        AnchorPane.setLeftAnchor(price, 105.0);
        stockPane.getChildren().add(price);

        Label change = new Label("TARGET PRICE");
        change.setFont(aeroMI10);
        change.setPrefSize(75, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, -1.0);
        AnchorPane.setLeftAnchor(change, 205.0);
        stockPane.getChildren().add(change);

        sellPane = new ScrollPane();
        sellPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        sellPane.setPrefSize(440.0, 418.0);
        sellPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sellPane.setFitToWidth(true);
        sellPane.setContent(sellOrderWidget());
        AnchorPane.setTopAnchor(sellPane, 10.0);
        AnchorPane.setLeftAnchor(sellPane, 5.0);
        stockPane.getChildren().add(sellPane);
        return stockPane;
    }

    private AnchorPane sellOrderWidget() {
        sellOrderWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(sellOrderWidget, 0.0);
        AnchorPane.setLeftAnchor(sellOrderWidget, 0.0);
        return sellOrderWidget;
    }

    private AnchorPane historyPane() {
        AnchorPane hPane = new AnchorPane();
        hPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        hPane.setPrefSize(450.0, 478.0);
        AnchorPane.setTopAnchor(hPane, 5.0);
        AnchorPane.setLeftAnchor(hPane, 5.0);


        historyPane = new ScrollPane();
        historyPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        historyPane.setPrefSize(440.0, 418.0);
        historyPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        historyPane.setFitToWidth(true);
        historyPane.setContent(historyWidget());
        AnchorPane.setTopAnchor(historyPane, 10.0);
        AnchorPane.setLeftAnchor(historyPane, 5.0);
        hPane.getChildren().add(historyPane);
        return hPane;
    }

    private AnchorPane historyWidget() {
        historyWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(historyWidget, 0.0);
        AnchorPane.setLeftAnchor(historyWidget, 0.0);
        return historyWidget;
    }

    public void setOpacity(double opacity) {
        portfolioAnchorPane.setOpacity(opacity);
    }

    void open() {
        if (!opened) {
            opened = true;
            portS.start();
            buyS.start();
            sellS.start();
            histS.start();
        }
        portfolioAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), portfolioAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
    }

    void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), portfolioAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            portfolioAnchorPane.setVisible(false);
            switch (newPane) {
                case "Overview":
                    ClientFrameGUI.overview.open();
                    break;
                case "Portfolio":
                    ClientFrameGUI.portfolio.open();
                    break;
                case "Buy Stocks":
                    ClientFrameGUI.buy.open();
                    break;
                case "Sell Stocks":
                    ClientFrameGUI.sell.open();
                    break;
            }
        });
        out.play();
    }
}
