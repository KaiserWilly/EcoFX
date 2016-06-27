import fx.FrameGUI;
import javafx.scene.text.Font;

/**
 * Created 1/30/16
 * Software Development
 * TSA Conference, Nashville Tennessee
 * StartDev: Start class of entire application
 */
public class StartDev { //Runs the entire application
    public static void main(String[] args) {
        Font.loadFont(StartDev.class.getResource("rsc/fonts/aeroM.ttf").toExternalForm(),12);
        FrameGUI.run(args);
    }
}
