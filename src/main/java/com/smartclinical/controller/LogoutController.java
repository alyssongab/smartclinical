package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class LogoutController {

    @FXML
    private Button botaoLogout;

    private Main main;

    // inicia o controller
    public void initialize() {
        botaoLogout.setOnAction(event -> fazerLogout());
    }

    private void fazerLogout() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Sair do Sistema");
            alert.setContentText("Deseja sair do sistema?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                confirmarLogout();
            }
    }   

    private void confirmarLogout() {
        try {
            Stage stage = (Stage) botaoLogout.getScene().getWindow();
            stage.close();
            // vai para o start novamente (tela de login)
            Main m = new Main();
            m.start(new Stage());

        } catch (Exception e) {
            System.out.println("Erro ao fazer logout: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
