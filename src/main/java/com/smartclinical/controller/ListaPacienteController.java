package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.dao.MedicoDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.RecepcionistaDAO;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.util.TipoUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

    private Node createNode(int idPaciente, Dialog<Void> dialog) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        gridPane.add(label, 0, 0, 2, 1);

        Button editButton = new Button("Editar");
        editButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #53c89b; -fx-text-fill: white;");
        editButton.setOnAction(event -> {
            showDialogEdit(idPaciente);
        });

        Button deleteButton = new Button("Excluir");
        deleteButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> {
            PacienteDAO pacienteDAO = new PacienteDAO();
            try {
                pacienteDAO.removerPaciente(idPaciente);
                dialog.close();
                mostrarAlerta("Excluir admin", "Excluir concluído!");
            } catch (SQLException e) {
                System.err.println("Erro ao excluir o usuário: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        gridPane.add(editButton, 0, 1);
        gridPane.add(deleteButton, 1, 1);

        return gridPane;
    }

    /*----------Dialog pane Editar usuário--------------*/

    private void showDialogEdit(int idMedico) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Editar Administrador");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createEditNode(idMedico));
        dialog.showAndWait();
    }

    private Node createEditNode(int idPaciente) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(30));

        PacienteDAO pacienteDAO = new PacienteDAO();
        Paciente paciente = pacienteDAO.getPaciente(idPaciente);

        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label labelNome = new Label("Nome:");
        TextField pacienteNome = new TextField(paciente.getNome());
        pacienteNome.setStyle("-fx-min-width: 200px");

        Label labelCPF = new Label("CPF:");
        TextField pacienteCPF = new TextField(paciente.getCpf());
        pacienteCPF.setStyle("-fx-min-width: 200px");

        Label labelDataNascimento = new Label("Data de nascimento");
        TextField pacienteDataNascimento = new TextField(paciente.getDataNascimento());
        pacienteDataNascimento.setStyle("-fx-min-width: 200px");


        Button saveButton = new Button("Salvar");
        saveButton.setStyle("-fx-max-height: 25px; -fx-min-width: 110px; -fx-background-color: #53c89b; -fx-text-fill: white;");
        saveButton.setOnAction(event -> {
            String nome = pacienteNome.getText();
            String cpf = pacienteCPF.getText();
            String dataNascimento = pacienteDataNascimento.getText();

            Paciente pacienteEdit = new Paciente(idPaciente,nome,cpf,dataNascimento);
            pacienteDAO.editarPaciente(pacienteEdit);
            mostrarAlerta("Edição", "Paciente editado com sucesso!");
            ((Stage) gridPane.getScene().getWindow()).close();
        });

        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(labelNome, 0, 2);
        gridPane.add(pacienteNome, 0, 3);
        gridPane.add(labelCPF, 0, 4);
        gridPane.add(pacienteCPF, 0, 5);
        gridPane.add(labelDataNascimento, 0, 6);
        gridPane.add(pacienteDataNascimento, 0, 7);
        gridPane.add(saveButton, 0, 9);
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
            main.abrirPainel("painelListaUsuarios.fxml", "Painel Listagem");
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
