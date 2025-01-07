package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ListagemPacientesController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Paciente> tableViewPacientes;

    @FXML
    private TableColumn<Paciente, Integer> colunaIdPaciente;

    @FXML
    private TableColumn<Paciente, String> colunaNome;

    @FXML
    private TableColumn<Paciente, Long> colunaCpf;

    @FXML
    private TableColumn<Paciente, String> colunaDataNascimento;

    private ObservableList<Paciente> listaPacientes;

    public void initialize() {
        // Inicializando a lista de consultas
        listaPacientes = FXCollections.observableArrayList();

        // Configurando as colunas da TableView
        colunaIdPaciente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        // Carregar dados da consulta e popular a TableView
        carregarDadosPaciente();
    }

    private void carregarDadosPaciente() {
        // Usando o DAO para listar os pacientes
        PacienteDAO pacienteDAO = new PacienteDAO();
        listaPacientes.setAll(pacienteDAO.listarPacientes());

        // Atribuindo a lista à TableView
        tableViewPacientes.setItems(listaPacientes);
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

    // Volta para tela anterior
    public void voltarParaPacientes(){
        try{
            Main main = new Main();
            main.abrirPainel("painelPacientes.fxml", "Painel de pacientes");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}

