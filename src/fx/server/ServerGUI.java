package fx.server;

import fx.FrameGUI;
import javafx.animation.FadeTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import server.ServerTimer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created 4/28/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ServerGUI: Diplaying and managing the UI of the server
 */
public class ServerGUI {

    private static Font aeroMI24 = Font.loadFont(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    private static Font aeroMI18 = Font.loadFont(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
    private static Font aeroMI12 = Font.loadFont(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 15);

    private Label console, secCount;
    private DropShadow ds;
    private DropShadow dsSmall = new DropShadow(2.0, 1.0, 1.0, Color.BLACK);
    private AnchorPane serverAnchorPane = new AnchorPane();
    private static ServerTasks.SECService secS = new ServerTasks.SECService();
    private static ServerTasks.consoleService conS = new ServerTasks.consoleService();
    private static controllerService troS = new controllerService();
    private static ServerTasks.serverService serS;
    private static ServerTasks.playerListService plaLiS = new ServerTasks.playerListService();
    private static ServerTasks.playerLabelService plaLaS = new ServerTasks.playerLabelService();
    private static initializerService iniS = new initializerService();
    private static ServerTasks.graphService graS = new ServerTasks.graphService();
    static XYChart.Series<Number, Number> markData = new XYChart.Series<>();
    private static LineChart<Number, Number> marketTrend;
    private static NumberAxis yAxis;

    public ServerGUI() {
        ds = new DropShadow(5.0, 3.0, 3.0, Color.BLACK);
    }

    public AnchorPane serverPane() {//Base layer of the server UI
        serverAnchorPane.setPadding(new Insets(0, 0, 0, 0));

        console = new Label();
        console.setTextAlignment(TextAlignment.LEFT);
        console.setAlignment(Pos.TOP_LEFT);
        console.setFont(aeroMI12);
        console.setPrefSize(325, 480);
        console.setStyle("-fx-background-color: white;");
        console.textProperty().bind(conS.messageProperty());
        AnchorPane.setTopAnchor(console, 25.0);
        AnchorPane.setLeftAnchor(console, 10.0);
        serverAnchorPane.getChildren().add(console);

        NumberAxis xAxis = new NumberAxis(0.0, 30.0, 2.0);
        yAxis = new NumberAxis(25.0, 30.0, 1.0);
        xAxis.setLabel("Time (sec)");
        yAxis.setLabel("Market Value ($)");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        marketTrend = new LineChart<>(xAxis, yAxis);
        marketTrend.setAnimated(false);
        marketTrend.getStylesheets().add("rsc/StylesheetCharts.css");
        marketTrend.getData().add(markData);
        marketTrend.setPrefSize(590, 200);
        marketTrend.setCreateSymbols(true);
        marketTrend.setLegendVisible(false);
        AnchorPane.setTopAnchor(marketTrend, 25.0);
        AnchorPane.setLeftAnchor(marketTrend, 390.0);
        serverAnchorPane.getChildren().add(marketTrend);

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
        stock.textProperty().bind(graS.messageProperty());
        stock.setPrefSize(100, 30);
        stock.setTextAlignment(TextAlignment.CENTER);
        stock.setAlignment(Pos.CENTER);

        graphWidget.getChildren().add(back);
        graphWidget.getChildren().add(stock);
        graphWidget.getChildren().add(forward);
        AnchorPane.setTopAnchor(graphWidget, 5.0);
        AnchorPane.setRightAnchor(graphWidget, 340.0);
        serverAnchorPane.getChildren().add(graphWidget);

        Label widgetL = new Label("Market Data Over 30s");
        widgetL.setFont(aeroMI18);
        widgetL.setTextFill(Paint.valueOf("White"));
        widgetL.setPrefSize(200, 30);
        widgetL.setTextAlignment(TextAlignment.CENTER);
        widgetL.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(widgetL, 7.0);
        AnchorPane.setRightAnchor(widgetL, 25.0);
        serverAnchorPane.getChildren().add(widgetL);

        Label playL = new Label("");
        playL.setFont(aeroMI18);
        playL.setTextFill(Paint.valueOf("White"));
        playL.textProperty().bind(plaLaS.messageProperty());
        playL.setPrefSize(200, 30);
        playL.setTextAlignment(TextAlignment.CENTER);
        playL.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(playL, 220.0);
        AnchorPane.setLeftAnchor(playL, 410.0);
        serverAnchorPane.getChildren().add(playL);

        Label playerList = new Label();
        playerList.setTextAlignment(TextAlignment.CENTER);
        playerList.textProperty().bind(plaLiS.messageProperty());
        playerList.setAlignment(Pos.TOP_CENTER);
        playerList.setFont(aeroMI18);
        playerList.setPrefSize(200, 255);
        playerList.setStyle("-fx-background-color: white;");
        AnchorPane.setTopAnchor(playerList, 250.0);
        AnchorPane.setLeftAnchor(playerList, 410.0);
        serverAnchorPane.getChildren().add(playerList);


        Label ipaL;
        try {
            ipaL = new Label("Server Host IP: " + InetAddress.getLocalHost().getHostAddress());
            ipaL.setFont(aeroMI18);
            ipaL.setTextFill(Paint.valueOf("White"));
            ipaL.setPrefSize(200, 30);
            ipaL.setAlignment(Pos.CENTER);
        } catch (UnknownHostException e) {
            System.out.println("Can't synthesize host IP address!");
            ipaL = new Label("Host IP address not available...");
            ipaL.setFont(aeroMI18);
            ipaL.setPrefSize(200, 25);
            ipaL.setAlignment(Pos.CENTER);
        }
        ipaL.setAlignment(Pos.CENTER_LEFT);
        ipaL.setTextAlignment(TextAlignment.LEFT);
        AnchorPane.setBottomAnchor(ipaL, 0.0);
        AnchorPane.setLeftAnchor(ipaL, 10.0);
        serverAnchorPane.getChildren().add(ipaL);

        secCount = new Label();
        secCount.setFont(aeroMI18);
        secCount.setTextFill(Paint.valueOf("White"));
        secCount.setTextAlignment(TextAlignment.RIGHT);
        secCount.setAlignment(Pos.CENTER_RIGHT);
        secCount.textProperty().bind(secS.messageProperty());
        secCount.setPrefSize(150, 30);
        AnchorPane.setBottomAnchor(secCount, 0.0);
        AnchorPane.setLeftAnchor(secCount, 180.0);
        serverAnchorPane.getChildren().add(secCount);


        Button pausePlay = new Button();
        pausePlay.setId("Start");
        pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpause.png"))));
        pausePlay.setOnMouseExited(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpause.png")))));
        pausePlay.setOnMouseEntered(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpausehover.png")))));
        pausePlay.setOnAction(event1 -> {
            switch (pausePlay.getId()) {
                case "Start":
                    pausePlay.setId("Pause");
                    pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverplayhover.png"))));
                    pausePlay.setOnMouseExited(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverplay.png")))));
                    pausePlay.setOnMouseEntered(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverplayhover.png")))));
                    ServerTimer.pauseTimer();
                    break;
                case "Pause":
                    pausePlay.setId("Start");
                    pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpausehover.png"))));
                    pausePlay.setOnMouseExited(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpause.png")))));
                    pausePlay.setOnMouseEntered(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpausehover.png")))));
                    ServerTimer.resumeTimer();

                    break;

            }
        });
        pausePlay.setBackground(null);
        pausePlay.setBorder(null);
        pausePlay.setEffect(ds);
        AnchorPane.setLeftAnchor(pausePlay, 660.0);
        AnchorPane.setBottomAnchor(pausePlay, 63.0);
        serverAnchorPane.getChildren().add(pausePlay);

        Button endGame = new Button();
        endGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverendgame.png"))));
        endGame.setOnMouseExited(e -> endGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverendgame.png")))));
        endGame.setOnMouseEntered(e -> endGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverendgamehover.png")))));
        endGame.setOnAction(event -> {
            ServerTimer.stopTimer();
            FrameGUI.setScene("End");
        });
        endGame.setBackground(null);
        endGame.setBorder(null);
        endGame.setEffect(ds);
        AnchorPane.setLeftAnchor(endGame, 660.0);
        AnchorPane.setBottomAnchor(endGame, 23.0);
        serverAnchorPane.getChildren().add(endGame);

        serverAnchorPane.managedProperty().bind(serverAnchorPane.visibleProperty());
        serverAnchorPane.setVisible(false);
        return serverAnchorPane;
    }


    public void setOpacity(double opacity) {
        serverAnchorPane.setOpacity(opacity);
    }

    public void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), serverAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            serverAnchorPane.setVisible(false);
            switch (newPane) {
                case "Menu":
                    FrameGUI.menu.open();
                    break;
                case "End":
                    FrameGUI.end.open();
                    break;
            }
        });
        out.play();
    }

    public void open() {
        serverAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), serverAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.setOnFinished(event -> {
            serS = new ServerTasks.serverService();
            serS.start();
            iniS = new initializerService();
            iniS.start();
        });
        in.play();
    }

    private static class initializerService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    troS = new ServerGUI.controllerService();
                    troS.start();
                    return null;
                }
            };
        }
    }


    private static class controllerService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    conS.start();
                    secS.start();
                    plaLiS.start();
                    plaLaS.start();
                    graS.start();
                    return null;
                }
            };
        }
    }
}
