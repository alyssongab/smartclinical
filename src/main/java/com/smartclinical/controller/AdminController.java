package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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
                m.abrirPainel("listaUsuarios.fxml", "Lista de Usuarios");
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

}