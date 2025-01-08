package com.smartclinical.dao;

import com.smartclinical.model.Admin;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    //Inserir usuário
    public void inserirMedico(Medico medico){
        String inserir = "INSERT INTO medicos (nome, telefone,senha, CRM, especialidade, tipoUser) VALUES(?,?,?,?,?,?)";
        // try with resource para conectar com o banco
        try(Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(inserir);

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getTelefone());
            stmt.setString(3, medico.getSenha());
            stmt.setString(4, medico.getCRM());
            stmt.setString(5, medico.getEspecialidade());
            stmt.setString(6, medico.getTipoUsuario().name());

            stmt.executeUpdate();


        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    // listar usuários
    public List<Medico> listarAdmin() {
        String listar = "SELECT id,nome,telefone,CRM,especialidade,tipoUser FROM medicos";
        Medico medico = null;
        List<Medico> medicos = new ArrayList<>();

        try(Connection con = ConexaoBD.getConexao()){
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(listar);

            ResultSet rs = stmt.executeQuery();
            System.out.println("SQL: " + listar);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String CRM = rs.getString("CRM");
                String especialidade = rs.getString("especialidade");
                String tipoUsuarioString = rs.getString("tipoUser");



                medico = new Medico(id,nome,telefone,CRM,especialidade,TipoUser.MEDICO);
                System.out.println("Nome: " + medico.getNome() + "Email:" + "CRM: " + medico.getCRM() + "especialidade: " + medico.getEspecialidade());
                medicos.add(medico);
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }

        return medicos;
    }


    public Medico getMedico(int idMedico) {
        String selecionar = "SELECT id, nome, telefone,senha, CRM, especialidade, tipoUser FROM medicos WHERE id = ?";
        Medico medico = null;

        try (Connection con = ConexaoBD.getConexao()) {
            if (con == null) {
                throw new SQLException("Conexão com o banco de dados não foi estabelecida.");
            }

            PreparedStatement stmt = con.prepareStatement(selecionar);
            stmt.setInt(1, idMedico);

            ResultSet rs = stmt.executeQuery();

            System.out.println("SQL: " + stmt); // Mostra a consulta com parâmetros

            if (rs.next()) {
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String senha = rs.getString("senha");
                String CRM = rs.getString("CRM");
                String especialidade = rs.getString("especialidade");
                String tipoUsuarioString = rs.getString("tipoUser");

                medico = new Medico(idMedico, nome, telefone,senha, CRM, especialidade, TipoUser.MEDICO);

            } else {
                System.out.println("Nenhum médico encontrado com o ID: " + idMedico);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar médico: " + e.getMessage());
        }

        return medico;
    }


    // Remover usuário
    public void removerMedico(int id) throws SQLException {
        String remover = "DELETE FROM medicos WHERE id = ?";
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

    // Editar medico
    public void editarMedico(Medico medico) {
        String atualizar = "UPDATE medicos SET nome = ?, telefone = ?, CRM = ?, especialidade = ?, tipoUser = ? WHERE id = ?";

        try (Connection con = ConexaoBD.getConexao()) {
            if (con == null) {
                System.out.println("Erro: Não foi possível obter a conexão com o banco de dados.");
                return;
            }

            try (PreparedStatement stmt = con.prepareStatement(atualizar)) {
                // Definir os parâmetros da consulta SQL
                stmt.setString(1, medico.getNome());
                stmt.setString(2, medico.getTelefone());
                stmt.setString(3, medico.getCRM());
                stmt.setString(4, medico.getEspecialidade());
                stmt.setString(5, medico.getTipoUsuario().name());
                if (medico.getTipoUsuario() == null) {
                    System.out.println("Erro: O tipo de usuário não foi definido.");
                    return;
                }

                stmt.setInt(6, medico.getId());


                // Executa a atualização e verifica o número de linhas afetadas
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Admin atualizado com sucesso.");
                } else {
                    System.out.println("Nenhum admin encontrado com o ID fornecido.");
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
