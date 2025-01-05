package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Medico extends Usuario {
    private String CRM;
    private String especialidade;
    public Medico(String nome, String email, String senha, String telefone, String CRM, String especialidade,TipoUser tipoUsuario) {
        super(nome, email, senha, telefone, tipoUsuario);
        this.CRM = CRM;
        this.especialidade = especialidade;
    }

    public Medico(int userId, String nome, String telefone,String CRM, String especialidad, TipoUser tipoUsuario) {
        super(userId, nome, telefone, tipoUsuario);
        this.CRM = CRM;
        this.especialidade = especialidade;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
