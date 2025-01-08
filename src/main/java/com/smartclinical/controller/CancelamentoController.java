package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.model.Consulta;
import com.smartclinical.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CancelamentoController {


    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Consulta> tableViewConsultas;

    @FXML
    private TableColumn<Consulta, Integer> colunaIdConsulta;

    @FXML
    private TableColumn<Consulta, String> colunaDataHora;

    @FXML
    private TableColumn<Consulta, String> colunaNomePaciente;

    @FXML
    private TableColumn<Consulta, String> colunaNomeMedico;

    private ObservableList<Consulta> listaConsultas;

    public void initialize() {
        // Inicializando a lista de consultas
        listaConsultas = FXCollections.observableArrayList();

        // Configurando as colunas da TableView
        colunaIdConsulta.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        colunaDataHora.setCellValueFactory(new PropertyValueFactory<>("data_hora"));
        colunaNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colunaNomeMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));

        // Carregar dados da consulta e popular a TableView
        carregarDadosConsultas();
    }

    private void carregarDadosConsultas() {
        // Usando o DAO para listar as consultas
        ConsultaDAO consultaDAO = new ConsultaDAO();
        listaConsultas.setAll(consultaDAO.listarConsultas());

        // Atribuindo a lista à TableView
        tableViewConsultas.setItems(listaConsultas);
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


    // Volta para o painel de agendamento
    public void voltarParaAgendamento(ActionEvent actionEvent) {
        try {
            Main main = new Main();
            main.abrirPainel("painelAgendamento.fxml", "Agendamento");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    // Se nenhuma consulta for selecionada
    private void exibirAlertaSelecao() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Nenhuma consulta selecionada");
        alerta.setHeaderText(null);
        alerta.setContentText("Por favor, selecione uma consulta na tabela para continuar.");
        alerta.showAndWait();
    }

    @FXML
    private void removerConsulta() {
        Consulta consultaSelecionada = tableViewConsultas.getSelectionModel().getSelectedItem();
        if (consultaSelecionada == null) {
            exibirAlertaSelecao();
        } else {
            // Criar um alerta de confirmação
            Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacao.setTitle("Confirmação de Remoção");
            alertaConfirmacao.setHeaderText("Excluir consulta");
            alertaConfirmacao.setContentText("Tem certeza que deseja cancelar e excluir a consulta?");

            // Adicionar botões de confirmação e cancelamento
            ButtonType botaoConfirmar = new ButtonType("Confirmar");
            ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alertaConfirmacao.getButtonTypes().setAll(botaoConfirmar, botaoCancelar);

            // Mostrar o alerta e aguardar a resposta
            Optional<ButtonType> result = alertaConfirmacao.showAndWait();
            if (result.isPresent() && result.get() == botaoConfirmar) {
                // A consulta foi confirmada para remoção, então chamar o metodo do DAO
                ConsultaDAO consultaDAO = new ConsultaDAO();
                boolean sucesso = consultaDAO.removerConsultaPorId(consultaSelecionada.getIdConsulta());

                if (sucesso) {
                    // Se a remoção for bem-sucedida, recarregar os dados da tabela
                    carregarDadosConsultas();
                } else {
                    // Caso não tenha sido possível remover a consulta
                    Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                    alertaErro.setTitle("Erro de Remoção");
                    alertaErro.setHeaderText(null);
                    alertaErro.setContentText("Ocorreu um erro ao tentar remover a consulta.");
                    alertaErro.showAndWait();
                }
            }
        }
    }
}