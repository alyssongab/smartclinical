package com.smartclinical.dao;

import com.smartclinical.model.Admin;
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

    public Paciente getPaciente(int idPaciente) {
        String selecionar = "SELECT id, nome, cpf, dataNascimento FROM pacientes WHERE id = ?";
        Paciente paciente = null;

        try (Connection con = ConexaoBD.getConexao()) {
            if (con == null) {
                throw new SQLException("Conexão com o banco de dados não foi estabelecida.");
            }

            PreparedStatement stmt = con.prepareStatement(selecionar);
            stmt.setInt(1, idPaciente);

            ResultSet rs = stmt.executeQuery();

            System.out.println("SQL: " + stmt); // Mostra a consulta com parâmetros

            if (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String dataNascimento = rs.getString("dataNascimento");

                paciente = new Paciente(idPaciente,nome,cpf,dataNascimento); // Instancia Admin somente se houver resultado

            } else {
                System.out.println("Nenhum administrador encontrado com o ID: " + idPaciente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar usuário: " + e.getMessage());
        }

        return paciente;
    }

    // Remover usuário
    public void removerPaciente(int id) throws SQLException {
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

    // Editar usuário
    public void editarPaciente(Paciente paciente) {
        String atualizar = "UPDATE pacientes SET nome = ?, cpf = ?, dataNascimento = ? WHERE id = ?";

        try (Connection con = ConexaoBD.getConexao()) {
            if (con == null) {
                System.out.println("Erro: Não foi possível obter a conexão com o banco de dados.");
                return;
            }

            try (PreparedStatement stmt = con.prepareStatement(atualizar)) {
                // Definir os parâmetros da consulta SQL
                stmt.setString(1, paciente.getNome());
                stmt.setString(2, paciente.getCpf());
                stmt.setString(3, paciente.getDataNascimento());
                stmt.setInt(4, paciente.getId());

                // Executa a atualização e verifica o número de linhas afetadas
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Admin atualizado com sucesso.");
                } else {
                    System.out.println("Nenhum paciente encontrado com o ID fornecido.");
                }
            } catch (SQLException e) {
                // Captura erros específicos do SQL e imprime a mensagem de erro
                System.out.println("Erro ao executar a consulta SQL.");
                e.printStackTrace();  // Detalha o stack trace completo para debugging
            }
        } catch (SQLException e) {
            // Captura erros relacionados à conexão com o banco de dados
            System.out.println("Erro ao tentar obter conexão com o banco de dados.");
            e.printStackTrace();
        }
    }

}
