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
    private static final String INSERT_QUERY = "INSERT INTO Prestamo (idEjemplar, idSocio, fecha_inicio, fecha_fin, Libro_ISBN, estado) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Prestamo";
    private static final String SELECT_BY_ID_PRESTAMO_QUERY = "SELECT * FROM Prestamo WHERE idPrestamo = ?";

    private static final String SELECT_BY_ID_SOCIO_QUERY = "SELECT * FROM Prestamo WHERE idSocio = ?";

    private static final String SELECT_BY_ID_EJEMPLAR_QUERY = "SELECT * FROM Prestamo WHERE idEjemplar = ?";

    private static final String SELECT_BY_DATES_QUERY = "SELECT * FROM Prestamo WHERE fecha_inicio >= ? AND fecha_fin <= ?";

    private static final String SELECT_BY_IDS_QUERY = "SELECT * FROM Prestamo WHERE idPrestamo = ? AND idSocio = ? AND idEjemplar = ?";

    private static final String UPDATE_QUERY = "UPDATE Prestamo SET idEjemplar = ?, idSocio = ?, fecha_inicio = ?, fecha_fin = ?, Libro_ISBN = ?, estado = ? WHERE idPrestamo = ?";
    private static final String DELETE_QUERY = "DELETE FROM Prestamo WHERE idPrestamo = ?";

    private static final String MOROSOS_QUERY = "SELECT * FROM Prestamo WHERE fecha_fin < CURDATE() AND estado = 'no devuelto'";

    // Método para insertar un nuevo prestamo en la base de datos
    public void insertPrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, prestamo.getIdEjemplar());
            statement.setInt(2, prestamo.getIdSocio());
            statement.setString(3, prestamo.getFechaInicio());
            statement.setString(4, prestamo.getFechaFin());
            statement.setString(5, prestamo.getLibroISBN());
            statement.setString(6, prestamo.getEstado());
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

    // Método para obtener todos los prestamos de la base de datos
    public List<Prestamo> getPrestamosDate(String fechaInicio, String fechaFin) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DATES_QUERY)) {
            statement.setString(1, fechaInicio);
            statement.setString(2, fechaFin);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = resultSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método para obtener un prestamo por su ID
    public Prestamo getPrestamoByPrestamoId(int idPrestamo) throws SQLException {
        Prestamo prestamo = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_PRESTAMO_QUERY)) {
            statement.setInt(1, idPrestamo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                prestamo = resultSetToPrestamo(resultSet);
            }
        }
        return prestamo;
    }

    // Método para obtener un prestamo por ID socio
    public List<Prestamo> getPrestamoBySocioId(int idSocio) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SOCIO_QUERY)) {
            statement.setInt(1, idSocio);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = resultSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método para obtener un prestamo por ID ejemplar
    public List<Prestamo> getPrestamoByEjemplarId(int idEjemplar) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_EJEMPLAR_QUERY)) {
            statement.setInt(1, idEjemplar);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Prestamo prestamo = resultSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método para obtener un prestamo por sus IDs
    public Prestamo getPrestamoByIds(int idPrestamo, int idSocio, int idEjemplar) throws SQLException {
        Prestamo prestamo = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_IDS_QUERY)) {
            statement.setInt(1, idPrestamo);
            statement.setInt(2, idSocio);
            statement.setInt(3, idEjemplar);
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
            statement.setInt(1, prestamo.getIdEjemplar());
            statement.setInt(2, prestamo.getIdSocio());
            statement.setString(3, prestamo.getFechaInicio());
            statement.setString(4, prestamo.getFechaFin());
            statement.setString(5, prestamo.getLibroISBN());
            statement.setString(6, prestamo.getEstado());
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

    public List<Prestamo> selectPrestamoMorosos() throws SQLException{
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(MOROSOS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = resultSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Prestamo
    private Prestamo resultSetToPrestamo(ResultSet resultSet) throws SQLException {
        return new Prestamo(
                resultSet.getInt("idPrestamo"),
                resultSet.getInt("idEjemplar"),
                resultSet.getInt("idSocio"),
                resultSet.getString("fecha_inicio"),
                resultSet.getString("fecha_fin"),
                resultSet.getString("Libro_ISBN"),
                resultSet.getString("estado"));
    }


}


