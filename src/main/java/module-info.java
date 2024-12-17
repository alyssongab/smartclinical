module com.smartclinical.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.smartclinical.app to javafx.fxml;
    exports com.smartclinical.app;
    exports com.smartclinical.controller;
    opens com.smartclinical.controller to javafx.fxml;
}