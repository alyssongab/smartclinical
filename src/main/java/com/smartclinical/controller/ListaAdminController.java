package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Usuario;
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

public class ListaAdminController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Admin> adminTable;

    @FXML
    private TableColumn<Admin, Integer> adminId;

    @FXML
    private TableColumn<Admin, String> adminNome;

    @FXML
    private TableColumn<Admin, String> adminEmail;

    @FXML
    private TableColumn<Admin, String> adminTelefone;

    @FXML
    private TableColumn<Admin, String> adminCargo;

    public void initialize(){
        adminId.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        adminTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        adminCargo.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        setupTable();
    }

    private void setupTable() {
        AdminDAO adminDao = new AdminDAO();
        List<Admin> admins = adminDao.listarAdmin();
        adminTable.getItems().clear();
        adminTable.getItems().addAll(admins);
    }

    @FXML
    private void rowClicked(){
        Admin clickedAdmin = adminTable.getSelectionModel().getSelectedItem();
        showDialog(clickedAdmin.getId());
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
        AdminDAO adminDao = new AdminDAO();

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
                adminDao.removerUsuario(idAdmin);
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
