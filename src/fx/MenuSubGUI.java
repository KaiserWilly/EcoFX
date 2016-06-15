package fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
                usernameBox.setPrefSize(250, 30);
                usernameBox.setSpacing(5);
                Label UNL = new Label("Name:");
                UNL.setFont(aeroMI18);
                UNL.setAlignment(Pos.CENTER_RIGHT);
                UNL.setPrefSize(145, 30);
                UNL.setTextFill(Paint.valueOf("White"));
                usernameBox.getChildren().add(UNL);
                TextField username = new TextField();
                username.setPrefSize(150, 30);
                username.setFont(aeroMI18);
                username.setAlignment(Pos.CENTER);
                usernameBox.getChildren().add(username);
                base.getChildren().add(usernameBox);
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
                    }
                    System.out.println("Going to join a game with the IP address: " + Values.ip);
                    FrameGUI.setScene("Client");
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
                    Label ipaL = new Label("Server Host IP: " + InetAddress.getLocalHost().getHostAddress() + ":1180");
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

                HBox seedBox = new HBox();
                seedBox.setPrefSize(250, 30);
                seedBox.setSpacing(5);
                Label seedL = new Label("Seed:");
                seedL.setFont(aeroMI18);
                seedL.setAlignment(Pos.CENTER_RIGHT);
                seedL.setPrefSize(145, 30);
                seedL.setTextFill(Paint.valueOf("White"));
                seedBox.getChildren().add(seedL);
                seed = new TextField(String.valueOf((int) (Math.random() * 20000000)));
                seed.setPrefSize(150, 30);
                seed.setFont(aeroMI18);
                seed.setAlignment(Pos.CENTER);
                seedBox.getChildren().add(seed);
                base.getChildren().add(seedBox);
                VBox.setMargin(seedBox, new Insets(0, 0, 0, 50));

                HBox maxPlayersBox = new HBox();
                maxPlayersBox.setPrefSize(250, 30);
                maxPlayersBox.setSpacing(5);
                int[] players = new int[50];
                for (int i = 0; i < players.length; i++) {
                    players[i] = i + 1;
                }
                Label maxPL = new Label("Maximum Players:");
                maxPL.setPrefSize(150, 30);
                maxPL.setAlignment(Pos.CENTER_RIGHT);
                maxPL.setTextFill(Paint.valueOf("White"));
                maxPL.setFont(aeroMI18);
                maxPlayersBox.getChildren().add(maxPL);
                Spinner maxP = new Spinner(1, 50, 10);
                maxP.setEditable(false);
                maxP.setPrefSize(150, 30);
                maxPlayersBox.getChildren().add(maxP);
                base.getChildren().add(maxPlayersBox);
                VBox.setMargin(maxPlayersBox, new Insets(0, 0, 0, 90));

                HBox modeBox = new HBox();
                modeBox.setPrefSize(300, 30);
                modeBox.setSpacing(10);
                ToggleGroup gamemodeToggle = new ToggleGroup();
                RadioButton consumerR = new RadioButton("Consumer Mode");
                consumerR.setFont(aeroMI18);
                consumerR.setTextFill(Paint.valueOf("White"));
                consumerR.setPrefSize(140, 30);
//                consumerR.setTooltip(new Tooltip("Interests, Dividends Enabled. Geared towards longer sessions."));
                consumerR.setToggleGroup(gamemodeToggle);
                modeBox.getChildren().add(consumerR);
                RadioButton investorR = new RadioButton("Investor Mode");
                investorR.setFont(aeroMI18);
                investorR.setTextFill(Paint.valueOf("White"));
                investorR.setPrefSize(140, 30);
