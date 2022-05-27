package cuphead.models;


public class User {

    private String username;
    private String password;
    private Score score;
    private int avatarIndex;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.score = new Score();
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setScore(Score score){
        this.score = score;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public Score getScore(){
        return this.score;
    }

    public int getAmateurScore(){
        return this.score.getAmateurScore();
    }

    public int getSemiProScore(){
        return this.score.getSemiProScore();
    }

    public int getMasterScore(){
        return this.score.getMasterScore();
    }

    public int getTotalScore(){
        return this.score.getAmateurScore() + this.score.getSemiProScore() + this.score.getMasterScore();
    }

    public void setAvatarIndex(int avatarIndex){
        this.avatarIndex = avatarIndex;
    }

    public int getAvatarIndex(){
        return this.avatarIndex;
    }

}
