package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.Sessao;
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
            main.abrirPainel("painelPacientes.fxml", "Painel de pacientes");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

    // metodo que faz o cadastro do paciente
    public void cadastrarPaciente(){
        String nome = pacienteInputNome.getText();
        long cpf = Long.parseLong(pacienteInputCpf.getText());
        String dataNascimento = pacienteInputDataNascimento.getText();

        Paciente paciente = new Paciente(nome, cpf, dataNascimento);

        // Obter o usuário logado da sessão
        Usuario usuarioLogado = Sessao.getUsuarioLogado();

        Recepcionista recep = (Recepcionista) usuarioLogado;

        try{
            recep.cadastrarPaciente(paciente);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Sucesso");
            alert.setContentText("Paciente cadastrado com sucesso!");
            alert.showAndWait();

            pacienteInputNome.clear();
            pacienteInputCpf.clear();
            pacienteInputDataNascimento.clear();
        }
        catch(Exception e){
            System.out.println("Erro ao cadastrar paciente: " + e.getMessage());
        }
    }
}
