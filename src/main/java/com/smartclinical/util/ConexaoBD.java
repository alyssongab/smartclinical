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
        try{
            // resgata informações do SEU banco de dados, através do arquivo db.properties
            FileInputStream arquivo = new FileInputStream("db.properties");
            props.load(arquivo);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        }
        catch(IOException | SQLException e){
            System.out.println("Erro ao carregar arquivo ou conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}
