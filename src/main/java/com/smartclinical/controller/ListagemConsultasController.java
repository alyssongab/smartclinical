package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.model.Consulta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

public class ListagemConsultasController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Consulta> tableViewConsultas;

    @FXML
    private TableColumn<Consulta, Integer> colunaIdConsulta;

    @FXML
    private TableColumn<Consulta, String> colunaDataHora;

    @FXML
    private TableColumn<Consulta, String> colunaNomePaciente;

    @FXML
    private TableColumn<Consulta, String> colunaNomeMedico;

    private ObservableList<Consulta> listaConsultas;

    public void initialize() {
        // Inicializando a lista de consultas
        listaConsultas = FXCollections.observableArrayList();

        // Configurando as colunas da TableView
        colunaIdConsulta.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        colunaDataHora.setCellValueFactory(new PropertyValueFactory<>("data_hora"));
        colunaNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colunaNomeMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));

        // Carregar dados da consulta e popular a TableView
        carregarDadosConsultas();
    }

    private void carregarDadosConsultas() {
        // Usando o DAO para listar as consultas
        ConsultaDAO consultaDAO = new ConsultaDAO();
        listaConsultas.setAll(consultaDAO.listarConsultas());

        // Atribuindo a lista à TableView
        tableViewConsultas.setItems(listaConsultas);
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

