package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PainelCadastrosController {

    @FXML
    private Button botaoLogout;

    @FXML
    private Button button_admin;

    @FXML
    private Button button_medico;

    @FXML
    private Button button_recepcionista;

    @FXML
    private Button button_paciente;

    public void initialize() {
        button_admin.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroAdmin.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_medico.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroMedico.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_recepcionista.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroRecepcionista.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_paciente.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroPaciente.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

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

    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelPrincipal.fxml", "Painel Principal");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}