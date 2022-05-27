package cuphead.views;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static Scene scene;
    private static MediaPlayer player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        player = new MediaPlayer(new Media(new URL(Main.class.getResource("/cuphead/music/0.mp3").toExternalForm()).toExternalForm()));
        player.play();
        loadFxmlFile("LoginPage");
        stage.setScene(scene);
        stage.setTitle("cuphead");
        stage.setResizable(false);
        stage.show();
    }

    public static void loadFxmlFile(String name) throws IOException {
        if(name.equals("GamePage")){
            BooleanProperty mute = player.muteProperty();
            player.stop();
            player = new MediaPlayer(new Media(new URL(Main.class.getResource("/cuphead/music/1.mp3").toExternalForm()).toExternalForm()));
            player.setMute(mute.getValue());
            player.play();
        }
        URL url = new URL(Main.class.getResource("/cuphead/fxml/" + name + ".fxml").toExternalForm());
        Parent root = FXMLLoader.load(url);
        if(scene == null){
            scene = new Scene(root);
        }
        scene.setRoot(root);
    }

    public static Scene getScene(){
        return scene;
    }

    public static void setPlayer(MediaPlayer player){
        Main.player = player;
    }

    public static MediaPlayer getPlayer(){
        return player;
    }
}

/*
import controllers.SceneController;
import utilities.MyScanner;
import views.LoginPageView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = MyScanner.getScanner();
        SceneController sceneController = SceneController.getSceneController();
        LoginPageView loginPageView = LoginPageView.getLoginPageView();
        loginPageView.setController();
        sceneController.setNextView(loginPageView);
        sceneController.run();
        scanner.close();
    }
}
 */
