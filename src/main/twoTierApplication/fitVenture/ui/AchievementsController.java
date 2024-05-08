package fitVenture.ui;

import java.util.HashMap;

import fitVenture.backend.achievements.Achievement;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AchievementsController {
    
    @FXML
    private VBox achievementVBox;
   
    @FXML
    public void showAchievements() {
        
        HashMap<String, Achievement> achievements = FitVentureStart.currentUser.getListOfAchievement().getCompletedAchievements();
        System.out.println(achievements);
        for (Achievement achievement : achievements.values()) {
            Rectangle rectangle = new Rectangle(100, 50, Color.RED);


            achievementVBox.getChildren().add(rectangle);
        }
    }


}
