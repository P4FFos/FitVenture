package fitVenture.ui;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.utils.Current_Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChallengesController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    public Label welcomeUsername;
    @FXML
    public Label dailyStepChallenge;
    @FXML
    public Label dailyKcalChallenge;
    @FXML
    public Label dailyDistanceChallenge;
    @FXML
    private ProgressBar stepProgressBar;
    @FXML
    private ProgressBar kcalProgressBar;
    @FXML
    private ProgressBar distProgressBar;
    @FXML
    private Button BackButton;
    @FXML
    private Label stepProgLabel;
    @FXML
    private Label kcalProgLabel;
    @FXML
    private Label distProgLabel;

    private int lowBMISteps = 10000;
    private int normalBMISteps = 8000;
    private int highBMISteps = 5000;
    private int veryHighBMISteps = 3000;

    private double lowBmiKcal = 4000.0;
    private double normalBmiKcal = 3000.0;
    private double highBmiKcal = 2500.0;
    private double veryHighBmiKcal = 2000.0;

    private double lowBmiDist = 10.0;
    private double normalBmiDist = 8.0;
    private double highBmiDist = 5.0;
    private double veryHighBmiDist = 3.0;

    private final String lowBmiStepChallenge = "Your daily challenge is: " + "Walk " + lowBMISteps + " steps today!";
    private final String normalBmiStepChallenge = "Your daily challenge is: " + "Walk " + normalBMISteps + " steps today!";
    private final String highBmiStepChallenge = "Your daily challenge is: " + "Walk " + highBMISteps + " steps today!";
    private final String veryHighBmiStepChallenge = "Your daily challenge is: " + "Walk " + veryHighBMISteps + " steps today!";

    private final String lowBmiKcalChallenge = "Your daily challenge is: " + "Eat " + lowBmiKcal + " kcal today!";
    private final String normalBmiKcalChallenge = "Your daily challenge is: " + "Eat " + normalBmiKcal + " kcal today!";
    private final String highBmiKcalChallenge = "Your daily challenge is: " + "Eat " + highBmiKcal + " kcal today!";
    private final String veryHighBmiKcalChallenge = "Your daily challenge is: " + "Eat " + veryHighBmiKcal + " kcal today!";

    private final String lowBmiDistChallenge = "Your daily challenge is: Walk " + lowBmiDist + " km today!";
    private final String normalBmiDistChallenge = "Your daily challenge is: Walk " + normalBmiDist + " km today!";
    private final String highBmiDistChallenge = "Your daily challenge is: Walk " + highBmiDist + " km today!";
    private final String veryHighBmiDistChallenge = "Your daily challenge is: Walk " + veryHighBmiDist + " km today!";

    public void showChallenges() {

        // get information of the logged user from the database
        String userUsername = FitVentureStart.currentUser.getUsername();
        String userWeight = FitVentureStart.currentUser.getWeight();
        String userHeight = FitVentureStart.currentUser.getHeight();
        welcomeUsername.setText(userUsername);

        // parse height and weight values from string to double
        double height = Double.parseDouble(userHeight);
        double weight = Double.parseDouble(userWeight);

        // calculates BMI based on the new values
        double bmiValue = weight / Math.pow(height / 100, 2);

        // Create a HashMap to store the user's stats
        HashMap<String, Stats> statsHashMap = FitVentureStart.currentUser.getStats();

        // Initialize variables to store the total steps, calories, and distance for today
        double totalStepsToday = 0.0;
        double totalKcalToday = 0.0;
        double totalDistToday = 0.0;

        // Get today's date
        String todayDate = Current_Date.getDateToday(new Date()).substring(0, 10);

        // Iterate over the entries in the stats HashMap
        for (Map.Entry<String, Stats> valueFromHashmap : statsHashMap.entrySet()) {

            // Get the date from the current entry's key
            String statsDate = valueFromHashmap.getKey().substring(0, 10);

            // Check if the date from the current entry is today
            if (statsDate.equals(todayDate)) {

                // Get the stats from the current entry
                Stats stats = valueFromHashmap.getValue();

                // Parse the steps, distance, and calories from the stats and add them to the totals
                double stepsValue = Double.parseDouble(stats.getSteps());
                double distValue = Double.parseDouble(stats.getDistance());
                double kcalValue = Double.parseDouble(stats.getCalories());
                totalStepsToday += stepsValue;
                totalKcalToday += kcalValue;
                totalDistToday += distValue;
            }
        }

        // Determine the BMI category based on the BMI value
        int bmiCategory;
        if (bmiValue < 18.5) {
            bmiCategory = 1;
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            bmiCategory = 2;
        } else if (bmiValue >= 25 && bmiValue < 30) {
            bmiCategory = 3;
        } else {
            bmiCategory = 4;
        }

        // Perform different actions based on the BMI category
        switch (bmiCategory) {
            case 1:
                // If the BMI is less than 18.5, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(lowBmiStepChallenge);
                double progressStep = totalStepsToday / lowBMISteps;
                stepProgressBar.setProgress(progressStep);
                stepProgLabel.setText(totalStepsToday + "/" + lowBMISteps);

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(lowBmiKcalChallenge);
                double progressKcal = totalKcalToday / lowBmiKcal;
                kcalProgressBar.setProgress(progressKcal);
                kcalProgLabel.setText(totalKcalToday / 1000 + "/" + lowBmiKcal);

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(lowBmiDistChallenge);
                double progressDist = (totalDistToday / lowBmiDist) / 1000;
                distProgressBar.setProgress(progressDist);
                distProgLabel.setText((totalDistToday / 1000) + "/" + lowBmiDist);
                break;
            case 2:
                // If the BMI is between 18.5 and 25, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(normalBmiStepChallenge);
                double progress = totalStepsToday / normalBMISteps;
                stepProgressBar.setProgress(progress);
                stepProgLabel.setText(totalStepsToday + "/" + normalBMISteps);

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(normalBmiKcalChallenge);
                progressKcal = totalKcalToday / normalBmiKcal;
                kcalProgressBar.setProgress(progressKcal);
                kcalProgLabel.setText(totalKcalToday + "/" + normalBmiKcal);

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(normalBmiDistChallenge);
                progressDist = (totalDistToday / normalBmiDist) / 1000;
                distProgressBar.setProgress(progressDist);
                distProgLabel.setText((totalDistToday / 1000) + "/" + normalBmiDist);
                break;
            case 3:
                // If the BMI is between 25 and 30, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(highBmiStepChallenge);
                progress = totalStepsToday / highBMISteps;
                stepProgressBar.setProgress(progress);
                stepProgLabel.setText(totalStepsToday + "/" + highBMISteps);

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(highBmiKcalChallenge);
                progressKcal = totalKcalToday / highBmiKcal;
                kcalProgressBar.setProgress(progressKcal);
                kcalProgLabel.setText(totalKcalToday + "/" + highBmiKcal);

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(highBmiDistChallenge);
                progressDist = (totalDistToday / highBmiDist) / 1000;
                distProgressBar.setProgress(progressDist);
                distProgLabel.setText((totalDistToday / 1000) + "/" + highBmiDist);
                break;
            case 4:
                // If the BMI is 30 or higher, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(veryHighBmiStepChallenge);
                progress = totalStepsToday / veryHighBmiKcal;
                stepProgressBar.setProgress(progress);
                stepProgLabel.setText(totalStepsToday + "/" + veryHighBmiKcal);

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(veryHighBmiKcalChallenge);
                progressKcal = totalKcalToday / veryHighBmiKcal;
                kcalProgressBar.setProgress(progressKcal);
                kcalProgLabel.setText(totalKcalToday + "/" + veryHighBmiKcal);

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(veryHighBmiDistChallenge);
                progressDist = (totalDistToday / veryHighBmiDist) / 1000;
                distProgressBar.setProgress(progressDist);
                distProgLabel.setText((totalDistToday / 1000) + "/" + veryHighBmiDist);
                break;
        }
    }

    public void backToMainDashboard(ActionEvent event) throws IOException {
        // loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
        root = loader.load();

        MainDashboardController mainDashboardController = loader.getController();
        mainDashboardController.showChart(24);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}