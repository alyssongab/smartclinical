package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.TipoUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CadastroUsuarioController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TextField UsuarioInputNome;

    @FXML
    private TextField UsuarioInputEmail;

    @FXML
    private TextField UsuarioInputSenha;

    @FXML
    private TextField UsuarioInputTelefone;

    UsuarioDAO usuarioDao = new UsuarioDAO();

//    @FXML
//    private ComboBox<String> UsuarioComboBox;
//
//
//    public void initialize() {
//        UsuarioComboBox.setItems(FXCollections.observableArrayList("ADMIN", "MEDICO", "RECEPCIONISTA"));
//    }

//    public void cadastrarUsuario(ActionEvent actionEvent) {
//        String nome = UsuarioInputNome.getText();
//        String email = UsuarioInputEmail.getText();
//        String senha = UsuarioInputSenha.getText();
//        String telefone = UsuarioInputTelefone.getText();
//        TipoUser usuarioTipo = TipoUser.valueOf(UsuarioComboBox.getValue());
//
//        Usuario usuario = new Usuario(nome,email,senha,telefone,usuarioTipo);
//        usuarioDao.inserirUsuario(usuario);
//    }


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