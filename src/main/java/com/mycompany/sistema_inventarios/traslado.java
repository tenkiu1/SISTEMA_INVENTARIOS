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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class traslado extends javax.swing.JPanel {

    private int contadorMovimiento;
    private boolean modoEdicion = false;
    private String codigoMovimientoActual;

    public traslado() {
        initComponents();
        contadorMovimiento = obtenerUltimoCodigoMovimiento();
        actualizarKardex();

        // Agregar ActionListener al botón "buscador"
        buscador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        ELIMINAR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatoSeleccionado();
            }
        });
        EDITAR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDato();
            }
        });
        GUARDAR.addActionListener(new ActionListener() {
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

    private int obtenerUltimoCodigoMovimiento() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT MAX(cod_mov) FROM traslado_almacen";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String ultimoCodigo = resultSet.getString(1);
                    if (ultimoCodigo != null && ultimoCodigo.matches("TA\\d{4}")) {
                        System.out.println("Último código obtenido: " + ultimoCodigo);
                        return Integer.parseInt(ultimoCodigo.substring(2));
                    } else {
                        System.out.println("El último código no coincide con el patrón esperado.");
                    }
                } else {
                    System.out.println("No se obtuvieron resultados de la consulta.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al obtener el último código de movimiento.");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            System.out.println("Error al convertir el último código a entero.");
        }
        return 0;
    }

    private String generarProximoCodigoMovimiento() {
        contadorMovimiento++;
        return "TA" + String.format("%04d", contadorMovimiento);
    }

    private void actualizarKardex() {
        String codigoMovimiento = generarProximoCodigoMovimiento();
        String textoKardex = "TA" + String.format("%04d", contadorMovimiento);
        kardex.setText(textoKardex);
        System.out.println("Kardex actualizado a: " + textoKardex);
    }

    private void guardarCambios() {
        // Obtener los nuevos valores
        String nombreTrabajador = jTextField7.getText();
        String codProducto = jTextField5.getText();
        String producto = jTextField4.getText();
        double precio = Double.parseDouble(jTextField6.getText());
        String almacen = jTextField12.getText();
        int stockTotal = Integer.parseInt(jTextField8.getText());
        String almacenDestino = jComboBox1.getSelectedItem().toString();
        String sector = jComboBox3.getSelectedItem().toString();
        String motivo = jComboBox2.getSelectedItem().toString();
        Date fechaTraslado = jDateChooser1.getDate();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String queryUpdate = "UPDATE traslado_almacen SET nombre_trabajador=?, cod_producto=?, nombre_producto=?, precio=?, almacen=?, stock_total=?, almacen_destino=?, sector=?, motivo=?, fecha_traslado=? WHERE cod_mov=?";
            try (PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate)) {
                statementUpdate.setString(1, nombreTrabajador);
                statementUpdate.setString(2, codProducto);
                statementUpdate.setString(3, producto);
                statementUpdate.setDouble(4, precio);
                statementUpdate.setString(5, almacen);
                statementUpdate.setInt(6, stockTotal);
                statementUpdate.setString(7, almacenDestino);
                statementUpdate.setString(8, sector);
                statementUpdate.setString(9, motivo);
                statementUpdate.setDate(10, new java.sql.Date(fechaTraslado.getTime()));
                statementUpdate.setString(11, codigoMovimientoActual);

                statementUpdate.executeUpdate();
            }

            // Limpiar campos después de guardar cambios
            limpiarCampos();
            modoEdicion = false;

            // Actualizar la tabla
            actualizarTabla(codigoMovimientoActual);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la traslado en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarRetirada1() {
        String codigoMovimiento = generarProximoCodigoMovimiento();
        String nombreTrabajador = jTextField7.getText();
        String codigoProducto = jTextField5.getText();
        String producto = jTextField4.getText();
        String precioText = jTextField6.getText();
        String almacen = jTextField12.getText();
        String stockTotalText = jTextField8.getText();
        String almacen_destino = jComboBox1.getSelectedItem().toString();
        String sector = jComboBox3.getSelectedItem().toString();
        String motivo = jComboBox2.getSelectedItem().toString();
        Date fechatraslado = jDateChooser1.getDate();

        // Verificar si algún campo está vacío
        if (nombreTrabajador.isEmpty() || codigoProducto.isEmpty() || producto.isEmpty() || sector.isEmpty()
                || precioText.isEmpty() || almacen.isEmpty() || stockTotalText.isEmpty()
                || almacen_destino.isEmpty() || motivo.isEmpty() || fechatraslado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return; // Detener la ejecución si algún campo está vacío
        }

        // Convertir los valores a los tipos adecuados
        double precio = Double.parseDouble(precioText);
        int stockTotal = Integer.parseInt(stockTotalText);

        // Realizar la conexión a la base de datos y la inserción
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            // Iniciar una transacción para garantizar la consistencia de los datos
            connection.setAutoCommit(false);

            try {
                // Insertar en la tabla traslado_almacen
                String queryInsert = "INSERT INTO traslado_almacen (cod_mov, nombre_trabajador, cod_producto, nombre_producto, precio, almacen, stock_total, almacen_destino, sector, motivo, fecha_traslado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
                    statementInsert.setString(1, codigoMovimiento);
                    statementInsert.setString(2, nombreTrabajador);
                    statementInsert.setString(3, codigoProducto);
                    statementInsert.setString(4, producto);
                    statementInsert.setDouble(5, precio);
                    statementInsert.setString(6, almacen);
                    statementInsert.setInt(7, stockTotal);
                    statementInsert.setString(8, almacen_destino);
                    statementInsert.setString(9, sector);
                    statementInsert.setString(10, motivo);
                    statementInsert.setDate(11, new java.sql.Date(fechatraslado.getTime()));

                    int filasAfectadas = statementInsert.executeUpdate();
                    if (filasAfectadas > 0) {
                        // Éxito en la inserción, ahora realizar las actualizaciones adicionales

                        // Actualizar el almacen_destino y sector en la tabla almacen1
                        String queryUpdate = "UPDATE almacen1 SET almacen = ?, sector = ? WHERE codigoproducto = ?";
                        try (PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate)) {
                            statementUpdate.setString(1, almacen_destino);
                            statementUpdate.setString(2, sector);
                            statementUpdate.setString(3, codigoProducto);
                            statementUpdate.executeUpdate();
                        }

                        connection.commit(); // Confirmar la transacción si todo fue exitoso

                        JOptionPane.showMessageDialog(this, "Retirada registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        actualizarTabla(codigoMovimiento);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar la retirada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                connection.rollback(); // Deshacer la transacción en caso de error
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                connection.setAutoCommit(true); // Restaurar el modo de auto-commit
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        actualizarKardex();
        contadorMovimiento++;

    }

    private void limpiarCampos() {

        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField12.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jDateChooser1.setDate(null);
    }

    private void agregarDato() {
        String nombreTrabajador = jTextField7.getText();
        String codigoProducto = jTextField5.getText();
        String producto = jTextField4.getText();
        double precio = 0.0;
        String almacen = jTextField12.getText();
        String stockTotalText = jTextField8.getText();
        String almacenDestino = jComboBox1.getSelectedItem().toString();
        String sector = jComboBox3.getSelectedItem().toString();
        String motivo = jComboBox2.getSelectedItem().toString();
        Date fechaTraslado = jDateChooser1.getDate();

        // Verificar si algún campo está vacío
        if (nombreTrabajador.isEmpty() || codigoProducto.isEmpty() || producto.isEmpty() || sector.isEmpty()
                || almacen.isEmpty() || stockTotalText.isEmpty()
                || almacenDestino.isEmpty() || motivo.isEmpty() || fechaTraslado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return; // Detener la ejecución si algún campo está vacío
        }

        // Convertir los valores a los tipos adecuados
        String precioText = jTextField4.getText();
        if (!precioText.isEmpty()) {
            try {
                precio = Double.parseDouble(precioText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int stockTotal = Integer.parseInt(stockTotalText);

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
                // Insertar en la tabla "traslado_almacen"
                String trasladoQuery = "INSERT INTO traslado_almacen (cod_mov, nombre_trabajador, cod_producto, nombre_producto, precio, almacen, stock_total, almacen_destino, sector, motivo, fecha_traslado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement trasladoStatement = connection.prepareStatement(trasladoQuery)) {
                    trasladoStatement.setString(1, generarProximoCodigoMovimiento());
                    trasladoStatement.setString(2, nombreTrabajador);
                    trasladoStatement.setString(3, codigoProducto);
                    trasladoStatement.setString(4, producto);
                    trasladoStatement.setDouble(5, precio);
                    trasladoStatement.setString(6, almacen);
                    trasladoStatement.setInt(7, stockTotal);
                    trasladoStatement.setString(8, almacenDestino);
                    trasladoStatement.setString(9, sector);
                    trasladoStatement.setString(10, motivo);
                    trasladoStatement.setDate(11, new java.sql.Date(fechaTraslado.getTime()));

                    trasladoStatement.executeUpdate();
                }

                // Limpiar campos después de agregar
                limpiarCampos();

                // Actualizar la tabla
                actualizarKardex();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al registrar el traslado en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

   private void editarDato() {
    int filaSeleccionada = kardex1.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una fila para editar.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        String codMovimiento = (String) kardex1.getValueAt(filaSeleccionada, 0);
        String nombreTrabajador = (String) kardex1.getValueAt(filaSeleccionada, 1);
        String codProducto = (String) kardex1.getValueAt(filaSeleccionada, 2);
        String producto = (String) kardex1.getValueAt(filaSeleccionada, 3);
        double precio = (double) kardex1.getValueAt(filaSeleccionada, 4);
        String almacen = (String) kardex1.getValueAt(filaSeleccionada, 5);
        int stockTotal = (int) kardex1.getValueAt(filaSeleccionada, 6);
        String almacenDestino = (String) kardex1.getValueAt(filaSeleccionada, 7);
        String sector = (String) kardex1.getValueAt(filaSeleccionada, 8);
        String motivo = (String) kardex1.getValueAt(filaSeleccionada, 9);
        
        // Obtener la fecha como cadena de texto
        String fechaTrasladoStr = (String) kardex1.getValueAt(filaSeleccionada, 10);
        Date fechaTraslado = null;

        // Verificar si la cadena no es nula y no está vacía antes de intentar la conversión
        if (fechaTrasladoStr != null && !fechaTrasladoStr.isEmpty()) {
            try {
                // Utilizar un formato de fecha apropiado para tu aplicación
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                fechaTraslado = dateFormat.parse(fechaTrasladoStr);
            } catch (ParseException e) {
                e.printStackTrace();
                // Manejar el error de conversión de fecha según sea necesario
            }
        }

        // Llenar los campos con los datos de la fila seleccionada
        jTextField7.setText(nombreTrabajador);
        jTextField5.setText(codProducto);
        jTextField4.setText(producto);
        jTextField6.setText(String.valueOf(precio));
        jTextField12.setText(almacen);
        jTextField8.setText(String.valueOf(stockTotal));
        jComboBox1.setSelectedItem(almacenDestino);
        jComboBox3.setSelectedItem(sector);
        jComboBox2.setSelectedItem(motivo);
        jDateChooser1.setDate(fechaTraslado);

        // Activar el modo de edición
        modoEdicion = true;

        // Guardar el código de movimiento actual para referencia en la edición
        codigoMovimientoActual = codMovimiento;
    }
}

    private void eliminarDatoSeleccionado() {
        int filaSeleccionada = kardex1.getSelectedRow();
        if (filaSeleccionada != -1) {
            String codMovimiento = (String) kardex1.getValueAt(filaSeleccionada, 0);

            // Eliminar la fila en la base de datos "traslado_almacen"
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
                String queryDelete = "DELETE FROM traslado_almacen WHERE cod_mov=?";
                try (PreparedStatement statementDelete = connection.prepareStatement(queryDelete)) {
                    statementDelete.setString(1, codMovimiento);

                    statementDelete.executeUpdate();
                }

                actualizarTabla(codMovimiento);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el registro de la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
        }
    }

    private void actualizarTabla(String codMovimiento) {

        DefaultTableModel tableModel = (DefaultTableModel) kardex1.getModel();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

    
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
            String query = "SELECT cod_mov,nombre_trabajador,cod_producto,nombre_producto,precio,almacen,stock_total,almacen_destino,sector,motivo,fecha_traslado  FROM traslado_almacen WHERE cod_mov = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, codMovimiento);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Object[] rowData = {
                            resultSet.getString("cod_mov"),
                            resultSet.getString("nombre_trabajador"),
                            resultSet.getString("cod_producto"),
                            resultSet.getString("nombre_producto"),
                            resultSet.getDouble("precio"),
                            resultSet.getString("almacen"),
                            resultSet.getInt("stock_total"),
                            resultSet.getString("almacen_destino"), 
                            resultSet.getString("sector"),
                            resultSet.getString("motivo"),
                            resultSet.getString("fecha_traslado"),};
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
            String query = "SELECT producto, Precio,SUM(stock) as stock,almacen FROM almacen1 WHERE codigoproducto = ?";
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        kardex = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        kardex1 = new javax.swing.JTable();
        ELIMINAR = new javax.swing.JButton();
        EDITAR = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        buscador = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        GUARDAR = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("TRASLADO DE UN PRODUCTO A OTRO ALMACEN");

        jLabel3.setText("CODIGO DEL PRODUCTO:");

        jLabel4.setText("PRODUCTO:");

        jLabel5.setText("PRECIO");

        jTextField4.setEditable(false);

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel6.setText("NOMBRE DEL ENCARGADO:");

        jLabel7.setText("SELECCIONE ALMACEN DESTINO:");

        jLabel8.setText("MOTIVO DE TRASLADO");

        jLabel9.setText("STOCK TOTAL:");

        jLabel10.setText("SELECCIONE EL SECTOR DEL ALMACEN: ");

        jTextField8.setEditable(false);

        jLabel11.setText("INGRESE FECHA:");

        jLabel12.setText("CODIGO KARDEX: ");

        kardex.setEditable(false);
        kardex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kardexActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        kardex1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CODIGO", "ENCARGADO", "CODIGO PRODUCTO", "PRODUCTO", "PRECIO", "ALMACEN", "STOCK TOTAL", "ALMACEN DESTINO", "SECTOR DEL ALMACEN ", "MOTIVO", "FECHA"
            }
        ));
        jScrollPane1.setViewportView(kardex1);

        ELIMINAR.setText("ELIMINAR");
        ELIMINAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELIMINARActionPerformed(evt);
            }
        });

        EDITAR.setText("EDITAR");

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

        jLabel13.setText("ALMACÉN ORIGEN :");

        jTextField12.setEditable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALMACEN A", "ALMACEN B", "ALMACEN C" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. MEJORA DE GESTION DE ALMACEN", "2. ALMACEN EN FUMIGACION", "3. ALMACEN NO FUNCIONANDO", "4. PRODUCTO SOLICITADO EN OTRO ALMACEN" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ZONA DE ALTA ROTACION", "ZONA DE BAJA ROTACION ", "ZONA DE MEDIA ROTACION" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        GUARDAR.setText("GUARDAR");

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(kardex, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 23, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 75, Short.MAX_VALUE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 469, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(GUARDAR, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EDITAR, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133)
                .addComponent(ELIMINAR, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
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
                            .addComponent(kardex, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EDITAR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GUARDAR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ELIMINAR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void kardexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kardexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kardexActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        registrarRetirada1();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscadorActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void ELIMINARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ELIMINARActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ELIMINARActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EDITAR;
    private javax.swing.JButton ELIMINAR;
    private javax.swing.JButton GUARDAR;
    private javax.swing.JButton buscador;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
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
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField kardex;
    private javax.swing.JTable kardex1;
    // End of variables declaration//GEN-END:variables
}
