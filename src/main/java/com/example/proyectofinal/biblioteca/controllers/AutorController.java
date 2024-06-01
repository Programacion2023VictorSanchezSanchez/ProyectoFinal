package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.db.AutorDAO;
import javafx.fxml.Initializable;

import com.example.proyectofinal.biblioteca.model.Autor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la gestión de autores en la biblioteca.
 */
public class AutorController implements Initializable {

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
    private Label lbIdAutor;

    @FXML
    private Label lbNombre;

    @FXML
    private TableColumn<Autor, Integer> tcIdAutor;

    @FXML
    private TableColumn<Autor, String> tcNombre;

    @FXML
    private TextField tfIdAutor;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfIdBuscar;

    @FXML
    private TableView<Autor> tvAutores;

    private final AutorDAO autorDAO = new AutorDAO();

    /**
     * Inicializa el controlador.
     */
    public void initialize() {
        // Configurar las columnas de la TableView
        tcIdAutor.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Cargar todos los autores en la TableView al inicio
        cargarTodosLosAutores();
    }

    /**
     * Método para manejar el evento de guardar un autor.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Autor autor = obtenerDatosAutorDeFormulario();
        if (autor != null) {
            try {
                autorDAO.insertAutor(autor);
                cargarTodosLosAutores(); // Recargar la tabla después de la inserción
                limpiarFormulario();
                mostrarMensaje("Éxito", "Autor guardado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al guardar el autor: " + e.getMessage());
            }
        }
    }

    /**
     * Método para manejar el evento de borrar un autor.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickBorrar(ActionEvent event) {
        Autor autor = tvAutores.getSelectionModel().getSelectedItem();
        if (autor != null) {
            try {
                autorDAO.deleteAutorById(autor.getIdAutor());
                cargarTodosLosAutores(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Autor borrado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar el autor: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un autor primero.");
        }
    }

    /**
     * Método para manejar el evento de modificar un autor.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickModificar(ActionEvent event) {
        Autor autor = obtenerDatosAutorDeFormulario();
        if (autor != null) {
            try {
                autorDAO.updateAutor(autor);
                cargarTodosLosAutores(); // Recargar la tabla después de la modificación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Autor modificado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al modificar el autor: " + e.getMessage());
            }
        }
    }

    /**
     * Método para manejar el evento de buscar un autor.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickBuscar(ActionEvent event) {
        int idAutorABuscar;
        try {
            idAutorABuscar = Integer.parseInt(tfIdBuscar.getText());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El ID del autor a buscar debe ser un número entero.");
            return;
        }

        try {
            Autor autorEncontrado = autorDAO.getAutorById(idAutorABuscar);
            if (autorEncontrado != null) {
                tvAutores.getItems().clear(); // Limpiar la tabla
                tvAutores.getItems().add(autorEncontrado); // Agregar el autor encontrado a la tabla
            } else {
                mostrarAdvertencia("No se encontró ningún autor con el ID proporcionado.");
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar el autor: " + e.getMessage());
        }
    }

    /**
     * Método para manejar el evento de mostrar todos los autores.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosAutores();
    }

    /**
     * Método para manejar el evento de clic en la TableView de autores.
     */
    @FXML
    private void onClickTvAutores() {
        Autor autorSeleccionado = tvAutores.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            // Mostrar los datos del autor seleccionado en el formulario
            mostrarDatosAutorEnFormulario(autorSeleccionado);
        }
    }

    /**
     * Carga todos los autores en la TableView.
     */
    private void cargarTodosLosAutores() {
        try {
            List<Autor> autores = autorDAO.getAllAutores();
            tvAutores.getItems().clear();
            tvAutores.getItems().addAll(autores);
        } catch (SQLException e) {
            mostrarError("Error al cargar los autores: " + e.getMessage());
        }
    }

    /**
     * Obtiene los datos de autor ingresados en el formulario.
     * @return Objeto Autor con los datos del formulario.
     */
    private Autor obtenerDatosAutorDeFormulario() {
        int idAutor;
        String nombre = tfNombre.getText().trim();

        try {
            idAutor = Integer.parseInt(tfIdAutor.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El ID del autor debe ser un número entero.");
            return null;
        }

        try {
            Autor autor = new Autor(idAutor, nombre);
            return autor;
        } catch (IllegalArgumentException e) {
            mostrarAdvertencia("Error al crear el autor: " + e.getMessage());
            return null;
        }
    }

    /**
     * Muestra los datos de un autor en el formulario.
     * @param autor Objeto Autor a mostrar.
     */
    private void mostrarDatosAutorEnFormulario(Autor autor) {
        tfIdAutor.setText(String.valueOf(autor.getIdAutor()));
        tfNombre.setText(autor.getNombre());
    }

    /**
     * Limpia el formulario.
     */
    private void limpiarFormulario() {
        tfIdAutor.clear();
        tfNombre.clear();
    }

    /**
     * Muestra un mensaje de error.
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
     * Muestra una advertencia.
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
     * Muestra un mensaje.
     * @param titulo Título del mensaje.
     * @param contenido Contenido del mensaje.
     * @param tipo Tipo de mensaje.
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

    }
}
