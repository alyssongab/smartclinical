package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.util.ConexaoBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
        submitLogin.setOnAction(event -> {
            try {
                fazerLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // método para autenticar usuário
    private void fazerLogin() throws IOException {
        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Campos Vazios", "Preencha todos os campos!");
        }
        else if(autenticarUsuario(email, senha)){
            // Se autenticação for bem-sucedida, navega para o painel principal
            Main m = new Main();
            m.abrirPainel("painel.fxml");
        }
        else {
            mostrarAlerta("Erro no login", "Email ou senha incorretos");
        }
    }

    // faz uma busca no banco utilizando prepared statement e retorna o valor(true) ou false
    private boolean autenticarUsuario(String email, String senha) {
        String busca = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try(Connection conn = ConexaoBD.getConexao()) {
            assert conn != null;
            try(PreparedStatement instrucao = conn.prepareStatement(busca)){

                instrucao.setString(1, email);
                instrucao.setString(2, senha);
                ResultSet resultado = instrucao.executeQuery();

                return resultado.next();
            }
        }
        catch(Exception e){
            System.out.println("Erro ao autenticar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
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