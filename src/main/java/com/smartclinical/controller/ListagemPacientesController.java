package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ListagemPacientesController {

    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Paciente> tableViewPacientes;

    @FXML
    private TableColumn<Paciente, Integer> colunaIdPaciente;

    @FXML
    private TableColumn<Paciente, String> colunaNome;

    @FXML
    private TableColumn<Paciente, Long> colunaCpf;

    @FXML
    private TableColumn<Paciente, String> colunaDataNascimento;

    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoRemover;

    private PacienteDAO pacienteDAO;

    private ObservableList<Paciente> listaPacientes;

    public void initialize() {
        // Inicializando a lista de consultas
        listaPacientes = FXCollections.observableArrayList();

        // Configurando as colunas da TableView
        colunaIdPaciente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        // Carregar dados da consulta e popular a TableView
        carregarDadosPaciente();
    }

    private void carregarDadosPaciente() {
        // Usando o DAO para listar os pacientes
        PacienteDAO pacienteDAO = new PacienteDAO();
        listaPacientes.setAll(pacienteDAO.listarPacientes());

        // Atribuindo a lista à TableView
        tableViewPacientes.setItems(listaPacientes);
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

    // alerta caso nenhum paciente for selecionado
    private void exibirAlertaSelecao() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Nenhum Paciente Selecionado");
        alerta.setHeaderText(null);
        alerta.setContentText("Por favor, selecione um paciente na tabela para continuar.");
        alerta.showAndWait();
    }

    // Volta para tela anterior
    public void voltarParaPacientes(){
        try{
            Main main = new Main();
            main.abrirPainel("painelPacientes.fxml", "Painel de pacientes");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

    @FXML
    private void editarPaciente() {
        Paciente pacienteSelecionado = tableViewPacientes.getSelectionModel().getSelectedItem();
        if (pacienteSelecionado == null) {
            exibirAlertaSelecao();
        } else {
            // Criar o diálogo de edição
            Dialog<Paciente> dialog = new Dialog<>();
            dialog.setTitle("Editar Paciente");

            // Criar os campos de texto para edição
            TextField campoNome = new TextField(pacienteSelecionado.getNome());
            TextField campoCpf = new TextField(String.valueOf(pacienteSelecionado.getCpf()));
            TextField campoDataNascimento = new TextField(pacienteSelecionado.getDataNascimento());

            // Criar um GridPane para organizar os campos
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            grid.add(new Label("Nome:"), 0, 0);
            grid.add(campoNome, 1, 0);
            grid.add(new Label("CPF:"), 0, 1);
            grid.add(campoCpf, 1, 1);
            grid.add(new Label("Data de Nascimento:"), 0, 2);
            grid.add(campoDataNascimento, 1, 2);

            dialog.getDialogPane().setContent(grid);

            // Adicionar botões "Salvar" e "Cancelar"
            ButtonType salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(salvarButtonType, ButtonType.CANCEL);

            // Tratar ação de salvar
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == salvarButtonType) {
                    pacienteSelecionado.setNome(campoNome.getText());
                    pacienteSelecionado.setCpf(Long.parseLong(campoCpf.getText()));
                    pacienteSelecionado.setDataNascimento(campoDataNascimento.getText());
                    return pacienteSelecionado;
                }
                return null;
            });

            // Mostrar o diálogo e aguardar a confirmação
            Optional<Paciente> result = dialog.showAndWait();
            result.ifPresent(paciente -> {
                // Atualizar o paciente no banco de dados
                PacienteDAO pacienteDAO = new PacienteDAO();
                pacienteDAO.alterarPaciente(paciente);

                // Recarregar os dados da tabela
                carregarDadosPaciente();
            });
        }
    }

    @FXML
    private void removerPaciente(){
        Paciente pacienteSelecionado = tableViewPacientes.getSelectionModel().getSelectedItem();
        if (pacienteSelecionado == null) {
            exibirAlertaSelecao();
        } else {
            // Criar um alerta de confirmação
            Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacao.setTitle("Confirmação de Remoção");
            alertaConfirmacao.setHeaderText("Remover Paciente");
            alertaConfirmacao.setContentText("Tem certeza que deseja remover o paciente " + pacienteSelecionado.getNome() + "?");

            // Adicionar botões de confirmação e cancelamento
            ButtonType botaoConfirmar = new ButtonType("Confirmar");
            ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alertaConfirmacao.getButtonTypes().setAll(botaoConfirmar, botaoCancelar);

            // Mostrar o alerta e aguardar a resposta
            Optional<ButtonType> result = alertaConfirmacao.showAndWait();
            if (result.isPresent() && result.get() == botaoConfirmar) {
                // Remover o paciente e possiveis consultas no banco de dados
               removerPacienteComConsultas(pacienteSelecionado.getId());

                // Recarregar os dados da tabela
                carregarDadosPaciente();
            }
        }
    }

    // remove as consultas relacionadas ao paciente a ser deletado
    public void removerPacienteComConsultas(int pacienteId) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();

        consultaDAO.removerConsultasPorPaciente(pacienteId);
        pacienteDAO.removerPaciente(pacienteId);

        carregarDadosPaciente();  // Atualizar a TableView
    }


}

