package cuphead.views.transitions;

import cuphead.controllers.GameController;
import cuphead.models.CupheadBullet;
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

public class BulletAnimation extends Transition {

    private CupheadBullet cupheadBullet;
    private Pane pane;

    public BulletAnimation(CupheadBullet cupheadBullet, Pane pane){
        this.cupheadBullet = cupheadBullet;
        this.setCycleDuration(Duration.millis(4000));
        this.setCycleCount(-1);
        this.pane = pane;
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v*4);
        double xPosition = cupheadBullet.getImageView().getX();
        double yPosition = cupheadBullet.getImageView().getY();
        this.pane.getChildren().remove(cupheadBullet.getImageView());
        try {
            this.cupheadBullet.setImageView(new ImagePacker(this.cupheadBullet, new Image(new URL(Main.class.getResource("/cuphead/cupheadBullet/normalBullet" + frame + ".png").toExternalForm()).toExternalForm())));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.cupheadBullet.getImageView().setX(xPosition + 15);
        this.cupheadBullet.getImageView().setY(yPosition);
        this.pane.getChildren().add(cupheadBullet.getImageView());
        if(this.cupheadBullet.getImageView().getX() > 1280){
            this.pane.getChildren().remove(this.cupheadBullet.getImageView());
            this.stop();
        }
        if(GameController.getGameController().removeBullet(this.pane, this.cupheadBullet)){
            this.stop();
        }
    }
}
