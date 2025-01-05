package com.example.smartclinical.controller;

import com.example.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class RelatoriosController {
    @FXML
    private Button botaoPainel;

    public void initialize() {
        botaoPainel.setOnAction(event -> {
            try {
                voltarPainel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void voltarPainel() throws IOException {
        Main m = new Main();
        m.abrirPainel("painelPrincipal.fxml");
    }
}
