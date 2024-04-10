package fitVenture.backend;

import fitVenture.backend.exceptions.RegistrationException;
import fitVenture.backend.user.User;

import java.util.HashMap;

public class FitVenture {
    // A HashMap to store all the users
    private HashMap<String, User> users;

    // Constructor
    public FitVenture() {
        users = new HashMap<>();
    }

    // get method to get hashMap of users
    public HashMap<String, User> getUsers() {
        return users;
    }

    // set method to get hashMap of users
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    // to add a user to the HashMap
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // to check if a user is in the HashMap
    // if no - register the user
    // if yes - return an error message based on the condition of the input
    public boolean register(String username, String password, String weight, String height) throws RegistrationException {
        boolean isRegistered = false;
        if (username.equals("") && password.equals("")) {
            throw new RegistrationException("Input the username or password");
        }
        if (!(username instanceof String) || !(password instanceof String)){
            throw new RegistrationException("Wrong username or password format");
        }
        if (users.containsKey(username)) {
            throw new RegistrationException("Username already exists. Try again");
        } else if (!username.equals("") && !password.equals("") && username instanceof String && password instanceof String){
            User user = new User(username, password, weight, height);
            addUser(user);
            isRegistered = true;
        }
        return isRegistered;
    }
}