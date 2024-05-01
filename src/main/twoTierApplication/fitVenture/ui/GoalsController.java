
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
import java.util.Random;

public class GoalsController {

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
                goalInCaloriestLaber.setText("Goal is : "+ goal);
                progressInCalorieslabel.setText("Progress to goal: " + progressToGoal);
                progressBar.setProgress(progressToGoal/goal);
                hBox.getChildren().addAll(progressBar,progressInCalorieslabel,goalInCaloriestLaber);
                sortArray(hBox,k);
                Random random = new Random();
                uppdateProgressToGoal(random.nextInt(0,1000));


            }
        });

        vBox.getChildren().addAll(weightGoalArrayList);

    }

    private void sortArray(HBox hBox, String key) {
        if (weightGoalArrayList.isEmpty()) {
            // If the list is empty, simply add the HBox and its key
            weightGoalArrayList.add(hBox);
            listOfkeys.add(key);
        } else {
            int lastIndex = listOfkeys.size() - 1;
            int currentKey = Current_Date.getIntegerOfSpecificDateSecIncluded(listOfkeys.get(lastIndex));
            int newKey = Current_Date.getIntegerOfSpecificDateSecIncluded(key);

            if (newKey > currentKey) {
                // If the new key is greater than the current last key,
                // add the HBox and its key at the end
                weightGoalArrayList.add(hBox);
                listOfkeys.add(key);
            } else {
                // Otherwise, find the correct position to insert the new key
                int indexToInsert = 0;
                while (indexToInsert <= lastIndex &&
                        Current_Date.getIntegerOfSpecificDateSecIncluded(listOfkeys.get(indexToInsert)) < newKey) {
                    indexToInsert++;
                }

                // Insert the HBox and its key at the correct position
                weightGoalArrayList.add(indexToInsert, hBox);
                listOfkeys.add(indexToInsert, key);
            }
        }



        System.out.println("sorted dates");
        for (int i = 0; i < listOfkeys.size(); i++){
            System.out.println(listOfkeys.get(i));
        }
    }

    public void uppdateProgressToGoal(double value){




    }

    // this method will be needed after a meeting with the group about how weight data should be saved to the hashmap of users
    public void refresh(){
        Random random = new Random();
        for(int i =0; i<10;i++ ){
            if(!listOfkeys.isEmpty()){
                String key = listOfkeys.get(0);
                weightGoalHashMap.get(key).incrementProgress(random.nextInt(0,100));
                viewAWeightgoalsinProgress();

            }
        }
    }
}