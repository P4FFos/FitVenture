package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.stats.Stats;
import fitVenture.backend.user.User;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private String lowBmiChallenge = "Walk " + lowBMISteps +" steps today!";
    private String normalBmiChallenge = "Walk " + normalBMISteps + " steps today!";
    private String highBmiChallenge = "Walk " + highBMISteps + " steps today!";
    private String veryHighBmiChallenge = "Walk " + veryHighBMISteps + " steps today!";

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

        HashMap<String, Stats> statsHashMap  = FitVentureStart.currentUser.getStats();
        int totalStepsToday = 0;
        double totalKcalToday = 0.0;
        double totalDistToday = 0.0;
        String todayDate = Current_Date.getDateToday(new Date()).substring(0, 10);

        for (Map.Entry<String, Stats> valueFromHashmap : statsHashMap.entrySet()) {

            String statsDate = valueFromHashmap.getKey().substring(0, 10);
            if (statsDate.equals(todayDate)) {
                Stats stats = valueFromHashmap.getValue();
                int stepsValue = Integer.parseInt(stats.getSteps());
                double distValue = Double.parseDouble(stats.getDistance());
                double kcalValue = Double.parseDouble(stats.getCalories());
                totalStepsToday += stepsValue;
                totalKcalToday += kcalValue;
                totalDistToday += distValue;
            }
        }

        //logic to show the corresponding daily challenge for the user based on their BMI
        if (bmiValue < 18.5) {
            dailyStepChallenge.setText("Your daily challenge is: " + lowBmiChallenge);
            double progress =  totalStepsToday / lowBMISteps;
            stepProgressBar.setProgress(progress);
            stepProgLabel.setText(totalStepsToday + "/" + lowBMISteps);
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            dailyStepChallenge.setText("Your daily challenge is: " + normalBmiChallenge);
            double progress = totalStepsToday / normalBMISteps;
            stepProgressBar.setProgress(progress);
            stepProgLabel.setText(totalStepsToday + "/" + normalBMISteps);
        } else if (bmiValue >= 25 && bmiValue < 30) {
            dailyStepChallenge.setText("Your daily challenge is: " + highBmiChallenge);
            double progress =  totalStepsToday / highBMISteps;
            stepProgressBar.setProgress(progress);
            stepProgLabel.setText(totalStepsToday + "/" + highBMISteps);
        } else {
            dailyStepChallenge.setText("Your daily challenge is: " + veryHighBmiChallenge);
            int maxSteps = veryHighBMISteps;
            double progress = totalStepsToday / maxSteps;
            stepProgressBar.setProgress(progress);
            stepProgLabel.setText(totalStepsToday + "/" + maxSteps);
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