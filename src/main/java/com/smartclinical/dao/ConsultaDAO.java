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
            String criacao = "INSERT INTO consultas(data_hora, paciente_id, medico_id, valor) VALUES(?,?,?,?)";

            try (Connection conn = ConexaoBD.getConexao()) {
                PreparedStatement st = conn.prepareStatement(criacao);
                st.setString(1, consulta.getData_hora());
                st.setInt(2, consulta.getPaciente().getId());
                st.setInt(3, consulta.getMedico().getId());
                st.setDouble(4, consulta.getValor());

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
                double valor = rs.getDouble("valor");

                // obter os objetos Paciente e Medico para "criar" a Consulta para listagem
                // Recupera o paciente e medico completo com base no ID
                Paciente paciente = getPacienteById(paciente_id);
                Medico medico = getMedicoById(medico_id);

                // Cria a consulta com os objetos
                Consulta consulta = new Consulta(idConsulta, data_hora, paciente, medico, valor);
                consultas.add(consulta);
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        }
        return consultas;
    }

    public List<Consulta> listarConsultasProntuario(int medicoId) {
        List<Consulta> consultas = new ArrayList<>();
        MedicoDAO medicoDAO = new MedicoDAO();
        Medico medico = medicoDAO.getMedico(medicoId);

        String query = "SELECT * FROM consultas WHERE medico_id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, medico.getId());

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id_consulta = rs.getInt("id_consulta");
                    String data_hora = rs.getString("data_hora");
                    int paciente_id = rs.getInt("paciente_id");

                    Paciente paciente = getPacienteById(paciente_id);
                    Consulta consulta = new Consulta(id_consulta,data_hora, paciente, medico);
                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
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
                        rs.getLong(3),
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

    // remocao de consulta para remover paciente
    public void removerConsultasPorPaciente(int pacienteId) {
        String sql = "DELETE FROM consultas WHERE paciente_id = ?";

        try (Connection con = ConexaoBD.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pacienteId);
            stmt.executeUpdate();
            System.out.println("Consultas removidas com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao remover consultas do paciente: " + e.getMessage());
        }
    }

    public boolean removerConsultaPorId(int consultaId) {
        String sql = "DELETE FROM consultas WHERE id_consulta = ?";

        try (Connection con = ConexaoBD.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, consultaId); // Definir o ID da consulta a ser removida
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Consulta removida com sucesso");
                return true;
            } else {
                System.out.println("Consulta não encontrada");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover consulta: " + e.getMessage());
            return false;
        }
    }

    // Remover consultas associadas a um usuário
    public void removerConsultasPorUsuario(int usuarioId) {
        String removerConsultas = "DELETE FROM consultas WHERE medico_id = ?";

        try (Connection con = ConexaoBD.getConexao()) {
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(removerConsultas);

            // Configurar o parâmetro do usuário
            stmt.setInt(1, usuarioId);

            // Executar a remoção
            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("SQL: " + removerConsultas);

            if (linhasAfetadas > 0) {
                System.out.println("Consultas removidas com sucesso.");
            } else {
                System.out.println("Nenhuma consulta encontrada para o usuário com ID: " + usuarioId);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover consultas do usuário: " + e.getMessage());
        }
    }

}
