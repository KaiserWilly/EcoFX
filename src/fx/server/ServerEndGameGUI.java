package fx.server;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created 5/13/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ServerEndGameGUI: Displays the final rankings at the end of the game, serverside.
 */
public class ServerEndGameGUI {
    private Font aeroMI20 = Font.loadFont(ServerEndGameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);
    private Font aeroMI10 = Font.loadFont(ServerEndGameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    private ServerEndGameTasks.tableService tabS = new ServerEndGameTasks.tableService();
    private AnchorPane endGameAnchorPane = new AnchorPane(), gameWidget = new AnchorPane();
    static ScrollPane endGamePane;

    public AnchorPane createPane() {//Basic and lowest level of pane
        endGameAnchorPane.setPrefSize(980, 490);
        endGameAnchorPane.getChildren().add(gamePane());
        endGameAnchorPane.managedProperty().bind(endGameAnchorPane.visibleProperty());
        endGameAnchorPane.setVisible(false);
        return endGameAnchorPane;
    }

    private AnchorPane gamePane() {//Pane displaying final rankings
        AnchorPane stockPane = new AnchorPane();
        stockPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;");
        stockPane.setPrefSize(450.0, 478.0);
        AnchorPane.setLeftAnchor(stockPane, 265.0);
        AnchorPane.setTopAnchor(stockPane, 50.0);

        Label rankL = new Label("Final Rankings");
        rankL.setPrefSize(450, 25);
        rankL.setTextFill(Paint.valueOf("White"));
        rankL.setFont(aeroMI20);
        rankL.setAlignment(Pos.CENTER);
        rankL.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(rankL, 0.0);
        AnchorPane.setLeftAnchor(rankL, -10.0);
        stockPane.getChildren().add(rankL);


        Label title = new Label("RANK");
        title.setFont(aeroMI10);
        title.setPrefSize(50, 10);
        title.setTextFill(Paint.valueOf("White"));
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(title, 25.0);
        AnchorPane.setLeftAnchor(title, 7.0);
        stockPane.getChildren().add(title);

        Label price = new Label("TRADES");
        price.setFont(aeroMI10);
        price.setPrefSize(50, 10);
        price.setTextFill(Paint.valueOf("White"));
        price.setAlignment(Pos.CENTER);
        price.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(price, 25.0);
        AnchorPane.setLeftAnchor(price, 67.0);
        stockPane.getChildren().add(price);

        Label change = new Label("PLAYER NAME");
        change.setFont(aeroMI10);
        change.setPrefSize(120, 10);
        change.setTextFill(Paint.valueOf("White"));
        change.setAlignment(Pos.CENTER);
        change.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(change, 25.0);
        AnchorPane.setLeftAnchor(change, 127.0);
        stockPane.getChildren().add(change);

        Label assets = new Label("ASSETS");
        assets.setFont(aeroMI10);
        assets.setPrefSize(125, 10);
        assets.setTextFill(Paint.valueOf("White"));
        assets.setAlignment(Pos.CENTER);
        assets.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(assets, 25.0);
        AnchorPane.setLeftAnchor(assets, 257.0);
        stockPane.getChildren().add(assets);

        endGamePane = new ScrollPane();
        endGamePane.getStylesheets().add("rsc/StylesheetScrollPane.css");
        endGamePane.setPrefSize(440.0, 420);
        endGamePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        endGamePane.setFitToWidth(true);
        endGamePane.setContent(portfolioWidget());
        AnchorPane.setTopAnchor(endGamePane, 38.0);
        AnchorPane.setLeftAnchor(endGamePane, 5.0);
        stockPane.getChildren().add(endGamePane);

        return stockPane;
    }

    private AnchorPane portfolioWidget() {//Original scrollpane contents
        gameWidget.setPrefSize(440, 1725);
        AnchorPane.setTopAnchor(gameWidget, 0.0);
        AnchorPane.setLeftAnchor(gameWidget, 0.0);
        return gameWidget;
    }


    public void setOpacity(double opacity) {
        endGameAnchorPane.setOpacity(opacity);
    }

    void open() {
        endGameAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), endGameAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.setOnFinished(event -> tabS.start());
        in.play();
    }

}
