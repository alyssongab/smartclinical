package com.smartclinical.model;

public class Avaliacao {
    private int id;
    private String notaAtendimento;
    private String notaConsulta;
    private String descricao;

    public Avaliacao(int id, String notaAtendimento, String notaConsulta, String descricao) {
        this.id = id;
        this.notaAtendimento = notaAtendimento;
        this.notaConsulta = notaConsulta;
        this.descricao = descricao;
    }

    public Avaliacao(String notaAtendimento, String notaConsulta, String descricao) {
        this.notaAtendimento = notaAtendimento;
        this.notaConsulta = notaConsulta;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotaAtendimento() {
        return notaAtendimento;
    }

    public void setNotAtendimento(String nota_atendimento) {
        this.notaAtendimento = nota_atendimento;
    }

    public String getNotaConsulta() {
        return notaConsulta;
    }

    public void setNota_consulta(String nota_consulta) {
        this.notaConsulta = nota_consulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}