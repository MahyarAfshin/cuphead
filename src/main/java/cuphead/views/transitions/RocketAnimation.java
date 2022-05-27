package cuphead.views.transitions;

import cuphead.models.Cuphead;
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

public class RocketAnimation extends Transition {

    private Cuphead cuphead;
    private Pane pane;
    private double startingYPosition;

    public RocketAnimation(Pane pane){
        this.cuphead = Cuphead.getCuphead();
        this.pane = pane;
        this.startingYPosition = cuphead.getImageView().getY();
        setCycleDuration(Duration.millis(10000));
        setCycleCount(1);
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        pane.getChildren().remove(cuphead.getImageView());
        double xPosition = cuphead.getImageView().getX();
        double yPosition = cuphead.getImageView().getY();
        if(v < 0.05){
            try {
                cuphead.setImageView(new ImagePacker(cuphead, new Image(new URL(Main.class.getResource("/cuphead/rocket/0.png").toExternalForm()).toExternalForm())));
                cuphead.getImageView().setX(xPosition);
                cuphead.getImageView().setY(yPosition);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(v < 0.1){
            try {
                cuphead.setImageView(new ImagePacker(cuphead, new Image(new URL(Main.class.getResource("/cuphead/rocket/1.png").toExternalForm()).toExternalForm())));
                cuphead.getImageView().setX(xPosition);
                cuphead.getImageView().setY(yPosition);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                cuphead.setImageView(new ImagePacker(cuphead, new Image(new URL(Main.class.getResource("/cuphead/rocket/2.png").toExternalForm()).toExternalForm())));
                cuphead.getImageView().setX(xPosition + 15);
                cuphead.getImageView().setY(yPosition);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        pane.getChildren().add(cuphead.getImageView());
        try {
            if(cuphead.getImageView().getX() >= 1280 || cuphead.hitAsRocket(pane)){
                pane.getChildren().remove(cuphead.getImageView());
                try {
                    cuphead.setImageView(new ImagePacker(cuphead, new Image(new URL(Main.class.getResource("/cuphead/cupheadFly/cupheadNormal.png").toExternalForm()).toExternalForm())));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                cuphead.getImageView().setX(20);
                cuphead.getImageView().setY(startingYPosition);
                cuphead.setIsRocket(false);
                pane.getChildren().add(cuphead.getImageView());
                cuphead.setIsVulnerable(true);
                this.stop();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
