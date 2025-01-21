package com.smartclinical.model;

public class Prontuario {
    private int prontuarioId;
    private int consultaId;
    private String descricao;

    public Prontuario(int prontuarioId, int consultaId, String descricao) {
        this.prontuarioId = prontuarioId;
        this.consultaId = consultaId;
        this.descricao = descricao;
    }

    public Prontuario(int consultaId, String descricao) {
        this.consultaId = consultaId;
        this.descricao = descricao;
    }

    public int getProntuarioId() {
        return prontuarioId;
    }

    public void setProntuarioId(int prontuarioId) {
        this.prontuarioId = prontuarioId;
    }

    public int getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
