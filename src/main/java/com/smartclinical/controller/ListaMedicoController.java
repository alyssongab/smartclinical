package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.dao.MedicoDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Medico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListaMedicoController {
    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Medico> medicoTable;

    @FXML
    private TableColumn<Medico, Integer> medicoId;

    @FXML
    private TableColumn<Medico, String> medicoNome;

    @FXML
    private TableColumn<Medico, String> medicoEmail;

    @FXML
    private TableColumn<Medico, String> medicoTelefone;

    @FXML
    private TableColumn<Medico, String> medicoCRM;

    @FXML
    private TableColumn<Medico, String> medicoEspecialidade;


    public void initialize(){
        medicoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        medicoEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        medicoTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        medicoCRM.setCellValueFactory(new PropertyValueFactory<>("CRM"));
        medicoEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
        setupTable();
    }

    private void setupTable() {
        MedicoDAO medicoDAO = new MedicoDAO();
        List<Medico> medicos = medicoDAO.listarAdmin();
        medicoTable.getItems().clear();
        medicoTable.getItems().addAll(medicos);
    }

    @FXML
    private void rowClicked(){
        Medico clickedMedico = medicoTable.getSelectionModel().getSelectedItem();
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
        MedicoDAO medicoDAO = new MedicoDAO();

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
