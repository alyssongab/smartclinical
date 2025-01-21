package com.smartclinical.dao;
import com.smartclinical.model.Avaliacao;
import com.smartclinical.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public List<Avaliacao> listarAvaliacoes() {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacoes";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idAvaliacao = rs.getInt("id_avaliacao");
                String notaAtendimento = rs.getString("nota_atendimento");
                String notaConsulta = rs.getString("nota_consulta");
                String descricao = rs.getString("descricao");

                Avaliacao avaliacao = new Avaliacao(idAvaliacao,notaAtendimento, notaConsulta, descricao);
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar avaliacoes: " + e.getMessage());
        }
        return avaliacoes;
    }
}
