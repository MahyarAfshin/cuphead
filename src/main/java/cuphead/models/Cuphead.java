package cuphead.models;

import cuphead.controllers.GameController;
import cuphead.views.Main;
import cuphead.views.transitions.CupheadBlinkAnimation;
import cuphead.views.transitions.ExplodeAnimation;
import cuphead.views.utilities.ImagePacker;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Cuphead {

    private static Cuphead cuphead;

    private ImagePacker imageView;
    private ImagePacker fireImage;
    private int hitPointsLeft;
    private boolean isVulnerable;
    private String armourType; // this string could only be "BULLET" or "BOMB"
    private Rectangle rocketBar;
    private boolean canConvertToRocket;
    private boolean isRocket;

    public static Cuphead getCuphead() {
        if(cuphead == null){
            cuphead = new Cuphead();
        }
        return cuphead;
    }

    private Cuphead(){
        this.isVulnerable = true;
        this.armourType = "BULLET";
        this.canConvertToRocket = false;
        this.isRocket = false;
    }

    public void hit(Pane pane) throws IOException {
        hitPointsLeft -= 1;
        if(hitPointsLeft <= 0){
            GameController.getGameController().endGame(pane);
        }
        System.out.println(hitPointsLeft);
        CupheadBlinkAnimation animation = new CupheadBlinkAnimation();
        animation.play();
    }

    public void moveUp(){
        if(!hitTop()){
            this.imageView.setY(this.imageView.getY() - 50);
        }
    }

    public void moveDown(){
        if(!hitFloor()){
            this.imageView.setY(this.imageView.getY() + 50);
        }
    }

    public void moveLeft(){
        if(!hitLeftWall()){
            this.imageView.setX(this.imageView.getX() - 50);
        }
    }

    public void moveRight(){
        if(!hitRightWall()){
            this.imageView.setX(this.imageView.getX() + 50);
        }
    }

    public boolean hitLeftWall(){
        return this.imageView.getX() <= 0;
    }

    public boolean hitRightWall(){
        return this.imageView.getX() + this.imageView.getImage().getWidth() >= 1280;
    }

    public boolean hitTop(){
        return this.imageView.getY() <= 0;
    }

    public boolean hitFloor(){
        return this.imageView.getY() + this.imageView.getImage().getHeight() >= 720;
    }

    public boolean hitAsRocket(Pane pane) throws MalformedURLException {
        for (Node child : pane.getChildren()) {
            if(!child.equals(this.imageView) && child instanceof ImagePacker && !((((ImagePacker) child).getObject() instanceof CupheadBullet) || ((((ImagePacker) child).getObject() instanceof CupheadBomb))) && child.getBoundsInParent().intersects(this.imageView.getBoundsInParent())){
                if(((ImagePacker) child).getObject() instanceof Miniboss){
                    ((Miniboss) ((ImagePacker) child).getObject()).hitBomb();
                }
                else if(((ImagePacker) child).getObject() instanceof Boss){
                    ((Boss) ((ImagePacker) child).getObject()).hitRocket(pane);
                }
                else if(((ImagePacker) child).getObject() instanceof BossEgg){
                    pane.getChildren().remove(child);
                }
                ExplodeAnimation animation = new ExplodeAnimation(pane);
                animation.play();
                return true;
            }
        }
        return false;
    }

    public void setImageView(ImagePacker imageView){
        this.imageView = imageView;
    }

    public ImageView getImageView(){
        return this.imageView;
    }

    public void setFireImage(ImagePacker fireImage){
        this.fireImage = fireImage;
    }

    public ImageView getFireImage(){
        return this.fireImage;
    }

    public void setHitPointsLeft(int hitPointsLeft){
        this.hitPointsLeft = hitPointsLeft;
    }

    public int getHitPointsLeft(){
        return this.hitPointsLeft;
    }

    public void setIsVulnerable(boolean isVulnerable){
        this.isVulnerable = isVulnerable;
    }

    public boolean getIsVulnerable(){
        return this.isVulnerable;
    }

    public void setArmourType(String armourType){
        this.armourType = armourType;
    }

    public String getArmourType(){
        return this.armourType;
    }

    public void setRocketBar(Rectangle rocketBar){
        this.rocketBar = rocketBar;
    }

    public Rectangle getRocketBar(){
        return this.rocketBar;
    }

    public void setCanConvertToRocket(boolean canConvertToRocket){
        this.canConvertToRocket = canConvertToRocket;
    }

    public boolean getCanConvertToRocket(){
        return this.canConvertToRocket;
    }

    public void setIsRocket(boolean isRocket){
        this.isRocket = isRocket;
    }

    public boolean getIsRocket(){
        return this.isRocket;
    }

}
