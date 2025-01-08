package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.Sessao;
import com.smartclinical.util.TipoUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
            mostrarCamposAdicionais(newValue);  // Chama o metodo para mostrar/ocultar os campos
        });

        // Inicialmente oculta os campos específicos
        mostrarCamposAdicionais(null);
    }

    // metodo que lida com a exibição dos campos adicionais
    private void mostrarCamposAdicionais(TipoUser tipoUser) {
        boolean isMedico = (tipoUser == TipoUser.MEDICO);
        boolean isRecepcionista = (tipoUser == TipoUser.RECEPCIONISTA);

        campoCRM.setVisible(isMedico);
        campoEspecialidade.setVisible(isMedico);
        campoTurno.setVisible(isRecepcionista);
    }

    // metodo para cadastrar de acordo com o tipo de usuario selecionado
    public void cadastrarUsuario(){
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();
        String telefone = campoTelefone.getText();
        TipoUser tipoUser = comboCargo.getValue();

        // Obter o usuário logado da sessão
        Usuario usuarioLogado = Sessao.getUsuarioLogado();
        Admin admin = (Admin) usuarioLogado;

        if(tipoUser == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campo em branco");
            alert.setContentText("Selecione o cargo");
            alert.showAndWait();
            return;
        }

        switch(tipoUser){
            case MEDICO:
                String crm = campoCRM.getText();
                String especialidade = campoEspecialidade.getText();
                Medico medico = new Medico(nome, email, senha, telefone, tipoUser, crm, especialidade);
                admin.cadastrarMedico(medico);
                break;

            case RECEPCIONISTA:
                String turno = campoTurno.getText();
                Recepcionista recepcionista = new Recepcionista(nome, email, senha, telefone, tipoUser, turno);
                admin.cadastrarRecepcionista(recepcionista);
                break;

            case ADMIN:
                Admin adminUser = new Admin(nome, email, senha, telefone, tipoUser);
                admin.cadastrarAdmin(adminUser);
                break;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Usuário cadastrado com sucesso!");
        alert.showAndWait();

        campoNome.clear();
        campoEmail.clear();
        campoSenha.clear();
        campoTelefone.clear();
        comboCargo.setValue(null);
        campoCRM.clear();
        campoEspecialidade.clear();
        campoTurno.clear();
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
