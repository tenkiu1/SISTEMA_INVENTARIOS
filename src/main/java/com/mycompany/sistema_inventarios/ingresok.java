
package com.mycompany.sistema_inventarios;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author DIMAR FF
 */
public class ingresok extends javax.swing.JPanel {
private Connection con;
    public ingresok() {
        initComponents();
        connectToDatabase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        populateTable(model);
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


    void populateTable(DefaultTableModel model) {
        try {
       
            String query = "SELECT cod_mov, cod_producto, producto, precio, stock, almacen, sector_almacen, fecha_ingreso FROM ingreso_producto";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

        
            model.setRowCount(0);


            while (rs.next()) {
                Object[] row = {
                    rs.getObject("cod_mov"),
                    rs.getObject("cod_producto"),
                    rs.getObject("producto"),
                    rs.getObject("precio"),
                    rs.getObject("stock"),
                    rs.getObject("almacen"),
                    rs.getObject("sector_almacen"),
                    rs.getObject("fecha_ingreso")
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
                rs.getObject("cod_producto"),
                rs.getObject("producto"),
                rs.getObject("precio"),
                rs.getObject("stock"),
                rs.getObject("almacen"),
                rs.getObject("sector_almacen"),
                rs.getObject("fecha_ingreso")
            };
            model.addRow(row);
        }

        // Cierra el ResultSet
    } catch (SQLException ex) {
        // Handle any potential SQL errors
        ex.printStackTrace();
    }
}
  
 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        IMPRIMIR = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        BUSCAR = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo kardex", "codigo de producto", "producto", "precio", "stock ingresado", "almacen", "sector", "fecha movimiento"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        IMPRIMIR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IMPRIMIR.setText("IMPRIMIR");
        IMPRIMIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IMPRIMIRActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("codigo de kardex:");

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

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(47, 47, 47)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                    .addGap(40, 40, 40))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel2)
                                .addGap(41, 41, 41)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(BUSCAR, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(IMPRIMIR, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(49, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BUSCAR)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(IMPRIMIR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void IMPRIMIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IMPRIMIRActionPerformed
try {
    // Ruta del archivo Jasper
    String reportName = "/reporte/Ingreso.jasper";

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

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

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


        StringBuilder queryBuilder = new StringBuilder("SELECT cod_mov, cod_producto, producto, precio, stock, almacen, sector_almacen, fecha_ingreso FROM ingreso_producto WHERE 1 = 1");

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
            queryBuilder.append(" AND fecha_ingreso BETWEEN '").append(fechaInicialStr).append("' AND '").append(fechaFinalStr).append("'");
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
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
