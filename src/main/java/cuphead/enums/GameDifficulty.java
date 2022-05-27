package cuphead.enums;

public enum GameDifficulty {

    AMATEUR(10, 50, 150, "AMATEUR"),
    SEMI_PRO(5, 100, 100, "SEMI_PRO"),
    MASTER(2, 150, 50, "MASTER");

    private int hitPointsLeft;
    private int vulnerabilityCoefficientPercentage;
    private int damageCoefficientPercentage;
    private String name;

    GameDifficulty(int hitPointsLeft, int vulnerabilityCoefficientPercentage, int damageCoefficientPercentage, String name){
        this.hitPointsLeft = hitPointsLeft;
        this.vulnerabilityCoefficientPercentage = vulnerabilityCoefficientPercentage;
        this.damageCoefficientPercentage = damageCoefficientPercentage;
        this.name = name;
    }

    public void setHitPointsLeft(int hitPointsLeft){
        this.hitPointsLeft = hitPointsLeft;
    }

    public void setVulnerabilityCoefficientPercentage(int vulnerabilityCoefficientPercentage){
        this.vulnerabilityCoefficientPercentage = vulnerabilityCoefficientPercentage;
    }

    public void setDamageCoefficientPercentage(int damageCoefficientPercentage){
        this.damageCoefficientPercentage = damageCoefficientPercentage;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getHitPointsLeft(){
        return this.hitPointsLeft;
    }

    public int getVulnerabilityCoefficientPercentage(){
        return this.vulnerabilityCoefficientPercentage;
    }

    public int getDamageCoefficientPercentage(){
        return this.damageCoefficientPercentage;
    }

    public String getName(){
        return this.name;
    }
}
