module com.example.proyectofinal.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proyectofinal.biblioteca to javafx.fxml;
    exports com.example.proyectofinal.biblioteca;
    opens com.example.proyectofinal.biblioteca.controllers to javafx.fxml;
    exports com.example.proyectofinal.biblioteca.controllers;
    opens com.example.proyectofinal.biblioteca.model to javafx.fxml;
    exports com.example.proyectofinal.biblioteca.model;
    opens com.example.proyectofinal.biblioteca.db to javafx.fxml;
    exports com.example.proyectofinal.biblioteca.db;
}