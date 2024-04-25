package fitVenture.backend.goal;

public class WeightGoal {
    int weight;


    // Empty constructor used for Json deserializing
    public WeightGoal(){}

    public WeightGoal(int weight ){
        this.weight= weight;
    }

    public int getWeight(){
      return   this.weight;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }

}
