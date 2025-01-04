package com.smartclinical.controller;

import com.smartclinical.app.Main;
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

<<<<<<< HEAD
    private Main main;
=======
    @FXML
    private Button botaoPainelAdmin;
>>>>>>> matheus_admin

    // inicia o controller
    public void initialize() {
        botaoLogout.setOnAction(event -> fazerLogout());

<<<<<<< HEAD
        botaoPainelPacientes.setOnAction(event -> {
            try {
                abrirPainelPacientes();
=======
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
>>>>>>> matheus_admin
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
<<<<<<< HEAD
    * ******** FIM DO LOGOUT ********
    */

    public void abrirPainelPacientes() throws IOException {
        Main m = new Main();
        m.abrirPainel("painelPacientes.fxml", "Painel de Pacientes");
    }
}
=======
     * ******** FIM DO LOGOUT ********
     */

//    public void abrirPainelPacientes() throws IOException {
//        Main m = new Main();
//        m.abrirPainel("painelPacientes.fxml");
//    }
}
>>>>>>> matheus_admin
