package com.smartclinical.model;

import com.smartclinical.dao.PacienteDAO;
import com.smartclinical.util.TipoUser;

public class Recepcionista extends Usuario {

    // atributo exclusivo
    private String turno;

    // Construtores

    public Recepcionista(int userId,String nome, String telefone, String senha, String turno, TipoUser tipoUsuario) {
        super(userId, nome, telefone,senha, tipoUsuario);
        this.turno = turno;
    }

    public Recepcionista(String nome, String senha, String telefone, String turno, TipoUser tipoUsuario) {
        super(nome, senha, telefone, tipoUsuario);
        this.turno = turno;
    }

    public Recepcionista(int userId, String nome, String email, String telefone,TipoUser tipoUsuario, String turno) {
        super(userId,nome,email,telefone,tipoUsuario);
        this.turno = turno;
    }

    //editar
    public Recepcionista(int idRecepcionista, String nome, String telefone, String turno, TipoUser tipoUser) {
        super(idRecepcionista,nome,telefone,tipoUser);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
