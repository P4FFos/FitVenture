package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.user.User;
import fitVenture.backend.utils.FileHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FitVentureStart extends Application {

    // static FitVenture attribute, used for registration and login
    public static FitVenture fitVenture;
    public static User currentUser;
    public static String jsonPath = "src/main/java/fitVenture/backend/database/database.json";

    @Override
    public void start(Stage stage) throws IOException {
        // initialise json path to save user data
        fitVenture = FileHandler.jsonDeserializer(jsonPath);

        // loads LoginRegistrationScene
        FXMLLoader fxmlLoader = new FXMLLoader(FitVentureStart.class.getResource("LoginRegistrationScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

