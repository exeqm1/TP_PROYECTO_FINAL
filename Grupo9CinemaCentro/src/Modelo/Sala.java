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
public class Sala {

    private int idSala;
    private int nroSala;
    private boolean apta3D;
    private int capacidad;
    private boolean activa;

    public Sala(int idSala, int nroSala, boolean apta3D, int capacidad, boolean estado) {
        this.idSala = idSala;
        this.nroSala = nroSala;
        this.apta3D = apta3D;
        this.capacidad = capacidad;
        this.activa= estado;
    }

    public Sala(int nroSala, boolean apta3D, int capacidad, boolean estado) {
        this.nroSala = nroSala;
        this.apta3D = apta3D;
        this.capacidad = capacidad;
        this.activa = estado;
    }

    public Sala() {
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getNroSala() {
        return nroSala;
    }

    public void setNroSala(int nroSala) {
        this.nroSala = nroSala;
    }

    public boolean isApta3D() {
        return apta3D;
    }

    public void setApta3D(boolean apta3D) {
        this.apta3D = apta3D;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean getEstado() {
        return activa;
    }

    public void setEstado(boolean estado) {
        this.activa = estado;
    }

    @Override
    public String toString() {
        return "Sala{" + "idSala=" + idSala + ", nroSala=" + nroSala + ", apta3D=" + apta3D + ", capacidad=" + capacidad + ", estado=" + activa + '}';
    }

}
