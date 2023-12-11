package com.mycompany.sistema_inventarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class nuevo_usu extends javax.swing.JPanel {

    private int filaSeleccionada = -1;

    public nuevo_usu() {
        eliminarUsuario();
        initComponents();
        mostrarDatosEnTabla(); // Mostrar datos al cargar el panel

        // Agregar ListSelectionListener a la tabla
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Obtener el índice de la fila seleccionada
                filaSeleccionada = tabla.getSelectedRow();

                // Rellenar los campos de texto con los datos de la fila seleccionada
                if (filaSeleccionada != -1) {
                    txtcodigo.setText(tabla.getValueAt(filaSeleccionada, 0).toString());
                    txtnombre.setText(tabla.getValueAt(filaSeleccionada, 1).toString());
                    txtapellidos.setText(tabla.getValueAt(filaSeleccionada, 2).toString());
                    txttelefono.setText(tabla.getValueAt(filaSeleccionada, 3).toString());
                    txtemail.setText(tabla.getValueAt(filaSeleccionada, 4).toString());
                    txtusuario.setText(tabla.getValueAt(filaSeleccionada, 5).toString());
                    txtcontraseña.setText(tabla.getValueAt(filaSeleccionada, 6).toString());
                }
            }
        });

        btnagregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtcodigo.getText();
                String nombre = txtnombre.getText();
                String apellidos = txtapellidos.getText();
                String telefono = txttelefono.getText();
                String email = txtemail.getText();
                String usuario = txtusuario.getText();
                String contraseña = txtcontraseña.getText();

                agregarUsuario(codigo, nombre, apellidos, telefono, usuario, contraseña, email);
                limpiarCampos();
                mostrarDatosEnTabla(); // Actualizar la tabla después de agregar un usuario
            }
        });
    }

    private void agregarUsuario(String codigo, String nombre, String apellidos, String telefono, String email, String usuario, String contraseña) {
        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "");

            String sql = "INSERT INTO usuario (cod_usuario, nombre, apellido, num_telefono, email, nom_usuario, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Crear la declaración preparada
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                // Establecer los parámetros
                pstmt.setString(1, codigo);
                pstmt.setString(2, nombre);
                pstmt.setString(3, apellidos);
                pstmt.setString(4, telefono);
                pstmt.setString(5, email);
                pstmt.setString(6, usuario);
                pstmt.setString(7, contraseña);

                // Ejecutar la actualización
                pstmt.executeUpdate();

            }

            // Cerrar la conexión
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }

    private void limpiarCampos() {
        // Limpiar los JTextField después de agregar a la base de datos
        txtcodigo.setText("");
        txtnombre.setText("");
        txtapellidos.setText("");
        txttelefono.setText("");
        txtusuario.setText("");
        txtcontraseña.setText("");
        txtemail.setText("");
    }

    private void mostrarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "");
            String sql = "SELECT * FROM usuario";

            try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    // Agregar una fila a la tabla con los datos del resultado
                    modelo.addRow(new Object[]{
                        rs.getString("cod_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("num_telefono"),
                        rs.getString("email"),
                        rs.getString("nom_usuario"),
                        rs.getString("contraseña")
                    });
                }
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }

    private void modificarUsuario(String codigo, String nombre, String apellidos, String telefono, String email, String usuario, String contraseña) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "");

            String sql = "UPDATE usuario SET nombre=?, apellido=?, num_telefono=?, email=?, nom_usuario=?, contraseña=? WHERE cod_usuario=?";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, apellidos);
                pstmt.setString(3, telefono);
                pstmt.setString(4, email);
                pstmt.setString(5, usuario);
                pstmt.setString(6, contraseña);
                pstmt.setString(7, codigo);

                pstmt.executeUpdate();
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }

    private void eliminarUsuario() {
        try {
            if (filaSeleccionada != -1) {
                String codigo = tabla.getValueAt(filaSeleccionada, 0).toString();

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "");

                String sql = "DELETE FROM usuario WHERE cod_usuario=?";

                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setString(1, codigo);
                    pstmt.executeUpdate();
                }

                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtusuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtapellidos = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        btnagregar = new javax.swing.JButton();
        txtcontraseña = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        txtcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Apellidos:");

        btnagregar.setText("Agregar");

        jLabel8.setText("Contraseña:");

        jLabel5.setText("Numero de Telefono:");

        jLabel6.setText("Usuario:");

        jLabel3.setText("Nombre:");

        jLabel2.setText("Codigo de Usuario:");

        jLabel1.setText("Email:");

        jButton1.setText("Modificar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Apellido", "telefono", "Email", "Usuario", "Contraseña"
            }
        ));
        jScrollPane2.setViewportView(tabla);

        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtemail)
                            .addComponent(jLabel1)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton1)
                                .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2)))
                            .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnagregar)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (filaSeleccionada != -1) {
            // Obtener los nuevos valores de los campos de texto
            String codigo = txtcodigo.getText();
            String nombre = txtnombre.getText();
            String apellidos = txtapellidos.getText();
            String telefono = txttelefono.getText();
            String email = txtemail.getText();
            String usuario = txtusuario.getText();
            String contraseña = txtcontraseña.getText();

            // Llamada a la función para modificar el usuario
            modificarUsuario(codigo, nombre, apellidos, telefono, email, usuario, contraseña);

            // Limpiar campos y actualizar la tabla después de modificar
            limpiarCampos();
            mostrarDatosEnTabla();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (filaSeleccionada != -1) {
            eliminarUsuario();
            limpiarCampos();
            mostrarDatosEnTabla(); // Actualizar la tabla después de eliminar un usuario
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      mostrarDatosEnTabla();
      limpiarCampos();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnagregar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable tabla;
    public javax.swing.JTextField txtapellidos;
    public javax.swing.JTextField txtcodigo;
    public javax.swing.JTextField txtcontraseña;
    public javax.swing.JTextField txtemail;
    public javax.swing.JTextField txtnombre;
    public javax.swing.JTextField txttelefono;
    public javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
