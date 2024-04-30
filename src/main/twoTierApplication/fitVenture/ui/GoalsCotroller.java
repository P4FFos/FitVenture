package fitVenture.ui;

import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.utils.Current_Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GoalsCotroller {

    @FXML
    private TextField addWeightgoal;

    private String key;
    @FXML
    private VBox vBox;
    private ArrayList<HBox> weightGoalArrayList;
    private ArrayList<String> listOfkeys;

    private HashMap<String,WeightGoal> weightGoalHashMap = FitVentureStart.currentUser.getWeightGoals();


    // this method adds the created goal to the hashMap of the user
    public void addGoal(ActionEvent event) throws Exception {
         // this is where the goal object is saved to the map. key is todays date including seconds ans minutes .

        key =Current_Date.getDateToday(new Date());
       if(addWeightgoal.getText()!=null){ //checking if the weight goal exists if not, the goal is added
         try {
           int value = Integer.parseInt(addWeightgoal.getText());
           WeightGoal weightGoal = new WeightGoal(value,0); // the progress always starts from zero
           //FitVentureStart.currentUser.addWeightGoal(key,weightGoal);
             weightGoalHashMap.put(key,weightGoal);
         } catch (Exception e){
           System.out.println("the entered value is not a number ");
         }

       }
       viewAWeightgoalsinProgress();
    }

    public void viewAWeightgoalsinProgress(){
        vBox.getChildren().clear();

        weightGoalArrayList = new ArrayList<>();
        listOfkeys= new ArrayList<>();

        weightGoalHashMap.forEach((k,v)->{
            double goal = v.getGoalInCaloris();
            double progressToGoal = v.getProgressToGoalInCalories();
            if(goal > progressToGoal){

            Label goalInCaloriestLaber = new Label();
             Label progressInCalorieslabel = new Label();
             HBox hBox = new HBox();
             ProgressBar progressBar = new ProgressBar();
             goalInCaloriestLaber.setPrefHeight(50);
             goalInCaloriestLaber.setStyle("-fx-font-size: 20px;");
             goalInCaloriestLaber.setPrefWidth(200);
             progressInCalorieslabel.setPrefHeight(50);
             progressInCalorieslabel.setStyle("-fx-font-size: 20px;");
             hBox.setPrefSize(200,50);
             hBox.setSpacing(10);
             progressBar.setPrefWidth(300);
             progressBar.setPrefHeight(50);
             goalInCaloriestLaber.setText("Goal is: "+ goal);
             progressInCalorieslabel.setText("Progress to goal: " + progressToGoal);
             progressBar.setProgress(progressToGoal/goal);
             hBox.getChildren().addAll(progressBar,progressInCalorieslabel,goalInCaloriestLaber);
             sortArray(hBox,k);
             uppdateProgressToGoal(v.getGoalInCaloris()/4);
            }
        });
        vBox.getChildren().addAll(weightGoalArrayList);

    }

    private void sortArray(HBox hBox, String key) {
        if (weightGoalArrayList.isEmpty()) {
            weightGoalArrayList.add(hBox);
            listOfkeys.add(key);
        } else {
            int lastindex = listOfkeys.size()-1;

            if (Current_Date.getIntegerOfSpecificDate( listOfkeys.get(lastindex)) < Current_Date.getIntegerOfSpecificDateSecIncluded(key)) {
                    weightGoalArrayList.add(hBox);
                    listOfkeys.add(key );
            } else {
                    String myKey = listOfkeys.get(lastindex);
                    HBox hBox1 = weightGoalArrayList.get(lastindex);
                    weightGoalArrayList.add(lastindex,hBox);
                    listOfkeys.add(lastindex,key);
                    weightGoalArrayList.add(lastindex+1,hBox1);
                    listOfkeys.add(lastindex+1, myKey);
            }


        }
        System.out.println(listOfkeys.size());
        System.out.println(key);

    }

    // use the list of keys to get the key string
    // use the key to uppdate the hashmap
    // callWiewWeightGoalsProgress

    public void uppdateProgressToGoal(double value){
        if(!listOfkeys.isEmpty()){
            String key = listOfkeys.get(0);
            weightGoalHashMap.get(key).incrementProgress(value);

        }
    }
}
