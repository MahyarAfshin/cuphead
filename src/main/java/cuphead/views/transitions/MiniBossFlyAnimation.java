package cuphead.views.transitions;

import cuphead.controllers.GameController;
import cuphead.models.Cuphead;
import cuphead.models.CupheadBullet;
import cuphead.models.GameDatabase;
import cuphead.models.Miniboss;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MiniBossFlyAnimation extends Transition {

    private Miniboss miniboss;
    private Pane pane;

    public MiniBossFlyAnimation(Miniboss miniboss, Pane pane){
        this.miniboss = miniboss;
        this.setCycleDuration(Duration.millis(300));
        this.setCycleCount(-1);
        this.pane = pane;
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v*3);
        try {
            double xPosition = this.miniboss.getImageView().getX();
            double yPosition = this.miniboss.getImageView().getY();
            this.pane.getChildren().remove(this.miniboss.getImageView());
            ImagePacker imageView = new ImagePacker(this.miniboss, new Image(new URL(Main.class.getResource("/cuphead/miniBossFly/" + this.miniboss.getColor() + "/" + frame + ".png").toExternalForm()).toExternalForm()));
            this.miniboss.setImageView(imageView);
            this.miniboss.getImageView().setX(xPosition - 5);
            this.miniboss.getImageView().setY(yPosition);
            this.pane.getChildren().add(imageView);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if(miniboss.hitCuphead()){
            miniboss.setHitPointsLeft(0);
            try {
                Cuphead.getCuphead().hit(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(this.miniboss.getImageView().getX() + this.miniboss.getImageView().getImage().getWidth() <= 0 || this.miniboss.getHitPointsLeft() == 0){
            if(this.miniboss.getHitPointsLeft() == 0){
                GameDatabase.getGameDatabase().setGameScore(GameDatabase.getGameDatabase().getGameScore() + 5);
            }
            this.pane.getChildren().remove(this.miniboss.getImageView());
            this.stop();
        }
    }
}
