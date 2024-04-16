package fitVenture.backend;

import fitVenture.backend.exceptions.LoginException;
import fitVenture.backend.exceptions.RegistrationException;
import fitVenture.backend.exceptions.SaveDataException;
import fitVenture.backend.stats.Stats;
import fitVenture.backend.user.User;

import java.util.HashMap;

public class FitVenture {
    // A HashMap to store all the users
    private HashMap<String, User> users;

    // Constructor
    public FitVenture() {
        users = new HashMap<>();
    }

    // get the HashMap of users
    public HashMap<String, User> getUsers() {
        return users;
    }

    // set new HashMap of users (used by Jackson for Json deserializing)
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    // to add a user to the HashMap
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // method to get user
    public User getUser(String username) {
        return users.get(username);
    }

    // to check if a user is in the HashMap
    // if no - register the user
    // if yes - return an error message based on the condition of the input
    public boolean register(String username, String password, String weight, String height, String name) throws RegistrationException {
        boolean isRegistered = false;
        if (username.equals("") && password.equals("")) {
            throw new RegistrationException("Input the username or password");
        }
        if (!(username instanceof String) || !(password instanceof String)) {
            throw new RegistrationException("Wrong username or password format");
        }
        if (users.containsKey(username)) {
            throw new RegistrationException("Username already exists. Try again");
        } else if (!username.equals("") && !password.equals("") && username instanceof String && password instanceof String) {
            User user = new User(username, password, weight, height, name);
            addUser(user);
            isRegistered = true;
        }
        return isRegistered;
    }

    // to verify the user
    // if user is verified - return true
    // if user is not verified - return an error message based on the condition of the input
    public boolean verifyUser(String username, String password) throws LoginException {
        boolean correctUsername = false;
        boolean correctPassword = false;
        if (password.equals("") && username.equals("")) {
            throw new LoginException("Input the username or password");
        }
        if (password.equals("")) {
            throw new LoginException("Failed. Input the password");
        }
        if (username.equals("")) {
            throw new LoginException("Failed. Input the username");
        }
        if (!users.containsKey(username) && !username.equals("")) {
            throw new LoginException("Failed. Invalid username ");
        } else {
            correctUsername = true;
            User user = users.get(username);
            if (!user.getPassword().equals(password) && !password.equals("")) {
                throw new LoginException("Failed. Invalid password");
            } else {
                correctPassword = true;
            }
            return correctUsername && correctPassword;
        }
    }

    public void saveStatsData(String distance, String steps, String calories, String userUsername) throws SaveDataException {
        if (distance.equals("") || steps.equals("") || calories.equals("")) {
            throw new SaveDataException("Input the distance, steps or calories");
        } else {
            User currentUser = getUser(userUsername);
            String currentUserUsername = currentUser.getUsername();
            Stats stats = new Stats(steps, distance, calories, userUsername);
            currentUser.addStats(currentUserUsername, stats);
        }
    }
}