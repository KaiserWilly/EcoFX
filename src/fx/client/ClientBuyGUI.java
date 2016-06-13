package fx.client;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rsc.StockHistory;
import rsc.StockManagement;

/**
 * Created by james on 4/29/2016.
 */
public class ClientBuyGUI {
    public AnchorPane buyAnchorPane = new AnchorPane(), graphWidget = new AnchorPane();
    ;
    public static AnchorPane stockWidget = new AnchorPane();
    Font aeroMI30 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 40);
    Font aeroMI24 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    Font aeroMI18 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
    Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
    Font aeroMI10 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    Font aeroM14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroM.ttf"), 14);
    public static ClientBuyTasks.graphService graphS = new ClientBuyTasks.graphService();
    public static ClientBuyTasks.sliderService slidS = new ClientBuyTasks.sliderService();
    public static XYChart.Series<Number, Number> markData = new XYChart.Series<>();
    public static Slider buySlider;
    public static ScrollPane sPane;
    public static Label price, pChange;
    boolean opened = false;

    public AnchorPane createPane() {
        buyAnchorPane.setPrefSize(980, 490);
        buyAnchorPane.getChildren().add(stockPane());
        buyAnchorPane.getChildren().add(graphPane());


        buyAnchorPane.managedProperty().bind(buyAnchorPane.visibleProperty());
        buyAnchorPane.setVisible(false);
        return buyAnchorPane;
    }

    public AnchorPane stockPane() {
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

        Label change = new Label("CHANGE OVER 30S");
        change.setFont(aeroMI10);
        change.setPrefSize(75, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, -1.0);
        AnchorPane.setLeftAnchor(change, 200.0);
        stockPane.getChildren().add(change);

        sPane = new ScrollPane();
        sPane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        sPane.setPrefSize(440.0, 460.0);
        sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sPane.setFitToWidth(true);
        sPane.setContent(stockWidget());
        AnchorPane.setTopAnchor(sPane, 10.0);
        AnchorPane.setLeftAnchor(sPane, 5.0);
        stockPane.getChildren().add(sPane);
        return stockPane;
    }

    public AnchorPane stockWidget() {
        stockWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(stockWidget, 0.0);
        AnchorPane.setLeftAnchor(stockWidget, 0.0);
        ClientBuyTasks.widgetService widS = new ClientBuyTasks.widgetService();
        widS.start();
        return stockWidget;
    }

    public AnchorPane graphPane() {
        AnchorPane graphPane = new AnchorPane();
        graphPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        graphPane.setPrefSize(450.0, 478.0);
        graphPane.getChildren().add(graphWidget());
        graphPane.visibleProperty().bind(graphWidget.managedProperty());
        AnchorPane.setTopAnchor(graphPane, 5.0);
        AnchorPane.setRightAnchor(graphPane, 5.0);
        return graphPane;

    }

    public AnchorPane graphWidget() {
        graphWidget.setStyle("-fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-background-color: #333333;");
        graphWidget.setPrefSize(430, 350);
        AnchorPane.setTopAnchor(graphWidget, 10.0);
        AnchorPane.setLeftAnchor(graphWidget, 10.0);

        Label stock = new Label();
        stock.setFont(aeroMI30);
        stock.setTextFill(Paint.valueOf("White"));
        stock.textProperty().bind(graphS.messageProperty());
        stock.setPrefSize(150, 30);
        stock.setTextAlignment(TextAlignment.LEFT);
        stock.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setTopAnchor(stock, 10.0);
        AnchorPane.setLeftAnchor(stock, 75.0);
        graphWidget.getChildren().add(stock);


        Label priceL = new Label("STOCK PRICE");
        priceL.setFont(aeroMI10);
        priceL.setPrefSize(75, 10);
        priceL.setTextFill(Paint.valueOf("White"));
        priceL.setAlignment(Pos.CENTER);
        priceL.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(priceL, 15.0);
        AnchorPane.setLeftAnchor(priceL, 190.0);
        graphWidget.getChildren().add(priceL);


        Label change = new Label("CHANGE OVER 30S");
        change.setFont(aeroMI10);
        change.setPrefSize(75, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, 15.0);
        AnchorPane.setLeftAnchor(change, 270.0);
        graphWidget.getChildren().add(change);


        price = new Label();
        price.setFont(aeroMI18);
        price.setTextFill(Paint.valueOf("White"));
        price.setPrefSize(75.0, 30);
        price.setTextAlignment(TextAlignment.CENTER);
        price.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(price, 20.0);
        AnchorPane.setLeftAnchor(price, 190.0);
        graphWidget.getChildren().add(price);


        pChange = new Label();
        pChange.setContentDisplay(ContentDisplay.RIGHT);
        pChange.setFont(aeroMI18);
        pChange.setTextFill(Paint.valueOf("White"));
        pChange.setPrefSize(75.0, 30);
        pChange.setTextAlignment(TextAlignment.CENTER);
        pChange.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(pChange, 20.0);
        AnchorPane.setLeftAnchor(pChange, 270.0);
        graphWidget.getChildren().add(pChange);


        NumberAxis xAxis = new NumberAxis(0.0, 30.0, 2.0);
        NumberAxis yAxis = new NumberAxis(25.0, 30.0, 1.0);
        xAxis.setLabel("Time (sec)");
        yAxis.setLabel("Market Value ($)");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        LineChart<Number, Number> marketTrend = new LineChart<>(xAxis, yAxis);
        marketTrend.setAnimated(false);
        marketTrend.getStylesheets().add("rsc/StylesheetCharts.css");
        marketTrend.getData().add(markData);
        marketTrend.setPrefSize(425, 180);
        marketTrend.setCreateSymbols(true);
        marketTrend.setLegendVisible(false);
        AnchorPane.setTopAnchor(marketTrend, 40.0);
        AnchorPane.setLeftAnchor(marketTrend, -5.0);
        graphWidget.getChildren().add(marketTrend);


        buySlider = new Slider();
        buySlider.setPrefSize(400, 50);
        buySlider.setMin(0);
        buySlider.setMax(10);
        buySlider.setValue(0);
        buySlider.setShowTickLabels(true);
        buySlider.setShowTickMarks(true);
        buySlider.setMajorTickUnit((int) buySlider.getMax() / 5);
        buySlider.setMinorTickCount(5);
        buySlider.setBlockIncrement((int) buySlider.getMax() / 50);
        AnchorPane.setTopAnchor(buySlider, 225.0);
        AnchorPane.setLeftAnchor(buySlider, 5.0);
        graphWidget.getChildren().add(buySlider);
        graphWidget.managedProperty().bind(Bindings.greaterThan(buySlider.maxProperty(), 10.0));
        slidS.start();

        Label buyL = new Label("Buy:");
        buyL.setFont(aeroMI20);
        buyL.setPrefSize(200, 23);
        buyL.setTextFill(Paint.valueOf("White"));
        buyL.setAlignment(Pos.BOTTOM_LEFT);
        buyL.setTextAlignment(TextAlignment.LEFT);
        buyL.textProperty().bind(Bindings.format("Buy: %.0f Shares", buySlider.valueProperty()));
        AnchorPane.setTopAnchor(buyL, 205.0);
        AnchorPane.setLeftAnchor(buyL, 5.0);
        graphWidget.getChildren().add(buyL);

        Label sellOrderTitle = new Label("TARGET PRICE PER SHARE");
        sellOrderTitle.setFont(aeroMI10);
        sellOrderTitle.setPrefSize(120, 10);
        sellOrderTitle.setTextFill(Paint.valueOf("White"));
        sellOrderTitle.setAlignment(Pos.CENTER);
        sellOrderTitle.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setLeftAnchor(sellOrderTitle, 145.0);
        AnchorPane.setBottomAnchor(sellOrderTitle, 37.0);
        graphWidget.getChildren().add(sellOrderTitle);

        TextField sellOrderPrice = new TextField();
        sellOrderPrice.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null;");
        sellOrderPrice.setPrefSize(100, 25);
        sellOrderPrice.setAlignment(Pos.CENTER);
        sellOrderPrice.setFont(aeroMI14);
        AnchorPane.setLeftAnchor(sellOrderPrice, 155.0);
        AnchorPane.setBottomAnchor(sellOrderPrice, 50.0);
        graphWidget.getChildren().add(sellOrderPrice);


        CheckBox buyOrderCB = new CheckBox("Buy Order");
        buyOrderCB.setSelected(false);
        sellOrderPrice.editableProperty().bind(buyOrderCB.selectedProperty());
        buyOrderCB.setOnAction(event -> {
            if (!buyOrderCB.isSelected()) sellOrderPrice.setText("");
        });
        buyOrderCB.setPrefSize(100, 25);
        buyOrderCB.setTextFill(Paint.valueOf("White"));
        buyOrderCB.setAlignment(Pos.CENTER);
        buyOrderCB.setFont(aeroMI14);
        AnchorPane.setLeftAnchor(buyOrderCB, 25.0);
        AnchorPane.setBottomAnchor(buyOrderCB, 50.0);
        graphWidget.getChildren().add(buyOrderCB);


        Button buyB = new Button("Buy");
        buyB.setFont(aeroMI14);
        buyB.setPrefSize(50, 20);
        buyB.setTextFill(Paint.valueOf("White"));
        buyB.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;");
        buyB.setOnAction(event -> {
            String name = stock.textProperty().getValue();
            int quantity = (int) Math.floor(buySlider.getValue());
            double pps = StockHistory.getPrice(name);
            if (quantity > 0) {
                if (buyOrderCB.isSelected()) {
                    pps = Double.parseDouble(sellOrderPrice.getText().replaceAll("[^0-9.]", ""));
                    StockManagement.setBuyOrder(name, quantity, pps);
                } else {
                    StockManagement.buyStock(name, quantity, pps, false);
                }
            }
            sellOrderPrice.setText("");
            buySlider.setValue(0);
            buyOrderCB.setSelected(false);
            ClientFrameGUI.resetWidgets();

        });
        AnchorPane.setBottomAnchor(buyB, 5.0);
        AnchorPane.setRightAnchor(buyB, 5.0);
        graphWidget.getChildren().add(buyB);

        return graphWidget;
    }

    public void setOpacity(double opacity) {
        buyAnchorPane.setOpacity(opacity);
    }

    public void open() {

        if (!opened) {
            opened = true;
            startServices();
        }
        buyAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), buyAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
    }

    public void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), buyAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            buyAnchorPane.setVisible(false);
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

    void startServices() {
        graphS.start();
    }
}
