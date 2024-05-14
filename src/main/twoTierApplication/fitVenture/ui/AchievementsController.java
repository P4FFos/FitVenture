package fitVenture.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fitVenture.backend.achievements.Achievement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;


public class AchievementsController {

    // Constants
    final static int PROGRESSBAR_COMPLETE = 1;
    final static double PROGRESSBAR_WIDTH = 400.0;
    final static double PROGRESSBAR_HEIGHT = 40.0;
    final static double PROGRESSBARTEXT_WIDTH = 200.0;
    final static double PROGRESSBARTEXT_HEIGHT = 15.0;

    final static double SCROLLBAR_WIDTH = 680.0;
    final static double SCROLLBAR_HEIGHT = 1000.0;

    final static double VBOX_CHILDREN_MARGIN = 15.0;
    // Variables used for FXML
    @FXML
    private VBox achievementVBox;
    @FXML
    private ScrollPane scrollPaneAchievements;

    @FXML
    public void showAchievements() {

        scrollPaneAchievements.setContent(achievementVBox);
        // Gets a list of all achievements
        HashMap<String, Achievement> achievements = FitVentureStart.currentUser.getListOfAchievement().getAllAchievements();
        for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
            // For every iteration, get the entrys key and value
            String keyValue = entry.getKey();
            Achievement values = entry.getValue();
            // Creating the labels for the achievement
            Label achievementTag = new Label(values.getTag());
            Label achievementDesc = new Label(values.getDescription());
            Label achievementName = new Label(keyValue);

            // Set the colour of the text in the labels to dark blue
            achievementTag.setStyle("-fx-text-fill:#090b3f");
            achievementDesc.setStyle("-fx-text-fill: #090b3f");
            achievementName.setStyle("-fx-text-fill: #090b3f");
            achievementDesc.setStyle("-fx-border-color:black");
            achievementTag.setStyle("-fx-border-color:black");
            achievementName.setStyle("-fx-border-color:black");
            achievementTag.setStyle("-fx-font-size:30");
            achievementDesc.setStyle("-fx-font-size:20");
            achievementName.setStyle("-fx-font-size:30");
            achievementTag.setPadding(new Insets(80, 0, 50, 10));
            achievementDesc.setPadding(new Insets(50, 100, 50 ,200));
            achievementName.setPadding(new Insets(80, 100 , 50, 30));


            // Creating and setting up the progressbar for the achievements
            Label progressBarText = new Label();
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(PROGRESSBAR_WIDTH);
            progressBar.setPrefHeight(PROGRESSBAR_HEIGHT);


            // Setting up the progressbar text 
            progressBarText.setStyle("-fx-text-fill: #090b3f"); // Set the color to purple
            progressBarText.setPrefHeight(PROGRESSBARTEXT_HEIGHT); // Set the height of the text
            progressBarText.setPrefWidth(PROGRESSBARTEXT_WIDTH); // Set the width of the text
            progressBarText.setLabelFor(progressBar); // Put this label on the progressBar

            // Creates an anchorpane with the children being the values passed to the parameters
            AnchorPane allAnchorPane = new AnchorPane(achievementName, achievementTag, achievementDesc, progressBar, progressBarText);

            // Creates a null backgroundFill object to be used later
            BackgroundFill backgroundFill = new BackgroundFill(null, null, null);

            // These if statements are responsible for the coloring of the achievements depending on the completion of the progress bar
            if (values.getTag().equals("Distance")) {   // Gets the tag and checks if it equals to "Distance"
                if (FitVentureStart.currentUser.totalDistanceSinceStart() < values.getCompletionRequirement()) {    // If the total distance traveled since the start of the account is less than the completion requirement:
                    progressBar.setProgress(FitVentureStart.currentUser.totalDistanceSinceStart() / values.getCompletionRequirement());     // Set the progress of the bar to the total distance traveled since start of account divided by the completion requirement of the achievement
                    backgroundFill = new BackgroundFill(Color.rgb(169, 169, 223), CornerRadii.EMPTY, Insets.EMPTY);     // Color the background of the achievement grey
                    progressBarText.setText(String.format("%.2f Out Of %.2fm", FitVentureStart.currentUser.totalDistanceSinceStart(), values.getCompletionRequirement())); // Set the achievement text to tell the user how much distance is left in meters
                } else {
                    progressBar.setProgress(PROGRESSBAR_COMPLETE);        // If the total distance is greater than the completion requirement for the achievement, set it at 100%
                    progressBarText.setText("Done!");     // Set the text of the label to "Done!"
                    progressBarText.setPadding(new Insets(0,0,0,50)); // aligns the text label to the centre of the bar
                    backgroundFill = new BackgroundFill(Color.rgb(145, 145, 245), CornerRadii.EMPTY, Insets.EMPTY);    // Set the background color of the achievement to dark-purple
                }

            } else if (values.getTag().equals("Calories")) {        // Gets the tag and checks if it equals to "Calories"
                if (FitVentureStart.currentUser.totalCaloriesSinceStart() < values.getCompletionRequirement()) {        // If the total calories burned since the start of the account is less than the completion requirement:
                    progressBar.setProgress(FitVentureStart.currentUser.totalCaloriesSinceStart() / values.getCompletionRequirement()); // Set the progress of the bar to the total calories burned since start of account divided by the completion requirement of the achievement
                    backgroundFill = new BackgroundFill(Color.rgb(169, 169, 223), CornerRadii.EMPTY, Insets.EMPTY);   // Color the background of the achievement grey
                    progressBarText.setText(String.format("%.2f Out Of %.2fKCal", FitVentureStart.currentUser.totalDistanceSinceStart(), values.getCompletionRequirement())); // Set the achievement text to tell the user how many calories are left im KCal

                } else {
                    progressBar.setProgress(PROGRESSBAR_COMPLETE);        // If the total calories is greater than the completion requirement for the achievement, set it at 100%
                    progressBarText.setText("Done!");   // Set the text of the label to "Done!"
                    progressBarText.setPadding(new Insets(0,0,0,50));
                    backgroundFill = new BackgroundFill(Color.rgb(145, 145, 245), CornerRadii.EMPTY, Insets.EMPTY);  // Set the background color of the achievement to darkgrey
                }
            }
            // Sets the margins for all the children in the anchorpane, aswell as the anchorpane itself
            setMargins(allAnchorPane);
            // Creates a border and sets it to the anchorpane and the achievements name
            Border newBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL));
            allAnchorPane.setBorder(newBorder);
            achievementName.setBorder(newBorder);
            // Creates and sets the background for the anchorpane
            Background anchorBackground = new Background(backgroundFill);
            allAnchorPane.setBackground(anchorBackground);
            // Sets the margins for the children in the Vbox
            VBox.setMargin(allAnchorPane, new Insets(VBOX_CHILDREN_MARGIN));
            // Add the anchorpane to the vbox
            achievementVBox.getChildren().addAll(allAnchorPane);
        }
        // Sets the height and width for the scrollbar element
        scrollPaneAchievements.setPrefWidth(SCROLLBAR_WIDTH);
        scrollPaneAchievements.setPrefHeight(SCROLLBAR_HEIGHT);

    }

    public void setMargins(AnchorPane allAnchorPane) {
        // Name of the Achievement
        Node achievementName = allAnchorPane.getChildren().get(0);
        AnchorPane.setLeftAnchor(achievementName, 150.0);

        // Tag of the Achievement
        Node achievementTag = allAnchorPane.getChildren().get(1);
        AnchorPane.setLeftAnchor(achievementTag, 10.0);

        // Description of the Achievement
        Node achievementDesc = allAnchorPane.getChildren().get(2);
        AnchorPane.setRightAnchor(achievementDesc, 10.0);
        AnchorPane.setLeftAnchor(achievementDesc, 300.0);

        // Progressbar of the Achievement
        Node progressBar = allAnchorPane.getChildren().get(3);
        AnchorPane.setTopAnchor(progressBar, 70.0);
        AnchorPane.setRightAnchor(progressBar, 60.0);


        // ProgressBar label
        Node progressBarText = allAnchorPane.getChildren().get(4);
        AnchorPane.setTopAnchor(progressBarText, 80.0);
        AnchorPane.setRightAnchor(progressBarText, 130.0);


        // For all children in anchorpane
        AnchorPane.setTopAnchor(allAnchorPane, 80.0);
        AnchorPane.setBottomAnchor(allAnchorPane, 120.0);
    }

    @FXML
    public void returnBackToMain(ActionEvent event) throws IOException {
        // loads MainDashboardScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
        Parent root = loader.load(); // loading the MainDashboardScene.fxml

        MainDashboardController mainDashboardController = loader.getController();
        mainDashboardController.showChart();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // getting the stage
        Scene scene = new Scene(root); // adding the parent to the scene
        stage.setScene(scene); // adding scene to the stage
        stage.show(); // showing the stage
    }


}
