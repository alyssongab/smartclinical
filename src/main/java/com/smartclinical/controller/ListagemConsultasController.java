package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.PacienteDAO;
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


    /* ***** Exibir detalhes do paciente ****/


    //Seleciona o id da linha clicada e abre um dialog
    @FXML
    private void rowClicked(){
        Consulta clickedConsulta = tableViewConsultas.getSelectionModel().getSelectedItem();
        showDialog(clickedConsulta.getPacienteId());
    }

    private void showDialog(int idPaciente) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Paciente detalhes");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNode(idPaciente, dialog));  // Passa o dialog para a função createNode
        dialog.showAndWait();
    }

    private Node createNode(int idPaciente, Dialog<Void> dialog) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        Paciente paciente = pacienteDAO.getPaciente(idPaciente);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Detalhes Paciente:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 32px;");
        gridPane.add(label, 0, 0, 2, 1);

        Label labelNome = new Label("Nome: " + paciente.getNome());
        labelNome.setStyle("-fx-font-size: 20px;");
        gridPane.add(labelNome, 0, 0, 2, 4);

        Label labelCPF = new Label("CPF: " + paciente.getCpf());
        labelCPF.setStyle("-fx-font-size: 20px;");
        gridPane.add(labelCPF, 0, 0, 2, 6);

        Label labelDataNasc = new Label("Data de nascimento: " + paciente.getDataNascimento());
        labelDataNasc.setStyle("-fx-font-size: 20px;");
        gridPane.add(labelDataNasc, 0, 0, 2, 8);

        return gridPane;
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
            main.abrirPainel("painelPrincipal.fxml", "Painel Principal");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}

