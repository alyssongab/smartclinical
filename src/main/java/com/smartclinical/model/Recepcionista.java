package com.smartclinical.model;

import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.util.TipoUser;

public class Recepcionista extends Usuario {

    // atributo exclusivo
    private String turno;

    // Construtores
    public Recepcionista(String nome, String email, String senha, String telefone, TipoUser tipoUsuario, String turno) {
        super(nome, email, senha, telefone, tipoUsuario);
        this.turno = turno;
    }

    public Recepcionista(int userId, String nome, String telefone, TipoUser tipoUsuario, String turno) {
        super(userId, nome, telefone, tipoUsuario);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
