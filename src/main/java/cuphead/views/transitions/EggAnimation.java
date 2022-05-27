package cuphead.views.transitions;

import cuphead.controllers.GameController;
import cuphead.models.BossEgg;
import cuphead.models.Cuphead;
import cuphead.views.controllers.GamePageController;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class EggAnimation extends Transition {

    private BossEgg bossEgg;
    private Pane pane;

    public EggAnimation(BossEgg bossEgg, Pane pane){
        this.bossEgg = bossEgg;
        this.pane = pane;
        this.pane.getChildren().add(this.bossEgg.getImageView());
        setCycleDuration(Duration.millis(4000));
        setCycleCount(-1);
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        this.bossEgg.getImageView().setX(this.bossEgg.getImageView().getX() - 10);
        if(bossEgg.hitCuphead(this.pane)){
            pane.getChildren().remove(this.bossEgg.getImageView());
            try {
                Cuphead.getCuphead().hit(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.stop();
        }
        if(bossEgg.getImageView().getX() + bossEgg.getImageView().getImage().getWidth() <= 0){
            pane.getChildren().remove(bossEgg.getImageView());
            this.stop();
        }
    }
}
