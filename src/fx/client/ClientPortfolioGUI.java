package fx.client;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
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
 * Created by james on 4/29/2016.
 */
public class ClientPortfolioGUI {
    public AnchorPane portfolioAnchorPane = new AnchorPane();
    public static AnchorPane portfolioWidget = new AnchorPane(), buyOrderWidget = new AnchorPane(), sellOrderWidget = new AnchorPane();
    Font aeroMI30 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 40);
    Font aeroMI24 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
    Font aeroMI10 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    Font aeroM14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroM.ttf"), 14);
//    public static ClientSellTasks.graphService graphS = new ClientSellTasks.graphService();
    public static ClientPortfolioTasks.portfolioService portS = new ClientPortfolioTasks.portfolioService();
    public static ClientPortfolioTasks.buyOrderService buyS = new ClientPortfolioTasks.buyOrderService();
    public static ClientPortfolioTasks.sellOrderService sellS = new ClientPortfolioTasks.sellOrderService();
    public static XYChart.Series<Number, Number> markData = new XYChart.Series<>();
    boolean opened = false;

    public AnchorPane createPane() {
        portfolioAnchorPane.setPrefSize(980, 490);
        portfolioAnchorPane.getChildren().add(tabbedPane());


        portfolioAnchorPane.managedProperty().bind(portfolioAnchorPane.visibleProperty());
        portfolioAnchorPane.setVisible(false);
        return portfolioAnchorPane;
    }

    public AnchorPane tabbedPane() {

        AnchorPane tabPane = new AnchorPane();
        tabPane.setPrefSize(450, 478);
        AnchorPane.setTopAnchor(tabPane, 5.0);
        AnchorPane.setLeftAnchor(tabPane, 5.0);

        Tab port = new Tab("Portfolio");
        port.setContent(portfolioPane());

        Tab buy = new Tab("Buy Orders");
        buy.setContent(buyOrderPane());

        Tab sell = new Tab("Sell Orders");
        sell.setContent(sellOrderPane());

        TabPane tab = new TabPane();
        tab.getStylesheets().add("/rsc/StylesheetTabPane.css");
        tab.setPrefSize(450, 478);
        AnchorPane.setTopAnchor(tab, 0.0);
        AnchorPane.setLeftAnchor(tab, 0.0);

        tab.getTabs().add(port);
        tab.getTabs().add(buy);
        tab.getTabs().add(sell);

        tabPane.getChildren().add(tab);


        return tabPane;
    }

    public AnchorPane portfolioPane() {

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

        ScrollPane sPane = new ScrollPane();
        sPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        sPane.setPrefSize(440.0, 418.0);
        sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sPane.setFitToWidth(true);
        sPane.setContent(portfolioWidget());
        AnchorPane.setTopAnchor(sPane, 10.0);
        AnchorPane.setLeftAnchor(sPane, 5.0);
        stockPane.getChildren().add(sPane);
        return stockPane;
    }

    public AnchorPane portfolioWidget() {
        portfolioWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(portfolioWidget, 0.0);
        AnchorPane.setLeftAnchor(portfolioWidget, 0.0);
        return portfolioWidget;
    }

    public AnchorPane buyOrderPane() {

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
        AnchorPane.setLeftAnchor(change, 200.0);
        stockPane.getChildren().add(change);

        ScrollPane sPane = new ScrollPane();
        sPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        sPane.setPrefSize(440.0, 418.0);
        sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sPane.setFitToWidth(true);
        sPane.setContent(buyOrderWidget());
        AnchorPane.setTopAnchor(sPane, 10.0);
        AnchorPane.setLeftAnchor(sPane, 5.0);
        stockPane.getChildren().add(sPane);
        return stockPane;
    }

    public AnchorPane buyOrderWidget() {
        buyOrderWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(buyOrderWidget, 0.0);
        AnchorPane.setLeftAnchor(buyOrderWidget, 0.0);
        return buyOrderWidget;
    }

    public AnchorPane sellOrderPane() {

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
        AnchorPane.setLeftAnchor(change, 200.0);
        stockPane.getChildren().add(change);

        ScrollPane sPane = new ScrollPane();
        sPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        sPane.setPrefSize(440.0, 418.0);
        sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sPane.setFitToWidth(true);
        sPane.setContent(sellOrderWidget());
        AnchorPane.setTopAnchor(sPane, 10.0);
        AnchorPane.setLeftAnchor(sPane, 5.0);
        stockPane.getChildren().add(sPane);
        return stockPane;
    }

    public AnchorPane sellOrderWidget() {
        sellOrderWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(sellOrderWidget, 0.0);
        AnchorPane.setLeftAnchor(sellOrderWidget, 0.0);
        return sellOrderWidget;
    }

    public void setOpacity(double opacity) {
        portfolioAnchorPane.setOpacity(opacity);
    }

    public void open() {
        if (!opened) {
            opened = true;
            portS.start();
            buyS.start();
//            sellS.start();
        }
        portfolioAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), portfolioAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
    }

    public void close(String newPane) {
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
