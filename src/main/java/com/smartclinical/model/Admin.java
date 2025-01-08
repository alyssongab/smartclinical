package com.smartclinical.model;

import com.smartclinical.dao.UsuarioDAO;
import com.smartclinical.util.TipoUser;

public class Admin extends Usuario{

    public Admin(String nome, String email, String senha, String telefone, TipoUser tipoUsuario) {
        super(nome, email, senha, telefone, tipoUsuario);
    }

    public Admin(int userId, String nome, String telefone, TipoUser tipoUsuario) {
        super(userId, nome, telefone, tipoUsuario);
    }

    public void cadastrarAdmin(Admin admin) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserirAdmin(admin);
    }

    public void cadastrarMedico(Medico medico) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserirMedico(medico);
    }

    public void cadastrarRecepcionista(Recepcionista recepcionista) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserirRecepcionista(recepcionista);
    }

    public String toString(){
        return this.getNome();
    }
}
