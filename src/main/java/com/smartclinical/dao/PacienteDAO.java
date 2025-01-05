package com.smartclinical.dao;

import com.smartclinical.model.Paciente;
import com.smartclinical.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacienteDAO {

    public PacienteDAO(){}

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
}
