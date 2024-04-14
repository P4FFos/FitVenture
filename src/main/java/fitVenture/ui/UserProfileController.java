package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.exceptions.EditException;
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
    public Label errorLabel;


    // method to show information of a logged user in the user profile
    public void showData() {
        loggedUsername.setText(FitVentureStart.currentUser.getUsername());
        weightIndexValue.setText(String.valueOf(FitVentureStart.currentUser.getWeight()));
        heightIndexValue.setText(String.valueOf(FitVentureStart.currentUser.getHeight()));
        if (!heightIndexValue.getText().isEmpty() && !weightIndexValue.getText().isEmpty()) {
            //calculates BMI
            double bmiValue = FitVentureStart.currentUser.getWeight() / Math.pow(FitVentureStart.currentUser.getHeight() / 100, 2);
            //rounds bmi value up to 2 digits after coma
            bodyIndexValue.setText(String.format("%.2f", bmiValue));
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("Empty fields");
        }
    }

    public void editData(ActionEvent event) throws IOException {
        //changes visibility of buttons when user pressed edit button
        weightIndexValue.setVisible(false);
        heightIndexValue.setVisible(false);
        bodyIndexValue.setVisible(false);
        saveButton.setVisible(true);
        heightBox.setVisible(true);
        weightBox.setVisible(true);


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeData() throws IOException {
        String newHeight;
        String newWeight;


            // Get new height and weight from text fields
            newHeight = heightBox.getText();
            newWeight = weightBox.getText();
            if (!newHeight.isEmpty() && !newWeight.isEmpty()) {
                weightIndexValue.setText(newWeight);
                heightIndexValue.setText(newHeight);
                try {
                    double height = Double.parseDouble(newHeight);
                    double weight = Double.parseDouble(newWeight);

                    // Directly modify the height and weight fields
                    FitVentureStart.currentUser.height = height;
                    FitVentureStart.currentUser.weight = weight;
                    //calculates BMI
                    double bmiValue = FitVentureStart.currentUser.getWeight() / Math.pow(FitVentureStart.currentUser.getHeight() / 100, 2);
                    //rounds bmi value up to 2 digits after coma
                    bodyIndexValue.setText(String.format("%.2f", bmiValue));

                    // Save the updated user data back to the JSON file

                    FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                    errorLabel.setVisible(false);
                } catch (Exception e) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("wrong data entered");
                    heightIndexValue.setText(null);
                    weightIndexValue.setText(null);
                    bodyIndexValue.setText(null);
                    e.printStackTrace();
                }


            } else {
                errorLabel.setText("empty fields");
                errorLabel.setVisible(true);
                heightIndexValue.setText(null);
                weightIndexValue.setText(null);
                bodyIndexValue.setText(null);
            }
    }


    public void saveData(ActionEvent event) throws IOException {

        //method to retrieve new entered data from the textfields and save in into labels, then calculate new BMI
        changeData();
        //methods to change visibility of buttons when save data button is pressed
        weightIndexValue.setVisible(true);
        heightIndexValue.setVisible(true);
        bodyIndexValue.setVisible(true);
        saveButton.setVisible(false);
        heightBox.setVisible(false);
        weightBox.setVisible(false);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


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










