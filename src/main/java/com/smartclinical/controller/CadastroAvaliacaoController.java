package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AvaliacaoDAO;
import com.smartclinical.model.Avaliacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CadastroAvaliacaoController {

    @FXML
    private Button botaoLogout;

    @FXML
    private ComboBox<String> comboAtendimento;

    @FXML
    private ComboBox<String> comboConsulta;

    @FXML
    private TextArea descricaoFeedBack;

    AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    public void initialize(){
        botaoLogout.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainel("painelAvaliacao.fxml","Avaliação");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ObservableList<String> opcoes = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        comboAtendimento.setItems(opcoes);
        comboConsulta.setItems(opcoes);
    }

     public void cadastrarFeedback(){
        String notaAtendimento = (String) comboAtendimento.getSelectionModel().getSelectedItem();
        String notaConsulta = (String) comboConsulta.getSelectionModel().getSelectedItem();
        String descricao = descricaoFeedBack.getText();

         if (notaAtendimento == null || notaConsulta == null || descricao.isEmpty()) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erro");
             alert.setHeaderText("Dados Incompletos");
             alert.setContentText("Verifique novamente as informações preenchidas.");
             alert.showAndWait();
         } else {
             Avaliacao avaliacao = new Avaliacao(notaAtendimento, notaConsulta, descricao);


             boolean sucesso = avaliacaoDAO.cadastrarAvaliacao(avaliacao);

             Alert alert;
             if(sucesso) {
                 alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Sucesso");
                 alert.setHeaderText("Prontuario cadastrado com sucesso");

             } else {
                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erro");
                 alert.setHeaderText("");
                 alert.setContentText("Erro ao cadastrar!");
             }
             alert.showAndWait();
         }
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


}
