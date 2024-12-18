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
//            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
//            Parent root = loader.load();
//
//            // Obtém a janela atual (Stage)
//            Stage stage = (Stage) botaoLogout.getScene().getWindow();
//            stage.setMaximized(false);
//            stage.setResizable(false);
//
//            // Define a nova cena com o FXML de login
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setTitle("Tela de Login");
//            stage.show();
            // Obtém o Stage atual (janela de login ou painel)
            Stage stage = (Stage) botaoLogout.getScene().getWindow();

            // Fecha a janela atual antes de chamar o start novamente
            stage.close();

            // Chama o método start da classe Main para reabrir a janela de login
            Main mainApp = new Main();
            Stage novoLogin = new Stage();
            mainApp.start(novoLogin);  // Passa um novo Stage para o start
//
            novoLogin.setWidth(800);
            novoLogin.setHeight(600);
            novoLogin.setMaximized(false);
            novoLogin.show();

        } catch (Exception e) {
            System.out.println("Erro ao fazer logout: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
