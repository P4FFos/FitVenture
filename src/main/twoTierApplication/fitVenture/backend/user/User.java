package fitVenture.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fitVenture.backend.achievements.AchievementsList;
import fitVenture.backend.goal.RunningGoal;
import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.stats.RaceStats;
import fitVenture.backend.stats.Stats;
import fitVenture.backend.utils.DateUtil;
import java.util.HashMap;

// any unknown properties in JSON will be ignored (used because of the achievementsList)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    // User class attributes and HashMaps to store different types of stats
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;
    private HashMap<String, Stats> statsMap;
    private HashMap<String, RaceStats> raceStats;
    private HashMap<String, WeightGoal> weightGoal;
    private HashMap<String, RunningGoal> runningGoal;

    // json ignore used to ignore the list of achievements when serializing to the JSON file
    // since it is not needed to be stored in the JSON file
    @JsonIgnore
    private AchievementsList listOfAchievements;

    // Empty constructor used by Jackson for Json deserializing,
    // every time we run the application, the user object is created with the empty constructor
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
        listOfAchievements = new AchievementsList();
    }

    //#region User class get methods
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

    public AchievementsList getListOfAchievement() {
        return listOfAchievements;
    }

    public HashMap<String, Stats> getStats() {
        return statsMap;
    }

    public HashMap<String, RaceStats> getRaceStats() {
        return raceStats;
    }

    public HashMap<String, WeightGoal> getWeightGoal() {
        return weightGoal;
    }

    public HashMap<String, RunningGoal> getRunningGoal() {
        return runningGoal;
    }
    //#endregion User class get methods

    //#region User class set methods
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

    public void setRaceStats(HashMap<String, RaceStats> raceStats) {
        this.raceStats = raceStats;
    }

    public void setWeightGoal(HashMap<String, WeightGoal> weightGoal) {
        this.weightGoal = weightGoal;
    }

    public void setRunGoal(HashMap<String, RunningGoal> runGoal) {
        this.runningGoal = runGoal;
    }

    public void addStats(String newDate, Stats stats) {
        this.statsMap.put(newDate, stats);
    }

    public void addWeightGoal(String date, WeightGoal weightGoal) {
        this.weightGoal.put(date, weightGoal);
    }

    public void addRunGoal(String date, RunningGoal runningGoal) {
        this.runningGoal.put(date, runningGoal);
    }

    public void addRaceStats(String raceDate, RaceStats raceStats) {
        this.raceStats.put(raceDate, raceStats);
    }

    public boolean containsDateInStats(String date) {
        if (this.statsMap.containsKey(date)) return true;
        else return false;
    }
    //#endregion User class set methods

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

    // method to count total burned calories of a specific user for a specific period
    // used for weight goals calculation of the progress bar
    public double getTotalBurnedCalories(String goalDate) {
        // used to store the total burned calories for a specific period
        double totalBurnedCalories = 0.0;

        // for loop to iterate over each value of the stats HashMap
        for (String dateOfCreation : this.statsMap.keySet()) {
            Stats valueOfTheStat = this.statsMap.get(dateOfCreation);
            int dateOfStat = DateUtil.getIntegerOfSpecificDate(dateOfCreation);
            int goalStartDate = DateUtil.getIntegerOfSpecificDate(goalDate);

            // checks if date of the stat is bigger or equal to goal start date
            if (dateOfStat >= goalStartDate) {
                double burnedCalories = Double.parseDouble(valueOfTheStat.getCalories());
                totalBurnedCalories += burnedCalories;
            }
        }
        return totalBurnedCalories;
    }

    // method to count total ran distance of a specific user for a specific period
    // used for running goals calculation of the progress bar
    public double getTotalRanDistance(String goalDate) {
        double totalRanDistance = 0.0;

        // for loop to iterate over each value of the stats HashMap
        for (String dateOfCreation : this.statsMap.keySet()) {
            Stats valueOfTheStat = this.statsMap.get(dateOfCreation);
            int dateOfStat = DateUtil.getIntegerOfSpecificDate(dateOfCreation);
            int goalStartDate = DateUtil.getIntegerOfSpecificDate(goalDate);

            // checks if date of the stat is bigger or equal to goal start date
            if (dateOfStat >= goalStartDate) {
                double ranDistance = Double.parseDouble(valueOfTheStat.getDistance());
                totalRanDistance += ranDistance;
            }
        }
        return totalRanDistance;
    }
    
    // Gets the total calories burnt since the creation of the user
    public double totalCaloriesSinceStart(){
        double totalCalories = 0.0;

        // for loop to iterate over each value of the stats HashMap
        for (Stats stat : this.statsMap.values()) {
            totalCalories = totalCalories + Double.parseDouble(stat.getCalories());
        }
        return totalCalories;
    }

    // Gets the total distance traveled since the creation of the user
    public double totalDistanceSinceStart(){
        double totalDistance = 0.0;

        // for loop to iterate over each value of the stats HashMap
        for (Stats stat : this.statsMap.values()) {
            totalDistance = totalDistance + Double.parseDouble(stat.getDistance());
        }
        return totalDistance;
    }

    public void checkCompletedAchievements(){
        if (this.listOfAchievements == null){
            this.listOfAchievements = new AchievementsList();
        }
        this.listOfAchievements.checkCompletedAchievements(totalDistanceSinceStart(), totalCaloriesSinceStart());
    }
}
