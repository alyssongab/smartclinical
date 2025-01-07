package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RelatoriosController {
    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelAdmin.fxml", "Painel Admin");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}
