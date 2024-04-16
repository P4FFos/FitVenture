package fitVenture.backend.stats;

import fitVenture.ui.FitVentureStart;

import java.util.Date;

public class Stats {
    private String distance;
    private String steps;
    private String calories;

    // Empty constructor used by Jackson for Json deserializing
    public Stats() {
    }

    public Stats(String steps, String distance, String calories){
        this.distance = distance;
        this.steps = steps;
        this.calories = calories;
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
