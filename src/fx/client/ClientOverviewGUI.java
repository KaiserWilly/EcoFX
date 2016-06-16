package fx.client;

import fx.server.ServerGUI;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

/**
 * Created by james on 4/29/2016.
 */
public class ClientOverviewGUI {

    Font aeroMI24 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
    Font aeroM14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroM.ttf"), 14);
    Font aeroMI10 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    public AnchorPane overviewAnchorPane = new AnchorPane();
    public static XYChart.Series<Number, Number> avgMarData = new XYChart.Series<>();
    public static ClientOverviewTasks.graphService graphS = new ClientOverviewTasks.graphService();
    public static ClientOverviewTasks.tableService tabS = new ClientOverviewTasks.tableService();
    public boolean opened = false;
    public static ScrollPane tablePane;
    DropShadow dsSmall = new DropShadow(2.0, 1.0, 1.0, Color.BLACK);

    public AnchorPane createPane() {
        overviewAnchorPane.setPrefSize(980, 490);

        overviewAnchorPane.getChildren().add(graphPane());
        overviewAnchorPane.getChildren().add(rankPane());
        overviewAnchorPane.managedProperty().bind(overviewAnchorPane.visibleProperty());
        overviewAnchorPane.setVisible(false);
        return overviewAnchorPane;
    }

    public AnchorPane graphPane() {
        AnchorPane graphPane = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;";
        graphPane.setStyle(graphPaneStyle);
        graphPane.setPrefSize(450, 478);
        graphPane.getChildren().add(graphWidget());
        AnchorPane.setTopAnchor(graphPane, 5.0);
        AnchorPane.setRightAnchor(graphPane, 0.0);

        return graphPane;
    }

    public AnchorPane graphWidget() {
        AnchorPane graphWidget = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #333333;";
        graphWidget.setStyle(graphPaneStyle);
        graphWidget.setPrefSize(440, 250);
        AnchorPane.setTopAnchor(graphWidget, 5.0);
        AnchorPane.setRightAnchor(graphWidget, 5.0);

        HBox graphBox = new HBox();
        graphBox.setPrefSize(200, 30);
        graphBox.setAlignment(Pos.CENTER_LEFT);
        graphBox.setPadding(new Insets(0, 5, 0, 0));

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

        graphBox.getChildren().add(back);
        graphBox.getChildren().add(stock);
        graphBox.getChildren().add(forward);
        AnchorPane.setTopAnchor(graphBox, 5.0);
        AnchorPane.setLeftAnchor(graphBox, 5.0);
        graphWidget.getChildren().add(graphBox);

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
        marketTrend.setPrefSize(440, 180);
        marketTrend.setCreateSymbols(true);
        marketTrend.setLegendVisible(false);
        AnchorPane.setTopAnchor(marketTrend, 40.0);
        AnchorPane.setLeftAnchor(marketTrend, -5.0);
        graphWidget.getChildren().add(marketTrend);


        return graphWidget;
    }

    public AnchorPane rankPane() {
        AnchorPane rankPane = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;";
        rankPane.setStyle(graphPaneStyle);
        rankPane.setPrefSize(450, 478);

        Label rankL = new Label("Server Rankings");
        rankL.setPrefSize(450, 25);
        rankL.setTextFill(Paint.valueOf("White"));
        rankL.setFont(aeroMI20);
        rankL.setAlignment(Pos.CENTER);
        rankL.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(rankL, 0.0);
        AnchorPane.setLeftAnchor(rankL, -10.0);
        rankPane.getChildren().add(rankL);


        Label title = new Label("RANK");
        title.setFont(aeroMI10);
        title.setPrefSize(50, 10);
        title.setTextFill(Paint.valueOf("White"));
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(title, 25.0);
        AnchorPane.setLeftAnchor(title, 7.0);
        rankPane.getChildren().add(title);

        Label price = new Label("TRADES");
        price.setFont(aeroMI10);
        price.setPrefSize(50, 10);
        price.setTextFill(Paint.valueOf("White"));
        price.setAlignment(Pos.CENTER);
        price.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(price, 25.0);
        AnchorPane.setLeftAnchor(price, 67.0);
        rankPane.getChildren().add(price);

        Label change = new Label("PLAYER NAME");
        change.setFont(aeroMI10);
        change.setPrefSize(120, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, 25.0);
        AnchorPane.setLeftAnchor(change, 127.0);
        rankPane.getChildren().add(change);

        Label assets = new Label("ASSETS");
        assets.setFont(aeroMI10);
        assets.setPrefSize(125, 10);
        assets.setTextFill(Paint.valueOf("White"));
        assets.setAlignment(Pos.CENTER);
        assets.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(assets, 25.0);
        AnchorPane.setLeftAnchor(assets, 257.0);
        rankPane.getChildren().add(assets);


        tablePane = new ScrollPane();
        tablePane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        tablePane.setPrefSize(440, 420);
        tablePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tablePane.setFitToWidth(true);
        tablePane.setContent(new AnchorPane());
        AnchorPane.setTopAnchor(tablePane, 38.0);
        AnchorPane.setLeftAnchor(tablePane, 5.0);
        rankPane.getChildren().add(tablePane);


        AnchorPane.setTopAnchor(rankPane, 5.0);
        AnchorPane.setLeftAnchor(rankPane, 0.0);
        return rankPane;
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
        tabS.start();
    }
}
