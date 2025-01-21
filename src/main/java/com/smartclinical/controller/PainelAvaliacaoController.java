package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PainelAvaliacaoController {
    @FXML
    private Button button_avaliarConsulta;

    @FXML
    private Button botaoLogout;

    public void initialize() {
        button_avaliarConsulta.setOnAction(e -> {
            Main m = new Main();
            try {
                m.abrirPainel("cadastroAvaliacao.fxml","Avaliação");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }




}
