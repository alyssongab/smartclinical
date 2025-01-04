package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ListaUsuariosController {

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
                m.abrirPainel("cadastroUsuario.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_medico.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroUsuario.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_recepcionista.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroUsuario.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_paciente.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroUsuario.fxml", "Cadastro de Usuarios");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
