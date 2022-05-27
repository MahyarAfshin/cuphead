package cuphead.views.controllers;

import cuphead.controllers.UserController;
import cuphead.models.GameDatabase;
import cuphead.views.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfilePageController {

    private UserController userController = UserController.getUserController();

    public void deleteAccount(MouseEvent mouseEvent) throws IOException {
        this.userController.logoutUser();
        this.userController.deleteUser(this.userController.getGameDatabase().getPlayer());
        Main.loadFxmlFile("LoginPage");
    }

    public void setUserController(UserController userController){
        this.userController = userController;
    }

    public UserController getUserController(){
        return this.userController;
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        this.userController.logoutUser();
        Main.loadFxmlFile("LoginPage");
    }

    public void changeUsername(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("ChangeUsernamePage");
    }

    public void changePassword(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("ChangePasswordPage");
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("MainPage");
    }


    public void showCurrentAvatar(MouseEvent mouseEvent) throws MalformedURLException {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(new URL(Main.class.getResource("/cuphead/css/Game.css").toExternalForm()).toExternalForm());
        VBox box = new VBox();
        Circle avatar = new Circle();
        URL imageAddress = new URL(Main.class.getResource("/cuphead/avatars/avatar" + GameDatabase.getGameDatabase().getPlayer().getAvatarIndex() + ".png").toExternalForm());
        Image image = new Image(imageAddress.toExternalForm());
        avatar.setFill(new ImagePattern(image));
        avatar.setRadius(150);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        borderPane.setCenter(box);
        Button button = new Button("back");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.loadFxmlFile("ProfilePage");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        box.getChildren().add(avatar);
        box.getChildren().add(button);
        Main.getScene().setRoot(borderPane);
    }
}
