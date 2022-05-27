package cuphead.views.controllers;

import cuphead.controllers.GameController;
import cuphead.enums.GameDifficulty;
import cuphead.models.*;
import cuphead.views.Main;
import cuphead.views.transitions.*;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GamePageController {

    private GameController gameController = GameController.getGameController();
    public static ArrayList<Transition> animations = new ArrayList<>();
    public static ArrayList<Timeline> timelines = new ArrayList<>();
    private static long startTime;

    @FXML
    private Pane pane;

    @FXML
    public void initialize() {
        startTime = System.currentTimeMillis();
        GameDatabase.getGameDatabase().setGameScore(0);
        pane.getChildren().clear();
        if(gameController.getGameDatabase().getIsGrayScale()) {
            ColorAdjust monochrome = new ColorAdjust();
            monochrome.setSaturation(-1);
            pane.setEffect(monochrome);
        }
        makeBossHealthProgressBar();
        makeRocketProgressBar();
        try {
            setImageToCuphead();
            setImageToBoss();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        setPaneOnKeyPressed();
        playSceneBackgroundAnimation();
        this.gameController.generateMiniBosses(this.pane);
        this.gameController.bossShoot(this.pane);
        try {
            findSavedGameToPlay();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Boss.getBoss(pane).redrawHealthProgressBar(pane);
    }

    private void setPaneOnKeyPressed(){
        Main.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                //System.out.println(keyName);
                switch(keyName){
                    case "Up":
                        Cuphead.getCuphead().moveUp();
                        break;
                    case "Down":
                        Cuphead.getCuphead().moveDown();
                        break;
                    case "Right":
                        Cuphead.getCuphead().moveRight();
                        break;
                    case "Left":
                        Cuphead.getCuphead().moveLeft();
                        break;
                    case "Space":
                        try {
                            GamePageController.this.gameController.cupheadShoot(GamePageController.this.pane);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Tab":
                        try {
                            GamePageController.this.gameController.changeCupheadArmourType(pane);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Enter":
                        GamePageController.this.gameController.convertToRocket(pane);
                        break;
                    case "Esc":
                        try {
                            GamePageController.this.pauseGame();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setImageToCuphead() throws MalformedURLException {
        Cuphead cuphead = Cuphead.getCuphead();
        cuphead.setHitPointsLeft(GameDatabase.getGameDatabase().getDifficulty().getHitPointsLeft());
        URL imageAddress = new URL(Main.class.getResource("/cuphead/cupheadFly/cupheadNormal.png").toExternalForm());
        Image image = new Image(imageAddress.toExternalForm());
        ImagePacker imageView = new ImagePacker(cuphead, image);
        imageView.setX(20);
        imageView.setY((pane.getPrefHeight() - imageView.getImage().getHeight() / 2));
        cuphead.setImageView(imageView);
        pane.getChildren().add(cuphead.getImageView());
    }

    private void playSceneBackgroundAnimation(){
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> setBackgroundPositions(xPosition.get()));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(200), new KeyValue(xPosition, -15000))
        );
        timeline.play();
        timelines.add(timeline);
    }

    private void setBackgroundPositions(double xPosition) {
        String style = "-fx-background-position: " +
                "left " + 8*xPosition/8 + "px bottom," +
                "left " + 8*xPosition/7 + "px bottom," +
                "left " + 8*xPosition/6 + "px bottom," +
                "left " + 8*xPosition/5 + "px bottom," +
                "left " + 8*xPosition/4 + "px bottom," +
                "left " + 8*xPosition/3 + "px bottom," +
                "left " + 8*xPosition/2 + "px bottom," +
                "left " + 8*xPosition + "px bottom;";
        this.pane.setStyle(style);
    }

    private void setImageToBoss() throws MalformedURLException {
        Boss boss = Boss.getBoss(this.pane);
        boss.setHitPointsLeft(100 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage());
        ImagePacker imageView = new ImagePacker(boss, new Image(new URL(Main.class.getResource("/cuphead/bossFly/1.png").toExternalForm()).toExternalForm(), 488.25, 381.75, false, false));
        imageView.setX(800);
        imageView.setY((pane.getPrefHeight() - imageView.getImage().getHeight() / 2));
        boss.setImageView(imageView);
        pane.getChildren().add(boss.getImageView());
        BossFlyAnimation animation = new BossFlyAnimation(this.pane);
        animation.play();
    }

    private void makeBossHealthProgressBar(){
        Rectangle rectangle = new Rectangle(800, 660, 200, 50);
        rectangle.setStyle("-fx-arc-height: 20; -fx-arc-width: 20; -fx-fill: #c2a9a9");
        pane.getChildren().add(rectangle);
    }

    private void makeRocketProgressBar(){
        Rectangle rectangle = new Rectangle(50, 50, 100, 20);
        rectangle.setStyle("-fx-arc-height: 10; -fx-arc-width: 10; -fx-fill: #c2a9a9");
        pane.getChildren().add(rectangle);
        long start = System.currentTimeMillis();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), ev -> {
            long now = System.currentTimeMillis();
            double width = (double) ((now - start) % 20000) / (double) 20000 * 100;
            if(width < 0.1){
                width = 100;
            }
            GamePageController.this.gameController.drawTheRocketBar(GamePageController.this.pane, width);
        }));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Cuphead.getCuphead().setCanConvertToRocket(true);
            }
        });
        timeline.setCycleCount(20000);
        timeline.play();
        timelines.add(timeline);
    }

    public void startASavedGame(Game game) throws MalformedURLException {
        Cuphead.getCuphead().getImageView().setX(game.getCupheadXPosition());
        Cuphead.getCuphead().getImageView().setY(game.getCupheadYPosition());
        Cuphead.getCuphead().setArmourType(game.getCupheadArmourType());
        Cuphead.getCuphead().setHitPointsLeft(game.getCupheadHitPointsLeft());
        Boss.getBoss(pane).setHitPointsLeft(game.getBossHitPointsLeft());
        GameDatabase.getGameDatabase().setGameScore(game.getGameScore());
        GameDifficulty gameDifficulty = null;
        switch (game.getGameDifficulty()){
            case "AMATEUR":
                gameDifficulty = GameDifficulty.AMATEUR;
                break;
            case "SEMI_PRO":
                gameDifficulty = GameDifficulty.SEMI_PRO;
                break;
            case "MASTER":
                gameDifficulty = GameDifficulty.MASTER;
                break;
            default:
                break;
        }
        GameDatabase.getGameDatabase().setDifficulty(gameDifficulty);
        if(game.getCupheadIsRocket()){
            this.gameController.convertToRocket(pane);
        }
        Boss.getBoss(pane).getImageView().setX(game.getBossXPosition());
        Boss.getBoss(pane).getImageView().setY(game.getBossYPosition());
        for(int i = 0; i < game.getCupheadBulletsXPosition().size(); i++){
            CupheadBullet cupheadBullet = new CupheadBullet(pane);
            cupheadBullet.getImageView().setX(game.getCupheadBulletsXPosition().get(i));
            cupheadBullet.getImageView().setY(game.getCupheadBulletsYPosition().get(i));
            BulletAnimation animation = new BulletAnimation(cupheadBullet, pane);
            animation.play();
        }
        for(int i = 0; i < game.getCupheadBombsXPosition().size(); i++){
            CupheadBomb cupheadBomb = new CupheadBomb(pane);
            cupheadBomb.getImageView().setX(game.getCupheadBombsXPosition().get(i));
            cupheadBomb.getImageView().setY(game.getCupheadBombsYPosition().get(i));
            BombAnimation animation = new BombAnimation(cupheadBomb, pane);
            animation.play();
        }
        for(int i = 0; i < game.getBossEggsXPosition().size(); i++){
            BossEgg bossEgg = new BossEgg(pane);
            bossEgg.getImageView().setY(game.getBossEggsYPosition().get(i));
            bossEgg.getImageView().setX(game.getBossEggsXPosition().get(i));
            EggAnimation animation = new EggAnimation(bossEgg, pane);
            animation.play();
        }
        for(int i = 0; i < game.getYellowMiniBossesXPosition().size(); i++){
            Miniboss miniboss = new Miniboss("yellow", game.getYellowMiniBossesXPosition().get(i), game.getYellowMiniBossesYPosition().get(i));
            MiniBossFlyAnimation animation = new MiniBossFlyAnimation(miniboss, pane);
            animation.play();
        }
        for(int i = 0; i < game.getPurpleMiniBossesXPosition().size(); i++){
            Miniboss miniboss = new Miniboss("purple", game.getPurpleMiniBossesXPosition().get(i), game.getPurpleMiniBossesYPosition().get(i));
            MiniBossFlyAnimation animation = new MiniBossFlyAnimation(miniboss, pane);
            animation.play();
        }


    }

    public void findSavedGameToPlay() throws MalformedURLException {
        ArrayList<Game> games = GameDatabase.getGameDatabase().getSavedGames();
        for(int i = 0; i < games.size(); i++){
            if(games.get(i).getToPlay()){
                games.get(i).setToPlay(false);
                startASavedGame(games.get(i));
                games.remove(i);
                GameController.writeSavedGamesListToFile();
                return;
            }
        }
    }

    private String findElapsedTime(){
        long now = System.currentTimeMillis();
        int seconds = (int) ((now - startTime) / 1000);
        return EndGamePageController.getTimeString(seconds);
    }

    public void pauseGame() throws MalformedURLException {
        Main.getPlayer().stop();
        boolean mute = Main.getPlayer().muteProperty().getValue();
        Main.setPlayer(new MediaPlayer(new Media(new URL(Main.class.getResource("/cuphead/music/0.mp3").toExternalForm()).toExternalForm())));
        Main.getPlayer().setMute(mute);
        Main.getPlayer().play();
        for (Transition animation : animations) {
            animation.stop();
        }
        for (Timeline timeline : timelines) {
            timeline.stop();
        }
        Game game = new Game(pane);
        BorderPane root = new BorderPane();
        Text score = new Text("Your score is: " + GameDatabase.getGameDatabase().getGameScore());
        score.setStyle("-fx-font-size: 25; -fx-fill: #dc6b25");
        Text hitPointsLeft = new Text("Hit points left: " + Cuphead.getCuphead().getHitPointsLeft());
        Text time = new Text("Time elapsed: " + findElapsedTime());
        time.setStyle("-fx-font-size: 25; -fx-fill: #dc6b25");
        if(Cuphead.getCuphead().getHitPointsLeft() > 2) {
            hitPointsLeft.setStyle("-fx-font-size: 25; -fx-fill: #dc6b25");
        }
        else{
            hitPointsLeft.setStyle("-fx-font-size: 25; -fx-fill: #dc2525");
        }
        root.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox box = new VBox();
        root.setCenter(box);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(score);
        box.getChildren().add(hitPointsLeft);
        box.getChildren().add(time);
        if(UserDatabase.getUserDatabase().getUsers().contains(GameDatabase.getGameDatabase().getPlayer()) && GameDatabase.getGameDatabase().getSavedGames().size() == 0){
            Button save = new Button();
            save.setText("Save Game");
            save.setPrefWidth(150);
            save.setPrefHeight(50);
            save.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameDatabase.getGameDatabase().getSavedGames().add(game);
                    GameDatabase.getGameDatabase().getAllSavedGames().add(game);
                    GameController.writeSavedGamesListToFile();
                    try {
                        Main.loadFxmlFile("MainPage");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            box.getChildren().add(save);
        }
        Button restart = new Button();
        restart.setText("Restart");
        restart.setPrefWidth(150);
        restart.setPrefHeight(50);
        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.loadFxmlFile("GamePage");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button endGame = new Button();
        endGame.setText("End Game");
        endGame.setPrefWidth(150);
        endGame.setPrefHeight(50);
        endGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.loadFxmlFile("MainPage");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button sounds = new Button();
        sounds.setText("Mute/Unmute Sounds");
        sounds.setPrefWidth(150);
        sounds.setPrefHeight(50);
        sounds.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Main.getPlayer().isMute()){
                    Main.getPlayer().setMute(false);
                    return;
                }
                Main.getPlayer().setMute(true);
            }
        });
        Button help = new Button();
        help.setText("Help");
        help.setPrefWidth(150);
        help.setPrefHeight(50);
        help.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    ImageView imageView = new ImageView(new Image(new URL(Main.class.getResource("/cuphead/keyNotes/notes.png").toExternalForm()).toExternalForm(), 541, 164, false, false));
                    root.getChildren().add(imageView);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        Button back = new Button("back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                game.setToPlay(true);
                GameDatabase.getGameDatabase().getSavedGames().add(game);
                try {
                    Main.loadFxmlFile("GamePage");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        box.getChildren().add(restart);
        box.getChildren().add(endGame);
        box.getChildren().add(sounds);
        box.getChildren().add(help);
        box.getChildren().add(back);
        Main.getScene().setRoot(root);
    }

    public static long getStartTime(){
        return startTime;
    }

}
