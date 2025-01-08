package com.smartclinical.controller;

import com.smartclinical.app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PainelAgendamentoController {

    @FXML
    private Button botaoLogout;

    @FXML
    private Button botaoAgendarConsulta;

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

    // vai pra tela de agendar consulta
    public void comecarAgendamento(){
        try{
            Main m = new Main();
            m.abrirPainel("agendamentoConsulta.fxml", "Agendamento");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listarParaCancelar(){
        try{
            Main m = new Main();
            m.abrirPainel("cancelamentoConsulta.fxml", "Cancelar Consulta");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // volta para o painel
    public void voltarParaPainelPrincipal() {
        try{
            Main main = new Main();
            main.abrirPainel("painelPrincipal.fxml", "Painel Principal");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

}
