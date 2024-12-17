package com.smartclinical.controller;

import com.smartclinical.app.Login;
import com.smartclinical.util.ConexaoBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputSenha;

    @FXML
    private Button submitLogin;

    // método que inicia o controller
    public void initialize() {
        submitLogin.setOnAction(event -> fazerLogin());
    }

    // método para autenticar usuário
    private void fazerLogin() {
        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Campos Vazios", "Preencha todos os campos!");
        }
        else if(autenticarUsuario(email, senha)){
            carregarPainelPrincipal();
        }
        else {
            mostrarAlerta("Erro no login", "Email ou senha incorretos");
        }
    }

    // faz uma busca no banco utilizando prepared statement e retorna o valor(true) ou false
    private boolean autenticarUsuario(String email, String senha) {
        String busca = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try(Connection conn = ConexaoBD.getConexao();
            PreparedStatement instrucao = conn.prepareStatement(busca)){

            instrucao.setString(1, email);
            instrucao.setString(2, senha);
            ResultSet resultado = instrucao.executeQuery();

            return resultado.next();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void carregarPainelPrincipal() {
        try{
            FXMLLoader loader = new FXMLLoader(Login.class.getResource("painel.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) submitLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Painel Principal");
            stage.show();
        }
        catch(Exception e){
            System.out.println("Erro ao carregar painel principal " + e.getMessage());
            e.printStackTrace();
        }

    }

    // popup para caso de login inválido
    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}