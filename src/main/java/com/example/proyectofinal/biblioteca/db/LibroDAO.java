package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Libro;
import com.example.proyectofinal.biblioteca.model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    // Método para insertar un nuevo libro en la base de datos
    public void insertLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, libro.getISBN());
            statement.setString(2, libro.getTitulo());
            statement.setInt(3, libro.getIdAutor());
            statement.setInt(4, libro.getAnyo());
            statement.executeUpdate();
        }
    }

    // Método para obtener todos los libros de la base de datos
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

    // Método para obtener un libro por su ISBN
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

    // Método para obtener un libro por su titulo
    public List<Libro> getLibrosByTitle(String titulo) throws SQLException {
        List <Libro> listaLibros = new ArrayList<>();
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

    // Método para obtener una persona por su id y nombre
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

    // Método para actualizar los datos de un libro en la base de datos
    public void updateLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, libro.getTitulo());
            statement.setInt(2, libro.getIdAutor());
            statement.setInt(3, libro.getAnyo());
            statement.setString(4, libro.getISBN());
            statement.executeUpdate();
        }
    }

    // Método para eliminar un libro de la base de datos por su ISBN
    public void deleteLibroByISBN(String ISBN) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, ISBN);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Libro
    private Libro resultSetToLibro(ResultSet resultSet) throws SQLException {
        return new Libro(
                resultSet.getString("ISBN"),
                resultSet.getString("titulo"),
                resultSet.getInt("idAutor"),
                resultSet.getInt("anyo"));
    }


}

