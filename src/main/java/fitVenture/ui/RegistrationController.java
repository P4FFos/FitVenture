package fitVenture.ui;

import fitVenture.backend.FitVenture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Label errorText;
    public void register(ActionEvent event) throws IOException {
        String usernameText;
        String passwordText;
        try {
            usernameText = username.getText();
            passwordText = password.getText();
            FitVenture fitVenture = FitVentureStart.fitVenture;

            if (fitVenture.register(usernameText, passwordText, "0", "0")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                System.out.println("Registration Successful");
            }
        } catch (Exception e) {
            System.out.println("Registration Failed");
        }
    }
}
