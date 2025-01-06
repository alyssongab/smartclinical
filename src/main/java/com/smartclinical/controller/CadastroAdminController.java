package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.util.TipoUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CadastroAdminController {
    @FXML
    private Button botaoLogout;

    @FXML
    private TextField adminInputNome;

    @FXML
    private TextField adminInputEmail;

    @FXML
    private TextField adminInputSenha;

    @FXML
    private TextField adminInputTelefone;


    AdminDAO adminDao = new AdminDAO();


    public void cadastrarAdmin(ActionEvent actionEvent) {
        String nome =  adminInputNome.getText();
        String email = adminInputEmail.getText();
        String senha = adminInputSenha.getText();
        String telefone = adminInputTelefone.getText();
        TipoUser tipoUser = TipoUser.ADMIN;
        try{
            Admin admin = new Admin(nome,email,senha,telefone,tipoUser);
            adminDao.inserirAdmin(admin);
            mostrarAlerta("Cadastro", "Cadastro realizado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Limpa os campos do cadastro
    public void LimparCampos(ActionEvent actionEvent) {
        adminInputNome.clear();
        adminInputEmail.clear();
        adminInputTelefone.clear();
        adminInputSenha.clear();
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
