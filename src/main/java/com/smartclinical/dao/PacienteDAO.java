package com.smartclinical.dao;

import com.smartclinical.model.Paciente;
import com.smartclinical.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // cadastro de paciente
    public void cadastrarPaciente(Paciente paciente) {
        String cadastrar = "INSERT INTO pacientes(nome, cpf, data_nasc) VALUES(?,?,?)";

        try(Connection conn = ConexaoBD.getConexao()){
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(cadastrar);

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getDataNascimento());

            stmt.executeUpdate();
            System.out.println("SQL: " + cadastrar);

        }
        catch(SQLException e){
            System.out.println("Erro ao cadastrar paciente" + e.getMessage());
        }
    }

    // lista pacientes
    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String listar = "SELECT * FROM pacientes ORDER BY nome ASC";

        try(Connection conn = ConexaoBD.getConexao();
            PreparedStatement stmt = conn.prepareStatement(listar);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("data_nasc")
                );
                pacientes.add(paciente);
            }

        }
        catch(SQLException e) {
            System.out.println("Erro ao listar pacientes" + e.getMessage());
        }

        return pacientes;
    }
}
