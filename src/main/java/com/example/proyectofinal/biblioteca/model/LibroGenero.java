package com.example.proyectofinal.biblioteca.model;

/**
 * Clase que representa la relación entre un libro y su género.
 */
public class LibroGenero {
    // Atributos
    private String libroISBN;
    private String generoNombre;

    // Constructores
    public LibroGenero() {
        this("1334351112224", "Ejemplo");
    }
    public LibroGenero(String libroISBN, String generoNombre) {
        setLibroISBN(libroISBN);
        setGeneroNombre(generoNombre);
    }

    // Getters
    public String getLibroISBN() {
        return this.libroISBN;
    }
    public String getGeneroNombre() {
        return this.generoNombre;
    }

    // Setters
    public void setLibroISBN(String libroISBN) throws IllegalArgumentException{
        if(!libroISBN.matches("\\d{13}")){
            throw new IllegalArgumentException("El ISBN debe contener exactamente 13 dígitos");
        } else{
            this.libroISBN = libroISBN;
        }

    }
    public void setGeneroNombre(String generoNombre) {
            this.generoNombre = generoNombre;

    }

    // To String
    @Override
    public String toString() {
        return "LibroGenero{" +
                "libroISBN='" + this.libroISBN + '\'' +
                ", generoNombre='" + this.generoNombre + '\'' +
                '}';
    }
}
