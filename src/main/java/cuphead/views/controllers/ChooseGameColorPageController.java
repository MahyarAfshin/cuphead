package cuphead.views.controllers;

import cuphead.controllers.GameController;
import cuphead.models.GameDatabase;
import cuphead.views.Main;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ChooseGameColorPageController {

    private GameDatabase gameDatabase = GameDatabase.getGameDatabase();

    public void setGameDatabase(GameDatabase gameDatabase){
        this.gameDatabase = gameDatabase;
    }

    public GameDatabase getGameDatabase(){
        return this.gameDatabase;
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("Settings");
    }

    public void makeGameColorful(MouseEvent mouseEvent) throws IOException {
        this.gameDatabase.setIsGrayScale(false);
        Main.loadFxmlFile("Settings");
    }

    public void makeGameGrayscale(MouseEvent mouseEvent) throws IOException {
        this.gameDatabase.setIsGrayScale(true);
        Main.loadFxmlFile("Settings");
    }
}
