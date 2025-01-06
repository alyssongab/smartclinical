package com.smartclinical.dao;

import com.smartclinical.model.Consulta;
import com.smartclinical.util.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaDAO {

    // Função para verificar se o paciente existe no banco
    private boolean pacienteExiste(int pacienteId) throws SQLException {
        String query = "SELECT 1 FROM pacientes WHERE id_paciente = ?";
        try (Connection conn = ConexaoBD.getConexao()) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, pacienteId); // ID do paciente
            ResultSet rs = st.executeQuery();
            return rs.next();  // Retorna true se o paciente existe
        }
    }

    // Função para verificar se o médico existe no banco
    private boolean medicoExiste(int medicoId) throws SQLException {
        String query = "SELECT 1 FROM medicos WHERE id_medico = ?";
        try (Connection conn = ConexaoBD.getConexao()) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, medicoId);
            ResultSet rs = st.executeQuery();
            return rs.next();  // Retorna true se um registro for encontrado
        }
    }

    // Criar consulta
    public boolean criarConsulta(Consulta consulta) {
        try {
            // Verificar se o paciente existe
            if (!pacienteExiste(consulta.getPaciente().getId())) {
                System.out.println("Paciente não encontrado!");
                return false;
            }

            // Verificar se o médico existe
            if (!medicoExiste(consulta.getMedico().getId())) {
                System.out.println("Médico não encontrado!");
                return false;
            }

            // Se ambos existem, realizar a inserção da consulta
            String criacao = "INSERT INTO consultas(data_hora, paciente_id, medico_id) VALUES(?,?,?)";

            try (Connection conn = ConexaoBD.getConexao()) {
                PreparedStatement st = conn.prepareStatement(criacao);
                st.setString(1, consulta.getData_hora());
                st.setInt(2, consulta.getPaciente().getId());
                st.setInt(3, consulta.getMedico().getId());

                st.executeUpdate();
                System.out.println("Consulta criada com sucesso");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao criar consulta: " + e.getMessage());
            return false;
        }
    }
}
