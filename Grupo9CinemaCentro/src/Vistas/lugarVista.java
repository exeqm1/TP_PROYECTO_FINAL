/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelo.Comprador;
import Modelo.Conexion;
import Modelo.Lugar;
import Modelo.Pelicula;
import Modelo.Proyeccion;
import Modelo.Sala;
import Persistencia.LugarData;
import Persistencia.PeliculaData;
import Persistencia.ProyeccionData;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Usuario
 */
public class lugarVista extends javax.swing.JInternalFrame {

    private SistemaCine sistemaCine = new SistemaCine();

    private Conexion conex = sistemaCine.conexionDb();
    private LugarData lugarDAO = new LugarData(conex);
    private ProyeccionData proyeccionDAO = new ProyeccionData(conex);
    private PeliculaData peliculaDAO = new PeliculaData(conex);

    DefaultTableModel modeloTableButacas;
    TableRowSorter<DefaultTableModel> sortModelButacas;
    ListSelectionListener selectorLista;

    private void llenarTableButacas() {

        DocumentListener listenerFiltro = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarButacas();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrarButacas();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrarButacas();
            }
        };
        LugarData lugarDAO = new LugarData(conex);
        List<Lugar> listaLugares = lugarDAO.listarButacas();

        tableButacas.setShowGrid(false);
        modeloTableButacas = (DefaultTableModel) tableButacas.getModel();
        modeloTableButacas.setRowCount(0);

        for (Lugar l : listaLugares) {
            modeloTableButacas.addRow(new Object[]{
                l.getIdLugar(),
             
                l.getFila(),
                l.getNumero(),
                l.getDisponible()
            });
            System.out.println(l.getProyeccion());
        }

        sortModelButacas = new TableRowSorter<>(modeloTableButacas);
        tableButacas.setRowSorter(sortModelButacas);
        txtIDButaca.getDocument().addDocumentListener(listenerFiltro);
    }

    private void filtrarButacas() {
        String txtButaca = txtIDButaca.getText().trim();
        if (txtButaca.isEmpty()) {
            sortModelButacas.setRowFilter(null);
        } else {
            sortModelButacas.setRowFilter(RowFilter.regexFilter(txtButaca, 0));
        }
    }

    public void llenarComboPeliculas() {
        comboBoxPelicula.removeAllItems();
        for (Modelo.Pelicula p : new Persistencia.PeliculaData(conex).listarPeliculas()) {
            comboBoxPelicula.addItem(p);
        }

        comboBoxPelicula.setSelectedIndex(-1);
    }

    public void llenarComboSalas(Pelicula peli) {
        comboBoxSala.removeAllItems();
        if (peli != null) {
            List<Sala> salas = proyeccionDAO.salasPorPelicula(peli.getIdPelicula());
            for (Sala s : salas) {
                comboBoxSala.addItem(s);
            }
        }
        comboBoxSala.setSelectedIndex(-1);
    }

    public void llenarComboProyeccion(Pelicula peli, Sala sala) {
        comboBoxProyeccion.removeAllItems();
        if (peli != null && sala != null) {
            List<Modelo.Proyeccion> proyecciones = proyeccionDAO.proyeccionesPorPeliculaYSala(peli.getIdPelicula(), sala.getIdSala());
            for (Modelo.Proyeccion p : proyecciones) {
                comboBoxProyeccion.addItem(p);
            }
        }
    }

    public void limpiarCampos() {

        txtFila.setText("");
        txtNumero.setText("");
        radioButtonDisponible.setSelected(false);
        comboBoxProyeccion.setSelectedIndex(-1);
    }

    public lugarVista(SistemaCine sc) {

        initComponents();
        tableButacas.setDefaultEditor(Object.class, null);
        llenarComboPeliculas();
        llenarTableButacas();
        filtrarButacas();
        buttonGuardarCambios.setEnabled(false);

        comboBoxPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Modelo.Pelicula peliSeleccionada = (Modelo.Pelicula) comboBoxPelicula.getSelectedItem();

                comboBoxSala.removeAllItems();
                comboBoxProyeccion.removeAllItems();

                if (peliSeleccionada != null) {
                    llenarComboSalas(peliSeleccionada);
                }
            }
        });


        comboBoxSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Modelo.Pelicula peliSeleccionada = (Modelo.Pelicula) comboBoxPelicula.getSelectedItem();
                Modelo.Sala salaSeleccionada = (Modelo.Sala) comboBoxSala.getSelectedItem();

                comboBoxProyeccion.removeAllItems();

                if (peliSeleccionada != null && salaSeleccionada != null) {
                    llenarComboProyeccion(peliSeleccionada, salaSeleccionada);
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        comboBoxProyeccion = new javax.swing.JComboBox<>();
        txtFila = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        radioButtonDisponible = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboBoxPelicula = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        comboBoxSala = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableButacas = new javax.swing.JTable();
        buttonGuardar = new javax.swing.JButton();
        buttonModificar = new javax.swing.JButton();
        txtIDButaca = new javax.swing.JTextField();
        buttonGuardarCambios = new javax.swing.JButton();
        buttonEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jLabel6.setText("jLabel6");

        jLabel1.setText("GESTION DE ASIENTOS");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setText("Numero:");

        radioButtonDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonDisponibleActionPerformed(evt);
            }
        });

        jLabel3.setText("Proyeccion:");

        jLabel4.setText("Fila:");

        jLabel7.setText("Crear Butaca");

        jLabel8.setText("Disponible:");

        jLabel9.setText("Pelicula:");

        jLabel10.setText("Sala:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumero)
                            .addComponent(comboBoxProyeccion, 0, 126, Short.MAX_VALUE)
                            .addComponent(radioButtonDisponible)
                            .addComponent(txtFila, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(comboBoxPelicula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboBoxSala, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboBoxPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboBoxSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboBoxProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioButtonDisponible)
                    .addComponent(jLabel8))
                .addGap(28, 28, 28))
        );

        tableButacas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Proyeccion", "Fila", "Numero", "Disponible"
            }
        ));
        jScrollPane1.setViewportView(tableButacas);

        buttonGuardar.setText("Guardar");
        buttonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarActionPerformed(evt);
            }
        });

        buttonModificar.setText("Modificar");
        buttonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModificarActionPerformed(evt);
            }
        });

        buttonGuardarCambios.setText("Guardar Cambios");
        buttonGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarCambiosActionPerformed(evt);
            }
        });

        buttonEliminar.setText("Eliminar");
        buttonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEliminarActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar Butaca por ID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(326, 326, 326)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(buttonEliminar)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtIDButaca, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(buttonModificar)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(buttonGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonGuardarCambios)))
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 416, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtIDButaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonModificar)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(buttonGuardarCambios))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonGuardar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonEliminar)
                .addGap(272, 272, 272))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioButtonDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioButtonDisponibleActionPerformed

    private void buttonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuardarActionPerformed
        if (txtFila.getText().trim().isEmpty() || txtNumero.getText().trim().isEmpty() || comboBoxProyeccion.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
            return;
        }

        Proyeccion pro = (Proyeccion) comboBoxProyeccion.getSelectedItem();

        try {
            int fila = Integer.parseInt(txtFila.getText());
            int nro = Integer.parseInt(txtNumero.getText());
            boolean estado = radioButtonDisponible.isSelected();
            Lugar lugar = new Lugar(pro, fila, nro, estado);
            lugarDAO.insertButaca(lugar);
            limpiarCampos();
            // txtID.setEditable(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fila y número deben ser valores numéricos");
            return;
        }
        
        llenarTableButacas();
    }//GEN-LAST:event_buttonGuardarActionPerformed

    private void buttonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModificarActionPerformed
        if (tableButacas.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una butaca de la lista.");
        }
        tableButacas.setRowSelectionAllowed(true);

        selectorLista = (e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tableButacas.getSelectedRow();

                if (fila >= 0) {
                    int id = (int) tableButacas.getValueAt(fila, 0);
                    System.out.println("Fila seleccionada. ID: " + id);

                    Lugar lugar = lugarDAO.buscarButaca(id);
                    if (lugar != null) {
                        Proyeccion pro = lugar.getProyeccion();

                        for (int i = 0; i < comboBoxProyeccion.getItemCount(); i++) {
                            Proyeccion item = comboBoxProyeccion.getItemAt(i);
                            if (item.getIdProyeccion() == pro.getIdProyeccion()) {
                                comboBoxProyeccion.setSelectedIndex(i);
                                break;
                            }
                        }

                        txtFila.setText(String.valueOf(lugar.getFila()));
                        txtNumero.setText(String.valueOf(lugar.getNumero()));
                        radioButtonDisponible.setSelected(lugar.getDisponible());
                    }
                }
            }
        });

        tableButacas.getSelectionModel().addListSelectionListener(selectorLista);

        buttonGuardar.setEnabled(false);
        buttonGuardarCambios.setEnabled(true);
    }//GEN-LAST:event_buttonModificarActionPerformed

    private void buttonGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuardarCambiosActionPerformed
        if (tableButacas.getSelectedRow() >= 0) {

            if (comboBoxProyeccion.getSelectedItem() == null || txtFila.getText().isEmpty() || txtNumero.getText().isEmpty() || radioButtonDisponible == null) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                return;
            }

            int id = (int) tableButacas.getValueAt(tableButacas.getSelectedRow(), 0);
            Proyeccion pro = (Proyeccion) comboBoxProyeccion.getSelectedItem();
            boolean disponible = radioButtonDisponible.isSelected();

            int fila;
            int numero;

            try {
                fila = Integer.valueOf(txtFila.getText());
                numero = Integer.valueOf(txtNumero.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese formato valido para campo numerico");
                return;
            }

            Lugar lugar = new Lugar(id, pro, fila, numero, disponible);

            lugarDAO.actualizarButaca(lugar);
            llenarTableButacas();
            limpiarCampos();

            if (selectorLista != null) {
                tableButacas.getSelectionModel().removeListSelectionListener(selectorLista);
                selectorLista = null;
            }

            buttonGuardarCambios.setEnabled(false);
            buttonGuardar.setEnabled(true);
        }

    }//GEN-LAST:event_buttonGuardarCambiosActionPerformed

    private void buttonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEliminarActionPerformed
        if (tableButacas.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una butaca de la lista.");
            return;
        }

        int id = (int) tableButacas.getValueAt(tableButacas.getSelectedRow(), 0);

        Object[] opciones = {"Si", "No"};

        int resultado = JOptionPane.showOptionDialog(rootPane, "¿Seguro que desea eliminar la butaca?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (resultado == 0) {
            lugarDAO.borrarButaca(id);
        }

        llenarTableButacas();
    }//GEN-LAST:event_buttonEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEliminar;
    private javax.swing.JButton buttonGuardar;
    private javax.swing.JButton buttonGuardarCambios;
    private javax.swing.JButton buttonModificar;
    private javax.swing.JComboBox<Pelicula> comboBoxPelicula;
    private javax.swing.JComboBox<Proyeccion> comboBoxProyeccion;
    private javax.swing.JComboBox<Sala> comboBoxSala;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox radioButtonDisponible;
    private javax.swing.JTable tableButacas;
    private javax.swing.JTextField txtFila;
    private javax.swing.JTextField txtIDButaca;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables

}
