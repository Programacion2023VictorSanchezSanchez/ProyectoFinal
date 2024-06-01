package com.example.proyectofinal.biblioteca.model;

import java.sql.Date;

public class Prestamo {
    // Atributos
    private int idPrestamo;
    private int disponibles;
    private int idSocio;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean isDevuelto;

    // Constructores
    public Prestamo() {
    }

    public Prestamo(int idPrestamo, int disponibles, int idSocio, Date fechaInicio, Date fechaFin, boolean isDevuelto) {
        setIdPrestamo(idPrestamo);
        setDisponibles(disponibles);
        setIdSocio(idSocio);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setDevuelto(isDevuelto);
    }

    // Getters
    public int getIdPrestamo() {
        return this.idPrestamo;
    }
    public int getDisponibles() {
        return this.disponibles;
    }
    public int getIdSocio() {
        return this.idSocio;
    }
    public Date getFechaInicio() {
        return this.fechaInicio;
    }
    public Date getFechaFin() {
        return this.fechaFin;
    }
    public boolean isDevuelto() {
        return this.isDevuelto;
    }

    // Setters
    public void setIdPrestamo(int idPrestamo) throws IllegalArgumentException {
        if (idPrestamo <= 0) {
            throw new IllegalArgumentException("El ID del prestamo debe ser un número positivo");
        }else{
            this.idPrestamo = idPrestamo;
        }

    }
    public void setDisponibles(int disponibles) throws IllegalArgumentException {
        if (disponibles < 0) {
            throw new IllegalArgumentException("La cantidad de disponibles no puede ser negativa");
        }
        this.disponibles = disponibles;
    }
    public void setIdSocio(int idSocio) throws IllegalArgumentException {
        if (idSocio <= 0) {
            throw new IllegalArgumentException("El ID del socio debe ser un número positivo");
        }
        this.idSocio = idSocio;
    }
    public void setFechaInicio(Date fechaInicio) throws IllegalArgumentException {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        this.fechaInicio = fechaInicio;
    }
    public void setFechaFin(Date fechaFin) throws IllegalArgumentException {
        if (fechaFin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede ser nula");
        }
        this.fechaFin = fechaFin;
    }
    public void setDevuelto(boolean devuelto) {
        isDevuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "idPrestamo=" + idPrestamo +
                ", disponibles=" + disponibles +
                ", idSocio=" + idSocio +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", isDevuelto=" + isDevuelto +
                '}';
    }
}