//                investorR.setTooltip(new Tooltip("No Interest or Dividends. Geared towards shorter sessions."));
                investorR.setToggleGroup(gamemodeToggle);
                consumerR.setSelected(true);
                modeBox.getChildren().add(investorR);
                base.getChildren().add(modeBox);
                VBox.setMargin(modeBox, new Insets(0, 0, 0, 115));

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
                    System.out.println("Seed: " + seed.getText());
                    System.out.println("Max Players: " + String.valueOf(maxP.getValue()));
                    System.out.print("Mode: ");
                    if (gamemodeToggle.getSelectedToggle() == consumerR) {
                        System.out.println("Consumer Mode");
                        modeServer = "con";
                    } else {
                        System.out.println("Investor Mode");
                        modeServer = "inv";
                    }
                    Values.seed = seed.getText();
                    Values.maxP = String.valueOf(maxP.getValue());
                    Values.mode = modeServer;
                    FrameGUI.setScene("Server");
                });
                startserver.setEffect(ds);
                base.getChildren().add(startserver);
                VBox.setMargin(startserver, new Insets(0, 0, 0, 145));
                break;
            case "EditP":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));

                HBox nameBox = new HBox();
                nameBox.setPrefSize(250, 30);
                nameBox.setSpacing(5);
                Label nameL = new Label("Name:");
                nameL.setFont(aeroMI18);
                nameL.setAlignment(Pos.CENTER_RIGHT);
                nameL.setPrefSize(145, 30);
                nameL.setTextFill(Paint.valueOf("White"));
                nameBox.getChildren().add(nameL);
                TextField name = new TextField("The Kaiser");
                name.setPrefSize(150, 30);
                name.setFont(aeroMI18);
                name.setAlignment(Pos.CENTER);
                nameBox.getChildren().add(name);
                base.getChildren().add(nameBox);
                VBox.setMargin(nameBox, new Insets(0, 0, 0, 50));

                Button resetStats = new Button();
                resetStats.setBackground(null);
                resetStats.setBorder(null);
                resetStats.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenuresetstats.png"))));
                resetStats.setOnMouseExited(e -> resetStats.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenuresetstats.png")))));
                resetStats.setOnMouseEntered(e -> resetStats.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenuresetstatshover.png")))));
                resetStats.setOnAction(e -> System.out.println("Going to Reset the Stats!"));
                resetStats.setEffect(ds);
                base.getChildren().add(resetStats);
                VBox.setMargin(resetStats, new Insets(0, 0, 0, 145));

                Button saveProfile = new Button();
                saveProfile.setBackground(null);
                saveProfile.setBorder(null);
                saveProfile.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenusavesettings.png"))));
                saveProfile.setOnMouseExited(e -> saveProfile.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenusavesettings.png")))));
                saveProfile.setOnMouseEntered(e -> saveProfile.setGraphic(new ImageView(new Image(MenuSubGUI.class.getClassLoader().getResourceAsStream("rsc/menu/editp/submenusavesettingshover.png")))));
                saveProfile.setOnAction(e -> System.out.println("Going to save profile settings!"));
                saveProfile.setEffect(ds);
                base.getChildren().add(saveProfile);
                VBox.setMargin(saveProfile, new Insets(0, 0, 0, 120));

                break;
            case "ChangeP":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/changep/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));


                break;
            case "Extras":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/extras/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));


                break;
            case "Help":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/help/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));
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
                                "Designed By: Daniel Holt, JD Isenhart, Ryan Trost\n" +
                                "Engine Programming: Daniel Holt\n" +
                                "Networking: Ryan Trost\n" +
                                "AI Programming: Daniel Holt\n" +
                                "UI Programming: JD Isenhart\n" +
                                "Graphics/Artwork: JD Isenhart\n" +
                                "Animation: JD Isenhart\n" +
                                "Documentation: Daniel Holt\n" +
                                "Quality Assurance: Ryan Trost\n" +
                                "Additional Programming: Ryan Trost\n" +
                                "Moral Support: Evan Wiesenmeyer\n\n" +
                                "Special Thanks To:\n" +
                                "Robert Hazelhurst\n" +
                                "Michael Milham\n" +
                                "Cheri Albers\n" +
                                "Nate St. George";


                Label creditText = new Label(creditString);
                creditText.setTextAlignment(TextAlignment.CENTER);
                creditText.setAlignment(Pos.TOP_CENTER);
                creditText.setFont(aeroMI18);
                creditText.setTextFill(Paint.valueOf("White"));
                creditText.setPrefSize(500, 550);
                base.getChildren().add(creditText);
                VBox.setMargin(creditText, new Insets(0, 0, 0, 0));
                break;
            case "Settings":
                heading = new Label();
                heading.setEffect(ds);
                heading.setGraphic(new ImageView(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/menu/settings/submenuheader.png"))));
                base.getChildren().add(heading);
                VBox.setMargin(heading, new Insets(0, 0, 0, 175));
                HBox settingsModeBox = new HBox();
                settingsModeBox.setPrefSize(300, 30);
                settingsModeBox.setSpacing(10);
                ToggleGroup settingsmodeToggle = new ToggleGroup();
                RadioButton devR = new RadioButton("Developer Mode");
                devR.setFont(aeroMI18);
                devR.setTextFill(Paint.valueOf("White"));
                devR.setPrefSize(140, 30);
                devR.setToggleGroup(settingsmodeToggle);
                settingsModeBox.getChildren().add(devR);
                RadioButton conR = new RadioButton("Consumer Mode");
                conR.setFont(aeroMI18);
                conR.setTextFill(Paint.valueOf("White"));
                conR.setPrefSize(140, 30);
                conR.setToggleGroup(settingsmodeToggle);

                settingsModeBox.getChildren().add(conR);
                RadioButton partyR = new RadioButton("420 Mode");
                partyR.setFont(aeroMI18);
                partyR.setTextFill(Paint.valueOf("White"));
                partyR.setPrefSize(140, 30);
                partyR.setToggleGroup(settingsmodeToggle);
                switch (modeSettings) {
                    case "con":
                        conR.setSelected(true);
                        break;
                    case "party":
                        partyR.setSelected(true);
                        break;
                }
                settingsModeBox.getChildren().add(partyR);
                settingsmodeToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

                    if (settingsmodeToggle.getSelectedToggle() == partyR) {
                        FrameGUI.menu.setSceneStyle("420");
                        setNode("party");
                    } else {
                        FrameGUI.menu.setSceneStyle("Normal");
                        setNode("con");
                    }
                });

                base.getChildren().add(settingsModeBox);
                VBox.setMargin(settingsModeBox, new Insets(0, 0, 0, 55));
                break;
        }
        return base;
    }

    public static void setNode(String settingmode) {
        modeSettings = settingmode;
    }
}
