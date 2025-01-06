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

        List<Admin> admins = adminDAO.listarAdmin();
        for(Admin admin : admins){
            System.out.println("ID: " + admin.getId());
            System.out.println("TESTE - Nome: " + admin.getNome());
            System.out.println("Telefone: " + admin.getTelefone());
            System.out.println("Cargo: " + admin.getTipoUsuario().name());
        }
    }
}
