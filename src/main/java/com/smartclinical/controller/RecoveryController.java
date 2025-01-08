package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class RecoveryController {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoVoltarLogin;

    @FXML
    private TextField campoEmail;

    @FXML
    private void voltarParaLogin(){
        try{
            // fecha a painel principal
            Stage stage = (Stage) botaoVoltarLogin.getScene().getWindow();
            stage.close();
            // vai para o start novamente (tela de login)
            Main m = new Main();
            m.start(new Stage());

        } catch (Exception e) {
            System.out.println("Erro ao fazer logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void buscarEmail(){
        UsuarioDAO dao = new UsuarioDAO();
        String email = campoEmail.getText();
        Usuario usuario = dao.buscarUsuarioPorEmail(email);

        if(usuario != null){
            alterarSenha(usuario);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Email não cadastrado");
            alert.showAndWait();
        }
    }

    private void alterarSenha(Usuario usuario) {
        // Criar um diálogo personalizado para inserir a nova senha
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Alterar Senha");

        // Configurar o botão de confirmação (OK)
        ButtonType confirmarButtonType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelarButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmarButtonType, cancelarButtonType);

        // Criar um campo de senha (PasswordField)
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Digite a nova senha");

        // Adicionar o campo ao painel do diálogo
        dialog.getDialogPane().setContent(senhaField);

        // Definir o comportamento ao clicar no botão Confirmar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmarButtonType) {
                return senhaField.getText();  // Retorna a nova senha se confirmar
            }
            return null;
        });

        // Exibir o diálogo e pegar a nova senha
        Optional<String> resultado = dialog.showAndWait();

        // Se o usuário confirmou, altere a senha
        if (resultado.isPresent()) {
            String novaSenha = resultado.get();
            if (!novaSenha.isEmpty()) {
                // metodo dao para alterar a senha
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.alterarSenha(novaSenha, usuario.getId());

                // Mensagem de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Senha alterada com sucesso!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Senha não pode ser vazia!");
                alert.showAndWait();
            }
        }
    }
}

