package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase SocioDAO que proporciona métodos para interactuar con la base de datos de socios.
 */
public class SocioDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Socio
    private static final String INSERT_QUERY = "INSERT INTO Socio (nombre, apellidos, telefono, email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Socio";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Socio WHERE idSocio = ?";
    private static final String SELECT_BY_NAME_QUERY = "SELECT * FROM Socio WHERE nombre = ?";
    private static final String SELECT_BY_NAME_ID_QUERY = "SELECT * FROM Socio WHERE idSocio = ? AND nombre = ?";
    private static final String UPDATE_QUERY = "UPDATE Socio SET nombre = ?, apellidos = ?, email = ? WHERE idSocio = ?";
    private static final String DELETE_QUERY = "DELETE FROM Socio WHERE idSocio = ?";

    /**
     * Método para insertar un nuevo socio en la base de datos.
     *
     * @param socio El objeto Socio a insertar.
     * @throws SQLException Si ocurre un error al realizar la inserción.
     */
    public void insertSocio(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getApellidos());
            statement.setString(3, socio.getTelefono());
            statement.setString(4, socio.getEmail());

            statement.executeUpdate();
        }
    }

    /**
     * Método para obtener todos los socios de la base de datos.
     *
     * @return Una lista de objetos Socio.
     * @throws SQLException Si ocurre un error al realizar la consulta.
     */
    public List<Socio> getAllSocios() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Socio socio = resultSetToSocio(resultSet);
                socios.add(socio);
            }
        }
        return socios;
    }

    /**
     * Método para obtener un socio por su ID.
     *
     * @param idSocio El ID del socio.
     * @return El objeto Socio correspondiente al ID proporcionado.
     * @throws SQLException Si ocurre un error al realizar la consulta.
     */
    public Socio getSocioById(int idSocio) throws SQLException {
        Socio socio = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, idSocio);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                socio = resultSetToSocio(resultSet);
            }
        }
        return socio;
    }

    /**
     * Método para obtener socios por su nombre.
     *
     * @param nombre El nombre del socio.
     * @return Una lista de objetos Socio con el nombre proporcionado.
     * @throws SQLException Si ocurre un error al realizar la consulta.
     */
    public List<Socio> getSociosByName(String nombre) throws SQLException {
        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_QUERY)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Socio socio = resultSetToSocio(resultSet);
                socios.add(socio);
            }
        }
        return socios;
    }

    /**
     * Método para obtener un socio por su ID y nombre.
     *
     * @param idSocio El ID del socio.
     * @param nombre  El nombre del socio.
     * @return El objeto Socio correspondiente al ID y nombre proporcionados.
     * @throws SQLException Si ocurre un error al realizar la consulta.
     */
    public Socio getSocioByIdName(int idSocio, String nombre) throws SQLException {
        Socio socio = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_ID_QUERY)) {
            statement.setInt(1, idSocio);
            statement.setString(2, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                socio = resultSetToSocio(resultSet);
            }
        }
        return socio;
    }

    /**
     * Método para actualizar los datos de un socio en la base de datos.
     *
     * @param socio El objeto Socio con los datos actualizados.
     * @throws SQLException Si ocurre un error al realizar la actualización.
     */
    public void updateSocio(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getApellidos());
            statement.setString(3, socio.getEmail());
            statement.setInt(4, socio.getIdSocio());

            statement.executeUpdate();
        }
    }

    /**
     * Método para eliminar un socio de la base de datos por su ID.
     *
     * @param idSocio El ID del socio a eliminar.
     * @throws SQLException Si ocurre un error al realizar la eliminación.
     */
    public void deleteSocioById(int idSocio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idSocio);
            statement.executeUpdate();
        }
    }

    /**
     * Método auxiliar para mapear un ResultSet en la posición actual a un objeto Socio.
     *
     * @param resultSet El ResultSet de la consulta.
     * @return Un objeto Socio correspondiente al ResultSet.
     * @throws SQLException Si ocurre un error al leer el ResultSet.
     */
    private Socio resultSetToSocio(ResultSet resultSet) throws SQLException {
        Socio socio = new Socio(
                resultSet.getInt("idSocio"),
                resultSet.getString("nombre"),
                resultSet.getString("apellidos"),
                resultSet.getString("telefono"),
                resultSet.getString("email"));
        return socio;
    }

}




