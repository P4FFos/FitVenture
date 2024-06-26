package fitVenture.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginRegistrationController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    public void register(ActionEvent event) throws IOException {
        // Loads RegistrationScene one user pressed register button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationScene.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Getting the stage
        scene = new Scene(root); // Adding the parent to the scene
        stage.setScene(scene); // Adding scene to the stage
        stage.show(); // Showing the stage
    }

    public void login(ActionEvent event) throws IOException {
        // Loads LoginScene one user pressed login button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Getting the stage
        scene = new Scene(root); // Adding the parent to the scene
        stage.setScene(scene); // Adding scene to the stage
        stage.show(); // Showing the stage
    }
}
