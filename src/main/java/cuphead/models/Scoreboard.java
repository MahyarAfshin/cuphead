package cuphead.models;

import java.util.ArrayList;

public class Scoreboard {

    private static Scoreboard scoreboard;

    private ArrayList<User> amateurScoreboard;
    private ArrayList<User> semiProScoreboard;
    private ArrayList<User> masterScoreboard;
    private ArrayList<User> totalScoreboard;

    public static Scoreboard getScoreboard(){
        if(scoreboard == null){
            scoreboard = new Scoreboard();
        }
        return scoreboard;
    }

    private Scoreboard(){
        this.amateurScoreboard = new ArrayList<>();
        this.masterScoreboard = new ArrayList<>();
        this.semiProScoreboard = new ArrayList<>();
        this.totalScoreboard = new ArrayList<>();
    }

    public void setAmateurScoreboard(ArrayList<User> amateurScoreboard){
        this.amateurScoreboard = amateurScoreboard;
    }

    public ArrayList<User> getAmateurScoreboard(){
        return this.amateurScoreboard;
    }

    public void setSemiProScoreboard(ArrayList<User> semiProScoreboard){
        this.semiProScoreboard = semiProScoreboard;
    }

    public ArrayList<User> getSemiProScoreboard(){
        return this.semiProScoreboard;
    }

    public void setMasterScoreboard(ArrayList<User> masterScoreboard){
        this.masterScoreboard = masterScoreboard;
    }

    public ArrayList<User> getMasterScoreboard(){
        return this.masterScoreboard;
    }

    public void setTotalScoreboard(ArrayList<User> totalScoreboard){
        this.totalScoreboard = totalScoreboard;
    }

    public ArrayList<User> getTotalScoreboard(){
        return this.totalScoreboard;
    }

}
