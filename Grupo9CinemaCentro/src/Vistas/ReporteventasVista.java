/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vistas;

import Modelo.Conexion;
import Modelo.Pelicula;
import Persistencia.PeliculaData;
import Persistencia.TicketData;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Estudiante
 */
public class ReporteVentasVista extends javax.swing.JInternalFrame {

    private SistemaCine sistemaCine = new SistemaCine();
    private Conexion con = sistemaCine.conexionDb();
    private DefaultTableModel modelo;
    private PeliculaData peliculaData;
    private TicketData ticketData;
    
    public ReporteVentasVista() {
        initComponents();
        
        peliculaData = new PeliculaData(con);
        ticketData = new TicketData(con);

        
        modelo = new DefaultTableModel(
                new Object[]{"ID", "Inicio", "Fin", "Tipo", "Entradas", "Precio", "Subtotal"}, 
                0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1.setModel(modelo);

        cargarPeliculasEnCombo();
    }

    private void cargarPeliculasEnCombo() {
        cbPeliculas.removeAllItems();
        for (Pelicula p : peliculaData.listarPeliculasEnCartelera()) {
            cbPeliculas.addItem(p);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbPeliculas = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jdcDesde = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jdcHasta = new com.toedter.calendar.JDateChooser();
        btnConsultar = new javax.swing.JButton();
        tabladeventas = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setResizable(true);
        setTitle("Informe de ventas por película\n");

        jLabel1.setText("Pelicula:");

        jLabel2.setText("Desde:");

        jLabel3.setText("Hasta:");

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabladeventas.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbPeliculas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnConsultar)))
                .addGap(133, 133, 133)
                .addComponent(tabladeventas, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbPeliculas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jdcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jdcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnConsultar))
                    .addComponent(tabladeventas, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
         if (jdcDesde.getDate() == null || jdcHasta.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar fechas.");
            return;
        }

        Pelicula seleccionada = (Pelicula) cbPeliculas.getSelectedItem();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una película.");
            return;
        }

        LocalDate desde = jdcDesde.getDate().toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate hasta = jdcHasta.getDate().toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        cargarReporte(seleccionada.getIdPelicula(), desde, hasta);
    }//GEN-LAST:event_btnConsultarActionPerformed
    
    private void cargarReporte(int idPelicula, LocalDate desde, LocalDate hasta) {
        modelo.setRowCount(0);

        List<Object[]> lista = ticketData.listarVentas(idPelicula, desde, hasta);

        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JComboBox<Pelicula> cbPeliculas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser jdcDesde;
    private com.toedter.calendar.JDateChooser jdcHasta;
    private javax.swing.JScrollPane tabladeventas;
    // End of variables declaration//GEN-END:variables
}
