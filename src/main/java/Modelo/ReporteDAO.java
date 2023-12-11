
package Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ReporteDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public int Agregar(Reporte r){
    String sql="insert into reporte_irregularidad(cod_reporte,nombre_trabajador,almacen,cod_producto,producto,cantidad,descripcion,fecha_reporte)values(?,?,?,?,?,?,?,?)";
    try{
        con=conectar.getConnection();
        ps=con.prepareStatement(sql);
        ps.setString(1, r.getCod_reporte());
        ps.setString(2, r.getNom_trabajador());
        ps.setString(3, r.getAlmacen());
        ps.setString(4, r.getCod_producto());
        ps.setString(5, r.getProducto());
        ps.setInt(6, r.getCantidad());
        ps.setString(7, r.getDescripcion());
        ps.setDate(8, new java.sql.Date(r.getFecha().getTime()));
        ps.executeUpdate();
    }
    catch(Exception e){}
    return 1;
    }
    
    
    
}
