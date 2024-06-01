package com.example.proyectofinal.biblioteca.model;

/**
 * Clase que representa un préstamo de la biblioteca.
 */
public class Prestamo {
    // Atributos
    private int idPrestamo;
    private int idEjemplar;
    private String libroISBN;
    private int idSocio;
    private String fechaInicio;
    private String fechaFin;
    private String estado;

    // Constructores

    /**
     * Constructor que inicializa un prestamo con todos los atributos especificados
     * @param idPrestamo ID del prestamo
     * @param idEjemplar ID del ejemplar
     * @param idSocio ID del socio
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha final de devolucion
     * @param libroISBN ISBN del libro
     * @param estado Estado de si esta devuelto o no
     */
    public Prestamo(int idPrestamo, int idEjemplar, int idSocio, String fechaInicio, String fechaFin, String libroISBN, String estado) {
        setIdPrestamo(idPrestamo);
        setIdEjemplar(idEjemplar);
        setIdSocio(idSocio);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setLibroISBN(libroISBN);
        setEstado(estado);
    }

    // Getters
    public int getIdPrestamo() {
        return this.idPrestamo;
    }

    public int getIdEjemplar() {
        return this.idEjemplar;
    }

    public String getLibroISBN() {
        return this.libroISBN;
    }

    public int getIdSocio() {
        return this.idSocio;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public String getEstado() {
        return this.estado;
    }

    // Setters
    public void setIdPrestamo(int idPrestamo) throws IllegalArgumentException {
        if (idPrestamo <= 0) {
            throw new IllegalArgumentException("El ID del prestamo debe ser un número positivo");
        } else {
            this.idPrestamo = idPrestamo;
        }

    }
    public void setIdEjemplar(int idEjemplar) throws IllegalArgumentException {
        if (idEjemplar <= 0) {
            throw new IllegalArgumentException("El ID del ejemplar no puede ser negativo");
        }
        this.idEjemplar = idEjemplar;
    }
    public void setLibroISBN(String libroISBN) throws IllegalArgumentException {
        if (!libroISBN.matches("\\d{13}")) {
            throw new IllegalArgumentException("El ISBN debe contener exactamente 13 dígitos");
        } else {
            this.libroISBN = libroISBN;
        }
    }
    public void setIdSocio(int idSocio) throws IllegalArgumentException {
        if (idSocio <= 0) {
            throw new IllegalArgumentException("El ID del socio debe ser un número positivo");
        }
        this.idSocio = idSocio;
    }
    public void setFechaInicio(String fechaInicio) throws IllegalArgumentException {
        if (!fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("La fecha debe de tener formato YYYY-MM-DD");
        }
        this.fechaInicio = fechaInicio;
    }
    public void setFechaFin(String fechaFin) throws IllegalArgumentException {
        if (!fechaFin.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("La fecha debe de tener formato YYYY-MM-DD");
        }
        this.fechaFin = fechaFin;
    }
    public void setEstado(String estado) throws IllegalArgumentException {
        String estadoLowerCase = estado.toLowerCase();
        if (estadoLowerCase.equals("devuelto") || estadoLowerCase.equals("no devuelto")) {
            this.estado = estadoLowerCase;
        } else {
            throw new IllegalArgumentException("El estado debe ser 'Devuelto' o 'No devuelto'.");
        }

    }
    @Override
    public String toString () {
        return "Prestamo{" +
                "idPrestamo=" + idPrestamo +
                ", idEjemplar=" + idEjemplar +
                ", libroISBN='" + libroISBN + '\'' +
                ", idSocio=" + idSocio +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                '}';
    }
}




