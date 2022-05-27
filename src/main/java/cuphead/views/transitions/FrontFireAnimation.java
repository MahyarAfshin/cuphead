package cuphead.views.transitions;

import cuphead.models.Cuphead;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;

public class FrontFireAnimation extends Transition {

    private Cuphead cuphead;
    private Pane pane;

    public FrontFireAnimation(Pane pane){
        this.cuphead = Cuphead.getCuphead();
        this.pane = pane;
        this.setCycleDuration(Duration.millis(200));
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        int frame = 0;
        if(v > 0.5){
            frame = 1;
        }
        try {
            pane.getChildren().remove(cuphead.getFireImage());
            if(v != 1) {
                ImagePacker imageView = new ImagePacker(this.cuphead, new Image(new URL(Main.class.getResource("/cuphead/cupheadBullet/frontFire" + frame + ".png").toExternalForm()).toExternalForm()));
                imageView.setX(cuphead.getImageView().getX() + cuphead.getImageView().getImage().getWidth() - 5);
                imageView.setY(cuphead.getImageView().getY() + 15);
                cuphead.setFireImage(imageView);
                pane.getChildren().add(imageView);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
