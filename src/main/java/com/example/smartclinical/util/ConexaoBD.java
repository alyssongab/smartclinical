package com.example.smartclinical.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBD {

    private static String url = "jdbc:mysql://localhost:3306/javapoofx";
    private static String usuario = "root";
    private static String senha = "root";


    public ConexaoBD() {

        this.url = "jdbc:mysql://localhost:3306/javapoofx";
        this.usuario = "root";
        this.senha = "root";

    }

    public static Connection getConexao() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(url, usuario, senha);

    }
}

