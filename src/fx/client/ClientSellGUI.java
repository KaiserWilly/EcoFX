package fx.client;

import javafx.animation.FadeTransition;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Created by james on 4/29/2016.
 */
public class ClientSellGUI {
    public AnchorPane sellAnchorPane = new AnchorPane();
    public static AnchorPane stockWidget = new AnchorPane();

    public AnchorPane createPane() {
        sellAnchorPane.setPrefSize(980, 490);
//        sellAnchorPane.getChildren().add(stockPane());

        sellAnchorPane.managedProperty().bind(sellAnchorPane.visibleProperty());
        sellAnchorPane.setVisible(false);
        return sellAnchorPane;
    }

    public AnchorPane stockPane() {
        AnchorPane stockPane = new AnchorPane();
        String graphPaneStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;";
        stockPane.setStyle(graphPaneStyle);
        stockPane.setPrefSize(450.0, 478.0);

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
        ClientSellTasks.widgetService widS = new ClientSellTasks.widgetService();
        widS.start();
        return stockWidget;
    }

    public void setOpacity(double opacity) {
        sellAnchorPane.setOpacity(opacity);
    }

    public void open() {
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
                case "Buy":
                    ClientFrameGUI.buy.open();
                    break;
                case "Sell":
                    ClientFrameGUI.sell.open();
                    break;
            }
        });
        out.play();
    }
}
