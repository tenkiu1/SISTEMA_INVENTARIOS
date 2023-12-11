
package Modelo;


public class Packing {
    
    int codigo;
    String almacen;
    String proveedor;
    byte[] archivo;
    String fecha;

    public Packing() {
    }

    public Packing(int codigo, String almacen, String proveedor, byte[] archivo, String fecha) {
        this.codigo = codigo;
        this.almacen = almacen;
        this.proveedor = proveedor;
        this.archivo = archivo;
        this.fecha = fecha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
