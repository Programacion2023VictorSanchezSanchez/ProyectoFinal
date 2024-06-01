package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.BibliotecaAPP;
import com.example.proyectofinal.biblioteca.model.Socio;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BibliotecaController {

    private ObservableList<Socio> listaSocios;

    @FXML
    private Button btAutores;

    @FXML
    private Button btEjemplaresDisponibles;

    @FXML
    private Button btGeneros;

    @FXML
    private Button btLibros;

    @FXML
    private Button btLibrosGenero;

    @FXML
    private Button btPrestamos;

    @FXML
    private Button btSocios;

    @FXML
    void onClickAutores(ActionEvent event) {

    }

    @FXML
    void onClickEjemplaresDisponibles(ActionEvent event) {

    }

    @FXML
    void onClickGeneros(ActionEvent event) {

    }

    @FXML
    void onClickLibros(ActionEvent event) {

    }

    @FXML
    void onClickLibrosGenero(ActionEvent event) {

    }

    @FXML
    void onClickPrestamos(ActionEvent event) {

    }

    @FXML
    void onClickSocios(ActionEvent event) {
        abrirSocios();
    }
    private void abrirSocios(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/socio-view.fxml"));
            Parent root = loader.load();

            SocioController socioController=loader.getController();
            socioController.initialize(listaSocios);
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Socios");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

}

