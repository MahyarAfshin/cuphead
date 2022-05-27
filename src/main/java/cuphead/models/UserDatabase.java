package cuphead.models;

import java.util.ArrayList;

public class UserDatabase {

    private static UserDatabase userDatabase;

    private ArrayList<User> users;

    public static UserDatabase getUserDatabase(){
        if(userDatabase == null){
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    private UserDatabase(){
        this.users = new ArrayList<>();
    }

    public User getUserByUsername(String username){
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void setUsers(ArrayList<User> users){
        this.users = users;
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }
}
