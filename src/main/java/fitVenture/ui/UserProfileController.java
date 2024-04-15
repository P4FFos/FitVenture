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
    public Label fullNamevalue;
    @FXML
    public Label bodyIndexValue;
    @FXML
    public Label loggedUsername;
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


    private String userUsername =  FitVentureStart.currentUser.getUsername();
    private double userWeight =  FitVentureStart.currentUser.getWeight();
    private double userHeight = FitVentureStart.currentUser.getHeight();
    private String userName =  FitVentureStart.currentUser.getName();

    // method to show information of a logged user in the user profile
    public void showData() {
        // set user information from the database into labels

        loggedUsername.setText(userUsername);
        weightIndexValue.setText(String.valueOf(userWeight));
        heightIndexValue.setText(String.valueOf(userHeight));

        // checks if the user height and weight fields are not empty
        if (!heightIndexValue.getText().isEmpty() && !weightIndexValue.getText().isEmpty()) {
            // calculates BMI
            fullNamevalue.setText(userName);
            double bmiValue = userWeight / Math.pow(userHeight, 2);
            // rounds bmi value up to 2 digits after coma
            bodyIndexValue.setText(String.format("%.2f", bmiValue));
        } else {
            // shows an error if the user weight and height fields are empty
            errorLabel.setVisible(true);
            errorLabel.setText("Failed. Input your personal data");
        }
    }

    public void editData(ActionEvent event) throws IOException {
        //changes visibility of buttons when user pressed edit button
        weightIndexValue.setVisible(false);
        heightIndexValue.setVisible(false);
        bodyIndexValue.setVisible(false);
        fullNamevalue.setVisible(false);
        saveButton.setVisible(true);
        heightBox.setVisible(true);
        weightBox.setVisible(true);
        nameBox.setVisible(true);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeData() throws IOException {
        String newHeight;
        String newWeight;
        String newName;

        // Get new height and weight from text fields
        newHeight = heightBox.getText();
        newWeight = weightBox.getText();
        newName = nameBox.getText();

        // checks if the new height or weight fields are empty
        if (!newHeight.isEmpty() && !newWeight.isEmpty()) {
            weightIndexValue.setText(newWeight);
            heightIndexValue.setText(newHeight);
            fullNamevalue.setText(newName);
            try {
                // parse new string values into double values to calculate new BMI
                double height = Double.parseDouble(newHeight);
                double weight = Double.parseDouble(newWeight);

                // calculates BMI based on the new values
                double bmiValue = weight / Math.pow(height / 100, 2);
                // rounds bmi value up to 2 digits after coma
                bodyIndexValue.setText(String.format("%.2f", bmiValue));

                // Update currentUser object with new data
                FitVentureStart.currentUser.setHeight(height);
                FitVentureStart.currentUser.setWeight(weight);
                FitVentureStart.currentUser.setName(newName);

                // Saves the updated user data back to the JSON file
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                errorLabel.setVisible(false);
            } catch (Exception e) {

                // shows error label if user input wrong height and weight
                errorLabel.setVisible(true);
                errorLabel.setText("Failed. You entered wrong data type");
                heightIndexValue.setText(null);
                weightIndexValue.setText(null);
                bodyIndexValue.setText(null);
                fullNamevalue.setText(null);
                e.printStackTrace();
            }
        } else {
            // shows error label if user didn't input weight or height
            errorLabel.setText("Failed. Input your weight and height");
            errorLabel.setVisible(true);
            heightIndexValue.setText(null);
            weightIndexValue.setText(null);
            bodyIndexValue.setText(null);
            fullNamevalue.setText(null);
        }
    }

    // method to save new entered data and turn back to the user profile
    public void saveData(ActionEvent event) throws IOException {
        weightIndexValue.setVisible(true);
        heightIndexValue.setVisible(true);
        bodyIndexValue.setVisible(true);
        fullNamevalue.setVisible(true);
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

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}










