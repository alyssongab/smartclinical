package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Usuario {
    protected int userId;
    protected String nome;
    protected String senha;
    protected String telefone;
    protected TipoUser tipoUsuario;

    public Usuario() {}

    public Usuario(String nome,String telefone,String senha, TipoUser tipoUsuario) {
        this.nome = nome;
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

    public Usuario(int id, String nome, String telefone,String senha, TipoUser tipoUsuario) {
        this.userId = id;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

