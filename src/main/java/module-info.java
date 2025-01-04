module com.example.smartclinical {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.smartclinical to javafx.fxml;
    exports com.example.smartclinical.app;
    opens com.example.smartclinical.app to javafx.fxml;
    exports com.example.smartclinical.controller;
    opens com.example.smartclinical.controller to javafx.fxml;
}
