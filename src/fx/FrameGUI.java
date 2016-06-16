package fx;

import fx.client.ClientFrameGUI;
import fx.server.ServerEndGameGUI;
import fx.server.ServerGUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import rsc.Values;


/**
 * Created by james on 3/16/2016.
 */
public class FrameGUI extends Application {

    static Stage baseStage;
    public static MainMenuGUI menu = new MainMenuGUI();
    public static ServerGUI server = new ServerGUI();
    public static ClientFrameGUI client = new ClientFrameGUI();
    public static ServerEndGameGUI end = new ServerEndGameGUI();
    static String curScene = "Menu";
    static StackPane stack;
    static Scene scene;
    static Node menuP, clientP, serverP, endP;

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        baseStage = primaryStage;
        baseStage.setTitle("Echonomics v" + Values.VERSION);
        baseStage.getIcons().add(new Image(FrameGUI.class.getClassLoader().getResourceAsStream("rsc/frame/EcoIcon-01.png")));
        baseStage.setScene(createScene());

        baseStage.setMaxHeight(600);
        baseStage.setMinHeight(600);
        baseStage.setMaxWidth(1000);
        baseStage.setMinWidth(1000);
        setScene("Menu");
        baseStage.show();

    }

    public static Scene createScene() {
        stack = new StackPane();
        stack.setPadding(new Insets(0, 0, 30, 0));
        clientP = client.clientPane();
        stack.getChildren().add(clientP);
        serverP = server.serverPane();
        stack.getChildren().add(serverP);
        menuP = menu.menuPane();
        stack.getChildren().add(menuP);
        endP = end.createPane();
        stack.getChildren().add(endP);


        client.setOpacity(0.0);
        server.setOpacity(0.0);
        menu.setOpacity(0.0);
        end.setOpacity(0.0);
        scene = new Scene(stack, 1000, 600);
        scene.getStylesheets().add("rsc/StylesheetRoot.css");
        return scene;
    }

    public static void setScene(String scene) {
        switch (curScene) {
            case "Menu":
                menu.close(scene);
                break;
            case "Server":
                server.close(scene);
                break;
        }
        curScene = scene;
    }
}
