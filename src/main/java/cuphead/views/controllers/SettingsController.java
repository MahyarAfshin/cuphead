package cuphead.views.controllers;

import cuphead.enums.GameDifficulty;
import cuphead.models.GameDatabase;
import cuphead.views.Main;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingsController {
    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("MainPage");
    }

    public void chooseGameDifficulty(MouseEvent mouseEvent) throws MalformedURLException {
        BorderPane root = new BorderPane();
        root.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox box = new VBox();
        root.setCenter(box);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        Button amateur = new Button();
        amateur.setText("Amateur");
        amateur.setPrefWidth(150);
        amateur.setPrefHeight(50);
        amateur.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameDatabase.getGameDatabase().setDifficulty(GameDifficulty.AMATEUR);
                try {
                    Main.loadFxmlFile("Settings");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button semiPro = new Button();
        semiPro.setText("Semi Pro");
        semiPro.setPrefWidth(150);
        semiPro.setPrefHeight(50);
        semiPro.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameDatabase.getGameDatabase().setDifficulty(GameDifficulty.SEMI_PRO);
                try {
                    Main.loadFxmlFile("Settings");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button master = new Button();
        master.setText("Master");
        master.setPrefWidth(150);
        master.setPrefHeight(50);
        master.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameDatabase.getGameDatabase().setDifficulty(GameDifficulty.MASTER);
                try {
                    Main.loadFxmlFile("Settings");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button back = new Button("back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.loadFxmlFile("Settings");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        box.getChildren().add(amateur);
        box.getChildren().add(semiPro);
        box.getChildren().add(master);
        box.getChildren().add(back);
        Main.getScene().setRoot(root);
    }

    public void chooseGameColor(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("ChooseGameColorPage");
    }

    public void changeSoundMuteProperty(MouseEvent mouseEvent) {
        if(Main.getPlayer().isMute()){
            Main.getPlayer().setMute(false);
            return;
        }
        Main.getPlayer().setMute(true);
    }
}
