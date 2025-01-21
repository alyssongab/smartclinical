package com.smartclinical.dao;

import com.smartclinical.model.Consulta;
import com.smartclinical.model.Paciente;
import com.smartclinical.model.Prontuario;
import com.smartclinical.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDAO {

    // Método para cadastrar um prontuário
    public boolean cadastrarProntuario(int consultaId, String descricao) {
        String sql = "INSERT INTO prontuarios (consulta_id, descricao) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consultaId);
            stmt.setString(2, descricao);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;  // Retorna true se o registro foi inserido
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar prontuário: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todos os prontuários
    public List<Prontuario> listarProntuarios(int pacienteId) {
        List<Prontuario> prontuarios = new ArrayList<>();
        String sql = "select c.*, p.* from consultas c inner join prontuarios p on c.id_consulta = p.consulta_id where c.paciente_id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, pacienteId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id_prontuario = rs.getInt("id_prontuario");
                    int consulta_id = rs.getInt("consulta_id");
                    String descricao = rs.getString("descricao");

                    Prontuario prontuario = new Prontuario(id_prontuario, consulta_id, descricao);
                    prontuarios.add(prontuario);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        }
        return prontuarios;
    }
}