package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.db.LibroDAO;
import com.example.proyectofinal.biblioteca.model.Libro;
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

public class LibroController implements Initializable {

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
    private Label lbISBN;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbIdAutor;

    @FXML
    private Label lbAnyo;

    @FXML
    private TableColumn<Libro, String> tcISBN;

    @FXML
    private TableColumn<Libro, String> tcNombre;

    @FXML
    private TableColumn<Libro, Integer> tcIdAutor;

    @FXML
    private TableColumn<Libro, Integer> tcAnyo;

    @FXML
    private TextField tfISBN;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfIdAutor;

    @FXML
    private TextField tfAnyo;

    @FXML
    private TextField tfISBNBuscar;

    @FXML
    private TextField tfTituloBuscar;

    @FXML
    private TableView<Libro> tvLibros;

    private final LibroDAO libroDAO = new LibroDAO();

    public void initialize() {
        // Configurar las columnas de la TableView
        tcISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcIdAutor.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
        tcAnyo.setCellValueFactory(new PropertyValueFactory<>("anyo"));

        // Cargar todos los libros en la TableView al inicio
        cargarTodosLosLibros();
    }

    @FXML
    private void onClickGuardar(ActionEvent event) {
        Libro libro = obtenerDatosLibroDeFormulario();
        if (libro != null) {
            try {
                libroDAO.insertLibro(libro);
                cargarTodosLosLibros(); // Recargar la tabla después de la inserción
                limpiarFormulario();
                mostrarMensaje("Éxito", "Libro guardado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al guardar el libro: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onClickBorrar(ActionEvent event) {
        Libro libro = tvLibros.getSelectionModel().getSelectedItem();
        if (libro != null) {
            try {
                libroDAO.deleteLibroByISBN(libro.getISBN());
                cargarTodosLosLibros(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Libro borrado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar el libro: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un libro primero.");
        }
    }

    @FXML
    private void onClickModificar(ActionEvent event) {
        Libro libro = obtenerDatosLibroDeFormulario();
        if (libro != null) {
            try {
                libroDAO.updateLibro(libro);
                cargarTodosLosLibros(); // Recargar la tabla después de la modificación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Libro modificado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al modificar el libro: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onClickBuscar(ActionEvent event) {
        String isbnABuscar = tfISBNBuscar.getText().trim();
        String tituloABuscar = tfTituloBuscar.getText().trim();

        try {
            if(!isbnABuscar.isEmpty() && !tituloABuscar.isEmpty() ){
                Libro libroEncontrado = libroDAO.getLibroByISBNTitle(isbnABuscar, tituloABuscar);
                if(libroEncontrado != null){
                    tvLibros.getItems().clear(); // Limpiar la tabla
                    tvLibros.getItems().add(libroEncontrado); // Agregar el libro encontrado a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún libro con el ISBN y el titulo proporcionado.");
                }
            }else if(!isbnABuscar.isEmpty()){
                Libro libroEncontrado = libroDAO.getLibroByISBN(isbnABuscar);
                if(libroEncontrado != null){
                    tvLibros.getItems().clear(); // Limpiar la tabla
                    tvLibros.getItems().add(libroEncontrado); // Agregar el libro encontrado a la tabla
                }else{
                    mostrarAdvertencia("No se encontró ningún libro con el ISBN proporcionado.");
                }
            } else{
                List<Libro> listaLibros = libroDAO.getLibrosByTitle(tituloABuscar);
                if(!listaLibros.isEmpty()){
                    tvLibros.getItems().clear(); // Limpiar la tabla
                    tvLibros.getItems().addAll(listaLibros); // Agregar el libro encontrado a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún libro con el titulo proporcionado.");
                }

            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el libro: " + e.getMessage());
        } catch (IllegalArgumentException e){
            mostrarAdvertencia("Error al buscar el libro: " + e.getMessage());
        }
    }

    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosLibros();
    }

    @FXML
    private void onClickTvLibros() {
        Libro libroSeleccionado = tvLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            // Mostrar los datos del libro seleccionado en el formulario
            mostrarDatosLibroEnFormulario(libroSeleccionado);
        }
    }

    private void cargarTodosLosLibros() {
        try {
            List<Libro> libros = libroDAO.getAllLibros();
            tvLibros.getItems().clear();
            tvLibros.getItems().addAll(libros);
        } catch (SQLException e) {
            mostrarError("Error al cargar los libros: " + e.getMessage());
        }
    }

    private Libro obtenerDatosLibroDeFormulario() {
        String ISBN = tfISBN.getText().trim();
        String nombre = tfNombre.getText().trim();
        int idAutor;
        int anyo;

        try {
            idAutor = Integer.parseInt(tfIdAutor.getText().trim());
            anyo = Integer.parseInt(tfAnyo.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El ID de autor y el año deben ser números enteros.");
            return null;
        }

        try {
            Libro libro = new Libro(ISBN, nombre, idAutor, anyo);
            return libro;
        } catch (IllegalArgumentException e) {
            mostrarAdvertencia("Error al crear el libro: " + e.getMessage());
            return null;
        }
    }

    private void mostrarDatosLibroEnFormulario(Libro libro) {
        tfISBN.setText(libro.getISBN());
        tfNombre.setText(libro.getTitulo());
        tfIdAutor.setText(String.valueOf(libro.getIdAutor()));
        tfAnyo.setText(String.valueOf(libro.getAnyo()));
    }

    private void limpiarFormulario() {
        tfISBN.clear();
        tfNombre.clear();
        tfIdAutor.clear();
        tfAnyo.clear();
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

