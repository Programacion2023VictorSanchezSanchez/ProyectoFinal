package com.example.proyectofinal.biblioteca.model;

public class Genero {
    // Atributos
    private String nombre;

    // Constructores
    public Genero() {
        this.nombre = "";
    }
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

