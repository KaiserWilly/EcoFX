import fx.FrameGUI;
import javafx.scene.text.Font;

/**
 * Created by james on 4/3/2016.
 */
public class StartDev {
    public static void main(String[] args) {
        Font.loadFont(StartDev.class.getResource("rsc/fonts/aeroM.ttf").toExternalForm(),12);
        FrameGUI.run(args);
    }
}
