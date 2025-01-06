package com.smartclinical.model;

import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.util.TipoUser;

public class Recepcionista extends Usuario {

    // atributo exclusivo
    private String turno;

    // Construtores

    public Recepcionista(int userId,String nome, String email, String senha, String telefone, String turno, TipoUser tipoUsuario) {
        super(userId, nome, email,senha, telefone, tipoUsuario);
        this.turno = turno;
    }

    public Recepcionista(String nome, String email, String senha, String telefone, String turno, TipoUser tipoUsuario) {
        super(nome, email, senha, telefone, tipoUsuario);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
