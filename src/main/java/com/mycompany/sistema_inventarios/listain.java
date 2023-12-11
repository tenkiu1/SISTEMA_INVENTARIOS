package com.mycompany.sistema_inventarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class listain extends javax.swing.JPanel {

    public listain() {
        initComponents();
        mostrarDatosEnTabla();
    }

    private void mostrarDatosEnTabla() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        Map<String, Map<String, Object>> datosAgrupados = new HashMap<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT almacen, sector, codigoproducto, producto, Precio, SUM(stock) as stock, MIN(Fecha) as Fecha FROM almacen1 GROUP BY almacen, sector, codigoproducto, producto, Precio";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String nalmacen = resultSet.getString("almacen");
                        String nsector = resultSet.getString("sector");
                        String codProducto = resultSet.getString("codigoproducto");
                        String nproducto = resultSet.getString("producto");
                        String nprecio = String.valueOf(resultSet.getDouble("Precio"));
                        int stock = resultSet.getInt("stock");
                        String nfecha = resultSet.getString("Fecha");

                        // Verificar si el stock es negativo
                        if (stock >= 0) {
                            String clave = nalmacen + nsector + codProducto;
                            if (!datosAgrupados.containsKey(clave) || nfecha.compareTo(datosAgrupados.get(clave).get("Fecha").toString()) < 0) {
                                Map<String, Object> nuevoRegistro = new HashMap<>();
                                nuevoRegistro.put("almacen", nalmacen);
                                nuevoRegistro.put("sector", nsector);
                                nuevoRegistro.put("codigoproducto", codProducto);
                                nuevoRegistro.put("producto", nproducto);
                                nuevoRegistro.put("Precio", nprecio);
                                nuevoRegistro.put("stock", stock);
                                nuevoRegistro.put("Fecha", nfecha);

                                datosAgrupados.put(clave, nuevoRegistro);
                            }
                        }
                    }
                }
            }

            for (Map.Entry<String, Map<String, Object>> entry : datosAgrupados.entrySet()) {
                Map<String, Object> registro = entry.getValue();

                model.addRow(new Object[]{
                    registro.get("almacen"),
                    registro.get("sector"),
                    registro.get("codigoproducto"),
                    registro.get("producto"),
                    registro.get("Precio"),
                    registro.get("stock"),
                    registro.get("Fecha")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void actualizarTabla(ResultSet rs) {
    try {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0);

        while (rs.next()) {
            Object[] row = {
                rs.getObject("almacen"),
                rs.getObject("sector"),
                rs.getObject("codigoproducto"),
                rs.getObject("producto"),
                rs.getObject("Precio"),
                rs.getObject("stock"),
                rs.getObject("Fecha")
            };
            model.addRow(row);
        }
    } catch (SQLException ex) {
        // Handle any potential SQL errors
        ex.printStackTrace();
    }
}
private void buscar() {
  try {
        String codProducto = jTextField2.getText();
        String almacen = jComboBox2.getSelectedItem().toString();

        java.util.Date fecha = jDateChooser1.getDate();
        java.sql.Date sqlFecha = (fecha != null) ? new java.sql.Date(fecha.getTime()) : null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaStr = (sqlFecha != null) ? sdf.format(sqlFecha) : null;

        // Crear la consulta SQL base
        StringBuilder queryBuilder = new StringBuilder("SELECT almacen, sector, codigoproducto, producto, Precio, SUM(stock) as stock, MIN(Fecha) as Fecha FROM almacen1 WHERE 1 = 1");

        // Agregar condiciones según los campos llenados en la interfaz gráfica
        if (!codProducto.isEmpty()) {
            queryBuilder.append(" AND codigoproducto = ?");
        }

        if (!almacen.isEmpty()) {
            queryBuilder.append(" AND almacen = ?");
        }

        if (sqlFecha != null) {
            queryBuilder.append(" AND Fecha = ?");
        }

        // Conectar a la base de datos y ejecutar la consulta
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
                int parameterIndex = 1;

                // Configurar parámetros de la consulta según los campos llenados
                if (!codProducto.isEmpty()) {
                    statement.setString(parameterIndex++, codProducto);
                }

                if (!almacen.isEmpty()) {
                    statement.setString(parameterIndex++, almacen);
                }

                if (sqlFecha != null) {
                    statement.setDate(parameterIndex++, sqlFecha);
                }

                // Ejecutar la consulta y actualizar la tabla
                try (ResultSet resultSet = statement.executeQuery()) {
                    actualizarTabla(resultSet);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    private void eliminarRegistro(String almacen, String sector, String codProducto) {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
        String query = "DELETE FROM almacen1 WHERE almacen = ? AND sector = ? AND codigoproducto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, almacen);
            statement.setString(2, sector);
            statement.setString(3, codProducto);
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    
    private void mostrarFormularioEdicion(Map<String, Object> datosActuales) {
   
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        buscar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("SELECCIONE ALMACÉN: ");

        jLabel2.setText("CODIGO DE PRODUCTO:");

        jLabel3.setText("SELECCIONE SECTOR: ");

        jLabel4.setText("SELECCIONE FECHA: ");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("INVENTARIO GENERAL");

        buscar.setText("BUSCAR");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        jButton2.setText("ELIMINAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ALMACEN", "SECTOR", "CODIGO PRODUCTO", "PRODUCTO", "PRECIO", "STOCK", "FECHA DE INGRESO"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ZONA DE ALTA ROTACION", "ZONA DE BAJA ROTACION ", "ZONA DE MEDIA ROTACION" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALMACEN A", "ALMACEN B", "ALMACEN C" }));

        jButton1.setText("ACTUALIZAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(63, 63, 63)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(95, 95, 95)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(316, 316, 316)
                                .addComponent(jLabel5)))
                        .addGap(0, 106, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(97, 97, 97))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
       buscar();
    }//GEN-LAST:event_buscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mostrarDatosEnTabla();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         // Obtener la fila seleccionada
    int filaSeleccionada = jTable1.getSelectedRow();

    // Verificar si hay una fila seleccionada
    if (filaSeleccionada != -1) {
        // Obtener los valores de la fila seleccionada
        String almacen = jTable1.getValueAt(filaSeleccionada, 0).toString();
        String sector = jTable1.getValueAt(filaSeleccionada, 1).toString();
        String codProducto = jTable1.getValueAt(filaSeleccionada, 2).toString();
        // Agrega cualquier otra columna que necesites para identificar el registro

        // Realizar la eliminación en la base de datos
        eliminarRegistro(almacen, sector, codProducto);
        
        // Actualizar la tabla después de eliminar
        mostrarDatosEnTabla();
    } else {
       
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
