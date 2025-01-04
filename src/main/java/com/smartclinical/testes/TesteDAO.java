package com.smartclinical.testes;


import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class TesteDAO {
    public static void main(String[] args) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        List<Usuario> usuarios = usuarioDAO.listarUsuario();
        for(Usuario usuario : usuarios){
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println("Cargo: " + usuario.getTipoUsuario().name());
        }
    }
}

