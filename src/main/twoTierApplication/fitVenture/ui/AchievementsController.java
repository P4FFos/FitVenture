package fitVenture.ui;

import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import fitVenture.backend.achievements.Achievement;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
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
            AnchorPane anchorPane = new AnchorPane(rectangle);
            Border newBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            anchorPane.setBorder(newBorder);

            achievementVBox.getChildren().add(rectangle);
        }
    }


}
