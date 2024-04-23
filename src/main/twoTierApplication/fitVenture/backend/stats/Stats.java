package fitVenture.backend.stats;

public class Stats {
    private String distance;
    private String steps;
    private String calories;

    // Empty constructor used by Jackson for Json deserializing
    public Stats() {
    }

    // Stats class constructor
    public Stats(String steps, String distance, String calories){
        this.distance = distance;
        this.steps = steps;
        this.calories = calories;
    }

    // Get methods for stats attributes
    public String getSteps(){
        return this.steps;
    }

    public String getDistance(){
        return this.distance;
    }

    public String getCalories(){
        return this.calories;
    }

    // Set methods for stats attributes
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