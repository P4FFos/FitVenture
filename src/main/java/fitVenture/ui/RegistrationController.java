package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.exceptions.RegistrationException;
import fitVenture.backend.user.User;
import fitVenture.backend.utils.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    public PasswordField password;
    @FXML
    public Label errorLabel;

    public void register(ActionEvent event) throws IOException {
        // attributes used to store data from the fields that user entered
        String usernameText;
        String passwordText;

        try {
            usernameText = username.getText();
            passwordText = password.getText();
            FitVenture fitVenture = FitVentureStart.fitVenture;

            // with this if method we check if username entered by user exists in the database
            if (fitVenture.register(usernameText, passwordText, "0", "0")) {
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                System.out.println("Registration Successful");
            }
        } catch (RegistrationException e){
            // shows an error based on the condition of the input(ex. empty fields, existing username)
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
            username.clear();
            password.clear();
            System.out.println("Registration failed");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backToRegLoginPage(ActionEvent event) throws IOException{
        // loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginRegistrationScene.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
