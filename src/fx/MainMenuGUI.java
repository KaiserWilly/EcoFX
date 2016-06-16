package fx;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by james on 4/5/2016.
 */
public class MainMenuGUI {


    VBox buttonBox = new VBox();
    VBox logoBox = new VBox();
    AnchorPane menuAnchorPane = new AnchorPane();
    DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.BLACK), logoE = new DropShadow(5.0, 1.0, 1.0, Color.BLACK);
    static VBox subMenu;
    static Label statBorder;
    static Label logo;
    static Button returnButton;
    static ParallelTransition mainMenuTo = new ParallelTransition();
    static ParallelTransition mainMenuFrom = new ParallelTransition();
    static Rectangle subRec;
    static Double subMenuOpacity = 0.2;
    static Button[] menuButt;
    static Scene scene;

    public AnchorPane menuPane() {

        buttonBox.setPadding(new Insets(8));
        buttonBox.setSpacing(1);
        AnchorPane.setLeftAnchor(buttonBox, 5.0);
        AnchorPane.setBottomAnchor(buttonBox, 30.0);

        menuButt = new Button[3];
        for (int i = 0; i < menuButt.length; i++) {
            menuButt[i] = createMenuButton(i);
            VBox.setMargin(menuButt[i], new Insets(0, 0, 0, 4));
            buttonBox.getChildren().add(menuButt[i]);
        }


        logo = new Label();
        logo.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/main/EcoBanner.png"))));
//        logo.setEffect(logoE);
        VBox.setMargin(logo, new Insets(4, 4, 0, 0));
        logoBox.getChildren().add(logo);
        AnchorPane.setRightAnchor(logoBox, 150.0);
        AnchorPane.setTopAnchor(logoBox, 75.0);

        statBorder = new Label();
        statBorder.setEffect(logoE);
        statBorder.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/main/menuSplash-01.png"))));
        AnchorPane.setRightAnchor(statBorder, 32.0);
        AnchorPane.setBottomAnchor(statBorder, 17.0);

        subRec = new Rectangle(1000, 600, Paint.valueOf("Black"));
        subRec.setOpacity(0.0);
        AnchorPane.setLeftAnchor(subRec, 0.0);
        AnchorPane.setTopAnchor(subRec, 0.0);

        menuAnchorPane.getChildren().add(subRec);
        menuAnchorPane.getChildren().add(buttonBox);
        menuAnchorPane.getChildren().add(logoBox);
        menuAnchorPane.getChildren().add(statBorder);
        menuAnchorPane.managedProperty().bind(menuAnchorPane.visibleProperty());
        menuAnchorPane.setVisible(false);
        return menuAnchorPane;
    }

    public Button createMenuButton(int index) {
        String[] menuButtonPaths = new String[]{
                "rsc/menu/main/menuplaybutton.png",
                "rsc/menu/main/menuhostbutton.png",
                "rsc/menu/main/menucreditsbutton.png"
        };
        String[] menuButtHoverPaths = new String[]{
                "rsc/menu/main/menuplaybuttonhover.png",
                "rsc/menu/main/menuhostbuttonhover.png",
                "rsc/menu/main/menucreditsbuttonhover.png"
        };
        String[] menuButtNames = new String[]{
                "Play",
                "Host",
                "Credits"
        };


        Button menuButtTempate = new Button();
        menuButtTempate.setEffect(ds);
        menuButtTempate.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream(menuButtonPaths[index]))));
        menuButtTempate.setBackground(null);
        menuButtTempate.setPrefSize(313, 44);
        menuButtTempate.setBorder(null);
        menuButtTempate.setOnAction(e -> handleButtonClick(menuButtNames[index]));
        menuButtTempate.setOnMouseExited(e -> menuButtTempate.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream(menuButtonPaths[index])))));
        menuButtTempate.setOnMouseEntered(e -> menuButtTempate.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream(menuButtHoverPaths[index])))));
        return menuButtTempate;
    }

    public void handleButtonClick(String src) {
        mainMenuTo = new ParallelTransition();

        returnButton = new Button();
        returnButton.setOpacity(0.0);
        returnButton.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/main/submenureturnbutton.png"))));
        returnButton.setOnAction(e -> {
            mainMenuFrom = new ParallelTransition();
            MainMenuGUI.transitionMenuFrom();
            mainMenuFrom.play();
            setLayersFrom();
            menuAnchorPane.getChildren().remove(returnButton);
        });
        returnButton.setOnMouseExited(e -> returnButton.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/main/submenureturnbutton.png")))));
        returnButton.setOnMouseEntered(e -> returnButton.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/main/submenureturnbuttonhover.png")))));
        returnButton.setBackground(null);
        returnButton.setBorder(null);
        returnButton.setEffect(ds);
        AnchorPane.setLeftAnchor(returnButton, 370.0);
        AnchorPane.setBottomAnchor(returnButton, 25.0);
        menuAnchorPane.getChildren().add(returnButton);
        returnButton.toBack();

        subMenu = MenuSubGUI.getSubmenu(src);
        AnchorPane.setLeftAnchor(subMenu, 250.0);
        AnchorPane.setTopAnchor(subMenu, 25.0);
        menuAnchorPane.getChildren().add(subMenu);
        subMenu.toBack();

        System.out.println("Oh no! You pushed the " + src + " button!");
        MainMenuGUI.transitionMenuTo(true);
        setLayersTo();
        mainMenuTo.play();

    }

    public static void transitionMenuTo(Boolean returnButt) {
        FadeTransition subMenuBackTo = new FadeTransition(Duration.millis(250), subRec);
        subMenuBackTo.setFromValue(0.0);
        subMenuBackTo.setToValue(subMenuOpacity);
        subMenuBackTo.setCycleCount(1);
        subMenuBackTo.setAutoReverse(false);
        mainMenuTo.getChildren().add(subMenuBackTo);

        FadeTransition menuButtTo;
        for (Button aMenuButt : menuButt) { // Add button transitions
            menuButtTo = new FadeTransition(Duration.millis(250), aMenuButt);
            menuButtTo.setFromValue(1.0);
            menuButtTo.setToValue(0.0);
            menuButtTo.setCycleCount(1);
            menuButtTo.setAutoReverse(false);
            mainMenuTo.getChildren().add(menuButtTo);
        }
        FadeTransition menuSplashTo = new FadeTransition(Duration.millis(250), statBorder);
        menuSplashTo.setFromValue(1.0);
        menuSplashTo.setToValue(0.0);
        menuSplashTo.setCycleCount(1);
        menuSplashTo.setAutoReverse(false);
        mainMenuTo.getChildren().add(menuSplashTo);

        FadeTransition logoTo = new FadeTransition(Duration.millis(250), logo);
        logoTo.setFromValue(1.0);
        logoTo.setToValue(0.0);
        logoTo.setCycleCount(1);
        logoTo.setAutoReverse(false);
        mainMenuTo.getChildren().add(logoTo);

        FadeTransition vBoxTo = new FadeTransition(Duration.millis(250), subMenu);
        vBoxTo.setFromValue(0.0);
        vBoxTo.setToValue(1.0);
        vBoxTo.setCycleCount(1);
        vBoxTo.setAutoReverse(false);
        mainMenuTo.getChildren().add(vBoxTo);

        if (returnButt) {
            FadeTransition returnButtonTo = new FadeTransition(Duration.millis(250), returnButton);
            returnButtonTo.setFromValue(0.0);
            returnButtonTo.setToValue(1.0);
            returnButtonTo.setCycleCount(1);
            returnButtonTo.setAutoReverse(false);
            mainMenuTo.getChildren().add(returnButtonTo);
        }
    }

    public static void transitionMenuFrom() {
        FadeTransition subMenuBackFrom = new FadeTransition(Duration.millis(250), subRec);
        subMenuBackFrom.setFromValue(subMenuOpacity);
        subMenuBackFrom.setToValue(0.0);
        subMenuBackFrom.setCycleCount(1);
        subMenuBackFrom.setAutoReverse(false);
        mainMenuFrom.getChildren().add(subMenuBackFrom);

        FadeTransition menuButtFrom;
        for (Button aMenuButt : menuButt) { // Add button transitions
            menuButtFrom = new FadeTransition(Duration.millis(250), aMenuButt);
            menuButtFrom.setFromValue(.0);
            menuButtFrom.setToValue(1.0);
            menuButtFrom.setCycleCount(1);
            menuButtFrom.setAutoReverse(false);
            mainMenuFrom.getChildren().add(menuButtFrom);
        }
        FadeTransition menuSplashFrom = new FadeTransition(Duration.millis(250), statBorder);
        menuSplashFrom.setFromValue(0.0);
        menuSplashFrom.setToValue(1.0);
        menuSplashFrom.setCycleCount(1);
        menuSplashFrom.setAutoReverse(false);
        mainMenuFrom.getChildren().add(menuSplashFrom);

        FadeTransition logoFrom = new FadeTransition(Duration.millis(250), logo);
        logoFrom.setFromValue(0.0);
        logoFrom.setToValue(1.0);
        logoFrom.setCycleCount(1);
        logoFrom.setAutoReverse(false);
        mainMenuFrom.getChildren().add(logoFrom);

        FadeTransition vBoxFrom = new FadeTransition(Duration.millis(250), subMenu);
        vBoxFrom.setFromValue(1.0);
        vBoxFrom.setToValue(0.0);
        vBoxFrom.setCycleCount(1);
        vBoxFrom.setAutoReverse(false);
        mainMenuFrom.getChildren().add(vBoxFrom);

        FadeTransition returnButtonFrom = new FadeTransition(Duration.millis(250), returnButton);
        returnButtonFrom.setFromValue(1.0);
        returnButtonFrom.setToValue(0.0);
        returnButtonFrom.setCycleCount(1);
        returnButtonFrom.setAutoReverse(false);
        mainMenuFrom.getChildren().add(returnButtonFrom);
    }

    public void setLayersTo() {
        subRec.toFront();
        subMenu.toFront();
        returnButton.toFront();
    }

    public void setLayersFrom() {
        subMenu.toBack();
        returnButton.toBack();
        subRec.toBack();
    }

    public void setSceneStyle(String style) {
        switch (style) {
            case "Normal":
                try {
                    FrameGUI.scene.getStylesheets().remove("rsc/StylesheetMLG.css");
                } catch (Exception e) {
                    System.out.println("No other stylesheet detected!");
                }
                FrameGUI.scene.getStylesheets().add("rsc/StylesheetRoot.css");
                break;
            case "420":
                try {
                    FrameGUI.scene.getStylesheets().remove("rsc/StylesheetRoot.css");
                } catch (Exception e) {
                    System.out.println("No other stylesheet detected!");
                }
                FrameGUI.scene.getStylesheets().add("rsc/StylesheetMLG.css");
                break;

        }


    }

    public void setOpacity(double opacity) {
        menuAnchorPane.setOpacity(opacity);
    }

    public void close(String newPane) {
        FadeTransition out = new FadeTransition(Duration.millis(250), menuAnchorPane);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        out.setOnFinished(event -> {
            menuAnchorPane.setVisible(false);
            switch (newPane) {
                case "Menu":
                    FrameGUI.menu.open();
                    break;
                case "Server":
                    FrameGUI.server.open();
                    break;
                case "Client":
                    FrameGUI.client.open();
                    break;
            }
        });
        out.play();
    }


    public void open() {
        menuAnchorPane.setVisible(true);
        FadeTransition in = new FadeTransition(Duration.millis(250), menuAnchorPane);
        in.setFromValue(0.0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.play();
    }
}
