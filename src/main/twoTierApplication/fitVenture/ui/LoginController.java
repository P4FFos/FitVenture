package fitVenture.ui;
import fitVenture.backend.FitVenture;
import fitVenture.backend.MQTTSubscriber;
import fitVenture.backend.MQTTPublisher;
import fitVenture.backend.exceptions.LoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Label errorLabel;
    @FXML
    public Button loginButton;
    @FXML
    public Button returnButton;

    public void login(ActionEvent event) throws IOException {
        // Attributes used to store data from the fields that user entered
        String passwordText;
        String usernameText;

        try {
            FitVenture fitVenture = FitVentureStart.fitVenture;
            usernameText = username.getText();
            passwordText = password.getText();

            if (fitVenture.verifyUser(usernameText, passwordText)) {
                // Store user object data, once user is logged in
                FitVentureStart.currentUser = fitVenture.getUser(usernameText);
                FitVentureStart.currentUser.checkCompletedAchievements();

                // Publish to the MQTT broker
                new MQTTPublisher();
                // Subscribe to the MQTT broker
                new MQTTSubscriber();

                // Loads MainDashboardScene one user pressed login button
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
                root = loader.load();

                MainDashboardController mainDashboardController = loader.getController();
                mainDashboardController.showChart();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (LoginException e) {
            // Shows an error based on the condition of the input(ex. empty fields, existing username)
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
            username.clear();
            password.clear();
        }
    }

    public void backToRegLoginPage(ActionEvent event) throws IOException {
        // Loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginRegistrationScene.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
