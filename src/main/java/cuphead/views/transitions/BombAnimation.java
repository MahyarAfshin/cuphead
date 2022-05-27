package cuphead.views.transitions;

import cuphead.controllers.GameController;
import cuphead.models.CupheadBomb;
import cuphead.models.CupheadBullet;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;

public class BombAnimation extends Transition {

    private CupheadBomb cupheadBomb;
    private Pane pane;

    public BombAnimation(CupheadBomb cupheadBomb, Pane pane){
        this.cupheadBomb = cupheadBomb;
        this.setCycleDuration(Duration.millis(2000));
        this.setCycleCount(-1);
        this.pane = pane;
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        this.cupheadBomb.getImageView().setY(this.cupheadBomb.getImageView().getY() + 10);
        if(this.cupheadBomb.getImageView().getY() >= 720 ){
            this.pane.getChildren().remove(this.cupheadBomb.getImageView());
            this.stop();
        }
        if(GameController.getGameController().removeBomb(this.pane, this.cupheadBomb)){
            this.stop();
        }
    }
}
