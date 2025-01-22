package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.model.Consulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RelatoriosController {

    @FXML private TableView<Consulta> tabelaConsultas;
    @FXML private TableColumn<Consulta, String> colunaPaciente;
    @FXML private TableColumn<Consulta, String> colunaMedico;
    @FXML private TableColumn<Consulta, Double> colunaValor;
    @FXML private Button botaoLogout;
    @FXML private Label labelResumo;

    private ConsultaDAO consultaDAO;

    public RelatoriosController() {
        consultaDAO = new ConsultaDAO();
    }

    @FXML
    public void initialize() {
        // Definindo as colunas da tabela
        colunaPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colunaMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    @FXML
    public void gerarRelatorio() {
        // Buscar todas as consultas
        List<Consulta> consultas = consultaDAO.listarConsultas();

        if (consultas.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Relatório");
            alert.setHeaderText(null);
            alert.setContentText("Nenhuma consulta encontrada.");
            alert.showAndWait();
        } else {
            // Exibir as consultas na tabela
            tabelaConsultas.getItems().setAll(consultas);

            // Calcular a soma dos valores e a quantidade de consultas
            double somaValores = 0;
            int quantidadeConsultas = consultas.size();

            for (Consulta consulta : consultas) {
                somaValores += consulta.getValor(); // Somando o valor das consultas
            }

            // Atualizar o texto do labelResumo
            String resumo = String.format("Total de Consultas: %d  |  Soma dos Valores: R$ %.2f", quantidadeConsultas, somaValores);
            labelResumo.setText(resumo);
        }
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

    // botao que volta para o painel principal
    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelPrincipal.fxml", "Painel Principal");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }
}

