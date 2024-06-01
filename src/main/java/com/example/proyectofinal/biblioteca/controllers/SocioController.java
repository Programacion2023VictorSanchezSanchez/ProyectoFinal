package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.model.Socio;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SocioController implements Initializable {

    private ObservableList<Socio> listaSocios;

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
    private TableColumn<?, ?> tcApellidos;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<?, ?> tcIdSocio;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcTelefono;

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
    private TableView<?> tvSocios;

    @FXML
    void onClickBorrar(ActionEvent event) {

    }

    @FXML
    void onClickBuscar(ActionEvent event) {

    }

    @FXML
    void onClickGuardar(ActionEvent event) {

    }

    @FXML
    void onClickModificar(ActionEvent event) {

    }

    @FXML
    void onClickMostrarTodos(ActionEvent event) {

    }

    @FXML
    void onClickTvAlumnos(MouseEvent event) {

    }
    public void initialize(ObservableList<Socio> listaSocios){ }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

}


