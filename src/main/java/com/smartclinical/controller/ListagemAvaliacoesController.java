package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AvaliacaoDAO;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.model.Avaliacao;
import com.smartclinical.model.Consulta;
import com.smartclinical.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ListagemAvaliacoesController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Avaliacao> tableViewAvaliacoes;

    @FXML
    private TableColumn<Avaliacao, Integer> colunaIdAvaliacao;

    @FXML
    private TableColumn<Avaliacao, String> colunaNotaAtendimento;

    @FXML
    private TableColumn<Avaliacao, String> colunaNotaConsulta;

    @FXML
    private TableColumn<Avaliacao, String> colunaDescricao;

    private ObservableList<Avaliacao> listaAvaliacao;

    public void initialize() {
        // Inicializando a lista de consultas
        listaAvaliacao = FXCollections.observableArrayList();

        // Configurando as colunas da TableView
        colunaIdAvaliacao.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNotaAtendimento.setCellValueFactory(new PropertyValueFactory<>("notaAtendimento"));
        colunaNotaConsulta.setCellValueFactory(new PropertyValueFactory<>("notaConsulta"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Carregar dados da consulta e popular a TableView
        carregarDadosAvaliacoes();
    }

    private void carregarDadosAvaliacoes() {
        // Usando o DAO para listar as consultas
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        listaAvaliacao.setAll(avaliacaoDAO.listarAvaliacoes());

        // Atribuindo a lista à TableView
        tableViewAvaliacoes.setItems(listaAvaliacao);
    }


    /* ***** Exibir detalhes do paciente ****/


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

    // Volta para tela anterior
    public void voltarParaPacientes(){
        try{
            Main main = new Main();
            main.abrirPainel("painelPrincipal.fxml", "Painel Principal");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}

