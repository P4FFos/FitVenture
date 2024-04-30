package fitVenture.backend.user;

import fitVenture.backend.stats.RaceStats;
import fitVenture.backend.stats.Stats;
import java.util.HashMap;

public class User {
    // User class attributes and HashMap of stats to store Distance, Calories and Steps
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;
    private HashMap<String, Stats> statsMap;
    private HashMap<String, RaceStats> raceStats;

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
        this.statsMap = new HashMap<>();
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
        return statsMap;
    }

    public HashMap<String, RaceStats> getRaceStats() {
        return raceStats;
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
        this.statsMap = stats;
    }

    public void addStats(String newDate, Stats stats) {
        this.statsMap.put(newDate, stats);
    }

    public boolean containsDateInStats(String date){
        if (this.statsMap.containsKey(date)) return true;
        else return false;
    }

    public void updateStats(String steps, String distance, String calories, String date){ // Updates the stats of an already existing entry
        int newSteps = Integer.parseInt(steps);
        double newDistance = Math.round(Double.parseDouble(distance));
        double newCalories = Math.round(Double.parseDouble(calories));

        int oldSteps = Integer.parseInt(statsMap.get(date).getSteps());
        double oldDistance = Double.parseDouble(statsMap.get(date).getDistance());
        double oldCalories = Double.parseDouble(statsMap.get(date).getCalories());

        String updatedSteps = String.valueOf(oldSteps + newSteps);
        String updatedDistance = String.valueOf(oldDistance + newDistance);
        String updatedCalories = String.valueOf(oldCalories + newCalories);

        Stats stats = new Stats(updatedSteps, updatedDistance, updatedCalories);
        this.statsMap.put(date, stats);
    }

    public void setRaceStats(HashMap<String, RaceStats> raceStats) {
        this.raceStats = raceStats;
    }

    public void addRaceStats(String raceDate, RaceStats raceStats) {
        this.raceStats.put(raceDate, raceStats);
    }

}
