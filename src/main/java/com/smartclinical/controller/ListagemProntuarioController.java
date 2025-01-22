package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.ProntuarioDAO;
import com.smartclinical.model.Consulta;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Prontuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ListagemProntuarioController {
    @FXML
    private Button botaoLogout;

    @FXML
    private ComboBox<Paciente> comboPaciente;

    @FXML
    private TableView<Prontuario> tableViewProntuarios;

    @FXML
    private TableColumn<Prontuario, Integer> colunaIdProntuario;

    @FXML
    private TableColumn<Prontuario, Integer> colunaIdConsulta;

    @FXML
    private TableColumn<Prontuario, String> descricao;

    private ObservableList<Prontuario> listaProntuarios;
    private PacienteDAO pacienteDAO;
    private ProntuarioDAO prontuarioDAO;

    @FXML
    public void initialize() {
        pacienteDAO = new PacienteDAO();
        prontuarioDAO = new ProntuarioDAO();  // Instancia o DAO de Prontuário
        listaProntuarios = FXCollections.observableArrayList();

        // Configuração correta das colunas da TableView
        colunaIdProntuario.setCellValueFactory(new PropertyValueFactory<>("prontuarioId"));
        colunaIdConsulta.setCellValueFactory(new PropertyValueFactory<>("consultaId"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        carregarPacientes();
    }

    private void carregarPacientes() {
        List<Paciente> pacientes = pacienteDAO.listarPacientes();
        ObservableList<Paciente> pacienteObservableList = FXCollections.observableArrayList(pacientes);
        comboPaciente.setItems(pacienteObservableList);

        comboPaciente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                carregarDadosProntuarios(newValue.getId());
            }
        });
    }

    private void carregarDadosProntuarios(int pacienteId) {
        List<Prontuario> prontuarios = prontuarioDAO.listarProntuarios(pacienteId);  // Implementar no DAO
        listaProntuarios.setAll(prontuarios);
        tableViewProntuarios.setItems(listaProntuarios);
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

    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelProntuario.fxml", "Prontuario");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}
