package cuphead.views.controllers;

import cuphead.controllers.UserController;
import cuphead.views.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChangePasswordPageController {

    private UserController userController = UserController.getUserController();

    @FXML
    private TextField newPassword;
    @FXML
    private TextField newPasswordConfirmation;
    @FXML
    private VBox passwordError;
    @FXML
    private VBox confirmationError;

    public void setUserController(UserController userController){
        this.userController = userController;
    }

    public UserController getUserController(){
        return this.userController;
    }

    public void changePassword(MouseEvent mouseEvent) throws IOException {
        passwordValidityErrorHandling();
        confirmationErrorHandling();
        if(passwordError.getChildren().isEmpty() && confirmationError.getChildren().isEmpty()){
            this.userController.changePassword(this.userController.getGameDatabase().getPlayer(), this.newPassword.getText());
            Main.loadFxmlFile("MainPage");
        }
    }

    public void passwordValidityErrorHandling(){
        String passwordText = this.newPassword.getText();
        if (!userController.isPasswordValid(passwordText)) {
            Text error = new Text("Password is not valid!");
            addAPasswordError(error);
        }
        else{
            this.passwordError.getChildren().clear();
        }
    }

    public void addAPasswordError(Text error){
        error.setStyle("-fx-fill: #c23d3d");
        passwordError.getChildren().clear();
        passwordError.getChildren().add(error);
    }

    public void confirmationErrorHandling(){
        if(this.passwordError.getChildren().isEmpty()){
            String passwordText = this.newPassword.getText();
            String confirmationText = this.newPasswordConfirmation.getText();
            if(!userController.checkPasswordConfirmation(passwordText, confirmationText)){
                Text error = new Text("Passwords didn't match!");
                addAConfirmationError(error);
            }
            else{
                this.confirmationError.getChildren().clear();
            }
        }
    }

    public void addAConfirmationError(Text error){
        error.setStyle("-fx-fill: #c23d3d");
        confirmationError.getChildren().clear();
        confirmationError.getChildren().add(error);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.loadFxmlFile("ProfilePage");
    }
}
