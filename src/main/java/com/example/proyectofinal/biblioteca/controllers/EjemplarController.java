package com.example.proyectofinal.biblioteca.controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import com.example.proyectofinal.biblioteca.db.EjemplarDAO;
import com.example.proyectofinal.biblioteca.model.Ejemplar;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la gestión de ejemplares en la biblioteca.
 */
public class EjemplarController implements Initializable {

    @FXML
    private Button btGuardar;

    @FXML
    private Button btBorrar;

    @FXML
    private Button btModificar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btMostrarTodos;

    @FXML
    private TextField tfIdEjemplar;

    @FXML
    private TextField tfLibroISBN;

    @FXML
    private TextField tfEstado;

    @FXML
    private TextField tfIdBuscar;

    @FXML
    private TextField tfISBNBuscar;

    @FXML
    private TableView<Ejemplar> tvEjemplares;

    @FXML
    private TableColumn<Ejemplar, Integer> tcIdEjemplar;

    @FXML
    private TableColumn<Ejemplar, String> tcLibroISBN;

    @FXML
    private TableColumn<Ejemplar, String> tcEstado;

    private final EjemplarDAO ejemplarDAO = new EjemplarDAO();

    /**
     * Inicializa el controlador.
     */
    public void initialize() {
        // Configurar las columnas de la TableView
        tcIdEjemplar.setCellValueFactory(new PropertyValueFactory<>("idEjemplar"));
        tcLibroISBN.setCellValueFactory(new PropertyValueFactory<>("libroISBN"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar todos los ejemplares en la TableView al inicio
        cargarTodosLosEjemplares();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Guardar" para guardar un nuevo ejemplar.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Ejemplar ejemplar = obtenerDatosEjemplarDeFormulario();
        if (ejemplar != null) {
            try {
                ejemplarDAO.insertEjemplar(ejemplar);
                cargarTodosLosEjemplares(); // Recargar la tabla después de la inserción
                limpiarFormulario();
                mostrarMensaje("Éxito", "Ejemplar guardado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al guardar el ejemplar: " + e.getMessage());
            }
        }
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Borrar" para eliminar un ejemplar seleccionado.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickBorrar(ActionEvent event) {
        Ejemplar ejemplar = tvEjemplares.getSelectionModel().getSelectedItem();
        if (ejemplar != null) {
            try {
                ejemplarDAO.deleteEjemplarById(ejemplar.getIdEjemplar());
                cargarTodosLosEjemplares(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Ejemplar borrado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar el ejemplar: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un ejemplar primero.");
        }
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Modificar" para actualizar un ejemplar existente.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickModificar(ActionEvent event) {
        Ejemplar ejemplar = obtenerDatosEjemplarDeFormulario();
        if (ejemplar != null) {
            try {
                ejemplarDAO.updateEjemplar(ejemplar);
                cargarTodosLosEjemplares(); // Recargar la tabla después de la modificación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Ejemplar modificado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al modificar el ejemplar: " + e.getMessage());
            }
        }
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Buscar" para buscar ejemplares por ISBN o por ID.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickBuscar(ActionEvent event) {
        String isbnABuscar = tfISBNBuscar.getText().trim();
        String idABuscar = tfIdBuscar.getText().trim();

        try {
            if(!isbnABuscar.isEmpty() && !idABuscar.isEmpty() ){
                Ejemplar ejemplarEncontrado = ejemplarDAO.getEjemplarByIdISBN(Integer.parseInt(idABuscar), isbnABuscar);
                if(ejemplarEncontrado != null){
                    tvEjemplares.getItems().clear(); // Limpiar la tabla
                    tvEjemplares.getItems().add(ejemplarEncontrado); // Agregar el ejemplar encontrado a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún ejemplar con el ISBN y el id proporcionado.");
                }
            }else if(!isbnABuscar.isEmpty()){
                List<Ejemplar> ejemplaresEncontrado = ejemplarDAO.getEjemplarByISBN(isbnABuscar);
                if(!ejemplaresEncontrado.isEmpty()){
                    tvEjemplares.getItems().clear(); // Limpiar la tabla
                    tvEjemplares.getItems().addAll(ejemplaresEncontrado); // Agregar los ejemplares encontrados a la tabla
                }else{
                    mostrarAdvertencia("No se encontró ningún ejemplar con el ISBN proporcionado.");
                }
            } else{
                Ejemplar ejemplarEncontrado = ejemplarDAO.getEjemplarById(Integer.parseInt(idABuscar));
                if(ejemplarEncontrado != null){
                    tvEjemplares.getItems().clear(); // Limpiar la tabla
                    tvEjemplares.getItems().addAll(ejemplarEncontrado); // Agregar el ejemplar encontrado a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún ejemplar con el id proporcionado.");
                }

            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el libro: " + e.getMessage());
        } catch (IllegalArgumentException e){
            mostrarAdvertencia("Error al buscar el libro: " + e.getMessage());
        }
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Mostrar Todos" para cargar todos los ejemplares.
     * @param event Evento de acción.
     */
    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosEjemplares();
    }

    /**
     * Método que se ejecuta al hacer clic en un elemento de la TableView de ejemplares.
     * @param event Evento de ratón.
     */
    @FXML
    private void onClickTvEjemplares(MouseEvent event) {
        Ejemplar ejemplarSeleccionado = tvEjemplares.getSelectionModel().getSelectedItem();
        if (ejemplarSeleccionado != null) {
            // Mostrar los datos del ejemplar seleccionado en el formulario
            mostrarDatosEjemplarEnFormulario(ejemplarSeleccionado);
        }
    }

    /**
     * Carga todos los ejemplares en la TableView.
     */
    private void cargarTodosLosEjemplares() {
        try {
            List<Ejemplar> ejemplares = ejemplarDAO.getAllEjemplares();
            tvEjemplares.getItems().clear();
            tvEjemplares.getItems().addAll(ejemplares);
        } catch (SQLException e) {
            mostrarError("Error al cargar los ejemplares: " + e.getMessage());
        }
    }

    /**
     * Obtiene los datos de un ejemplar del formulario.
     * @return Objeto Ejemplar con los datos del formulario.
     */
    private Ejemplar obtenerDatosEjemplarDeFormulario() {
        int idEjemplar;
        String libroISBN = tfLibroISBN.getText().trim();
        String estado = tfEstado.getText().trim();

        try {
            idEjemplar = Integer.parseInt(tfIdEjemplar.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El ID del ejemplar debe ser un número entero.");
            return null;
        }

        try {
            Ejemplar ejemplar = new Ejemplar(idEjemplar, libroISBN, estado);
            return ejemplar;
        } catch (IllegalArgumentException e) {
            mostrarAdvertencia("Error al crear el ejemplar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Muestra los datos de un ejemplar en el formulario.
     * @param ejemplar Objeto Ejemplar a mostrar.
     */
    private void mostrarDatosEjemplarEnFormulario(Ejemplar ejemplar) {
        tfIdEjemplar.setText(String.valueOf(ejemplar.getIdEjemplar()));
        tfLibroISBN.setText(ejemplar.getLibroISBN());
        tfEstado.setText(ejemplar.getEstado());
    }

    /**
     * Limpia el formulario.
     */
    private void limpiarFormulario() {
        tfIdEjemplar.clear();
        tfLibroISBN.clear();
        tfEstado.clear();
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


