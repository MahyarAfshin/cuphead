package cuphead.views.controllers;

import cuphead.controllers.ScoreboardController;
import cuphead.models.User;
import cuphead.views.Main;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ScoreboardPageController {

    private ScoreboardController scoreboardController = ScoreboardController.getScoreboardController();

    public void setScoreboardController(ScoreboardController scoreboardController){
        this.scoreboardController = scoreboardController;
    }

    public ScoreboardController getScoreboardController(){
        return this.scoreboardController;
    }

    public void showAmateurScoreboard(MouseEvent mouseEvent) throws MalformedURLException {
        ArrayList<User> users = this.scoreboardController.getScoreboard().getAmateurScoreboard();
        BorderPane root = new BorderPane();
        root.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox pack = new VBox();
        pack.setAlignment(Pos.CENTER);
        pack.setSpacing(10);
        root.setCenter(pack);
        for(int i = 0; i < users.size(); i++){
            Text text = new Text(users.get(i).getUsername() + " : "+users.get(i).getAmateurScore());
            addTextToScoreboard(pack, text, i+1);
        }
        addBackButton(root, pack);
    }


    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("MainPage");
    }

    public void showSemiProScoreboard(MouseEvent mouseEvent) throws MalformedURLException {
        ArrayList<User> users = this.scoreboardController.getScoreboard().getSemiProScoreboard();
        BorderPane root = new BorderPane();
        root.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox pack = new VBox();
        pack.setAlignment(Pos.CENTER);
        pack.setSpacing(10);
        root.setCenter(pack);
        for(int i = 0; i < users.size(); i++){
            Text text = new Text(users.get(i).getUsername() + " : "+users.get(i).getSemiProScore());
            addTextToScoreboard(pack, text, i+1);
        }
        addBackButton(root, pack);
    }

    public void showMasterScoreboard(MouseEvent mouseEvent) throws MalformedURLException {
        ArrayList<User> users = this.scoreboardController.getScoreboard().getMasterScoreboard();
        BorderPane root = new BorderPane();
        root.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox pack = new VBox();
        pack.setAlignment(Pos.CENTER);
        pack.setSpacing(10);
        root.setCenter(pack);
        for(int i = 0; i < users.size(); i++){
            Text text = new Text(users.get(i).getUsername() + " : "+users.get(i).getMasterScore());
            addTextToScoreboard(pack, text, i+1);
        }
        addBackButton(root, pack);
    }

    private void addBackButton(BorderPane root, VBox pack) {
        Button back = new Button();
        back.setText("back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.loadFxmlFile("ScoreboardPage");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pack.getChildren().add(back);
        Main.getScene().setRoot(root);
    }

    public void showTotalScoreboard(MouseEvent mouseEvent) throws MalformedURLException {
        ArrayList<User> users = this.scoreboardController.getScoreboard().getTotalScoreboard();
        BorderPane root = new BorderPane();
        root.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox pack = new VBox();
        pack.setAlignment(Pos.CENTER);
        pack.setSpacing(10);
        root.setCenter(pack);
        for(int i = 0; i < users.size(); i++){
            Text text = new Text(users.get(i).getUsername() + " : "+users.get(i).getTotalScore());
            addTextToScoreboard(pack, text, i+1);
        }
        addBackButton(root, pack);
    }

    private void addTextToScoreboard(VBox pack, Text text, int rank) {
        text.setStyle("-fx-font-size: 20");
        VBox box = new VBox();
        switch (rank){
            case 1:
                box.setStyle("-fx-background-color: #b99c29");
                break;
            case 2:
                box.setStyle("-fx-background-color: #d9c7c7");
                break;
            case 3:
                box.setStyle("-fx-background-color: #d36812");
                break;
            default:
                box.setStyle("-fx-background-color: #29b9a8");
                break;
        }
        box.setMaxHeight(50);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(400);
        box.getChildren().add(text);
        pack.getChildren().add(box);
    }
}
