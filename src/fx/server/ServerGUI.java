package fx.server;

import fx.FrameGUI;
import fx.MenuSubGUI;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import rsc.Values;

import java.net.*;

/**
 * Created by james on 4/4/2016.
 */
public class ServerGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Stage baseStage;
    static Font aeroMI24 = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 24);
    static Font aeroMI18 = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
    static Font aeroMI12 = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 15);

    String seedS, maxPS, modeS;
    private Label console, secCount;
    DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.BLACK);
    DropShadow dsSmall = new DropShadow(2.0, 1.0, 1.0, Color.BLACK);
    private AnchorPane serverAnchorPane = new AnchorPane();
    private static ServerTasks.SECService secS = new ServerTasks.SECService();
    private static ServerTasks.consoleService conS = new ServerTasks.consoleService();
    private static controllerService troS = new controllerService();
    private static ServerTasks.serverService serS;
    public static ServerTasks.playerListService plaLiS = new ServerTasks.playerListService();
    private static ServerTasks.playerLabelService plaLaS = new ServerTasks.playerLabelService();
    public static initializerService iniS = new initializerService();
    public static ServerTasks.graphService graS = new ServerTasks.graphService();
    public static XYChart.Series<Number, Number> avgMarData = new XYChart.Series<>();
    public static LineChart<Number, Number> marketTrend;
    public static NumberAxis yAxis;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stack = new StackPane();
        stack.setPadding(new Insets(0, 0, 30, 0));
        stack.getChildren().add(serverPane());
        Scene scene = new Scene(stack, 1000, 600);
        scene.getStylesheets().add("rsc/StylesheetRoot.css");
        baseStage = primaryStage;
        baseStage.setScene(scene);
        baseStage.setTitle("Echo Economics v1.1");
        baseStage.setMaxHeight(600);
        baseStage.setMinHeight(600);
        baseStage.setMaxWidth(1000);
        baseStage.setMinWidth(1000);
        baseStage.show();
    }

    public AnchorPane serverPane() {
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
        marketTrend.getData().add(avgMarData);
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


        Label ipaL = null;
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
                    break;
                case "Pause":
                    pausePlay.setId("Start");
                    pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpausehover.png"))));
                    pausePlay.setOnMouseExited(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpause.png")))));
                    pausePlay.setOnMouseEntered(e -> pausePlay.setGraphic(new ImageView(new Image(ServerGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverpausehover.png")))));
                    break;

            }
        });
        pausePlay.setBackground(null);
        pausePlay.setBorder(null);
        pausePlay.setEffect(ds);
        AnchorPane.setLeftAnchor(pausePlay, 660.0);
        AnchorPane.setBottomAnchor(pausePlay, 103.0);
        serverAnchorPane.getChildren().add(pausePlay);

        Button endGame = new Button();
        endGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverendgame.png"))));
        endGame.setOnMouseExited(e -> endGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverendgame.png")))));
        endGame.setOnMouseEntered(e -> endGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serverendgamehover.png")))));
        endGame.setBackground(null);
        endGame.setBorder(null);
        endGame.setEffect(ds);
        AnchorPane.setLeftAnchor(endGame, 660.0);
        AnchorPane.setBottomAnchor(endGame, 23.0);
        serverAnchorPane.getChildren().add(endGame);

        Button saveGame = new Button();
        saveGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serversavegame.png"))));
        saveGame.setOnMouseExited(e -> saveGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serversavegame.png")))));
        saveGame.setOnMouseEntered(e -> saveGame.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/server/serversavegamehover.png")))));
        saveGame.setBackground(null);
        saveGame.setBorder(null);
        saveGame.setEffect(ds);
        AnchorPane.setLeftAnchor(saveGame, 660.0);
        AnchorPane.setBottomAnchor(saveGame, 63.0);
        serverAnchorPane.getChildren().add(saveGame);

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

    public static class initializerService extends Service<Void> {

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


    public static class controllerService extends Service<Void> {
//        int count = 0;

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
