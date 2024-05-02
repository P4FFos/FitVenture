package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.goal.RunningGoal;
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
    private TextField userWeightGoalValue;// text field where user inputs Weight Goal in KG
    @FXML
    private TextField userRunGoalValue;// text field where user inputs Run Goal in KM
    @FXML
    private VBox runBoxContainer; // VBox with all existing running goals
    @FXML
    private VBox weightBoxContainer;//VBox with all existing weight goals

    private ArrayList<HBox> weightGoalArrayList;
    private ArrayList<String> listOfWeightKeys;
    private ArrayList<HBox> runGoalArrayList;
    private ArrayList<String> listOfRunKeys;

    // to get the weight goals of the user
    private HashMap<String, WeightGoal> weightGoalHashMap = FitVentureStart.currentUser.getWeightGoal();
    private HashMap<String, RunningGoal> runGoalHashMap = FitVentureStart.currentUser.getRunningGoal();

    // FitVenture object used for JSON serializer
    FitVenture fitVenture = FitVentureStart.fitVenture;

    // this method adds the created goal to the hashMap of the user
    public void addWeightGoal(ActionEvent event) throws Exception {
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

    public void addRunGoal(ActionEvent event) throws Exception {
        // gets date today to assign it to the new goal

        String goalCreationDate = Current_Date.getDateToday(new Date());
        // checks if the entered value is not null
        // saves new goal into the HashMap
        if (userRunGoalValue.getText() != null) {
            try {
                double runGoalInKM = Double.parseDouble(userRunGoalValue.getText());
                RunningGoal runningGoal = new RunningGoal(runGoalInKM);
                FitVentureStart.currentUser.addRunGoal(goalCreationDate, runningGoal);
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewRunGoalsInProgress();
    }

    // the method is responsible for showing progress to the user in the progressBar
    public void viewWeightGoalsInProgress() {
        weightBoxContainer.getChildren().clear(); // Clear all objects from the container
        weightGoalArrayList = new ArrayList<>();
        listOfWeightKeys = new ArrayList<>();

        weightGoalHashMap.forEach((goalCreationDate, goalValue) -> { // for each Weight object do the fallowing
            double goal = goalValue.getGoalInCalories();
            double progressToGoal = FitVentureStart.currentUser.getTotalBurnedCalories(goalCreationDate);

            if (goal > progressToGoal) { // check if the goal was completed
                // settings for progressBar for the user
                ProgressBar weightProgressBar = new ProgressBar();
                weightProgressBar.setPrefWidth(330);
                weightProgressBar.setPrefHeight(50);
                weightProgressBar.setStyle("-fx-accent: green;");

                //settings for the goalLabel
                Label weightGoalInCaloriesLabel = new Label();
                weightGoalInCaloriesLabel.setPrefHeight(50);
                weightGoalInCaloriesLabel.setStyle("-fx-font-size: 15px;");
                weightGoalInCaloriesLabel.setPrefWidth(220);
                weightGoalInCaloriesLabel.setText("You burned: " + progressToGoal + " KCal");

                //settings for the progressLabel
                Label weightProgressInCaloriesLabel = new Label();
                weightProgressInCaloriesLabel.setPrefHeight(50);
                weightProgressInCaloriesLabel.setPrefWidth(220);
                weightProgressInCaloriesLabel.setStyle("-fx-font-size: 15px;");
                weightProgressInCaloriesLabel.setText(" The goal is: " + goal + " KCal");

                // Setting foe the HBox that holds the progressbar and both Labels
                HBox hBox = new HBox();
                hBox.setPrefWidth(700);
                hBox.setSpacing(10);

                weightProgressBar.setProgress(progressToGoal / goal); // set the value of the progressbar
                hBox.getChildren().addAll(weightProgressBar, weightGoalInCaloriesLabel, weightProgressInCaloriesLabel); // add three objects to the hBox
                sortWeightArray(hBox, goalCreationDate); // Making sure that the current goal in progress (bar) is always on the top of the list of other bars
            }
        });
        weightBoxContainer.getChildren().addAll(weightGoalArrayList);// add everything to the container that is reserved a space in the fxml file
    }

    // the method is responsible for showing progress to the user in the progressBar
    public void viewRunGoalsInProgress() {
        runBoxContainer.getChildren().clear(); // Clear all objects from the container
        runGoalArrayList = new ArrayList<>();
        listOfRunKeys = new ArrayList<>();

        runGoalHashMap.forEach((goalCreationDate, goalValue) -> { // for each Run object do the following
            double goal = goalValue.getGoalDistInM();
            double progressToGoal = FitVentureStart.currentUser.getTotalRanDistance(goalCreationDate);

            if (goal > progressToGoal) { // check if the goal was completed
                // settings for progressBar for the user
                ProgressBar runProgressBar = new ProgressBar();
                runProgressBar.setPrefWidth(330);
                runProgressBar.setPrefHeight(50);
                runProgressBar.setStyle("-fx-accent: green;");

                //settings for the runGoalLabel
                Label runGoalInMetersLabel = new Label();
                runGoalInMetersLabel.setPrefHeight(50);
                runGoalInMetersLabel.setStyle("-fx-font-size: 15px;");
                runGoalInMetersLabel.setPrefWidth(220);
                runGoalInMetersLabel.setText("You ran: " + progressToGoal + " m");

                //settings for the progressLabel
                Label runProgressInMetersLabel = new Label();
                runProgressInMetersLabel.setPrefHeight(50);
                runProgressInMetersLabel.setPrefWidth(220);
                runProgressInMetersLabel.setStyle("-fx-font-size: 15px;");
                runProgressInMetersLabel.setText(" The goal is: " + goal + " Km");
                // Setting foe the HBox that holds the progressbar and both Labels
                HBox hBox = new HBox();
                hBox.setPrefWidth(700);
                hBox.setSpacing(10);

                runProgressBar.setProgress(progressToGoal / goal); // set the value of the progressbar
                hBox.getChildren().addAll(runProgressBar, runGoalInMetersLabel, runProgressInMetersLabel); // add three objects to the hBox
                sortRunArray(hBox, goalCreationDate); // Making sure that the current goal in progress (bar) is always on the top of the list of other bars
            }
        });
        runBoxContainer.getChildren().addAll(runGoalArrayList);// add everything to the container that is reserved a space in the fxml file
    }

    // This method is responsible for sorting HBox objects by date
    private void sortWeightArray(HBox hBox, String goalCreationDate) {
        // If the list is empty, simply add the HBox and its key
        if (weightGoalArrayList.isEmpty()) {
            weightGoalArrayList.add(hBox);
            listOfWeightKeys.add(goalCreationDate);
        } else {
            int lastIndex = listOfWeightKeys.size() - 1;
            int currentGoalKey = Current_Date.getIntegerOfSpecificDate(listOfWeightKeys.get(lastIndex));
            int newGoalKey = Current_Date.getIntegerOfSpecificDate(goalCreationDate);

            // If the new key is greater than the current last key,
            // add the HBox and its key at the end
            if (newGoalKey > currentGoalKey) {

                weightGoalArrayList.add(hBox);
                listOfWeightKeys.add(goalCreationDate);
            } else {
                // Otherwise, find the correct position to insert the new key
                int indexToInsert = 0;
                while (indexToInsert <= lastIndex &&
                        Current_Date.getIntegerOfSpecificDate(listOfWeightKeys.get(indexToInsert)) < newGoalKey) {
                    indexToInsert++;
                }

                // Insert the HBox and its key at the correct position
                weightGoalArrayList.add(indexToInsert, hBox);
                listOfWeightKeys.add(indexToInsert, goalCreationDate);
            }
        }
    }

    // This method is responsible for sorting HBox objects by date
    private void sortRunArray(HBox hBox, String goalCreationDate) {
        // If the list is empty, simply add the HBox and its key
        if (runGoalArrayList.isEmpty()) {
            runGoalArrayList.add(hBox);
            listOfRunKeys.add(goalCreationDate);
        } else {
            int lastIndex = listOfRunKeys.size() - 1;
            int currentGoalKey = Current_Date.getIntegerOfSpecificDate(listOfRunKeys.get(lastIndex));
            int newGoalKey = Current_Date.getIntegerOfSpecificDate(goalCreationDate);

            // If the new key is greater than the current last key,
            // add the HBox and its key at the end
            if (newGoalKey > currentGoalKey) {

                runGoalArrayList.add(hBox);
                listOfRunKeys.add(goalCreationDate);
            } else {
                // Otherwise, find the correct position to insert the new key
                int indexToInsert = 0;
                while (indexToInsert <= lastIndex &&
                        Current_Date.getIntegerOfSpecificDate(listOfRunKeys.get(indexToInsert)) < newGoalKey) {
                    indexToInsert++;
                }

                // Insert the HBox and its key at the correct position
                runGoalArrayList.add(indexToInsert, hBox);
                listOfWeightKeys.add(indexToInsert, goalCreationDate);
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
