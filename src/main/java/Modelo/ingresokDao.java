/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class ingresokDao {
    
    private Conexion conectar = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs; 
    
    public List<ingresok> obtenerKardexi() {
        List<ingresok> datos = new ArrayList<>();
        String sql = "SELECT * FROM ingreso_producto ";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ingresok u = new ingresok();
                u.setCodigo(rs.getInt("cod_mov"));
                u.setCodigoproducto(rs.getString("codigoProducto"));
                u.setProducto(rs.getString("producto"));
                u.setPrecio(rs.getInt("precio"));
                u.setStock(rs.getInt("stock"));
                u.setAlmacen(rs.getString("almacen"));
                u.setSector(rs.getString("sector_almacen"));
                u.setFecha(rs.getDate("fecha_ingreso"));
                datos.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones. Debes mejorarlo según tus necesidades.
        } finally {
            cerrarConexion(); // Método para cerrar la conexión. Debes implementarlo según tu lógica.
        }

        return datos;
    }
private void cerrarConexion() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Puedes mejorar el manejo de excepciones según tus necesidades.
        }
    }
    
}
