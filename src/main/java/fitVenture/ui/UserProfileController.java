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

    // method to show information of a logged user in the user profile
    public void showData() {
        loggedUsername.setText(FitVentureStart.currentUser.getUsername());
        weightIndexValue.setText(String.valueOf(FitVentureStart.currentUser.getWeight()));
        heightIndexValue.setText(String.valueOf(FitVentureStart.currentUser.getHeight()));
        //calculates BMI
        double bmiValue = FitVentureStart.currentUser.getWeight() / Math.pow(FitVentureStart.currentUser.getHeight() / 100, 2);
        //rounds bmi value up to 2 digits after coma
        bodyIndexValue.setText(String.format("%.2f", bmiValue));
    }

    public void changeData() throws IOException{
        String newHeight;
        String newWeight;

        try {
         // Get new height and weight from text fields
            newHeight = heightBox.getText();
            newWeight = weightBox.getText();

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

    } catch (IOException e) {
            e.printStackTrace();
        }
}

    public void editData(ActionEvent event) throws IOException {
        //loads EditUserProfileScene when user pressed edit button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditUserProfileScene.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void saveData(ActionEvent event) throws IOException {
        //loads MainDashboardScene one user pressed login button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
        root = loader.load();
        
        //calls method showData in the userProfile, once user is logged in
        UserProfileController UserProfileController = loader.getController();
        UserProfileController.changeData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}










