package com.smartclinical.testes;

import com.smartclinical.dao.AdminDAO;
import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.model.Admin;
import com.smartclinical.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class testeDaoAdmin {
    public static void main(String[] args) throws SQLException {
        AdminDAO adminDAO = new AdminDAO();

        List<Admin> usuarios = adminDAO.listarAdmin();
        for(Admin usuario : usuarios){
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println("Cargo: " + usuario.getTipoUsuario().name());
        }
    }
}
