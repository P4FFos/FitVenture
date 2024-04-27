package fitVenture.ui;

import fitVenture.backend.utils.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public Label fullNameValue;
    @FXML
    public Label bodyIndexValue;
    @FXML
    public Label welcomeUsername;
    @FXML
    public Button saveButton;
    @FXML
    public Button editButton;
    @FXML
    public TextField weightBox;
    @FXML
    public TextField heightBox;
    @FXML
    public TextField nameBox;
    @FXML
    public Label errorLabel;

    // method to show information of a logged user in the user profile
    public void showData() {
        // get information of the logged user from the database
        String userUsername = FitVentureStart.currentUser.getUsername();
        String userWeight = FitVentureStart.currentUser.getWeight();
        String userHeight = FitVentureStart.currentUser.getHeight();
        String userName = FitVentureStart.currentUser.getName();

        // set user information from the database into labels
        welcomeUsername.setText(userUsername);
        weightIndexValue.setText(userWeight);
        heightIndexValue.setText(userHeight);
        fullNameValue.setText(userName);

        // checks if the user height and weight fields are not empty
        if (!heightIndexValue.getText().isEmpty() && !weightIndexValue.getText().isEmpty()) {
            // parse height and weight values from string to double
            double height = Double.parseDouble(userHeight);
            double weight = Double.parseDouble(userWeight);

            // calculates BMI based on the new values
            double bmiValue = weight / Math.pow(height / 100, 2);

            // rounds BMI value up to 2 digits after coma
            bodyIndexValue.setText(String.format("%.2f", bmiValue));
        } else {
            // shows an error if the user weight and height fields are empty
            errorLabel.setVisible(true);
            errorLabel.setText("Failed. Input your personal data");
        }
    }

    // method which changes the visibility of the buttons and text fields when user pressed edit button
    public void editData(ActionEvent event) {
        // changes visibility of user profile into edit mode
        weightIndexValue.setVisible(false);
        heightIndexValue.setVisible(false);
        bodyIndexValue.setVisible(false);
        fullNameValue.setVisible(false);
        saveButton.setVisible(true);
        heightBox.setVisible(true);
        weightBox.setVisible(true);
        nameBox.setVisible(true);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeData() {
        // get new height, weight and name values from text fields
        String newHeight = heightBox.getText();
        String newWeight = weightBox.getText();
        String newName = nameBox.getText();

        // checks if the new height or weight fields are empty
        if (!newHeight.isEmpty() && !newWeight.isEmpty()) {
            weightIndexValue.setText(newWeight);
            heightIndexValue.setText(newHeight);
            fullNameValue.setText(newName);
            try {
                // parse new string values into double values to calculate new BMI
                double height = Double.parseDouble(newHeight);
                double weight = Double.parseDouble(newWeight);

                // calculates BMI based on the new values
                double bmiValue = weight / Math.pow(height / 100, 2);

                // rounds bmi value up to 2 digits after coma
                bodyIndexValue.setText(String.format("%.2f", bmiValue));

                // updates currentUser object with new data from text fields
                FitVentureStart.currentUser.setHeight(newHeight);
                FitVentureStart.currentUser.setWeight(newWeight);
                FitVentureStart.currentUser.setName(newName);

                // saves the updated user data back to the JSON file
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                errorLabel.setVisible(false);
            } catch (Exception e) {
                // shows error label if user input wrong height and weight
                errorLabel.setVisible(true);
                errorLabel.setText("Failed. You entered wrong data type");
                heightIndexValue.setText(null);
                weightIndexValue.setText(null);
                bodyIndexValue.setText(null);
                fullNameValue.setText(null);
            }
        } else {
            // shows error label if user didn't input weight and height
            errorLabel.setText("Failed. Input your personal data");
            errorLabel.setVisible(true);
            heightIndexValue.setText(null);
            weightIndexValue.setText(null);
            bodyIndexValue.setText(null);
            fullNameValue.setText(null);
        }
    }

    // method to save new entered data and turn back to the user profile
    public void saveData(ActionEvent event) {
        weightIndexValue.setVisible(true);
        heightIndexValue.setVisible(true);
        bodyIndexValue.setVisible(true);
        fullNameValue.setVisible(true);
        saveButton.setVisible(false);
        heightBox.setVisible(false);
        weightBox.setVisible(false);
        nameBox.setVisible(false);

        changeData();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // button to return back to the MainDashboard
    public void backToMainDashboard(ActionEvent event) throws IOException {
        // loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
        root = loader.load();

        MainDashboardController mainDashboardController = loader.getController();
        mainDashboardController.dayChart();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}










