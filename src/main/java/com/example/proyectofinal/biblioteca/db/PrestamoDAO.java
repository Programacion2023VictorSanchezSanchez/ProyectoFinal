package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Prestamo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Prestamo
    private static final String INSERT_QUERY = "INSERT INTO Prestamos (disponibles, ISBN, idSocio, fecha_inicio, fecha_fin, isDevuelto) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Prestamos";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Prestamos WHERE idPrestamo = ?";
    private static final String UPDATE_QUERY = "UPDATE Prestamos SET disponibles = ?, ISBN = ?, idSocio = ?, fecha_inicio = ?, fecha_fin = ?, isDevuelto = ? WHERE idPrestamo = ?";
    private static final String DELETE_QUERY = "DELETE FROM Prestamos WHERE idPrestamo = ?";

    // Método para insertar un nuevo prestamo en la base de datos
    public void insertPrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, prestamo.getDisponibles());
            statement.setString(2, prestamo.getISBN());
            statement.setInt(3, prestamo.getIdSocio());
            statement.setDate(4, prestamo.getFechaInicio());
            statement.setDate(5, prestamo.getFechaFin());
            statement.setBoolean(6, prestamo.isDevuelto());
            statement.executeUpdate();

        }
    }

    // Método para obtener todos los prestamos de la base de datos
    public List<Prestamo> getAllPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = resultSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método para obtener un prestamo por su ID
    public Prestamo getPrestamoById(int idPrestamo) throws SQLException {
        Prestamo prestamo = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, idPrestamo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                prestamo = resultSetToPrestamo(resultSet);
            }
        }
        return prestamo;
    }

    // Método para actualizar los datos de un prestamo en la base de datos
    public void updatePrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, prestamo.getDisponibles());
            statement.setString(2, prestamo.getISBN());
            statement.setInt(3, prestamo.getIdSocio());
            statement.setDate(4, prestamo.getFechaInicio());
            statement.setDate(5, prestamo.getFechaFin());
            statement.setBoolean(6, prestamo.isDevuelto());
            statement.setInt(7, prestamo.getIdPrestamo());
            statement.executeUpdate();
        }
    }

    // Método para eliminar un prestamo de la base de datos por su ID
    public void deletePrestamoById(int idPrestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idPrestamo);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Prestamo
    private Prestamo resultSetToPrestamo(ResultSet resultSet) throws SQLException {
        return new Prestamo(
                resultSet.getInt("idPrestamo"),
                resultSet.getInt("disponibles"),
                resultSet.getString("ISBN"),
                resultSet.getInt("idSocio"),
                resultSet.getDate("fecha_inicio"),
                resultSet.getDate("fecha_fin"),
                resultSet.getBoolean("isDevuelto"));
    }


}


