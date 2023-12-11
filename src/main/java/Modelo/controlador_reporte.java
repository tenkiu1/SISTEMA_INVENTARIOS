
package Modelo;

import com.mycompany.sistema_inventarios.Reporte_Irregularidad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;


public class controlador_reporte implements ActionListener{
    Reporte r= new Reporte();
    ReporteDAO dao= new ReporteDAO();
    Reporte_Irregularidad ri= new Reporte_Irregularidad();
    
    public controlador_reporte(Reporte_Irregularidad ri){
        this.ri=ri;
        this.ri.btnsubir.addActionListener(this);
    }
    
    
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==ri.btnsubir){
        agregar();
        ri.txtcodigoreporte.setText("");
        ri.txttrabajador.setText("");
        ri.almacen.setSelectedItem("");
        ri.txtcodigoproducto.setText("");
        ri.txtproducto.setText("");
        ri.txtcantidad.setText("");
        ri.txtproblema.setText("");
        ri.fecha.setDate(null);
        
        }
       
    }
    
    
    
    
    public void agregar(){
        
        String cod=(String)ri.txtcodigoreporte.getText();
        String tra=(String)ri.txttrabajador.getText();
        String alm=(String)ri.almacen.getSelectedItem();
        String cod_prod=(String)ri.txtcodigoproducto.getText();
        String prod=ri.txtproducto.getText();
        Integer can=Integer.parseInt(ri.txtcantidad.getText());
        String descr=ri.txtproblema.getText();
        java.util.Date fecha=ri.fecha.getDate();
   
        r.setCod_reporte(cod);
        r.setNom_trabajador(tra);
        r.setAlmacen(alm);
        r.setCod_producto(cod_prod);
        r.setProducto(prod);
        r.setCantidad(can);
        r.setDescripcion(descr);
        r.setFecha(fecha);
        
        int re=dao.Agregar(r);
        if (re == 1) {
    int opcion = JOptionPane.showConfirmDialog(ri, "Reporte agregado con éxito. ¿Desea imprimirlo?", "Confirmación", JOptionPane.YES_NO_OPTION);
    
    if (opcion == JOptionPane.YES_OPTION) {
         try {
        String reportName = "/reporte/reporte_irregularidad.jasper";
        java.util.Map<String, Object> parameters = new java.util.HashMap<>();

        InputStream inputStream = getClass().getResourceAsStream(reportName);

        if (inputStream == null) {
            throw new JRException("El archivo Jasper no se encontró: " + reportName);
        }

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());

        JasperViewer.viewReport(jasperPrint, false);
    } catch (JRException e) {
        e.printStackTrace();
    }
     
    } else {
        // Lógica si el usuario elige no imprimir
        System.out.println("El usuario eligió no imprimir el reporte.");
    }
        }else{
            JOptionPane.showMessageDialog(ri, "Error al agregar Reporte");
        }
    }
    
    
    
}
