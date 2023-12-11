/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistema_inventarios;

import Modelo.controlador_packing;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.RowFilter;



public class Packing_List extends javax.swing.JFrame {
private controlador_packing controlador;
private TableRowSorter trsfiltro;
    String filtro;

    public Packing_List() {
        initComponents();
        
    }
 public void setControlador(controlador_packing controlador) {
        this.controlador = controlador;
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        btnarchivo = new javax.swing.JButton();
        btnagregar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        datofecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        btneliminar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        almacendatos = new javax.swing.JComboBox<>();
        txtproveedor = new javax.swing.JTextField();
        btnlistar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btnconfirmar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnpdf = new javax.swing.JButton();
        btnbuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("REPORTE DE PACKING LIST");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        jLabel4.setText("Nombre del Proveedor:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        jLabel5.setText("Archivo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        jLabel6.setText("Fecha de Ingreso:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        txtcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoKeyTyped(evt);
            }
        });
        jPanel1.add(txtcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 190, 30));

        btnarchivo.setText("Seleccionar Archivo");
        btnarchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnarchivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnarchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 190, 30));

        btnagregar.setText("Subir");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });
        jPanel1.add(btnagregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 130, 30));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Almacen", "Proveedor", "Archivo", "Fecha"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabla);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 620, 115));

        datofecha.setToolTipText("");
        jPanel1.add(datofecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 190, 30));

        jLabel7.setText("Codigo:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        btneliminar.setText("Eliminar");
        jPanel1.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, 130, 30));

        jLabel8.setText("Almacen");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        almacendatos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C" }));
        jPanel1.add(almacendatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 190, 30));
        jPanel1.add(txtproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 190, 30));

        btnlistar.setText("Listar");
        jPanel1.add(btnlistar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 60, 30));

        btneditar.setText("Editar");
        jPanel1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 70, 30));

        btnconfirmar.setText("OK");
        jPanel1.add(btnconfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 60, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/atras.png"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 40));

        btnpdf.setText("Abrir Pdf");
        jPanel1.add(btnpdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 240, 130, 30));

        btnbuscar.setText("buscar");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(525, 120, -1, 30));

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

    private void btnarchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnarchivoActionPerformed
        
    }//GEN-LAST:event_btnarchivoActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
    
    }//GEN-LAST:event_tablaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     this.dispose();

        // Notifica al controlador que se ha confirmado
        
               // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        txtcodigo.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(final KeyEvent e){
                    String cadena =txtcodigo.getText();
                    txtcodigo.setText(cadena);
                    repaint();
                    filtro();
                }});

    }//GEN-LAST:event_btnbuscarActionPerformed

    private void txtcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyTyped
        trsfiltro = new TableRowSorter(tabla.getModel());
        tabla.setRowSorter(trsfiltro);
    }//GEN-LAST:event_txtcodigoKeyTyped

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
      
    }//GEN-LAST:event_btnagregarActionPerformed

    public void filtro(){
    filtro=txtcodigo.getText();
    trsfiltro.setRowFilter(RowFilter.regexFilter(txtcodigo.getText(), 0));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
      Packing_List plist=new Packing_List();
      controlador_packing cp=new controlador_packing(plist);
      cp.iniciar();
      plist.setVisible(true);
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> almacendatos;
    public javax.swing.JButton btnagregar;
    public javax.swing.JButton btnarchivo;
    private javax.swing.JButton btnbuscar;
    public javax.swing.JButton btnconfirmar;
    public javax.swing.JButton btneditar;
    public javax.swing.JButton btneliminar;
    public javax.swing.JButton btnlistar;
    public javax.swing.JButton btnpdf;
    public com.toedter.calendar.JDateChooser datofecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable tabla;
    public javax.swing.JTextField txtcodigo;
    public javax.swing.JTextField txtproveedor;
    // End of variables declaration//GEN-END:variables
}
