package cuphead.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cuphead.enums.GameDifficulty;
import cuphead.models.*;
import cuphead.views.Main;
import cuphead.views.controllers.GamePageController;
import cuphead.views.transitions.*;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GameController {

    private static GameController gameController;

    private GameDatabase gameDatabase;

    public static GameController getGameController(){
        if(gameController == null){
            gameController = new GameController();
        }
        return gameController;
    }

    public void cupheadShoot(Pane pane) throws MalformedURLException {
        if(Cuphead.getCuphead().getArmourType().equals("BULLET")){
            shootCupheadBullet(pane);
        }
        else{
            shootCupheadBomb(pane);
        }
    }

    public void changeCupheadArmourType(Pane pane) throws MalformedURLException {
        String newArmourType = "";
        if(Cuphead.getCuphead().getArmourType().equals("BULLET")){
            newArmourType = "BOMB";
        }
        else{
            newArmourType = "BULLET";
        }
        Cuphead.getCuphead().setArmourType(newArmourType);
        ChangeCupheadArmourAnimation animation = new ChangeCupheadArmourAnimation(newArmourType, pane);
        animation.play();
    }

    private void shootCupheadBomb(Pane pane) throws MalformedURLException {
        BombAnimation animation = new BombAnimation(new CupheadBomb(pane), pane);
        animation.play();
        FrontFireAnimation frontFireAnimation = new FrontFireAnimation(pane);
        frontFireAnimation.play();
    }

    private void shootCupheadBullet(Pane pane) throws MalformedURLException {
        BulletAnimation animation = new BulletAnimation(new CupheadBullet(pane), pane);
        animation.play();
        FrontFireAnimation frontFireAnimation = new FrontFireAnimation(pane);
        frontFireAnimation.play();
    }

    public void bossShoot(Pane pane){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            Boss.getBoss(pane).setShootTime(true);
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        GamePageController.timelines.add(timeline);
    }

    public void generateMiniBosses(Pane pane){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), ev -> {
            generateMiniBossSeries(pane);
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        GamePageController.timelines.add(timeline);
    }

    private void generateMiniBossSeries(Pane pane){
        Random rand = new Random();
        int colorIndex = rand.nextInt(2);
        String color = "";
        if(colorIndex == 0){
            color = "yellow";
        }
        else{
            color = "purple";
        }
        int randomYPosition = rand.nextInt(2) * 350 + 100;
       String finalColor = color;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            Miniboss miniboss = null;
            try {
                miniboss = new Miniboss(finalColor, 1000, randomYPosition);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            MiniBossFlyAnimation animation = new MiniBossFlyAnimation(miniboss, pane);
            animation.play();
        }));
        timeline.setCycleCount(3);
        timeline.play();
        GamePageController.timelines.add(timeline);
    }

    // bellow function returns true if the bullet bounds intersects with something and false otherwise
    // this function also removes the bullet from the pane if there is any intersection
    public boolean removeBullet(Pane pane, CupheadBullet cupheadBullet){
        for (Node child : pane.getChildren()) {
            if(child instanceof  ImagePacker && !child.equals(cupheadBullet.getImageView()) && !(((ImagePacker) child).getObject() instanceof Cuphead) && cupheadBullet.getImageView().getBoundsInParent().intersects(child.getBoundsInParent())){
                if(((ImagePacker) child).getObject() instanceof Miniboss){
                    ((Miniboss) ((ImagePacker) child).getObject()).hitBullet();
                }
                else if(((ImagePacker) child).getObject() instanceof Boss){
                    ((Boss) ((ImagePacker) child).getObject()).hitBullet(pane);
                }
                else if(((ImagePacker) child).getObject() instanceof BossEgg){
                    pane.getChildren().remove(child);
                }
                pane.getChildren().remove(cupheadBullet.getImageView());
                return true;
            }
        }
        return false;
    }

    public boolean removeBomb(Pane pane, CupheadBomb cupheadBomb){
        for (Node child : pane.getChildren()) {
            if(child instanceof  ImagePacker && !child.equals(cupheadBomb.getImageView()) && !(((ImagePacker) child).getObject() instanceof Cuphead) && cupheadBomb.getImageView().getBoundsInParent().intersects(child.getBoundsInParent())){
                if(((ImagePacker) child).getObject() instanceof Miniboss){
                    ((Miniboss) ((ImagePacker) child).getObject()).hitBomb();
                }
                else if(((ImagePacker) child).getObject() instanceof Boss){
                    ((Boss) ((ImagePacker) child).getObject()).hitBomb(pane);
                }
                else if(((ImagePacker) child).getObject() instanceof BossEgg){
                    pane.getChildren().remove(child);
                }
                pane.getChildren().remove(cupheadBomb.getImageView());
                return true;
            }
        }
        return false;
    }

    public void convertToRocket(Pane pane){
        if(Cuphead.getCuphead().getCanConvertToRocket()){
            Cuphead.getCuphead().setCanConvertToRocket(false);
            long start = System.currentTimeMillis();
            RocketAnimation rocketAnimation = new RocketAnimation(pane);
            rocketAnimation.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), ev -> {
                long now = System.currentTimeMillis();
                double width = (double) ((now - start) % 20000) / (double) 20000 * 100;
                if(width < 0.1){
                    width = 100;
                }
                GameController.this.drawTheRocketBar(pane, width);
            }));
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Cuphead.getCuphead().setCanConvertToRocket(true);
                }
            });
            timeline.setCycleCount(20000);
            timeline.play();
            GamePageController.timelines.add(timeline);
        }
    }

    public void drawTheRocketBar(Pane pane, double width){
        pane.getChildren().remove(Cuphead.getCuphead().getRocketBar());
        Cuphead.getCuphead().setRocketBar(new Rectangle(50, 50, width, 20));
        Cuphead.getCuphead().getRocketBar().setStyle("-fx-arc-height: 10; -fx-arc-width: 10; -fx-fill: #ff0101");
        pane.getChildren().add(Cuphead.getCuphead().getRocketBar());
    }

    public static void writeSavedGamesListToFile() {
        File main = new File("src", "main");
        File resources = new File(main, "resources");
        File cuphead = new File(resources, "cuphead");
        File json = new File(cuphead, "json");
        File gamesFile = new File(json, "SavedGames.json");
        try {
            FileWriter gameWriter = new FileWriter(gamesFile);
            gameWriter.write(new Gson().toJson(GameDatabase.getGameDatabase().getAllSavedGames()));
            gameWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void endGame(Pane pane) throws IOException {
        GameDatabase.getGameDatabase().setGameScore(GameDatabase.getGameDatabase().getGameScore() + Cuphead.getCuphead().getHitPointsLeft() * 10);
        ScoreboardController.getScoreboardController().updatePlayerScore(GameDatabase.getGameDatabase().getGameScore(), GameDatabase.getGameDatabase().getDifficulty(), GameDatabase.getGameDatabase().getPlayer());
        ArrayList<Transition> animations = GamePageController.animations;
        ArrayList<Timeline> timelines = GamePageController.timelines;
        for (Transition animation : animations) {
            animation.stop();
        }
        for (Timeline timeline : timelines) {
            timeline.stop();
        }
        if(Boss.getBoss(pane).getHitPointsLeft() <= 0){
            VictoryAnimation animation = new VictoryAnimation(pane);
            animation.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Main.loadFxmlFile("EndGamePage");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            animation.play();
        }
        else{
            Main.loadFxmlFile("EndGamePage");
        }
    }

    private GameController(){
        this.gameDatabase = GameDatabase.getGameDatabase();
    }

    public void setGameDatabase(GameDatabase gameDatabase){
        this.gameDatabase = gameDatabase;
    }

    public GameDatabase getGameDatabase(){
        return this.gameDatabase;
    }
}
