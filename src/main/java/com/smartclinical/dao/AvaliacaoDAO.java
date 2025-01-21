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

    // Método para cadastrar uma avaliação
    public boolean cadastrarAvaliacao(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacoes (nota_atendimento, nota_consulta, descricao) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, avaliacao.getNotaAtendimento());
            stmt.setString(2, avaliacao.getNotaConsulta());
            stmt.setString(3, avaliacao.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar avaliacao: " + e.getMessage());
            return false;
        }
    }

}
