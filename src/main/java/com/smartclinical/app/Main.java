package com.smartclinical.app;

import com.smartclinical.controller.PainelPacientesController;

import com.smartclinical.util.TipoUser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
    private static Stage stg;

    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void abrirRedefinicaoSenha(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
        stg.setTitle("Nova senha");
        stg.setWidth(800);
        stg.setHeight(600);

        stg.setResizable(false);

        stg.setOnCloseRequest(event -> {
            // simula um logout
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText("Sair do Sistema");
            alert.setContentText("Deseja sair do sistema?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);  // Encerra a aplicação após o logout
            }
            else{
                event.consume();
            }
        });

    }

    public void abrirPainel(String fxml, String title) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
        stg.setTitle(title);
        stg.setWidth(1280);
        stg.setHeight(800);

        // Centralizando o Stage
        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stg.setX((screenBounds.getWidth() - stg.getWidth()) / 2);
        stg.setY((screenBounds.getHeight() - stg.getHeight()) / 2);

        stg.setOnCloseRequest(event -> {
            // simula um logout
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText("Sair do Sistema");
            alert.setContentText("Deseja sair do sistema?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);  // Encerra a aplicação após o logout
            }
            else{
                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}


