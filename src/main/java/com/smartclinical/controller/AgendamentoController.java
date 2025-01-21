package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.MedicoDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.model.Consulta;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AgendamentoController {

    @FXML
    private Button botaoLogout;

    @FXML
    private ComboBox<Medico> comboMedico;

    @FXML
    private ComboBox<Paciente> comboPaciente;

    @FXML
    private TextField inputDataHora;

    @FXML
    private TextField inputValor;

    private MedicoDAO medicoDAO;
    private PacienteDAO pacienteDAO;

    @FXML
    public void initialize() {
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
        carregarMedicos();
        carregarPacientes();
    }

    private void carregarMedicos() {
        List<Medico> medicos = medicoDAO.listarMedicos();
        ObservableList<Medico> medicoObservableList = FXCollections.observableArrayList(medicos);
        comboMedico.setItems(medicoObservableList);
    }

    private void carregarPacientes() {
        List<Paciente> pacientes = pacienteDAO.listarPacientes();
        ObservableList<Paciente> pacienteObservableList = FXCollections.observableArrayList(pacientes);
        comboPaciente.setItems(pacienteObservableList);
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

    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelAgendamento.fxml", "Agendamento");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

//    public void agendarConsulta() {
//
//    }

    // Metodo para agendar a consulta (exemplo)
    public void agendarConsulta() {
        String dataHora = inputDataHora.getText();
        Medico medicoSelecionado = comboMedico.getSelectionModel().getSelectedItem();
        Paciente pacienteSelecionado = comboPaciente.getSelectionModel().getSelectedItem();
        double valor = Double.parseDouble(inputValor.getText());

        if (medicoSelecionado == null || pacienteSelecionado == null || dataHora.isEmpty() || valor <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Dados Incompletos");
            alert.setContentText("Verifique novamente as informações preenchidas.");
            alert.showAndWait();
        } else {
            Consulta consulta = new Consulta(dataHora, pacienteSelecionado, medicoSelecionado, valor);
            ConsultaDAO consultaDAO = new ConsultaDAO();

            // verifica se há paciente e medico, depois cria a consulta
            boolean sucesso = consultaDAO.criarConsulta(consulta);

            Alert alert;
            if(sucesso) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Agendada com sucesso");
                alert.setContentText("Consulta agendada para: \n" + dataHora + "\n Médico: " + medicoSelecionado + "\n Paciente: " + pacienteSelecionado + "\n Valor: R$ " + valor);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("");
                alert.setContentText("Erro ao agendar!");
            }
            alert.showAndWait();
        }
    }


}
