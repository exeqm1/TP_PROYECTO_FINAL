package Persistencia;

import Modelo.Ticket;
import Modelo.Lugar;
import Modelo.Comprador;
import Modelo.Conexion;
import Modelo.Proyeccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TicketData {

    private Connection conec = null;
    private CompradorData compradorData;
    private LugarData lugarData;
    private ProyeccionData proyecciondata;

    public TicketData(Conexion conex) {
        this.conec = conex.conectar();
        this.compradorData = new CompradorData(conex);
        this.lugarData = new LugarData(conex);
        this.proyecciondata = new ProyeccionData(conex);
    }

    // Metodos CRUD
    public void guardarTicket(Ticket ticket) {
        String sql = "INSERT INTO ticket (Id_comprador, Id_lugar, fechaCompra, fechaFuncion, monto, activo) VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = conec.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ticket.getComprador().getIdComprador());
            ps.setInt(2, ticket.getAsiento().getIdLugar());

            ps.setDate(3, Date.valueOf(ticket.getFechaCompra()));
            ps.setDate(4, Date.valueOf(ticket.getFechaFuncion()));

            ps.setDouble(5, ticket.getMonto());
            ps.setBoolean(6, ticket.isActivo());

            int filasAfectadas = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setIdTicket(rs.getInt(1));
                }
            }

            if (filasAfectadas > 0) {
                
                JOptionPane.showMessageDialog(null, "Ticket Nº " + ticket.getIdTicket() + " generado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo generar el ticket.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket: " + ex.getMessage());
        }
    }

    public Ticket buscarTicket(int Id_ticket) {
        Ticket ticket = null;
        String sql = "SELECT * FROM ticket WHERE Id_ticket = ?";

        try (PreparedStatement ps = conec.prepareStatement(sql)) {
            ps.setInt(1, Id_ticket);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();

                    ticket.setIdTicket(Id_ticket);
                    ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
                    ticket.setFechaFuncion(rs.getDate("fechaFuncion").toLocalDate());
                    ticket.setMonto(rs.getDouble("monto"));

                    int Id_comprador = rs.getInt("Id_comprador");
                    Comprador comprador = compradorData.buscarComprador(Id_comprador);
                    ticket.setComprador(comprador);

                    int Id_lugar = rs.getInt("Id_lugar");
                    Lugar asiento = lugarData.buscarButaca(Id_lugar);
                    ticket.setAsiento(asiento);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el ticket con ID: " + Id_ticket);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar ticket: " + ex.getMessage());
        }
        return ticket;
    }

    // Baja Logica
    public void anularTicket(int idTicket) {
        String sql = "UPDATE ticket SET activo = ? WHERE Id_ticket = ?";

        try (PreparedStatement ps = conec.prepareStatement(sql)) {

            ps.setBoolean(1, false);
            ps.setInt(2, idTicket);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                Ticket t = buscarTicket(idTicket);
                if (t != null) {
                    lugarData.liberarLugar(t.getAsiento().getIdLugar());
                }

                JOptionPane.showMessageDialog(null, "Ticket Nº " + idTicket + " anulado y asiento liberado.");
            } else {
                JOptionPane.showMessageDialog(null, "Advertencia: No se encontró el ticket para anular.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al anular ticket: " + ex.getMessage());
        }
    }

    // Baja Fisica
    public void borrarTicket(int idTicket) {
        Ticket t = buscarTicket(idTicket);
        if (t != null) {
            lugarData.liberarLugar(t.getAsiento().getIdLugar());

            String sql = "DELETE FROM ticket WHERE Id_ticket = ?";

            try (PreparedStatement ps = conec.prepareStatement(sql)) {

                ps.setInt(1, idTicket);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {

                    JOptionPane.showMessageDialog(null, "Ticket Nº " + idTicket + " eliminado de la base de datos y asiento liberado.");
                } else {
                    JOptionPane.showMessageDialog(null, "Advertencia: No se encontró el ticket para eliminar.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar ticket: " + ex.getMessage());
            }
        }
    }
    // Metodos Adicionales

    public List<Ticket> listarTicketsPorComprador(int Id_comprador) {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE Id_comprador = ?";

        try (PreparedStatement ps = conec.prepareStatement(sql)) {
            ps.setInt(1, Id_comprador);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();

                    ticket.setIdTicket(rs.getInt("Id_ticket"));
                    ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
                    ticket.setMonto(rs.getDouble("monto"));
                    int Id_lugar = rs.getInt("Id_lugar");
                    
                    Lugar asiento = lugarData.buscarButaca(Id_lugar);
                    ticket.setAsiento(asiento);
                    
                    Comprador comprador = compradorData.buscarComprador(Id_comprador);
                    ticket.setComprador(comprador);

                    lista.add(ticket);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar tickets: " + ex.getMessage());
        }
        return lista;
    }

    public List<Ticket> listarTickets() {
    List<Ticket> lista = new ArrayList<>();
    String sql = "SELECT * FROM ticket";

    try (PreparedStatement ps = conec.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Ticket ticket = new Ticket();

            ticket.setIdTicket(rs.getInt("Id_ticket"));

            int Id_comprador = rs.getInt("Id_comprador");
            int Id_lugar = rs.getInt("Id_lugar");

            // --- Manejo seguro de fechas ---
            Date fCompra = rs.getDate("fechaCompra");
            if (fCompra != null) {
                ticket.setFechaCompra(fCompra.toLocalDate());
            }

            Date fFuncion = rs.getDate("fechaFuncion");
            if (fFuncion != null) {
                ticket.setFechaFuncion(fFuncion.toLocalDate());
            }

            ticket.setMonto(rs.getDouble("monto"));
            ticket.setActivo(rs.getBoolean("activo"));

            // --- Buscar comprador ---
            Comprador comprador = compradorData.buscarComprador(Id_comprador);
            if (comprador == null) {
                System.out.println("⚠ Comprador no encontrado: " + Id_comprador);
            }
            ticket.setComprador(comprador);

            // --- Buscar asiento ---
            Lugar asiento = lugarData.buscarButaca(Id_lugar);
            if (asiento == null) {
                System.out.println("⚠ Asiento no encontrado: " + Id_lugar);
            }
            ticket.setAsiento(asiento);

            lista.add(ticket);
        }

    } catch (SQLException ex) {
        Logger.getLogger(TicketData.class.getName()).log(Level.SEVERE, null, ex);
    }

    return lista;
}

               
            

}
