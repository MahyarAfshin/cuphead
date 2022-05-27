package cuphead.views.controllers;

import cuphead.controllers.UserController;
import cuphead.models.User;
import cuphead.views.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;

public class LoginPageController {
    UserController userController = UserController.getUserController();
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private VBox usernameError;
    @FXML
    private VBox passwordError;


    public void register(MouseEvent mouseEvent) throws IOException {
        usernameValidityErrorHandling();
        usernameUniquenessErrorHandling();
        passwordValidityErrorHandling();
        if(usernameError.getChildren().isEmpty() && passwordError.getChildren().isEmpty()){
            this.userController.registerUser(this.username.getText(), this.password.getText());
            Main.loadFxmlFile("MainPage");
        }
    }

    public void usernameValidityErrorHandling(){
        String usernameText = this.username.getText();
        if(!userController.isUsernameValid(usernameText)){
            Text error = new Text("Username is not valid!");
            addAUsernameError(error);
        }
        else{
            this.usernameError.getChildren().clear();
        }
    }

    public void passwordValidityErrorHandling(){
        if(usernameError.getChildren().isEmpty()) {
            String passwordText = this.password.getText();
            if (!userController.isPasswordValid(passwordText)) {
                Text error = new Text("Password is not valid!");
                addAPasswordError(error);
            }
            else{
                this.passwordError.getChildren().clear();
            }
        }
    }

    public void usernameUniquenessErrorHandling(){
        if(usernameError.getChildren().isEmpty()) {
            String usernameText = this.username.getText();
            User user = this.userController.getUserDatabase().getUserByUsername(this.username.getText());
            if (user != null) {
                Text error = new Text("A user with this username already exists!");
                addAUsernameError(error);
            }
            else{
                this.usernameError.getChildren().clear();
            }
        }
    }

    public void addAUsernameError(Text error){
        error.setStyle("-fx-fill: #c23d3d");
        usernameError.getChildren().clear();
        usernameError.getChildren().add(error);
    }

    public void addAPasswordError(Text error){
        error.setStyle("-fx-fill: #c23d3d");
        passwordError.getChildren().clear();
        passwordError.getChildren().add(error);
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        usernameValidityErrorHandling();
        passwordValidityErrorHandling();
        loginErrorHandling();
        if(usernameError.getChildren().isEmpty() && passwordError.getChildren().isEmpty()){
            this.userController.loginUser(this.username.getText());
            Main.loadFxmlFile("MainPage");
        }
    }

    public void loginErrorHandling(){
        User user = userController.getUserDatabase().getUserByUsername(this.username.getText());
        if(usernameError.getChildren().isEmpty() && passwordError.getChildren().isEmpty()) {
            if(user != null) {
                if (!userController.checkPassword(user, this.password.getText())) {
                    Text error = new Text("Username and password didn't match!");
                    addAPasswordError(error);
                }
            }
            else{
                Text error = new Text("You should register first!");
                addAPasswordError(error);
            }
        }
    }

    public void registerAsGuest(MouseEvent mouseEvent) throws IOException {
        this.userController.registerAsGuest();
        Main.loadFxmlFile("MainPage");
    }
}
