
package Modelo;

import java.util.Date;


public class Reporte {
    
    String cod_reporte;
    String nom_trabajador;
    String almacen;
    String cod_producto;
    String producto;
    int cantidad;
    String descripcion;
    Date fecha;

    public Reporte() {
    }

    public Reporte(String cod_reporte, String nom_trabajador, String almacen, String cod_producto, String producto, int cantidad, String descripcion, Date fecha) {
        this.cod_reporte = cod_reporte;
        this.nom_trabajador = nom_trabajador;
        this.almacen = almacen;
        this.cod_producto = cod_producto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getCod_reporte() {
        return cod_reporte;
    }

    public void setCod_reporte(String cod_reporte) {
        this.cod_reporte = cod_reporte;
    }

    public String getNom_trabajador() {
        return nom_trabajador;
    }

    public void setNom_trabajador(String nom_trabajador) {
        this.nom_trabajador = nom_trabajador;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

   

    
    
    
}
