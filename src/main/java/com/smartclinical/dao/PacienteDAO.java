package com.smartclinical.dao;

import com.smartclinical.model.Medico;
import com.smartclinical.model.Paciente;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // cadastro de paciente
    public void cadastrarPaciente(Paciente paciente) {
        String cadastrar = "INSERT INTO pacientes(nome, cpf, dataNascimento) VALUES(?,?,?)";

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

    // listar usuários
    public List<Paciente> listarAdmin() {
        String listar = "SELECT id, nome, cpf, dataNascimento FROM pacientes";
        List<Paciente> medicos = new ArrayList<>();

        try(Connection con = ConexaoBD.getConexao()){
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(listar);

            ResultSet rs = stmt.executeQuery();
            System.out.println("SQL: " + listar);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String dataNas = rs.getString("dataNascimento");

                Paciente paciente = new Paciente(id, nome,cpf,dataNas);
                medicos.add(paciente);
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }

        return medicos;
    }

    // Remover usuário
    public void removerMedico(int id) throws SQLException {
        String remover = "DELETE FROM pacientes WHERE id = ?";
        try (Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(remover);

            stmt.setInt(1, id);  // Define o ID do usuário a ser removido
            int linhasAfetadas = stmt.executeUpdate(); // executeUpdate retorna o número de linhas afetadas

            System.out.println("SQL Executado: " + remover);

            if (linhasAfetadas > 0) {
                // Caso a exclusão tenha sido bem-sucedida
                System.out.println("Usuário com ID " + id + " removido com sucesso.");
            } else {
                // Caso não haja usuário com o ID fornecido
                System.out.println("Nenhum usuário encontrado com o ID: " + id);
            }
        } catch (SQLException e) {
            // Melhorando a mensagem de erro para incluir detalhes sobre o ID que causou o erro
            System.err.println("Erro ao tentar remover o usuário com ID " + id + ": " + e.getMessage());
            throw new SQLException("Erro ao remover usuário.", e);
        }
    }

}
