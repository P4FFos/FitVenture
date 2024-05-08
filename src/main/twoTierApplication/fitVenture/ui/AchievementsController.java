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
    
    @FXML
    private VBox achievementVBox;
    @FXML
    private ScrollPane scrollPaneAchievements;
    
    @FXML
    public void showAchievements() {
        
        scrollPaneAchievements.setContent(achievementVBox);
        HashMap<String, Achievement> achievements = FitVentureStart.currentUser.getListOfAchievement().getAllAchievements();
        for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
            String keyValue = entry.getKey();
            Achievement values = entry.getValue();
            
            Label achievementTag = new Label(values.getTag());
            Label achievementDesc = new Label(values.getDescription());
            Label key = new Label(keyValue);
            
            achievementTag.setTextFill(Color.BLACK);
            achievementDesc.setTextFill(Color.BLACK);
            key.setTextFill(Color.BLACK);
            
            Label progressBarText = new Label(FitVentureStart.currentUser.totalDistanceSinceStart() + " Out Of " + values.getCompletionRequirement());
            
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(400.0);
            progressBar.setPrefHeight(40.0);
            
            progressBarText.setTextFill(Color.BLACK);
            progressBarText.setPrefHeight(15.0);
            progressBarText.setPrefWidth(200.0);
            progressBarText.setLabelFor(progressBar);

            AnchorPane allAnchorPane = new AnchorPane(key, achievementTag, achievementDesc, progressBar, progressBarText);
            
            BackgroundFill backgroundFill = new BackgroundFill(null, null, null);  
            
            if (values.getTag().equals("Distance")) {
                if (FitVentureStart.currentUser.totalDistanceSinceStart() < values.getCompletionRequirement()) {
                    progressBar.setProgress(FitVentureStart.currentUser.totalDistanceSinceStart() / values.getCompletionRequirement());
                    backgroundFill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
                } else {
                    progressBar.setProgress(1);
                    progressBarText.setText("Done!");
                    backgroundFill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
                }
                
            } else if (values.getTag().equals("Calories")) {
                if (FitVentureStart.currentUser.totalCaloriesSinceStart() < values.getCompletionRequirement()) {
                    progressBar.setProgress(FitVentureStart.currentUser.totalCaloriesSinceStart() / values.getCompletionRequirement());
                    backgroundFill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
                } else {
                    progressBar.setProgress(1);
                    progressBarText.setText("Done!");
                    backgroundFill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
                }
            }
            
            setMargins(allAnchorPane);
            Border newBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL));
            allAnchorPane.setBorder(newBorder);  
            key.setBorder(newBorder);
            Background anchorBackground = new Background(backgroundFill);
            allAnchorPane.setBackground(anchorBackground);
            //achievementTag.setLabelFor(anchorPane);
            //achievementDesc.setLabelFor(anchorPane);
            
            VBox.setMargin(allAnchorPane, new Insets(25.0));
            achievementVBox.getChildren().addAll(allAnchorPane);
        }
        scrollPaneAchievements.setPrefWidth(680);
        scrollPaneAchievements.setPrefHeight(1000);
        
    }
    
    public void setMargins(AnchorPane allAnchorPane) {
        // Name of the Achievement
        AnchorPane.setLeftAnchor(allAnchorPane.getChildren().get(0), 10.0); 

        // Tag of the Achievement
        AnchorPane.setLeftAnchor(allAnchorPane.getChildren().get(1), 150.0); 
        
        // Description of the Achievement
        AnchorPane.setRightAnchor(allAnchorPane.getChildren().get(2), 10.0); 
        AnchorPane.setLeftAnchor(allAnchorPane.getChildren().get(2), 300.0); 

        // Progressbas of the Achievement
        AnchorPane.setTopAnchor(allAnchorPane.getChildren().get(3), 120.0);
        AnchorPane.setRightAnchor(allAnchorPane.getChildren().get(3), 60.0); 

        // ProgressBar label
        AnchorPane.setTopAnchor(allAnchorPane.getChildren().get(4), 130.0);
        AnchorPane.setRightAnchor(allAnchorPane.getChildren().get(4), 80.0); 
        // For everything
        AnchorPane.setTopAnchor(allAnchorPane, 10.0);
        AnchorPane.setBottomAnchor(allAnchorPane, 10.0);
    }

    @FXML
    public void returnBackToMain(ActionEvent event) throws IOException {
        // loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
        Parent root = loader.load(); // loading the AchievementsScene.fxml

        MainDashboardController mainDashboardController = loader.getController();
        mainDashboardController.showChart();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // getting the stage
        Scene scene = new Scene(root); // adding the parent to the scene
        stage.setScene(scene); // adding scene to the stage
        stage.show(); // showing the stage
    }

    
}
