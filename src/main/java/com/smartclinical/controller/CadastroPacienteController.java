package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Recepcionista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CadastroPacienteController {
    @FXML
    private Button botaoLogout;

    @FXML
    private TextField pacienteInputNome;

    @FXML
    private TextField pacienteInputCpf;

    @FXML
    private TextField pacienteInputDataNascimento;

    @FXML
    private Button submitCadastroPaciente;

    PacienteDAO pacientedao = new PacienteDAO();

    public CadastroPacienteController() {

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


    // volta para o painel de pacientes
    public void voltarParaPacientes(){
        try{
            Main main = new Main();
            main.abrirPainel("painelCadastros.fxml", "Painel Cadastros");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

    // cadastra o paciente no banco de dados
    public void cadastrarPaciente(){
        String nome = pacienteInputNome.getText();
        String cpf = pacienteInputCpf.getText();
        String dataNascimento = pacienteInputDataNascimento.getText();

        Paciente paciente = new Paciente(nome, cpf, dataNascimento);
        pacientedao.cadastrarPaciente(paciente);
        clear();
        mostrarAlerta("Cadastro","paciente cadastrado com sucesso!");

    }

    public void clear(){
        pacienteInputNome.clear();
        pacienteInputCpf.clear();
        pacienteInputDataNascimento.clear();
    }

    public void LimparCampos(ActionEvent actionEvent) {
        pacienteInputNome.clear();
        pacienteInputCpf.clear();
        pacienteInputDataNascimento.clear();
    }

    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}