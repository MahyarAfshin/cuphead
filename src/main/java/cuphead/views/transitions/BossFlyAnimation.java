package cuphead.views.transitions;

import cuphead.controllers.GameController;
import cuphead.models.Boss;
import cuphead.models.BossEgg;
import cuphead.models.Cuphead;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BossFlyAnimation extends Transition {

    private Boss boss;
    private String direction; // it could only be "UP" or "DOWN"
    private Pane pane;

    public BossFlyAnimation(Pane pane){
        this.boss = Boss.getBoss(pane);
        this.direction = "Up";
        this.pane = pane;
        setCycleDuration(Duration.millis(750));
        setCycleCount(-1);
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        double yPosition = this.boss.getImageView().getY();
        int frame = (int) Math.floor(v*5) + 1;
        this.pane.getChildren().remove(this.boss.getImageView());
        if(this.boss.getShootTime()){
            this.pause();
            BossEggShootAnimation animation = new BossEggShootAnimation(pane, this);
            animation.play();
        }
        try {
            this.boss.setImageView(new ImagePacker(this.boss, new Image(new URL(Main.class.getResource("/cuphead/bossFly/" + frame + ".png").toExternalForm()).toExternalForm(), 488.25, 381.75, false, false)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.boss.getImageView().setX(800);
        if(this.direction.equals("UP")){
            this.boss.getImageView().setY(yPosition - 5);
        }
        else if(this.direction.equals("DOWN")){
            this.boss.getImageView().setY(yPosition + 5);
        }
        if(this.boss.hitCuphead()){
            try {
                Cuphead.getCuphead().hit(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.pane.getChildren().add(this.boss.getImageView());
        this.updateDirection();
        if(boss.getHitPointsLeft() <= 0){
            try {
                GameController.getGameController().endGame(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateDirection(){
        if(this.boss.getImageView().getY() <= 10){
            this.direction = "DOWN";
        }
        if(this.boss.getImageView().getY() + this.boss.getImageView().getImage().getHeight() >= 650){
            this.direction = "UP";
        }
    }
}
