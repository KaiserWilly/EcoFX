package fx.client;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import rsc.Values;

 /**
 * Created 4/2/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * ClientFrameGUI: Generates the frame of the client, including both header and footer.
 * Also acts as a frame for all client panes.
 */
public class ClientFrameGUI {
    private static String curScene = "Overview";
    private AnchorPane clientAnchorPane = new AnchorPane();
    private StackPane clientStack = new StackPane();
    static ClientOverviewGUI overview = new ClientOverviewGUI();
    static ClientPortfolioGUI portfolio = new ClientPortfolioGUI();
    static ClientBuyGUI buy = new ClientBuyGUI();
    static ClientSellGUI sell = new ClientSellGUI();
    private static ClientFrameTasks.ServerService servS;
    static ClientFrameTasks.CohService cohS = new ClientFrameTasks.CohService();
    static ClientFrameTasks.MessageService mesS = new ClientFrameTasks.MessageService();
    private AnchorPane overviewP, portfolioP, buyP, sellP;
    private Font aeroMI20 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 20);

    public AnchorPane clientPane() { //Header and footer of client pane
        ToolBar header = createToolBarHeader();
        AnchorPane.setTopAnchor(header, 0.0);
        AnchorPane.setLeftAnchor(header, 0.0);
        clientAnchorPane.getChildren().add(header);

        clientStack = createStack();
        AnchorPane.setTopAnchor(clientStack, 40.0);
        AnchorPane.setLeftAnchor(clientStack, 10.0);
        clientAnchorPane.getChildren().add(clientStack);

        ToolBar footer = createToolBarFooter();
        AnchorPane.setBottomAnchor(footer, -30.0);
        AnchorPane.setLeftAnchor(footer, 0.0);
        clientAnchorPane.getChildren().add(footer);

        clientAnchorPane.managedProperty().bind(clientAnchorPane.visibleProperty());
        clientAnchorPane.setVisible(false);
        return clientAnchorPane;
    }

    private StackPane createStack() {
        StackPane stack = new StackPane();
        stack.setPrefSize(980, 490);
        sellP = sell.createPane();
        stack.getChildren().add(sellP);
        buyP = buy.createPane();
        stack.getChildren().add(buyP);
        portfolioP = portfolio.createPane();
        stack.getChildren().add(portfolioP);
        overviewP = overview.createPane();
        stack.getChildren().add(overviewP);

        overview.setOpacity(0.0);
        portfolio.setOpacity(0.0);
        buy.setOpacity(0.0);
        sell.setOpacity(0.0);
        setScene("Overview");

        return stack;
    }

    public static void setScene(String scene) {// Change current pane of client
        switch (curScene) {
            case "Overview":
                overview.close(scene);
                break;
            case "Portfolio":
                portfolio.close(scene);
                break;
            case "Buy Stocks":
                buy.close(scene);
                break;
            case "Sell Stocks":
                sell.close(scene);
                break;
        }
        curScene = scene;
    }

    public void setOpacity(double opacity) {
        clientAnchorPane.setOpacity(opacity);
    }//Change opacuty of client pane

    public void open() {//Display client pane
        clientAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), clientAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
        servS = new ClientFrameTasks.ServerService(Values.ip);
        servS.start();
        cohS.start();
        mesS.start();
    }

    private ToolBar createToolBarHeader() {//Return header of client
        ToolBar bar = new ToolBar();
        bar.setId("header");
        bar.setStyle("-fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-background-color: #333333;");
        Label totalAssets = new Label();
        totalAssets.textProperty().bind(cohS.messageProperty());
        totalAssets.setPadding(new Insets(0, 0, 0, 20));
        totalAssets.setTextFill(Paint.valueOf("White"));
        totalAssets.setFont(aeroMI20);
        bar.getItems().add(totalAssets);

        bar.getItems().add(createSeparator());

        Label news = new Label();
        news.textProperty().bind(mesS.messageProperty());
        news.setPadding(new Insets(0, 20, 0, 0));
        news.setTextFill(Paint.valueOf("White"));
        news.setFont(aeroMI20);
        bar.getItems().add(news);

        bar.setPrefSize(1000, 20);
        bar.getStylesheets().add("rsc/StylesheetClient.css");

        return bar;
    }

    private ToolBar createToolBarFooter() {//Return footer of client
        ToolBar bar = new ToolBar();
        bar.setId("footer");
        HBox layout = new HBox();
        Font aeroMI14 = Font.loadFont(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 14);
        String buttonStyle = "-fx-base: #333333;";
        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(970, 25);

        String[] names = new String[]{
                "Overview",
                "Portfolio",
                null,
                "Buy Stocks",
                "Sell Stocks"
        };
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                Label logo = new Label();
                logo.setGraphic(new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/frame/toolbaricon-01.png"))));
                layout.getChildren().add(logo);
            } else {
                Button footTool = new Button(names[i]);
                footTool.setTextFill(Paint.valueOf("White"));
                footTool.setPrefSize(100, 25);
                footTool.setFont(aeroMI14);
                footTool.setStyle(buttonStyle);
                footTool.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-effect: null;" + buttonStyle);
                final int finalI = i;
                footTool.setOnAction(event -> setScene(names[finalI]));
                layout.getChildren().add(footTool);
            }

        }

        bar.getItems().add(layout);
        bar.setPrefSize(1000, 25);
        bar.getStylesheets().add("rsc/StylesheetClient.css");
        return bar;
    }

    private Label createSeparator() {//Create new separator
        Label sep = new Label();
        sep.setGraphic(new ImageView(new Image(ClientFrameGUI.class.getClassLoader().getResourceAsStream("rsc/client/frame/toolbarseparator-01.png"))));
        sep.setPadding(new Insets(0, 10, 0, 10));
        return sep;
    }

    static void resetWidgets() { //Force UI widgets to update
        ClientPortfolioGUI.histS.change = true;
        ClientPortfolioGUI.buyS.change = true;
        ClientPortfolioGUI.sellS.change = true;
        ClientPortfolioGUI.portS.change = true;
        ClientBuyGUI.slidS.change = true;
        ClientSellGUI.slidS.change = true;
        ClientSellGUI.widS.change = true;

    }


}
