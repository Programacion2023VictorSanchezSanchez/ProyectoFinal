package com.example.proyectofinal.biblioteca.model;

public class Socio {
    // Atributos
    private int idSocio;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    // Constructores

    public Socio() {
        this(999,"Mariano", "Rajoy", "987654321", "ejemplo@gmail.com");
    }
    public Socio(String nombre, String apellidos, String telefono, String email) {
        this(999, nombre, apellidos, telefono, email);

    }
    public Socio(int idSocio, String nombre, String apellidos, String telefono, String email){
        setIdSocio(idSocio);
        setNombre(nombre);
        setApellidos(apellidos);
        setTelefono(telefono);
        setEmail(email);
    }

    // Getters
    public int getIdSocio() {
        return this.idSocio;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    public String getTelefono() {
        return this.telefono;
    }
    public String getEmail() {
        return this.email;
    }

    // Setters
    public void setIdSocio(int idSocio) throws IllegalArgumentException {
        if (idSocio <= 0) {
            throw new IllegalArgumentException("El ID del socio debe ser un número positivo");
        }
        this.idSocio = idSocio;
    }
    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public void setTelefono(String telefono) throws IllegalArgumentException {
        if (!telefono.matches("\\d{9}")) {
            throw new IllegalArgumentException("El teléfono debe contener exactamente 9 dígitos");
        }
        this.telefono = telefono;
    }
    public void setEmail(String email) throws IllegalArgumentException {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("El email no es válido");
        }
        this.email = email;
    }

    // To String
    @Override
    public String toString() {
        return "Socio{" +
                "idSocio=" + this.idSocio +
                ", nombre='" + this.nombre + '\'' +
                ", apellidos='" + this.apellidos + '\'' +
                ", telefono='" + this.telefono + '\'' +
                ", email='" + this.email + '\'' +
                '}';
    }
}
