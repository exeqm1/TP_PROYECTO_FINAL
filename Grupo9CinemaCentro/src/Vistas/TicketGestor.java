/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Modelo.*;
import Persistencia.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import javax.swing.UIManager;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author PC1
 */
public class TicketGestor extends javax.swing.JInternalFrame {

  private SistemaCine sc ; 
    private Conexion con ; 
  private  ProyeccionData pd;
     private LugarData ld;
    public TicketGestor(SistemaCine sc) {
        
        initComponents();
        

        
        this.sc = sc;
    this.con = sc.conexionDb();
        this.pd=new ProyeccionData(con);
        this.ld=new  LugarData(con);
        llenarTableTicket();
        
        setSize(800, 600);
        setResizable(false);
        
        llenarTableCompradores();
        filtrarCompradores();
        
        filtrarTickets();
        llenarListPeliculas();
        llenarListProyeccion();
        llenarListButacas();
        buttonGroup1.add(radioButtonEfectivo);
        buttonGroup1.add(radioButtonTransfer);

    }
    DefaultTableModel modeloTableComprador; // (DTM Personalizado)
    TableRowSorter<DefaultTableModel> sortModelComprador; // (Filtrado)

    DefaultTableModel modeloTableTicket;
    TableRowSorter<DefaultTableModel> sortModelTicket;

    
    private void llenarTableTicket() {
        DocumentListener listenerFiltro = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarTickets();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrarTickets();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrarTickets();
            }
        };

        TicketData ticketDAO = new TicketData(con);
        List<Ticket> listaTickets = ticketDAO.listarTickets();

        tableTicket.setShowGrid(false);
        modeloTableTicket = (DefaultTableModel) tableTicket.getModel();
        modeloTableTicket.setRowCount(0);

       for (Ticket t : listaTickets) {

    Comprador nombreComprador = t.getComprador() ;
    Comprador idComprador = t.getComprador() ;

    Lugar asiento = t.getAsiento() ;

    String tituloPelicula = "—";
   

    modeloTableTicket.addRow(new Object[]{
        t.getIdTicket(),
        nombreComprador,
        idComprador,
        asiento,
        t.getFechaCompra(),
        t.getFechaFuncion(),
        tituloPelicula,
        t.getMonto(),
        t.isActivo()
    });
}


        sortModelTicket = new TableRowSorter<>(modeloTableTicket);
        tableTicket.setRowSorter(sortModelTicket);
        txtIDTicket.getDocument().addDocumentListener(listenerFiltro);
    }

    private void filtrarTickets() {
        String txtTicket = txtIDTicket.getText().trim();
        if (txtTicket.isEmpty()) {
            sortModelTicket.setRowFilter(null);
        } else {
            sortModelTicket.setRowFilter(RowFilter.regexFilter(txtTicket, 0));
        }
    }

    
    private void llenarTableCompradores() {

        DocumentListener listenerFiltro = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarCompradores();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrarCompradores();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrarCompradores();
            }
        };
        CompradorData compradorDAO = new CompradorData(con);
        List<Comprador> listaCompradores = compradorDAO.listarCompradores();

        tableCompradores.setShowGrid(false);
        modeloTableComprador = (DefaultTableModel) tableCompradores.getModel();
        modeloTableComprador.setRowCount(0);

        for (Comprador c : listaCompradores) {
            modeloTableComprador.addRow(new Object[]{
                c,
                c.getIdComprador()
            });
        }

        sortModelComprador = new TableRowSorter<>(modeloTableComprador);
        tableCompradores.setRowSorter(sortModelComprador);
        txtIDComprador.getDocument().addDocumentListener(listenerFiltro);
    }

    private void filtrarCompradores() {
        String txtComprador = txtIDComprador.getText().trim();
        if (txtComprador.isEmpty()) {
            sortModelComprador.setRowFilter(null);
        } else {
            sortModelComprador.setRowFilter(RowFilter.regexFilter(txtComprador, 1));
        }
    }

    private void llenarListPeliculas() {
        PeliculaData peliculaDAO = new PeliculaData(con);
        List<Pelicula> listaPeliculas = peliculaDAO.listarPeliculasEnCartelera();

        for (Pelicula p : listaPeliculas) {
            comboBoxPeliculas.addItem(p);
        }

    }

    private void llenarListProyeccion() {
        comboBoxProyeccion.removeAllItems();
    comboBoxButaca.removeAllItems();
        
        List<Proyeccion> listaProyecciones = pd.listarActivas();

        for (Proyeccion p : listaProyecciones) {
            comboBoxProyeccion.addItem(p);
        }
    }

    private void llenarListButacas() {
        Proyeccion itemSeleccionado = (Proyeccion) comboBoxProyeccion.getSelectedItem();
      ;

        if (itemSeleccionado != null) {
            List<Lugar> listaLugares = ld.lugaresDisponiblesPorProyeccion(itemSeleccionado.getIdProyeccion());
            comboBoxButaca.removeAllItems();
            for (Lugar butaca : listaLugares) {
                comboBoxButaca.addItem(butaca);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una proyeccion.");
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtIDTicket = new javax.swing.JTextField();
        botonNuevoTicket = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        radioButtonTransfer = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        radioButtonEfectivo = new javax.swing.JRadioButton();
        comboBoxPeliculas = new javax.swing.JComboBox<>();
        comboBoxProyeccion = new javax.swing.JComboBox<>();
        comboBoxButaca = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        dateChooserEmision = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        dateChooserFuncion = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtIDComprador = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCompradores = new javax.swing.JTable();
        botonGenerarTicket = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTicket = new javax.swing.JTable();
        botonAnularTicket = new javax.swing.JButton();
        botonBorrarTicket = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setMaximumSize(new java.awt.Dimension(2767, 2767));
        jPanel1.setMinimumSize(new java.awt.Dimension(300, 300));

        txtIDTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDTicketActionPerformed(evt);
            }
        });

        botonNuevoTicket.setText("Nuevo Ticket");
        botonNuevoTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoTicketActionPerformed(evt);
            }
        });

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Butaca");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Función");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Pelicula");

        radioButtonTransfer.setText("Transferencia");
        radioButtonTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonTransferActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Proyeccion:");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Medio de Pago:");

        radioButtonEfectivo.setText("Efectivo");
        radioButtonEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonEfectivoActionPerformed(evt);
            }
        });

        comboBoxPeliculas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxPeliculasActionPerformed(evt);
            }
        });

        comboBoxProyeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxProyeccionActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Total a Pagar:");

        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fecha de Emision:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Fecha de Funcion:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(radioButtonEfectivo)
                                .addGap(18, 18, 18)
                                .addComponent(radioButtonTransfer))
                            .addComponent(comboBoxButaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(comboBoxProyeccion, javax.swing.GroupLayout.Alignment.LEADING, 0, 158, Short.MAX_VALUE)
                                .addComponent(comboBoxPeliculas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(dateChooserEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboBoxPeliculas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(comboBoxProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboBoxButaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserEmision, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioButtonEfectivo)
                        .addComponent(radioButtonTransfer)))
                .addContainerGap())
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Datos del Cliente: ");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscar Por ID:");

        txtIDComprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDCompradorActionPerformed(evt);
            }
        });

        tableCompradores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre", "ID"
            }
        ));
        jScrollPane3.setViewportView(tableCompradores);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtIDComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIDComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );

        botonGenerarTicket.setText("Generar Ticket");
        botonGenerarTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarTicketActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("GESTION DE TICKETS");

        tableTicket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Cliente", "Butaca", "Emisión", "Fecha", "Horario", "Pelicula", "Monto", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tableTicket);

        botonAnularTicket.setText("Anular Ticket");
        botonAnularTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnularTicketActionPerformed(evt);
            }
        });

        botonBorrarTicket.setText("Borrar Ticket");
        botonBorrarTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarTicketActionPerformed(evt);
            }
        });

        jLabel3.setText("Buscar Ticket (ID)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(txtIDTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonAnularTicket)
                            .addGap(18, 18, 18)
                            .addComponent(botonBorrarTicket))
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(botonGenerarTicket)
                                    .addGap(134, 134, 134)
                                    .addComponent(botonNuevoTicket))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46)))
                            .addGap(28, 28, 28)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonNuevoTicket)
                            .addComponent(botonGenerarTicket)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnularTicket)
                    .addComponent(txtIDTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBorrarTicket)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioButtonTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioButtonTransferActionPerformed

    private void radioButtonEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioButtonEfectivoActionPerformed

    private void botonAnularTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnularTicketActionPerformed
        if (tableTicket.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un ticket de la lista.");
            return;
        }

        int id = (int) tableTicket.getValueAt(tableTicket.getSelectedRow(), 0);

        TicketData ticketDAO = new TicketData(con);

        Object[] opciones = {"Si", "No"};

        int resultado = JOptionPane.showOptionDialog(rootPane, "¿Seguro que desea anular el ticket?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (resultado == 0) {
            ticketDAO.anularTicket(id);
        }
    }//GEN-LAST:event_botonAnularTicketActionPerformed

    private void botonNuevoTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoTicketActionPerformed
        tableCompradores.clearSelection();
        txtIDComprador.setText("");
        comboBoxPeliculas.setSelectedIndex(-1);
        comboBoxProyeccion.setSelectedIndex(-1);
        comboBoxButaca.setSelectedIndex(-1);
        txtMonto.setText("");
        buttonGroup1.clearSelection();
        dateChooserEmision.setDate(null);
        dateChooserFuncion.setDate(null);


    }//GEN-LAST:event_botonNuevoTicketActionPerformed

    private void botonGenerarTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarTicketActionPerformed
        if (tableCompradores.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un cliente de la lista.");
            return;
        }

        if (comboBoxPeliculas.getSelectedItem() == null || comboBoxProyeccion.getSelectedItem() == null || comboBoxButaca.getSelectedItem() == null || txtMonto.getText().isEmpty() || buttonGroup1.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos para continuar.");
            return;
        }

        if (dateChooserEmision.getDate() == null || dateChooserFuncion.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione las fechas correspondientes");
            return;
        }

        int id = 0;
        Lugar asiento = (Lugar) comboBoxButaca.getSelectedItem();
        Comprador nombreComprador = (Comprador) tableCompradores.getValueAt(tableCompradores.getSelectedRow(), 0);
        LocalDate fechaEmision = dateChooserEmision.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFuncion = dateChooserFuncion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        double monto = 0;
        boolean estado = true;
        Proyeccion funcion = (Proyeccion) comboBoxProyeccion.getSelectedItem();

        try {
            monto = Double.valueOf(txtMonto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Formato invalido para campo numerico. ");
            return;
        }

        TicketData ticketDAO = new TicketData(con);
        Ticket ticket = new Ticket( asiento, nombreComprador, fechaEmision, fechaFuncion, monto, estado, funcion);
        ticketDAO.guardarTicket(ticket);
        llenarTableTicket(); 

    }//GEN-LAST:event_botonGenerarTicketActionPerformed

    private void txtIDTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDTicketActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDTicketActionPerformed

    private void botonBorrarTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarTicketActionPerformed
        if (tableTicket.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un ticket de la lista.");
            return;
        }

        int id = (int) tableTicket.getValueAt(tableTicket.getSelectedRow(), 0);

        TicketData ticketDAO = new TicketData(con);

        Object[] opciones = {"Si", "No"};

        int resultado = JOptionPane.showOptionDialog(rootPane, "¿Seguro que desea anular el ticket?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (resultado == 0) {
            ticketDAO.anularTicket(id);
        }

    }//GEN-LAST:event_botonBorrarTicketActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtIDCompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDCompradorActionPerformed

    }//GEN-LAST:event_txtIDCompradorActionPerformed

    private void comboBoxPeliculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPeliculasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxPeliculasActionPerformed

    private void comboBoxProyeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxProyeccionActionPerformed
     comboBoxButaca.removeAllItems();
     llenarListButacas();
    }//GEN-LAST:event_comboBoxProyeccionActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnularTicket;
    private javax.swing.JButton botonBorrarTicket;
    private javax.swing.JButton botonGenerarTicket;
    private javax.swing.JButton botonNuevoTicket;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<Lugar> comboBoxButaca;
    private javax.swing.JComboBox<Pelicula> comboBoxPeliculas;
    private javax.swing.JComboBox<Proyeccion> comboBoxProyeccion;
    private com.toedter.calendar.JDateChooser dateChooserEmision;
    private com.toedter.calendar.JDateChooser dateChooserFuncion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton radioButtonEfectivo;
    private javax.swing.JRadioButton radioButtonTransfer;
    private javax.swing.JTable tableCompradores;
    private javax.swing.JTable tableTicket;
    private javax.swing.JTextField txtIDComprador;
    private javax.swing.JTextField txtIDTicket;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables

   
}
