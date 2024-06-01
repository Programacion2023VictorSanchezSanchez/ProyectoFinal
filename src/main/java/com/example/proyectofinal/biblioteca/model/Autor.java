package com.example.proyectofinal.biblioteca.model;

/**
 * Clase que representa un autor en la biblioteca.
 */
public class Autor {
    // Atributos
    private int idAutor; // Autoincremental
    private String nombre;

    // Constructores

    /**
     * Constructor por defecto que inicializa un autor con valores predeterminados.
     */
    public Autor() {
        this(999,"Ejemplo");
    }

    /**
     * Constructor que inicializa un autor con su nombre.
     *
     * @param nombre El nombre del autor.
     */
    public Autor(String nombre) {
        this(999, nombre);
    }

    /**
     * Constructor que inicializa un autor con todos sus atributos.
     *
     * @param idAutor El ID del autor.
     * @param nombre  El nombre del autor.
     */
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

