package cuphead.views.transitions;

import cuphead.models.Cuphead;
import cuphead.views.controllers.GamePageController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class CupheadBlinkAnimation extends Transition {

    private Cuphead cuphead;

    public CupheadBlinkAnimation(){
        this.cuphead = Cuphead.getCuphead();
        this.cuphead.setIsVulnerable(false);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(1);
        this.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CupheadBlinkAnimation.this.cuphead.getImageView().setOpacity(1);
                CupheadBlinkAnimation.this.cuphead.setIsVulnerable(true);
            }
        });
        GamePageController.animations.add(this);
    }

    @Override
    protected void interpolate(double v) {
        this.cuphead.setIsVulnerable(false);
        int frame = (int) (v * 10);
        if(frame % 2 == 1){
            cuphead.getImageView().setOpacity(1);
        }
        else{
            cuphead.getImageView().setOpacity(0);
        }
    }
}
