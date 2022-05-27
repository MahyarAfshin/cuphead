package cuphead.models;

import cuphead.views.Main;
import cuphead.views.utilities.ImagePacker;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.net.MalformedURLException;
import java.net.URL;

public class BossEgg {

    private ImagePacker imageView;

    public BossEgg(Pane pane) throws MalformedURLException {
        this.imageView = new ImagePacker(this, new Image(new URL(Main.class.getResource("/cuphead/bossEgg/egg.png").toExternalForm()).toExternalForm()));
        this.imageView.setX(Boss.getBoss(pane).getImageView().getX() - this.imageView.getImage().getWidth());
        this.imageView.setY(Boss.getBoss(pane).getImageView().getY() + Boss.getBoss(pane).getImageView().getImage().getRequestedHeight() / 2);
    }

    public boolean hitCuphead(Pane pane){
        if(pane.getChildren().contains(this.imageView) && Cuphead.getCuphead().getIsVulnerable() && Cuphead.getCuphead().getImageView().getBoundsInParent().intersects(this.imageView.getBoundsInParent())){
            return true;
        }
        return false;
    }

    public void setImageView(ImagePacker imageView){
        this.imageView = imageView;
    }

    public ImagePacker getImageView(){
        return this.imageView;
    }
}
