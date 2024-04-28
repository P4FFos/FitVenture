package fitVenture.backend.goal;


public class WeightGoal {
    private double weightGoal;
    private double progressToGoalInCalories;
    private double goalInCaloris;

    // Empty constructor used for Json deserializing
    public WeightGoal(){
    }

    public WeightGoal( int weightGoal, int progressToGoalInCalories){
        this.weightGoal = weightGoal;
        this.progressToGoalInCalories = progressToGoalInCalories;
        this.goalInCaloris=weightGoal*7700;
    }

    public double getGoalWeight(){
      return   this.weightGoal;
    }
    public double getProgressToGoalInCalories(){
        return this.progressToGoalInCalories;
    }
    public void setGoalWeight(int weightGoal){
        this.weightGoal = weightGoal;
    }
    public void setProgressToGoalInCalories(int progressToGoalInCalories){
        this.progressToGoalInCalories = progressToGoalInCalories;
    }
    public double getRatio(){
        return progressToGoalInCalories /weightGoal;
    }
    public double caloriesInGoal(){
        return weightGoal*7700;
    }
    public double getGoalInCaloris(){
        return this.goalInCaloris;
    }

}
