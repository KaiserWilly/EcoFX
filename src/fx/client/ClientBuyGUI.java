package fx.client;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created by james on 4/29/2016.
 */
public class ClientBuyGUI {
    public AnchorPane buyAnchorPane = new AnchorPane();
    public static AnchorPane stockWidget = new AnchorPane();
    Font aeroMI30 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 40);
    Font aeroMI24 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
    Font aeroM14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroM.ttf"), 14);
    public static ClientBuyTasks.graphService graphS = new ClientBuyTasks.graphService();
    public static XYChart.Series<Number, Number> markData = new XYChart.Series<>();
    DropShadow dsSmall = new DropShadow(2.0, 1.0, 1.0, Color.BLACK);
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
        ClientBuyTasks.widgetService widS = new ClientBuyTasks.widgetService();
        widS.start();
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


        Label buyL = new Label("Buy:");
        buyL.setFont(aeroMI20);
        buyL.setPrefSize(175, 23);
        buyL.setTextFill(Paint.valueOf("White"));
        buyL.setAlignment(Pos.BOTTOM_LEFT);
        buyL.setTextAlignment(TextAlignment.LEFT);
        AnchorPane.setTopAnchor(buyL, 205.0);
        AnchorPane.setLeftAnchor(buyL, 5.0);
        graphWidget.getChildren().add(buyL);

        Slider buy = new Slider();
        buy.setPrefSize(400, 50);
        buy.setMin(0);
        buy.setMax(3000000);
        buy.setValue(0);
        buy.setShowTickLabels(true);
        buy.setShowTickMarks(true);
        buy.setMajorTickUnit((int) buy.getMax() / 5);
        buy.setMinorTickCount(5);
        buy.setBlockIncrement((int) buy.getMax() / 50);
        AnchorPane.setTopAnchor(buy, 225.0);
        AnchorPane.setLeftAnchor(buy, 5.0);
        graphWidget.getChildren().add(buy);

        String buttonStyle = "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;";


        TextField buyPrice = new TextField();
        buyPrice.setPrefSize(100, 25);
        buyPrice.setAlignment(Pos.CENTER);
        buyPrice.setFont(aeroMI14);
        AnchorPane.setLeftAnchor(buyPrice, 125.0);
        AnchorPane.setBottomAnchor(buyPrice, 50.0);
        graphWidget.getChildren().add(buyPrice);


        CheckBox buyOrder = new CheckBox("Buy Order");
        buyOrder.setSelected(false);
        buyPrice.editableProperty().bind(buyOrder.selectedProperty());
        buyOrder.setOnAction(event -> {
            if (!buyOrder.isSelected()) buyPrice.setText("");
        });
        buyOrder.setPrefSize(100, 25);
        buyOrder.setTextFill(Paint.valueOf("White"));
        buyOrder.setAlignment(Pos.CENTER);
        buyOrder.setFont(aeroMI14);
        AnchorPane.setLeftAnchor(buyOrder, 25.0);
        AnchorPane.setBottomAnchor(buyOrder, 50.0);
        graphWidget.getChildren().add(buyOrder);


        Button buyB = new Button("Buy");
        buyB.setFont(aeroMI14);
        buyB.setPrefSize(50, 20);
        buyB.setTextFill(Paint.valueOf("White"));
        buyB.setStyle(buttonStyle);
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
