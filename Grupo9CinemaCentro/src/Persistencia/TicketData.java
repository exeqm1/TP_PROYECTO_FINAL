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
   
    

    public TicketData(Conexion conex) {
        this.conec = conex.conectar();
        this.compradorData = new CompradorData(conex);
        this.lugarData = new LugarData(conex);
       
     
    }

    // Metodos CRUD
   public void guardarTicket(Ticket ticket) {
    String sql = "INSERT INTO ticket (Id_comprador, Id_lugar, fechaCompra,  monto, activo) "
               + "VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conec.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, ticket.getComprador().getIdComprador());
        ps.setInt(2, ticket.getAsiento().getIdLugar());
        ps.setDate(3, Date.valueOf(ticket.getFechaCompra()));
      
        ps.setDouble(4, ticket.getMonto());
        ps.setBoolean(5, ticket.isActivo());

        int filasAfectadas = ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                ticket.setIdTicket(rs.getInt(1));
            }
        }

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null,
                    "Ticket Nº " + ticket.getIdTicket() + " generado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "No se pudo generar el ticket.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,
                "Error al generar el ticket: " + ex.getMessage());
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
                
                ticket.setMonto(rs.getDouble("monto"));

                int Id_comprador = rs.getInt("Id_comprador");
                Comprador comprador = compradorData.buscarComprador(Id_comprador);
                ticket.setComprador(comprador);

                int Id_lugar = rs.getInt("Id_lugar");
                Lugar asiento = lugarData.buscarButaca(Id_lugar);
                ticket.setAsiento(asiento);

            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró el ticket con ID: " + Id_ticket);
            }
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,
                "Error al buscar ticket: " + ex.getMessage());
    }

    return ticket;
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

        try (PreparedStatement ps = conec.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setIdTicket(rs.getInt("Id_ticket"));
                ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
                
                ticket.setMonto(rs.getDouble("monto"));
                ticket.setActivo(rs.getBoolean("activo"));

                int Id_comprador = rs.getInt("Id_comprador");
                Comprador comprador = compradorData.buscarComprador(Id_comprador);
                ticket.setComprador(comprador);

                int Id_lugar = rs.getInt("Id_lugar");
                Lugar asiento = lugarData.buscarButaca(Id_lugar);
                ticket.setAsiento(asiento);
                
   
                

                lista.add(ticket);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TicketData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<Ticket> listarTickets2() {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM ticket";

        try (PreparedStatement ps = conec.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setIdTicket(rs.getInt("Id_ticket"));

                int Id_comprador = rs.getInt("Id_comprador");
                int Id_lugar = rs.getInt("Id_lugar");

                
                Date fCompra = rs.getDate("fechaCompra");
                if (fCompra != null) {
                    ticket.setFechaCompra(fCompra.toLocalDate());
                }

              
               

                ticket.setMonto(rs.getDouble("monto"));
                ticket.setActivo(rs.getBoolean("activo"));

                
                Comprador comprador = compradorData.buscarComprador(Id_comprador);
                if (comprador == null) {
                    System.out.println(" Comprador no encontrado: " + Id_comprador);
                }
                ticket.setComprador(comprador);

               
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
    public void anularTicket(int idTicket) {
    String sql = "UPDATE ticket SET activo = 0 WHERE Id_ticket = ?";

    try (PreparedStatement ps = conec.prepareStatement(sql)) {

        ps.setInt(1, idTicket);

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
    

    public void borrarTicket(int idTicket) {
    String sql = "DELETE FROM ticket WHERE Id_ticket = ?";

    try (PreparedStatement ps = conec.prepareStatement(sql)) {

        ps.setInt(1, idTicket);

        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Ticket Nº " + idTicket + " eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "No existe un ticket con ese ID.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al borrar ticket: " + ex.getMessage());
    }
}
    public List<Object[]> listarVentas() {
    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT * FROM ticket WHERE activo = 1";

    try (PreparedStatement ps = conec.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            int id = rs.getInt("Id_ticket");
           
            double monto = rs.getDouble("monto");

            int idComprador = rs.getInt("Id_comprador");
            Comprador comp = compradorData.buscarComprador(idComprador);

            int idLugar = rs.getInt("Id_lugar");
            Lugar asiento = lugarData.buscarButaca(idLugar);

            Object[] fila = {
                id,
                comp.getNombre(),
                asiento.getFila(),
                asiento.getNumero(),
               
                monto
            };

            lista.add(fila);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, 
            "Error al listar ventas: " + ex.getMessage());
    }

    return lista;
}



    
}
