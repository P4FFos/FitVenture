package fitVenture.backend.stats;

import fitVenture.ui.FitVentureStart;

public class Stats {
    private String distance;
    private String steps;
    private String calories;
    private String userUsername = FitVentureStart.currentUser.getUsername();

    // Empty constructor used by Jackson for Json deserializing
    public Stats() {
    }

    public Stats(String steps, String distance, String calories, String userUsername){
        this.distance = distance;
        this.steps = steps;
        this.calories = calories;
        this.userUsername = userUsername;
    }

    // get methods for stats attributes
    public String getSteps(){
        return this.steps;
    }

    public String getDistance(){
        return this.distance;
    }

    public String getCalories(){
        return this.calories;
    }

    public String getUserUsername(){
        return this.userUsername;
    }

    // set methods for stats attributes
    public void setSteps(String steps){
        this.steps = steps;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public void setCalories(String calories){
        this.calories = calories;
    }

}
