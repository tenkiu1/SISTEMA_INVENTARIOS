
package Modelo;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PackingDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
    List<Packing>datos=new ArrayList<>();
    String sql="select * from packing_list";
    try{
    con=conectar.getConnection();
    ps=con.prepareStatement(sql);
    rs=ps.executeQuery();
    while(rs.next()){
        Packing p=new Packing();
        p.setCodigo(rs.getInt(1));
        p.setAlmacen(rs.getString(2));
        p.setProveedor(rs.getString(3));
        p.setArchivo(rs.getBytes(4));
        p.setFecha(rs.getString(5));
        datos.add(p);
    }
    }
    catch(Exception e){}
    return datos;
    }
    
    public int Agregar(Packing pa) {
    String sql = "insert into packing_list(cod_packing, proveedor, almacen, archivo, fecha) values(?,?,?,?,?)";

    try {
        con = conectar.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, pa.getCodigo());
        ps.setString(2, pa.getProveedor());
        ps.setString(3, pa.getAlmacen());
        ps.setBytes(4, pa.getArchivo());
        ps.setString(5, pa.getFecha());

        // Ejecutar la inserción
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            // La inserción fue exitosa
            return 1;
        } else {
            // La inserción no tuvo éxito
            return 0;
        }
    } catch (SQLException e) {
        // Manejar la excepción
        e.printStackTrace();
        return 0;
    } finally {
        // Cerrar recursos
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public int Actualizar(Packing pa){
        int r=0;
        String sql="update packing_list set proveedor=?,almacen=?,archivo=?,fecha=? where cod_packing=?";
        try{
        con=conectar.getConnection();
        ps=con.prepareStatement(sql);
        ps.setString(1, pa.getAlmacen());
        ps.setString(2, pa.getProveedor());
        ps.setBytes(3, pa.getArchivo());
        ps.setString(4, pa.getFecha());
        ps.setInt(5, pa.getCodigo());
        r=ps.executeUpdate();
        if(r==1){
            return 1;
        }else{
        return 0;
        }
        }
        catch(Exception e)
        {}
        return r;
    }
    
    public void Eliminar(int cod){
        String sql="delete from packing_list where cod_packing="+cod;
        try{
        con=conectar.getConnection();
        ps=con.prepareStatement(sql);
        ps.executeUpdate();
    }
    catch(Exception e){
    }
    }
    
    
}
