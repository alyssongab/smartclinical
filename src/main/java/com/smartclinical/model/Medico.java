package com.smartclinical.model;

import com.smartclinical.util.TipoUser;

public class Medico extends Usuario {
    private String CRM;
    private String especialidade;

    public Medico(String nome, String telefone,String senha, String CRM, String especialidade, TipoUser tipoUsuario) {
        super(nome, telefone, senha, tipoUsuario);
        this.CRM = CRM;
        this.especialidade = especialidade;
    }


    //listar
    public Medico(int id, String nome, String telefone, String CRM, String especialidade, TipoUser tipoUser) {
        super(id, nome,telefone,tipoUser);
        this.CRM = CRM;
        this.especialidade = especialidade;
    }

    public Medico(int idMedico, String nome, String telefone, String senha, String crm, String especialidade, TipoUser tipoUser) {
        super(idMedico, nome,telefone,senha,tipoUser);
        this.CRM = crm;
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
