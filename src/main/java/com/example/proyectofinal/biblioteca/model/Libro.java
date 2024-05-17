package com.example.proyectofinal.biblioteca.model;

public class Libro {
    // Atributos
    private String ISBN;
    private String titulo;
    private int idAutor;
    private int anyo;

    // Constructores
    public Libro() {
        this("1112223334445", "Ejemplo", 10, 1950);
    }
    public Libro(String ISBN, String titulo, int idAutor, int anyo) {
        setISBN(ISBN);
        setTitulo(titulo);
        setIdAutor(idAutor);
        setAnyo(anyo);
    }

    // Getters
    public String getISBN() {
        return this.ISBN;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public int getIdAutor() {
        return this.idAutor;
    }
    public int getAnyo() {
        return this.anyo;
    }

    // Setters
    public void setISBN(String ISBN)throws IllegalArgumentException {
        if(!ISBN.matches("\\d{13}")){
            throw new IllegalArgumentException("El ISBN debe contener exactamente 13 dígitos");
        } else{
            this.ISBN = ISBN;
        }
    }
    public void setTitulo(String titulo) throws IllegalArgumentException {
        if(titulo == null || titulo.isEmpty()){
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }else{
            this.titulo = titulo;
        }

    }
    public void setIdAutor(int idAutor) throws IllegalArgumentException{
        if(idAutor <= 0){
            throw new IllegalArgumentException("El id del Autor no puede ser menor o igual a 0");
        }else{
            this.idAutor = idAutor;
        }

    }
    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    // To String
    @Override
    public String toString() {
        return "Libro{" +
                "ISBN='" + this.ISBN + '\'' +
                ", titulo='" + this.titulo + '\'' +
                ", idAutor=" + this.idAutor +
                ", anyo=" + this.anyo +
                '}';
    }
}

