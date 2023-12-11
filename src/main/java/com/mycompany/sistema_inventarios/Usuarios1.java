/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_inventarios;

import Modelo.Usuario;
import Modelo.UsuarioDAO;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class Usuarios1 extends javax.swing.JPanel {

    private DefaultTableModel modeloTabla;

    public Usuarios1() {
        initComponents();
        modeloTabla = (DefaultTableModel) tabla.getModel();
        cargarUsuarios();
    }

    public void setModeloTabla(DefaultTableModel modelo) {
        this.modeloTabla = modelo;
    }

    public void cargarUsuarios() {
        // Limpiar la tabla antes de cargar nuevos datos
        modeloTabla.setRowCount(0);

        // Obtener la lista de usuarios desde el DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();

        // Llenar la tabla con los datos de los usuarios
        for (Usuario usuario : usuarios) {
            Object[] fila = {
                usuario.getCodigo(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getTelefono(),
                usuario.getEmail(),
                usuario.getNombreusuario(),
                usuario.getContraseña()
            };
            modeloTabla.addRow(fila);  // Aquí usamos modeloTabla en lugar de modelo
        }
    }

    private void eliminarUsuario(String codigoUsuario) {
        try {
            // Configurar la conexión a la base de datos (ajusta la URL, usuario y contraseña)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "");

            // Consulta SQL para la eliminación de datos
            String sql = "DELETE FROM usuario WHERE cod_usuario = ?";

            // Crear la declaración preparada
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                // Establecer el parámetro
                pstmt.setString(1, codigoUsuario);

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

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Apellido", "telefono", "Email", "Usuario", "Contraseña"
            }
        ));
        jScrollPane2.setViewportView(tabla);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 700, 380));

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
