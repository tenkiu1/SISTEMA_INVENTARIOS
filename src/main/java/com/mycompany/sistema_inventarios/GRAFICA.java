/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_inventarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jorge
 */
public class GRAFICA extends javax.swing.JPanel {

    /**
     * Creates new form GRAFICA
     */
    public GRAFICA() {
        initComponents();
        cargarDatosDesdeBaseDeDatos();
   }

     private void cargarDatosDesdeBaseDeDatos() {
        DefaultPieDataset dataset = createDatasetFromDatabase();
        JButton btnIngreso = new JButton("INGRESO");
        btnIngreso.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        ingreso ingresoPanel = new ingreso(); 
        JDialog dialog = new JDialog();
        dialog.setTitle("INGRESO");
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(ingresoPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
});
        JButton btnRetirada = new JButton("RETIRADA");
        btnRetirada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipo_re retri = new tipo_re(); 
                JDialog dialog = new JDialog();
                dialog.setTitle("RETIRADA");
                dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog.getContentPane().add(retri);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        JButton btnListaInventario = new JButton("LISTA DEL INVENTARIO GENERAL");
        btnListaInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listain lis = new listain();
                JDialog dialog = new JDialog();
                dialog.setTitle("LISTA DEL INVENTARIO");
                dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog.getContentPane().add(lis);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        JFreeChart chart = ChartFactory.createPieChart(
                "Inventario de los Almacenes", 
                dataset,  
                true,     
                true,
                false
        ); 

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));

       
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);

       
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.PAGE_AXIS));
        panelBotones.add(Box.createVerticalGlue());
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        btnIngreso.setFont(buttonFont);
        btnRetirada.setFont(buttonFont);
        btnListaInventario.setFont(buttonFont);
        Color buttonBackground = Color.WHITE;
        btnIngreso.setBackground(buttonBackground);
        btnRetirada.setBackground(buttonBackground);
        btnListaInventario.setBackground(buttonBackground);
        btnIngreso.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRetirada.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnListaInventario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotones.add(btnIngreso);
        panelBotones.add(Box.createVerticalStrut(10));  
        panelBotones.add(btnRetirada);
        panelBotones.add(Box.createVerticalStrut(10));  
        panelBotones.add(btnListaInventario);
        panelBotones.add(Box.createVerticalGlue());

        add(panelBotones, BorderLayout.LINE_END);
    }
    private DefaultPieDataset createDatasetFromDatabase() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventarios", "root", "")) {
        String query = "SELECT almacen, SUM(stock) AS stock_total FROM almacen1 GROUP BY almacen";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String almacen = resultSet.getString("almacen");
                int stockTotal = resultSet.getInt("stock_total");
                
                // Verificar si el stock es negativo
                if (stockTotal >= 0) {
                    dataset.setValue(almacen, stockTotal);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return dataset;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("INVENTARIO GENERAL");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GRAFICA inventarioPanel = new GRAFICA();
            frame.getContentPane().add(inventarioPanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
