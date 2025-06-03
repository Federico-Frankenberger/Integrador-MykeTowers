package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String URL="jdbc:mysql://localhost:3306/integrador";
    public static final String USER="root";
    public static final String PASSWORD="rootpassword";

    public static Connection obtenerConexion() throws SQLException {
        if(URL==null||URL.isEmpty()||USER==null|| USER.isEmpty()||PASSWORD==null||PASSWORD.isEmpty()){
            throw new SQLException("Configuración de la base de datos erronea.");
        }
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
