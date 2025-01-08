package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.MedicoDAO;
import com.smartclinical.dao.RecepcionistaDAO;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.util.TipoUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CadastroRecepcionistaController {
    @FXML
    private Button botaoLogout;

    @FXML
    private ComboBox<String> recepcionistaComboBox02;

    @FXML
    private TextField UsuarioInputNome;

    @FXML
    private TextField UsuarioInputTelefone;

    @FXML
    private TextField UsuarioInputSenha;


    RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();

    public void initialize() {
        recepcionistaComboBox02.setItems(FXCollections.observableArrayList("Diurno", "Vespertino"));
    }

    public void cadastrarRecepcionista(ActionEvent actionEvent) {
        String nome =  UsuarioInputNome.getText();
        String telefone = UsuarioInputTelefone.getText();
        String senha = UsuarioInputSenha.getText();
        String turno = recepcionistaComboBox02.getValue();
        TipoUser tipoUser = TipoUser.RECEPCIONISTA;
        try{
            Recepcionista recepcionista = new Recepcionista(nome,telefone,senha,turno, tipoUser);
            recepcionistaDAO.inserirRecepcionista(recepcionista);
            clear();
            mostrarAlerta("Cadastro", "Cadastro realizado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(){
        UsuarioInputNome.clear();
        UsuarioInputSenha.clear();
        UsuarioInputTelefone.clear();
    }

    //Limpa os campos do cadastro
    public void LimparCampos(ActionEvent actionEvent) {
        UsuarioInputNome.clear();
        UsuarioInputSenha.clear();
        UsuarioInputTelefone.clear();
    }

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

    public void voltarParaPainelPrincipal(ActionEvent actionEvent) {
        try{
            Main main = new Main();
            main.abrirPainel("painelCadastros.fxml", "Painel Cadastros");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
