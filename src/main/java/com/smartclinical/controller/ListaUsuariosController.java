package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ListaUsuariosController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Usuario> tableViewUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colunaIdUsuario;

    @FXML
    private TableColumn<Usuario, String> colunaNome;

    @FXML
    private TableColumn<Usuario, Long> colunaEmail;

    @FXML
    private TableColumn<Usuario, String> colunaTelefone;

    @FXML
    private TableColumn<Usuario, String> colunaTipoUser;

    private ObservableList<Usuario> listaUsuarios;

    public void initialize() {
        // Inicializando a lista de consultas
        listaUsuarios = FXCollections.observableArrayList();

        // Configurando as colunas da TableView
        colunaIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaTipoUser.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));

        // Carregar dados da consulta e popular a TableView
        carregarDadosUsuario();
    }

    private void carregarDadosUsuario() {
        // Usando o DAO para listar os pacientes
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        listaUsuarios.setAll(usuarioDAO.listarUsuario());

        // Atribuindo a lista à TableView
        tableViewUsuarios.setItems(listaUsuarios);
    }

    /**
     * ****** LOGOUT APENAS ***************
     */
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
    /*
     * ******** FIM DO LOGOUT ********
     */

    // Volta para tela anterior
    public void voltarParaAdmin(){
        try{
            Main main = new Main();
            main.abrirPainel("admin.fxml", "Painel Admin");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}
