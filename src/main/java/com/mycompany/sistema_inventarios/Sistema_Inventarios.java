/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_inventarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jorge
 */
public class Sistema_Inventarios {

  public static void main(String[] args) {
        // Información de conexión
        String url = "jdbc:mysql://localhost:3306/inventarios";
        String usuario = "root";
        String contraseña = "";

        // Intentar establecer la conexión
        try {
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            // La conexión ha sido establecida con éxito
            System.out.println("Conexión exitosa a la base de datos");
            
            conexion.close();
        } catch (SQLException e) {
            // Manejar errores de conexión
            e.printStackTrace();
        }
    }
  }