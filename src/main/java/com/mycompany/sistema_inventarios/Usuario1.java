/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_inventarios;

/**
 *
 * @author jorge
 */
public class Usuario1 extends javax.swing.JPanel {

    public Usuario1() {
        initComponents();

        nuevo_usu nu = new nuevo_usu();
        nu.setSize(720, 470);
        nu.setLocation(0, 0);
        
        grafico4.removeAll();
        grafico4.add(nu);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        grafico4 = new javax.swing.JPanel();
        btnlista1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grafico4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout grafico4Layout = new javax.swing.GroupLayout(grafico4);
        grafico4.setLayout(grafico4Layout);
        grafico4Layout.setHorizontalGroup(
            grafico4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 718, Short.MAX_VALUE)
        );
        grafico4Layout.setVerticalGroup(
            grafico4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );

        jPanel1.add(grafico4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 720, 390));

        btnlista1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnlista1.setText("LISTA DE USUARIOS");
        btnlista1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlista1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnlista1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 190, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("NUEVO USUARIO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 180, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnlista1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlista1ActionPerformed
        Usuarios1 us1 = new Usuarios1();
        us1.setSize(720, 470);
        us1.setLocation(0, 0);

        grafico4.removeAll();
        grafico4.add(us1);
        grafico4.revalidate();
        grafico4.repaint();
    }//GEN-LAST:event_btnlista1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        nuevo_usu nu = new nuevo_usu();
        nu.setSize(720, 470);
        nu.setLocation(0, 0);

        grafico4.removeAll();
        grafico4.add(nu);
        grafico4.revalidate();
        grafico4.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed
    public static void main(String args[]) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnlista1;
    private javax.swing.JPanel grafico4;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
