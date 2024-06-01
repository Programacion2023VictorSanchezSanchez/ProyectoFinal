package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para interactuar con la tabla Libro en la base de datos.
 */
public class LibroDAO {

    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Libro
    private static final String INSERT_QUERY = "INSERT INTO Libro (ISBN, titulo, idAutor, anyo) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Libro";
    private static final String SELECT_BY_ISBN_QUERY = "SELECT * FROM Libro WHERE ISBN = ?";
    private static final String SELECT_BY_ISBN_TITLE_QUERY = "SELECT * FROM Libro WHERE ISBN = ? AND titulo = ?";
    private static final String SELECT_BY_TITLE_QUERY = "SELECT * FROM Libro WHERE titulo = ?";
    private static final String UPDATE_QUERY = "UPDATE Libro SET titulo = ?, idAutor = ?, anyo = ? WHERE ISBN = ?";
    private static final String DELETE_QUERY = "DELETE FROM Libro WHERE ISBN = ?";

    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * @param libro El objeto Libro a insertar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void insertLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, libro.getISBN());
            statement.setString(2, libro.getTitulo());
            statement.setInt(3, libro.getIdAutor());
            statement.setInt(4, libro.getAnyo());
            statement.executeUpdate();
        }
    }

    /**
     * Obtiene todos los libros de la base de datos.
     *
     * @return Una lista de objetos Libro.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public List<Libro> getAllLibros() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resultSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Obtiene un libro de la base de datos por su ISBN.
     *
     * @param ISBN El ISBN del libro a buscar.
     * @return El objeto Libro encontrado o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public Libro getLibroByISBN(String ISBN) throws SQLException {
        Libro libro = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_QUERY)) {
            statement.setString(1, ISBN);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                libro = resultSetToLibro(resultSet);
            }
        }
        return libro;
    }

    /**
     * Obtiene libros de la base de datos por su título.
     *
     * @param titulo El título del libro a buscar.
     * @return Una lista de objetos Libro con el mismo título.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public List<Libro> getLibrosByTitle(String titulo) throws SQLException {
        List<Libro> listaLibros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_TITLE_QUERY)) {
            statement.setString(1, titulo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resultSetToLibro(resultSet);
                listaLibros.add(libro);
            }
        }
        return listaLibros;
    }

    /**
     * Obtiene un libro de la base de datos por su ISBN y título.
     *
     * @param ISBN   El ISBN del libro a buscar.
     * @param titulo El título del libro a buscar.
     * @return El objeto Libro encontrado o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public Libro getLibroByISBNTitle(String ISBN, String titulo) throws SQLException {
        Libro libro = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_TITLE_QUERY)) {
            statement.setString(1, ISBN);
            statement.setString(2, titulo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                libro = resultSetToLibro(resultSet);
            }
        }
        return libro;
    }

    /**
     * Actualiza los datos de un libro en la base de datos.
     *
     * @param libro El objeto Libro con los nuevos datos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void updateLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, libro.getTitulo());
            statement.setInt(2, libro.getIdAutor());
            statement.setInt(3, libro.getAnyo());
            statement.setString(4, libro.getISBN());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina un libro de la base de datos por su ISBN.
     *
     * @param ISBN El ISBN del libro a eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void deleteLibroByISBN(String ISBN) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, ISBN);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte un ResultSet a un objeto Libro.
     *
     * @param resultSet El ResultSet a convertir.
     * @return Un objeto Libro.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private Libro resultSetToLibro(ResultSet resultSet) throws SQLException {
        return new Libro(
                resultSet.getString("ISBN"),
                resultSet.getString("titulo"),
                resultSet.getInt("idAutor"),
                resultSet.getInt("anyo"));
    }
}


