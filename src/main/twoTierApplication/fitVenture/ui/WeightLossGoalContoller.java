package fitVenture.ui;

import fitVenture.backend.goal.WeightGoal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class WeightLossGoalContoller extends ProgressBar {
    @FXML
    private AnchorPane progracePane; // the container that holds the progressBar
    @FXML
    private Label goalInWeight; // Label that displays weight goal
    @FXML
    private Label goalInCalories; // Label that displays goal in Calories
    private static ProgressBar progressBar; // the reference to progressBar object
    private  WeightGoal weightGoal; // reference to weight goal object





    public void progressBarProgress(String key){ // this methos is responcibe for creating and displaying the progressBar
        progressBar = new ProgressBar();
        weightGoal = FitVentureStart.currentUser.getWeightGoal(key); // weightGoal object

        displayWeightAndCalories(); // displaying weight and calories in respective labers

        progressBar.setProgress(calCulateProgress()); // setting progress value to the progressBar

        progressBar.setPrefHeight(91);
        progressBar.setPrefWidth(1796);

        progracePane.getChildren().add(progressBar); // adding progressBar to the container for display

    }

    private double calCulateProgress(){ // this metod is responcible for calculating the progress to the goal. the returned value should be between 0 and 1
       double progress = weightGoal.getProgressToGoalInCalories();
       double goal =weightGoal.getGoalInCaloris();
       return (progress/goal);
    }


    // loading the goal.fxml file when the user clicks on return
    public void exitClick(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Goals.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // displaying calculated calories and set weight to the user
    public void displayWeightAndCalories(){
        double goal = weightGoal.getGoalWeight();
        double calories = weightGoal.caloriesInGoal();
        goalInWeight.setText("Weight Goal: " + goal);
        goalInCalories.setText("Goal In Calories: " + calories);


    }

    // changing the progress value as more calories are burned
    public static void loopForProgressBar(double value) {
            progressBar.setProgress(value);

        } // This method shall be called everytime the weightGoal is being updated by data from the arduino
    }












