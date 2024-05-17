package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Autor
    private static final String INSERT_QUERY = "INSERT INTO Autor (nombre) VALUES (?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Autor";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Autor WHERE idAutor = ?";
    private static final String UPDATE_QUERY = "UPDATE Autor SET nombre = ? WHERE idAutor = ?";
    private static final String DELETE_QUERY = "DELETE FROM Autor WHERE idAutor = ?";

    // Método para insertar un nuevo autor en la base de datos
    public void insertAutor(Autor autor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, autor.getNombre());
            statement.executeUpdate();

            // Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    autor.setIdAutor(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el autor");
                }
            }
        }
    }

    // Método para obtener todos los autores de la base de datos
    public List<Autor> getAllAutores() throws SQLException {
        List<Autor> autores = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Autor autor = resultSetToAutor(resultSet);
                autores.add(autor);
            }
        }
        return autores;
    }

    // Método para obtener un autor por su ID
    public Autor getAutorById(int idAutor) throws SQLException {
        Autor autor = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, idAutor);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                autor = resultSetToAutor(resultSet);
            }
        }
        return autor;
    }

    // Método para actualizar los datos de un autor en la base de datos
    public void updateAutor(Autor autor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, autor.getNombre());
            statement.setInt(2, autor.getIdAutor());
            statement.executeUpdate();
        }
    }

    // Método para eliminar un autor de la base de datos por su ID
    public void deleteAutorById(int idAutor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idAutor);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Autor
    private Autor resultSetToAutor(ResultSet resultSet) throws SQLException {
        return new Autor(
                resultSet.getInt("idAutor"),
                resultSet.getString("nombre"));
    }

}

