
package fitVenture.backend.goal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeightGoal {
    @JsonProperty("weightGoal")
    private Double weightGoal;
    @JsonProperty ("progressToGoalInCalories")
    private Double progressToGoalInCalories;
    @JsonProperty("goalInCaloris")
    private Double goalInCaloris;

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
      this.weightGoal = (double)weightGoal;
    }
    public void setProgressToGoalInCalories(int progressToGoalInCalories){
        this.progressToGoalInCalories = (double) progressToGoalInCalories;
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
