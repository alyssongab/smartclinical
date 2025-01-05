package com.smartclinical.dao;

import com.smartclinical.model.Admin;
import com.smartclinical.model.Usuario;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    //Inserir usuário
    public void inserirAdmin(Admin admin){
        String inserir = "INSERT INTO admins (nome, email, senha, telefone, tipoUser) VALUES(?,?,?,?.?)";

        // try with resource para conectar com o banco
        try(Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(inserir);

            stmt.setString(1, admin.getNome());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getSenha());
            stmt.setString(4, admin.getTelefone());
            stmt.setString(5, admin.getTipoUsuario().name());

            stmt.executeUpdate();
            System.out.println("SQL: " + inserir);

        }
        catch (SQLException e) {
            System.out.println("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    // listar usuários
    public List<Admin> listarAdmin() {
        String listar = "SELECT id, nome, email, telefone, tipoUser FROM admins";
        List<Admin> admins = new ArrayList<>();

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

                Admin admin = new Admin(id, nome, email, telefone, tipoUsuario);
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
