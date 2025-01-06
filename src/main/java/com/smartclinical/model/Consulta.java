package com.smartclinical.model;

public class Consulta {
    private int idConsulta;
    private String data_hora;
    private Paciente paciente;
    private Medico medico;
    private String observacao;

    public Consulta(String data, Paciente paciente, Medico medico, String observacao) {
        this.data_hora = data;
        this.paciente = paciente;
        this.medico = medico;
        this.observacao = observacao;
    }

    public Consulta(String data, Paciente paciente, Medico medico) {
        this.data_hora = data;
        this.paciente = paciente;
        this.medico = medico;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getData_hora() {
        return data_hora;
    }

    public void setData_hora(String data_hora) {
        this.data_hora = data_hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String toString() {
        return "Nome do paciente: " + paciente.getNome() + '\n'
                + "Data: " + data_hora + '\n'
                + "Medico: " + medico + '\n';
    }
}
