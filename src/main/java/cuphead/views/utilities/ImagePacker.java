package cuphead.views.utilities;

import cuphead.models.GameDatabase;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePacker extends ImageView {

    private Object object;

    public ImagePacker(Object object, Image image){
        super(image);
        this.object = object;
        if(GameDatabase.getGameDatabase().getIsGrayScale()) {
            ColorAdjust monochrome = new ColorAdjust();
            monochrome.setSaturation(-1);
            this.setEffect(monochrome);
        }
    }

    public void setObject(Object object){
        this.object = object;
    }

    public Object getObject(){
        return this.object;
    }
}
