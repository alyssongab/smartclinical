package com.smartclinical.testes;

import com.smartclinical.util.ConexaoBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteConexao {
    public static void main(String[] args) {
        try{
            Connection conexao = ConexaoBD.getConexao();

            String sql = "select * from usuario";
            System.out.println("SQL: " + sql);

            Statement stmt = conexao.createStatement();
            boolean temResultado = stmt.execute(sql);

            if(temResultado){
                ResultSet resultados = stmt.getResultSet();
                while(resultados.next()){
                    System.out.println("ID: " + resultados.getInt(1));
                    System.out.println("Nome: " + resultados.getString(2));
                    System.out.println("Email: " + resultados.getString(3));
                    System.out.println("Senha: " + resultados.getString(4));
                    System.out.println("Telefone: " + resultados.getString(5));
                    System.out.println("Usuario: " + resultados.getString(6));
                }
            }
        }
        catch(SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}

