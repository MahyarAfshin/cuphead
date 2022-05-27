package cuphead.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cuphead.models.GameDatabase;
import cuphead.models.User;
import cuphead.models.UserDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {

    private static UserController userController;

    private UserDatabase userDatabase;
    private GameDatabase gameDatabase;

    public static UserController getUserController(){
        if(userController == null){
            userController = new UserController();
        }
        return userController;
    }

    private UserController(){
        this.userDatabase = UserDatabase.getUserDatabase();
        this.gameDatabase = GameDatabase.getGameDatabase();
        readUsersListFromFile();
    }

    public boolean isUsernameValid(String username){
        Matcher matcher = Pattern.compile("\\s*[a-zA-Z_@#$]+\\s*").matcher(username);
        return matcher.matches();
    }

    public boolean isPasswordValid(String password){
        if(password.length() < 8){
            return false;
        }
        Matcher numberMatcher = Pattern.compile(".*[0-9]+.*").matcher(password);
        Matcher lowerCaseAlphabetMatcher = Pattern.compile(".*[a-z]+.*").matcher(password);
        Matcher upperCaseAlphabetMatcher = Pattern.compile(".*[A-Z].*").matcher(password);
        Matcher specificCharacterMatcher = Pattern.compile(".*[@_$-]+.*").matcher(password);
        Matcher whiteSpaceMatcher = Pattern.compile(".*\\s+.*").matcher(password);
        return numberMatcher.matches() && lowerCaseAlphabetMatcher.matches() && upperCaseAlphabetMatcher.matches() && specificCharacterMatcher.matches() && !whiteSpaceMatcher.matches();
    }

    public void deleteUser(User user){
        this.userDatabase.getUsers().remove(user);
    }

    public void logoutUser(){
        this.gameDatabase.setPlayer(null);
    }

    public void registerUser(String username, String password) throws MalformedURLException {
        User user = new User(username, password);
        assignRandomAvatarToUser(user);
        this.userDatabase.addUser(user);
        GameDatabase.getGameDatabase().setPlayer(user);
        GameDatabase.getGameDatabase().readSavedGamesListFromFile();
        GameDatabase.getGameDatabase().setGameScore(0);
        writeUsersListToFile();
    }

    public void loginUser(String username){
        User user = this.userDatabase.getUserByUsername(username);
        GameDatabase.getGameDatabase().setPlayer(user);
        GameDatabase.getGameDatabase().readSavedGamesListFromFile();
        GameDatabase.getGameDatabase().setGameScore(0);
    }

    public void registerAsGuest() throws MalformedURLException {
        User user = new User("GUEST", "");
        assignRandomAvatarToUser(user);
        GameDatabase.getGameDatabase().setPlayer(user);
        GameDatabase.getGameDatabase().setGameScore(0);
    }

    public static void readUsersListFromFile() {
        try {
            String input = new String(Files.readAllBytes(Paths.get("src", "main", "resources", "cuphead", "json", "Users.json")));
            ArrayList<User> users = new Gson().fromJson(input, new TypeToken<List<User>>() {
            }.getType());
            if (users == null) {
                users = new ArrayList<>();
            }
            UserDatabase.getUserDatabase().setUsers(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeUsersListToFile() {
        File main = new File("src", "main");
        File resources = new File(main, "resources");
        File cuphead = new File(resources, "cuphead");
        File json = new File(cuphead, "json");
        File usersFile = new File(json, "Users.json");
        try {
            FileWriter userWriter = new FileWriter(usersFile);
            userWriter.write(new Gson().toJson(UserDatabase.getUserDatabase().getUsers()));
            userWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void changeUsername(User user, String newUsername){
        user.setUsername(newUsername);
        writeUsersListToFile();
    }

    public void changePassword(User user, String newPassword){
        user.setPassword(newPassword);
        writeUsersListToFile();
    }

    public boolean checkPasswordConfirmation(String newPassword, String passwordConfirmation){
        return newPassword.equals(passwordConfirmation);
    }

    public void assignRandomAvatarToUser(User user) throws MalformedURLException {
        Random rand = new Random();
        int number = rand.nextInt(9);
        user.setAvatarIndex(number);
    }

    public boolean checkPassword(User user, String password){
        return user.getPassword().equals(password);
    }

    public void setUserDatabase(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    public UserDatabase getUserDatabase(){
        return this.userDatabase;
    }

    public void setGameDatabase(GameDatabase gameDatabase){
        this.gameDatabase = gameDatabase;
    }

    public GameDatabase getGameDatabase(){
        return this.gameDatabase;
    }
}
