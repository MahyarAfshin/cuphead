package cuphead.views.controllers;

import cuphead.models.Boss;
import cuphead.models.Cuphead;
import cuphead.models.GameDatabase;
import cuphead.views.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EndGamePageController {

    @FXML
    private VBox box;

    @FXML
    public void initialize() throws MalformedURLException {
        if(Cuphead.getCuphead().getHitPointsLeft() <= 0){
            ImageView imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/quotes/images.jpeg").toExternalForm()).toExternalForm()));
            box.getChildren().add(imageView);
            Boss boss = Boss.getBoss(new Pane());
            int hitPointsLeft = boss.getHitPointsLeft();
            System.out.println(hitPointsLeft / (100 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage()));
            ProgressBar progressBar = new ProgressBar(1 - (double) hitPointsLeft / (double) (100 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage()));
            box.getChildren().add(progressBar);
        }
        Text score = new Text("Your score: " + GameDatabase.getGameDatabase().getGameScore());
        score.setStyle("-fx-font-size: 25; -fx-fill: #198ed5");
        Text time = new Text("Time elapsed: " + findElapsedTime());
        time.setStyle("-fx-font-size: 25; -fx-fill: #198ed5");
        box.getChildren().add(score);
        box.getChildren().add(time);
    }

    private String findElapsedTime(){
        long now = System.currentTimeMillis();
        int seconds = (int) ((now - GamePageController.getStartTime()) / 1000);
        return getTimeString(seconds);
    }

    public static String getTimeString(int seconds) {
        int minutes = seconds / 60;
        seconds -= minutes * 60;
        String time = "";
        if(minutes < 10){
            time = time + 0 + minutes;
        }
        else{
            time += minutes;
        }
        time += ":";
        if(seconds < 10){
            time = time + 0 + seconds;
        }
        else{
            time += seconds;
        }
        return time;
    }

    public void restart(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("GamePage");
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("MainPage");
    }
}
