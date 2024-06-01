package com.example.proyectofinal.biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase principal para la aplicación de la biblioteca.
 */
public class BibliotecaAPP extends Application {

    /**
     * Método principal que inicia la aplicación.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BibliotecaAPP.class.getResource("views/biblioteca-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Biblioteca");
        stage.setScene(scene);
        stage.show();
    }
}



