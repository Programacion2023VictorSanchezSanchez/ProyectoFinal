package com.example.proyectofinal.biblioteca.model;

public class Ejemplar {
    // Atributos
    private int idEjemplar;
    private String libroISBN;
    private String estado;

    // Constructores
    public Ejemplar() {
        this(5, "1111222233334", "disponible");
    }

    public Ejemplar(String libroISBN, String estado){
        this(5, libroISBN, estado);
    }

    public Ejemplar(int idEjemplar, String libroISBN, String estado) {
        setIdEjemplar(idEjemplar);
        setLibroISBN(libroISBN);
        setEstado(estado);

    }

    // Getters
    public int getIdEjemplar() {
        return this.idEjemplar;
    }

    public String getLibroISBN() {
        return this.libroISBN;
    }

    public String getEstado() {
        return this.estado;
    }

    // Setters
    public void setIdEjemplar(int idEjemplar) throws IllegalArgumentException {
        if (idEjemplar <= 0) {
            throw new IllegalArgumentException("La cantidad de disponibles no puede ser negativa");
        }
        this.idEjemplar = idEjemplar;
    }

    public void setLibroISBN(String libroISBN) {
        this.libroISBN = libroISBN;
    }

    public void setEstado(String estado)  {

        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ejemplar{" +
                "idEjemplar=" + idEjemplar +
                ", libroISBN='" + libroISBN + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

