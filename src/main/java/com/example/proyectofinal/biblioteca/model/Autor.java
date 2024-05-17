package com.example.proyectofinal.biblioteca.model;

public class Autor {
    // Atributos
    private int idAutor; // Autoincremental
    private String nombre;

    // Constructores
    public Autor() {
        this(999,"Ejemplo");
    }
    public Autor(String nombre) {
        this(999, nombre);
    }
    public Autor(int idAutor, String nombre) {
        setIdAutor(idAutor);
        setNombre(nombre);
    }

    // Getters
    public int getIdAutor() {
        return this.idAutor;
    }
    public String getNombre() {
        return this.nombre;
    }

    // Setters
    public void setIdAutor(int idAutor) {
        if(idAutor <= 0){
            throw new IllegalArgumentException("El id del Autor no puede ser menor o igual a 0");
        }else{
            this.idAutor = idAutor;
        }
    }
    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    // To String
    @Override
    public String toString() {
        return "Autor{" +
                "idAutor=" + this.idAutor +
                ", nombre='" + this.nombre + '\'' +
                '}';
    }
}

