package cuphead.views.controllers;

import cuphead.controllers.GameController;
import cuphead.controllers.ScoreboardController;
import cuphead.controllers.UserController;
import cuphead.models.GameDatabase;
import cuphead.views.Main;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainPageController {

    public VBox error;
    private UserController userController = UserController.getUserController();
    private GameController gameController = GameController.getGameController();
    private ScoreboardController scoreboardController = ScoreboardController.getScoreboardController();

    public void exit(MouseEvent mouseEvent) throws IOException {
        this.userController.logoutUser();
        Main.loadFxmlFile("LoginPage");
    }

    public void profileMenu(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("ProfilePage");
    }

    public void setUserController(UserController userController){
        this.userController = userController;
    }

    public UserController getUserController(){
        return this.userController;
    }

    public void setScoreboardController(ScoreboardController scoreboardController){
        this.scoreboardController = scoreboardController;
    }

    public ScoreboardController getScoreboardController(){
        return this.scoreboardController;
    }

    public void scoreboard(MouseEvent mouseEvent) throws IOException {
        this.scoreboardController.updateScoreboard();
        Main.loadFxmlFile("ScoreboardPage");
    }

    public void settings(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("Settings");
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("GamePage");
    }

    public void resume(MouseEvent mouseEvent) throws IOException {
        if(gameController.getGameDatabase().getSavedGames().size() > 0) {
            gameController.getGameDatabase().getSavedGames().get(0).setToPlay(true);
            Main.loadFxmlFile("GamePage");
        }
        else{
            Text text = new Text("You don't have any saved Games!");
            text.setStyle("-fx-fill: #c23d3d");
            error.getChildren().clear();
            error.getChildren().add(text);
        }
    }
}
