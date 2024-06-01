package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.db.GeneroDAO;
import com.example.proyectofinal.biblioteca.model.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GeneroController implements Initializable {

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
    private Label lbNombre;

    @FXML
    private TableColumn<Genero, String> tcNombre;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfGeneroBuscar;

    @FXML
    private TableView<Genero> tvGeneros;

    private final GeneroDAO generoDAO = new GeneroDAO();

    public void initialize() {
        // Configurar la columna de la TableView
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Cargar todos los géneros en la TableView al inicio
        cargarTodosLosGeneros();
    }

    @FXML
    private void onClickGuardar(ActionEvent event) {
        String nombre = tfNombre.getText().trim();
        if (!nombre.isEmpty()) {
            try {
                Genero genero = new Genero(nombre);
                generoDAO.insertGenero(genero);
                cargarTodosLosGeneros(); // Recargar la tabla después de la inserción
                limpiarFormulario();
                mostrarMensaje("Éxito", "Género guardado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al guardar el género: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, ingrese el nombre del género.");
        }
    }

    @FXML
    private void onClickBorrar(ActionEvent event) {
        Genero genero = tvGeneros.getSelectionModel().getSelectedItem();
        if (genero != null) {
            try {
                generoDAO.deleteGeneroByName(genero.getNombre());
                cargarTodosLosGeneros(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Género borrado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar el género: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un género primero.");
        }
    }

    @FXML
    private void onClickModificar(ActionEvent event) {
        String nombre = tfNombre.getText().trim();
        if (!nombre.isEmpty()) {
            Genero generoSeleccionado = tvGeneros.getSelectionModel().getSelectedItem();
            if (generoSeleccionado != null) {
                try {
                    Genero nuevoGenero = new Genero(nombre);
                    generoDAO.updateGenero(nuevoGenero, generoSeleccionado.getNombre());
                    cargarTodosLosGeneros(); // Recargar la tabla después de la modificación
                    limpiarFormulario();
                    mostrarMensaje("Éxito", "Género modificado exitosamente", Alert.AlertType.INFORMATION);
                } catch (SQLException e) {
                    mostrarError("Error al modificar el género: " + e.getMessage());
                }
            } else {
                mostrarAdvertencia("Por favor, seleccione un género primero.");
            }
        } else {
            mostrarAdvertencia("Por favor, ingrese el nombre del género.");
        }
    }

    @FXML
    private void onClickBuscar(ActionEvent event) {
        String nombreABuscar = tfGeneroBuscar.getText().trim();

        try {
            Genero generoEncontrado = generoDAO.getGeneroByName(nombreABuscar);
            if (generoEncontrado != null) {
                tvGeneros.getItems().clear(); // Limpiar la tabla
                tvGeneros.getItems().add(generoEncontrado); // Agregar el género encontrado a la tabla
            } else {
                mostrarAdvertencia("No se encontró ningún género con el nombre proporcionado.");
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar el género: " + e.getMessage());
        }
    }

    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosGeneros();
    }

    @FXML
    private void onClickTvGeneros() {
        Genero generoSeleccionado = tvGeneros.getSelectionModel().getSelectedItem();
        if (generoSeleccionado != null) {
            // Mostrar los datos del género seleccionado en el formulario
            mostrarDatosGeneroEnFormulario(generoSeleccionado);
        }
    }

    private void cargarTodosLosGeneros() {
        try {
            List<Genero> generos = generoDAO.getAllGeneros();
            tvGeneros.getItems().clear();
            tvGeneros.getItems().addAll(generos);
        } catch (SQLException e) {
            mostrarError("Error al cargar los géneros: " + e.getMessage());
        }
    }

    private void mostrarDatosGeneroEnFormulario(Genero genero) {
        tfNombre.setText(genero.getNombre());
    }

    private void limpiarFormulario() {
        tfNombre.clear();
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

