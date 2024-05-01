package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.utils.Current_Date;
import fitVenture.backend.utils.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GoalsController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField userWeightGoalValue; // text field where user inputs Weight Goal in KG

    @FXML
    private VBox vBoxContainer; // VBox with all existing running goals

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

    // the method is responsible for showing progress to the user in the progressBar
    public void viewWeightGoalsInProgress() {
        vBoxContainer.getChildren().clear(); // Clear all objects from the container
        weightGoalArrayList = new ArrayList<>();
        listOfKeys = new ArrayList<>();

        weightGoalHashMap.forEach((goalCreationDate, goalValue) -> { // for each Weight object do the fallowing
            double goal = goalValue.getGoalInCalories();
            double progressToGoal = FitVentureStart.currentUser.getTotalBurnedCalories(goalCreationDate);

            if (goal > progressToGoal) { // check if the goal was completed
                // settings for progressBar for the user
                ProgressBar progressBar = new ProgressBar();
                progressBar.setPrefWidth(330);
                progressBar.setPrefHeight(50);
                progressBar.setStyle("-fx-accent: green;");

                //settings for the goalLabel
                Label goalInCaloriesLabel = new Label();
                goalInCaloriesLabel.setPrefHeight(50);
                goalInCaloriesLabel.setStyle("-fx-font-size: 15px;");
                goalInCaloriesLabel.setPrefWidth(220);
                goalInCaloriesLabel.setText("You burned: " + progressToGoal + " Cal");

                //settings for the progressLabel
                Label progressInCaloriesLabel = new Label();
                progressInCaloriesLabel.setPrefHeight(50);
                progressInCaloriesLabel.setPrefWidth(220);
                progressInCaloriesLabel.setStyle("-fx-font-size: 15px;");
                progressInCaloriesLabel.setText(" The goal is: " + goal + " Cal");

                // Setting foe the HBox that holds the progressbar and both Labels
                HBox hBox = new HBox();
                hBox.setPrefWidth(700);
                hBox.setSpacing(10);

                progressBar.setProgress(progressToGoal / goal); // set the value of the progressbar
                hBox.getChildren().addAll(progressBar, goalInCaloriesLabel, progressInCaloriesLabel); // add three objects to the hBox
                sortArray(hBox, goalCreationDate); // Making sure that the current goal in progress (bar) is always on the top of the list of other bars
            }
        });
        vBoxContainer.getChildren().addAll(weightGoalArrayList);// add everything to the container that is reserved a space in the fxml file
    }

    // This method is responsible for sorting HBox objects by date
    private void sortArray(HBox hBox, String goalCreationDate) {
        // If the list is empty, simply add the HBox and its key
        if (weightGoalArrayList.isEmpty()) {
            weightGoalArrayList.add(hBox);
            listOfKeys.add(goalCreationDate);
        } else {
            int lastIndex = listOfKeys.size() - 1;
            int currentGoalKey = Current_Date.getIntegerOfSpecificDate(listOfKeys.get(lastIndex));
            int newGoalKey = Current_Date.getIntegerOfSpecificDate(goalCreationDate);

            // If the new key is greater than the current last key,
            // add the HBox and its key at the end
            if (newGoalKey > currentGoalKey) {

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

    // button to return back to the MainDashboard
    public void returnBackToMain(ActionEvent event) throws IOException {
        // loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
        root = loader.load();

        MainDashboardController mainDashboardController = loader.getController();
        mainDashboardController.showChart();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
