package fitVenture.backend.stats;

public class Stats {
    double distance;
    double steps;
    double calories;
    public Stats(double steps, double distance, double calories){
        this.distance = distance;
        this.steps = steps;
        this.calories = calories;
    }

    public double getSteps(){
        return this.steps;
    }

    public double getDistance(){
        return this.distance;
    }

    public double getCalories(){
        return this.calories;
    }
}
