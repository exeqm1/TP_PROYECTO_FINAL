/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Lugar;
import Modelo.Pelicula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class PeliculaData {

    private Connection conex ;

    public PeliculaData(Conexion conex) {
        this.conex = conex.conectar();
    }

    // Metodos CRUD
    public void agregarPelicula(Pelicula p) {

        String sql = "INSERT INTO `pelicula`( `titulo`, `director`, `actores`, `origen`, `genero`, `estreno`, `enCartelera`) VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conex.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDirector());
            ps.setString(3, p.getActores());
            ps.setString(4, p.getOrigen());
            ps.setString(5, p.getGenero());
            ps.setDate(6, Date.valueOf(p.getEstreno()));
            ps.setBoolean(7, p.isEnCartelera());
            

            int filasAgregadas = ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setIdPelicula(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar pelicula. ");
            }

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Pelicula guardada con el id: " + p.getIdPelicula());
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar pelicula. " + ex.getMessage());
        }

    }

    public Pelicula buscarPelicula(int idPeli) {
        Pelicula peli = null;

        String search = "SELECT * FROM pelicula WHERE id_Pelicula = ?";

        try (PreparedStatement statement = conex.prepareStatement(search)) {
            statement.setInt(1, idPeli);

            try (ResultSet rsBuscar = statement.executeQuery()) {

                if (rsBuscar.next()) {
                    peli = new Pelicula();
                    peli.setIdPelicula(rsBuscar.getInt("id_Pelicula"));
                    peli.setTitulo(rsBuscar.getString("titulo"));
                    peli.setDirector(rsBuscar.getString("director"));
                    peli.setActores(rsBuscar.getString("actores"));
                    peli.setOrigen(rsBuscar.getString("origen"));
                    peli.setGenero(rsBuscar.getString("genero"));
                    peli.setEstreno(rsBuscar.getDate("estreno").toLocalDate());
                    peli.setEnCartelera(rsBuscar.getBoolean("enCartelera"));
                } else {
                    System.out.println("No se encontró la pelicula indicado.");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar pelicula. " + e.getMessage());
        }
        return peli;
    }
    
   
    public void borrarPelicula(int idPeli) {
        String delete = "DELETE FROM pelicula WHERE id_Pelicula = ?";

        try (PreparedStatement statement = conex.prepareStatement(delete)) {
            statement.setInt(1, idPeli);

            int filasAgregadas = statement.executeUpdate();

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Pelicula con ID: " + idPeli + " eliminada correctamente. Filas modificadas: " + filasAgregadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la pelicula para eliminar. Filas modificadas: " + filasAgregadas);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar pelicula. " + e.getMessage());
        }
    }
 public void bajaPelicula(int id_Pelicula) {
        String sql = "UPDATE pelicula SET enCartelera = 0 WHERE id_Pelicula = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, id_Pelicula);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Película dada de baja");
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al dar de baja película ");
        }
    }
  public List<Pelicula> listarPeliculasEnCartelera() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT * FROM pelicula WHERE enCartelera = 1 ";

        try (PreparedStatement ps = conex.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pelicula peli = new Pelicula();
                peli.setIdPelicula(rs.getInt("id_Pelicula"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setDirector(rs.getString("director"));
                peli.setActores(rs.getString("actores"));
                peli.setOrigen(rs.getString("origen"));
                peli.setGenero(rs.getString("genero"));
                peli.setEstreno(rs.getDate("estreno").toLocalDate());
                peli.setEnCartelera(rs.getBoolean("enCartelera"));
               
                lista.add(peli);
            }
          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar películas en cartelera: " );
        }
        return lista;
    }
    public void actualizarPelicula(Pelicula peli) {
        String update = "UPDATE pelicula SET titulo = ?, director = ?, actores = ?, origen = ?, genero = ?, estreno = ?, enCartelera = ? WHERE id_Pelicula = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {
            statement.setString(1, peli.getTitulo());
            statement.setString(2, peli.getDirector());
            statement.setString(3, peli.getActores());
            statement.setString(4, peli.getOrigen());
            statement.setString(5, peli.getGenero());
            statement.setDate(6,java.sql.Date.valueOf( peli.getEstreno()));
            statement.setBoolean(7, peli.isEnCartelera());
            statement.setInt(8, peli.getIdPelicula());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, " Pelicula con ID " + peli.getIdPelicula() + " actualizada correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la pelicula para actualizar. Filas afectadas: " + filasAfectadas);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar pelicula: " + ex.getMessage());
        }
    }


    
    
   

    
    
    public void sacarDeCartelera(int idPeli) {
    
    String update = "UPDATE pelicula SET enCartelera = 0 WHERE id_Pelicula = ?";

    try (PreparedStatement statement = conex.prepareStatement(update)) {
        
         

        statement.setInt(1, idPeli);

        int filasAfectadas = statement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Pelicula con ID " + idPeli + " fue sacada de cartelera.");
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia: No se encontró la pelicula para sacar de cartelera.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al sacar la pelicula de cartelera: " + ex.getMessage());
    }
}
}
