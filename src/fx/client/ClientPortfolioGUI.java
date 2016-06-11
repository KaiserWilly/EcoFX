package fx.client;

import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Created by james on 4/29/2016.
 */
public class ClientPortfolioGUI {
    AnchorPane portfolioAnchorPane = new AnchorPane();

    public AnchorPane createPane() {
        portfolioAnchorPane.setPrefSize(980, 490);

        portfolioAnchorPane.getChildren().add(stockPortfolioPane());

        portfolioAnchorPane.managedProperty().bind(portfolioAnchorPane.visibleProperty());
        portfolioAnchorPane.setVisible(false);
        return portfolioAnchorPane;
    }

    public AnchorPane stockPortfolioPane() {
        String stockPPStyle = "-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: #444444;";
        AnchorPane stockPortfolioAnchorPane = new AnchorPane();
        stockPortfolioAnchorPane.setStyle(stockPPStyle);
        stockPortfolioAnchorPane.setPrefSize(480, 472);




        AnchorPane.setTopAnchor(stockPortfolioAnchorPane, 5.0);
        AnchorPane.setLeftAnchor(stockPortfolioAnchorPane, 5.0);
        return stockPortfolioAnchorPane;
    }

    public void setOpacity(double opacity) {
        portfolioAnchorPane.setOpacity(opacity);
    }

    public void open() {
        portfolioAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), portfolioAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
    }

    public void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), portfolioAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            portfolioAnchorPane.setVisible(false);
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
