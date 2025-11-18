/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Pelicula;
import Modelo.Proyeccion;
import Modelo.Sala;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ProyeccionData {

    Connection conex = null;
    PeliculaData peliDAO;
    SalaData salaDAO;

    public ProyeccionData(Conexion conexion) {
        conex = conexion.conectar();
        peliDAO = new PeliculaData(conexion);
        salaDAO = new SalaData(conexion);
    }

    public void agregarProyeccion(Proyeccion proyeccion) {
        String sql = "INSERT INTO `proyeccion`( `Id_pelicula`, `Id_sala`, `idioma`, `es3D`, `subtitulada`, `horaInicio`, `horaFin`, `precio`, `activa`) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conex.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, proyeccion.getPelicula().getIdPelicula());
            ps.setInt(2, proyeccion.getSala().getIdSala());
            ps.setString(3, proyeccion.getIdioma());
            ps.setBoolean(4, proyeccion.isEs3D());
            ps.setBoolean(5, proyeccion.isSubtitulada());
            ps.setTime(6, Time.valueOf(proyeccion.getHoraInicio()));
            ps.setTime(7, Time.valueOf(proyeccion.getHoraFin()));
            ps.setDouble(8, proyeccion.getPrecio());
            ps.setBoolean(9, proyeccion.isActiva());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {

                proyeccion.setIdProyeccion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "proyeccion guardada con id " + proyeccion.getIdProyeccion());
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al agregar fila");
        }

    }

    public List<Proyeccion> listarProyeccion() {
        String sql = "SELECT p.Id_proyeccion, p.idioma, p.es3D, p.subtitulada, p.horaInicio, p.horaFin, p.precio, p.activa,  \n"
                + "                 pe.Id_pelicula, pe.titulo, pe.director, pe.actores, pe.genero, pe.origen, pe.estreno, pe.enCartelera, \n"
                + "                 s.Id_sala, s.nroSala, s.apta3D, s.capacidad, s.estado  \n"
                + "                 FROM proyeccion p  \n"
                + "                 JOIN pelicula pe ON p.Id_pelicula = pe.Id_pelicula  \n"
                + "                 JOIN sala s ON p.Id_sala = s.Id_sala \n"
                + "                 ORDER BY p.Id_proyeccion;";

        List<Proyeccion> lista = new ArrayList<>();

        try {
            PreparedStatement ps = conex.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Proyeccion p = new Proyeccion();

                Pelicula pel = new Pelicula();
                pel.setIdPelicula(rs.getInt("Id_pelicula"));
                p.setPelicula(pel);

                Sala sala = new Sala();

                p.setSala(sala);

                p.setIdProyeccion(rs.getInt("Id_proyeccion"));
                p.setIdioma(rs.getString("idioma"));
                p.setEs3D(rs.getBoolean("es3D"));
                p.setSubtitulada(rs.getBoolean("subtitulada"));
                p.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
                p.setHoraFin(rs.getTime("horaFin").toLocalTime());
                p.setPrecio(rs.getDouble("precio"));
                p.setActiva(rs.getBoolean("activa"));

                sala.setIdSala(rs.getInt("Id_sala"));
                sala.setApta3D(rs.getBoolean("apta3D"));
                sala.setCapacidad(rs.getInt("capacidad"));
                sala.setEstado(rs.getBoolean("activa"));
                sala.setNroSala(rs.getInt("nroSala"));

                pel.setTitulo(rs.getString("titulo"));
                pel.setDirector(rs.getString("director"));
                pel.setActores(rs.getString("actores"));
                pel.setOrigen(rs.getString("origen"));
                pel.setGenero(rs.getString("genero"));
                pel.setEstreno(rs.getDate("estreno").toLocalDate());
                pel.setEnCartelera(rs.getBoolean("enCartelera"));
                p.setPelicula(pel);

                lista.add(p);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar la lista: " + ex.getMessage());
        }

        return lista;
    }

    public void modificarProyeccion(Proyeccion pro) {

        String sql = "UPDATE `proyeccion` SET id_pelicula=?, id_sala=?, idioma=?,es3D=?,subtitulada=?,horaInicio=?,horaFin=?,precio=?, activa = ? WHERE Id_proyeccion=?";
        try {
            PreparedStatement ps = conex.prepareStatement(sql);
            ps.setInt(1, pro.getPelicula().getIdPelicula());
            ps.setInt(2, pro.getSala().getIdSala());
            ps.setString(3, pro.getIdioma());

            ps.setBoolean(4, pro.isEs3D());

            ps.setBoolean(5, pro.isSubtitulada());

            ps.setTime(6, Time.valueOf(pro.getHoraInicio()));

            ps.setTime(7, Time.valueOf(pro.getHoraFin()));

            ps.setDouble(8, pro.getPrecio());

            ps.setBoolean(9, pro.isActiva());
            ps.setInt(10, pro.getIdProyeccion());

            int rs = ps.executeUpdate();
            if (rs >= 1) {
                JOptionPane.showMessageDialog(null, "actualizacion exitosa");
            } else {
                JOptionPane.showMessageDialog(null, "no se actualizo nada");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al actualizar la proyeccion");
        }
    }

    public void eliminarProyeccion(int id_proyeccion) {
        String sql = "DELETE FROM proyeccion WHERE Id_proyeccion=?";
        try {
            PreparedStatement ps = conex.prepareStatement(sql);
            ps.setInt(1, id_proyeccion);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Proyeccion eliminada");
            } else {
                JOptionPane.showMessageDialog(null, "No se elimnino ninguna fila");
            }
            ps.close();

        } catch (SQLException ex) {
            {
                JOptionPane.showMessageDialog(null, "Error al eliminar proyeccion");
            }
        }

    }

    public List<Proyeccion> listarActivas() {
        String sql = "SELECT p.*, pe.*, s.* FROM proyeccion p JOIN pelicula pe ON pe.id_pelicula = p.id_pelicula JOIN sala s ON s.id_sala = p.id_sala WHERE p.activa = 1;";

        List<Proyeccion> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conex.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proyeccion pr = new Proyeccion();

                pr.setIdProyeccion(rs.getInt("id_proyeccion"));
                pr.setIdioma(rs.getString("idioma"));
                pr.setEs3D(rs.getBoolean("es3D"));
                pr.setSubtitulada(rs.getBoolean("subtitulada"));
                pr.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
                pr.setHoraFin(rs.getTime("horaFin").toLocalTime());

                pr.setActiva(rs.getBoolean("activa"));

                Pelicula pe = new Pelicula();

                pe.setEnCartelera(rs.getBoolean("enCartelera"));

                pe.setTitulo(rs.getString("titulo"));
                pe.setIdPelicula(rs.getInt("id_pelicula"));

                pr.setPelicula(pe);

                Sala sa = new Sala();

                sa.setIdSala(rs.getInt("id_sala"));
                sa.setNroSala(rs.getInt("nroSala"));
                pr.setSala(sa);
                lista.add(pr);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al listar proyecciones");
        }
        return lista;

    }

    public void bajaLogica(int id) {
        String sql = "UPDATE `proyeccion` SET activa=0 WHERE Id_proyeccion=?";
        PreparedStatement ps;
        try {
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id);
            int r = ps.executeUpdate();
            if (r > 0) {

                JOptionPane.showMessageDialog(null, "fila actualizada");
            } else {
                JOptionPane.showMessageDialog(null, "no se a encontrado ninguna fila");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al dar de baja");
        }

    }

    public Proyeccion buscarProyeccion(int idProyeccion) {
        Proyeccion proyeccion = null;
        String sql = "SELECT * FROM proyeccion WHERE Id_proyeccion = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, idProyeccion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proyeccion = new Proyeccion();

                    proyeccion.setIdProyeccion(idProyeccion);
                    int idPelicula = rs.getInt("Id_pelicula");
                    int idSala = rs.getInt("Id_sala");

                    proyeccion.setIdioma(rs.getString("idioma"));
                    proyeccion.setEs3D(rs.getBoolean("es3D"));
                    proyeccion.setSubtitulada(rs.getBoolean("subtitulada"));
                    proyeccion.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
                    proyeccion.setHoraFin(rs.getTime("horaFin").toLocalTime());
                    proyeccion.setPrecio(rs.getDouble("precio"));
                    proyeccion.setActiva(rs.getBoolean("activa"));
                    Pelicula pelicula = peliDAO.buscarPelicula(idPelicula);
                    Sala sala = salaDAO.buscarSala(idSala);

                    proyeccion.setPelicula(pelicula);
                    proyeccion.setSala(sala);

                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar proyección: " + ex.getMessage());
        }
        return proyeccion;
    }

    public List<Proyeccion> listarProyeccionesPorPelicula(int idPeli) {
        List<Proyeccion> listaProyecciones = new ArrayList<>();
        String sql = "SELECT * FROM proyeccion WHERE Id_pelicula = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, idPeli);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proyeccion proyeccion = new Proyeccion();

                    proyeccion.setIdProyeccion(rs.getInt("Id_proyeccion"));
                    Pelicula pelicula = peliDAO.buscarPelicula(rs.getInt("Id_pelicula"));
                    Sala sala = salaDAO.buscarSala(rs.getInt("Id_sala"));

                    if (pelicula != null && sala != null) {
                        proyeccion.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
                        proyeccion.setHoraFin(rs.getTime("horaFin").toLocalTime());
                        proyeccion.setPelicula(pelicula);
                        proyeccion.setSala(sala);
                        listaProyecciones.add(proyeccion);
                    } else {

                        System.out.println("ADVERTENCIA DE INTEGRIDAD: Proyección ID " + proyeccion.getIdProyeccion()
                                + " omitida por falta de Película o Sala.");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar proyecciones: " + ex.getMessage());
        }
        return listaProyecciones;
    }

    public List<Sala> salasPorPelicula(int idPelicula) {

        String sql = "SELECT DISTINCT s.* FROM sala s JOIN proyeccion p ON p.Id_sala = s.Id_sala WHERE p.Id_pelicula = ? AND p.activa = 1";

        List<Sala> lista = new ArrayList<>();

        try (PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setInt(1, idPelicula);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sala s = new Sala();
                s.setIdSala(rs.getInt("Id_sala"));
                s.setNroSala(rs.getInt("nroSala"));
                s.setApta3D(rs.getBoolean("apta3D"));
                s.setCapacidad(rs.getInt("capacidad"));
                s.setEstado(rs.getBoolean("estado"));

                lista.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error al traer salasPorPelicula: " + e.getMessage());
        }

        return lista;
    }

    public List<Proyeccion> proyeccionesPorPeliculaYSala(int idPelicula, int idSala) {

        String sql = "SELECT * FROM proyeccion WHERE Id_pelicula = ? AND Id_sala = ? AND activa = 1";

        List<Proyeccion> lista = new ArrayList<>();

        try (PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setInt(1, idPelicula);
            ps.setInt(2, idSala);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Proyeccion p = new Proyeccion();

                p.setIdProyeccion(rs.getInt("Id_proyeccion"));
                Pelicula pe = peliDAO.buscarPelicula(rs.getInt("Id_pelicula"));
                Sala s = salaDAO.buscarSala(rs.getInt("Id_sala"));

                p.setPelicula(pe);
                p.setSala(s);
                p.setIdioma(rs.getString("idioma"));
                p.setEs3D(rs.getBoolean("es3D"));
                p.setSubtitulada(rs.getBoolean("subtitulada"));
                p.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
                p.setHoraFin(rs.getTime("horaFin").toLocalTime());
                p.setPrecio(rs.getDouble("precio"));
                p.setActiva(rs.getBoolean("activa"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al traer proyeccionesPorPeliculaYSala: " + e.getMessage());
        }

        return lista;
    }
}
