package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.ConsultaDAO;
import com.smartclinical.dao.MedicoDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.ProntuarioDAO;
import com.smartclinical.model.Consulta;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Prontuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CadastroProntuarioController {

    @FXML
    private Button botaoLogout;

    @FXML
    private ComboBox<Consulta> comboConsulta;

    @FXML
    private ComboBox<Medico> comboMedico;

    @FXML
    private TextArea descricaoProntuario;

    private ConsultaDAO consultaDAO;
    private MedicoDAO medicoDAO;

    @FXML
    public void initialize() {
        consultaDAO = new ConsultaDAO();
        medicoDAO = new MedicoDAO();
        carregarMedicos();
    }

    private void carregarMedicos() {
        List<Medico> medicos = medicoDAO.listarMedicos();
        ObservableList<Medico> medicoObservableList = FXCollections.observableArrayList(medicos);
        comboMedico.setItems(medicoObservableList);

        // Listener para carregar consultas ao selecionar um médico
        comboMedico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                carregarConsultas(newValue.getId());
            }
        });
    }

    private void carregarConsultas(int medicoId) {
        List<Consulta> consultas = consultaDAO.listarConsultasProntuario(medicoId);
        ObservableList<Consulta> consultaObservableList = FXCollections.observableArrayList(consultas);
        comboConsulta.setItems(consultaObservableList);
    }


    // Metodo cadastro prontuário
    public void cadastrarProntuario() {
        String descricao = descricaoProntuario.getText();
        Consulta consultaSelecionada = comboConsulta.getSelectionModel().getSelectedItem();
        Medico medicoSelecionado = comboMedico.getSelectionModel().getSelectedItem();

        if (consultaSelecionada == null || medicoSelecionado == null || descricao.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Dados Incompletos");
            alert.setContentText("Verifique novamente as informações preenchidas.");
            alert.showAndWait();
        } else {
            ProntuarioDAO prontuarioDAO = new ProntuarioDAO();

            boolean sucesso = prontuarioDAO.cadastrarProntuario(consultaSelecionada.getIdConsulta(), descricao);

            Alert alert;
            if(sucesso) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Prontuario cadastrado com sucesso");

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("");
                alert.setContentText("Erro ao cadastrar!");
            }
            alert.showAndWait();
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

    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelProntuario.fxml", "Prontuario");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }



}
