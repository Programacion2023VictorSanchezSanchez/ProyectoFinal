package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Genero
    private static final String INSERT_QUERY = "INSERT INTO Genero (nombre) VALUES (?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Genero";
    private static final String SELECT_BY_NAME_QUERY = "SELECT * FROM Genero WHERE nombre = ?";
    private static final String UPDATE_QUERY = "UPDATE Genero SET nombre = ? WHERE nombre = ?";
    private static final String DELETE_QUERY = "DELETE FROM Genero WHERE nombre = ?";

    // Método para insertar un nuevo género en la base de datos
    public void insertGenero(Genero genero) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, genero.getNombre());
            statement.executeUpdate();
        }
    }

    // Método para obtener todos los géneros de la base de datos
    public List<Genero> getAllGeneros() throws SQLException {
        List<Genero> generos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genero genero = resultSetToGenero(resultSet);
                generos.add(genero);
            }
        }
        return generos;
    }

    // Método para obtener un género por su nombre
    public Genero getGeneroByName(String nombre) throws SQLException {
        Genero genero = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_QUERY)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                genero = resultSetToGenero(resultSet);
            }
        }
        return genero;
    }

    // Método para actualizar los datos de un género en la base de datos
    public void updateGenero(Genero genero, String oldNombre) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, genero.getNombre());
            statement.setString(2, oldNombre);
            statement.executeUpdate();
        }
    }

    // Método para eliminar un género de la base de datos por su nombre
    public void deleteGeneroByName(String nombre) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, nombre);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Genero
    private Genero resultSetToGenero(ResultSet resultSet) throws SQLException {
        return new Genero(resultSet.getString("nombre"));
    }

}
