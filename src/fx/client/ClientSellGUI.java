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
public class ClientSellGUI {
    public AnchorPane sellAnchorPane = new AnchorPane();
    public static AnchorPane stockWidget = new AnchorPane();
    Font aeroMI30 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 40);
    Font aeroMI24 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
    Font aeroMI10 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    Font aeroM14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroM.ttf"), 14);
    public static ClientSellTasks.widgetService widS = new ClientSellTasks.widgetService();
    public static ClientSellTasks.graphService graphS = new ClientSellTasks.graphService();
    public static ClientSellTasks.sliderService slidS = new ClientSellTasks.sliderService();
    public static XYChart.Series<Number, Number> markData = new XYChart.Series<>();
    public static Slider sellSlider;
    boolean opened = false;

    public AnchorPane createPane() {
        sellAnchorPane.setPrefSize(980, 490);
        sellAnchorPane.getChildren().add(stockPane());
        sellAnchorPane.getChildren().add(graphPane());


        sellAnchorPane.managedProperty().bind(sellAnchorPane.visibleProperty());
        sellAnchorPane.setVisible(false);
        return sellAnchorPane;
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

        ScrollPane sPane = new ScrollPane();
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
        return stockWidget;
    }

    public AnchorPane graphPane() {
        AnchorPane graphPane = new AnchorPane();
        graphPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        graphPane.setPrefSize(450.0, 478.0);
        graphPane.getChildren().add(graphWidget());
        AnchorPane.setTopAnchor(graphPane, 5.0);
        AnchorPane.setRightAnchor(graphPane, 5.0);
        return graphPane;

    }

    public AnchorPane graphWidget() {
        AnchorPane graphWidget = new AnchorPane();
        graphWidget.setStyle("-fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-background-color: #333333;");
        graphWidget.setPrefSize(430, 350);
        AnchorPane.setTopAnchor(graphWidget, 10.0);
        AnchorPane.setLeftAnchor(graphWidget, 10.0);

        Label stock = new Label();
        stock.setFont(aeroMI30);
        stock.setTextFill(Paint.valueOf("White"));
        stock.textProperty().bind(graphS.messageProperty());
        stock.setPrefSize(250, 30);
        stock.setTextAlignment(TextAlignment.LEFT);
        stock.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setTopAnchor(stock, 10.0);
        AnchorPane.setLeftAnchor(stock, 55.0);
        graphWidget.getChildren().add(stock);

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


        Label sellL = new Label();
        sellL.setFont(aeroMI20);
        sellL.setPrefSize(175, 23);
        sellL.setTextFill(Paint.valueOf("White"));
        sellL.setAlignment(Pos.BOTTOM_LEFT);
        sellL.setTextAlignment(TextAlignment.LEFT);
        AnchorPane.setTopAnchor(sellL, 205.0);
        AnchorPane.setLeftAnchor(sellL, 5.0);
        graphWidget.getChildren().add(sellL);

        sellSlider = new Slider();
        sellSlider.setPrefSize(400, 50);
        sellSlider.setMin(0);
        sellSlider.setMax(1000);
        sellSlider.setValue(0);
        sellSlider.setShowTickLabels(true);
        sellSlider.setShowTickMarks(true);
        sellSlider.setMajorTickUnit((int) sellSlider.getMax() / 5);
        sellSlider.setMinorTickCount(5);
        sellSlider.setBlockIncrement((int) sellSlider.getMax() / 50);
        sellL.textProperty().bind(Bindings.format("Buy: %.0f Shares", sellSlider.valueProperty()));
        AnchorPane.setTopAnchor(sellSlider, 225.0);
        AnchorPane.setLeftAnchor(sellSlider, 5.0);
        graphWidget.getChildren().add(sellSlider);
        slidS.start();

        String buttonStyle = "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;";
        TextField sellOrderPrice = new TextField();
        sellOrderPrice.setPrefSize(100, 25);
        sellOrderPrice.setAlignment(Pos.CENTER);
        sellOrderPrice.setFont(aeroMI14);
        AnchorPane.setLeftAnchor(sellOrderPrice, 125.0);
        AnchorPane.setBottomAnchor(sellOrderPrice, 50.0);
        graphWidget.getChildren().add(sellOrderPrice);


        CheckBox sellOrderCB = new CheckBox("Sell Order");
        sellOrderCB.setSelected(false);
        sellOrderPrice.editableProperty().bind(sellOrderCB.selectedProperty());
        sellOrderCB.setOnAction(event -> {
            if (!sellOrderCB.isSelected()) sellOrderPrice.setText("");
        });
        sellOrderCB.setPrefSize(100, 25);
        sellOrderCB.setTextFill(Paint.valueOf("White"));
        sellOrderCB.setAlignment(Pos.CENTER);
        sellOrderCB.setFont(aeroMI14);
        AnchorPane.setLeftAnchor(sellOrderCB, 25.0);
        AnchorPane.setBottomAnchor(sellOrderCB, 50.0);
        graphWidget.getChildren().add(sellOrderCB);


        Button sellB = new Button("Sell");
        sellB.setFont(aeroMI14);
        sellB.setPrefSize(50, 20);
        sellB.setTextFill(Paint.valueOf("White"));
        sellB.setStyle(buttonStyle);
        sellB.setOnAction(event -> {
            String name = stock.textProperty().getValue();
            int quantity = (int) Math.floor(sellSlider.getValue());
            double pps = StockHistory.getPrice(name);

            if (sellOrderCB.isSelected()) {
                pps = Double.parseDouble(sellOrderPrice.getText().replaceAll("[^0-9.]", ""));
                StockManagement.setSellOrder(name, quantity, pps);
            } else {
                StockManagement.sellStock(name, quantity, pps);
            }
            sellOrderPrice.setText("");
            sellSlider.setValue(0);
            sellOrderCB.setSelected(false);
        });
        AnchorPane.setBottomAnchor(sellB, 5.0);
        AnchorPane.setRightAnchor(sellB, 5.0);
        graphWidget.getChildren().add(sellB);

        return graphWidget;
    }

    public void setOpacity(double opacity) {
        sellAnchorPane.setOpacity(opacity);
    }

    public void open() {
        if (!opened) {
            graphS.start();
            widS.start();
            opened = true;
        }
        sellAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), sellAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
    }

    public void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), sellAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            sellAnchorPane.setVisible(false);
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
