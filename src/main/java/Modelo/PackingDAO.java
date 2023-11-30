
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    public int Agregar(Packing p){
    String sql="insert into packing_list(cod_packing,almacen,proveedor,archivo,fecha)values(?,?,?,?,?)";
    try{
        con=conectar.getConnection();
        ps=con.prepareStatement(sql);
        ps.setInt(1, p.getCodigo());
        ps.setString(2, p.getAlmacen());
        ps.setString(3, p.getProveedor());
        ps.setBytes(4, p.getArchivo());
        ps.setString(5, p.getFecha());
        
        ps.executeUpdate();
    }
    catch(Exception e){}
    return 1;
    }
}
