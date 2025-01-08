module com.smartclinical.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.smartclinical.app to javafx.fxml;
    exports com.smartclinical.app;

    exports com.smartclinical.controller;
    opens com.smartclinical.controller to javafx.fxml;

    exports com.smartclinical.model;
    opens com.smartclinical.model to javafx.base;

    exports com.smartclinical.util;
    opens com.smartclinical.util to javafx.base;
}