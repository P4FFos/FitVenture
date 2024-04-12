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
    public TextField heightField;
    @FXML
    public TextField weightField;
    @FXML
    public Label bodyIndexValue;
    @FXML
    public Label loggedUsername;

    public void showData() {
        loggedUsername.setText(currentUser.getUsername());
    }
}





