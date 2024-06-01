package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Prestamo. Esta clase proporciona métodos para
 * realizar operaciones CRUD en la base de datos relacionadas con los préstamos.
 */
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

    /**
     * Inserta un nuevo préstamo en la base de datos.
     *
     * @param prestamo El objeto Prestamo que se desea insertar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Obtiene todos los préstamos de la base de datos.
     *
     * @return Una lista de objetos Prestamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Obtiene todos los préstamos de la base de datos dentro de un rango de fechas.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Una lista de objetos Prestamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Obtiene un préstamo por su ID.
     *
     * @param idPrestamo El ID del préstamo.
     * @return Un objeto Prestamo, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Obtiene préstamos por el ID del socio.
     *
     * @param idSocio El ID del socio.
     * @return Una lista de objetos Prestamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Obtiene préstamos por el ID del ejemplar.
     *
     * @param idEjemplar El ID del ejemplar.
     * @return Una lista de objetos Prestamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Obtiene un préstamo por sus IDs.
     *
     * @param idPrestamo El ID del préstamo.
     * @param idSocio El ID del socio.
     * @param idEjemplar El ID del ejemplar.
     * @return Un objeto Prestamo, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Actualiza los datos de un préstamo en la base de datos.
     *
     * @param prestamo El objeto Prestamo con los datos actualizados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Elimina un préstamo de la base de datos por su ID.
     *
     * @param idPrestamo El ID del préstamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void deletePrestamoById(int idPrestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idPrestamo);
            statement.executeUpdate();
        }
    }

    /**
     * Selecciona los préstamos morosos (aquellos cuya fecha de fin es anterior a la fecha actual y no han sido devueltos).
     *
     * @return Una lista de objetos Prestamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<Prestamo> selectPrestamoMorosos() throws SQLException {
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

    /**
     * Convierte un ResultSet a un objeto Prestamo.
     *
     * @param resultSet El ResultSet a convertir.
     * @return Un objeto Prestamo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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



