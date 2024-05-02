package fitVenture.backend.goal;

public class RunningGoal {
    // attribute to store the goal weight in kg from the filed
    private double runGoalInKM;
    // attribute to store the goal in calories
    private double runGoalinM;

    // constant to calculate the goal from KG to calories
    private static final double KM_TO_METERS_MULTIPLIER = 0.001;

    // Empty constructor used for Json deserializing
    public RunningGoal() {
    }

    public RunningGoal(double runGoalInKM) {
        this.runGoalInKM = runGoalInKM;
        // calculate the goal in calories
        this.runGoalinM = runGoalInKM * KM_TO_METERS_MULTIPLIER;
    }

    // get methods for runGoal
    public double getGoalDistInKM() {
        return this.runGoalInKM;
    }

    public double getGoalDistInM() {
        return this.runGoalinM;
    }

    // set methods for runGoal
    public void setRunGoalInKm(double runGoalInKM) {
        this.runGoalInKM = runGoalInKM;
    }

    public void setRunGoalInM(double runGoalinM) {
        this.runGoalinM = runGoalinM;
    }


}
