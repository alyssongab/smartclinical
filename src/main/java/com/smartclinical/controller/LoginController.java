package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.Sessao;
import com.smartclinical.util.TipoUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.*;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputSenha;

    @FXML
    private Button submitLogin;


    // metodo que inicia o controller
    public void initialize() {
        submitLogin.setOnAction(event -> {
            try {
                fazerLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // metodo para autenticar usuário
    private void fazerLogin() throws IOException {
        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Campos Vazios", "Preencha todos os campos!");
        }
        else {
            Usuario usuario = autenticarUsuario(email, senha);
            if (usuario != null) {
                // Preenche a sessão com o usuário logado
                Sessao.setUsuarioLogado(usuario);
                Sessao.setTipoUser(usuario.getTipoUsuario()); // define o tipo de usuario

                // Se autenticação for bem-sucedida, navega para o painel principal
                Main m = new Main();
                m.abrirPainel("painelPrincipal.fxml", "Painel Principal");
            } else {
                mostrarAlerta("Erro no login", "Email ou senha incorretos");
            }
        }
    }

    // faz uma busca no banco utilizando prepared statement e retorna o tipo de usuario
    private Usuario autenticarUsuario(String email, String senha) {
        String busca = "SELECT id,nome,email,senha,telefone,tipoUser FROM usuario WHERE email = ? AND senha = ?";

        try(Connection conn = ConexaoBD.getConexao()) {
            assert conn != null;
            PreparedStatement instrucao = conn.prepareStatement(busca);
            instrucao.setString(1, email);
            instrucao.setString(2, senha);

            ResultSet resultado = instrucao.executeQuery();

            // Verifica se o usuário foi encontrado
            if(resultado.next()) {
                // Recupera dados básicos do usuário
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                String tipoUser = resultado.getString("tipoUser");

                // Transforma de string para enum
                TipoUser tipoUsuario = TipoUser.valueOf(tipoUser);

                // Caso recepcionista
                if (tipoUsuario.equals(TipoUser.RECEPCIONISTA)) {
                    // Consulta a tabela recepcionista para saber o turno
                    String turno = obterTurnoRecepcionista(id);
                    Recepcionista recepcionista = new Recepcionista(nome, email, senha, telefone, tipoUsuario, turno);
                    return recepcionista;
                }
                else if(tipoUsuario.equals(TipoUser.MEDICO)) {
                    // Consultar a tabela medico para obter crm e especialidade
                    Medico medico = obterMedico(id, nome, email, senha, telefone);
                    return medico;
                }
                else{
                    return new Admin(nome, email, senha, telefone, tipoUsuario);
                }
            }
            else{
                return null;
            }

        }
        catch(SQLException e){
            System.out.println("Erro ao autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private String obterTurnoRecepcionista(int idUsuario) {
        String sql = "SELECT turno FROM recepcionistas WHERE id_recepcionista = ?";
        try (Connection conn = ConexaoBD.getConexao()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("turno");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Medico obterMedico(int idUsuario, String nome, String email, String senha, String telefone) {
        String sql = "SELECT crm, especialidade FROM medicos WHERE id_medico = ?";
        try (Connection conn = ConexaoBD.getConexao()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String crm = rs.getString("crm");
                String especialidade = rs.getString("especialidade");
                return new Medico(nome, email, senha, telefone, TipoUser.MEDICO, crm, especialidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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