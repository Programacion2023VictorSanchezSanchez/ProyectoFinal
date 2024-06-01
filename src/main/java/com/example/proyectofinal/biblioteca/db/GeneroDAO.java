package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para interactuar con la tabla Genero en la base de datos.
 */
public class GeneroDAO {

    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Genero
    private static final String INSERT_QUERY = "INSERT INTO Genero (nombre) VALUES (?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Genero";
    private static final String SELECT_BY_NAME_QUERY = "SELECT * FROM Genero WHERE nombre = ?";
    private static final String UPDATE_QUERY = "UPDATE Genero SET nombre = ? WHERE nombre = ?";
    private static final String DELETE_QUERY = "DELETE FROM Genero WHERE nombre = ?";

    /**
     * Inserta un nuevo género en la base de datos.
     *
     * @param genero El objeto Genero a insertar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void insertGenero(Genero genero) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, genero.getNombre());
            statement.executeUpdate();
        }
    }

    /**
     * Obtiene todos los géneros de la base de datos.
     *
     * @return Una lista de objetos Genero.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene un género de la base de datos por su nombre.
     *
     * @param nombre El nombre del género a buscar.
     * @return El objeto Genero encontrado o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Actualiza los datos de un género en la base de datos.
     *
     * @param genero    El objeto Genero con los nuevos datos.
     * @param oldNombre El nombre antiguo del género.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void updateGenero(Genero genero, String oldNombre) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, genero.getNombre());
            statement.setString(2, oldNombre);
            statement.executeUpdate();
        }
    }

    /**
     * Elimina un género de la base de datos por su nombre.
     *
     * @param nombre El nombre del género a eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void deleteGeneroByName(String nombre) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, nombre);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte un ResultSet a un objeto Genero.
     *
     * @param resultSet El ResultSet a convertir.
     * @return Un objeto Genero.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private Genero resultSetToGenero(ResultSet resultSet) throws SQLException {
        return new Genero(resultSet.getString("nombre"));
    }

}
