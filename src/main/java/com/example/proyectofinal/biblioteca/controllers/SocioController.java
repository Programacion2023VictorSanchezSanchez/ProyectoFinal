package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.db.SocioDAO;
import com.example.proyectofinal.biblioteca.model.Socio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SocioController implements Initializable {

    @FXML
    private Button btBorrar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btGuardar;

    @FXML
    private Button btModificar;

    @FXML
    private Button btMostrarTodos;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbIdSocio;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbTelefono;

    @FXML
    private TableColumn<Socio, String> tcApellidos;

    @FXML
    private TableColumn<Socio, String> tcEmail;

    @FXML
    private TableColumn<Socio, Integer> tcIdSocio;

    @FXML
    private TableColumn<Socio, String> tcNombre;

    @FXML
    private TableColumn<Socio, String> tcTelefono;

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfIdBuscar;

    @FXML
    private TextField tfIdSocio;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TableView<Socio> tvSocios;

    private final SocioDAO socioDAO = new SocioDAO();

    @FXML
    public void initialize() {
        // Configurar las columnas de la TableView
        tcIdSocio.setCellValueFactory(new PropertyValueFactory<>("idSocio"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Cargar todos los socios en la TableView al inicio
        cargarTodosLosSocios();
    }

    @FXML
    private void onClickGuardar(ActionEvent event) {
        Socio socio = obtenerDatosSocioDeFormulario();
        try {
            socioDAO.insertSocio(socio);
            cargarTodosLosSocios(); // Recargar la tabla después de la inserción
            limpiarFormulario();
        } catch (SQLException e) {
            mostrarError("Error al guardar el socio: " + e.getMessage());
        }
    }

    @FXML
    private void onClickBorrar(ActionEvent event) {
        Socio socio = tvSocios.getSelectionModel().getSelectedItem();
        if (socio != null) {
            try {
                socioDAO.deleteSocioByid(socio.getIdSocio());
                cargarTodosLosSocios(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
            } catch (SQLException e) {
                mostrarError("Error al borrar el socio: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un socio primero.");
        }
    }

    @FXML
    private void onClickModificar(ActionEvent event) {
        Socio socio = obtenerDatosSocioDeFormulario();
        try {
            socioDAO.updateSocio(socio);
            cargarTodosLosSocios(); // Recargar la tabla después de la modificación
            limpiarFormulario();
        } catch (SQLException e) {
            mostrarError("Error al modificar el socio: " + e.getMessage());
        }
    }

    @FXML
    private void onClickBuscar(ActionEvent event) {
        int idSocioABuscar;
        try {
            idSocioABuscar = Integer.parseInt(tfIdBuscar.getText());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El ID del socio a buscar debe ser un número entero.");
            return;
        }

        try {
            Socio socioEncontrado = socioDAO.getSocioByid(idSocioABuscar);
            if (socioEncontrado != null) {
                tvSocios.getItems().clear(); // Limpiar la tabla
                tvSocios.getItems().add(socioEncontrado); // Agregar el socio encontrado a la tabla
            } else {
                mostrarAdvertencia("No se encontró ningún socio con el ID proporcionado.");
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar el socio: " + e.getMessage());
        }
    }

    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosSocios();
    }

    @FXML
    private void onClickTvAlumnos() {
        Socio socioSeleccionado = tvSocios.getSelectionModel().getSelectedItem();
        if (socioSeleccionado != null) {
            // Mostrar los datos del socio seleccionado en el formulario
            mostrarDatosSocioEnFormulario(socioSeleccionado);
        }
    }

    private void cargarTodosLosSocios() {
        try {
            List<Socio> socios = socioDAO.getAllSocios();
            tvSocios.getItems().clear();
            tvSocios.getItems().addAll(socios);
        } catch (SQLException e) {
            mostrarError("Error al cargar los socios: " + e.getMessage());
        }
    }

    @FXML
    private Socio obtenerDatosSocioDeFormulario() {
        int idSocio;
        String nombre = tfNombre.getText().trim();
        String apellidos = tfApellidos.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String email = tfEmail.getText().trim();

        try {
            idSocio = Integer.parseInt(tfIdSocio.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El ID del socio debe ser un número entero.");
            return null;
        }

        try {
            Socio socio = new Socio(idSocio, nombre, apellidos, telefono, email);
            return socio;
        } catch (IllegalArgumentException e) {
            mostrarAdvertencia("Error al crear el socio: " + e.getMessage());
            return null;
        }
    }


    private void mostrarDatosSocioEnFormulario(Socio socio) {
        tfIdSocio.setText(String.valueOf(socio.getIdSocio()));
        tfNombre.setText(socio.getNombre());
        tfApellidos.setText(socio.getApellidos());
        tfTelefono.setText(socio.getTelefono());
        tfEmail.setText(socio.getEmail());
    }

    private void limpiarFormulario() {
        tfIdSocio.clear();
        tfNombre.clear();
        tfApellidos.clear();
        tfTelefono.clear();
        tfEmail.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}




