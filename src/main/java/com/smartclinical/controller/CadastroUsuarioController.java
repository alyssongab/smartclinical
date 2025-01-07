package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.util.TipoUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class CadastroUsuarioController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEmail;

    @FXML
    private TextField campoSenha;

    @FXML
    private TextField campoTelefone;

    @FXML
    private ComboBox<TipoUser> comboCargo;

    @FXML
    private TextField campoCRM;

    @FXML
    private TextField campoEspecialidade;

    @FXML
    private TextField campoTurno;

    @FXML
    public void initialize() {
        // Cria uma lista observável com os valores do Enum TipoUser
        ObservableList<TipoUser> tiposDeUsuario = FXCollections.observableArrayList(TipoUser.values());

        // Preenche o ComboBox com os valores do Enum
        comboCargo.setItems(tiposDeUsuario);

        // Adiciona o listener para a mudança de seleção
        comboCargo.valueProperty().addListener((observable, oldValue, newValue) -> {
            mostrarCamposAdicionais(newValue);  // Chama o método para mostrar/ocultar os campos
        });
    }

    // Método que lida com a exibição dos campos adicionais
    private void mostrarCamposAdicionais(TipoUser tipoUser) {
        // Oculta todos os campos adicionais por padrão
        campoCRM.setVisible(false);
        campoEspecialidade.setVisible(false);
        campoTurno.setVisible(false);

        // Exibe campos de acordo com o tipo de usuário selecionado
        if (tipoUser == TipoUser.MEDICO) {
            campoCRM.setVisible(true);
            campoEspecialidade.setVisible(true);
        } else if (tipoUser == TipoUser.RECEPCIONISTA) {
            campoTurno.setVisible(true);
        }
        // Para ADMIN, não faz nada, pois já estão ocultos por padrão
    }

    public void voltarParaPainelAdmin(ActionEvent actionEvent) {
        try {
            Main m = new Main();
            m.abrirPainel("admin.fxml", "Painel Admin");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
