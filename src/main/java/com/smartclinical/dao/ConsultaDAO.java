package com.smartclinical.dao;

import com.smartclinical.model.Consulta;
import com.smartclinical.model.Medico;
import com.smartclinical.model.Paciente;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Consulta> listarConsultas(){
        List<Consulta> consultas = new ArrayList<>();
        String query = "SELECT * FROM consultas";

        try(Connection conn = ConexaoBD.getConexao();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int idConsulta = rs.getInt("id_consulta");
                String data_hora = rs.getString("data_hora");
                int paciente_id = rs.getInt("paciente_id");
                int medico_id = rs.getInt("medico_id");

                // obter os objetos Paciente e Medico para criar a Consulta
                // Recupera o paciente e medico completo com base no ID
                Paciente paciente = getPacienteById(paciente_id);
                Medico medico = getMedicoById(medico_id);

                // Cria a consulta com os objetos
                Consulta consulta = new Consulta(idConsulta, data_hora, paciente, medico);
                consultas.add(consulta);
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        }
        return consultas;
    }

    // Criar paciente através do Id
    private Paciente getPacienteById(int pacienteId) throws SQLException {
        String queryPaciente = "SELECT * FROM pacientes WHERE id_paciente = ?";
        try (Connection conn = ConexaoBD.getConexao();
            PreparedStatement st = conn.prepareStatement(queryPaciente)) {

            st.setInt(1, pacienteId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                // Cria um paciente a partir dos dados da query
                return new Paciente(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }
            return null;
        }
    }

    // Criar médico através do Id
    private Medico getMedicoById(int medicoId) throws SQLException {
        String queryMedico = "SELECT u.*, m.crm, m.especialidade FROM usuario u " +
                            "JOIN medicos m ON u.id = m.id_medico WHERE u.id = ?";

        try (Connection conn = ConexaoBD.getConexao();
        PreparedStatement st = conn.prepareStatement(queryMedico)) {

            st.setInt(1, medicoId);

            ResultSet rsMedico = st.executeQuery();
            if (rsMedico.next()) {
                // Cria um medico a partir dos dados da query
                return new Medico(
                        rsMedico.getInt("id"),
                        rsMedico.getString("nome"),
                        rsMedico.getString("email"),
                        rsMedico.getString("senha"),
                        rsMedico.getString("telefone"),
                        TipoUser.valueOf(rsMedico.getString("tipoUser")),
                        rsMedico.getString("crm"),
                        rsMedico.getString("especialidade")
                );
            }
            return null;
        }
    }
}
