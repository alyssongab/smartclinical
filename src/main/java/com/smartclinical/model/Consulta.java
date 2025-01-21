package com.smartclinical.model;

public class Consulta {
    private int idConsulta;
    private String data_hora;
    private Paciente paciente;
    private Medico medico;
    private String observacao;
    private double valor;

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

    public Consulta(int idConsulta, String data, Paciente paciente, Medico medico) {
        this.idConsulta = idConsulta;
        this.data_hora = data;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Consulta(String data, Paciente paciente, Medico medico, Double valor) {
        this.data_hora = data;
        this.paciente = paciente;
        this.medico = medico;
        this.valor = valor;
    }

    public Consulta(int idCsonulta, String data, Paciente paciente, Medico medico, Double valor) {
        this.idConsulta = idCsonulta;
        this.data_hora = data;
        this.paciente = paciente;
        this.medico = medico;
        this.valor = valor;
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

    public int getPacienteId() { return paciente.getId(); }

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

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    // Métodos para acessar as propriedades dos objetos Paciente e Medico
    public String getNomePaciente() {
        return paciente != null ? paciente.getNome() : "Paciente não encontrado";
    }

    public String getNomeMedico() {
        return medico != null ? medico.getNome() : "Médico não encontrado";
    }

    public String toString() {
        return "Nome do paciente: " + getNomePaciente() + '\n'
                + "Data: " + data_hora + '\n'
                + "Medico: " + getNomeMedico() + '\n';
    }
}
