package fitVenture.backend;

import fitVenture.backend.exceptions.LoginException;
import fitVenture.backend.exceptions.RegistrationException;
import fitVenture.backend.exceptions.SaveDataException;
import fitVenture.backend.stats.Stats;
import fitVenture.backend.stats.RaceStats;
import fitVenture.backend.user.User;

import java.util.Date;
import java.util.HashMap;

import static fitVenture.backend.utils.Current_Date.getDateToday;

public class FitVenture {
    // A HashMap to store all the users
    private HashMap<String, User> users;

    // Constructor
    public FitVenture() {
        users = new HashMap<>();
    }

    // Get the HashMap of users
    public HashMap<String, User> getUsers() {
        return users;
    }

    // Set new HashMap of users (used by Jackson for Json deserializing)
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    // Add a user to the HashMap
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // Method to get user
    public User getUser(String username) {
        return users.get(username);
    }

    // Checks if a user is in the HashMap
    // if no - register the user
    // if yes - return an error message based on the condition of the input
    public boolean register(String username, String password, String weight, String height, String name) throws RegistrationException {
        boolean isRegistered = false;
        if (username.isEmpty() && password.isEmpty()) {
            throw new RegistrationException("Input the username and password");
        }
        if (username.isEmpty()) {
            throw new RegistrationException("Input the username");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Input the password");
        }
        if (users.containsKey(username)) {
            throw new RegistrationException("Such user already exists. Try again");
        } else {
            User user = new User(username, password, weight, height, name);
            addUser(user);
            isRegistered = true;
        }
        return isRegistered;
    }

    // Verifies the user
    // if user is verified - return true
    // if user is not verified - return an error message based on the condition of the input
    public boolean verifyUser(String username, String password) throws LoginException {
        boolean correctUsername = false;
        boolean correctPassword = false;
        if (password.isEmpty() && username.isEmpty()) {
            throw new LoginException("Input the username and password");
        }
        if (password.isEmpty()) {
            throw new LoginException("Failed. Input the password");
        }
        if (username.isEmpty()) {
            throw new LoginException("Failed. Input the username");
        }
        if (!users.containsKey(username)) {
            throw new LoginException("Failed. Invalid username ");
        } else {
            correctUsername = true;
            User user = users.get(username);
            if (!user.getPassword().equals(password)) {
                throw new LoginException("Failed. Invalid password");
            } else {
                correctPassword = true;
            }
            return correctUsername && correctPassword;
        }
    }

    // Saves stats data of the user to the HashMap
    public void saveStatsData(String distance, String steps, String calories, String userUsername) throws SaveDataException {
        if (distance.isEmpty() || steps.isEmpty() || calories.isEmpty()) {
            throw new SaveDataException("Input the distance, steps or calories");
        } else {
            String currentTime = getDateToday(new Date());
            User currentUser = getUser(userUsername);

            if(currentUser.containsDateInStats(currentTime)){ // Checks if entry already exits
                currentUser.updateStats(steps, distance, calories, currentTime);
            }else {
                Stats stats = new Stats(steps, distance, calories);
                currentUser.addStats(currentTime, stats);
            }
            currentUser.checkCompletedAchievements();
        }
    }

    // Saves race stats data of the user to the HashMap
    public void saveRaceStatsData(String startTime, String endTime, String raceDuration, String distance, String steps, String calories, String userUsername) throws SaveDataException {
        if (distance.isEmpty() || steps.isEmpty() || calories.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || raceDuration.isEmpty()) {
            throw new SaveDataException("Failed, nothing to save");
        } else {
            User currentUser = getUser(userUsername);
            RaceStats raceStats = new RaceStats(steps, distance, calories, startTime, endTime, raceDuration);
            currentUser.addRaceStats(getDateToday(new Date()), raceStats);
        }
    }
}