package com.smartclinical.dao;

import com.smartclinical.model.Medico;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import javax.sound.midi.Receiver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecepcionistaDAO {
    //Inserir usuário
    public void inserirRecepcionista(Recepcionista admin){
        String inserir = "INSERT INTO recepcionistas (nome, telefone,senha,turno, tipoUser) VALUES(?,?,?,?,?)";
        // try with resource para conectar com o banco
        try(Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(inserir);

            stmt.setString(1, admin.getNome());
            stmt.setString(2, admin.getTelefone());
            stmt.setString(3, admin.getSenha());
            stmt.setString(4, admin.getTurno());
            stmt.setString(5, admin.getTipoUsuario().name());

            stmt.executeUpdate();
            System.out.println("SQL: INSERT INTO admins (nome, email, senha, telefone, tipoUser) VALUES (" + admin.getNome() + ", " + admin.getSenha() + ", " + admin.getTelefone() + ", " + admin.getTipoUsuario().name() + ")");

        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    // listar usuários
    public List<Recepcionista> listarRecepcionista() {
        String listar = "SELECT id,nome,telefone,senha,turno, tipoUser FROM recepcionistas";
        List<Recepcionista> admins = new ArrayList<>();

        try(Connection con = ConexaoBD.getConexao()){
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(listar);

            ResultSet rs = stmt.executeQuery();
            System.out.println("SQL: " + listar);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String senha = rs.getString("senha");
                String turno = rs.getString("turno");
                String tipoUsuarioString = rs.getString("tipoUser");

                Recepcionista admin = new Recepcionista(id,nome,telefone,senha,turno, TipoUser.RECEPCIONISTA);
                admins.add(admin);
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }

        return admins;
    }


    public Recepcionista getRecepcionista(int idRecepcionista) {
        String selecionar = "SELECT id, nome, telefone, turno FROM recepcionistas WHERE id = ?";
        Recepcionista recepcionista = null;

        try (Connection con = ConexaoBD.getConexao()) {
            if (con == null) {
                throw new SQLException("Conexão com o banco de dados não foi estabelecida.");
            }

            PreparedStatement stmt = con.prepareStatement(selecionar);
            stmt.setInt(1, idRecepcionista);

            ResultSet rs = stmt.executeQuery();

            System.out.println("SQL: " + stmt); // Mostra a consulta com parâmetros

            if (rs.next()) {
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String turno = rs.getString("turno");

                recepcionista = new Recepcionista(idRecepcionista,nome,telefone,turno, TipoUser.RECEPCIONISTA);

            } else {
                System.out.println("Nenhum médico encontrado com o ID: " + idRecepcionista);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar médico: " + e.getMessage());
        }

        return recepcionista;
    }


    // Remover usuário
    public void removerUsuario(int id) throws SQLException {
        String remover = "DELETE FROM recepcionistas WHERE id = ?";
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


    // Editar Recepcionista
    public void editarRecepcionista(Recepcionista recepcionista) {
        String atualizar = "UPDATE recepcionistas SET nome = ?, telefone = ?, turno = ?, tipoUser = ? WHERE id = ?";

        try (Connection con = ConexaoBD.getConexao()) {
            if (con == null) {
                System.out.println("Erro: Não foi possível obter a conexão com o banco de dados.");
                return;
            }

            try (PreparedStatement stmt = con.prepareStatement(atualizar)) {
                // Definir os parâmetros da consulta SQL
                stmt.setString(1, recepcionista.getNome());
                stmt.setString(2, recepcionista.getTelefone());
                stmt.setString(3, recepcionista.getTurno());
                stmt.setString(4, recepcionista.getTipoUsuario().name());

                if (recepcionista.getTipoUsuario() == null) {
                    System.out.println("Erro: O tipo de usuário não foi definido.");
                    return;
                }

                stmt.setInt(5, recepcionista.getId());


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

