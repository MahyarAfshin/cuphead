package cuphead.models;

import cuphead.views.Main;
import cuphead.views.utilities.ImagePacker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.MalformedURLException;
import java.net.URL;

public class Miniboss {

    private int hitPointsLeft;
    private String color; // the color could be just yellow and purple
    private ImagePacker imageView;

    public Miniboss(String color, double xPosition, double yPosition) throws MalformedURLException {
        this.color = color;
        this.imageView = new ImagePacker(this, new Image(new URL(Main.class.getResource("/cuphead/miniBossFly/" + this.color + "/0.png").toExternalForm()).toExternalForm()));
        this.hitPointsLeft = 2 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage();
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
    }

    public boolean hitCuphead(){
        if(Cuphead.getCuphead().getIsVulnerable() && Cuphead.getCuphead().getImageView().getBoundsInParent().intersects(this.imageView.getBoundsInParent())){
            return true;
        }
        return false;
    }

    public void hitBomb(){
        this.hitPointsLeft = 0;
    }

    public void hitBullet(){
        this.hitPointsLeft -= 1;
    }

    public void setImageView(ImagePacker imageView){
        this.imageView = imageView;
    }

    public ImageView getImageView(){
        return this.imageView;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    public void setHitPointsLeft(int hitPointsLeft){
        this.hitPointsLeft = hitPointsLeft;
    }

    public int getHitPointsLeft(){
        return this.hitPointsLeft;
    }
}
