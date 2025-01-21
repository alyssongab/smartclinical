package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.util.Sessao;
import com.smartclinical.util.TipoUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PainelPrincipalController {

    @FXML
    private Button botaoLogout;

    @FXML
    private Button botaoPainelPacientes;

    @FXML
    private Button botaoPainelAdmin;

    @FXML
    private Button botaoPainelAgendaMedica;

    @FXML
    private Button botaoPainelAgendamento;

    @FXML
    private Button botaoPainelAvaliacoes;

    @FXML
    private Button botaoPainelProntuarios;

    // inicia o controller
    public void initialize() {
        TipoUser tipoUser = Sessao.getTipoUser();
        System.out.println("Voce logou com o tipo: " + tipoUser);
        System.out.println("Nome: " + Sessao.getUsuarioLogado());

        // desabilita botoes de acordo com o tipo de usuario
        desabilitarBotoes(tipoUser);

        // para fazer logout
        botaoLogout.setOnAction(event -> fazerLogout());

        // ação do botão "Pacientes"
        botaoPainelPacientes.setOnAction(event -> {
            try {
                Main m = new Main();
                m.abrirPainel("painelPacientes.fxml", "Painel de Pacientes");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // ação do botão "Admin"
        botaoPainelAdmin.setOnAction(event -> {
            try {
                Main m = new Main();
                m.abrirPainel("admin.fxml", "Painel de Admin");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // ação do botão "Agendamento"
        botaoPainelAgendamento.setOnAction(event -> {
            try{
                Main m = new Main();
                m.abrirPainel("painelAgendamento.fxml", "Agendamento");
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
        });

        botaoPainelAgendaMedica.setOnAction(event -> {
            try{
                Main m = new Main();
                m.abrirPainel("listaConsultas.fxml", "Consultas");
            }
            catch(IOException e){
                System.out.println("Erro ao carregar listagem de consultas " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        botaoPainelProntuarios.setOnAction(event -> {
            try{
                Main m = new Main();
                m.abrirPainel("painelProntuario.fxml", "Consultas");
            }
            catch(IOException e){
                System.out.println("Erro ao carregar listagem de consultas " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        botaoPainelAvaliacoes.setOnAction(event -> {
            try{
                Main m = new Main();
                m.abrirPainel("listaAvaliacoes.fxml", "Avaliações");
            }
            catch(IOException e){
                System.out.println("Erro ao carregar listagem de avaliações " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
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


    // metodo que desabilita os botoes de acordo com o usuario logado
    private void desabilitarBotoes(TipoUser tipoUser) {
        switch (tipoUser) {
            case ADMIN:
                break;

            case RECEPCIONISTA:
                // nao pode acessar paineis de admin e de prontuarios
                botaoPainelAdmin.setDisable(true);
                botaoPainelAvaliacoes.setDisable(true);
                botaoPainelProntuarios.setDisable(true);
                break;

            case MEDICO:
                // nao pode acessar pacientes, agendamento e paineis do admin
                botaoPainelAdmin.setDisable(true);
                botaoPainelAvaliacoes.setDisable(true);
                botaoPainelPacientes.setDisable(true);
                botaoPainelAgendamento.setDisable(true);
                break;

            default:
                System.out.println("Tipo de usuário desconhecido");
                break;
        }
    }
}