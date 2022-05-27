package cuphead.views.transitions;

import cuphead.models.Boss;
import cuphead.models.BossEgg;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;

public class BossEggShootAnimation extends Transition {

    private Boss boss;
    private Pane pane;
    private BossFlyAnimation bossFlyAnimation;
    private boolean startShoot;

    public BossEggShootAnimation(Pane pane, BossFlyAnimation bossFlyAnimation){
        this.boss = Boss.getBoss(pane);
        this.pane = pane;
        this.bossFlyAnimation = bossFlyAnimation;
        this.startShoot = false;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(1);
        this.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BossEggShootAnimation.this.boss.setShootTime(false);
                BossEggShootAnimation.this.bossFlyAnimation.play();
            }
        });
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        pane.getChildren().remove(this.boss.getImageView());
        int frame = (int) (Math.floor(v * 11) + 1);
        if(!startShoot && frame == 5){
            try {
                EggAnimation eggAnimation = new EggAnimation(new BossEgg(this.pane), this.pane);
                eggAnimation.play();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            startShoot = true;
        }
        double yPosition = this.boss.getImageView().getY();
        try {
            this.boss.setImageView(new ImagePacker(this.boss, new Image(new URL(Main.class.getResource("/cuphead/bossShoot/" + frame + ".png").toExternalForm()).toExternalForm(), 597.75, 381.75, false, false)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.boss.getImageView().setX(800 - 109.5);
        this.boss.getImageView().setY(yPosition);
        pane.getChildren().add(this.boss.getImageView());
    }
}
