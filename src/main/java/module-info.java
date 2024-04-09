module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens fitVenture to javafx.fxml;
    exports fitVenture;
}