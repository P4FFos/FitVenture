package fitVenture.backend.goal;

public class RunningGoal {
    // attribute to store the goal weight in kg from the filed
    private double runGoalInKM;
    // attribute to store the goal in calories
    private double runGoalInM;

    // constant to calculate the goal from KG to calories
    private static final double KM_TO_METERS_MULTIPLIER = 1000;

    // Empty constructor used by Jackson for Json deserializing,
    // every time we run the application, the user object is created with the empty constructor
    public RunningGoal() {
    }

    public RunningGoal(double runGoalInKM) {
        this.runGoalInKM = runGoalInKM;
        // calculate the goal in calories
        this.runGoalInM = runGoalInKM * KM_TO_METERS_MULTIPLIER;
    }

    //#region RunningGoal Class Get Methods
    public double getRunGoalInKM() {
        return this.runGoalInKM;
    }

    public double getRunGoalInM() {
        return this.runGoalInM;
    }
    //#endregion

    //#region RunningGoal Class Set Methods
    public void setRunGoalInKm(double runGoalInKM) {
        this.runGoalInKM = runGoalInKM;
    }

    public void setRunGoalInM(double runGoalInM) {
        this.runGoalInM = runGoalInM;
    }
    //#endregion
}
