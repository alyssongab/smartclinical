package com.smartclinical.dao;

import com.smartclinical.model.Medico;
import com.smartclinical.util.ConexaoBD;
import com.smartclinical.util.TipoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public List<Medico> listarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.email, u.senha, u.telefone, u.tipoUser, m.crm, m.especialidade FROM usuario u JOIN medicos m ON u.id = m.id_medico";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("telefone"),
                        TipoUser.valueOf(rs.getString("tipoUser")),
                        rs.getString("crm"),
                        rs.getString("especialidade")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar medicos: " + e.getMessage());
        }

        return medicos;
    }
}
