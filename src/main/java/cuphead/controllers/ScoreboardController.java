package cuphead.controllers;

import cuphead.enums.GameDifficulty;
import cuphead.models.GameDatabase;
import cuphead.models.Scoreboard;
import cuphead.models.User;
import cuphead.models.UserDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardController {

    private static ScoreboardController scoreboardController;

    private Scoreboard scoreboard;

    public static ScoreboardController getScoreboardController(){
        if(scoreboardController == null){
            scoreboardController = new ScoreboardController();
        }
        return scoreboardController;
    }

    private ScoreboardController(){
        this.scoreboard = Scoreboard.getScoreboard();
    }

    public void updateScoreboard(){
        ArrayList<User> amateurScoreboardList = sortAmateurScoreboard();
        ArrayList<User> semiProScoreboardList = sortSemiProScoreboard();
        ArrayList<User> masterScoreboardList = sortMasterScoreboard();
        ArrayList<User> totalScoreboardList = sortTotalScoreScoreboard();
        this.scoreboard.setAmateurScoreboard(amateurScoreboardList);
        this.scoreboard.setSemiProScoreboard(semiProScoreboardList);
        this.scoreboard.setMasterScoreboard(masterScoreboardList);
        this.scoreboard.setTotalScoreboard(totalScoreboardList);
    }

    private ArrayList<User> sortAmateurScoreboard(){
        ArrayList<User> sortedTopUsers = new ArrayList<>();
        ArrayList<User> users = UserDatabase.getUserDatabase().getUsers();
        if(!users.contains(GameDatabase.getGameDatabase().getPlayer())){
            users.add(GameDatabase.getGameDatabase().getPlayer());
        }
        sortAmateurScoreUsers(users);
        for(int i = 0; i < users.size(); i++){
            if(i < 10){
                sortedTopUsers.add(users.get(i));
            }
        }
        return sortedTopUsers;
    }

    private ArrayList<User> sortSemiProScoreboard(){
        ArrayList<User> sortedTopUsers = new ArrayList<>();
        ArrayList<User> users = UserDatabase.getUserDatabase().getUsers();
        if(!users.contains(GameDatabase.getGameDatabase().getPlayer())){
            users.add(GameDatabase.getGameDatabase().getPlayer());
        }
        sortSemiProScoreUsers(users);
        for(int i = 0; i < users.size(); i++){
            if(i < 10){
                sortedTopUsers.add(users.get(i));
            }
        }
        return sortedTopUsers;
    }

    private ArrayList<User> sortMasterScoreboard(){
        ArrayList<User> sortedTopUsers = new ArrayList<>();
        ArrayList<User> users = UserDatabase.getUserDatabase().getUsers();
        if(!users.contains(GameDatabase.getGameDatabase().getPlayer())){
            users.add(GameDatabase.getGameDatabase().getPlayer());
        }
        sortMasterScoreUsers(users);
        for(int i = 0; i < users.size(); i++){
            if(i < 10){
                sortedTopUsers.add(users.get(i));
            }
        }
        return sortedTopUsers;
    }

    private ArrayList<User> sortTotalScoreScoreboard(){
        ArrayList<User> sortedTopUsers = new ArrayList<>();
        ArrayList<User> users = UserDatabase.getUserDatabase().getUsers();
        if(!users.contains(GameDatabase.getGameDatabase().getPlayer())){
            users.add(GameDatabase.getGameDatabase().getPlayer());
        }
        sortTotalScoreUsers(users);
        for(int i = 0; i < users.size(); i++){
            if(i < 10){
                sortedTopUsers.add(users.get(i));
            }
        }
        return sortedTopUsers;
    }

    public void setScoreboard(Scoreboard scoreboard){
        this.scoreboard = scoreboard;
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    private void sortAmateurScoreUsers(ArrayList<User> users){
        Comparator<User> comparator = Comparator.comparing(User::getAmateurScore).reversed().thenComparing(User::getUsername);
        Collections.sort(users, comparator);
    }

    private void sortSemiProScoreUsers(ArrayList<User> users){
        Comparator<User> comparator = Comparator.comparing(User::getSemiProScore).reversed().thenComparing(User::getUsername);
        Collections.sort(users, comparator);
    }

    private void sortMasterScoreUsers(ArrayList<User> users){
        Comparator<User> comparator = Comparator.comparing(User::getMasterScore).reversed().thenComparing(User::getUsername);
        Collections.sort(users, comparator);
    }

    private void sortTotalScoreUsers(ArrayList<User> users){
        Comparator<User> comparator = Comparator.comparing(User::getTotalScore).reversed().thenComparing(User::getUsername);
        Collections.sort(users, comparator);
    }

    public void updatePlayerScore(int gameScore, GameDifficulty difficulty, User player){
        if(difficulty == GameDifficulty.AMATEUR){
            if(gameScore > player.getAmateurScore()){
                player.getScore().setAmateurScore(gameScore);
            }
        }
        if(difficulty == GameDifficulty.SEMI_PRO){
            if(gameScore > player.getSemiProScore()){
                player.getScore().setSemiProScore(gameScore);
            }
        }
        if(difficulty == GameDifficulty.MASTER){
            if(gameScore > player.getMasterScore()){
                player.getScore().setMasterScore(gameScore);
            }
        }
    }
}
