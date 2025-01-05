package com.example.smartclinical.controller;

import com.example.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PainelPacientesController {
    @FXML
    private Button botaoLogout;

    @FXML
    private Button botaoPainel;

    public void initialize(){
        botaoPainel.setOnAction(event -> {
            try {
                voltarPainel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void voltarPainel() throws IOException {
        Main m = new Main();
        m.abrirPainel("painelPrincipal.fxml");
    }


    /**
     * ****** LOGOUT APENAS ***************
     */
    public void fazerLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Sair do Sistema");
        alert.setContentText("Deseja sair do sistema?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            confirmarLogout();
        }
    }

    public void confirmarLogout() {
        try {
            // fecha a painel principal
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
    /*
     * ******** FIM DO LOGOUT ********
     */
}
