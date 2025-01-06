package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
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
}
