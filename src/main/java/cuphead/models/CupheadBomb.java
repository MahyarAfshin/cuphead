package cuphead.models;

import cuphead.views.Main;
import cuphead.views.utilities.ImagePacker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.MalformedURLException;
import java.net.URL;

public class CupheadBomb {

    private ImagePacker imageView;

    public CupheadBomb(Pane pane) throws MalformedURLException {
        this.imageView = new ImagePacker(this, new Image(new URL(Main.class.getResource("/cuphead/bomb/0.png").toExternalForm()).toExternalForm()));
        this.imageView.setX(Cuphead.getCuphead().getImageView().getX() + Cuphead.getCuphead().getImageView().getImage().getWidth());
        this.imageView.setY(Cuphead.getCuphead().getImageView().getY());
        pane.getChildren().add(this.imageView);
    }

    public void setImageView(ImagePacker imageView){
        this.imageView = imageView;
    }

    public ImageView getImageView(){
        return this.imageView;
    }
}
