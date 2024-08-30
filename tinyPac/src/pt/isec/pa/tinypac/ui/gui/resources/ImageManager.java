package pt.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.HashMap;

public class ImageManager {

    private ImageManager(){}

    private final static String folderName = "images/";
    private static final HashMap<String, Image> images = new HashMap<>();

    public static Image getImage(String fileImage){
        Image image = images.get(fileImage);
        if(image == null){
            try(InputStream is = ImageManager.class.getResourceAsStream(folderName + fileImage)){
                image = new Image(is);
                images.put(fileImage, image);
            }catch (Exception e){
                return null;
            }
        }
        return image;
    }

    public static Image getExternalImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try {
                image = new Image(filename);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    public static void purgeImage(String filename) { images.remove(filename); }
}
