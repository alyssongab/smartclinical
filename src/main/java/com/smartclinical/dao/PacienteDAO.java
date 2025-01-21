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
            stmt.setLong(2, paciente.getCpf());
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
                        rs.getLong("cpf"),
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


    public Paciente getPaciente(int idPaciente) {
        String selecionar = "SELECT id_paciente, nome, cpf, data_nasc FROM pacientes WHERE id_paciente = ?";
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
                String dataNascimento = rs.getString("data_nasc");

                paciente = new Paciente(idPaciente,nome,Long.parseLong(cpf),dataNascimento); // Instancia paciente somente se houver resultado

            } else {
                System.out.println("Nenhum paciente encontrado com o ID: " + idPaciente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar paciente: " + e.getMessage());
        }

        return paciente;
    }

    // editar pacientes
    public void alterarPaciente(Paciente paciente) {
        // Comando SQL para atualizar os dados do paciente
        String alterar = "UPDATE pacientes SET nome = ?, cpf = ?, data_nasc = ? WHERE id_paciente = ?";

        try (Connection conn = ConexaoBD.getConexao()) {
            assert conn != null;

            // Prepara o statement com o comando SQL
            PreparedStatement stmt = conn.prepareStatement(alterar);

            // Seta os valores dos parâmetros
            stmt.setString(1, paciente.getNome());
            stmt.setLong(2, paciente.getCpf());
            stmt.setString(3, paciente.getDataNascimento());
            stmt.setInt(4, paciente.getId()); // Usando o ID do paciente para identificar qual registro atualizar

            // Executa a atualização no banco de dados
            int rowsAffected = stmt.executeUpdate();

            // Se nenhuma linha foi afetada, pode indicar que o paciente não foi encontrado
            if (rowsAffected > 0) {
                System.out.println("Paciente atualizado com sucesso.");
            } else {
                System.out.println("Nenhum paciente encontrado com o ID fornecido.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao alterar paciente: " + e.getMessage());
        }
    }

    // deletar paciente
    public void removerPaciente(int idPaciente) {
        // Comando SQL para excluir o paciente com o ID fornecido
        String remover = "DELETE FROM pacientes WHERE id_paciente = ?";

        try (Connection conn = ConexaoBD.getConexao()) {
            assert conn != null;

            // Prepara o statement com o comando SQL
            PreparedStatement stmt = conn.prepareStatement(remover);

            // Seta o valor do parâmetro (ID do paciente)
            stmt.setInt(1, idPaciente);

            // Executa a remoção no banco de dados
            int rowsAffected = stmt.executeUpdate();

            // Se nenhuma linha foi afetada, pode indicar que o paciente não foi encontrado
            if (rowsAffected > 0) {
                System.out.println("Paciente removido com sucesso.");
            } else {
                System.out.println("Nenhum paciente encontrado com o ID fornecido.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover paciente: " + e.getMessage());
        }
    }


}
