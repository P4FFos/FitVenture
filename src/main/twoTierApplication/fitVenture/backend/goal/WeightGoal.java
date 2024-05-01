
package fitVenture.backend.goal;


public class WeightGoal {
    private double weightGoal;
    private double progressToGoalInCalories;
    private double goalInCaloris;

    // Empty constructor used for Json deserializing
    public WeightGoal(){
    }

    public WeightGoal( double weightGoal, double progressToGoalInCalories){
        this.weightGoal = weightGoal;
        this.progressToGoalInCalories = progressToGoalInCalories;
        this.goalInCaloris=weightGoal*7700;
    }

    public double getGoalWeight(){
        return this.weightGoal;
    }
    public double getProgressToGoalInCalories(){
        return this.progressToGoalInCalories;
    }
    public void setGoalWeight(int weightGoal){
      this.weightGoal =  weightGoal;
    }
    public void setProgressToGoalInCalories(int progressToGoalInCalories){
        this.progressToGoalInCalories = progressToGoalInCalories;
    }
    public double getRatio(){
        double progress = progressToGoalInCalories;

        return weightGoal;
    }

    public double getGoalInCaloris(){
        return this.goalInCaloris;
    }
    public void incrementProgress(double progress ){
        this.progressToGoalInCalories= progressToGoalInCalories+progress;
    }

}
