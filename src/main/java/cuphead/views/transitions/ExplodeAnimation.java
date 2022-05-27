package cuphead.views.transitions;

import cuphead.models.Cuphead;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;

public class ExplodeAnimation extends Transition {

    private double xPosition;
    private double yPosition;
    private Pane pane;
    private ImageView imageView;

    public ExplodeAnimation(Pane pane) throws MalformedURLException {
        this.pane = pane;
        this.xPosition = Cuphead.getCuphead().getImageView().getX();
        this.yPosition = Cuphead.getCuphead().getImageView().getY();
        this.imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/rocketExplosion/0.png").toExternalForm()).toExternalForm(), 300, 200, false, false));
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
        boolean mute = Main.getPlayer().muteProperty().getValue();
        Main.getPlayer().stop();
        Main.setPlayer(new MediaPlayer(new Media(new URL(Main.class.getResource("/cuphead/music/3.mp3").toExternalForm()).toExternalForm())));
        Main.getPlayer().play();
        pane.getChildren().add(imageView);
        setCycleDuration(Duration.millis(750));
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                try {
                    Main.getPlayer().stop();
                    Main.setPlayer(new MediaPlayer(new Media(new URL(Main.class.getResource("/cuphead/music/1.mp3").toExternalForm()).toExternalForm())));
                    Main.getPlayer().setMute(mute);
                    Main.getPlayer().play();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        setCycleCount(1);
        GamePageController.animations.add(this);
    }
    @Override
    protected void interpolate(double v) {
        int frame = 0;
        if(v > 0.25 && v < 0.5){
            frame = 1;
        }
        else if(v > 0.5 && v < 0.75){
            frame = 2;
        }
        else if(v > 0.75){
            frame = 3;
        }
        pane.getChildren().remove(imageView);
        try {
            this.imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/rocketExplosion/" + frame + ".png").toExternalForm()).toExternalForm(), 300, 200, false, false));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
        pane.getChildren().add(imageView);

    }
}
