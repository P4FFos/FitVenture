module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires org.eclipse.paho.client.mqttv3;

    exports fitVenture.backend;
    opens fitVenture.backend to javafx.fxml;
    exports fitVenture.ui;
    opens fitVenture.ui to javafx.fxml;
    exports fitVenture.backend.user;
    opens fitVenture.backend.user to javafx.fxml, com.fasterxml.jackson.databind;
    exports fitVenture.backend.exceptions;
    opens fitVenture.backend.exceptions to javafx.fxml;
    exports fitVenture.backend.stats;
    opens fitVenture.backend.stats to com.fasterxml.jackson.databind;
    exports fitVenture.backend.goal;
    opens fitVenture.backend.goal to com.fasterxml.jackson.databind;

}