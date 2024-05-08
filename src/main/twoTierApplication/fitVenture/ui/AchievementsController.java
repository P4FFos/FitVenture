package fitVenture.ui;

import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import fitVenture.backend.achievements.Achievement;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
import javafx.scene.shape.Rectangle;

public class AchievementsController {
    
    @FXML
    private VBox achievementVBox;
   
    @FXML
    public void showAchievements() {
        
        HashMap<String, Achievement> achievements = FitVentureStart.currentUser.getListOfAchievement().getCompletedAchievements();
        System.out.println(achievements);
        for (Achievement achievement : achievements.values()) {
            Rectangle rectangle = new Rectangle(50, 50, Color.RED);
            
            
            Label achievementTag = new Label(achievement.getTag());
            Label achievementDesc = new Label(achievement.getDescription());
            
            achievementTag.setTextFill(Color.BLACK);
            achievementDesc.setTextFill(Color.BLACK);
            
            
            AnchorPane anchorPane = new AnchorPane(rectangle, achievementTag, achievementDesc);
            AnchorPane.setRightAnchor(achievementDesc, 10.0);
            AnchorPane.setLeftAnchor(achievementDesc, 150.0);
            AnchorPane.setTopAnchor(achievementDesc, 10.0);
            AnchorPane.setBottomAnchor(achievementDesc, 10.0);

            AnchorPane.setLeftAnchor(achievementTag, 30.0);
            AnchorPane.setLeftAnchor(rectangle, 10.0);
            Border newBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL));
            anchorPane.setBorder(newBorder);   
            BackgroundFill backgroundFill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY); 
            Background anchorBackground = new Background(backgroundFill);
            anchorPane.setBackground(anchorBackground);
            
            //achievementTag.setLabelFor(anchorPane);
            //achievementDesc.setLabelFor(anchorPane);
            

            achievementVBox.getChildren().addAll(anchorPane);
        }
    }


}
