package fitVenture.ui;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.utils.Current_Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Label stepProgLabel;
    @FXML
    private Label kcalProgLabel;
    @FXML
    private Label distProgLabel;

    private final int LOW_BMI_STEPS = 3000;
    private final int NORMAL_BMI_STEPS = 4000;
    private final int HIGH_BMI_STEPS = 5000;
    private final int VERY_HIGH_BMI_STEPS = 6000;
    

    private final double LOW_BMI_KCAL = 500.0;
    private final double NORMAL_BMI_KCAL = 800.0;
    private final double HIGH_BMI_KCAL = 1000.0;
    private final double VERY_HIGH_BMI_KCAL = 1200.0;

    private final double LOW_BMI_DIST = 3.0;
    private final double NORMAL_BMI_DIST = 4.0;
    private final double HIGH_BMI_DIST = 5.0;
    private final double VERY_HIGH_BMI_DIST = 6.0;

    private final String LOW_BMI_STEP_CHALLENGE = "Your daily challenge is: " + "Walk " + LOW_BMI_STEPS + " steps today!";
    private final String NORMAL_BMI_STEP_CHALLENGE = "Your daily challenge is: " + "Walk " + NORMAL_BMI_STEPS + " steps today!";
    private final String HIGH_BMI_STEP_CHALLENGE = "Your daily challenge is: " + "Walk " + HIGH_BMI_STEPS + " steps today!";
    private final String VERY_HIGH_BMI_STEP_CHALLENGE = "Your daily challenge is: " + "Walk " + VERY_HIGH_BMI_STEPS + " steps today!";

    private final String LOW_BMI_KCAL_CHALLENGE = "Your daily challenge is: " + "Burn " +LOW_BMI_KCAL + " kcal today!";
    private final String NORMAL_BMI_KCAL_CHALLENGE = "Your daily challenge is: " + "Burn " + NORMAL_BMI_KCAL + " kcal today!";
    private final String HIGH_BMI_KCAL_CHALLENGE = "Your daily challenge is: " + "Burn " + HIGH_BMI_KCAL + " kcal today!";
    private final String VERY_HIGH_BMI_KCAL_CHALLENGE = "Your daily challenge is: " + "Burn " + VERY_HIGH_BMI_KCAL + " kcal today!";

    private final String LOW_BMI_DIST_CHALLENGE = "Your daily challenge is: Walk " + LOW_BMI_DIST + " km today!";
    private final String NORMAL_BMI_DIST_CHALLENGE = "Your daily challenge is: Walk " + NORMAL_BMI_DIST + " km today!";
    private final String HIGH_BMI_DIST_CHALLENGE = "Your daily challenge is: Walk " + HIGH_BMI_DIST + " km today!";
    private final String VERY_HIGH_BMI_DIST_CHALLENGE = "Your daily challenge is: Walk " + VERY_HIGH_BMI_DIST + " km today!";

    public void showChallenges() {
        stepProgressBar.setStyle("-fx-accent: #a9a9df;");
        distProgressBar.setStyle("-fx-accent: #a9a9df;");
        kcalProgressBar.setStyle("-fx-accent: #a9a9df;");

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
        //Initialize progress variables
        double progressStep;
        double progressKcal;
        double progressDist;

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


        // Set the daily challenges based on the BMI category
        switch (bmiCategory) {
            case 1:
                // If the BMI is less than 18.5, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(LOW_BMI_STEP_CHALLENGE);
                progressStep = totalStepsToday / LOW_BMI_STEPS;
                stepProgressBar.setProgress(progressStep);
                if (progressStep >= 1) {
                    stepProgLabel.setText("Completed!");
                } else {
                    stepProgLabel.setText(totalStepsToday + "/" + LOW_BMI_STEPS);
                }

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(LOW_BMI_KCAL_CHALLENGE);
                progressKcal = totalKcalToday / LOW_BMI_KCAL;
                kcalProgressBar.setProgress(progressKcal);
                if (progressKcal >= 1) {
                    kcalProgLabel.setText("Completed!");
                } else {
                    kcalProgLabel.setText(totalKcalToday + "/" + LOW_BMI_KCAL);
                }

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(LOW_BMI_DIST_CHALLENGE);
                progressDist = (totalDistToday / LOW_BMI_DIST) / 1000;
                distProgressBar.setProgress(progressDist);
                if (progressDist >= 1) {
                    distProgLabel.setText("Completed!");
                } else {
                    distProgLabel.setText((totalDistToday / 1000) + "/" + LOW_BMI_DIST);
                }
                break;
            case 2:
                // If the BMI is between 18.5 and 25, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(NORMAL_BMI_STEP_CHALLENGE);
                progressStep = totalStepsToday / NORMAL_BMI_STEPS;
                stepProgressBar.setProgress(progressStep);
                if (progressStep >= 1) {
                    stepProgLabel.setText("Completed!");
                } else {
                    stepProgLabel.setText(totalStepsToday + "/" + NORMAL_BMI_STEPS);
                }

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(NORMAL_BMI_KCAL_CHALLENGE);
                progressKcal = totalKcalToday / NORMAL_BMI_KCAL;
                kcalProgressBar.setProgress(progressKcal);
                if (progressKcal >= 1) {
                    kcalProgLabel.setText("Completed!");
                } else {
                    kcalProgLabel.setText(totalKcalToday + "/" + NORMAL_BMI_KCAL);
                }

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(NORMAL_BMI_DIST_CHALLENGE);
                progressDist = (totalDistToday / NORMAL_BMI_DIST) / 1000;
                distProgressBar.setProgress(progressDist);
                if (progressDist >= 1) {
                    distProgLabel.setText("Completed!");
                } else {
                    distProgLabel.setText((totalDistToday / 1000) + "/" +NORMAL_BMI_DIST);
                }
                break;
            case 3:
                // If the BMI is between 25 and 30, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(HIGH_BMI_STEP_CHALLENGE);
                progressStep = totalStepsToday / HIGH_BMI_STEPS;
                stepProgressBar.setProgress(progressStep);
                if (progressStep >= 1) {
                    stepProgLabel.setText("Completed!");
                } else {
                    stepProgLabel.setText(totalStepsToday + "/" + HIGH_BMI_STEPS);
                }


                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(HIGH_BMI_KCAL_CHALLENGE);
                progressKcal = totalKcalToday / HIGH_BMI_KCAL;
                kcalProgressBar.setProgress(progressKcal);
                if (progressKcal >= 1) {
                    kcalProgLabel.setText("Completed!");
                } else {
                    kcalProgLabel.setText(totalKcalToday + "/" + HIGH_BMI_KCAL);
                }


                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(HIGH_BMI_DIST_CHALLENGE);
                progressDist = (totalDistToday / HIGH_BMI_DIST) / 1000;
                distProgressBar.setProgress(progressDist);
                if (progressDist >= 1) {
                    distProgLabel.setText("Completed!");
                } else {
                    distProgLabel.setText((totalDistToday / 1000) + "/" + HIGH_BMI_DIST);
                }
                break;
            case 4:
                // If the BMI is 30 or higher, set the daily step challenge and update the progress bar and label
                dailyStepChallenge.setText(VERY_HIGH_BMI_STEP_CHALLENGE);
                progressStep = totalStepsToday / VERY_HIGH_BMI_STEPS;
                stepProgressBar.setProgress(progressStep);
                if (progressStep >= 1) {
                    stepProgLabel.setText("Completed!");
                } else {
                    stepProgLabel.setText(totalStepsToday + "/" + VERY_HIGH_BMI_STEPS);
                }

                // Set the daily calorie challenge and update the progress bar and label
                dailyKcalChallenge.setText(VERY_HIGH_BMI_KCAL_CHALLENGE);
                progressKcal = totalKcalToday / VERY_HIGH_BMI_KCAL;
                kcalProgressBar.setProgress(progressKcal);
                if (progressKcal >= 1) {
                    kcalProgLabel.setText("Completed!");
                } else {
                    kcalProgLabel.setText(totalKcalToday + "/" + VERY_HIGH_BMI_KCAL);
                }

                // Set the daily distance challenge and update the progress bar and label
                dailyDistanceChallenge.setText(VERY_HIGH_BMI_DIST_CHALLENGE);
                progressDist = (totalDistToday / VERY_HIGH_BMI_DIST) / 1000;
                distProgressBar.setProgress(progressDist);
                if (progressDist >= 1) {
                    distProgLabel.setText("Completed!");
                } else {
                    distProgLabel.setText((totalDistToday / 1000) + "/" + VERY_HIGH_BMI_DIST);
                }
                break;
        }
    }

    public void backToMainDashboard(ActionEvent event) throws IOException {
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
