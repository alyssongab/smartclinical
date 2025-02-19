package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AdminController {

    @FXML
    private Button botaoLogout;

    @FXML
    private Button button_cadastrarUsuario;

    @FXML
    private Button button_listarUsuario;

    @FXML
    private Button button_relatorios;

    public void initialize() {
        button_cadastrarUsuario.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroUsuario.fxml", "Cadastro de Pacientes");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_listarUsuario.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("listaUsuarios.fxml", "Gerenciamento de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_relatorios.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("relatorios.fxml", "Relatorios");
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

    // botao que volta para o painel principal
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
