package fitVenture.backend.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.stats.Steps;
import fitVenture.backend.utils.Current_Date; 

public class User {
    // user class attributes
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;
    private Map<String, Stats> savedStats;

    // Empty constructor used by Jackson for Json deserializing
    public User(){}

    // user class constructor
    public User(String username, String password, String weight, String height, String name) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.name = name;
        this.savedStats = new HashMap<>();
    }

    // user class get methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public double getSteps(String date) {
        return savedStats.get(date).getSteps();
    }

    public double getDistance(String date) {
        return savedStats.get(date).getDistance();
    }

    public double getCalories(String date) {
        return savedStats.get(date).getCalories();
    }

    public void saveSteps(Stats stats) {
        savedStats.put(Current_Date.getDateToday(new Date()), stats);
    }
}
