package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Medico extends Usuario {
    private String crm;
    private String especialidade;

    public Medico(String nome, String email, String senha, String telefone, TipoUser tipoUser, String crm, String especialidade) {
        super(nome, email, senha, telefone, tipoUser);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Medico(int id, String nome, String email, String senha, String telefone, TipoUser tipoUser, String crm, String especialidade) {
        super(id, nome, email, senha, telefone, tipoUser);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String toString() {
        return this.nome;
    }
}
