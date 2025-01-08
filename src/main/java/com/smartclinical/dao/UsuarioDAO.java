package com.smartclinical.dao;

import com.smartclinical.model.Admin;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Recepcionista;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    //Inserir Admin
    public void inserirAdmin(Usuario usuario) {
        String inserir = "INSERT INTO usuario (nome, email, senha, telefone, tipoUser) VALUES(?,?,?,?,?)";

        // try with resource para conectar com o banco
        try(Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(inserir);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getTipoUsuario().name());

            stmt.executeUpdate();
            System.out.println("SQL: " + inserir);

        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir Admin: " + e.getMessage());
        }
    }

    // Inserir medico
    public void inserirMedico(Medico medico) {
        String inserirUsuario = "INSERT INTO usuario (nome, email, senha, telefone, tipoUser) VALUES(?,?,?,?,?)";
        String inserirMedico = "INSERT INTO medicos (id_medico, crm, especialidade) VALUES (?,?,?)";

        try(Connection conn = ConexaoBD.getConexao()){
            assert conn != null;
            conn.setAutoCommit(false);

            // primeiro insere na tabela usuario
            try(PreparedStatement stmt = conn.prepareStatement(inserirUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, medico.getNome());
                stmt.setString(2, medico.getEmail());
                stmt.setString(3, medico.getSenha());
                stmt.setString(4, medico.getTelefone());
                stmt.setString(5, medico.getTipoUsuario().name());

                stmt.executeUpdate();

                // pega o ID gerado para o usuario acima
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int usuarioId = rs.getInt(1);

                    // insere na tabela medico
                    try(PreparedStatement stmtMedico = conn.prepareStatement(inserirMedico)) {
                        stmtMedico.setInt(1, usuarioId);
                        stmtMedico.setString(2, medico.getCrm());
                        stmtMedico.setString(3, medico.getEspecialidade());

                        stmtMedico.executeUpdate();
                        System.out.println("SQL " + inserirMedico);
                    }
                }
                conn.commit(); // confirmar a transação
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir medico: " + e.getMessage());
        }
    }

    // inserir Recepcionista
    public void inserirRecepcionista(Recepcionista recepcionista) {
        String inserirUsuario = "INSERT INTO usuario (nome, email, senha, telefone, tipoUser) VALUES(?,?,?,?,?)";
        String inserirRecepcionista = "INSERT INTO recepcionistas (id_recepcionista, turno) VALUES (?,?)";

        try(Connection conn = ConexaoBD.getConexao()){
            assert conn != null;
            conn.setAutoCommit(false);

            // primeiro insere na tabela usuario
            try(PreparedStatement stmt = conn.prepareStatement(inserirUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, recepcionista.getNome());
                stmt.setString(2, recepcionista.getEmail());
                stmt.setString(3, recepcionista.getSenha());
                stmt.setString(4, recepcionista.getTelefone());
                stmt.setString(5, recepcionista.getTipoUsuario().name());

                stmt.executeUpdate();

                // pega o ID gerado para o usuario acima
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int usuarioId = rs.getInt(1);

                    // insere na tabela medico
                    try(PreparedStatement stmtRecepcionista = conn.prepareStatement(inserirRecepcionista)) {
                        stmtRecepcionista.setInt(1, usuarioId);
                        stmtRecepcionista.setString(2, recepcionista.getTurno());

                        stmtRecepcionista.executeUpdate();
                        System.out.println("SQL " + inserirRecepcionista);
                    }
                }
                conn.commit(); // confirmar a transação
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir Recepcionista: " + e.getMessage());
        }
    }

    // listar usuários
    public List<Usuario> listarUsuario() {
        String listar = "SELECT id, nome, email, telefone, tipoUser FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

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
                String tipoUsuarioString = rs.getString("tipoUser");
                // convertendo o tipo de usuario para enum
                TipoUser tipoUsuario = TipoUser.valueOf(tipoUsuarioString);

                Usuario usuario = new Usuario(id, nome, telefone, tipoUsuario);
                usuarios.add(usuario);
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    // Remover usuário
    public void removerUsuario(int id) throws SQLException {
        String remover = "DELETE FROM usuario WHERE id = ?";
        try(Connection con = ConexaoBD.getConexao()){
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(remover);

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate(); // executeUpdate retorna um inteiro
            System.out.println("SQL: " + remover);

            if (linhasAfetadas > 0) {
                System.out.println("Operação realizada com sucesso.");
            } else {
                System.out.println("Nenhuma linha foi afetada.");
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao remover usuario: " + e.getMessage());
        }
    }

    // Editar usuário
    public void editarUsuario(Usuario usuario) {

    }

}
