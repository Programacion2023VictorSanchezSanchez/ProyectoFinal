package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.db.PrestamoDAO;
import com.example.proyectofinal.biblioteca.model.Prestamo;
import com.example.proyectofinal.biblioteca.model.Socio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrestamoController implements Initializable {

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
    private Label lbIdPrestamo;

    @FXML
    private Label lbIdEjemplar;

    @FXML
    private Label lbLibroISBN;

    @FXML
    private Label lbIdSocio;

    @FXML
    private Label lbFechaInicio;

    @FXML
    private Label lbFechaFin;

    @FXML
    private Label lbEstado;

    @FXML
    private TableColumn<Prestamo, Integer> tcIdPrestamo;

    @FXML
    private TableColumn<Prestamo, Integer> tcIdEjemplar;

    @FXML
    private TableColumn<Prestamo, String> tcLibroISBN;

    @FXML
    private TableColumn<Prestamo, Integer> tcIdSocio;

    @FXML
    private TableColumn<Prestamo, String> tcFechaInicio;

    @FXML
    private TableColumn<Prestamo, String> tcFechaFin;

    @FXML
    private TableColumn<Prestamo, String> tcEstado;

    @FXML
    private TextField tfIdPrestamo;

    @FXML
    private TextField tfIdEjemplar;

    @FXML
    private TextField tfLibroISBN;

    @FXML
    private TextField tfIdSocio;

    @FXML
    private TextField tfFechaInicio;

    @FXML
    private TextField tfFechaFin;

    @FXML
    private TextField tfEstado;

    @FXML
    private TextField tfFechaInicioBuscar;

    @FXML
    private TextField tfFechaFinBuscar;

    @FXML
    private TextField tfPrestamoBuscar;

    @FXML
    private TextField tfSocioBuscar;

    @FXML
    private TextField tfEjemplarBuscar;

    @FXML
    private TableView<Prestamo> tvPrestamos;

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public void initialize() {
        // Configurar las columnas de la TableView
        tcIdPrestamo.setCellValueFactory(new PropertyValueFactory<>("idPrestamo"));
        tcIdEjemplar.setCellValueFactory(new PropertyValueFactory<>("idEjemplar"));
        tcLibroISBN.setCellValueFactory(new PropertyValueFactory<>("libroISBN"));
        tcIdSocio.setCellValueFactory(new PropertyValueFactory<>("idSocio"));
        tcFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tcFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar todos los préstamos en la TableView al inicio
        cargarTodosLosPrestamos();
    }

    @FXML
    private void onClickGuardar(ActionEvent event) {
        Prestamo prestamo = obtenerDatosPrestamoDeFormulario();
        try {
            prestamoDAO.insertPrestamo(prestamo);
            cargarTodosLosPrestamos(); // Recargar la tabla después de la inserción
            limpiarFormulario();
            mostrarMensaje("Éxito", "Préstamo guardado exitosamente", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarError("Error al guardar el préstamo: " + e.getMessage());
        }
    }

    @FXML
    private void onClickBorrar(ActionEvent event) {
        Prestamo prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo != null) {
            try {
                prestamoDAO.deletePrestamoById(prestamo.getIdPrestamo());
                cargarTodosLosPrestamos(); // Recargar la tabla después de la eliminación
                limpiarFormulario();
                mostrarMensaje("Éxito", "Préstamo borrado exitosamente", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                mostrarError("Error al borrar el préstamo: " + e.getMessage());
            }
        } else {
            mostrarAdvertencia("Por favor, seleccione un préstamo primero.");
        }
    }

    @FXML
    private void onClickModificar(ActionEvent event) {
        Prestamo prestamo = obtenerDatosPrestamoDeFormulario();
        try {
            prestamoDAO.updatePrestamo(prestamo);
            cargarTodosLosPrestamos(); // Recargar la tabla después de la modificación
            limpiarFormulario();
            mostrarMensaje("Éxito", "Préstamo modificado exitosamente", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarError("Error al modificar el préstamo: " + e.getMessage());
        }
    }

    @FXML
    private void onClickBuscar(ActionEvent event) {
        String idPrestamoABuscar = tfPrestamoBuscar.getText().trim();
        String idSocioABuscar = tfSocioBuscar.getText().trim();
        String idEjemplarABuscar = tfEjemplarBuscar.getText().trim();

        try {
            if(!idSocioABuscar.isEmpty() && !idPrestamoABuscar.isEmpty() && !idEjemplarABuscar.isEmpty() ){
                Prestamo prestamo = prestamoDAO.getPrestamoByIds(Integer.parseInt(idPrestamoABuscar), Integer.parseInt(idSocioABuscar), Integer.parseInt(idEjemplarABuscar));
                if(prestamo != null){
                    tvPrestamos.getItems().clear(); // Limpiar la tabla
                    tvPrestamos.getItems().add(prestamo); // Agregar el prestamo encontrado a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún prstamo con los IDs proporcionados");
                }
            }else if(!idPrestamoABuscar.isEmpty()){
                Prestamo prestamo = prestamoDAO.getPrestamoByPrestamoId(Integer.parseInt(idPrestamoABuscar));
                if(prestamo != null){
                    tvPrestamos.getItems().clear(); // Limpiar la tabla
                    tvPrestamos.getItems().add(prestamo); // Agregar el prestamo encontrado a la tabla
                }else{
                    mostrarAdvertencia("No se encontró ningún prestamo con el ID proporcionado");
                }
            } else if(!idSocioABuscar.isEmpty()){
                List<Prestamo> prestamos = prestamoDAO.getPrestamoBySocioId(Integer.parseInt(idSocioABuscar));
                if(!prestamos.isEmpty()){
                    tvPrestamos.getItems().clear(); // Limpiar la tabla
                    tvPrestamos.getItems().addAll(prestamos); // Agregar los prestamos encontrados a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún prestamo con el IdSocio proporcionado.");
                }

            } else if(!idEjemplarABuscar.isEmpty()){
                List<Prestamo> prestamos = prestamoDAO.getPrestamoByEjemplarId(Integer.parseInt(idEjemplarABuscar));
                if(!prestamos.isEmpty()){
                    tvPrestamos.getItems().clear(); // Limpiar la tabla
                    tvPrestamos.getItems().addAll(prestamos); // Agregar los prestamos encontrados a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún prestamo con el idEjemplar proporcionado");
                }
            }else{
                mostrarAdvertencia("Solo se puede filtrar por los 3 campos a la vez o de manera individual");
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el prestamo: " + e.getMessage());
        } catch (IllegalArgumentException e){
            mostrarAdvertencia("Error al buscar al el prestamo: " + e.getMessage());
        }
    }

    @FXML
    private void onClickBuscarFecha(ActionEvent event){
        String fechaInicioBuscar = tfFechaInicioBuscar.getText().trim();
        String fechaFinBuscar = tfFechaFinBuscar.getText().trim();

        try {
            if(!fechaInicioBuscar.isEmpty() && !fechaFinBuscar.isEmpty() ){
                if(fechaInicioBuscar.matches("\\d{4}-\\d{2}-\\d{2}") && fechaFinBuscar.matches("\\d{4}-\\d{2}-\\d{2}")){


                List<Prestamo> prestamos = prestamoDAO.getPrestamosDate(fechaInicioBuscar, fechaFinBuscar);
                if(!prestamos.isEmpty()){
                    tvPrestamos.getItems().clear(); // Limpiar la tabla
                    tvPrestamos.getItems().addAll(prestamos); // Agregar los prestamos encontrado a la tabla
                } else{
                    mostrarAdvertencia("No se encontró ningún prestamo entre esas fechas");
                }
                }else{
                    mostrarAdvertencia("El formato correcto de fecha es YYYY-MM-DD");
                }
            } else{
                mostrarAdvertencia("Debes de introducir ambas fechas para buscar en ese rango");
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el prestamo: " + e.getMessage());
        } catch (IllegalArgumentException e){
            mostrarAdvertencia("Error al buscar el prestamo: " + e.getMessage());
        }
    }


    @FXML
    private void onClickMostrarTodos(ActionEvent event) {
        cargarTodosLosPrestamos();
    }
    @FXML
    private void onClickMorosos(ActionEvent event){
        cargarMorosos();
    }

    @FXML
    private void onClickTvPrestamos() {
        Prestamo prestamoSeleccionado = tvPrestamos.getSelectionModel().getSelectedItem();
        if (prestamoSeleccionado != null) {
            // Mostrar los datos del préstamo seleccionado en el formulario
            mostrarDatosPrestamoEnFormulario(prestamoSeleccionado);
        }
    }

    private void cargarTodosLosPrestamos() {
        try {
            List<Prestamo> prestamos = prestamoDAO.getAllPrestamos();
            tvPrestamos.getItems().clear();
            tvPrestamos.getItems().addAll(prestamos);
        } catch (SQLException e) {
            mostrarError("Error al cargar los préstamos: " + e.getMessage());
        }
    }

    private void cargarMorosos(){
        try {
            List<Prestamo> prestamos = prestamoDAO.selectPrestamoMorosos();
            tvPrestamos.getItems().clear();
            tvPrestamos.getItems().addAll(prestamos);
        } catch (SQLException e) {
            mostrarError("Error al cargar los préstamos: " + e.getMessage());
        }
    }
    @FXML
    private Prestamo obtenerDatosPrestamoDeFormulario() {
        int idPrestamo;
        int idEjemplar;
        int idSocio;
        String libroISBN = tfLibroISBN.getText().trim();
        String fechaInicio = tfFechaInicio.getText().trim();
        String fechaFin = tfFechaFin.getText().trim();
        String estado = tfEstado.getText().trim();

        try {
            idPrestamo = Integer.parseInt(tfIdPrestamo.getText().trim());
            idSocio = Integer.parseInt(tfIdSocio.getText().trim());
            idEjemplar = Integer.parseInt(tfIdEjemplar.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("Los IDs deben ser un número entero.");
            return null;
        }

        try {
            Prestamo prestamo = new Prestamo(idPrestamo, idEjemplar,idSocio, fechaInicio, fechaFin,libroISBN, estado);
            return prestamo;
        } catch (IllegalArgumentException e) {
            mostrarAdvertencia("Error al crear el prestamo: " + e.getMessage());
            return null;
        }
    }

    private void mostrarDatosPrestamoEnFormulario(Prestamo prestamo) {
        tfIdPrestamo.setText(String.valueOf(prestamo.getIdPrestamo()));
        tfIdEjemplar.setText(String.valueOf(prestamo.getIdEjemplar()));
        tfLibroISBN.setText(prestamo.getLibroISBN());
        tfIdSocio.setText(String.valueOf(prestamo.getIdSocio()));
        tfFechaInicio.setText(String.valueOf(prestamo.getFechaInicio()));
        tfFechaFin.setText(String.valueOf(prestamo.getFechaFin()));
        tfEstado.setText(prestamo.getEstado());
    }

    private void limpiarFormulario() {
        tfIdPrestamo.clear();
        tfIdEjemplar.clear();
        tfLibroISBN.clear();
        tfIdSocio.clear();
        tfFechaInicio.clear();
        tfFechaFin.clear();
        tfEstado.clear();
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

