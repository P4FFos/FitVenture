package fitVenture.backend.goal;

public class WeightGoal {
    // attribute to store the goal weight in kg from the filed
    private double weightGoalInKg;
    // attribute to store the goal in calories
    private double goalInCalories;

    // constant to calculate the goal from KG to calories
    private static final double CALORIES_PER_KG = 7700;

    // Empty constructor used for Json deserializing
    public WeightGoal() {
    }

    public WeightGoal(double weightGoalInKg) {
        this.weightGoalInKg = weightGoalInKg;
        // calculate the goal in calories
        this.goalInCalories = weightGoalInKg * CALORIES_PER_KG;
    }

    // get methods for weightGoal
    public double getGoalWeightInKg() {
        return this.weightGoalInKg;
    }

    public double getGoalInCalories() {
        return this.goalInCalories;
    }

    // set methods for weightGoal
    public void setGoalWeightInKg(double weightGoalInKg) {
        this.weightGoalInKg = weightGoalInKg;
    }

    public void setGoalInCalories(double goalInCalories) {
        this.goalInCalories = goalInCalories;
    }
}
