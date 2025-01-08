package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.dao.MedicoDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Medico;
import com.smartclinical.util.TipoUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListaMedicoController {
    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Medico> medicoTable;

    @FXML
    private TableColumn<Medico, Integer> medicoId;

    @FXML
    private TableColumn<Medico, String> medicoNome;

    @FXML
    private TableColumn<Medico, String> medicoTelefone;

    @FXML
    private TableColumn<Medico, String> medicoCRM;

    @FXML
    private TableColumn<Medico, String> medicoEspecialidade;


    public void initialize(){
        medicoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        medicoTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        medicoCRM.setCellValueFactory(new PropertyValueFactory<>("CRM"));
        medicoEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
        setupTable();
    }

    private void setupTable() {
        MedicoDAO medicoDAO = new MedicoDAO();
        List<Medico> medicos = medicoDAO.listarAdmin();
        medicoTable.getItems().clear();
        medicoTable.getItems().addAll(medicos);
    }

    @FXML
    private void rowClicked(){
        Medico clickedMedico = medicoTable.getSelectionModel().getSelectedItem();
        showDialog(clickedMedico.getId());
    }

    private void showDialog(int id) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Admin options");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNode(id));  // Passa o dialog para a função createNode
        dialog.showAndWait();
    }

    private Node createNode(int idMedico) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        gridPane.add(label, 0, 0, 2, 1);

        Button editButton = new Button("Editar");
        editButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #53c89b; -fx-text-fill: white;");
        editButton.setOnAction(event -> {
            showDialogEdit(idMedico);
        });

        Button deleteButton = new Button("Excluir");
        deleteButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> {
            MedicoDAO medicoDAO = new MedicoDAO();
            try {
                medicoDAO.removerMedico(idMedico);
                ((Stage) gridPane.getScene().getWindow()).close();
                mostrarAlerta("Excluir admin", "Excluir concluído!");
            } catch (SQLException e) {
                System.err.println("Erro ao excluir o usuário: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        gridPane.add(editButton, 0, 1);
        gridPane.add(deleteButton, 1, 1);

        return gridPane;
    }



    /*----------Dialog pane Editar usuário--------------*/

    private void showDialogEdit(int idMedico) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Editar Administrador");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createEditNode(idMedico));
        dialog.showAndWait();
    }

    private Node createEditNode(int idMedico) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(30));

        MedicoDAO medicoDAO = new MedicoDAO();
        Medico medico = medicoDAO.getMedico(idMedico);


        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label labelNome = new Label("Nome:");
        TextField medicoNome = new TextField(medico.getNome());
        medicoNome.setStyle("-fx-min-width: 200px");

        Label labelTelefone = new Label("Telefone:");
        TextField medicoTelefone = new TextField(medico.getTelefone());
        medicoTelefone.setStyle("-fx-min-width: 200px");


        Label labelCRM = new Label("CRM:");
        TextField medicoCRM = new TextField(medico.getCRM());
        medicoCRM.setStyle("-fx-min-width: 200px");

        Label labelEspecialidade = new Label("Especialidade:");
        TextField medicoEspecialidade = new TextField(medico.getEspecialidade());
        medicoEspecialidade.setStyle("-fx-min-width: 200px");


        Button saveButton = new Button("Salvar");
        saveButton.setStyle("-fx-max-height: 25px; -fx-min-width: 110px; -fx-background-color: #53c89b; -fx-text-fill: white;");
        saveButton.setOnAction(event -> {
            String nome = medicoNome.getText();
            String telefone = medicoTelefone.getText();
            String CRM = medicoCRM.getText();
            String especialidade = medicoEspecialidade.getText();

            medicoDAO.editarMedico(new Medico(idMedico, nome,telefone,CRM,especialidade,TipoUser.MEDICO));
            mostrarAlerta("Edição", "Admin editado com sucesso!");
            ((Stage) gridPane.getScene().getWindow()).close();
        });

        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(labelNome, 0, 2);
        gridPane.add(medicoNome, 0, 3);
        gridPane.add(labelTelefone, 0, 6);
        gridPane.add(medicoTelefone, 0, 7);
        gridPane.add(labelCRM, 0, 8);
        gridPane.add(medicoCRM, 0, 9);
        gridPane.add(labelEspecialidade, 0, 10);
        gridPane.add(medicoEspecialidade, 0, 11);
        gridPane.add(saveButton, 0, 13);
        return gridPane;
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
            main.abrirPainel("painelListaUsuarios.fxml", "Painel Listagem");
        }
        catch(IOException e){
            throw new RuntimeException();
        }
    }

    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
