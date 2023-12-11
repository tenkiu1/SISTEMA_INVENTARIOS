
package Modelo;

import Modelo.Packing;
import Modelo.PackingDAO;
import Tabla.TablaPack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import com.mycompany.sistema_inventarios.Packing_List;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;



public class controlador_packing implements ActionListener{
    PackingDAO dao=new PackingDAO();
    TablaPack tb =new TablaPack();
    Packing pa=new Packing();
    Packing_List plist=new Packing_List();
    DefaultTableModel modelo=new DefaultTableModel();
    String ruta_archivo = "";
    int id = -1;
    private byte[] pdfBytes;
    
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
  
    
    public controlador_packing(Packing_List plist){
        this.plist=plist;
        this.plist.btnarchivo.addActionListener(this);
        this.plist.btnagregar.addActionListener(this);
        this.plist.btnlistar.addActionListener(this);
        this.plist.btneliminar.addActionListener(this);
        this.plist.btneditar.addActionListener(this);
        this.plist.btnconfirmar.addActionListener(this);
        this.plist.btnpdf.addActionListener(this);
    }
    /*public void mouseclicked(MouseEvent evt){
    
    int column = plist.tabla.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / plist.tabla.getRowHeight();
        plist.txtproveedor.setEnabled(true);
        if (row < plist.tabla.getRowCount() && row >= 0 && column < plist.tabla.getColumnCount() && column >= 0) {
            id = (int) plist.tabla.getValueAt(row, 0);
            Object value = plist.tabla.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                if (boton.getText().equals("Vacio")) {
                    JOptionPane.showMessageDialog(null, "No hay archivo");
                } else {
                    PackingDAO pd = new PackingDAO();
                    pd.ejecutar_archivoPDF(id);
                    try {
                        Desktop.getDesktop().open(new File("new.pdf"));
                    } catch (Exception ex) {
                    }
                }

            } else {
                String n = "" + plist.tabla.getValueAt(row, 2);
                plist.txtproveedor.setText(n);
            }
        }
    }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==plist.btnarchivo){
        seleccionar();
        }
        if(e.getSource()==plist.btnagregar){
        agregar();
        limpiarTabla();
        listar(plist.tabla);
        plist.txtcodigo.setText("");
        plist.almacendatos.setSelectedItem("");
        plist.txtproveedor.setText("");  
        plist.btnarchivo.setText("Seleccionar Archivo");
        plist.datofecha.setDate(null);
        
        }
        if(e.getSource()==plist.btnlistar){
        limpiarTabla();
        listar(plist.tabla);
        }
        if(e.getSource()==plist.btneliminar){
        Eliminar();
        limpiarTabla();
        listar(plist.tabla);
        }
        if(e.getSource()==plist.btnpdf){
            int fila=plist.tabla.getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(plist, "Debe Seleccionar una Fila");
            }else{
              id = (int) plist.tabla.getValueAt(fila, 0);
            abrirPdf(id);
            }
        }
        if(e.getSource()==plist.btneditar){
        int fila=plist.tabla.getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(plist, "Debe Seleccionar una Fila");
            }
            else{
            Integer cod=Integer.parseInt((String)plist.tabla.getValueAt(fila,0).toString());
            String alm=(String)plist.tabla.getValueAt(fila, 1);
            String prov=(String)plist.tabla.getValueAt(fila, 2);
            Object valorCelda = plist.tabla.getValueAt(fila, 3);
            if (valorCelda != null) {
                if (valorCelda instanceof String) {
                    String pdf = (String) valorCelda;
                    plist.btnarchivo.setText(pdf);
                } else {
                    System.err.println("El valor en la celda no es una instancia de String: " + valorCelda);
                    // Manejar el caso en que el valor no es una instancia de String
                }
            } else {
                // Manejar el caso en que la celda es null
            }
            String fecha = (String) plist.tabla.getValueAt(fila, 4);
            if (fecha != null) {
            try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechad = dateFormat.parse(fecha);
            plist.datofecha.setDate(fechad);
            // Hacer algo con 'fechad'
            } catch (ParseException s) {
            // Manejar la excepción
            s.printStackTrace(); // Otra opción: System.err.println("Error al parsear la fecha: " + e.getMessage());
            }
            } else {
            // Manejar el caso en que la fecha es null
            System.err.println("La fecha es null");
            }
            plist.txtcodigo.setText(""+cod);
            plist.almacendatos.setSelectedItem(alm);
            plist.txtproveedor.setText(prov);
            plist.txtcodigo.setEnabled(false);
            
            
           
            }
        }
        if(e.getSource()==plist.btnconfirmar){
        Actualizar();
        limpiarTabla();
        listar(plist.tabla);
        plist.txtcodigo.setText("");
        plist.almacendatos.setSelectedItem("");
        plist.txtproveedor.setText("");  
        plist.btnarchivo.setText("Seleccionar Archivo");
        plist.datofecha.setDate(null);
        plist.txtcodigo.setEnabled(true);
       
        }
    }
    public void iniciar(){
     plist.setTitle("Packing List");
     plist.setLocationRelativeTo(null);
     
     }
    
    public void seleccionar() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(plist);
        if (se == 0) {
            plist.btnarchivo.setText("" + j.getSelectedFile().getName());
            ruta_archivo = j.getSelectedFile().getAbsolutePath();
          
        } else {
        }
    }
    
    
         
         
    
    
    public void limpiarTabla(){
        for(int i=0; i<plist.tabla.getRowCount(); i++){
            modelo.removeRow(i);
            i=i-1;
        }
    }
    void Eliminar(){
    int fila=plist.tabla.getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(plist, "Debe Seleccionar los Datos a Eliminar");
            }else{
            int cod=Integer.parseInt((String)plist.tabla.getValueAt(fila, 0).toString());
            dao.Eliminar(cod);
                JOptionPane.showMessageDialog(plist, "Dato Eliminado");
            }
    }
    
    public void Actualizar(){
        File ruta = new File(ruta_archivo);
        Integer cod=Integer.parseInt(plist.txtcodigo.getText());
        String alm=(String)plist.almacendatos.getSelectedItem();
        String prov=plist.txtproveedor.getText();
        Date fe=plist.datofecha.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateF = dateFormat.format(fe);
        try {
            byte[] pdf = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(pdf);
            pa.setCodigo(cod);
            pa.setAlmacen(alm);
            pa.setProveedor(prov);
            pa.setArchivo(pdf);
            pa.setFecha(dateF);
            int r=dao.Actualizar(pa);
        if(r==1){
            JOptionPane.showMessageDialog(plist, "Packing List actualizado con exito");
        }else{
            JOptionPane.showMessageDialog(plist, "Error al actualizar Packing List");
        }
        } catch (IOException ex) {
            pa.setArchivo(null);
            System.out.println("Error al agregar archivo pdf "+ex.getMessage());
        }
        
        
    }
    private void abrirPdf(int id) {
    String sql = "SELECT archivo FROM packing_list WHERE cod_packing = ?";
    try {
        con=conectar.getConnection();
        ps=con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            pdfBytes = resultSet.getBytes("archivo");

            try (InputStream bos = new ByteArrayInputStream(pdfBytes)) {
                int tamanoInput = bos.available();
                byte[] datosPDF = new byte[tamanoInput];
                bos.read(datosPDF, 0, tamanoInput);

                File tempFile = File.createTempFile("tempPdf", ".pdf");
                tempFile.deleteOnExit();

                try (OutputStream out = new FileOutputStream(tempFile)) {
                    out.write(datosPDF);
                    
                }

                Desktop.getDesktop().open(tempFile);
            }
           
        } else {
            JOptionPane.showMessageDialog(plist, "No se encontró el archivo PDF asociado a este registro.");
        }
    } catch (IOException | SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(plist, "Error al abrir el archivo PDF.");
    }
}
    
    public void agregar(){
        File ruta = new File(ruta_archivo);
        Integer cod=Integer.parseInt(plist.txtcodigo.getText());
        String alm=(String)plist.almacendatos.getSelectedItem();
        String prov=plist.txtproveedor.getText();
        Date fe=plist.datofecha.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateF = dateFormat.format(fe);
        pa.setCodigo(cod);
        pa.setAlmacen(alm);
        pa.setProveedor(prov);
        try {
            byte[] pdf = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(pdf);
            pa.setArchivo(pdf);
        } catch (IOException ex) {
            pa.setArchivo(null);
            //System.out.println("Error al agregar archivo pdf "+ex.getMessage());
        }
        pa.setFecha(dateF);
        int r=dao.Agregar(pa);
        if(r==1){
            JOptionPane.showMessageDialog(plist, "Packing List agregado con exito");
        }else{
            JOptionPane.showMessageDialog(plist, "Error al agregar Packing List");
        }
    }
    
    public void listar(JTable tabla){
        modelo=(DefaultTableModel)tabla.getModel();
        List<Packing>lista=dao.listar();
        Object[]object=new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getCodigo();
            object[1]=lista.get(i).getAlmacen();
            object[2]=lista.get(i).getProveedor();
            object[3]=lista.get(i).getArchivo();
            object[4]=lista.get(i).getFecha();
            modelo.addRow(object);
        }
        plist.tabla.setModel(modelo);
    }
    
    
    
}
