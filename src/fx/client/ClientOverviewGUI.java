package fx.client;

import fx.server.ServerGUI;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rsc.Values;

import static fx.client.ClientBuyGUI.widS;

/**
 * Created by james on 4/29/2016.
 */
public class ClientOverviewGUI {

    Font aeroMI24 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
    Font aeroM14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroM.ttf"), 14);
    public AnchorPane overviewAnchorPane = new AnchorPane();
    public static XYChart.Series<Number, Number> avgMarData = new XYChart.Series<>();
    public static ClientOverviewTasks.graphService graphS = new ClientOverviewTasks.graphService();
    public boolean opened = false;
    DropShadow dsSmall = new DropShadow(2.0, 1.0, 1.0, Color.BLACK);

    public AnchorPane createPane() {
        overviewAnchorPane.setPrefSize(980, 490);

        overviewAnchorPane.getChildren().add(graphPane());
        overviewAnchorPane.getChildren().add(rankPane());
        overviewAnchorPane.getChildren().add(holdingsPane());
        overviewAnchorPane.managedProperty().bind(overviewAnchorPane.visibleProperty());
        overviewAnchorPane.setVisible(false);
        return overviewAnchorPane;
    }

    public AnchorPane graphPane() {
        AnchorPane graphPane = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; ";
        graphPane.setStyle(graphPaneStyle);
        graphPane.setPrefSize(488, 350);

        HBox graphWidget = new HBox();
        graphWidget.setPrefSize(200, 30);
        graphWidget.setAlignment(Pos.CENTER_LEFT);
        graphWidget.setPadding(new Insets(0, 5, 0, 0));

        Button back = new Button();
        back.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/general/graphwidgetbuttonback.png"))));
        back.setOnMouseExited(e -> back.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/general/graphwidgetbuttonback.png")))));
        back.setOnMouseEntered(e -> back.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/general/graphwidgetbuttonbackhover.png")))));
        back.setOnAction(event -> Values.currentStockName = Values.getStockBackward());
        back.setBackground(null);
        back.setBorder(null);
        back.setEffect(dsSmall);

        Button forward = new Button();
        forward.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/general/graphwidgetbuttonforward.png"))));
        forward.setOnMouseExited(e -> forward.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/general/graphwidgetbuttonforward.png")))));
        forward.setOnMouseEntered(e -> forward.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/general/graphwidgetbuttonforwardhover.png")))));
        forward.setOnAction(event -> Values.currentStockName = Values.getStockForward());
        forward.setBackground(null);
        forward.setBorder(null);
        forward.setEffect(dsSmall);

        Label stock = new Label();
        stock.setFont(aeroMI24);
        stock.setTextFill(Paint.valueOf("White"));
        stock.textProperty().bind(graphS.messageProperty());
        stock.setPrefSize(100, 30);
        stock.setTextAlignment(TextAlignment.CENTER);
        stock.setAlignment(Pos.CENTER);

        graphWidget.getChildren().add(back);
        graphWidget.getChildren().add(stock);
        graphWidget.getChildren().add(forward);
        AnchorPane.setTopAnchor(graphWidget, 5.0);
        AnchorPane.setRightAnchor(graphWidget, 280.0);
        graphPane.getChildren().add(graphWidget);


        Label shares = new Label("Shares Available: 1,200,000");
//        shares.setStyle("-fx-background-color: #ffffff;");
        shares.setFont(aeroM14);
        shares.setPrefSize(175, 23);
        shares.setTextFill(Paint.valueOf("White"));
        shares.setAlignment(Pos.BOTTOM_LEFT);
        shares.setTextAlignment(TextAlignment.LEFT);
        AnchorPane.setTopAnchor(shares, 27.0);
        AnchorPane.setLeftAnchor(shares, 15.0);
        graphPane.getChildren().add(shares);


        NumberAxis xAxis = new NumberAxis(0.0, 30.0, 2.0);
        NumberAxis yAxis = new NumberAxis(25.0, 30.0, 1.0);
        xAxis.setLabel("Time (sec)");
        yAxis.setLabel("Market Value ($)");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        LineChart<Number, Number> marketTrend = new LineChart<>(xAxis, yAxis);
        marketTrend.setAnimated(false);
        marketTrend.getStylesheets().add("rsc/StylesheetCharts.css");
        marketTrend.getData().add(avgMarData);
        marketTrend.setPrefSize(470, 180);
        marketTrend.setCreateSymbols(true);
        marketTrend.setLegendVisible(false);
        AnchorPane.setTopAnchor(marketTrend, 40.0);
        AnchorPane.setLeftAnchor(marketTrend, -5.0);
        graphPane.getChildren().add(marketTrend);


        Label buyL = new Label("Buy:");
        buyL.setFont(aeroMI20);
        buyL.setPrefSize(175, 23);
        buyL.setTextFill(Paint.valueOf("White"));
        buyL.setAlignment(Pos.BOTTOM_LEFT);
        buyL.setTextAlignment(TextAlignment.LEFT);
        AnchorPane.setTopAnchor(buyL, 210.0);
        AnchorPane.setLeftAnchor(buyL, 5.0);
        graphPane.getChildren().add(buyL);

        Slider buy = new Slider();
        buy.setPrefSize(450, 50);
        buy.setMin(0);
        buy.setMax(1200000);
        buy.setValue(0);
        buy.setShowTickLabels(true);
        buy.setShowTickMarks(true);
        buy.setMajorTickUnit((int) buy.getMax() / 5);
        buy.setMinorTickCount(5);
        buy.setBlockIncrement((int) buy.getMax() / 50);
        AnchorPane.setTopAnchor(buy, 225.0);
        AnchorPane.setLeftAnchor(buy, 5.0);
        graphPane.getChildren().add(buy);


        Label sellL = new Label("Sell:");
        sellL.setFont(aeroMI20);
        sellL.setPrefSize(175, 23);
        sellL.setTextFill(Paint.valueOf("White"));
        sellL.setAlignment(Pos.BOTTOM_LEFT);
        sellL.setTextAlignment(TextAlignment.LEFT);
        AnchorPane.setTopAnchor(sellL, 260.0);
        AnchorPane.setLeftAnchor(sellL, 5.0);
        graphPane.getChildren().add(sellL);


        Slider sell = new Slider();
        sell.setPrefSize(450, 50);
        sell.setMin(0);
        sell.setMax(1200000);
        sell.setValue(0);
        sell.setShowTickLabels(true);
        sell.setShowTickMarks(true);
        sell.setMajorTickUnit((int) buy.getMax() / 5);
        sell.setMinorTickCount(5);
        sell.setBlockIncrement((int) buy.getMax() / 50);
        sell.setDisable(true);
        AnchorPane.setTopAnchor(sell, 275.0);
        AnchorPane.setLeftAnchor(sell, 5.0);
        graphPane.getChildren().add(sell);

        String buttonStyle = "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null; -fx-base: #444444;";

        Button sellB = new Button("Sell");
        sellB.setFont(aeroMI14);
        sellB.setPrefSize(50, 20);
        sellB.setTextFill(Paint.valueOf("White"));
        sellB.setStyle(buttonStyle);
        sellB.setDisable(true);
        AnchorPane.setBottomAnchor(sellB, 5.0);
        AnchorPane.setRightAnchor(sellB, 15.0);
        graphPane.getChildren().add(sellB);


        Button buyB = new Button("Buy");
        buyB.setFont(aeroMI14);
        buyB.setPrefSize(50, 20);
        buyB.setTextFill(Paint.valueOf("White"));
        buyB.setStyle(buttonStyle);
        AnchorPane.setBottomAnchor(buyB, 5.0);
        AnchorPane.setRightAnchor(buyB, 75.0);
        graphPane.getChildren().add(buyB);


        AnchorPane.setTopAnchor(graphPane, 5.0);
        AnchorPane.setRightAnchor(graphPane, 0.0);
        return graphPane;
    }

    public AnchorPane rankPane() {
        AnchorPane rankPane = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; ";
        rankPane.setStyle(graphPaneStyle);
        rankPane.setPrefSize(488, 350);

        ObservableList<playerRank> data = FXCollections.observableArrayList(
                new playerRank("1", "337", "The Kaiser", "$125,300", "0.9%"),
                new playerRank("2", "302", "Mandalute", "$122,900", "0.2%"),
                new playerRank("3", "254", "TheGamerzRevolution", "$111,600", "0.1%"),
                new playerRank("4", "113", "E-Dubble", "$99,300", "-0.3%")
        );

        String[] columnNames = new String[]{"Rank", "Trades", "Name", "Assets", "Asset Change over 30s"};
        String[] propertyValue = new String[]{"rank", "trades", "name", "assets", "assetChange"};
        int[] columnWidth = new int[]{50, 50, 125, 75, 163};

        TableView<playerRank> rankView = new TableView<>();
        rankView.setItems(data);

        for (int i = 0; i < 5; i++) {
            TableColumn column = new TableColumn();
            column.setText(columnNames[i]);
            column.setMinWidth(columnWidth[i]);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory(propertyValue[i]));
            rankView.getColumns().add(column);
        }
        rankView.setPrefSize(470, 300);
        rankView.setEditable(false);
        rankView.getStylesheets().add("rsc/StylesheetTable.css");
        AnchorPane.setTopAnchor(rankView, 35.0);
        AnchorPane.setLeftAnchor(rankView, 7.0);
        rankPane.getChildren().add(rankView);

        Label rankL = new Label("Server Rankings");
        rankL.setPrefSize(480, 25);
        rankL.setTextFill(Paint.valueOf("White"));
        rankL.setFont(aeroMI20);
        rankL.setAlignment(Pos.CENTER);
        rankL.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(rankL, 5.0);
        AnchorPane.setLeftAnchor(rankL, 5.0);
        rankPane.getChildren().add(rankL);

        AnchorPane.setTopAnchor(rankPane, 5.0);
        AnchorPane.setLeftAnchor(rankPane, 0.0);
        return rankPane;
    }

    public AnchorPane holdingsPane() {
        AnchorPane holdingsPane = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;";
        holdingsPane.setStyle(graphPaneStyle);
        holdingsPane.setPrefSize(980, 117);


        AnchorPane.setTopAnchor(holdingsPane, 360.0);
        AnchorPane.setRightAnchor(holdingsPane, 0.0);
        return holdingsPane;
    }

    public void setOpacity(double opacity) {
        overviewAnchorPane.setOpacity(opacity);
    }

    public void open() {
        overviewAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), overviewAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.setOnFinished(event -> {
            if (!opened) {
                opened = true;
                startServices();
            }
        });
        in.play();
    }

    public void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), overviewAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            overviewAnchorPane.setVisible(false);
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

    public void startServices() {
        graphS.start();
    }

    public static class playerRank {
        private StringProperty rank;
        private StringProperty trades;
        private StringProperty name;
        private StringProperty assets;
        private StringProperty assetChange;

        public playerRank(String rank, String trades, String name, String assets, String assetChange) {
            this.rank = new SimpleStringProperty(rank);
            this.trades = new SimpleStringProperty(trades);
            this.name = new SimpleStringProperty(name);
            this.assets = new SimpleStringProperty(assets);
            this.assetChange = new SimpleStringProperty(assetChange);
        }

        public StringProperty rankProperty() {
            return rank;
        }

        public StringProperty tradesProperty() {
            return trades;
        }

        public StringProperty nameProperty() {
            return name;
        }

        public StringProperty assetsProperty() {
            return assets;
        }

        public StringProperty assetChangeProperty() {
            return assetChange;
        }
    }

}
