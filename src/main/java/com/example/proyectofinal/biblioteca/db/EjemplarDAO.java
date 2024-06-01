package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.db.DBConnection;
import com.example.proyectofinal.biblioteca.model.Ejemplar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para interactuar con la tabla EjemplaresDisponibles en la base de datos.
 */
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

    /**
     * Inserta un nuevo ejemplar en la base de datos.
     *
     * @param ejemplar El objeto Ejemplar a insertar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void insertEjemplar(Ejemplar ejemplar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, ejemplar.getLibroISBN());
            statement.setString(2, ejemplar.getEstado());
            statement.executeUpdate();
        }
    }

    /**
     * Obtiene todos los ejemplares de la base de datos.
     *
     * @return Una lista de objetos Ejemplar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene un ejemplar de la base de datos por su ID.
     *
     * @param idEjemplar El ID del ejemplar a buscar.
     * @return El objeto Ejemplar encontrado o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene ejemplares de la base de datos por su ISBN.
     *
     * @param ISBN El ISBN del libro del ejemplar a buscar.
     * @return Una lista de objetos Ejemplar con el mismo ISBN.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene un ejemplar de la base de datos por su ID y su ISBN.
     *
     * @param idEjemplar El ID del ejemplar a buscar.
     * @param ISBN       El ISBN del libro del ejemplar a buscar.
     * @return El objeto Ejemplar encontrado o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Actualiza los datos de un ejemplar en la base de datos.
     *
     * @param ejemplar El objeto Ejemplar con los nuevos datos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void updateEjemplar(Ejemplar ejemplar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, ejemplar.getLibroISBN());
            statement.setString(2, ejemplar.getEstado());
            statement.setInt(3, ejemplar.getIdEjemplar());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina un ejemplar de la base de datos por su ID.
     *
     * @param idEjemplar El ID del ejemplar a eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void deleteEjemplarById(int idEjemplar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idEjemplar);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte un ResultSet a un objeto Ejemplar.
     *
     * @param resultSet El ResultSet a convertir.
     * @return Un objeto Ejemplar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private Ejemplar resultSetToEjemplar(ResultSet resultSet) throws SQLException {
        int idEjemplar = resultSet.getInt("idEjemplar");
        String libroISBN = resultSet.getString("Libro_ISBN");
        String estado = resultSet.getString("estado");
        return new Ejemplar(idEjemplar, libroISBN, estado);
    }
}





