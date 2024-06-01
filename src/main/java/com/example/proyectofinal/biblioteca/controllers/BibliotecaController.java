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

/**
 * Controlador para la vista principal de la biblioteca.
 * Gestiona la apertura de las diferentes vistas de la aplicación.
 */
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

    /**
     * Abre la vista de autores.
     *
     * @param event Evento de clic en el botón de autores.
     */
    @FXML
    void onClickAutores(ActionEvent event) {
        abrirAutores();
    }

    /**
     * Abre la vista de ejemplares disponibles.
     *
     * @param event Evento de clic en el botón de ejemplares disponibles.
     */
    @FXML
    void onClickEjemplaresDisponibles(ActionEvent event) {
        abrirEjemplares();
    }

    /**
     * Abre la vista de géneros.
     *
     * @param event Evento de clic en el botón de géneros.
     */
    @FXML
    void onClickGeneros(ActionEvent event) {
        abrirGeneros();
    }

    /**
     * Abre la vista de libros.
     *
     * @param event Evento de clic en el botón de libros.
     */
    @FXML
    void onClickLibros(ActionEvent event) {
        abrirLibros();
    }

    /**
     * Abre la vista de libros por género.
     *
     * @param event Evento de clic en el botón de libros por género.
     */
    @FXML
    void onClickLibrosGenero(ActionEvent event) {
        abrirLibroGenero();
    }

    /**
     * Abre la vista de préstamos.
     *
     * @param event Evento de clic en el botón de préstamos.
     */
    @FXML
    void onClickPrestamos(ActionEvent event) {
        abrirPrestamos();
    }

    /**
     * Abre la vista de socios.
     *
     * @param event Evento de clic en el botón de socios.
     */
    @FXML
    void onClickSocios(ActionEvent event) {
        abrirSocios();
    }

    /**
     * Abre la vista de socios.
     */
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

    /**
     * Abre la vista de autores.
     */
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

    /**
     * Abre la vista de libros.
     */
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

    /**
     * Abre la vista de generos.
     */
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

    /**
     * Abre la vista de libros por genero.
     */
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

    /**
     * Abre la vista de ejemplares.
     */
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

    /**
     * Abre la vista de prestamos.
     */
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



