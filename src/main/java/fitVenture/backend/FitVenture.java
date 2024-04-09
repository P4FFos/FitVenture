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
    // if yes - return an error message
    public boolean register(String username, String password, String weight, String height) throws RegistrationException {
        if (users.containsKey(username)) {
            return false;
        } else if (!username.equals(null) && !password.equals(null)) {
            User user = new User(username, password, weight, height);
            addUser(user);
            return true;
        } else {
            return false;
        }
    }
}