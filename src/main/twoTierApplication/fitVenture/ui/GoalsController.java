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
    private TextField userWeightGoalValue; // text field where user inputs Weight Goal in KG

    @FXML
    private VBox listOfGoals; // VBox with all existing running goals

    private ArrayList<HBox> weightGoalArrayList;
    private ArrayList<String> listOfKeys;

    // to get the weight goals of the user
    private HashMap<String, WeightGoal> weightGoalHashMap = FitVentureStart.currentUser.getWeightGoal();

    // FitVenture object used for JSON serializer
    FitVenture fitVenture = FitVentureStart.fitVenture;

    // this method adds the created goal to the hashMap of the user
    public void addGoal(ActionEvent event) throws Exception {
        // gets date today to assign it to the new goal
        String goalCreationDate = Current_Date.getDateToday(new Date());

        // checks if the entered value is not null
        // saves new goal into the HashMap
        if (userWeightGoalValue.getText() != null) {
            try {
                double weightGoalInKg = Double.parseDouble(userWeightGoalValue.getText());
                WeightGoal weightGoal = new WeightGoal(weightGoalInKg);
                FitVentureStart.currentUser.addWeightGoal(goalCreationDate, weightGoal);
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // call the method to see all goals
        viewWeightGoalsInProgress();
    }

    public void viewWeightGoalsInProgress() {
        listOfGoals.getChildren().clear();
        weightGoalArrayList = new ArrayList<>();
        listOfKeys = new ArrayList<>();

        weightGoalHashMap.forEach((goalCreationDate, goalValue) -> {
            double goal = goalValue.getGoalInCalories();
            double progressToGoal = FitVentureStart.currentUser.getTotalBurnedCalories(goalCreationDate);

            if (goal > progressToGoal) {
                ProgressBar progressBar = new ProgressBar();
                progressBar.setPrefWidth(330);
                progressBar.setPrefHeight(50);
                progressBar.setStyle("-fx-accent: green;");

                Label goalInCaloriestLaber = new Label();
                goalInCaloriestLaber.setPrefHeight(50);
                goalInCaloriestLaber.setStyle("-fx-font-size: 15px;");
                goalInCaloriestLaber.setPrefWidth(220);
                goalInCaloriestLaber.setText("You burned: " + progressToGoal + " Cal");

                Label progressInCalorieslabel = new Label();
                progressInCalorieslabel.setPrefHeight(50);
                progressInCalorieslabel.setPrefWidth(220);
                progressInCalorieslabel.setStyle("-fx-font-size: 15px;");
                progressInCalorieslabel.setText(" The goal is: " + goal + " Cal");

                HBox hBox = new HBox();
                hBox.setPrefWidth(700);
                hBox.setSpacing(10);

                progressBar.setProgress(progressToGoal / goal);
                hBox.getChildren().addAll(progressBar, goalInCaloriestLaber, progressInCalorieslabel);
                sortArray(hBox, goalCreationDate);
            }
        });
        listOfGoals.getChildren().addAll(weightGoalArrayList);
    }

    private void sortArray(HBox hBox, String goalCreationDate) {
        if (weightGoalArrayList.isEmpty()) {
            // If the list is empty, simply add the HBox and its key
            weightGoalArrayList.add(hBox);
            listOfKeys.add(goalCreationDate);
        } else {
            int lastIndex = listOfKeys.size() - 1;
            int currentGoalKey = Current_Date.getIntegerOfSpecificDate(listOfKeys.get(lastIndex));
            int newGoalKey = Current_Date.getIntegerOfSpecificDate(goalCreationDate);

            if (newGoalKey > currentGoalKey) {
                // If the new key is greater than the current last key,
                // add the HBox and its key at the end
                weightGoalArrayList.add(hBox);
                listOfKeys.add(goalCreationDate);
            } else {
                // Otherwise, find the correct position to insert the new key
                int indexToInsert = 0;
                while (indexToInsert <= lastIndex &&
                        Current_Date.getIntegerOfSpecificDate(listOfKeys.get(indexToInsert)) < newGoalKey) {
                    indexToInsert++;
                }

                // Insert the HBox and its key at the correct position
                weightGoalArrayList.add(indexToInsert, hBox);
                listOfKeys.add(indexToInsert, goalCreationDate);
            }
        }
    }
}
