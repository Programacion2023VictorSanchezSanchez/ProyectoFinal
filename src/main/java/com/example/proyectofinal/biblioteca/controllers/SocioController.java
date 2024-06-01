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

/**
 * Controlador para la gestión de socios en la interfaz gráfica.
 */
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
    private TextField tfNombreBuscar;

    @FXML
    private TextField tfIdSocio;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TableView<Socio> tvSocios;

    private final SocioDAO socioDAO = new SocioDAO();

    /**
     * Método de inicialización para configurar la interfaz gráfica.
     */
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

    /**
     * Maneja el evento de clic en el botón "Guardar".
     *
     * @param event El evento de acción.
     */
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Socio socio = obtenerDatosSocioDeFormulario();
        try {
            socioDAO.insertSocio(socio);
            cargarTodosLosSocios(); // Recargar la tabla después de la inserción
            limpiarFormulario();
            mostrarMensaje("Éxito", "Socio guardado exitosamente", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarError("Error al guardar el socio: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento de clic en el botón "Borrar".
     *
     * @param event El evento de acción.
     */
    @FXML
    private void onClickBorrar(ActionEvent event) {
        Socio socio = tvSocios.getSelectionModel().getSelectedItem();
        if (socio != null) {
            try {
                socioDAO.deleteSocioById(socio.getIdSocio());
                cargarTodosLosSocios(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Socio borrado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar el socio: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un socio primero.");
        }
    }

    /**
     * Maneja el evento de clic en el botón "Modificar".
     *
     * @param event El evento de acción.
     */
    @FXML
    private void onClickModificar(ActionEvent event) {
        Socio socio = obtenerDatosSocioDeFormulario();
        try {
            socioDAO.updateSocio(socio);
            cargarTodosLosSocios(); // Recargar la tabla después de la modificación
            limpiarFormulario();
            mostrarMensaje("Éxito", "Socio modificado exitosamente", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarError("Error al modificar el socio: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento de clic en el botón "Buscar".
     *
     * @param event El evento de acción.
     */
    @FXML
    private void onClickBuscar(ActionEvent event) {
        String idSocioABuscar = tfIdBuscar.getText().trim();
        String nombreABuscar = tfNombreBuscar.getText().trim();

        try {
            if (!idSocioABuscar.isEmpty() && !nombreABuscar.isEmpty()) {
                Socio socioEncontrado = socioDAO.getSocioByIdName(Integer.parseInt(idSocioABuscar), nombreABuscar);
                if (socioEncontrado != null) {
                    tvSocios.getItems().clear(); // Limpiar la tabla
                    tvSocios.getItems().add(socioEncontrado); // Agregar el socio encontrado a la tabla
                } else {
                    mostrarAdvertencia("No se encontró ningún socio con el ID y el nombre proporcionado.");
                }
            } else if (!idSocioABuscar.isEmpty()) {
                Socio socioEncontrado = socioDAO.getSocioById(Integer.parseInt(idSocioABuscar));
                if (socioEncontrado != null) {
                    tvSocios.getItems().clear(); // Limpiar la tabla
                    tvSocios.getItems().add(socioEncontrado); // Agregar el socio encontrado a la tabla
                } else {
                    mostrarAdvertencia("No se encontró ningún socio con el ID proporcionado.");
                }
            } else {
                List<Socio> listaSocios = socioDAO.getSociosByName(nombreABuscar);
                if (!listaSocios.isEmpty()) {
                    tvSocios.getItems().clear(); // Limpiar la tabla
                    tvSocios.getItems().addAll(listaSocios); // Agregar el socio encontrado a la tabla
                } else {
                    mostrarAdvertencia("No se encontró ningún socio con el Nombre proporcionado.");
                }

            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el socio: " + e.getMessage());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El id del socio debe de ser un número entero.");
        }
    }

    /**
     * Maneja el evento de clic en el botón "Mostrar Todos".
     *
     * @param event El evento de acción.
     */
    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosSocios();
    }

    /**
     * Maneja el evento de clic en la tabla de socios.
     */
    @FXML
    private void onClickTvAlumnos() {
        Socio socioSeleccionado = tvSocios.getSelectionModel().getSelectedItem();
        if (socioSeleccionado != null) {
            // Mostrar los datos del socio seleccionado en el formulario
            mostrarDatosSocioEnFormulario(socioSeleccionado);
        }
    }

    /**
     * Carga todos los socios en la tabla.
     */
    private void cargarTodosLosSocios() {
        try {
            List<Socio> socios = socioDAO.getAllSocios();
            tvSocios.getItems().clear();
            tvSocios.getItems().addAll(socios);
        } catch (SQLException e) {
            mostrarError("Error al cargar los socios: " + e.getMessage());
        }
    }

    /**
     * Obtiene los datos del socio del formulario.
     *
     * @return Un objeto Socio con los datos del formulario.
     */
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

    /**
     * Muestra los datos del socio en el formulario.
     *
     * @param socio El objeto Socio cuyos datos se mostrarán.
     */
    private void mostrarDatosSocioEnFormulario(Socio socio) {
        tfIdSocio.setText(String.valueOf(socio.getIdSocio()));
        tfNombre.setText(socio.getNombre());
        tfApellidos.setText(socio.getApellidos());
        tfTelefono.setText(socio.getTelefono());
        tfEmail.setText(socio.getEmail());
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {
        tfIdSocio.clear();
        tfNombre.clear();
        tfApellidos.clear();
        tfTelefono.clear();
        tfEmail.clear();
    }

    /**
     * Muestra un mensaje de error en una alerta.
     *
     * @param mensaje El mensaje de error.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de advertencia en una alerta.
     *
     * @param mensaje El mensaje de advertencia.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje en una alerta.
     *
     * @param titulo   El título de la alerta.
     * @param contenido El contenido del mensaje.
     * @param tipo     El tipo de alerta.
     */
    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    /**
     * Método de inicialización de la interfaz gráfica.
     *
     * @param url             La URL de localización.
     * @param resourceBundle El conjunto de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}





