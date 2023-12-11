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
public class retirada extends javax.swing.JPanel {

    private int contadorMovimiento = 1;
    private DefaultTableModel tableModel;
    private boolean modoEdicion = false;
    private String codigoMovimientoActual;

    public retirada() {
        initComponents();
        obtenerUltimoCodigoMovimiento();
        String textoKardex = "RV" + String.format("%04d", contadorMovimiento);
        kardex2.setText(textoKardex);

        String[] columnNames = {"Código Movimiento", "Nombre Trabajador", "Código Producto", "Producto", "Precio", "Almacén", "Stock Total", "Stock Salida", "Cliente", "Encargado Recepción", "Fecha Retirada"};
        tableModel = new DefaultTableModel(columnNames, 0);
        jTable1.setModel(tableModel);

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDatoRetirada();
            }
        });

        // Agregar ActionListener al botón "Eliminar"
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatoRetirada();
            }
        });

        // Agregar ActionListener al botón "Guardar"
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modoEdicion) {
                    guardarCambiosRetirada();
                } else {
                    agregarDatoRetirada();
                }
            }
        });

        buscador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }

    private void obtenerUltimoCodigoMovimiento() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT MAX(cod_mov) FROM retirada_venta";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {

                        String ultimoKardex = resultSet.getString(1);
                        if (ultimoKardex != null && ultimoKardex.startsWith("RV")) {
                            int ultimoValor = Integer.parseInt(ultimoKardex.substring(2));
                            contadorMovimiento = ultimoValor + 1;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener el último valor de Kardex desde la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void editarDatoRetirada() {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            String codigoMovimiento = (String) jTable1.getValueAt(filaSeleccionada, 0);

            String nombreTrabajador = jTextField7.getText();
            String codigoProducto = jTextField5.getText();
            String producto = jTextField4.getText();
            double precio = Double.parseDouble(jTextField6.getText());
            String almacen = jTextField12.getText();
            int stockTotal = Integer.parseInt(jTextField8.getText());
            int stockSalida = Integer.parseInt(jTextField9.getText());
            String cliente = jTextField10.getText();
            String encargadoRecepcion = jTextField11.getText();
            Date fechaRetirada = jDateChooser1.getDate();

            jTextField7.setText(nombreTrabajador);
            jTextField5.setText(codigoProducto);
            jTextField4.setText(producto);
            jTextField6.setText(String.valueOf(precio));
            jTextField12.setText(almacen);
            jTextField8.setText(String.valueOf(stockTotal));
            jTextField9.setText(String.valueOf(stockSalida));
            jTextField10.setText(cliente);
            jTextField11.setText(encargadoRecepcion);
            jDateChooser1.setDate(fechaRetirada);

            modoEdicion = true;

            codigoMovimientoActual = codigoMovimiento;
        }
    }

    private void eliminarDatoRetirada() {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada != -1) {
            String codigoMovimiento = (String) jTable1.getValueAt(filaSeleccionada, 0);
            eliminarDatoRetirada(codigoMovimiento);

            tableModel.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
        }
    }

    private void actualizarDatoRetirada(String codigoMovimiento, String nombreTrabajador, String codigoProducto, String producto, double precio,
            String almacen, int stockTotal, int stockSalida, String cliente, String encargadoRecepcion, Date fechaRetirada) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {

            String queryUpdate = "UPDATE retirada_venta SET nombre_trabajador=?, cod_producto=?, producto=?, precio=?, almacen=?, "
                    + "stock_total=?, stock_salida=?, cliente=?, encargado_recepcion=?, fecha_retirada=? WHERE cod_mov=?";
            try (PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate)) {
                statementUpdate.setString(1, nombreTrabajador);
                statementUpdate.setString(2, codigoProducto);
                statementUpdate.setString(3, producto);
                statementUpdate.setDouble(4, precio);
                statementUpdate.setString(5, almacen);
                statementUpdate.setInt(6, stockTotal);
                statementUpdate.setInt(7, stockSalida);
                statementUpdate.setString(8, cliente);
                statementUpdate.setString(9, encargadoRecepcion);
                statementUpdate.setDate(10, new java.sql.Date(fechaRetirada.getTime()));
                statementUpdate.setString(11, codigoMovimiento);

                int filasAfectadas = statementUpdate.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Retirada actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la retirada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDatoRetirada(String codigoMovimiento) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {

            String queryDelete = "DELETE FROM retirada_venta WHERE cod_mov=?";
            try (PreparedStatement statementDelete = connection.prepareStatement(queryDelete)) {
                statementDelete.setString(1, codigoMovimiento);

                int filasAfectadas = statementDelete.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Retirada eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la retirada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCambiosRetirada() {

        String nombreTrabajador = jTextField7.getText();
        String codigoProducto = jTextField5.getText();
        String producto = jTextField4.getText();
        double precio = Double.parseDouble(jTextField6.getText());
        String almacen = jTextField12.getText();
        int stockTotal = Integer.parseInt(jTextField8.getText());
        int stockSalida = Integer.parseInt(jTextField9.getText());
        String cliente = jTextField10.getText();
        String encargadoRecepcion = jTextField11.getText();
        Date fechaRetirada = jDateChooser1.getDate();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(codigoMovimientoActual)) {
                tableModel.setValueAt(nombreTrabajador, i, 1);
                tableModel.setValueAt(codigoProducto, i, 2);
                tableModel.setValueAt(producto, i, 3);
                tableModel.setValueAt(precio, i, 4);
                tableModel.setValueAt(almacen, i, 5);
                tableModel.setValueAt(stockTotal, i, 6);
                tableModel.setValueAt(stockSalida, i, 7);
                tableModel.setValueAt(cliente, i, 8);
                tableModel.setValueAt(encargadoRecepcion, i, 9);
                tableModel.setValueAt(fechaRetirada, i, 10);
                break;
            }
        }

        actualizarDatoRetirada(codigoMovimientoActual, nombreTrabajador, codigoProducto, producto, precio, almacen, stockTotal, stockSalida, cliente, encargadoRecepcion, fechaRetirada);
        limpiarCamposRetirada();
        modoEdicion = false;
    }

    private void limpiarCamposRetirada() {

        jTextField7.setText("");
        jTextField5.setText("");
        jTextField4.setText("");
        jTextField6.setText("");
        jTextField12.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jDateChooser1.setDate(null);
    }

    private void agregarDatoRetirada() {
        String codigoMovimiento = "RV" + String.format("%04d", contadorMovimiento - 1);
        String nombreTrabajador = jTextField7.getText();
        String codigoProducto = jTextField5.getText();
        String producto = jTextField4.getText();
        String precioText = jTextField6.getText();
        String almacen = jTextField12.getText();
        String stockTotalText = jTextField8.getText();
        String stockSalidaText = jTextField9.getText();
        String cliente = jTextField10.getText();
        String encargadoRecepcion = jTextField11.getText();
        Date fechaRetirada = jDateChooser1.getDate();
        if (nombreTrabajador.isEmpty() || codigoProducto.isEmpty() || producto.isEmpty()
                || precioText.isEmpty() || almacen.isEmpty() || stockTotalText.isEmpty()
                || stockSalidaText.isEmpty() || cliente.isEmpty() || encargadoRecepcion.isEmpty() || fechaRetirada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double precio = Double.parseDouble(precioText);
        int stockTotal = Integer.parseInt(stockTotalText);
        int stockSalida = Integer.parseInt(stockSalidaText);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            connection.setAutoCommit(false);

            try {

                String queryInsert = "INSERT INTO retirada_venta (cod_mov, nombre_trabajador, cod_producto, producto, precio, almacen, stock_total, stock_salida, cliente, encargado_recepcion, fecha_retirada) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
                    statementInsert.setString(1, codigoMovimiento);
                    statementInsert.setString(2, nombreTrabajador);
                    statementInsert.setString(3, codigoProducto);
                    statementInsert.setString(4, producto);
                    statementInsert.setDouble(5, precio);
                    statementInsert.setString(6, almacen);
                    statementInsert.setInt(7, stockTotal);
                    statementInsert.setInt(8, stockSalida);
                    statementInsert.setString(9, cliente);
                    statementInsert.setString(10, encargadoRecepcion);
                    statementInsert.setDate(11, new java.sql.Date(fechaRetirada.getTime()));

                    int filasAfectadas = statementInsert.executeUpdate();
                    if (filasAfectadas > 0) {
                        String queryUpdate = "UPDATE almacen1 SET stock = stock - ? WHERE codigoproducto = ? AND  almacen = ?";
                        try (PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate)) {
                            statementUpdate.setInt(1, stockSalida);
                            statementUpdate.setString(2, codigoProducto);
                            statementUpdate.executeUpdate();
                        }
                        connection.commit();
                        JOptionPane.showMessageDialog(this, "Retirada registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        actualizarTabla(codigoMovimiento);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar la retirada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        limpiarCamposRetirada();
        obtenerUltimoCodigoMovimiento();
    }

   private void registrarRetirada1() {
    String codigoMovimiento = "RV" + String.format("%04d", contadorMovimiento - 1);
    String nombreTrabajador = jTextField7.getText();
    String codigoProducto = jTextField5.getText();
    String producto = jTextField4.getText();
    String precioText = jTextField6.getText();
    String almacen = jTextField12.getText();
    String stockTotalText = jTextField8.getText();
    String stockSalidaText = jTextField9.getText();
    String cliente = jTextField10.getText();
    String encargadoRecepcion = jTextField11.getText();
    Date fechaRetirada = jDateChooser1.getDate();

    // Validar que los campos obligatorios estén llenos
    if (nombreTrabajador.isEmpty() || codigoProducto.isEmpty() || producto.isEmpty()
            || precioText.isEmpty() || almacen.isEmpty() || stockTotalText.isEmpty()
            || stockSalidaText.isEmpty() || cliente.isEmpty() || encargadoRecepcion.isEmpty() || fechaRetirada == null) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Convertir los valores a los tipos adecuados
    double precio = Double.parseDouble(precioText);
    int stockTotal = Integer.parseInt(stockTotalText);
    int stockSalida = Integer.parseInt(stockSalidaText);

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
        connection.setAutoCommit(false);

        try {
            // Insertar en la tabla retirada_venta
            String queryInsert = "INSERT INTO retirada_venta (cod_mov, nombre_trabajador, cod_producto, producto, precio, almacen, stock_total, stock_salida, cliente, encargado_recepcion, fecha_retirada) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
                statementInsert.setString(1, "RV" + String.format("%04d", contadorMovimiento));
                statementInsert.setString(2, nombreTrabajador);
                statementInsert.setString(3, codigoProducto);
                statementInsert.setString(4, producto);
                statementInsert.setDouble(5, precio);
                statementInsert.setString(6, almacen);
                statementInsert.setInt(7, stockTotal);
                statementInsert.setInt(8, stockSalida);
                statementInsert.setString(9, cliente);
                statementInsert.setString(10, encargadoRecepcion);
                statementInsert.setDate(11, new java.sql.Date(fechaRetirada.getTime()));

                int filasAfectadas = statementInsert.executeUpdate();
                if (filasAfectadas > 0) {
                    // Actualizar el stock en el almacén
                    String queryUpdate = "UPDATE almacen1 SET stock = stock - ? WHERE codigoproducto = ? AND almacen = ?";
                    try (PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate)) {
                        statementUpdate.setInt(1, stockSalida);
                        statementUpdate.setString(2, codigoProducto);
                        statementUpdate.setString(3, almacen);
                        statementUpdate.executeUpdate();

                        // Verificar el nuevo stock después de la retirada
                        String queryCheckStock = "SELECT stock FROM almacen1 WHERE codigoproducto = ? AND almacen = ?";
                        try (PreparedStatement statementCheckStock = connection.prepareStatement(queryCheckStock)) {
                            statementCheckStock.setString(1, codigoProducto);
                            statementCheckStock.setString(2, almacen);
                            try (ResultSet resultSetCheckStock = statementCheckStock.executeQuery()) {
                                if (resultSetCheckStock.next()) {
                                    int nuevoStock = resultSetCheckStock.getInt("stock");
                                    if (nuevoStock < 0) {
                                        // Stock negativo, eliminar la fila
                                        String queryEliminarFila = "DELETE FROM almacen1 WHERE codigoproducto = ? AND almacen = ?";
                                        try (PreparedStatement statementEliminarFila = connection.prepareStatement(queryEliminarFila)) {
                                            statementEliminarFila.setString(1, codigoProducto);
                                            statementEliminarFila.setString(2, almacen);
                                            statementEliminarFila.executeUpdate();
                                        }
                                    }
                                }
                            }
                        }

                        connection.commit();
                        JOptionPane.showMessageDialog(this, "Retirada registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        actualizarTabla(codigoMovimiento);
                        contadorMovimiento++;
                    }
                }
            }
        } catch (SQLException ex) {
            connection.rollback();
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            connection.setAutoCommit(true);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    limpiarCamposRetirada();
    obtenerUltimoCodigoMovimiento();
}
    private void actualizarTabla(String codMovimiento) {
        // Limpiar el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT cod_mov, nombre_trabajador, cod_producto, producto, precio, almacen, stock_total, stock_salida, cliente, encargado_recepcion, fecha_retirada FROM retirada_venta WHERE cod_mov = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, codMovimiento);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Object[] rowData = {
                            resultSet.getString("cod_mov"),
                            resultSet.getString("nombre_trabajador"),
                            resultSet.getString("cod_producto"),
                            resultSet.getString("producto"),
                            resultSet.getDouble("precio"),
                            resultSet.getString("almacen"),
                            resultSet.getInt("stock_total"),
                            resultSet.getInt("stock_salida"),
                            resultSet.getString("cliente"),
                            resultSet.getString("encargado_recepcion"),
                            resultSet.getDate("fecha_retirada")
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

    private void buscarProducto() {
        String codigoProducto = jTextField5.getText();

        if (codigoProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT producto, Precio, stock, almacen FROM almacen1 WHERE codigoproducto = ? AND stock >= 0";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, codigoProducto);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        jTextField4.setText(resultSet.getString("producto"));
                        jTextField6.setText(String.valueOf(resultSet.getDouble("precio")));
                        jTextField8.setText(String.valueOf(resultSet.getInt("stock")));
                        jTextField12.setText(String.valueOf(resultSet.getString("almacen")));
                    } else {
                        JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        kardex2 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        buscador = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("RETIRADA DE UN PRODUCTO PARA VENTAS");

        jLabel3.setText("CODIGO DEL PRODUCTO:");

        jLabel4.setText("PRODUCTO:");

        jLabel5.setText("PRECIO");

        jTextField4.setEditable(false);

        jTextField6.setEditable(false);

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel6.setText("NOMBRE DEL ENCARGADO:");

        jLabel7.setText("STOCK DE SALIDA: ");

        jLabel8.setText("NOMBRE DEL CLIENTE: ");

        jLabel9.setText("STOCK TOTAL:");

        jLabel10.setText("ENCARGADO DE LA RECEPCION:");

        jTextField8.setEditable(false);

        jLabel11.setText("INGRESE FECHA:");

        jLabel12.setText("CODIGO KARDEX: ");

        kardex2.setEditable(false);
        kardex2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kardex2ActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CODIGO", "ENCARGADO", "CODIGO PRODUCTO", "PRODUCTO", "PRECIO", "ALMACEN", "STOCK TOTAL", "STOCK DE SALIDA", "CLIENTE", "RECEPCIONISTA", "FECHA"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("ELIMINAR");

        jButton2.setText("EDITAR");

        jButton3.setText("REGISTRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        buscador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/vidrio-de-aumento (1).png"))); // NOI18N
        buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscadorActionPerformed(evt);
            }
        });

        jLabel13.setText("ALMACÉN:");

        jTextField12.setEditable(false);

        jButton4.setText("GUARDAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscador)))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.LEADING))
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 87, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(kardex2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(169, 169, 169))))))
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 542, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kardex2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
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

    private void kardex2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kardex2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kardex2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        registrarRetirada1(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscadorActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscador;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField kardex2;
    // End of variables declaration//GEN-END:variables
}
