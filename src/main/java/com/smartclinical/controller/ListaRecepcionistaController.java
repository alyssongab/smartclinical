package com.smartclinical.controller;

import com.smartclinical.app.Main;
import com.smartclinical.dao.AdminDAO;
import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.dao.RecepcionistaDAO;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Recepcionista;
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

public class ListaRecepcionistaController {
    @FXML
    private Button botaoLogout;

    @FXML
    private TableView<Recepcionista> recepcionistaTable;

    @FXML
    private TableColumn<Recepcionista, Integer> recepcionistaId;

    @FXML
    private TableColumn<Recepcionista, String> recepcionistaNome;

    @FXML
    private TableColumn<Recepcionista, String> recepcionistaTelefone;

    @FXML
    private TableColumn<Recepcionista, String> recepcionistaTurno;


    public void initialize(){
        recepcionistaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        recepcionistaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        recepcionistaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        recepcionistaTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        setupTable();
    }

    private void setupTable() {
        RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();
        List<Recepcionista> medicos = recepcionistaDAO.listarRecepcionista();
        recepcionistaTable.getItems().clear();
        recepcionistaTable.getItems().addAll(medicos);
    }

    @FXML
    private void rowClicked(){
        Recepcionista clickedRecepcionista = recepcionistaTable.getSelectionModel().getSelectedItem();
        showDialog(clickedRecepcionista.getId());
    }

    private void showDialog(int id) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Admin options");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNode(id, dialog));  // Passa o dialog para a função createNode
        dialog.showAndWait();
    }

    private Node createNode(int idRecepcionista, Dialog<Void> dialog) {
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
            showDialogEdit(idRecepcionista);
        });

        Button deleteButton = new Button("Excluir");
        deleteButton.setStyle("-fx-max-height: 25px;-fx-min-width: 110px;-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> {
            RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();
            try {
                recepcionistaDAO.removerUsuario(idRecepcionista);
                dialog.close();
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

    private Node createEditNode(int idRecepcionista) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(30));

        RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();
        Recepcionista recepcionista = recepcionistaDAO.getRecepcionista(idRecepcionista);

        Label label = new Label("Escolha uma ação para o administrador:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label labelNome = new Label("Nome:");
        TextField recepcionistaNome = new TextField(recepcionista.getNome());
        recepcionistaNome.setStyle("-fx-min-width: 200px");

        Label labelTelefone = new Label("Telefone:");
        TextField recepcionistaTelefone = new TextField(recepcionista.getTelefone());
        recepcionistaTelefone.setStyle("-fx-min-width: 200px");

        Label labelTurno = new Label("Turno:");
        TextField recepcionistaTurno = new TextField(recepcionista.getTurno());
        recepcionistaTurno.setStyle("-fx-min-width: 200px");


        Button saveButton = new Button("Salvar");
        saveButton.setStyle("-fx-max-height: 25px; -fx-min-width: 110px; -fx-background-color: #53c89b; -fx-text-fill: white;");
        saveButton.setOnAction(event -> {
            String nome = recepcionistaNome.getText();
            String telefone = recepcionistaTelefone.getText();
            String turno = recepcionistaTurno.getText();


            Recepcionista recepcionistaEdit = new Recepcionista(idRecepcionista,nome,telefone,turno, TipoUser.RECEPCIONISTA);
            recepcionistaDAO.editarRecepcionista(recepcionistaEdit);
            mostrarAlerta("Edição", "Recepcionista editado com sucesso!");
            ((Stage) gridPane.getScene().getWindow()).close();
        });

        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(labelNome, 0, 2);
        gridPane.add(recepcionistaNome, 0, 3);
        gridPane.add(labelTelefone, 0, 6);
        gridPane.add(recepcionistaTelefone, 0, 7);
        gridPane.add(labelTurno, 0, 8);
        gridPane.add(recepcionistaTurno, 0, 9);
        gridPane.add(saveButton, 0, 11);
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

