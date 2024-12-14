module com.smartclinical.smartclinical {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.smartclinical.smartclinical to javafx.fxml;
    exports com.smartclinical.smartclinical;
}