package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Usuario;
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
        dialog.setTitle("Admin options");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNode(id, dialog));  // Passa o dialog para a função createNode
        dialog.showAndWait();
    }

    private Node createNode(int idAdmin, Dialog<Void> dialog) {
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
            dialog.close();
            showDialogEdit(idAdmin);
        });

        Button deleteButton = new Button("Excluir");
        deleteButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> {
            AdminDAO adminDao = new AdminDAO();
            try {
                adminDao.removerUsuario(idAdmin);
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


    private void showDialogEdit(int idAdmin) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Edição");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(editNode(idAdmin, dialog));
        dialog.showAndWait();
    }

    private Node editNode(int id, Dialog<Void> dialog) {
        dialog.close();
        GridPane gridPane = new GridPane();
        AdminDAO adminDao = new AdminDAO();

        Admin admin = adminDao.getAdmin(id);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label label = new Label("Editar administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        gridPane.add(label, 0, 0, 2, 1);

        TextField adminNome = new TextField();
        adminNome.setText(admin.getNome());
        adminNome.setStyle("-fx-min-width: 250px");

        TextField adminTelefone = new TextField();
        adminTelefone.setText(admin.getTelefone());
        adminTelefone.setStyle("-fx-min-width: 250px");

        Button submitButton = new Button("Editar");
        submitButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #53c89b; -fx-text-fill: white;");

        submitButton.setOnAction(event -> {
            String editAdminNome = adminNome.getText();
            String editAdminTelefone = adminTelefone.getText();
            Admin adminEdit = new Admin(id, editAdminNome,editAdminTelefone, TipoUser.ADMIN);
            adminDao.editarAdmin(adminEdit);
            mostrarAlerta("Edição","Edição concluída!");

        });
        gridPane.add(adminNome, 0, 1);
        gridPane.add(adminTelefone,0,2);
        gridPane.add(submitButton, 0, 4);

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
