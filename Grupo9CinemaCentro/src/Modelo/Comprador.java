
package Modelo;

import java.time.LocalDate;

public class Comprador {
    private int idComprador;
    private int dni;
    private String nombre;
    private String password;
    private String medioPago;
    private LocalDate fechaNac;

    public Comprador(int idComprador, int dni, String nombre, String password,String medioPago, LocalDate fechaNac) {
        this.idComprador = idComprador;
        this.dni = dni;
        this.nombre = nombre;
        this.password = password;
        this.medioPago= medioPago;
        this.fechaNac = fechaNac;
    }

    public Comprador(int dni, String nombre, String password, String medioPago, LocalDate fechaNac) {
        this.dni = dni;
        this.nombre = nombre;
        this.password = password;
        this.medioPago = medioPago;
        this.fechaNac = fechaNac;
    }


    public Comprador() {
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
    

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
