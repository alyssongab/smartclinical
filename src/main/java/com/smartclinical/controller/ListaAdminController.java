package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.model.Admin;
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
        dialog.setTitle("Admin Options");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNode(id));
        dialog.showAndWait();
    }

    private Node createNode(int idAdmin) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        gridPane.add(label, 0, 0, 2, 1);

        Button editButton = new Button("Editar");
        editButton.setStyle("-fx-max-height: 25px; -fx-min-width: 110px; -fx-background-color: #53c89b; -fx-text-fill: white;");
        editButton.setOnAction(event -> {
            showDialogEdit(idAdmin);  // Abre o diálogo de edição
        });

        Button deleteButton = new Button("Excluir");
        deleteButton.setStyle("-fx-max-height: 25px; -fx-min-width: 110px; -fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> {
            AdminDAO adminDao = new AdminDAO();
            try {
                adminDao.removerUsuario(idAdmin);
                ((Stage) gridPane.getScene().getWindow()).close();
                mostrarAlerta("Excluir admin", "Excluir concluído!");
            } catch (SQLException e) {
                System.err.println("Erro ao excluir o usuário: " + e.getMessage());
            }
        });

        gridPane.add(editButton, 0, 1);
        gridPane.add(deleteButton, 1, 1);

        return gridPane;
    }



    /*----------Dialog pane Editar usuário--------------*/


    private void showDialogEdit(int idAdmin) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Editar Administrador");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createEditNode(idAdmin));
        dialog.showAndWait();
    }

    private Node createEditNode(int idAdmin) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        AdminDAO adminDao = new AdminDAO();
        Admin admin = adminDao.getAdmin(idAdmin);

        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label labelNome = new Label("Nome:");
        TextField adminNome = new TextField(admin.getNome());
        adminNome.setStyle("-fx-min-width: 200px");

        Label labelTelefone = new Label("Telefone:");
        TextField adminTelefone = new TextField(admin.getTelefone());
        adminTelefone.setStyle("-fx-min-width: 200px");

        Button saveButton = new Button("Salvar");
        saveButton.setStyle("-fx-max-height: 25px; -fx-min-width: 110px; -fx-background-color: #53c89b; -fx-text-fill: white;");
        saveButton.setOnAction(event -> {
            String nome = adminNome.getText();
            String telefone = adminTelefone.getText();
            adminDao.editarAdmin(new Admin(idAdmin, nome, telefone, TipoUser.ADMIN));
            mostrarAlerta("Edição", "Admin editado com sucesso!");
            ((Stage) gridPane.getScene().getWindow()).close();
        });
        
        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(labelNome, 0, 2);
        gridPane.add(adminNome, 0, 3);
        gridPane.add(labelTelefone, 0, 4);
        gridPane.add(adminTelefone, 0, 5);
        gridPane.add(saveButton, 0, 7);
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
