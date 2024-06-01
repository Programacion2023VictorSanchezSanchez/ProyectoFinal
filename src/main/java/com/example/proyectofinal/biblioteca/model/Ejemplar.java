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
            throw new IllegalArgumentException("El id de Ejemplar no puede ser negativo");
        }
        this.idEjemplar = idEjemplar;
    }

    public void setLibroISBN(String libroISBN) throws IllegalArgumentException{
        if(!libroISBN.matches("\\d{13}")){
            throw new IllegalArgumentException("El ISBN debe contener exactamente 13 dígitos");
        } else{
            this.libroISBN = libroISBN;
        }
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

