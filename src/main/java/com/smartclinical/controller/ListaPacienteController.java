package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.RecepcionistaDAO;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Recepcionista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListaPacienteController {
    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Paciente> pacienteTable;

    @FXML
    private TableColumn<Paciente, Integer> pacienteId;

    @FXML
    private TableColumn<Paciente, String> pacienteNome;

    @FXML
    private TableColumn<Paciente, String> pacienteCpf;

    @FXML
    private TableColumn<Paciente, String> pacienteDataNascimento;



    public void initialize(){
        pacienteId.setCellValueFactory(new PropertyValueFactory<>("id"));
        pacienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        pacienteCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        pacienteDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        setupTable();
    }

    private void setupTable() {
        PacienteDAO pacienteDAO = new PacienteDAO();
        List<Paciente> pacientes = pacienteDAO.listarAdmin();
        pacienteTable.getItems().clear();
        pacienteTable.getItems().addAll(pacientes);
    }

    @FXML
    private void rowClicked(){
        Paciente clickedMedico = pacienteTable.getSelectionModel().getSelectedItem();
        showDialog(clickedMedico.getId());
    }

    private void showDialog(int id) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Admin options");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNode(id, dialog));  // Passa o dialog para a função createNode
        dialog.showAndWait();
    }

    private Node createNode(int idAdmin, Dialog<Void> dialog) {
        GridPane gridPane = new GridPane();
        PacienteDAO medicoDAO = new PacienteDAO();

        // Botão "Editar"
        Button editButton = new Button("Editar");
        editButton.setOnAction(event -> {
            Main m = new Main();
            try {
                m.abrirPainelEdit("editaAdmin.fxml", "Editar admin", idAdmin);
                dialog.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Botão "Excluir"
        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(event -> {

            try {
                // Remover o usuário
                medicoDAO.removerMedico(idAdmin);
                dialog.close();
                mostrarAlerta("Excluir admin", "Excluir concluído!");
            } catch (SQLException e) {
                // Tratar exceções, se necessário
                System.err.println("Erro ao excluir o usuário: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        // Adicionando os botões nas posições corretas do GridPane
        gridPane.add(editButton, 0, 0); // Posição (0, 0) para Editar
        gridPane.add(deleteButton, 0, 1); // Posição (0, 1) para Excluir

        return gridPane;
    }

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

    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelPrincipal.fxml", "Painel Principal");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
