/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_inventarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class ingreso extends javax.swing.JPanel {

    private int contadorKardex = 1;
    private DefaultTableModel tableModel;
    private boolean modoEdicion = false;
    private String codigoMovimientoActual;

    /**
     * Creates new form ingreso
     */
    public ingreso() {
        initComponents();
        actualizarContadorKardex();
        String textoKardex = "IP" + String.format("%04d", contadorKardex);
        kardex.setText(textoKardex);
        String[] columnNames = {"C_Kardex", "C_Producto", "Producto", "Precio", "Stock", "Almacén", "Sector", "Fecha"};
        tableModel = new DefaultTableModel(columnNames, 0);
        jTable1.setModel(tableModel);

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDato();
            }
        });

        jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatoSeleccionado();
            }
        });

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modoEdicion) {
                    guardarCambios();
                } else {
                    agregarDato();
                }
            }
        });
    }

    private void editarDato() {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String codProducto = (String) jTable1.getValueAt(filaSeleccionada, 1);
            String producto = (String) jTable1.getValueAt(filaSeleccionada, 2);
            double precio = (double) jTable1.getValueAt(filaSeleccionada, 3);
            String stock = (String) jTable1.getValueAt(filaSeleccionada, 4);
            String almacen = (String) jTable1.getValueAt(filaSeleccionada, 5);
            String sector = (String) jTable1.getValueAt(filaSeleccionada, 6);
            Date fecha = (Date) jTable1.getValueAt(filaSeleccionada, 7);

            // Llenar los campos con los datos de la fila seleccionada
            jTextField2.setText(codProducto);
            jTextField3.setText(producto);
            jTextField4.setText(String.valueOf(precio));
            jTextField5.setText(stock);
            jComboBox1.setSelectedItem(almacen);
            jComboBox2.setSelectedItem(sector);
            jDateChooser1.setDate(fecha);

            // Activar el modo de edición
            modoEdicion = true;

            // Guardar el código de movimiento actual para referencia en la edición
            codigoMovimientoActual = (String) jTable1.getValueAt(filaSeleccionada, 0);
        }
    }

    private void guardarCambios() {
        // Obtener los nuevos valores
        String codProducto = jTextField2.getText();
        String producto = jTextField3.getText();
        double precio = Double.parseDouble(jTextField4.getText());
        String stock = jTextField5.getText();
        String almacen = jComboBox1.getSelectedItem().toString();
        String sector = jComboBox2.getSelectedItem().toString();
        Date fecha = jDateChooser1.getDate();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String queryUpdate = "UPDATE ingreso_producto SET cod_producto=?, producto=?, precio=?, stock=?, almacen=?, sector_almacen=?, fecha_ingreso=? WHERE cod_mov=?";
            try (PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate)) {
                statementUpdate.setString(1, codProducto);
                statementUpdate.setString(2, producto);
                statementUpdate.setDouble(3, precio);
                statementUpdate.setString(4, stock);
                statementUpdate.setString(5, almacen);
                statementUpdate.setString(6, sector);
                statementUpdate.setDate(7, new java.sql.Date(fecha.getTime()));
                statementUpdate.setString(8, codigoMovimientoActual);

                statementUpdate.executeUpdate();
            }

            // Limpiar campos después de guardar cambios
            limpiarCampos();
            modoEdicion = false;

            // Actualizar la tabla
            actualizarTabla(codigoMovimientoActual);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el producto en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDatoSeleccionado() {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada != -1) {
            String codMovimiento = (String) jTable1.getValueAt(filaSeleccionada, 0);

            // Eliminar la fila en la base de datos "ingreso_producto"
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
                String queryDelete = "DELETE FROM ingreso_producto WHERE cod_mov=?";
                try (PreparedStatement statementDelete = connection.prepareStatement(queryDelete)) {
                    statementDelete.setString(1, codMovimiento);

                    statementDelete.executeUpdate();
                }

                // Eliminar la fila en la base de datos "almacen1"
                String queryDeleteAlmacen = "DELETE FROM almacen1 WHERE codigoproducto=?";
                try (PreparedStatement statementDeleteAlmacen = connection.prepareStatement(queryDeleteAlmacen)) {
                    String codProducto = (String) jTable1.getValueAt(filaSeleccionada, 1);
                    statementDeleteAlmacen.setString(1, codProducto);

                    statementDeleteAlmacen.executeUpdate();
                }

                // Actualizar la tabla después de eliminar
                actualizarTabla(codMovimiento);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto de la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
        }
    }

    private void actualizarContadorKardex() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT MAX(cod_mov) FROM ingreso_producto";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtener el último valor de Kardex y incrementarlo
                        String ultimoKardex = resultSet.getString(1);
                        if (ultimoKardex != null && ultimoKardex.startsWith("IP")) {
                            int ultimoValor = Integer.parseInt(ultimoKardex.substring(2));
                            contadorKardex = ultimoValor + 1;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener el último valor de Kardex desde la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        kardex = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        buscador = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("INGRESO DE UN PRODUCTO");

        jLabel2.setText("CODIGO KARDEX: ");

        kardex.setEditable(false);
        kardex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kardexActionPerformed(evt);
            }
        });

        jLabel3.setText("CODIGO DEL PRODUCTO:");

        jLabel4.setText("PRODUCTO:");

        buscador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/vidrio-de-aumento (1).png"))); // NOI18N
        buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscadorActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setText("PRECIO");

        jTextField4.setEditable(false);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel6.setText("STOCK:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALMACEN A", "ALMACEN B", "ALMACEN C" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel7.setText("INGRESE FECHA:");

        jLabel8.setText("SECTOR DE ALMACEN:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ZONA DE ALTA ROTACION", "ZONA DE BAJA ROTACION ", "ZONA DE MEDIA ROTACION" }));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "C_Kardex", "C_Producto", "Producto", "Precio", "Stock", "Almacén", "Sector", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel9.setText("SELECCIONAR ALMACÉN:");

        jButton2.setText("INGRESAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("EDITAR");

        jButton5.setText("ELIMINAR");

        jButton1.setText("GUARDAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                        .addGap(12, 12, 12))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kardex, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(298, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(266, 266, 266)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kardex, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(137, 137, 137)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(333, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
    }// </editor-fold>//GEN-END:initComponents
private void actualizarTabla(String codMovimiento) {
        // Limpiar el modelo de la tabla
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT cod_mov, cod_producto, producto, precio, stock, almacen, sector_almacen, fecha_ingreso FROM ingreso_producto WHERE cod_mov = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, codMovimiento);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Object[] rowData = {
                            resultSet.getString("cod_mov"),
                            resultSet.getString("cod_producto"),
                            resultSet.getString("producto"),
                            resultSet.getDouble("precio"),
                            resultSet.getString("stock"),
                            resultSet.getString("almacen"),
                            resultSet.getString("sector_almacen"),
                            resultSet.getDate("fecha_ingreso")
                        };
                        tableModel.addRow(rowData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void kardexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kardexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kardexActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String codigoProducto = jTextField2.getText();
        String nombreProducto = jTextField3.getText();
        double precioProducto = 0.0;
        String stockProducto = jTextField5.getText();
        String almacen = jComboBox1.getSelectedItem().toString();
        String sector = jComboBox2.getSelectedItem().toString();
        java.util.Date fechaIngreso = jDateChooser1.getDate();
        String codigoMovimiento = "IP" + String.format("%04d", contadorKardex - 1);

        // Validar que los campos obligatorios estén llenos
        if (codigoProducto.isEmpty() || nombreProducto.isEmpty() || stockProducto.isEmpty() || fechaIngreso == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar que el precio sea un número válido
        String precioText = jTextField4.getText();
        if (!precioText.isEmpty()) {
            try {
                precioProducto = Double.parseDouble(precioText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            // Insertar en la tabla "ingreso_producto"
            String ingresoProductoQuery = "INSERT INTO ingreso_producto (cod_mov, cod_producto, producto, precio, stock, almacen, sector_almacen, fecha_ingreso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ingresoProductoStatement = connection.prepareStatement(ingresoProductoQuery)) {
                ingresoProductoStatement.setString(1, "IP" + String.format("%04d", contadorKardex));
                ingresoProductoStatement.setString(2, codigoProducto);
                ingresoProductoStatement.setString(3, nombreProducto);
                ingresoProductoStatement.setDouble(4, precioProducto);
                ingresoProductoStatement.setString(5, stockProducto);
                ingresoProductoStatement.setString(6, almacen);
                ingresoProductoStatement.setString(7, sector);
                ingresoProductoStatement.setDate(8, new java.sql.Date(fechaIngreso.getTime()));

                ingresoProductoStatement.executeUpdate();
            }
            String almacenQuery = "INSERT INTO almacen1 (almacen, sector,codigoproducto, producto, Precio, stock, Fecha) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement almacenStatement = connection.prepareStatement(almacenQuery)) {
                almacenStatement.setString(1, almacen);
                almacenStatement.setString(2, sector);
                almacenStatement.setString(3, codigoProducto);
                almacenStatement.setString(4, nombreProducto);
                almacenStatement.setDouble(5, precioProducto);
                almacenStatement.setString(6, stockProducto);
                almacenStatement.setDate(7, new java.sql.Date(fechaIngreso.getTime()));

                almacenStatement.executeUpdate();
                limpiarCampos();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar el producto en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
        actualizarTabla(codigoMovimiento);
        limpiarCampos();
        contadorKardex++;

    }//GEN-LAST:event_jButton2ActionPerformed
    private void limpiarCampos() {

        jTextField2.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
        jTextField4.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jDateChooser1.setDate(null);

    }

    private void agregarDato() {
        String codigoProducto = jTextField2.getText();
        String nombreProducto = jTextField3.getText();
        double precioProducto = 0.0;
        String stockProducto = jTextField5.getText();
        String almacen = jComboBox1.getSelectedItem().toString();
        String sector = jComboBox2.getSelectedItem().toString();
        java.util.Date fechaIngreso = jDateChooser1.getDate();
        String codigoMovimiento = "IP" + String.format("%04d", contadorKardex - 1);

        // Validar que los campos obligatorios estén llenos
        if (codigoProducto.isEmpty() || nombreProducto.isEmpty() || stockProducto.isEmpty() || fechaIngreso == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar que el precio sea un número válido
        String precioText = jTextField4.getText();
        if (!precioText.isEmpty()) {
            try {
                precioProducto = Double.parseDouble(precioText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            // Insertar en la tabla "ingreso_producto"
            String ingresoProductoQuery = "INSERT INTO ingreso_producto (cod_mov, cod_producto, producto, precio, stock, almacen, sector_almacen, fecha_ingreso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ingresoProductoStatement = connection.prepareStatement(ingresoProductoQuery)) {
                ingresoProductoStatement.setString(1, "IP" + String.format("%04d", contadorKardex));
                ingresoProductoStatement.setString(2, codigoProducto);
                ingresoProductoStatement.setString(3, nombreProducto);
                ingresoProductoStatement.setDouble(4, precioProducto);
                ingresoProductoStatement.setString(5, stockProducto);
                ingresoProductoStatement.setString(6, almacen);
                ingresoProductoStatement.setString(7, sector);
                ingresoProductoStatement.setDate(8, new java.sql.Date(fechaIngreso.getTime()));

                ingresoProductoStatement.executeUpdate();
            }

            // Limpiar campos después de agregar
            limpiarCampos();

            // Incrementar el contador de Kardex
            contadorKardex++;

            // Actualizar la tabla
            actualizarTabla(codigoMovimiento);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar el producto en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void buscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscadorActionPerformed

        String codigoProducto = jTextField2.getText();
        if (codigoProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Conectar a la base de datos y ejecutar la consulta
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT nombre, precio FROM producto WHERE codigo = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, codigoProducto);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {

                        String nombreProducto = resultSet.getString("nombre");
                        double precioProducto = resultSet.getDouble("precio");

                        jTextField3.setText(nombreProducto);
                        jTextField4.setText(String.valueOf(precioProducto));
                    } else {

                        JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_buscadorActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscador;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField kardex;
    // End of variables declaration//GEN-END:variables
}
