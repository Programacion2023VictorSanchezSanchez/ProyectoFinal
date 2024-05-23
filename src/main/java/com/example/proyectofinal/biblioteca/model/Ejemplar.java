package com.example.proyectofinal.biblioteca.model;

public class Ejemplar {
    // Atributos
    private int disponibles;
    private String libroISBN;

    // Constructores
    public Ejemplar() {
        this(5, "1111222233334");
    }

    public Ejemplar(int disponibles, String libroISBN) {
        setDisponibles(disponibles);
        setLibroISBN(libroISBN);
    }

    // Getters
    public int getDisponibles() {
        return this.disponibles;
    }

    public String getLibroISBN() {
        return this.libroISBN;
    }

    // Setters
    public void setDisponibles(int disponibles) throws IllegalArgumentException {
        if (disponibles < 0) {
            throw new IllegalArgumentException("La cantidad de disponibles no puede ser negativa");
        }
        this.disponibles = disponibles;
    }

    public void setLibroISBN(String libroISBN) throws IllegalArgumentException {
        if (libroISBN == null || libroISBN.isEmpty()) {
            throw new IllegalArgumentException("El ISBN del libro no puede estar vacío");
        }
        this.libroISBN = libroISBN;
    }

    @Override
    public String toString() {
        return "Ejemplar{" +
                "disponibles=" + disponibles +
                ", libroISBN='" + libroISBN + '\'' +
                '}';
    }
}

