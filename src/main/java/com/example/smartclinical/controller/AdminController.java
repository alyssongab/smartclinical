package com.example.smartclinical.controller;

import com.example.smartclinical.app.Main;
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
                m.abrirPainel("cadastroUsuario.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_listarUsuario.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("listaUsuarios.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button_relatorios.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("relatorios.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
