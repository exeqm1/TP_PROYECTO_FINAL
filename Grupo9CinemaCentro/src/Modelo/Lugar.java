/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class Lugar {
    private int idLugar;
    private Proyeccion proyeccion;
    private int fila;
    private int numero;
    private boolean disponible;

    public Lugar(int idLugar, Proyeccion proyeccion, int fila, int numero, boolean disponible) {
        this.idLugar = idLugar;
        this.proyeccion = proyeccion;
        this.fila = fila;
        this.numero = numero;
        this.disponible = disponible;
    }

    public Lugar(Proyeccion proyeccion, int fila, int numero, boolean disponible) {
        this.proyeccion = proyeccion;
        this.fila = fila;
        this.numero = numero;
        this.disponible = disponible;
    }

    public Lugar() {
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "N°: " + idLugar;
    }

   
    
}
