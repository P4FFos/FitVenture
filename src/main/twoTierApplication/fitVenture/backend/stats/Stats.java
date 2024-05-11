package fitVenture.backend.stats;

public class Stats {
    // stats class attributes
    private String distance;
    private String steps;
    private String calories;

    // Empty constructor used by Jackson for Json deserializing,
    // every time we run the application, the user object is created with the empty constructor
    public Stats() {
    }

    // Stats class constructor
    public Stats(String steps, String distance, String calories){
        this.distance = distance;
        this.steps = steps;
        this.calories = calories;
    }

    //#region Get Methods
    public String getSteps(){
        return this.steps;
    }

    public String getDistance(){
        return this.distance;
    }

    public String getCalories(){
        return this.calories;
    }
    //#endregion

    //#region Set Methods
    public void setSteps(String steps){
        this.steps = steps;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public void setCalories(String calories){
        this.calories = calories;
    }
    //#endregion
}
