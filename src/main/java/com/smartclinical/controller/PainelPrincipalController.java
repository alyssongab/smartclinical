package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.util.Sessao;
import com.smartclinical.util.TipoUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PainelPrincipalController {

    @FXML
    private Button botaoLogout;

    @FXML
    private Button botaoPainelPacientes;

    @FXML
    private Button botaoPainelAdmin;

    // inicia o controller
    public void initialize() {
        TipoUser tipoUser = Sessao.getTipoUser();
        System.out.println("Voce logou como: " + tipoUser);

        botaoLogout.setOnAction(event -> fazerLogout());

        // lida com o botão "Pacientes"
        botaoPainelPacientes.setOnAction(event -> {
            try {
                Main m = new Main();
                m.abrirPainel("painelPacientes.fxml", "Painel de Pacientes");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // lida com o botão "Admin"
        botaoPainelAdmin.setOnAction(event -> {
            try {
                Main m = new Main();
                m.abrirPainel("admin.fxml", "Painel de Admin");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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

//    public void abrirPainelPacientes() throws IOException {
//        Main m = new Main();
//        m.abrirPainel("painelPacientes.fxml");
//    }
}