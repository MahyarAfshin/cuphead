package cuphead.views.controllers;

import cuphead.controllers.UserController;
import cuphead.models.User;
import cuphead.views.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChangeUsernamePageController {

    private UserController userController = UserController.getUserController();

    @FXML
    private VBox usernameError;
    @FXML
    private TextField newUsername;

    public void changeUsername(MouseEvent mouseEvent) throws IOException {
        usernameValidityErrorHandling();
        if(usernameError.getChildren().isEmpty()){
            this.userController.changeUsername(this.userController.getGameDatabase().getPlayer(), this.newUsername.getText());
            Main.loadFxmlFile("MainPage");
        }

    }

    public void usernameValidityErrorHandling(){
        String usernameText = this.newUsername.getText();
        if(!userController.isUsernameValid(usernameText)){
            Text error = new Text("Username is not valid!");
            addError(error);
        }
        else{
            this.usernameError.getChildren().clear();
        }
    }

    public void addError(Text error){
        error.setStyle("-fx-fill: #c23d3d");
        usernameError.getChildren().clear();
        usernameError.getChildren().add(error);
    }

    public void setUserController(UserController userController){
        this.userController = userController;
    }

    public UserController getUserController(){
        return this.userController;
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("ProfilePage");
    }
}
