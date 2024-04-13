package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.utils.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class UserProfileController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    public Label heightIndexValue;
    @FXML
    public Label weightIndexValue;
    @FXML
    public Label bodyIndexValue;
    @FXML
    public Label loggedUsername;

    // method to show information of a logged user in the user profile
    public void showData() {
        loggedUsername.setText(FitVentureStart.currentUser.getUsername());
        weightIndexValue.setText(String.valueOf(FitVentureStart.currentUser.getWeight()));
        heightIndexValue.setText(String.valueOf(FitVentureStart.currentUser.getHeight()));
        //calculates BMI
        double bmiValue = FitVentureStart.currentUser.getWeight() / Math.pow(FitVentureStart.currentUser.getHeight() / 100, 2);
        //rounds bmi value up to 2 digits after coma
        bodyIndexValue.setText(String.format("%.2f", bmiValue));
    }
}





