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

    public Recepcionista(String nome, String email, String senha, String telefone, TipoUser tipoUsuario) {
        super(nome, email, senha, telefone, tipoUsuario);
    }

    public void cadastrarPaciente(Paciente paciente) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        pacienteDAO.cadastrarPaciente(paciente);
    }

    public String toString(){
        return this.getNome();
    }
}
