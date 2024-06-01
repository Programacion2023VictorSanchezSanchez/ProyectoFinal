package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.db.LibroGeneroDAO;
import com.example.proyectofinal.biblioteca.model.LibroGenero;
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
 * Controlador para la vista de gestión de relaciones libro-género.
 */
public class LibroGeneroController implements Initializable {

    @FXML
    private Button btBorrar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btGuardar;

    @FXML
    private Button btMostrarTodos;

    @FXML
    private Label lbLibroISBN;

    @FXML
    private TableColumn<LibroGenero, String> tcLibroISBN;

    @FXML
    private TableColumn<LibroGenero, String> tcNombreGenero;

    @FXML
    private TextField tfLibroISBN;

    @FXML
    private TextField tfNombreGenero;

    @FXML
    private TextField tfISBNBuscar;

    @FXML
    private TextField tfGeneroBuscar;

    @FXML
    private TableView<LibroGenero> tvLibroGenero;

    private final LibroGeneroDAO libroGeneroDAO = new LibroGeneroDAO();

    /**
     * Inicializa la vista y configura las columnas de la tabla.
     */
    public void initialize() {
        // Configurar las columnas de la TableView
        tcLibroISBN.setCellValueFactory(new PropertyValueFactory<>("libroISBN"));
        tcNombreGenero.setCellValueFactory(new PropertyValueFactory<>("generoNombre"));

        // Cargar todas las relaciones libro-género en la TableView al inicio
        cargarTodosLosLibroGeneros();
    }

    /**
     * Maneja el evento de clic en el botón Guardar.
     * Guarda una nueva relación libro-género en la base de datos.
     */
    @FXML
    private void onClickGuardar(ActionEvent event) {
        String libroISBN = tfLibroISBN.getText().trim();
        String generoNombre = tfNombreGenero.getText().trim();
        if (!libroISBN.isEmpty() && !generoNombre.isEmpty()) {
            try {
                LibroGenero libroGenero = new LibroGenero(libroISBN, generoNombre);
                libroGeneroDAO.insertLibroGenero(libroGenero);
                cargarTodosLosLibroGeneros(); // Recargar la tabla después de la inserción
                limpiarFormulario();
                mostrarMensaje("Éxito", "Relación libro-género guardada exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al guardar la relación libro-género: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, complete todos los campos.");
        }
    }

    /**
     * Maneja el evento de clic en el botón Borrar.
     * Borra una relación libro-género de la base de datos.
     */
    @FXML
    private void onClickBorrar(ActionEvent event) {
        String libroISBN = tfLibroISBN.getText().trim();
        if (!libroISBN.isEmpty()) {
            try {
                libroGeneroDAO.deleteLibroGenero(libroISBN);
                cargarTodosLosLibroGeneros(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Relación libro-género eliminada exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar la relación libro-género: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, ingrese el ISBN del libro.");
        }
    }


    /**
     * Maneja el evento de clic en el botón Buscar.
     * Busca relaciones libro-género en la base de datos.
     */
    @FXML
    private void onClickBuscar(ActionEvent event) {
        String libroISBNBuscar = tfISBNBuscar.getText().trim();
        String generoBuscar = tfGeneroBuscar.getText().trim();

        if (!libroISBNBuscar.isEmpty() && !generoBuscar.isEmpty()) {
            mostrarAdvertencia("Por favor, haga la búsqueda por un solo parámetro.");
            return;
        }

        if (!libroISBNBuscar.isEmpty()) {
            try {
                List<LibroGenero> libroGeneros = libroGeneroDAO.getLibroGenerosByISBN(libroISBNBuscar);
                if (!libroGeneros.isEmpty()) {
                    tvLibroGenero.getItems().clear(); // Limpiar la tabla
                    tvLibroGenero.getItems().addAll(libroGeneros); // Agregar las relaciones encontradas a la tabla
                } else {
                    mostrarAdvertencia("No se encontraron relaciones libro-género para el ISBN proporcionado.");
                }
            } catch (SQLException e) {
                mostrarError("Error al buscar las relaciones libro-género: " + e.getMessage());
            }
        } else if(!generoBuscar.isEmpty()) {
            try{
                List<LibroGenero> libroGeneros = libroGeneroDAO.getLibroGenerosByGenero(generoBuscar);
                if(!libroGeneros.isEmpty()){
                    tvLibroGenero.getItems().clear(); // Limpiar la tabla
                    tvLibroGenero.getItems().addAll(libroGeneros); // Agregar las relaciones encontradas a la tabla
                } else{
                    mostrarAdvertencia("No se encontraron relacions libro-genero para el genero proporcionado");
                }
            } catch (SQLException e){
                mostrarError("Error al buscar las relaciones libro-genero: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, ingrese el ISBN del libro a buscar.");
        }
    }

    /**
     * Maneja el evento de clic en el botón Mostrar Todos.
     * Carga todas las relaciones libro-género en la TableView.
     */
    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosLibroGeneros();
    }

    /**
     * Maneja el evento de selección en la TableView.
     * Muestra los datos de la relación libro-género seleccionada en el formulario.
     */
    @FXML
    private void onClickTvLibroGenero() {
        LibroGenero libroGeneroSeleccionado = tvLibroGenero.getSelectionModel().getSelectedItem();
        if (libroGeneroSeleccionado != null) {
            // Mostrar los datos de la relación libro-género seleccionada en el formulario
            mostrarDatosLibroGeneroEnFormulario(libroGeneroSeleccionado);
        }
    }


    /**
     * Método para cargar todas las relaciones libro-género en la TableView.
     */
    private void cargarTodosLosLibroGeneros() {
        try {
            List<LibroGenero> libroGeneros = libroGeneroDAO.getAllLibroGeneros();
            tvLibroGenero.getItems().clear();
            tvLibroGenero.getItems().addAll(libroGeneros);
        } catch (SQLException e) {
            mostrarError("Error al cargar las relaciones libro-género: " + e.getMessage());
        }
    }

    /**
     * Método para mostrar los datos de una relación libro-género en el formulario.
     *
     * @param libroGenero Objeto LibroGenero que contiene los datos a mostrar.
     */
    private void mostrarDatosLibroGeneroEnFormulario(LibroGenero libroGenero) {
        tfLibroISBN.setText(libroGenero.getLibroISBN());
        tfNombreGenero.setText(libroGenero.getGeneroNombre());
    }

    /**
     * Método para limpiar los campos del formulario.
     */
    private void limpiarFormulario() {
        tfLibroISBN.clear();
        tfNombreGenero.clear();
    }


    /**
     * Método para mostrar un mensaje de error.
     *
     * @param mensaje Mensaje de error a mostrar.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método para mostrar una advertencia.
     *
     * @param mensaje Mensaje de advertencia a mostrar.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método para mostrar un mensaje con título y contenido.
     *
     * @param titulo   Título del mensaje.
     * @param contenido Contenido del mensaje.
     * @param tipo     Tipo de alerta a mostrar.
     */
    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Método de inicialización de la interfaz Initializable
    }
}

