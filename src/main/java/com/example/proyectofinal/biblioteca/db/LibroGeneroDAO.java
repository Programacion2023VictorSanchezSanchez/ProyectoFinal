package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.LibroGenero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para acceder y manipular la tabla de relaciones
 * entre libros y géneros en la base de datos.
 */
public class LibroGeneroDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Libro_has_Genero
    private static final String INSERT_QUERY = "INSERT INTO Libro_has_Genero (Libro_ISBN, Genero_nombre) VALUES (?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Libro_has_Genero";
    private static final String SELECT_BY_ISBN_QUERY = "SELECT * FROM Libro_has_Genero WHERE Libro_ISBN = ?";
    private static final String SELECT_BY_GENERO_QUERY = "SELECT * FROM Libro_has_Genero WHERE Genero_nombre = ?";
    private static final String DELETE_QUERY = "DELETE FROM Libro_has_Genero WHERE Libro_ISBN = ?";

    /**
     * Inserta una nueva relación libro-género en la base de datos.
     *
     * @param libroGenero La relación libro-género a insertar.
     * @throws SQLException Si ocurre algún error SQL.
     */
    public void insertLibroGenero(LibroGenero libroGenero) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, libroGenero.getLibroISBN());
            statement.setString(2, libroGenero.getGeneroNombre());
            statement.executeUpdate();
        }
    }

    /**
     * Obtiene todas las relaciones libro-género de la base de datos.
     *
     * @return Una lista de todas las relaciones libro-género.
     * @throws SQLException Si ocurre algún error SQL.
     */
    public List<LibroGenero> getAllLibroGeneros() throws SQLException {
        List<LibroGenero> libroGeneros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LibroGenero libroGenero = resultSetToLibroGenero(resultSet);
                libroGeneros.add(libroGenero);
            }
        }
        return libroGeneros;
    }

    /**
     * Obtiene todas las relaciones libro-género por ISBN del libro.
     *
     * @param libroISBN El ISBN del libro.
     * @return Una lista de relaciones libro-género para el libro dado.
     * @throws SQLException Si ocurre algún error SQL.
     */
    public List<LibroGenero> getLibroGenerosByISBN(String libroISBN) throws SQLException {
        List<LibroGenero> libroGeneros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_QUERY)) {
            statement.setString(1, libroISBN);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LibroGenero libroGenero = resultSetToLibroGenero(resultSet);
                libroGeneros.add(libroGenero);
            }
        }
        return libroGeneros;
    }

    /**
     * Obtiene todas las relaciones libro-género por nombre del género.
     *
     * @param generoNombre El nombre del género.
     * @return Una lista de relaciones libro-género para el género dado.
     * @throws SQLException Si ocurre algún error SQL.
     */
    public List<LibroGenero> getLibroGenerosByGenero(String generoNombre) throws SQLException {
        List<LibroGenero> libroGeneros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_GENERO_QUERY)) {
            statement.setString(1, generoNombre);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LibroGenero libroGenero = resultSetToLibroGenero(resultSet);
                libroGeneros.add(libroGenero);
            }
        }
        return libroGeneros;
    }

    /**
     * Elimina una relación libro-género de la base de datos por ISBN del libro.
     *
     * @param libroISBN El ISBN del libro.
     * @throws SQLException Si ocurre algún error SQL.
     */
    public void deleteLibroGenero(String libroISBN) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, libroISBN);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte un conjunto de resultados de base de datos en un objeto LibroGenero.
     *
     * @param resultSet El conjunto de resultados de la base de datos.
     * @return El objeto LibroGenero.
     * @throws SQLException Si ocurre algún error SQL.
     */
    private LibroGenero resultSetToLibroGenero(ResultSet resultSet) throws SQLException {
        return new LibroGenero(
                resultSet.getString("Libro_ISBN"),
                resultSet.getString("Genero_nombre"));
    }

}


