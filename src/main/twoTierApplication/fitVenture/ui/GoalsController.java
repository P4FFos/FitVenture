package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.utils.Current_Date;
import fitVenture.backend.utils.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GoalsController {

    @FXML
    private TextField userWeightGoalValue;

    @FXML
    private VBox listOfGoals;

    private ArrayList<HBox> weightGoalArrayList;
    private ArrayList<String> listOfKeys;

    // to get the weight goals of the user
    private HashMap<String, WeightGoal> weightGoalHashMap = FitVentureStart.currentUser.getWeightGoals();
    // to get the total amount of burned calories of the user
    private final double burnedCalories = FitVentureStart.currentUser.getTotalBurnedCalories();

    FitVenture fitVenture = FitVentureStart.fitVenture;

    // this method adds the created goal to the hashMap of the user
    public void addGoal(ActionEvent event) throws Exception {
        // this is where the goal object is saved to the map. key is today date including seconds and minutes .
        String goalCreationDate = Current_Date.getDateToday(new Date());
        if (userWeightGoalValue.getText() != null) { //checking if the weight goal exists if not, the goal is added
            try {
                double weightGoalInKg = Double.parseDouble(userWeightGoalValue.getText()); // get the weight goal in KG from the user
                WeightGoal weightGoal = new WeightGoal(weightGoalInKg, burnedCalories);
                weightGoalHashMap.put(goalCreationDate, weightGoal);
                // saves the goal object to the json file
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
            } catch (Exception e) {
                System.out.println("Failed, you entered a wrong value");
            }
        }
        viewWeightGoalsInProgress();
    }

    public void viewWeightGoalsInProgress() {
        listOfGoals.getChildren().clear();

        weightGoalArrayList = new ArrayList<>();
        listOfKeys = new ArrayList<>();

        weightGoalHashMap.forEach((key, value) -> {
            double goalInCalories = value.getGoalInCalories();
            if (goalInCalories > burnedCalories) {
                Label goalInCaloriestLabel = new Label();
                Label progressInCalorieslabel = new Label();
                HBox hBox = new HBox();
                ProgressBar progressBar = new ProgressBar();
                goalInCaloriestLabel.setPrefHeight(50);
                goalInCaloriestLabel.setStyle("-fx-font-size: 15px;");
                goalInCaloriestLabel.setPrefWidth(200);
                progressInCalorieslabel.setPrefHeight(50);
                progressInCalorieslabel.setStyle("-fx-font-size: 15px;");
                hBox.setPrefSize(200, 50);
                hBox.setSpacing(10);
                progressBar.setPrefWidth(300);
                progressBar.setPrefHeight(50);
                goalInCaloriestLabel.setText("Goal is: " + goalInCalories + " calories");
                progressInCalorieslabel.setText("You burned: " + burnedCalories + " calories");
                progressBar.setProgress(burnedCalories / goalInCalories);
                hBox.getChildren().addAll(progressBar, progressInCalorieslabel, goalInCaloriestLabel);
                sortArray(hBox, key);
            }
        });
        listOfGoals.getChildren().addAll(weightGoalArrayList);
    }

    private void sortArray(HBox hBox, String key) {
        if (weightGoalArrayList.isEmpty()) {
            weightGoalArrayList.add(hBox);
            listOfKeys.add(key);
        } else {
            int lastIndex = listOfKeys.size() - 1;

            if (Current_Date.getIntegerOfSpecificDate(listOfKeys.get(lastIndex)) < Current_Date.getIntegerOfSpecificDateSecIncluded(key)) {
                weightGoalArrayList.add(hBox);
                listOfKeys.add(key);
            } else {
                String myKey = listOfKeys.get(lastIndex);
                HBox hBox1 = weightGoalArrayList.get(lastIndex);
                weightGoalArrayList.add(lastIndex, hBox);
                listOfKeys.add(lastIndex, key);
                weightGoalArrayList.add(lastIndex + 1, hBox1);
                listOfKeys.add(lastIndex + 1, myKey);
            }
        }
    }
}
