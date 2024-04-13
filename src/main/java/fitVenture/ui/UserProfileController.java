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

import static fitVenture.ui.FitVentureStart.currentUser;


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
        loggedUsername.setText(currentUser.getUsername());
        weightIndexValue.setText(String.valueOf(currentUser.getWeight()));
        heightIndexValue.setText(String.valueOf(currentUser.getHeight()));
        //calculates BMI
        double bmiValue = currentUser.getWeight() / Math.pow(currentUser.getHeight() / 100, 2);
        //rounds bmi value up to 2 digits after coma
        bodyIndexValue.setText(String.format("%.2f", bmiValue));
    }
}





