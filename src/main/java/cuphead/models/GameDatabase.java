package cuphead.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cuphead.controllers.GameController;
import cuphead.enums.GameDifficulty;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameDatabase {

    private static GameDatabase gameDatabase;

    private User player;
    private GameDifficulty difficulty;
    private boolean isGrayScale;
    private ArrayList<Game> savedGames;
    private ArrayList<Game> allSavedGames;
    private int gameScore;

    public static GameDatabase getGameDatabase(){
        if(gameDatabase == null){
            gameDatabase = new GameDatabase();
        }
        return gameDatabase;
    }

    private GameDatabase(){
        this.difficulty = GameDifficulty.SEMI_PRO;
        this.isGrayScale = false;
        this.savedGames = new ArrayList<>();
        this.allSavedGames = new ArrayList<>();
    }

    public void readSavedGamesListFromFile() {
        try {
            String input = new String(Files.readAllBytes(Paths.get("src", "main", "resources", "cuphead", "json", "SavedGames.json")));
            ArrayList<Game> games = new Gson().fromJson(input, new TypeToken<List<Game>>() {
            }.getType());
            if (games == null) {
                games = new ArrayList<>();
            }
            this.savedGames = new ArrayList<>();
            for(int i = 0; i < games.size(); i++){
                if(games.get(i).getUsername().equals(this.player.getUsername())){
                    this.savedGames.add(games.get(i));
                }
            }
            //System.out.println(this.savedGames.get(0).getUsername());
            this.setAllSavedGames(games);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlayer(User player){
        this.player = player;
    }

    public User getPlayer(){
        return this.player;
    }

    public void setDifficulty(GameDifficulty gameDifficulty){
        this.difficulty = gameDifficulty;
    }

    public GameDifficulty getDifficulty(){
        return this.difficulty;
    }

    public void setIsGrayScale(boolean isGrayScale){
        this.isGrayScale = isGrayScale;
    }

    public boolean getIsGrayScale(){
        return this.isGrayScale;
    }

    public void setSavedGames(ArrayList<Game> savedGames){
        this.savedGames = savedGames;
    }

    public ArrayList<Game> getSavedGames(){
        return this.savedGames;
    }

    public void setAllSavedGames(ArrayList<Game> allSavedGames){
        this.allSavedGames = allSavedGames;
    }

    public ArrayList<Game> getAllSavedGames(){
        return this.allSavedGames;
    }

    public void setGameScore(int gameScore){
        this.gameScore = gameScore;
    }

    public int getGameScore(){
        return this.gameScore;
    }
}
