package com.example.proyectofinal.biblioteca.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos.
 * Implementa el patrón Singleton para asegurar que solo exista una única conexión a la base de datos.
 */
public class DBConnection {
    // URL de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://proyecto.cdosausqy9de.us-east-1.rds.amazonaws.com/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "usuarioroot";

    private static Connection connection;

    /**
     * Constructor privado para evitar la creación de instancias directas.
     */
    private DBConnection() {}

    /**
     * Obtiene la instancia única de la conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     */
    public static Connection getConnection() {
        if (connection == null) {
            // Bloqueo sincronizado para evitar problemas de concurrencia
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        // Establecer la conexión a la base de datos
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



