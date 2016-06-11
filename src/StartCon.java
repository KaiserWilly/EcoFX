import fx.FrameGUI;
import rsc.Values;

import java.util.HashMap;

/**
 * Created by james on 4/3/2016.
 */
public class StartCon {
    public static void main(String[] args) {
//        FrameGUI.run(args);
        HashMap<String, Object> settings = new HashMap<>();
        settings.put("Background", "mlg");
        Values.saveSettings(settings);
        settings = Values.getSettings();
        System.out.println(settings.get("Background"));
    }
}
