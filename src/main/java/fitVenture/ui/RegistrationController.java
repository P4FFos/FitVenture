package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.exceptions.RegistrationException;
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
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
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
    public Pane optionalPane;
    @FXML
    public Label optionalErrorLabel;
    @FXML
    public Label nameErrorLabel;
    @FXML
    public Label nameOptionalErrorLabel;
    @FXML
    public TextField height;
    @FXML
    public TextField weight;
    @FXML
    public TextField name;
    @FXML
    public Label errorLabel;

    public void register(ActionEvent event) throws IOException {
        // attributes used to store data from the fields that user entered
        String usernameText;
        String passwordText;
        String weightText;
        String heightText;
        String nameText; 

        try {
            usernameText = username.getText();
            passwordText = password.getText();

            FitVenture fitVenture = FitVentureStart.fitVenture;
            // Checks if the pane with the optional inputs(weight, height) is visible.
            if(optionalPane.isVisible()) {
                weightText = weight.getText();
                heightText = height.getText();
                nameText = name.getText();
                boolean optionalIsInteger;
                boolean nameContainsInt;
                // Checks if the name contains an integer.
                try {
                    Integer.parseInt(nameText);
                    nameContainsInt = true;
                    nameErrorLabel.setVisible(true);
                    nameOptionalErrorLabel.setVisible(false);
                    System.out.println("Name should not contain any numbers.");
                } catch (Exception e) {
                    nameContainsInt = false;
                    nameErrorLabel.setVisible(false);
                }
                // Checks if the optional values are integers.
                try {
                    Integer.parseInt(weightText);
                    Integer.parseInt(heightText);
                    optionalIsInteger = true;
                    optionalErrorLabel.setVisible(false);

                } catch (NumberFormatException e) {
                    optionalIsInteger = false;
                    System.out.println("Incorrect input, should be a number.");
                    optionalErrorLabel.setVisible(true);
                    nameOptionalErrorLabel.setVisible(false);
                }

                if (optionalIsInteger && !nameContainsInt) {
                    if (fitVenture.register(usernameText, passwordText, weightText, heightText, nameText)) {
                        FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
                        root = loader.load();
        
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        System.out.println("Registration Successful");
                    }
                } else if (!optionalIsInteger && nameContainsInt) {
                    optionalErrorLabel.setVisible(false);
                    nameErrorLabel.setVisible(false);
                    nameOptionalErrorLabel.setVisible(true);
                }
            } else {
                // with this if method we check if username entered by user exists in the database
                if (fitVenture.register(usernameText, passwordText, "0", "0", "N/A")) {
                    FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
                    root = loader.load();
    
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    System.out.println("Registration Successful");
                }
            }
        } catch (RegistrationException e){
            // shows an error based on the condition of the input(ex. empty fields, existing username)
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
            username.clear();
            password.clear();
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

    public void optionalInput(MouseEvent event) throws IOException {
        if (optionalPane.isVisible()) {
            optionalPane.setVisible(false);
        } else {
            optionalPane.setVisible(true);
        }
    }
}
