package com.smartclinical.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBD {

    // static para não precisa instanciar
    public static Connection getConexao() {
        Properties props = new Properties();
        try(FileInputStream arquivo = new FileInputStream("db.properties")){
            // resgata informações do SEU banco de dados, através do arquivo db.properties
            props.load(arquivo);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new IllegalArgumentException("Configurações de banco de dados inválidas no arquivo db.properties");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(url, user, password);
        }
        // Tratamentos com suas respectivas exceções
        catch(IOException e){
            System.out.println("Erro ao carregar o arquivo db.properties " + e.getMessage());
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            System.out.println("Erro ao carregar o driver JDBC: " + e.getMessage());
            e.printStackTrace();
        }
        catch(SQLException e){
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
