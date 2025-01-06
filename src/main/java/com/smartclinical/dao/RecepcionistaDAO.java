package com.smartclinical.dao;

import com.smartclinical.model.Medico;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecepcionistaDAO {
    //Inserir usuário
    public void inserirAdmin(Recepcionista admin){
        String inserir = "INSERT INTO recepcionistas (nome, email, telefone,senha,turno, tipoUser) VALUES(?,?,?,?,?,?)";
        // try with resource para conectar com o banco
        try(Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(inserir);

            stmt.setString(1, admin.getNome());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getTelefone());
            stmt.setString(4, admin.getSenha());
            stmt.setString(5, admin.getTurno());
            stmt.setString(6, admin.getTipoUsuario().name());

            stmt.executeUpdate();
            System.out.println("SQL: INSERT INTO admins (nome, email, senha, telefone, tipoUser) VALUES (" + admin.getNome() + ", " + admin.getEmail() + ", " + admin.getSenha() + ", " + admin.getTelefone() + ", " + admin.getTipoUsuario().name() + ")");

        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    // listar usuários
    public List<Recepcionista> listarAdmin() {
        String listar = "SELECT id,nome, email, telefone,senha,turno, tipoUser FROM recepcionistas";
        List<Recepcionista> admins = new ArrayList<>();

        try(Connection con = ConexaoBD.getConexao()){
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(listar);

            ResultSet rs = stmt.executeQuery();
            System.out.println("SQL: " + listar);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                String senha = rs.getString("senha");
                String turno = rs.getString("turno");
                String tipoUsuarioString = rs.getString("tipoUser");
                // convertendo o tipo de usuario para enum
                TipoUser tipoUsuario = TipoUser.valueOf(tipoUsuarioString);

                Recepcionista admin = new Recepcionista(id,nome, email, telefone,senha,turno, TipoUser.RECEPCIONISTA);
                admins.add(admin);
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }

        return admins;
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

    // Editar usuário
    public void editarAdmin(Medico admin) {
        String atualizar = "UPDATE admins SET nome = ?, senha = ?, telefone = ?, tipoUser = ? WHERE id = ?";

        try (Connection con = ConexaoBD.getConexao()) {
            // Verifica se a conexão foi bem-sucedida
            if (con == null) {
                System.out.println("Erro: Não foi possível obter a conexão com o banco de dados.");
                return;
            }

            // Prepara a instrução SQL
            try (PreparedStatement stmt = con.prepareStatement(atualizar)) {
                // Definir os parâmetros da consulta SQL
                stmt.setString(1, admin.getNome());
                stmt.setString(2, admin.getSenha());
                stmt.setString(3, admin.getTelefone());

                // Verifica se o tipo de usuário é válido (não nulo)
                if (admin.getTipoUsuario() == null) {
                    System.out.println("Erro: O tipo de usuário não foi definido.");
                    return; // Impede a execução da query se o tipo de usuário for nulo
                }
                stmt.setString(4, admin.getTipoUsuario().name()); // Converte Enum para String
                stmt.setInt(5, admin.getId());

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
            } catch (Exception e) {
                // Captura erros genéricos, caso ocorram
                System.out.println("Erro inesperado durante a execução.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // Captura erros relacionados à conexão com o banco de dados
            System.out.println("Erro ao tentar obter conexão com o banco de dados.");
            e.printStackTrace();
        } catch (Exception e) {
            // Captura outros tipos de exceções gerais que podem ocorrer
            System.out.println("Erro inesperado.");
            e.printStackTrace();
        }
    }
}

