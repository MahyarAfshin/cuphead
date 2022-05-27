package cuphead.models;

import cuphead.views.utilities.ImagePacker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Boss {

    private static Boss boss;

    private ImagePacker imageView;
    private int hitPointsLeft;
    private Rectangle healthProgressBar;
    private Text hitPointsLeftPercentage;
    private boolean shootTime;

    public static Boss getBoss(Pane pane){
        if(boss == null){
            boss = new Boss(pane);
        }
        return boss;
    }

    private Boss(Pane pane){
        this.shootTime = false;
        this.hitPointsLeft = 100 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage();
        this.healthProgressBar = new Rectangle(800, 660, 200, 50);
        this.healthProgressBar.setStyle("-fx-arc-height: 20; -fx-arc-width: 20; -fx-fill: #a13434");
        this.hitPointsLeftPercentage = new Text(720, 700, "");
        this.hitPointsLeftPercentage.setText("100%");
        this.hitPointsLeftPercentage.setStyle("-fx-font-size: 30; -fx-fill: #838316");
        pane.getChildren().add(this.hitPointsLeftPercentage);
        pane.getChildren().add(healthProgressBar);
    }

    public boolean hitCuphead(){
        if(Cuphead.getCuphead().getIsVulnerable() && Cuphead.getCuphead().getImageView().getBoundsInParent().intersects(this.imageView.getBoundsInParent())){
            return true;
        }
        return false;
    }

    public void hitRocket(Pane pane) {
        this.hitPointsLeft -= 4;
        GameDatabase.getGameDatabase().setGameScore(GameDatabase.getGameDatabase().getGameScore() + 12);
        redrawHealthProgressBar(pane);
    }

    public void hitBomb(Pane pane){
        this.hitPointsLeft -= 2;
        GameDatabase.getGameDatabase().setGameScore(GameDatabase.getGameDatabase().getGameScore() + 6);
        redrawHealthProgressBar(pane);
    }

    public void redrawHealthProgressBar(Pane pane) {
        pane.getChildren().remove(healthProgressBar);
        pane.getChildren().remove(this.hitPointsLeftPercentage);
        this.healthProgressBar = new Rectangle(800, 660, 200 * hitPointsLeft / (100 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage()), 50);
        this.healthProgressBar.setStyle("-fx-arc-height: 20; -fx-arc-width: 20; -fx-fill: #a13434");
        pane.getChildren().add(healthProgressBar);
        this.hitPointsLeftPercentage = new Text(720, 700, "");
        this.hitPointsLeftPercentage.setText(String.valueOf(100 * hitPointsLeft / (100 * 100 / GameDatabase.getGameDatabase().getDifficulty().getDamageCoefficientPercentage())) + "%");
        this.hitPointsLeftPercentage.setStyle("-fx-font-size: 30; -fx-fill: #838316");
        pane.getChildren().add(this.hitPointsLeftPercentage);
    }

    public void hitBullet(Pane pane){
        this.hitPointsLeft -= 1;
        GameDatabase.getGameDatabase().setGameScore(GameDatabase.getGameDatabase().getGameScore() + 3);
        redrawHealthProgressBar(pane);
    }

    public void setImageView(ImagePacker imageView){
        this.imageView = imageView;
    }

    public ImageView getImageView(){
        return this.imageView;
    }

    public void setHitPointsLeft(int hitPointsLeft){
        this.hitPointsLeft = hitPointsLeft;
    }

    public int getHitPointsLeft(){
        return this.hitPointsLeft;
    }

    public void setShootTime(boolean shootTime){
        this.shootTime = shootTime;
    }

    public boolean getShootTime(){
        return this.shootTime;
    }

    public void setHealthProgressBar(Rectangle healthProgressBar){
        this.healthProgressBar = healthProgressBar;
    }

    public Rectangle getHealthProgressBar(){
        return this.healthProgressBar;
    }

}
