package pt.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.text.Font;
import java.util.HashMap;

public class FontManager {
    private static String folderName = "font/";
    private static final HashMap<String, Font> fonts = new HashMap<>();
    private FontManager() { }
    public static Font getFont(String fileName){
        Font font = fonts.get(fileName);
        if(font == null){
            font = Font.loadFont(folderName + fileName, 45);
            fonts.put(fileName, font);

            /*
              try {
                font = Font.loadFont(new File(folderName + fileName).toURI().toURL().toExternalForm(), 45);
                fonts.put(fileName, font);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
             */
        }
        return font;
    }
}
