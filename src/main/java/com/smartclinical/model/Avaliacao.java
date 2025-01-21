package com.smartclinical.model;

public class Avaliacao {
    private int id;
    private String nota_atendimento;
    private String nota_consulta;
    private String descricao;

    public Avaliacao(int id, String nota_atendimento, String nota_consulta, String descricao) {
        this.id = id;
        this.nota_atendimento = nota_atendimento;
        this.nota_consulta = nota_consulta;
        this.descricao = descricao;
    }

    public Avaliacao(String nota_atendimento, String nota_consulta, String descricao) {
        this.nota_atendimento = nota_atendimento;
        this.nota_consulta = nota_consulta;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotaAtendimento() {
        return nota_atendimento;
    }

    public void setNotAtendimento(String nota_atendimento) {
        this.nota_atendimento = nota_atendimento;
    }

    public String getNotaConsulta() {
        return nota_consulta;
    }

    public void setNota_consulta(String nota_consulta) {
        this.nota_consulta = nota_consulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
