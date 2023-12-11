package com.mycompany.sistema_inventarios;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class retiradak extends javax.swing.JPanel {

    private Connection con;
    private static retiradak instancia;

    public retiradak() {
        initComponents();

        connectToDatabase();

        populateTable();
    }

    public synchronized static retiradak obtenerInstancia() {
        if (instancia == null) {
            instancia = new retiradak();
        }
        return instancia;
    }

    private void connectToDatabase() {
        try {

            String url = "jdbc:mysql://localhost:3306/inventarios";
            String user = "root";
            String password = "";

            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
    }

    private void populateTable() {
        try {

            String query = "SELECT cod_mov, nombre_trabajador, cod_producto, producto, precio, almacen, stock_total, stock_salida, cliente, encargado_recepcion, fecha_retirada FROM retirada_venta";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getObject("cod_mov"),
                    rs.getObject("nombre_trabajador"),
                    rs.getObject("cod_producto"),
                    rs.getObject("producto"),
                    rs.getObject("precio"),
                    rs.getObject("almacen"),
                    rs.getObject("stock_total"),
                    rs.getObject("stock_salida"),
                    rs.getObject("cliente"),
                    rs.getObject("encargado_recepcion"),
                    rs.getObject("fecha_retirada")
                };
                model.addRow(row);
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
    }

    public void actualizarTabla(ResultSet rs) {
        try {

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getObject("cod_mov"),
                    rs.getObject("nombre_trabajador"),
                    rs.getObject("cod_producto"),
                    rs.getObject("producto"),
                    rs.getObject("precio"),
                    rs.getObject("almacen"),
                    rs.getObject("stock_total"),
                    rs.getObject("stock_salida"),
                    rs.getObject("cliente"),
                    rs.getObject("encargado_recepcion"),
                    rs.getObject("fecha_retirada")
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            // Handle any potential SQL errors
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        IMPRIMIR = new javax.swing.JButton();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BUSCAR = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo kardex", "Encargado", "codigo de producto", "producto", "precio", "almacen", "stock_total", "stock Retirada", "Cliente", "Encargado de recepcion", "fecha movimiento"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setText("HISTORIAL KARDEX");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("codigo de producto:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Ingrese fecha inical: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("hasta:");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALMACEN A", "ALMACEN B", "ALMACEN C" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        IMPRIMIR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IMPRIMIR.setText("IMPRIMIR");
        IMPRIMIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IMPRIMIRActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("codigo de kardex:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Seleccione almacen:");

        BUSCAR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        BUSCAR.setText("BUSCAR");
        BUSCAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUSCARActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(174, 174, 174)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(jComboBox2, 0, 198, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(174, 174, 174)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BUSCAR, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IMPRIMIR, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(485, 485, 485)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(BUSCAR, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(IMPRIMIR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void IMPRIMIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IMPRIMIRActionPerformed
   try {
    // Ruta del archivo Jasper
    String reportName = "/reporte/retirada.jasper";

    // Parámetros del informe
    Map<String, Object> parameters = new HashMap<>();
    String mov = jTextField1.getText();
    String codProducto = jTextField2.getText();
    String selectedAlmacen = (String) jComboBox2.getSelectedItem();
    // Obtener las fechas seleccionadas
    java.util.Date fechaInicial = jDateChooser1.getDate();
    java.util.Date fechaFinal = jDateChooser3.getDate();

    // Agregar los valores al mapa de parámetros solo si no están vacíos
    if (!mov.isEmpty()) {
        parameters.put("cod_mov", mov);
    }
    if (!codProducto.isEmpty()) {
        parameters.put("cod_producto", codProducto);
    }
    if (selectedAlmacen != null && !selectedAlmacen.isEmpty()) {
        parameters.put("almacen", selectedAlmacen);
    }
    if (fechaInicial != null) {
        parameters.put("fechaInicial", fechaInicial);
    }
    if (fechaFinal != null) {
        parameters.put("fechaFinal", fechaFinal);
    }

    // InputStream del archivo Jasper
    InputStream inputStream = getClass().getResourceAsStream(reportName);

    if (inputStream == null) {
        throw new JRException("El archivo Jasper no se encontró: " + reportName);
    }

    // Llenar el informe con datos y parámetros
    JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, con);

    // Visualizar el informe
    JasperViewer.viewReport(jasperPrint, false);
} catch (JRException e) {
    e.printStackTrace();
}
        
    }//GEN-LAST:event_IMPRIMIRActionPerformed

    private void BUSCARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUSCARActionPerformed
        try {

            String codKardex = jTextField1.getText();
            String codProducto = jTextField2.getText();
            String almacen = (String) jComboBox2.getSelectedItem();

            java.util.Date fechaInicial = jDateChooser1.getDate();
            java.util.Date fechaFinal = jDateChooser3.getDate();

            java.sql.Date sqlFechaInicial = (fechaInicial != null) ? new java.sql.Date(fechaInicial.getTime()) : null;
            java.sql.Date sqlFechaFinal = (fechaFinal != null) ? new java.sql.Date(fechaFinal.getTime()) : null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaInicialStr = (sqlFechaInicial != null) ? sdf.format(sqlFechaInicial) : null;
            String fechaFinalStr = (sqlFechaFinal != null) ? sdf.format(sqlFechaFinal) : null;

            StringBuilder queryBuilder = new StringBuilder("SELECT cod_mov, nombre_trabajador, cod_producto, producto, precio, almacen, stock_total, stock_salida, cliente, encargado_recepcion, fecha_retirada FROM retirada_venta WHERE 1 = 1");

            if (!codKardex.isEmpty()) {
                queryBuilder.append(" AND cod_mov = '").append(codKardex).append("'");
            }

            if (!codProducto.isEmpty()) {
                queryBuilder.append(" AND cod_producto = '").append(codProducto).append("'");
            }

            if (!almacen.isEmpty()) {
                queryBuilder.append(" AND almacen = '").append(almacen).append("'");
            }

            if (sqlFechaInicial != null && sqlFechaFinal != null) {
                queryBuilder.append(" AND fecha_retirada BETWEEN '").append(fechaInicialStr).append("' AND '").append(fechaFinalStr).append("'");
            }

            PreparedStatement pst = con.prepareStatement(queryBuilder.toString());
            ResultSet rs = pst.executeQuery();

            actualizarTabla(rs);

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_BUSCARActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BUSCAR;
    private javax.swing.JButton IMPRIMIR;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
