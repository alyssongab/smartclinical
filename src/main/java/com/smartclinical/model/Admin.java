package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Admin extends Usuario{

    //Construtor para inserção
    public Admin(String nome, String email, String telefone, String senha, TipoUser tipoUsuario) {
        super(nome, email, telefone, senha, tipoUsuario);
    }

    //Construtor para listagem
    public Admin(int id, String nome, String email, String telefone, TipoUser tipoUsuario) {
            super(id, nome, email, telefone, tipoUsuario);
    }

    public Admin(int id, String nome, String telefone, TipoUser tipoUsuario) {
        super(id, nome, telefone, tipoUsuario);
    }
}
