package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
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

    // alerta caso nenhum paciente for selecionado
    private void exibirAlertaSelecao() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Nenhum Paciente Selecionado");
        alerta.setHeaderText(null);
        alerta.setContentText("Por favor, selecione um paciente na tabela para continuar.");
        alerta.showAndWait();
    }

    // editar usuario
    @FXML
    private void editarUsuario() {
        Usuario usuarioSelecionado = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado == null) {
            exibirAlertaSelecao();
        } else {
            // Criar o diálogo de edição
            Dialog<Usuario> dialog = new Dialog<>();
            dialog.setTitle("Editar Usuário");

            // Criar os campos de texto para edição
            TextField campoNome = new TextField(usuarioSelecionado.getNome());
            TextField campoEmail = new TextField(usuarioSelecionado.getEmail());
            TextField campoTelefone = new TextField(usuarioSelecionado.getTelefone());

            // Criar um GridPane para organizar os campos
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            grid.add(new Label("Nome:"), 0, 0);
            grid.add(campoNome, 1, 0);
            grid.add(new Label("E-mail:"), 0, 1);
            grid.add(campoEmail, 1, 1);
            grid.add(new Label("Telefone:"), 0, 2);
            grid.add(campoTelefone, 1, 2);

            dialog.getDialogPane().setContent(grid);

            // Adicionar botões "Salvar" e "Cancelar"
            ButtonType salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(salvarButtonType, ButtonType.CANCEL);

            // Tratar ação de salvar
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == salvarButtonType) {
                    usuarioSelecionado.setNome(campoNome.getText());
                    usuarioSelecionado.setEmail(campoEmail.getText());
                    usuarioSelecionado.setTelefone(campoTelefone.getText());
                    return usuarioSelecionado;
                }
                return null;
            });

            // Mostrar o diálogo e aguardar a confirmação
            Optional<Usuario> result = dialog.showAndWait();
            result.ifPresent(usuario -> {
                // Atualizar o usuário no banco de dados
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.editarUsuario(usuario);

                // Recarregar os dados da tabela
                carregarDadosUsuario();
            });
        }
    }

    @FXML
    private void removerUsuario() throws SQLException {
        Usuario usuarioSelecionado = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado == null) {
            exibirAlertaSelecao();
        } else {
            // Criar um alerta de confirmação
            Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacao.setTitle("Confirmação de Remoção");
            alertaConfirmacao.setHeaderText("Remover Usuário");
            alertaConfirmacao.setContentText("Tem certeza que deseja remover o usuário " + usuarioSelecionado.getNome() + "?");

            // Adicionar botões de confirmação e cancelamento
            ButtonType botaoConfirmar = new ButtonType("Confirmar");
            ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alertaConfirmacao.getButtonTypes().setAll(botaoConfirmar, botaoCancelar);

            // Mostrar o alerta e aguardar a resposta
            Optional<ButtonType> result = alertaConfirmacao.showAndWait();
            if (result.isPresent() && result.get() == botaoConfirmar) {
                // Remover o usuário e possivelmente consultas no banco de dados
                removerUsuarioComConsultas(usuarioSelecionado.getId());

                // Recarregar os dados da tabela
                carregarDadosUsuario();
            }
        }
    }

    // Remove as consultas relacionadas ao usuário a ser deletado
    public void removerUsuarioComConsultas(int usuarioId) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();

        consultaDAO.removerConsultasPorUsuario(usuarioId);
        usuarioDAO.removerUsuario(usuarioId);

        carregarDadosUsuario();  // Atualizar a TableView
    }


}
