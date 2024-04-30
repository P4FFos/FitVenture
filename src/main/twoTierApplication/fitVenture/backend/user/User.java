package fitVenture.backend.user;

import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.stats.Stats;
import java.util.HashMap;

public class User {
    // User class attributes and HashMap of stats to store Distance, Calories and Steps
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;
    private HashMap<String, Stats> stats;
    private HashMap<String, WeightGoal> weightGoals;

    // Empty constructor used by Jackson for Json deserializing
    public User() {
    }

    // User class constructor
    public User(String username, String password, String weight, String height, String name) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.name = name;
        this.stats = new HashMap<>();
        this.weightGoals = new HashMap<>();
    }

    // User class get methods
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

    public HashMap<String, Stats> getStats() {
        return stats;
    }

    public HashMap<String, WeightGoal> getWeightGoals() {
        return this.weightGoals;
    }

    // User class set methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStats(HashMap<String, Stats> stats) {
        this.stats = stats;
    }

    public void setWeightGoals(HashMap<String, WeightGoal> weightGoals) {
        this.weightGoals = weightGoals;
    }

    public void addStats(String newDate, Stats stats) {
        this.stats.put(newDate, stats);
    }

    public WeightGoal getWeightGoal(String key) {
        if (weightGoals.get(key) != null) {
            return weightGoals.get(key);
        }
        return null;
    }

    public void addWeightGoal(String key, WeightGoal weightGoal) {
        this.weightGoals.putIfAbsent(key, weightGoal);
    }

    // method to count total burned calories of a specific user
    // used for weight goals calculation of the progress bar
    public double getTotalBurnedCalories() {
        double totalBurnedCalories = 0;
        for (Stats stats : this.stats.values()) {
            double burnedCalories = Double.parseDouble(stats.getCalories());
            totalBurnedCalories += burnedCalories;
        }
        return totalBurnedCalories;
    }
}
