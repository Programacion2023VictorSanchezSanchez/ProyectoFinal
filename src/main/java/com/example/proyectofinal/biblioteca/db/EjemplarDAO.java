package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Ejemplar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EjemplarDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Ejemplares
    private static final String INSERT_QUERY = "INSERT INTO EjemplaresDisponibles (Libro_ISBN, estado) VALUES (?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM EjemplaresDisponibles";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM EjemplaresDisponibles WHERE idEjemplar = ?";

    private static final String SELECT_BY_ISBN_QUERY = "SELECT * FROM EjemplaresDisponibles WHERE Libro_ISBN = ?";

    private static final String SELECT_BY_ISBN_ID_QUERY = "SELECT * FROM EjemplaresDisponibles WHERE idEjemplar = ? AND Libro_ISBN = ?";
    private static final String UPDATE_QUERY = "UPDATE EjemplaresDisponibles SET Libro_ISBN = ?, estado = ? WHERE idEjemplar = ?";
    private static final String DELETE_QUERY = "DELETE FROM EjemplaresDisponibles WHERE idEjemplar = ?";

    // Método para insertar un nuevo ejemplar en la base de datos
    public void insertEjemplar(Ejemplar ejemplar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, ejemplar.getLibroISBN());
            statement.setString(2, ejemplar.getEstado());
            statement.executeUpdate();
        }
    }

    // Método para obtener todos los ejemplares de la base de datos
    public List<Ejemplar> getAllEjemplares() throws SQLException {
        List<Ejemplar> ejemplares = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ejemplar ejemplar = resultSetToEjemplar(resultSet);
                ejemplares.add(ejemplar);
            }
        }
        return ejemplares;
    }

    // Método para obtener un ejemplar por su ID
    public Ejemplar getEjemplarById(int idEjemplar) throws SQLException {
        Ejemplar ejemplar = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, idEjemplar);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ejemplar = resultSetToEjemplar(resultSet);
            }
        }
        return ejemplar;
    }

    // Método para obtener un ejemplar por su ISBN
    public List<Ejemplar> getEjemplarByISBN(String ISBN) throws SQLException {
        List<Ejemplar> listaEjemplares = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_QUERY)) {
            statement.setString(1, ISBN);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ejemplar ejemplar = resultSetToEjemplar(resultSet);
                listaEjemplares.add(ejemplar);
            }
        }
        return listaEjemplares;
    }

    // Método para obtener un ejemplar por su ID
    public Ejemplar getEjemplarByIdISBN(int idEjemplar, String ISBN) throws SQLException {
        Ejemplar ejemplar = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_ID_QUERY)) {
            statement.setInt(1, idEjemplar);
            statement.setString(2, ISBN);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ejemplar = resultSetToEjemplar(resultSet);
            }
        }
        return ejemplar;
    }


    // Método para actualizar los datos de un ejemplar en la base de datos
    public void updateEjemplar(Ejemplar ejemplar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, ejemplar.getLibroISBN());
            statement.setString(2, ejemplar.getEstado());
            statement.setInt(3, ejemplar.getIdEjemplar());
            statement.executeUpdate();
        }
    }

    // Método para eliminar un ejemplar de la base de datos por su ID
    public void deleteEjemplarById(int idEjemplar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idEjemplar);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Ejemplar
    private Ejemplar resultSetToEjemplar(ResultSet resultSet) throws SQLException {
        int idEjemplar = resultSet.getInt("idEjemplar");
        String libroISBN = resultSet.getString("Libro_ISBN");
        String estado = resultSet.getString("estado");
        return new Ejemplar(idEjemplar, libroISBN, estado);
    }
}




