package cuphead.models;

public class Score {

    private int amateurScore;
    private int semiProScore;
    private int masterScore;

    public Score(){
        this.amateurScore = 0;
        this.semiProScore = 0;
        this.masterScore = 0;
    }

    public void setAmateurScore(int amateurScore){
        this.amateurScore = amateurScore;
    }

    public void setSemiProScore(int semiProScore){
        this.semiProScore = semiProScore;
    }

    public void setMasterScore(int masterScore){
        this.masterScore = masterScore;
    }

    public int getAmateurScore(){
        return this.amateurScore;
    }

    public int getSemiProScore(){
        return this.semiProScore;
    }

    public int getMasterScore(){
        return this.masterScore;
    }

    public int calculateTotalScore(){
        return this.amateurScore + this.masterScore + this.semiProScore;
    }
}
