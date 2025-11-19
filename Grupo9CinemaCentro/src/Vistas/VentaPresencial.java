/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Modelo.*;
import Persistencia.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.ZoneId;
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
public class VentaPresencial extends javax.swing.JInternalFrame {

    private SistemaCine sistemaCine = new SistemaCine();
    private Conexion conex = sistemaCine.conexionDb();

    DefaultTableModel modeloTableComprador; // (DTM Personalizado)
    TableRowSorter<DefaultTableModel> sortModelComprador; // (Filtrado)

    DefaultTableModel modeloTableTicket;
    TableRowSorter<DefaultTableModel> sortModelTicket;

    //======== Metodos Table Ticket ========
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

        TicketData ticketDAO = new TicketData(conex);
        List<Ticket> listaTickets = ticketDAO.listarTickets();

        tableTicket.setShowGrid(false);
        modeloTableTicket = (DefaultTableModel) tableTicket.getModel();
        modeloTableTicket.setRowCount(0);

        for (Ticket t : listaTickets) {
            modeloTableTicket.addRow(new Object[]{
                t.getIdTicket(),
                t.getComprador(),
                t.getAsiento(),
                t.getFechaCompra(),
                t.getFechaFuncion(),
                t.getFuncion().getHoraInicio(),
                t.getFuncion().getPelicula(),
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

    //========== Metodos Table Comprador ===========
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
        CompradorData compradorDAO = new CompradorData(conex);
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

    //========== Metodos ComboBox Pelicula ===========
    private void llenarListPeliculas() {
        PeliculaData peliculaDAO = new PeliculaData(conex);
        List<Pelicula> listaPeliculas = peliculaDAO.listarPeliculasEnCartelera();

        for (Pelicula p : listaPeliculas) {
            comboBoxPeliculas.addItem(p);
        }

//        if (comboBoxPeliculas.getItemCount() > 0) {
//            Pelicula preseleccionada = (Pelicula) comboBoxPeliculas.getSelectedItem();
//            comboBoxProyeccion.removeAllItems();
//            llenarListProyeccion(preseleccionada);    
//            }   
    }

    private void listenerPeliculas() {

        comboBoxPeliculas.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {

                Pelicula peliculaSeleccionada = (Pelicula) e.getItem();
                comboBoxSala.removeAllItems();
                comboBoxProyeccion.removeAllItems();
                comboBoxButaca.removeAllItems();
                llenarListSalas(peliculaSeleccionada);

            }
        });
    }

    //========== Metodos ComboBox Sala ============
    private void llenarListSalas(Pelicula peli) {
        ProyeccionData proyeccionDAO = new ProyeccionData(conex);
        List<Sala> salas = proyeccionDAO.salasPorPelicula(peli.getIdPelicula());

        for (Sala s : salas) {
            comboBoxSala.addItem(s);
        }
        listenerSala();

        if (comboBoxSala.getItemCount() > 0) {
            Sala s = (Sala) comboBoxSala.getSelectedItem();
            proyeccionDAO = new ProyeccionData(conex);

            List<Proyeccion> lista = proyeccionDAO.proyeccionesPorPeliculaYSala(
                    peli.getIdPelicula(), s.getIdSala());

            comboBoxProyeccion.removeAllItems();
            for (Proyeccion p : lista) {
                comboBoxProyeccion.addItem(p);
            }
        }
        }

    

    private void listenerSala() {

        comboBoxSala.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {

                Sala sala = (Sala) e.getItem();
                Pelicula peli = (Pelicula) comboBoxPeliculas.getSelectedItem();
                ProyeccionData proyeccionDAO = new ProyeccionData(conex);

                comboBoxProyeccion.removeAllItems();
                comboBoxButaca.removeAllItems();

                List<Proyeccion> listaProyecciones = proyeccionDAO.proyeccionesPorPeliculaYSala(peli.getIdPelicula(), sala.getIdSala());

                for (Proyeccion p : listaProyecciones) {
                    comboBoxProyeccion.addItem(p);
                }
            }
        });
    }

    //========== Metodos ComboBox Proyeccion ===========
    private void listenerProyeccion() {
        comboBoxProyeccion.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {

                Proyeccion proyeccionSeleccionada = (Proyeccion) e.getItem();
                comboBoxButaca.removeAllItems();
                llenarListButacas(proyeccionSeleccionada);
            }
        });
    }

    //========== Metodos ComboBox Butacas ===========
    private void llenarListButacas(Proyeccion pro) {
        //Proyeccion itemSeleccionado = (Proyeccion) comboBoxProyeccion.getSelectedItem();
        LugarData lugarDAO = new LugarData(conex);

        if (pro != null) {
            List<Lugar> listaLugares = lugarDAO.lugaresDisponiblesPorProyeccion(pro.getIdProyeccion());
            System.out.println(pro.getIdProyeccion());
            for (Lugar butaca : listaLugares) {
                comboBoxButaca.addItem(butaca);
            }
        }
    }

    public VentaPresencial(SistemaCine sc) {
        setSize(800, 600);
        setResizable(false);
        initComponents();
        tableCompradores.setDefaultEditor(Object.class, null);
        tableTicket.setDefaultEditor(Object.class, null);
        llenarTableCompradores();
        filtrarCompradores();
        llenarTableTicket();
        filtrarTickets();
        llenarListPeliculas();
        listenerPeliculas();
        listenerProyeccion();

        if (comboBoxPeliculas.getItemCount() > 0) {
            Pelicula seleccionada = (Pelicula) comboBoxPeliculas.getSelectedItem();
            llenarListSalas(seleccionada); // ← carga las salas
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
        jLabel12 = new javax.swing.JLabel();
        comboBoxPeliculas = new javax.swing.JComboBox<>();
        comboBoxProyeccion = new javax.swing.JComboBox<>();
        comboBoxButaca = new javax.swing.JComboBox<>();
        dateChooserEmision = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        dateChooserFuncion = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        comboBoxSala = new javax.swing.JComboBox<>();
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
        jLabel11.setText("Pelicula:");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Proyeccion:");

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

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fecha de Emision:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Fecha de Funcion:");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Total:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Sala:");

        comboBoxSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSalaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboBoxButaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBoxProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBoxSala, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jLabel16)
                                .addGap(24, 24, 24)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGap(93, 93, 93)
                            .addComponent(jLabel11)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(comboBoxPeliculas, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 21, Short.MAX_VALUE)
                                .addComponent(dateChooserFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(dateChooserEmision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(53, Short.MAX_VALUE))
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
                    .addComponent(jLabel13)
                    .addComponent(comboBoxSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(comboBoxProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboBoxButaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtIDComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
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
        jLabel7.setText("VENTA PRESENCIAL");

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
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addGap(1, 1, 1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(botonGenerarTicket)
                                    .addGap(134, 134, 134)
                                    .addComponent(botonNuevoTicket))
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(28, 28, 28)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel7)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonNuevoTicket)
                            .addComponent(botonGenerarTicket))
                        .addGap(30, 30, 30))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnularTicket)
                    .addComponent(txtIDTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBorrarTicket)
                    .addComponent(jLabel3))
                .addGap(69, 69, 69))
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

    private void botonAnularTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnularTicketActionPerformed
        if (tableTicket.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un ticket de la lista.");
            return;
        }

        int id = (int) tableTicket.getValueAt(tableTicket.getSelectedRow(), 0);

        TicketData ticketDAO = new TicketData(conex);

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
        buttonGroup1.clearSelection();
        dateChooserEmision.setDate(null);
        dateChooserFuncion.setDate(null);


    }//GEN-LAST:event_botonNuevoTicketActionPerformed

    private void botonGenerarTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarTicketActionPerformed
        if (tableCompradores.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un cliente de la lista.");
            return;
        }

        if (comboBoxPeliculas.getSelectedItem() == null || comboBoxProyeccion.getSelectedItem() == null || comboBoxButaca.getSelectedItem() == null) {
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


        TicketData ticketDAO = new TicketData(conex);
        Ticket ticket = new Ticket(asiento, nombreComprador, fechaEmision, fechaFuncion, monto, estado, funcion);
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

        TicketData ticketDAO = new TicketData(conex);

        Object[] opciones = {"Si", "No"};

        int resultado = JOptionPane.showOptionDialog(rootPane, "¿Seguro que desea anular el ticket?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (resultado == 0) {
            ticketDAO.anularTicket(id);
        }

    }//GEN-LAST:event_botonBorrarTicketActionPerformed

    private void txtIDCompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDCompradorActionPerformed

    }//GEN-LAST:event_txtIDCompradorActionPerformed

    private void comboBoxPeliculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPeliculasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxPeliculasActionPerformed

    private void comboBoxProyeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxProyeccionActionPerformed

    }//GEN-LAST:event_comboBoxProyeccionActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void comboBoxSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSalaActionPerformed

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
    private javax.swing.JComboBox<Sala> comboBoxSala;
    private com.toedter.calendar.JDateChooser dateChooserEmision;
    private com.toedter.calendar.JDateChooser dateChooserFuncion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tableCompradores;
    private javax.swing.JTable tableTicket;
    private javax.swing.JTextField txtIDComprador;
    private javax.swing.JTextField txtIDTicket;
    // End of variables declaration//GEN-END:variables

}
