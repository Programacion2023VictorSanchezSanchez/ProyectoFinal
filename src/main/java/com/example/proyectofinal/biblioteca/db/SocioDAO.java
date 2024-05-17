package com.example.proyectofinal.biblioteca.db;

import com.example.proyectofinal.biblioteca.model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {
    // Objeto de conexión a la base de datos.
    private final Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Persona
    private static final String INSERT_QUERY = "INSERT INTO Socio (nombre, apellidos, telefono, email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Socio";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Socio WHERE idSocio = ?";
    private static final String UPDATE_QUERY = "UPDATE Socio SET nombre = ?, apellidos = ?, email = ? WHERE idSocio = ?";
    private static final String DELETE_QUERY = "DELETE FROM Socio WHERE idSocio = ?";

    // Método para insertar una nueva persona en la base de datos
    public void insertSocio(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getApellidos());
            statement.setString(3, socio.getTelefono());
            statement.setString(4, socio.getEmail());

            statement.executeUpdate();
        }
    }

    // Método para obtener todas las personas de la base de datos
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

    // Método para obtener una persona por su DNI
    public Socio getSocioByid(int idSocio) throws SQLException {
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

    // Método para actualizar los datos de una persona en la base de datos
    public void updateSocio(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getApellidos());
            statement.setString(3, socio.getEmail());
            statement.setInt(4, socio.getIdSocio());

            statement.executeUpdate();
        }
    }

    // Método para eliminar una persona de la base de datos por su DNI
    public void deleteSocioByid(int idSocio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idSocio);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la
    //posición actual a un objeto Persona
    private Socio resultSetToSocio(ResultSet resultSet) throws SQLException {
        Socio socio = new Socio(
                resultSet.getInt("idSocio"),
                resultSet.getString("nombre"),
                resultSet.getString("apellidos"),
                resultSet.getString("telefono"),
                resultSet.getString("email"));
        return socio;
    }

    // Pruebas
    public static void main(String[] args) {
        SocioDAO socioDAO = new SocioDAO();
        try{
            List<Socio> socioList = socioDAO.getAllSocios();
            System.out.println(socioList.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}



