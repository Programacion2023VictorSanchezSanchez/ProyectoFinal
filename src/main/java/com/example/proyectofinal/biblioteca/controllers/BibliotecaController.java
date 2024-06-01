package com.example.proyectofinal.biblioteca.controllers;

import com.example.proyectofinal.biblioteca.BibliotecaAPP;
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
        abrirAutores();
    }

    @FXML
    void onClickEjemplaresDisponibles(ActionEvent event) {
        abrirEjemplares();
    }

    @FXML
    void onClickGeneros(ActionEvent event) {
        abrirGeneros();
    }

    @FXML
    void onClickLibros(ActionEvent event) {
        abrirLibros();
    }

    @FXML
    void onClickLibrosGenero(ActionEvent event) {
        abrirLibroGenero();
    }

    @FXML
    void onClickPrestamos(ActionEvent event) {
        abrirPrestamos();
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
            socioController.initialize();
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

    private void abrirAutores(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/autor-view.fxml"));
            Parent root = loader.load();

            AutorController autorController=loader.getController();
            autorController.initialize();
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Autores");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void abrirLibros(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/libro-view.fxml"));
            Parent root = loader.load();

            LibroController LibroController=loader.getController();
            LibroController.initialize();
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Libros");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void abrirGeneros(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/genero-view.fxml"));
            Parent root = loader.load();

            GeneroController generoController=loader.getController();
            generoController.initialize();
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Generos");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void abrirLibroGenero(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/libroGenero-view.fxml"));
            Parent root = loader.load();

            LibroGeneroController libroGeneroController=loader.getController();
            libroGeneroController.initialize();
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Libro por Genero");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void abrirEjemplares(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/ejemplar-view.fxml"));
            Parent root = loader.load();

            EjemplarController ejemplarController=loader.getController();
            ejemplarController.initialize();
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ejemplares");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void abrirPrestamos(){
        try{
            FXMLLoader loader = new FXMLLoader(BibliotecaAPP.class.getResource("views/prestamo-view.fxml"));
            Parent root = loader.load();

            PrestamoController prestamoController=loader.getController();
            prestamoController.initialize();
            Scene scene= new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Prestamos");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    }



