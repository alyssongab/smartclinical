package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Admin extends Usuario{

    public Admin(String nome, String email, String senha, String telefone, TipoUser tipoUsuario) {
        super(nome, email, senha, telefone, tipoUsuario);
    }

    public Admin(int userId, String nome, String telefone, TipoUser tipoUsuario) {
        super(userId, nome, telefone, tipoUsuario);
    }

    public String toString(){
        return "ADMIN!!!!!!!!!!!";
    }
}
