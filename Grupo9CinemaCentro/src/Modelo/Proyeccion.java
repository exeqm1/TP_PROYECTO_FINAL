/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import static java.time.temporal.TemporalQueries.localDate;
import static java.time.temporal.TemporalQueries.localTime;

/**
 *
 * @author Usuario
 */
public class Proyeccion {

    private int idProyeccion;
    private Pelicula pelicula;
    private Sala sala;
    private String idioma;
    private boolean es3D;
    private boolean subtitulada;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double precio;
    private boolean activa;

    public Proyeccion(Pelicula pelicula, Sala sala, String idioma, boolean es3D, boolean subtitulada, LocalTime horaInicio, LocalTime horaFin, double precio, boolean activa) {
        this.pelicula = pelicula;
        this.sala = sala;
        this.idioma = idioma;
        this.es3D = es3D;
        this.subtitulada = subtitulada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precio = precio;
        this.activa = activa;
    }

    
    
    public Proyeccion(int idProyeccion, Pelicula pelicula, Sala sala, String idioma, boolean es3D, boolean subtitulada, LocalTime horaInicio, LocalTime horaFin, double precio, boolean activa) {
        this.idProyeccion = idProyeccion;
        this.pelicula = pelicula;
        this.sala = sala;
        this.idioma = idioma;
        this.es3D = es3D;
        this.subtitulada = subtitulada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precio = precio;
        this.activa = activa;
    }

    
    
    
    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

   

 

    public Proyeccion() {
    }

    public int getIdProyeccion() {
        return idProyeccion;
    }

    public void setIdProyeccion(int idProyeccion) {
        this.idProyeccion = idProyeccion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public boolean isEs3D() {
        return es3D;
    }

    public void setEs3D(boolean es3D) {
        this.es3D = es3D;
    }

    public boolean isSubtitulada() {
        return subtitulada;
    }

    public void setSubtitulada(boolean subtitulada) {
        this.subtitulada = subtitulada;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return horaInicio + " - " + horaFin;
    }

}
