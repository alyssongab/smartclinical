package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Usuario {
    protected int userId;
    protected String nome;
    protected String email;
    protected String senha;
    protected String telefone;
    protected TipoUser tipoUsuario;

    public Usuario(String nome, String email, String senha, String telefone, TipoUser tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(int userId, String nome, String telefone, TipoUser tipoUsuario) {
        this.userId = userId;
        this.nome = nome;
        this.telefone = telefone;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(int id, String nome, String email, String telefone, TipoUser tipoUsuario) {
    }

    public int getId() {
        return userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoUser getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUser tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}

