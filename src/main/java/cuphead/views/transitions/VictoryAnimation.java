package cuphead.views.transitions;

import cuphead.views.Main;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;

public class VictoryAnimation extends Transition {

    private ImageView imageView;

    private Pane pane;

    public VictoryAnimation(Pane pane) throws MalformedURLException {
        this.pane = pane;
        this.imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/victoryImages/161340_1.png").toExternalForm()).toExternalForm()));
        setCycleDuration(Duration.millis(3000));
        setCycleCount(1);
    }
    @Override
    protected void interpolate(double v) {
        int frame = (int) (Math.floor(v * 26) + 1);
        pane.getChildren().remove(imageView);
        try {
            this.imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/victoryImages/161340_" + frame + ".png").toExternalForm()).toExternalForm()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        pane.getChildren().add(imageView);
    }
}
