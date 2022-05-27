package cuphead.views.transitions;

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

public class ChangeCupheadArmourAnimation extends Transition {

    private String newArmourType;
    private ImageView imageView;
    private Pane pane;

    public ChangeCupheadArmourAnimation(String newArmourType, Pane pane) throws MalformedURLException {
        this.newArmourType = newArmourType;
        this.imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/cupheadShootImage/" + newArmourType.toLowerCase() + ".png").toExternalForm()).toExternalForm()));
        this.imageView.setX(90);
        this.imageView.setY(90);
        this.pane = pane;
        this.pane.getChildren().add(imageView);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(1);
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        this.imageView.setStyle("-fx-opacity: " + (100 - v*100));
    }
}
