package cuphead.models;

import cuphead.views.utilities.ImagePacker;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Game { // this class is used to only save games

    private String username;
    private boolean toPlay;
    private double cupheadXPosition;
    private double cupheadYPosition;
    private boolean cupheadIsRocket;
    private double bossXPosition;
    private double bossYPosition;
    private int cupheadHitPointsLeft;
    private int bossHitPointsLeft;
    private int gameScore;
    private String gameDifficulty;
    private String cupheadArmourType;
    private ArrayList<Double> cupheadBulletsXPosition = new ArrayList<>();
    private ArrayList<Double> cupheadBulletsYPosition = new ArrayList<>();
    private ArrayList<Double> cupheadBombsXPosition = new ArrayList<>();
    private ArrayList<Double> cupheadBombsYPosition = new ArrayList<>();
    private ArrayList<Double> bossEggsXPosition = new ArrayList<>();
    private ArrayList<Double> bossEggsYPosition = new ArrayList<>();
    private ArrayList<Double> yellowMiniBossesXPosition = new ArrayList<>();
    private ArrayList<Double> yellowMiniBossesYPosition = new ArrayList<>();
    private ArrayList<Double> purpleMiniBossesXPosition = new ArrayList<>();
    private ArrayList<Double> purpleMiniBossesYPosition = new ArrayList<>();

    public Game(Pane pane){
        this.username = GameDatabase.getGameDatabase().getPlayer().getUsername();
        this.gameScore = GameDatabase.getGameDatabase().getGameScore();
        this.toPlay = false;
        this.cupheadXPosition = Cuphead.getCuphead().getImageView().getX();
        this.cupheadYPosition = Cuphead.getCuphead().getImageView().getY();
        this.cupheadIsRocket = Cuphead.getCuphead().getIsRocket();
        this.bossXPosition = Boss.getBoss(pane).getImageView().getX();
        this.bossYPosition = Boss.getBoss(pane).getImageView().getY();
        this.cupheadArmourType = Cuphead.getCuphead().getArmourType();
        this.cupheadHitPointsLeft = Cuphead.getCuphead().getHitPointsLeft();
        this.bossHitPointsLeft = Boss.getBoss(pane).getHitPointsLeft();
        this.gameDifficulty = GameDatabase.getGameDatabase().getDifficulty().getName();
        for(int i = 0; i < pane.getChildren().size(); i++){
            Node child = pane.getChildren().get(i);
            if(child instanceof ImagePacker){
                Object object = ((ImagePacker) child).getObject();
                if(object instanceof CupheadBullet){
                    cupheadBulletsXPosition.add(((CupheadBullet) object).getImageView().getX());
                    cupheadBulletsYPosition.add(((CupheadBullet) object).getImageView().getY());
                }
                else if(object instanceof CupheadBomb){
                    cupheadBombsXPosition.add(((CupheadBomb) object).getImageView().getX());
                    cupheadBombsYPosition.add(((CupheadBomb) object).getImageView().getY());
                }
                else if(object instanceof BossEgg){
                    bossEggsXPosition.add(((BossEgg) object).getImageView().getX());
                    bossEggsYPosition.add(((BossEgg) object).getImageView().getY());
                }
                else if(object instanceof Miniboss){
                    if(((Miniboss) object).getColor().equals("yellow")){
                        yellowMiniBossesXPosition.add(((Miniboss) object).getImageView().getX());
                        yellowMiniBossesYPosition.add(((Miniboss) object).getImageView().getY());
                    }
                    else{
                        purpleMiniBossesXPosition.add(((Miniboss) object).getImageView().getX());
                        purpleMiniBossesYPosition.add(((Miniboss) object).getImageView().getY());
                    }
                }
            }
        }
    }

    public void setToPlay(boolean toPlay){
        this.toPlay = toPlay;
    }


    public boolean getToPlay() {
        return toPlay;
    }

    public double getCupheadXPosition() {
        return cupheadXPosition;
    }

    public double getCupheadYPosition() {
        return cupheadYPosition;
    }

    public boolean getCupheadIsRocket() {
        return cupheadIsRocket;
    }

    public double getBossXPosition() {
        return bossXPosition;
    }

    public double getBossYPosition() {
        return bossYPosition;
    }

    public ArrayList<Double> getCupheadBulletsXPosition() {
        return cupheadBulletsXPosition;
    }

    public ArrayList<Double> getCupheadBulletsYPosition() {
        return cupheadBulletsYPosition;
    }

    public ArrayList<Double> getCupheadBombsXPosition() {
        return cupheadBombsXPosition;
    }

    public ArrayList<Double> getCupheadBombsYPosition() {
        return cupheadBombsYPosition;
    }

    public ArrayList<Double> getBossEggsXPosition() {
        return bossEggsXPosition;
    }

    public ArrayList<Double> getBossEggsYPosition() {
        return bossEggsYPosition;
    }

    public ArrayList<Double> getYellowMiniBossesXPosition() {
        return yellowMiniBossesXPosition;
    }

    public ArrayList<Double> getYellowMiniBossesYPosition() {
        return yellowMiniBossesYPosition;
    }

    public ArrayList<Double> getPurpleMiniBossesXPosition() {
        return purpleMiniBossesXPosition;
    }

    public ArrayList<Double> getPurpleMiniBossesYPosition() {
        return purpleMiniBossesYPosition;
    }

    public String getCupheadArmourType(){
        return this.cupheadArmourType;
    }

    public int getCupheadHitPointsLeft() {
        return cupheadHitPointsLeft;
    }

    public int getBossHitPointsLeft() {
        return bossHitPointsLeft;
    }

    public String getGameDifficulty() {
        return gameDifficulty;
    }

    public String getUsername(){
        return this.username;
    }

    public int getGameScore(){
        return this.gameScore;
    }
}
