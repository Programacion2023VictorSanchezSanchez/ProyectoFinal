package com.example.proyectofinal.biblioteca.model;

/**
 * Clase que representa un género en la biblioteca.
 */
public class Genero {
    // Atributos
    private String nombre;

    // Constructores

    /**
     * Constructor por defecto que inicializa un género con un nombre vacío.
     */
    public Genero() {
        this.nombre = "";
    }

    /**
     * Constructor que inicializa un género con el nombre especificado.
     *
     * @param nombre El nombre del género.
     */
    public Genero(String nombre) {
        setNombre(nombre);
    }

    // Getters
    public String getNombre() {
        return this.nombre;
    }

    // Setters
    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    // To String
    @Override
    public String toString() {
        return "Genero{" +
                "nombre='" + this.nombre + '\'' +
                '}';
    }
}

