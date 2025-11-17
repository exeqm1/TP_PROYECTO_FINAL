/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.*;
import Modelo.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PC1
 */
public class LugarData {

    private Connection conex = null;
    private ProyeccionData proyecciondata;

    public LugarData(Conexion conex) {
        this.conex = conex.conectar();
        this.proyecciondata = new ProyeccionData(conex);
    }

    // Metodos CRUD
    public void insertButaca(Lugar asiento) {
        String insert = "INSERT INTO lugar (fila, numero, disponible, Id_proyeccion) VALUES (?,?,?,?)";

        try (PreparedStatement statement = conex.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, asiento.getFila());
            statement.setInt(2, asiento.getNumero());
            statement.setBoolean(3, asiento.getDisponible());
            statement.setInt(4, asiento.getProyeccion().getIdProyeccion());
            int filasAgregadas = statement.executeUpdate();

            try (ResultSet rsId = statement.getGeneratedKeys()) {
                if (rsId.next()) {
                    asiento.setIdLugar(rsId.getInt(1));
                }
            }

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Asiento ingresado correctamente. Filas agregadas: " + filasAgregadas);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar asiento. Filas agregadas: " + filasAgregadas);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar asiento. " + e.getMessage());
        }
    }

    public Lugar buscarButaca(int idLugar) {
        Lugar asiento = null;

        String search = "SELECT * FROM lugar WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(search)) {
            statement.setInt(1, idLugar);

            try (ResultSet rsBuscar = statement.executeQuery()) {

                if (rsBuscar.next()) {
                    asiento = new Lugar();
                    asiento.setIdLugar(rsBuscar.getInt("Id_lugar"));
                   Proyeccion p=  proyecciondata.buscarProyeccion(rsBuscar.getInt("Id_proyeccion"));
                    asiento.setFila(rsBuscar.getInt("fila"));
                    asiento.setNumero(rsBuscar.getInt("numero"));
                    asiento.setDisponible(rsBuscar.getBoolean("disponible"));
                    asiento.setProyeccion(p);
                    
                    
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el lugar indicado.");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar asiento. " + e.getMessage());
        }

        return asiento;
    }

    // Baja Fisica
    public void borrarButaca(int idLugar) {
        String delete = "DELETE FROM lugar WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(delete)) {
            statement.setInt(1, idLugar);

            int filasAgregadas = statement.executeUpdate();

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Asiento con ID: " + idLugar + " eliminado correctamente. Filas modificadas: " + filasAgregadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para eliminar. Filas modificadas: " + filasAgregadas);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar asiento. " + e.getMessage());
        }
    }

    public void actualizarButaca(Lugar lugar) {
        String update = "UPDATE lugar SET fila = ?, numero = ?, disponible= ?, Id_proyeccion = ? WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {
            statement.setInt(1, lugar.getFila());
            statement.setInt(2, lugar.getNumero());
            statement.setBoolean(3, lugar.getDisponible());
            statement.setInt(4, lugar.getProyeccion().getIdProyeccion());
            statement.setInt(5, lugar.getIdLugar());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Lugar con ID " + lugar.getIdLugar() + " actualizado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para actualizar. Filas afectadas: " + filasAfectadas);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar: " + ex.getMessage());
        }
    }

    // Metodos Adicionales
    public List<Lugar> listarButacas() {

        List<Lugar> lista = new ArrayList<>();

        String list = "SELECT * FROM lugar";

        try (PreparedStatement ps = conex.prepareStatement(list)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Lugar asiento = new Lugar();
                    Proyeccion proyeccion = new Proyeccion();
                    proyeccion.setIdProyeccion(rs.getInt("Id_proyeccion"));
                    asiento.setIdLugar(rs.getInt("Id_lugar"));
                    asiento.setProyeccion(proyeccion);
                    asiento.setFila(rs.getInt("fila"));
                    asiento.setNumero(rs.getInt("numero"));
                    asiento.setDisponible(rs.getBoolean("disponible"));
                    lista.add(asiento);
                }
            }
        } catch (SQLException ex) {
            System.out.println(" Error al listar lugares: " + ex.getMessage());
        }
        return lista;
    }

   
    public void reservarButaca(Lugar asiento) {
        String update = "UPDATE lugar SET disponible = 0 WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {

           
            statement.setInt(1, asiento.getIdLugar());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Lugar con ID " + asiento.getIdLugar() + " reservado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para reservar. Filas afectadas: " + filasAfectadas);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar: " + ex.getMessage());
        }
    }

    // Baja Logica
    public void liberarLugar(int idLugar) {

        String update = "UPDATE lugar SET disponible = 1 WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {

           
            statement.setInt(1, idLugar);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Lugar con ID " + idLugar + " liberado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para liberar.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al liberar lugar: " + ex.getMessage());
        }
    }

    public List<Lugar> lugaresDisponiblesPorProyeccion(int Id_proyeccion) {
        String sql = "SELECT * FROM lugar WHERE Id_proyeccion = ? AND disponible = 1";
        List<Lugar> lista = new ArrayList<>();

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, Id_proyeccion);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Lugar l = new Lugar();
                    l.setIdLugar(rs.getInt("Id_lugar"));
                    l.setFila(rs.getInt("fila"));
                    l.setNumero(rs.getInt("numero"));
                    l.setDisponible(rs.getBoolean("disponible"));

                    int idpro = rs.getInt("Id_proyeccion");

                    Proyeccion pro = proyecciondata.buscarProyeccion(idpro);
                    
                    if (pro != null) {
                    l.setProyeccion(pro);

                    lista.add(l);
                    }
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }

        return lista;
    }

}
