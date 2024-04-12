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


public class UserProfileController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    public Label nameErrorLabel;
    @FXML
    public Label optionalErrorLabel;
    @FXML
    public Label nameOptionalErrorLabel;
    @FXML
    public TextField height;
    @FXML
    public TextField weight;
    @FXML
    public TextField age;
    @FXML
    public Label BMI;

    private final String jsonFilePath = "database.json";

        public void showdata() {
            try {
                FitVenture fitVenture = FileHandler.jsonDeserializer(jsonFilePath);
                if (fitVenture != null) {
                    double heightValue = fitVenture.getHeight();
                    double weightValue = fitVenture.getWeight();
                    int ageValue = fitVenture.getAge();

                    // Set the values to the TextFields
                    height.setText(String.valueOf(heightValue));
                    weight.setText(String.valueOf(weightValue));
                    age.setText(String.valueOf(ageValue));

                    double bmiValue = weightValue / Math.pow(heightValue / 100, 2); // height is divided by 100 to convert cm to m

                    BMI.setText(String.format("%.2f", bmiValue));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





