package fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import rsc.PlayerManagement;
import rsc.Values;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by james on 4/2/2016.
 */
public class MenuSubGUI {
    static DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.BLACK);
    static Font aeroMI18 = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 18);
    static Font aeroMI10 = Font.loadFont(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/fonts/aeroMI.ttf"), 10);
    public static TextField seed;
    public static String modeSettings = "con", modeServer = "con";

    public static VBox getSubmenu(String src) {
        Label heading;
        VBox base = new VBox();
        base.setPrefSize(500, 550);
        base.setPadding(new Insets(20, 0, 0, 0));
        base.setSpacing(20);
        switch (src) {
            case "Play":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/play/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));

                HBox ipBox = new HBox();
                ipBox.setPrefSize(250, 30);
                ipBox.setSpacing(5);
                Label ipL = new Label("IP Address:");
                ipL.setFont(aeroMI18);
                ipL.setAlignment(Pos.CENTER_RIGHT);
                ipL.setPrefSize(145, 30);
                ipL.setTextFill(Paint.valueOf("White"));
                ipBox.getChildren().add(ipL);
                TextField ip = new TextField();
                ip.setPrefSize(150, 30);
                ip.setFont(aeroMI18);
                ip.setAlignment(Pos.CENTER);
                ipBox.getChildren().add(ip);
                base.getChildren().add(ipBox);
                VBox.setMargin(ipBox, new Insets(0, 0, 0, 65));


                HBox usernameBox = new HBox();
                usernameBox.setPrefSize(280, 30);
                usernameBox.setSpacing(5);
                Label UNL = new Label("Name:");
                UNL.setFont(aeroMI18);
                UNL.setAlignment(Pos.CENTER_RIGHT);
                UNL.setPrefSize(145, 30);
                UNL.setTextFill(Paint.valueOf("White"));
                usernameBox.getChildren().add(UNL);

                TextField username = new TextField();
                username.setFont(aeroMI18);
                username.setAlignment(Pos.CENTER);
                usernameBox.getChildren().add(username);
                base.getChildren().add(usernameBox);

                Label warning = new Label("You Must Enter A Name Before Joining");
                warning.setFont(aeroMI18);
                warning.setTextAlignment(TextAlignment.CENTER);
                warning.setAlignment(Pos.CENTER);
                warning.setTextFill(Paint.valueOf("Red"));
                warning.setOpacity(0);
                base.getChildren().add(warning);

                VBox.setMargin(warning, new Insets(0, 0, 0, 120));

                VBox.setMargin(usernameBox, new Insets(0, 0, 0, 65));


                Button jServer = new Button();
                jServer.setBackground(null);
                jServer.setBorder(null);
                jServer.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/play/submenujoinserver.png"))));
                jServer.setOnMouseExited(e -> jServer.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/play/submenujoinserver.png")))));
                jServer.setOnMouseEntered(e -> jServer.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/play/submenujoinserverhover.png")))));
                jServer.setOnAction(e -> {
                    if (ip.getText().length() > 0) {
                        Values.ip = ip.getText();
                    }
                    if (username.getText().length() > 0) {
                        PlayerManagement.name = username.getText();
                        FrameGUI.setScene("Client");
                    } else {
                        warning.setOpacity(1.0);
                    }


                });
                jServer.setEffect(ds);
                base.getChildren().add(jServer);
                VBox.setMargin(jServer, new Insets(0, 0, 0, 145));

                break;
            case "Host":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/host/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));

                try {
                    Label ipaL = new Label("Server Host IP: " + InetAddress.getLocalHost().getHostAddress());
                    ipaL.setFont(aeroMI18);
                    ipaL.setTextFill(Paint.valueOf("White"));
                    ipaL.setPrefSize(300, 30);
                    ipaL.setAlignment(Pos.CENTER);
                    base.getChildren().add(ipaL);
                    VBox.setMargin(ipaL, new Insets(0, 0, 0, 100));
                } catch (UnknownHostException e) {
                    System.out.println("Can't synthesize host IP address!");
                    Label ipaL = new Label("Host IP address not available...");
                    ipaL.setFont(aeroMI18);
                    ipaL.setPrefSize(250, 25);
                    ipaL.setAlignment(Pos.CENTER);
                    base.getChildren().add(ipaL);
                    VBox.setMargin(ipaL, new Insets(0, 0, 0, 175));
                }

                Button startserver = new Button();
                startserver.setBackground(null);
                startserver.setBorder(null);
                startserver.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/host/submenustartserver.png"))));
                startserver.setOnMouseExited(e -> startserver.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/host/submenustartserver.png")))));
                startserver.setOnMouseEntered(e -> startserver.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/host/submenustartserverhover.png")))));
                startserver.setOnAction(e -> {
                    System.out.println();
                    System.out.println("Time to start a server!");
                    try {
                        System.out.println("Server Host IP: " + InetAddress.getLocalHost().getHostAddress() + ":1180");
                    } catch (UnknownHostException e1) {
                        System.out.println("Server host IP unknown!");
                    }
                    FrameGUI.setScene("Server");
                });
                startserver.setEffect(ds);
                base.getChildren().add(startserver);
                VBox.setMargin(startserver, new Insets(50, 0, 0, 145));
                break;

            case "Credits":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/credits/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));
                String creditString =
                        "Created for the 2016 TSA National Conference\n" +
                                "Nashville, Tennessee\n\n" +
                                "Designed By: S0782, S0785\n" +
                                "Engine Programming: S0785\n" +
                                "Networking: S0782\n" +
                                "UI Programming: S0782\n" +
                                "Graphics/Artwork: S0782\n" +
                                "Animation: S0782\n" +
                                "Documentation: S0785\n" +
                                "Quality Assurance: S0785\n" +
                                "Additional Programming: S0785\n";


                Label creditText = new Label(creditString);
                creditText.setTextAlignment(TextAlignment.CENTER);
                creditText.setAlignment(Pos.TOP_CENTER);
                creditText.setFont(aeroMI18);
                creditText.setTextFill(Paint.valueOf("White"));
                creditText.setPrefSize(500, 550);
                base.getChildren().add(creditText);
                VBox.setMargin(creditText, new Insets(0, 0, 0, 0));
                break;
        }
        return base;
    }

    public static void setNode(String settingmode) {
        modeSettings = settingmode;
    }
}
